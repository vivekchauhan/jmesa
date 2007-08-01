/**
 * 
 */
package org.jmesa.view;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
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
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public class TableFacadeImpl implements TableFacade {
    public static String CSV = "csv";
    public static String EXCEL = "excel";

    private HttpServletRequest request;
    private HttpServletResponse response;

    private String id;
    private int maxRows;
    private Collection<Object> items;
    private String[] columnNames;
    private String[] exportTypes;
    private WebContext webContext;
    private CoreContext coreContext;
    private Limit limit;
    private Table table;
    private Toolbar toolbar;
    private int[] maxRowsIncrements;
    private View view;
    private boolean performFilterAndSort = true;

    /**
     * The most common constructor that will be used to display a table and
     * exports. The intent is let the API do all the filtering and sorting
     * automatically.
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param maxRows The max rows to display on a page.
     * @param items The Collection of Beans or Collection of Maps.
     * @param columnNames The columns to be pulled from the items.
     */
    public TableFacadeImpl(String id, HttpServletRequest request, int maxRows, Collection<Object> items, String... columnNames) {
        this.id = id;
        this.maxRows = maxRows;
        this.items = items;
        this.request = request;
        this.columnNames = columnNames;
    }

    /**
     * This constructor is only useful if you are only using the facade for
     * exports, not html tables. This is because you are not setting the maxRows
     * which is always required for the html tables.
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param items The Collection of Beans or Collection of Maps.
     * @param columnNames The columns to be pulled from the items.
     */
    public TableFacadeImpl(String id, HttpServletRequest request, Collection<Object> items, String... columnNames) {
        this.id = id;
        this.items = items;
        this.request = request;
        this.columnNames = columnNames;
    }

    /**
     * This constructor is useful if you are doing the filtering and sorting
     * manually and intend to set the RowSelect object by yourself.
     * 
     * @param id The unique identifier for this table.
     * @param request The servlet request object.
     * @param columnNames The columns to be pulled from the items.
     */
    public TableFacadeImpl(String id, HttpServletRequest request, String... columnNames) {
        this.id = id;
        this.request = request;
        this.columnNames = columnNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setExportTypes(javax.servlet.http.HttpServletResponse,
     *      java.lang.String[])
     */
    public void setExportTypes(HttpServletResponse response, String... exportTypes) {
        this.response = response;
        this.exportTypes = exportTypes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#getWebContext()
     */
    public WebContext getWebContext() {
        if (webContext == null) {
            this.webContext = new HttpServletRequestWebContext(request);
        }

        return webContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setWebContext(org.jmesa.web.WebContext)
     */
    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#performFilterAndSort(boolean)
     */
    public void performFilterAndSort(boolean performFilterAndSort) {
        this.performFilterAndSort = performFilterAndSort;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setItems(java.util.Collection)
     */
    public void setItems(Collection<Object> items) {
        this.items = items;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#getCoreContext()
     */
    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        if (items == null) {
            throw new IllegalStateException("The items is null. You need to set the items on the facade.");
        }

        CoreContextFactory factory = new CoreContextFactoryImpl(performFilterAndSort, getWebContext());
        CoreContext cc = factory.createCoreContext(items, getLimit());

        return cc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setCoreContext(org.jmesa.core.CoreContext)
     */
    public void setCoreContext(CoreContext coreContext) {
        this.coreContext = coreContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#getLimit()
     */
    public Limit getLimit() {
        if (limit != null) {
            return limit;
        }

        LimitFactory limitFactory = new LimitFactoryImpl(id, getWebContext());
        Limit l = limitFactory.createLimit();

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

        return limit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setLimit(org.jmesa.limit.Limit)
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setRowSelect(int, int)
     */
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

        return rowSelect;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#getTable()
     */
    public Table getTable() {
        if (table != null) {
            return table;
        }

        Limit l = getLimit();

        if (!l.isExportable()) {
            if (l.getRowSelect() == null) {
                throw new IllegalStateException(
                        "The RowSelect is null. You need to set the Limit RowSelect on the facade, or use the contructor with the maxRows.");
            }

            HtmlTableFactory tableFactory = new HtmlTableFactory(getWebContext(), getCoreContext());
            this.table = tableFactory.createTable(columnNames);
        } else {
            String exportType = l.getExport().getType();
            if (exportType.equals(CSV)) {
                TableFactory tableFactory = new CsvTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnNames);
            } else if (exportType.equals(EXCEL)) {
                TableFactory tableFactory = new ExcelTableFactory(getWebContext(), getCoreContext());
                this.table = tableFactory.createTable(columnNames);
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#getToolbar()
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setToolbar(org.jmesa.view.html.toolbar.Toolbar)
     */
    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setMaxRowsIncrements(int... maxRowsIncrements) {
        this.maxRowsIncrements = maxRowsIncrements;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#getView()
     */
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
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return view;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#setView(org.jmesa.view.View)
     */
    public void setView(View view) {
        this.view = view;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jmesa.view.TableFacade#render()
     */
    public String render() {
        Limit l = getLimit();

        if (!l.isExportable()) {
            return getView().render().toString();
        } else {
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
            } else {
                throw new IllegalStateException("Not able to handle the export of type: " + exportType);
            }
        }

        return null;
    }
}
