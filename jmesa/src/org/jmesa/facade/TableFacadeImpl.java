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

import static org.jmesa.core.CoreContextFactoryImpl.JMESA_PREFERENCES_LOCATION;
import static org.jmesa.limit.LimitConstants.LIMIT_ROWSELECT_MAXROWS;

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
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PropertiesPreferences;
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
import org.jmesa.view.ViewExporter;
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
import org.jmesa.view.html.toolbar.DefaultToolbar;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.jexcel.JExcelTableFactory;
import org.jmesa.view.jexcel.JExcelView;
import org.jmesa.view.jexcel.JExcelViewExporter;
import org.jmesa.view.pdf.PdfView;
import org.jmesa.view.pdf.PdfViewExporter;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.state.SessionWorksheetState;
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
 * The simple example is:
 * </p>
 * 
 * <pre>
 *  TableFacade tableFacade = new TableFacadeImpl(id, request);
 *  tableFacade.setColumnProperties("name.firstName", "name.lastName", "term", "career", "born");\
 *  tableFacade.setItems(items);
 *  String html = tableFacade.render();
 * </pre>
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
 * The TableFacade interface also has setters for all the facade objects including the WebContext,
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
public class TableFacadeImpl implements TableFacade {

    private Logger logger = LoggerFactory.getLogger(TableFacadeImpl.class);
    public static String CSV = "csv";
    public static String EXCEL = "excel";
    public static String JEXCEL = "jexcel";
    public static String PDF = "pdf";
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
    private boolean editable;
    private Limit limit;
    private String stateAttr;
    private Table table;
    private Toolbar toolbar;
    private int[] maxRowsIncrements;
    private View view;
    private boolean performFilterAndSort = true;

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

    /**
     * <p>
     * Create the table with all the column properties.
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param columnProperties The columns to be pulled from the items.
     * @deprecated Replaced by {@link #TableFacadeImpl(String,HttpServletRequest)}
     */
    @Deprecated public TableFacadeImpl(String id, HttpServletRequest request, String... columnProperties) {
        this.id = id;
        this.request = request;
        this.columnProperties = columnProperties;
    }

    /**
     * <p>
     * This constructor is only useful if you are only using the facade for exports, not html
     * tables. This is because you are not setting the maxRows which is always required for the html
     * tables.
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param items The Collection of Beans or Collection of Maps.
     * @param columnProperties The columns to be pulled from the items.
     * @deprecated Replaced by {@link #TableFacadeImpl(String,HttpServletRequest)}
     */
    @Deprecated public TableFacadeImpl(String id, HttpServletRequest request, Collection<?> items, String... columnProperties) {
        this.id = id;
        this.request = request;
        this.items = items;
        this.columnProperties = columnProperties;
    }

    /**
     * <p>
     * The most common constructor that will be used to display an html table and exports. The
     * intent is let the API do all the filtering and sorting automatically.
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param maxRows The max rows to display on a page.
     * @param items The Collection of Beans or Collection of Maps.
     * @param columnProperties The columns to be pulled from the items.
     * @deprecated Replaced by {@link #TableFacadeImpl(String,HttpServletRequest)}
     */
    @Deprecated public TableFacadeImpl(String id, HttpServletRequest request, int maxRows, Collection<?> items, String... columnProperties) {
        this.id = id;
        this.request = request;
        this.maxRows = maxRows;
        this.items = items;
        this.columnProperties = columnProperties;
    }

    /**
     * <p>
     * This constructor is only useful if you are only using the facade for exports, not html
     * tables, and building the table using using the component factory. This is because you are not
     * setting the maxRows which is always required for the html tables.
     * </p>
     * 
     * <p>
     * This will not build the table automatically because there are no columns defined so you neeed
     * to build the table (probably using the component factory available) and then set the table on
     * the facade!
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param items The Collection of Beans or Collection of Maps.
     * @deprecated Replaced by {@link #TableFacadeImpl(String,HttpServletRequest)}
     */
    @Deprecated public TableFacadeImpl(String id, HttpServletRequest request, Collection<?> items) {
        this.id = id;
        this.request = request;
        this.items = items;
    }

    /**
     * <p>
     * The most common constructor that will be used to display an html table and exports if you are
     * using the component factory to build the table. The intent is let the API do all the
     * filtering and sorting automatically.
     * </p>
     * 
     * <p>
     * This will not build the table automatically because there are no columns defined so you neeed
     * to build the table (probably using the component factory available) and then set the table on
     * the facade!
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param maxRows The max rows to display on a page.
     * @param items The Collection of Beans or Collection of Maps.
     * @deprecated Replaced by {@link #TableFacadeImpl(String,HttpServletRequest)}
     */
    @Deprecated public TableFacadeImpl(String id, HttpServletRequest request, int maxRows, Collection<?> items) {
        this.id = id;
        this.request = request;
        this.maxRows = maxRows;
        this.items = items;
    }

    @Deprecated public void setExportTypes(HttpServletResponse response, String... exportTypes) {
        this.response = response;
        
        this.exportTypes = new ExportType[exportTypes.length];
        for (int i = 0; i < exportTypes.length; i++) {
            this.exportTypes[i] = ExportType.valueOfParam(exportTypes[i]);
        }
    }
    
    public void setExportTypes(HttpServletResponse response, ExportType... exportTypes) {
        if (toolbar != null) {
            throw new IllegalStateException(
                "It is too late to set the exportTypes. You need to set the exportTypes before using the Toolbar.");
        }

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
        this.editable = editable;
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

        if (logger.isDebugEnabled()) {
            if (limit.getRowSelect() == null) {
                logger.debug("The RowSelect is not set on the Limit. Be sure to set the totalRows on the facade.");
            }
        }

        return limit;
    }

    public void setLimit(Limit limit) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the Limit. You need to set the Limit before using the CoreContext.");
        }

        this.limit = limit;
    }

    public RowSelect setTotalRows(int totalRows) {
        return setRowSelect(getMaxRows(), totalRows);
    }

    /**
     * <p>
     * Use the setTotalRows method, which should be easier to understand. Be sure to set the maxRows on the 
     * facade after constructing a new TableFacadeImpl object.
     * </p>
     * 
     * @deprecated Replaced by {@link #setTotalRows(int)}
     */
    @Deprecated public RowSelect setRowSelect(int maxRows, int totalRows) {
        this.maxRows = maxRows;

        RowSelect rowSelect;

        Limit l = getLimit();

        if (l.isExportable()) {
            rowSelect = new RowSelectImpl(1, totalRows, totalRows);
            l.setRowSelect(rowSelect);
        } else {
            LimitFactory limitFactory = new LimitFactoryImpl(id, getWebContext());
            rowSelect = limitFactory.createRowSelect(maxRows, totalRows, l);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("The RowSelect is now set on the Limit.");
        }

        return rowSelect;
    }

    public void setStateAttr(String stateAttr) {
        if (limit != null) {
            throw new IllegalStateException(
                "It is too late to set the stateAttr. You need to set the stateAttr before using the Limit.");
        }

        this.stateAttr = stateAttr;
    }

    public void performFilterAndSort(boolean performFilterAndSort) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the performFilterAndSort. You need to set the performFilterAndSort before using the CoreContext.");
        }

        this.performFilterAndSort = performFilterAndSort;
    }

    public void setMessages(Messages messages) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the Messages. You need to set the Messages before using the CoreContext.");
        }

        this.messages = messages;
        SupportUtils.setWebContext(messages, getWebContext());
    }

    /**
     * @return Get the preferences if they are not set.
     */
    Preferences getPreferences() {
        if (preferences != null) {
            return preferences;
        }

        WebContext wc = getWebContext();
        String jmesaPreferencesLocation = (String) wc.getApplicationInitParameter(JMESA_PREFERENCES_LOCATION);
        this.preferences = new PropertiesPreferences(jmesaPreferencesLocation, wc);
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the Preferences. You need to set the Preferences before using the CoreContext.");
        }

        this.preferences = preferences;
        SupportUtils.setWebContext(preferences, getWebContext());
    }

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to add the FilterMatcher. You need to add the FilterMatcher before using the CoreContext.");
        }

        if (filterMatchers == null) {
            filterMatchers = new HashMap<MatcherKey, FilterMatcher>();
        }

        filterMatchers.put(key, matcher);
    }

    public void addFilterMatcherMap(FilterMatcherMap filterMatcherMap) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to add the FilterMatcher. You need to add the FilterMatcher before using the CoreContext.");
        }

        if (filterMatcherMap == null) {
            return;
        }

        Map<MatcherKey, FilterMatcher> filterMatchers = filterMatcherMap.getFilterMatchers();
        Set<MatcherKey> keys = filterMatchers.keySet();
        for (MatcherKey key : keys) {
            FilterMatcher matcher = filterMatchers.get(key);
            addFilterMatcher(key, matcher);
        }
    }

    public void setColumnSort(ColumnSort columnSort) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the ColumnSort. You need to set the ColumnSort before using the CoreContext.");
        }

        this.columnSort = columnSort;
        SupportUtils.setWebContext(columnSort, getWebContext());
    }

    public void setRowFilter(RowFilter rowFilter) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the RowFilter. You need to set the RowFilter before using the CoreContext.");
        }

        this.rowFilter = rowFilter;
        SupportUtils.setWebContext(rowFilter, getWebContext());
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    /**
     * @return Get the maxRows if they are not set.
     */
    int getMaxRows() {
        if (maxRows == 0) {
            Preferences pref = getPreferences();
            String mr = pref.getPreference(LIMIT_ROWSELECT_MAXROWS);
            this.maxRows = Integer.valueOf(mr);
        }

        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the maxRows. You need to set the maxRows before using the CoreContext.");
        }

        this.maxRows = maxRows;
    }

    public void setColumnProperties(String... columnProperties) {
        if (table != null) {
            throw new IllegalStateException(
                "It is too late to set the columnProperties. You need to set the columnProperties before using the Table.");
        }

        this.columnProperties = columnProperties;
    }

    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        if (items == null) {
            throw new IllegalStateException("The items are null. You need to set the items on the facade.");
        }

        CoreContextFactoryImpl factory = new CoreContextFactoryImpl(performFilterAndSort, getWebContext());

        factory.setPreferences(preferences);
        factory.setMessages(messages);
        factory.setColumnSort(columnSort);
        factory.setRowFilter(rowFilter);

        if (filterMatchers != null) {
            Set<MatcherKey> keySet = filterMatchers.keySet();
            for (MatcherKey key : keySet) {
                FilterMatcher matcher = filterMatchers.get(key);
                factory.addFilterMatcher(key, matcher);
            }
        }

        CoreContext cc = factory.createCoreContext(items, getLimit());
        cc.setEditable(editable);
        if (editable) {
            cc.setWorksheetState(new SessionWorksheetState(id, getWebContext()));
        }

        this.coreContext = cc;

        return cc;
    }

    public void setCoreContext(CoreContext coreContext) {
        if (view != null) {
            throw new IllegalStateException(
                "It is too late to set the CoreContext. You need to set the CoreContext before using the Toolbar or View.");
        }

        this.coreContext = coreContext;
        SupportUtils.setWebContext(coreContext, getWebContext());
    }

    public Table getTable() {
        if (table != null) {
            return table;
        }

        if (columnProperties == null || columnProperties.length == 0) {
            throw new IllegalStateException(
                "The column properties are null. You need to set the columnProperties, or build the Table with the factory.");
        }

        Limit l = getLimit();

        if (!l.isExported()) {
            if (l.getRowSelect() == null) {
                throw new IllegalStateException(
                    "The RowSelect is null. You need to set the totalRows on the facade.");
            }

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
        if (view != null) {
            throw new IllegalStateException(
                "It is too late to set the Table. You need to set the Table before using the Toolbar or View.");
        }

        this.table = table;
    }

    public Toolbar getToolbar() {
        if (toolbar != null) {
            return toolbar;
        }

        this.toolbar = new DefaultToolbar();
        SupportUtils.setTable(toolbar, getTable());
        SupportUtils.setCoreContext(toolbar, getCoreContext());
        SupportUtils.setWebContext(toolbar, getWebContext());
        SupportUtils.setMaxRowsIncrements(toolbar, maxRowsIncrements);
        SupportUtils.setExportTypes(toolbar, exportTypes);

        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        if (view != null) {
            throw new IllegalStateException(
                "It is too late to set the Toolbar. You need to set the Toolbar before using the View.");
        }

        this.toolbar = toolbar;
        SupportUtils.setTable(toolbar, getTable());
        SupportUtils.setCoreContext(toolbar, getCoreContext());
        SupportUtils.setWebContext(toolbar, getWebContext());
        SupportUtils.setMaxRowsIncrements(toolbar, maxRowsIncrements);
        SupportUtils.setExportTypes(toolbar, exportTypes);
    }

    public void setMaxRowsIncrements(int... maxRowsIncrements) {
        if (toolbar != null) {
            throw new IllegalStateException(
                "It is too late to set the maxRowsIncrements. You need to set the maxRowsIncrements before using the Toolbar or View.");
        }

        this.maxRowsIncrements = maxRowsIncrements;
    }

    public View getView() {
        if (view != null) {
            return view;
        }

        Limit l = getLimit();

        if (!l.isExported()) {
            this.view = new HtmlView((HtmlTable) getTable(), getToolbar(), getCoreContext());
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

        ExportType exportType = l.getExportType();

        try {
            if (exportType == ExportType.CSV) {
                ViewExporter exporter = new CsvViewExporter(v, response);
                exporter.export();
            } else if (exportType == ExportType.EXCEL) {
                ViewExporter exporter = new ExcelViewExporter(v, response);
                exporter.export();
            } else if (exportType == ExportType.JEXCEL) {
                ViewExporter exporter = new JExcelViewExporter(v, response);
                exporter.export();
            } else if (exportType == ExportType.PDF) {
                ViewExporter exporter = new PdfViewExporter(v, request, response);
                exporter.export();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
