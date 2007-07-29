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
     * 
     * @param maxRows
     * @param items
     * @param request
     * @param columns
     */
    public TableFacadeImpl(String id, HttpServletRequest request, int maxRows, Collection<Object> items, String... columnNames) {
        this.id = id;
        this.maxRows = maxRows;
        this.items = items;
        this.request = request;
        this.columnNames = columnNames;
    }

    /**
     * 
     * @param response
     * @param exportTypes
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

        WebContext wc = getWebContext();
        LimitFactory limitFactory = new LimitFactoryImpl(id, wc);
        Limit l = limitFactory.createLimit();

        if (maxRows > 0 && items != null) {
            if (l.isExportable()) {
                l.setRowSelect(new RowSelectImpl(1, items.size(), items.size()));
            } else {
                limitFactory.createRowSelect(maxRows, items.size(), l);
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
        RowSelect rowSelect;

        if (limit.isExportable()) {
            rowSelect = new RowSelectImpl(1, totalRows, totalRows);
            limit.setRowSelect(rowSelect);
        } else {
            LimitFactory limitFactory = new LimitFactoryImpl(id, getWebContext());
            rowSelect = limitFactory.createRowSelect(maxRows, totalRows, getLimit());
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
            toolbarFactory = new ToolbarFactoryImpl((HtmlTable) table, maxRowsIncrements, getWebContext(), getCoreContext(), exportTypes);
        } else {
            toolbarFactory = new ToolbarFactoryImpl((HtmlTable) table, getWebContext(), getCoreContext(), exportTypes);
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
                this.view = new CsvView(table, getCoreContext());
            } else if (exportType.equals(EXCEL)) {
                this.view = new ExcelView(table, getCoreContext());
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
            }
        }

        return null;
    }
}
