/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmesa.facade;

import static org.jmesa.facade.TableFacadeExceptions.validateColumnPropertiesIsNotNull;
import static org.jmesa.facade.TableFacadeExceptions.validateCoreContextIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateItemsIsNotNull;
import static org.jmesa.facade.TableFacadeExceptions.validateItemsIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateLimitIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateRowSelectIsNotNull;
import static org.jmesa.facade.TableFacadeExceptions.validateTableIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateToolbarIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateViewIsNull;
import static org.jmesa.facade.TableFacadeUtils.filterWorksheetItems;
import static org.jmesa.facade.TableFacadeUtils.isTableRefreshing;
import static org.jmesa.limit.LimitConstants.LIMIT_ROWSELECT_MAXROWS;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesFactory;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PreferencesFactory;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.state.SessionState;
import org.jmesa.limit.state.State;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.ExportTableFactory;
import org.jmesa.view.TableFactory;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvTableFactory;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.csv.CsvViewExporter;
import org.jmesa.view.excel.ExcelView;
import org.jmesa.view.excel.ExcelViewExporter;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlTableFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.toolbar.HtmlToolbar;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.jexcel.JExcelView;
import org.jmesa.view.jexcel.JExcelViewExporter;
import org.jmesa.view.pdf.PdfView;
import org.jmesa.view.pdf.PdfViewExporter;
import org.jmesa.view.pdfp.PdfPView;
import org.jmesa.view.pdfp.PdfPViewExporter;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetImpl;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;
import org.jmesa.worksheet.state.SessionWorksheetState;
import org.jmesa.worksheet.state.WorksheetState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This is a facade for working with tables that also has a little bit of builder in its veins. The
 * basic idea is you instantiate a TableFacade class and then interact with it in a natural way. The
 * facade completely abstracts away all the factory classes and eliminates a lot of boilerplate
 * code. The builder notion comes in because as you work with it it will internally build up
 * everything you need and keep track of it for you.
 * </p>
 *
 * <p>
 * Notice how there are no factories to deal with. However any API Object that you would have used
 * before is available through the facade, including the WebContext, CoreContext, Limit, Table,
 * Toolbar, and View. When you ask the facade for a given object it builds everything it needs up to
 * that point. Internally it keeps track of everything you are doing so it also works like a
 * builder.
 * </p>
 *
 * <p>
 * The TableFacade also has setters for all the facade objects including the WebContext,
 * CoreContext, Limit, Table, Toolbar, and View. The reason is if you really need to customize
 * something and want to set your own implementation you can. Your object just goes into the flow of
 * the facade. For instance if you want a custom toolbar just set the Toolbar on the facade and when
 * the render() method is called it will use your Toolbar.
 * </p>
 *
 * <p>
 * However, all this should feel very natural and you should not have to think about what you are
 * doing. Just interact with the facade how you need to and it will take care of everything.
 * </p>
 *
 * @since 2.1
 * @author Jeff Johnston
 */
public class TableFacade {
    private Logger logger = LoggerFactory.getLogger(TableFacade.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String id;
    private int maxRows;
    private Collection<?> items;
    private String[] columnProperties;
    private ExportType[] exportTypes;
    private WebContext webContext;
    private CoreContext coreContext;
    private Messages messages;
    private Preferences preferences;
    private Map<MatcherKey, FilterMatcher> filterMatchers;
    private RowFilter rowFilter;
    private ColumnSort columnSort;
    private Limit limit;
    private State state;
    private String stateAttr;
    private Table table;
    private Toolbar toolbar;
    private int[] maxRowsIncrements;
    private View view;
    private boolean autoFilterAndSort = true;
    private boolean editable;
    private Worksheet worksheet;
    private WorksheetState worksheetState;

    /**
     * <p>
     * Create the table with the id.
     * </p>
     *
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     */
    public TableFacade(String id, HttpServletRequest request) {
        this.id = id;
        this.request = request;
    }

    /**
     * <p>
     * Create the table with the id.
     * </p>
     *
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param response The servlet response object used for the exports.
     */
    public TableFacade(String id, HttpServletRequest request, HttpServletResponse response) {
        this.id = id;
        this.request = request;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    /**
     * Set the comma separated list of export types. The currently supported types are
     * ExportType.CVS, ExportType.EXCEL, ExportType.JEXCEL, and ExportType.PDF.
     *
     * @deprecated Use the setExportTypes() method that does not explicitly set the response.
     */
    @Deprecated
    public void setExportTypes(HttpServletResponse response, ExportType... exportTypes) {
        validateToolbarIsNull(toolbar, "exportTypes");

        this.response = response;
        this.exportTypes = exportTypes;
    }

    /**
     * Set the comma separated list of export types. The currently supported types are
     * ExportType.CVS, ExportType.EXCEL, ExportType.JEXCEL, and ExportType.PDF.
     */
    public void setExportTypes(ExportType... exportTypes) {
        validateToolbarIsNull(toolbar, "exportTypes");

        this.exportTypes = exportTypes;
    }

    /**
     * Get the WebContext. If the WebContext does not exist then one will be created.
     */
    public WebContext getWebContext() {
        if (webContext == null) {
            this.webContext = new HttpServletRequestWebContext(request);
        }

        return webContext;
    }

    /**
     * Set the WebContext on the facade. This will override the WebContext if it was previously set.
     */
    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;

        Object backingObject = webContext.getBackingObject();
        if (backingObject instanceof HttpServletRequest) {
            request = (HttpServletRequest) backingObject;
        }
    }

    /**
     * <p>
     * This feature will enable the table to be put in an editable state.
     * </p>
     *
     * @param editable If true will put table in an editable state.
     * @since 2.3
     */
    public void setEditable(boolean editable) {
        validateItemsIsNull(items);

        this.editable = editable;
    }

    /**
     * Get the Worksheet.
     * 
     * @since 2.3
     */
    public Worksheet getWorksheet() {
        if (worksheet != null || !editable) {
            return worksheet;
        }

        this.worksheetState = getWorksheetState();
        Worksheet ws = worksheetState.retrieveWorksheet();

        if (ws == null || !isTableRefreshing(id, getWebContext())) {
            ws = new WorksheetImpl(id, getMessages());
            persistWorksheet(ws);
        }

        this.worksheet = new WorksheetWrapper(ws, getWebContext());

        return worksheet;
    }

    /**
     * Add a row to the worksheet.
     */
    public void addWorksheetRow() {
    	addWorksheetRow(null);
    }

    /**
     * Add a row to the worksheet based on an already populated object.
     *
     * @param item (has to be same object as in collection of setItems()).
     */
    public void addWorksheetRow(Object item) {
        Worksheet ws = getWorksheet();
        if (ws != null) {
        	ws.addRow(item, getTable());
        }
        // for GAE
        persistWorksheet(ws);
    }

    /**
     * Get the WorksheetState.
     */
    private WorksheetState getWorksheetState() {
    	if (worksheetState == null) {
    		return new SessionWorksheetState(id, getWebContext());
    	}

    	return worksheetState;
    }

    /**
     * Saves the worksheet.
     *
     * @since 2.5.2
     */
    public void persistWorksheet(Worksheet worksheet) {
    	getWorksheetState().persistWorksheet(worksheet.getWorksheetImpl());
    }

    /**
     * Remove a worksheet row.
     */
    public void removeWorksheetRow() {
        String up = getLimit().getId() + "_" +  WorksheetWrapper.REMOVE_WORKSHEET_ROW;
        String name = getTable().getRow().getUniqueProperty();
        String value = getWebContext().getParameter(up);
        UniqueProperty uniqueProperty = new UniqueProperty(name, value);

        Worksheet ws = getWorksheet();
        WorksheetRow wsRow = ws.getRow(uniqueProperty);

        if (wsRow != null) {
            if (wsRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
                // remove row if ADDED in worksheet
                ws.removeRow(wsRow);
            } else if (wsRow.getRowStatus().equals(WorksheetRowStatus.REMOVE)) {
                // undo - remove
                if (wsRow.getColumns().size() == 0) {
                    ws.removeRow(uniqueProperty);
                } else {
                    wsRow.setRowStatus(WorksheetRowStatus.MODIFY);
                }
            } else {
                wsRow.setRowStatus(WorksheetRowStatus.REMOVE);

                boolean keepChangedValues = Boolean.parseBoolean(
                        getCoreContext().getPreference(HtmlConstants.REMOVE_ROW_KEEP_CHANGED_VALUES));
                if (!keepChangedValues) {
                    wsRow.getColumns().clear();
                }
            }
        } else {
            // add a new REMOVED row in worksheet
            wsRow = new WorksheetRow(uniqueProperty);
            wsRow.setRowStatus(WorksheetRowStatus.REMOVE);
            ws.addRow(wsRow);
        }
        // for GAE
        persistWorksheet(ws);
    }

    /**
     * <p>
     * Get the Limit. If the Limit does not exist then one will be created. If you are manually
     * sorting and filtering the table then as much of the Limit will be created as is possible. You
     * still might need to set the totalRows on the facade, which will set it on the Limit.
     * </p>
     *
     * <p>
     * If using the State interface then be sure to set that on the facade before requesting the Limit.
     * </p>
     */
    public Limit getLimit() {
        if (limit != null) {
            return limit;
        }

        LimitFactory limitFactory = new LimitFactory(id, getWebContext());
        limitFactory.setState(getState());
        Limit l = limitFactory.createLimit();

        /*
         * We will have a RowSelect if the State returned a Limit. However,
         * we will still need to re-populate the Limit's RowSelect based
         * on what the current items (if available) total rowcount is.
         */
        if (l.hasRowSelect()) {
            if (items != null) {
                int p = l.getRowSelect().getPage();
                int mr = l.getRowSelect().getMaxRows();
                RowSelect rowSelect = new RowSelect(p, mr, items.size());
                l.setRowSelect(rowSelect);
            }
            this.limit = l;
            return limit;
        }

        /*
         * We need to populate the Limit's RowSelect based on what the current
         * items (if available) total rowcount is.
         */
        if (items != null) {
            if (l.hasExport()) {
                RowSelect rowSelect = new RowSelect(1, items.size(), items.size());
                l.setRowSelect(rowSelect);
            } else {
                RowSelect rowSelect = limitFactory.createRowSelect(getMaxRows(), items.size());
                l.setRowSelect(rowSelect);
            }
        }

        this.limit = l;
        return limit;
    }

    /**
     * Set the Limit on the facade. This will override the Limit if it was previously set.
     */
    public void setLimit(Limit limit) {
        validateCoreContextIsNull(coreContext, "Limit");

        this.limit = limit;
    }

    /**
     * If you are manually sorting and filtering the table then you still need to ensure that you
     * set the RowSelect on the Limit. Using this method will set the RowSelect on the Limit.
     *
     * @return The totalRows to set on the Limit.
     */
    public RowSelect setTotalRows(int totalRows) {
        RowSelect rowSelect;

        Limit l = getLimit();

        if (l.hasExport()) {
            rowSelect = new RowSelect(1, totalRows, totalRows);
        } else {
            LimitFactory limitFactory = new LimitFactory(id, getWebContext());
            rowSelect = limitFactory.createRowSelect(getMaxRows(), totalRows);
        }
           
        l.setRowSelect(rowSelect);

        return rowSelect;
    }

    public State getState() {
        if (state != null) {
            return state;
        }

        if (stateAttr == null) {
            return null;
        }

        this.state = new SessionState();
        SupportUtils.setId(state, id);
        SupportUtils.setStateAttr(state, stateAttr);
        SupportUtils.setWebContext(state, getWebContext());
        return state;
    }

    /**
     * Sets the State on the facade.
     */
    public void setState(State state) {
        validateLimitIsNull(limit, "state");

        this.state = state;
        SupportUtils.setId(state, id);
        SupportUtils.setStateAttr(state, stateAttr);
        SupportUtils.setWebContext(state, getWebContext());
    }

    /**
     * Utilize the State interface to persist the Limit in the users HttpSession. Will persist the
     * Limit by the id.
     *
     * @param stateAttr The parameter that will be searched to see if the state should be used.
     */
    public void setStateAttr(String stateAttr) {
        validateLimitIsNull(limit, "stateAttr");

        this.stateAttr = stateAttr;
    }

    /**
     * By default the facade will sort and filter the Collection of Beans (or Maps) automatically.
     * This should be set to false if you are handling the filtering and sorting of the Collection
     * automatically.
     *
     * @param autoFilterAndSort True if should sort and filter the Collection of Beans (or Maps) automatically.
     */
    public void autoFilterAndSort(boolean autoFilterAndSort) {

        validateCoreContextIsNull(coreContext, "autoFilterAndSort");

        this.autoFilterAndSort = autoFilterAndSort;
    }

    /**
     * Get the Messages. If the Messages does not exist then one will be created.
     */
    public Messages getMessages() {
        if (messages != null) {
            return messages;
        }

        this.messages = MessagesFactory.getMessages(getWebContext());
        return messages;
    }

    /**
     * Set the Messages on the facade. This will override the Messages if it was previously set.
     */
    public void setMessages(Messages messages) {
        validateCoreContextIsNull(coreContext, "Messages");

        this.messages = messages;
        SupportUtils.setWebContext(messages, getWebContext());
    }

    /**
     * Get the Preferences. If the Preferences does not exist then one will be created.
     */
    public Preferences getPreferences() {
        if (preferences != null) {
            return preferences;
        }

        this.preferences = PreferencesFactory.getPreferences(getWebContext());
        return preferences;
    }

    /**
     * Set the Preferences on the facade. This will override the Preferences if it was previously
     * set.
     */
    public void setPreferences(Preferences preferences) {
        validateCoreContextIsNull(coreContext, "Preferences");

        this.preferences = preferences;
        SupportUtils.setWebContext(preferences, getWebContext());
    }

    /**
     * Add a FilterMatcher on the facade. This will override the FilterMatcher if it was previously
     * set.
     */
    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        validateCoreContextIsNull(coreContext, "FilterMatcher");

        if (filterMatchers == null) {
            filterMatchers = new HashMap<MatcherKey, FilterMatcher>();
        }

        filterMatchers.put(key, matcher);
    }

    /**
     * <p>
     * Add a FilterMatcherMap on the facade. Will add the various FilterMatchers to the facade using
     * the FilterMatcherMap interface.
     * </p>
     *
     * <p>
     * Most useful for the tag library because they have to use the FilterMatcherMap to register
     * filter matcher strategies, but could also be used as a way to bundle up filter matchers.
     * </p>
     */
    public void addFilterMatcherMap(FilterMatcherMap filterMatcherMap) {
        validateCoreContextIsNull(coreContext, "FilterMatcher");

        if (filterMatcherMap == null) {
            return;
        }

        SupportUtils.setWebContext(filterMatcherMap, getWebContext());

        Map<MatcherKey, FilterMatcher> matchers = filterMatcherMap.getFilterMatchers();
        Set<MatcherKey> keys = matchers.keySet();
        for (MatcherKey key : keys) {
            FilterMatcher matcher = matchers.get(key);
            addFilterMatcher(key, matcher);
        }
    }

    /**
     * Set the ColumnSort on the facade. This will override the ColumnSort if it was previously set.
     */
    public void setColumnSort(ColumnSort columnSort) {
        validateCoreContextIsNull(coreContext, "ColumnSort");

        this.columnSort = columnSort;
        SupportUtils.setWebContext(columnSort, getWebContext());
    }

    /**
     * Set the RowFilter on the facade. This will override the RowFilter if it was previously set.
     */
    public void setRowFilter(RowFilter rowFilter) {
        validateCoreContextIsNull(coreContext, "RowFilter");

        this.rowFilter = rowFilter;
        SupportUtils.setWebContext(rowFilter, getWebContext());
    }

    /**
     * Set the items, the Collection of Beans (or Maps).If you are performing the sorting and filtering
     * manually you should also set the autoFilterAndSort() to false because there is no reason to
     * have the API try to sort and filter if you have already done so.
     *
     * @param items The Collecton of Beans (or Maps) to use.
     */
    public void setItems(Collection<?> items) {
        validateCoreContextIsNull(coreContext, "items");

        if (editable) {
            this.items = filterWorksheetItems(items, getWorksheet());
        } else {
            this.items = items;
        }
    }

    public int getMaxRows() {
        if (maxRows == 0) {
            Preferences pref = getPreferences();
            String mr = pref.getPreference(LIMIT_ROWSELECT_MAXROWS);
            this.maxRows = Integer.valueOf(mr);
        }

        return maxRows;
    }

    /**
     * Set the maxRows on the facade. The max rows is the total rows that will display
     * on one page. This will override the maxRows if it was previously set.
     */
    public void setMaxRows(int maxRows) {
        validateCoreContextIsNull(coreContext, "maxRows");

        this.maxRows = maxRows;
    }

    /**
     * Set the column properties used for the table.
     *
     * @param columnProperties The columns to be pulled from the items.
     *
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public void setColumnProperties(String... columnProperties) {
        validateTableIsNull(table, "columnProperties");

        this.columnProperties = columnProperties;
    }

    /**
     * Get the CoreContext. If the CoreContext does not exist then one will be created.
     */
    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        validateItemsIsNotNull(items);

        CoreContextFactory factory = new CoreContextFactory(autoFilterAndSort, getWebContext());
        factory.setPreferences(getPreferences());
        factory.setMessages(getMessages());
        factory.setColumnSort(columnSort);
        factory.setRowFilter(rowFilter);

        if (filterMatchers != null) {
            Set<MatcherKey> keySet = filterMatchers.keySet();
            for (MatcherKey key : keySet) {
                FilterMatcher matcher = filterMatchers.get(key);
                factory.addFilterMatcher(key, matcher);
            }
        }

        this.coreContext = factory.createCoreContext(items, getLimit(), getWorksheet());
        return coreContext;
    }

    /**
     * Set the CoreContext on the facade. This will override the CoreContext if it was previously set.
     */
    public void setCoreContext(CoreContext coreContext) {
        validateTableIsNull(table, "CoreContext");

        this.coreContext = coreContext;
        SupportUtils.setWebContext(coreContext, getWebContext());
    }

    /**
     * Get the Table. If the Table does not exist then one will be created.
     */
    public Table getTable() {
        //TODO: the follow code should be removed when the 3.0 deprecated methods are removed.
        if (table == null) {
            validateColumnPropertiesIsNotNull(columnProperties);

            Limit l = getLimit();

            if (!l.isExported()) {
                validateRowSelectIsNotNull(l);

                HtmlTableFactory tableFactory = new HtmlTableFactory();
                this.table = tableFactory.createTable(columnProperties);
            } else {
                this.table = getExportTable(l.getExportType());
            }
        }

        TableFacadeUtils.initTable(this, table);
        
        return table;
    }

    protected Table getExportTable(ExportType exportType) {

        //TODO: the follow code should be removed when the 3.0 deprecated methods are removed.
        
        TableFactory tableFactory = null;

        if (exportType == ExportType.CSV) {
            tableFactory = new CsvTableFactory();
        } else if (exportType == ExportType.EXCEL) {
            tableFactory = new ExportTableFactory();
        } else if (exportType == ExportType.JEXCEL) {
            tableFactory = new ExportTableFactory();
        } else if (exportType == ExportType.PDF) {
            tableFactory = new HtmlTableFactory();
        } else if (exportType == ExportType.PDFP) {
            tableFactory = new ExportTableFactory();
        }

        if (tableFactory != null) {
            return tableFactory.createTable(columnProperties);
        }

        throw new IllegalStateException("Not able to handle the export of type: " + exportType);
    }

    /**
     * Set the Table on the facade. This will override the Table if it was previously set.
     */
    public void setTable(Table table) {
        validateViewIsNull(view, "Table");

        this.table = table;
    }

    /**
     * Get the Toolbar. If the Toolbar does not exist then one will be created.
     */
    public Toolbar getToolbar() {
        if (toolbar != null) {
            return toolbar;
        }

        this.toolbar = new HtmlToolbar();
        SupportUtils.setTable(toolbar, getTable());
        SupportUtils.setCoreContext(toolbar, getCoreContext());
        SupportUtils.setWebContext(toolbar, getWebContext());
        SupportUtils.setMaxRowsIncrements(toolbar, maxRowsIncrements);
        SupportUtils.setExportTypes(toolbar, exportTypes);

        return toolbar;
    }

    /**
     * Set the Toolbar on the facade. This will override the Toolbar if it was previously set.
     */
    public void setToolbar(Toolbar toolbar) {
        validateViewIsNull(view, "Toolbar");

        this.toolbar = toolbar;
        SupportUtils.setTable(toolbar, getTable());
        SupportUtils.setCoreContext(toolbar, getCoreContext());
        SupportUtils.setWebContext(toolbar, getWebContext());
        SupportUtils.setMaxRowsIncrements(toolbar, maxRowsIncrements);
        SupportUtils.setExportTypes(toolbar, exportTypes);
    }

    /**
     * Set the comma separated list of values to use for the max rows droplist. Be sure that one of
     * the values is the same as the maxRows set on the facade.
     */
    public void setMaxRowsIncrements(int... maxRowsIncrements) {
        validateToolbarIsNull(toolbar, "maxRowsIncrements");

        this.maxRowsIncrements = maxRowsIncrements;
    }

    /**
     * Get the View. If the View does not exist then one will be created.
     */
    public View getView() {
        if (view != null) {
            return view;
        }

        Limit l = getLimit();

        if (!l.isExported()) {
            setView(new HtmlView());
        } else {
            this.view = getExportView(l.getExportType());
        }

        return view;
    }

    protected View getExportView(ExportType exportType) {

        View exportView = null;

        if (exportType == ExportType.CSV) {
            exportView = new CsvView();
        } else if (exportType == ExportType.EXCEL) {
            exportView = new ExcelView();
        } else if (exportType == ExportType.JEXCEL) {
            exportView = new JExcelView();
        } else if (exportType == ExportType.PDF) {
            exportView = new PdfView();
        } else if (exportType == ExportType.PDFP) {
            exportView= new PdfPView();
        }

        if (exportView != null) {
            SupportUtils.setCoreContext(exportView, getCoreContext());
            SupportUtils.setWebContext(exportView, getWebContext());
            SupportUtils.setTable(exportView, getTable());
            return exportView;
        }

        throw new IllegalStateException("Not able to handle the export of type: " + exportType);
    }

    /**
     * Set the View on the facade. This will override the View if it was previously set.
     */
    public void setView(View view) {
        this.view = view;
        SupportUtils.setTable(view, getTable());
        SupportUtils.setToolbar(view, getToolbar());
        SupportUtils.setCoreContext(view, getCoreContext());
        SupportUtils.setWebContext(view, getWebContext());
    }

    /**
     * Generate the view.
     *
     * @return An html generated table will return the String markup. An export will be written out
     *         to the response and this method will return null.
     */
    public String render() {
        Limit l = getLimit();
        View v = getView();

        if (!l.isExported()) {
            return v.render().toString();
        }

        ExportType exportType = l.getExportType();
        renderExport(exportType, v);

        return null;
    }

    protected void renderExport(ExportType exportType, View view) {
        if (response == null) {
                throw new IllegalStateException(
                    "The HttpServletResponse is null. You need to call the " +
                    "TableFacadeImpl constructor (or factory) with the response object.");
        }

        try {
            CoreContext cc = getCoreContext();

            if (exportType == ExportType.CSV) {
                new CsvViewExporter(view, cc, response).export();
            } else if (exportType == ExportType.EXCEL) {
                new ExcelViewExporter(view, cc, response).export();
            } else if (exportType == ExportType.JEXCEL) {
                new JExcelViewExporter(view, cc, response).export();
            } else if (exportType == ExportType.PDF) {
                new PdfViewExporter(view, cc, request, response).export();
            } else if (exportType == ExportType.PDFP) {
                new PdfPViewExporter(view, cc, request, response).export();
            }
        } catch (Exception e) {
            logger.error("Not able to perform the " + exportType + " export.", e);
        }
    }
}
