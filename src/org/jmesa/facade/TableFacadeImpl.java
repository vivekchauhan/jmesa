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

import static org.jmesa.limit.LimitConstants.LIMIT_ROWSELECT_MAXROWS;
import static org.jmesa.facade.TableFacadeUtils.isTableRefreshing;

import static org.jmesa.facade.TableFacadeExceptions.validateCoreContextIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateTableIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateViewIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateToolbarIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateLimitIsNull;
import static org.jmesa.facade.TableFacadeExceptions.validateColumnPropertiesIsNotNull;
import static org.jmesa.facade.TableFacadeExceptions.validateRowSelectIsNotNull;
import static org.jmesa.facade.TableFacadeExceptions.validateItemsIsNotNull;
import static org.jmesa.facade.TableFacadeExceptions.validateItemsIsNull;

import static org.jmesa.facade.TableFacadeUtils.filterWorksheetItems;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactoryImpl;
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
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.TableFactory;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvTableFactory;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.csv.CsvViewExporter;
import org.jmesa.view.excel.ExcelTableFactory;
import org.jmesa.view.excel.ExcelView;
import org.jmesa.view.excel.ExcelViewExporter;
import org.jmesa.view.html.HtmlTableFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.HtmlToolbar;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.jexcel.JExcelTableFactory;
import org.jmesa.view.jexcel.JExcelView;
import org.jmesa.view.jexcel.JExcelViewExporter;
import org.jmesa.view.pdf.PdfView;
import org.jmesa.view.pdf.PdfViewExporter;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetImpl;
import org.jmesa.worksheet.state.SessionWorksheetState;
import org.jmesa.worksheet.state.WorksheetState;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public class TableFacadeImpl implements TableFacade {

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
    private String stateAttr;
    private Table table;
    private Toolbar toolbar;
    private int[] maxRowsIncrements;
    private View view;
    private boolean autoFilterAndSort = true;
    private boolean editable;
    private Worksheet worksheet;

    /**
     * <p>
     * Create the table with the id.
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     */
    public TableFacadeImpl(String id, HttpServletRequest request) {
        this.id = id;
        this.request = request;
    }

    public void setExportTypes(HttpServletResponse response, ExportType... exportTypes) {
        validateToolbarIsNull(toolbar, "exportTypes");
        
        this.response = response;
        this.exportTypes = exportTypes;
    }

    public WebContext getWebContext() {
        if (webContext == null) {
            this.webContext = new HttpServletRequestWebContext(request);
        }

        return webContext;
    }

    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
    }

    public void setEditable(boolean editable) {
        validateItemsIsNull(items);
        
        this.editable = editable;
    }
    
    public Worksheet getWorksheet() {
        if (worksheet != null || !editable) {
            return worksheet;
        }
        
        WorksheetState state = new SessionWorksheetState(id, getWebContext());
        Worksheet ws = state.retrieveWorksheet();
        
        if (ws == null || !isTableRefreshing(id, getWebContext())) {
            ws = new WorksheetImpl(id, getMessages());
            state.persistWorksheet(ws);
        } 
        
        this.worksheet = new WorksheetWrapper(ws, getWebContext());

        return worksheet;
    }
    
    public Limit getLimit() {
        if (limit != null) {
            return limit;
        }

        LimitFactory limitFactory = new LimitFactoryImpl(id, getWebContext());
        limitFactory.setStateAttr(stateAttr);
        Limit l = limitFactory.createLimit();

        if (l.isComplete()) {
            this.limit = l;
            return limit;
        }

        if (items != null) {
            if (l.isExported()) {
                l.setRowSelect(new RowSelectImpl(1, items.size(), items.size()));
            } else {
                limitFactory.createRowSelect(getMaxRows(), items.size(), l);
            }
        }
        
        this.limit = l;
        return limit;
    }

    public void setLimit(Limit limit) {
        validateCoreContextIsNull(coreContext, "Limit");

        this.limit = limit;
    }

    public RowSelect setTotalRows(int totalRows) {
        RowSelect rowSelect;

        Limit l = getLimit();

        if (l.isExported()) {
            rowSelect = new RowSelectImpl(1, totalRows, totalRows);
            l.setRowSelect(rowSelect);
        } else {
            LimitFactory limitFactory = new LimitFactoryImpl(id, getWebContext());
            rowSelect = limitFactory.createRowSelect(getMaxRows(), totalRows, l);
        }

        return rowSelect;
    }

    public void setStateAttr(String stateAttr) {
        validateLimitIsNull(limit, "stateAttr");

        this.stateAttr = stateAttr;
    }

    public void autoFilterAndSort(boolean autoFilterAndSort) {

        validateCoreContextIsNull(coreContext, "autoFilterAndSort");

        this.autoFilterAndSort = autoFilterAndSort;
    }

    @Deprecated
    public void performFilterAndSort(boolean performFilterAndSort) {
        validateCoreContextIsNull(coreContext, "performFilterAndSort");
        
        this.autoFilterAndSort = performFilterAndSort;
    }
    
    Messages getMessages() {
        if (messages != null) {
            return messages;
        }

        this.messages = MessagesFactory.getMessages(getWebContext());
        return messages;
    }

    public void setMessages(Messages messages) {
        validateCoreContextIsNull(coreContext, "Messages");
        
        this.messages = messages;
        SupportUtils.setWebContext(messages, getWebContext());
    }

    Preferences getPreferences() {
        if (preferences != null) {
            return preferences;
        }

        this.preferences = PreferencesFactory.getPreferences(getWebContext());
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        validateCoreContextIsNull(coreContext, "Preferences");

        this.preferences = preferences;
        SupportUtils.setWebContext(preferences, getWebContext());
    }

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        validateCoreContextIsNull(coreContext, "FilterMatcher");

        if (filterMatchers == null) {
            filterMatchers = new HashMap<MatcherKey, FilterMatcher>();
        }

        filterMatchers.put(key, matcher);
    }

    public void addFilterMatcherMap(FilterMatcherMap filterMatcherMap) {
        validateCoreContextIsNull(coreContext, "FilterMatcher");

        if (filterMatcherMap == null) {
            return;
        }

        Map<MatcherKey, FilterMatcher> matchers = filterMatcherMap.getFilterMatchers();
        Set<MatcherKey> keys = matchers.keySet();
        for (MatcherKey key : keys) {
            FilterMatcher matcher = matchers.get(key);
            addFilterMatcher(key, matcher);
        }
    }

    public void setColumnSort(ColumnSort columnSort) {
        validateCoreContextIsNull(coreContext, "ColumnSort");

        this.columnSort = columnSort;
        SupportUtils.setWebContext(columnSort, getWebContext());
    }

    public void setRowFilter(RowFilter rowFilter) {
        validateCoreContextIsNull(coreContext, "RowFilter");

        this.rowFilter = rowFilter;
        SupportUtils.setWebContext(rowFilter, getWebContext());
    }

    public void setItems(Collection<?> items) {
        validateCoreContextIsNull(coreContext, "items");
        
        if (editable) {
            this.items = filterWorksheetItems(items, getWorksheet());
        } else {
            this.items = items;
        }
    }

    int getMaxRows() {
        if (maxRows == 0) {
            Preferences pref = getPreferences();
            String mr = pref.getPreference(LIMIT_ROWSELECT_MAXROWS);
            this.maxRows = Integer.valueOf(mr);
        }

        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        validateCoreContextIsNull(coreContext, "maxRows");

        this.maxRows = maxRows;
    }

    public void setColumnProperties(String... columnProperties) {
        validateTableIsNull(table, "columnProperties");

        this.columnProperties = columnProperties;
    }

    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }
        
        validateItemsIsNotNull(items);

        CoreContextFactoryImpl factory = new CoreContextFactoryImpl(autoFilterAndSort, getWebContext());
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

    public void setCoreContext(CoreContext coreContext) {
        validateTableIsNull(table, "CoreContext");

        this.coreContext = coreContext;
        SupportUtils.setWebContext(coreContext, getWebContext());
    }

    public Table getTable() {
        if (table != null) {
            return table;
        }
        
        validateColumnPropertiesIsNotNull(columnProperties);

        Limit l = getLimit();

        if (!l.isExported()) {
            validateRowSelectIsNotNull(l);

            HtmlTableFactory tableFactory = new HtmlTableFactory(getWebContext(), getCoreContext());
            this.table = tableFactory.createTable(columnProperties);
        } else {
            ExportType exportType = l.getExportType();
            if (exportType == ExportType.CSV) {
                TableFactory tableFactory = new CsvTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else if (exportType == ExportType.EXCEL) {
                TableFactory tableFactory = new ExcelTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else if (exportType == ExportType.PDF) {
                TableFactory tableFactory = new HtmlTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else if (exportType == ExportType.JEXCEL) {
                TableFactory tableFactory = new JExcelTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return table;
    }

    public void setTable(Table table) {
        validateViewIsNull(view, "Table");

        this.table = table;
    }

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

    public void setToolbar(Toolbar toolbar) {
        validateViewIsNull(view, "Toolbar");

        this.toolbar = toolbar;
        SupportUtils.setTable(toolbar, getTable());
        SupportUtils.setCoreContext(toolbar, getCoreContext());
        SupportUtils.setWebContext(toolbar, getWebContext());
        SupportUtils.setMaxRowsIncrements(toolbar, maxRowsIncrements);
        SupportUtils.setExportTypes(toolbar, exportTypes);
    }

    public void setMaxRowsIncrements(int... maxRowsIncrements) {
        validateToolbarIsNull(toolbar, "maxRowsIncrements");

        this.maxRowsIncrements = maxRowsIncrements;
    }

    public View getView() {
        if (view != null) {
            return view;
        }

        Limit l = getLimit();

        if (!l.isExported()) {
            setView(new HtmlView());
        } else {
            ExportType exportType = l.getExportType();
            if (exportType == ExportType.CSV) {
                this.view = new CsvView(getTable(), getCoreContext());
            } else if (exportType == ExportType.EXCEL) {
                this.view = new ExcelView(getTable(), getCoreContext());
            } else if (exportType == ExportType.PDF) {
                this.view = new PdfView((HtmlTable) getTable(), getToolbar(), getWebContext(), getCoreContext());
            } else if (exportType == ExportType.JEXCEL) {
                this.view = new JExcelView(getTable(), getCoreContext());
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return view;
    }

    public void setView(View view) {
        this.view = view;
        SupportUtils.setTable(view, getTable());
        SupportUtils.setToolbar(view, getToolbar());
        SupportUtils.setCoreContext(view, getCoreContext());
        SupportUtils.setWebContext(view, getWebContext());
    }

    public String render() {
        Limit l = getLimit();
        View v = getView();

        if (!l.isExported()) {
            return v.render().toString();
        }

        try {
            ExportType exportType = l.getExportType();
            if (exportType == ExportType.CSV) {
                new CsvViewExporter(v, response).export();
            } else if (exportType == ExportType.EXCEL) {
                new ExcelViewExporter(v, response).export();
            } else if (exportType == ExportType.JEXCEL) {
                new JExcelViewExporter(v, response).export();
            } else if (exportType == ExportType.PDF) {
                new PdfViewExporter(v, request, response).export();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
