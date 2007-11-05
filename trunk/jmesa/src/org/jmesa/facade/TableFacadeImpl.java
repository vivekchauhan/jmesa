/**
 * 
 */
package org.jmesa.facade;

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
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
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
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactory;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
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
 * TableFacade tableFacade = new TableFacadeImpl(id, request, maxRows, items, &quot;name.firstName&quot;, &quot;name.lastName&quot;, &quot;term&quot;, &quot;career&quot;);
 * String html = tableFacade.render();
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
    public static String PDF = "pdf";

    private HttpServletRequest request;
    private HttpServletResponse response;

    private String id;
    private int maxRows;
    private Collection<?> items;
    private String[] columnProperties;
    private String[] exportTypes;
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
     * This constructor is most useful if you are only using the facade to get at the Limit.
     * </p>
     * 
     * <p>
     * This constructor is also useful if you are doing the filtering and sorting manually and
     * building the table using the component factory. However be sure to set the RowSelect object,
     * items, ant table before calling the render method.
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
     * This constructor is useful if you are doing the filtering and sorting manually. However be
     * sure to set the RowSelect object and items before calling the render method.
     * </p>
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param columnProperties The columns to be pulled from the items.
     */
    public TableFacadeImpl(String id, HttpServletRequest request, String... columnProperties) {
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
     */
    public TableFacadeImpl(String id, HttpServletRequest request, Collection<Object> items, String... columnProperties) {
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
     */
    public TableFacadeImpl(String id, HttpServletRequest request, int maxRows, Collection<Object> items, String... columnProperties) {
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
     */
    public TableFacadeImpl(String id, HttpServletRequest request, Collection<Object> items) {
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
     */
    public TableFacadeImpl(String id, HttpServletRequest request, int maxRows, Collection<Object> items) {
        this.id = id;
        this.request = request;
        this.maxRows = maxRows;
        this.items = items;
    }

    public void setExportTypes(HttpServletResponse response, String... exportTypes) {
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
            if (l.isExportable()) {
                l.setRowSelect(new RowSelectImpl(1, items.size(), items.size()));
            } else {
                if (maxRows > 0) {
                    limitFactory.createRowSelect(maxRows, items.size(), l);
                }
            }
        }

        this.limit = l;

        if (logger.isDebugEnabled()) {
            if (limit.getRowSelect() == null) {
                logger.debug("The RowSelect is not set on the Limit. Be sure to call the setRowSelect() method on the facade.");
            }
        }

        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public RowSelect setRowSelect(int maxRows, int totalRows) {
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
        this.stateAttr = stateAttr;
    }

    public void performFilterAndSort(boolean performFilterAndSort) {
        this.performFilterAndSort = performFilterAndSort;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        if (coreContext != null) {
            throw new IllegalStateException(
                    "It is too late to add this FilterMatcher. You need to add the FilterMatcher right after constructing the TableFacade.");
        }

        if (filterMatchers == null) {
            filterMatchers = new HashMap<MatcherKey, FilterMatcher>();
        }

        filterMatchers.put(key, matcher);
    }

    public void addFilterMatcherMap(FilterMatcherMap filterMatcherMap) {
        if (coreContext != null) {
            throw new IllegalStateException(
                    "It is too late to add this FilterMatcher. You need to add the FilterMatcher right after constructing the TableFacade.");
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
                    "It is too late to add this ColumnSort. You need to add the ColumnSort right after constructing the TableFacade.");
        }

        this.columnSort = columnSort;
    }

    public void setRowFilter(RowFilter rowFilter) {
        if (coreContext != null) {
            throw new IllegalStateException(
                    "It is too late to add this RowFilter. You need to add the RowFilter right after constructing the TableFacade.");
        }

        this.rowFilter = rowFilter;
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        if (items == null) {
            throw new IllegalStateException("The items is null. You need to set the items on the facade.");
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
        this.coreContext = coreContext;
    }

    public Table getTable() {
        if (table != null) {
            return table;
        }

        if (columnProperties == null || columnProperties.length == 0) {
            throw new IllegalStateException(
                    "The column properties are null. You need to use the contructor with the columnProperties, or build the table with the factory.");
        }

        Limit l = getLimit();

        if (!l.isExportable()) {
            if (l.getRowSelect() == null) {
                throw new IllegalStateException(
                        "The RowSelect is null. You need to set the Limit RowSelect on the facade, or use the contructor with the maxRows.");
            }

            HtmlTableFactory tableFactory = new HtmlTableFactory(getWebContext(), getCoreContext());
            this.table = tableFactory.createTable(columnProperties);
        } else {
            String exportType = l.getExport().getType();
            if (exportType.equals(CSV)) {
                TableFactory tableFactory = new CsvTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else if (exportType.equals(EXCEL)) {
                TableFactory tableFactory = new ExcelTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else if (exportType.equals(PDF)) {
                TableFactory tableFactory = new HtmlTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnProperties);
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Toolbar getToolbar() {
        if (toolbar != null) {
            return toolbar;
        }

        ToolbarFactory toolbarFactory;

        if (maxRowsIncrements != null && maxRowsIncrements.length > 0) {
            toolbarFactory = new ToolbarFactoryImpl((HtmlTable) getTable(), maxRowsIncrements, getWebContext(), getCoreContext(), exportTypes);
        } else {
            toolbarFactory = new ToolbarFactoryImpl((HtmlTable) getTable(), getWebContext(), getCoreContext(), exportTypes);
        }

        this.toolbar = toolbarFactory.createToolbar();

        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setMaxRowsIncrements(int... maxRowsIncrements) {
        this.maxRowsIncrements = maxRowsIncrements;
    }

    public View getView() {
        if (view != null) {
            return view;
        }

        Limit l = getLimit();

        if (!l.isExportable()) {
            this.view = new HtmlView((HtmlTable) getTable(), getToolbar(), getCoreContext());
        } else {
            String exportType = l.getExport().getType();
            if (exportType.equals(CSV)) {
                this.view = new CsvView(getTable(), getCoreContext());
            } else if (exportType.equals(EXCEL)) {
                this.view = new ExcelView(getTable(), getCoreContext());
            } else if (exportType.equals(PDF)) {
                this.view = new PdfView((HtmlTable) getTable(), getToolbar(), getWebContext(), getCoreContext());
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String render() {
        Limit l = getLimit();

        if (!l.isExportable()) {
            return getView().render().toString();
        }

        String exportType = l.getExport().getType();
        if (exportType.equals(CSV)) {
            ViewExporter exporter = new CsvViewExporter(getView(), response);
            try {
                exporter.export();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (exportType.equals(EXCEL)) {
            ViewExporter exporter = new ExcelViewExporter(getView(), response);
            try {
                exporter.export();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (exportType.equals(PDF)) {
            ViewExporter exporter = new PdfViewExporter(getView(), request, response);
            try {
                exporter.export();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalStateException("Not able to handle the export of type: " + exportType);
        }

        return null;
    }
}
