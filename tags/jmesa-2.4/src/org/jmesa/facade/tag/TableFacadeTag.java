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
package org.jmesa.facade.tag;

import static org.jmesa.facade.tag.TagUtils.getTableFacadeFilterMatcherMap;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeMessages;
import static org.jmesa.facade.tag.TagUtils.getTableFacadePreferences;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeRowFilter;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeColumnSort;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeToolbar;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeMaxRowIncrements;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeView;
import static org.jmesa.facade.tag.TagUtils.getTableFacadeExportTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.View;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.web.JspPageWebContext;
import org.jmesa.web.WebContext;

/**
 * A tag abstraction similar to the TableFacade. See the TableFacade document for more information.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class TableFacadeTag extends SimpleTagSupport {
    // facade attributes
    private String id;
    private Collection items;
    private Limit limit;
    private int maxRows;
    private String maxRowsIncrements;
    private String stateAttr;
    private boolean autoFilterAndSort = true;
    private String exportTypes;
    private String messages;
    private String preferences;
    private String rowFilter;
    private String columnSort;
    private String filterMatcherMap;
    private String view;
    private String toolbar;
    private boolean editable;    // tag attributes
    private String var;    // core attributes
    private TableFacade tableFacade;
    private HtmlTable table;
    private HtmlComponentFactory componentFactory;
    private Collection<Map<String, Object>> pageItems = new ArrayList<Map<String, Object>>();

    /**
     * The id to use.
     */
    public String getId() {
        return id;
    }

    /**
     * The unique identifier for this table. If you are using the stateAttr (State feature) then be
     * sure that this value is unique.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The Collecton of Beans (or Maps) to use.
     */
    public Collection getItems() {
        return items;
    }

    /**
     * Set the items, the Collection of Beans (or Maps), if not already set on the constructor.
     * Useful if performing the sorting and filtering manually and need to set the items on the
     * facade. If you are performing the sorting and filtering manually you should also set the
     * autoFilterAndSort() to false.
     * 
     * @param items The Collecton of Beans (or Maps) to use.
     */
    public void setItems(Collection items) {
        this.items = items;
    }

    /**
     * The max rows to display on a page.
     */
    public int getMaxRows() {
        return maxRows;
    }

    /**
     * The max rows to display on a page.
     * 
     * @param maxRows The maxRows to use.
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * The max rows increments to use.
     */
    public String getMaxRowsIncrements() {
        return maxRowsIncrements;
    }

    /**
     * Set the comma separated list of values to use for the max rows droplist. Be sure that one of
     * the values is the same as the maxRows set on the facade.
     * 
     * @param maxRowsIncrements The max rows increments to use.
     */
    public void setMaxRowsIncrements(String maxRowsIncrements) {
        this.maxRowsIncrements = maxRowsIncrements;
    }

    /**
     * The parameter that will be searched to see if the state should be used.
     */
    public String getStateAttr() {
        return stateAttr;
    }

    /**
     * Utilize the State interface to persist the Limit in the users HttpSession. Will persist the
     * Limit by the id.
     * 
     * @param stateAttr The parameter that will be searched to see if the state should be used.
     */
    public void setStateAttr(String stateAttr) {
        this.stateAttr = stateAttr;
    }

    /**
     * @return Is true if should sort and filter the Collection of Beans (or Maps).
     */
    public boolean isAutoFilterAndSort() {
        return autoFilterAndSort;
    }

    /**
     * By default the facade will sort and filter the Collection of Beans (or Maps) automatically.
     * This should be set to false if you are handling the filtering and sorting of the Collection
     * automatically.
     *
     * @param autoFilterAndSort
     */
    public void setAutoFilterAndSort(boolean autoFilterAndSort) {
        this.autoFilterAndSort = autoFilterAndSort;
    }

    /**
     * the comma separated list of export types. The currently supported types are
     * TableFacadeImpl.CVS and TableFacadeImpl.EXCEL.
     */
    public String getExportTypes() {
        return exportTypes;
    }

    /**
     * Set the comma separated list of export types. The currently supported types are
     * TableFacadeImpl.CVS and TableFacadeImpl.EXCEL.
     * 
     * @param exportTypes The exportTypes to use.
     */
    public void setExportTypes(String exportTypes) {
        this.exportTypes = exportTypes;
    }

    /**
     * @return Is true if the table should be editable.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Turn the table into an editable worksheet.
     * 
     * @param editable Is true if the table should be editable.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * The value to hold the current bean or map.
     */
    public String getVar() {
        return var;
    }

    /**
     * The value to hold the current bean or map.
     * 
     * @param var The var to use.
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * <p>
     * Get the Limit.
     * </p>
     * 
     * @return The Limit to use.
     */
    public Limit getLimit() {
        return limit;
    }

    /**
     * Set the Limit on the facade. This will override the Limit if it was previously set.
     * 
     * @param limit The Limit to use.
     */
    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    /**
     * Get the Messages. If the Messages does not exist then one will be created.
     * 
     * @return The Messages to use.
     */
    public String getMessages() {
        return messages;
    }

    /**
     * Set the Messages on the facade.
     * 
     * @param messages The Messages to use.
     */
    public void setMessages(String messages) {
        this.messages = messages;
    }

    /**
     * Get the Preferences. If the Preferences does not exist then one will be created.
     * 
     * @return The Preferences to use.
     */
    public String getPreferences() {
        return preferences;
    }

    /**
     * Set the Preferences on the facade.
     * 
     * @param preferences The Preferences to use.
     */
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    /**
     * Get the RowFilter. If the RowFilter does not exist then one will be created.
     * 
     * @return The RowFilter to use.
     */
    public String getRowFilter() {
        return rowFilter;
    }

    /**
     * Set the RowFilter on the facade.
     * 
     * @param rowFilter The RowFilter to use.
     */
    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    /**
     * Get the ColumnSort. If the ColumnSort does not exist then one will be created.
     * 
     * @return The ColumnSort to use.
     */
    public String getColumnSort() {
        return columnSort;
    }

    /**
     * Set the ColumnSort on the facade.
     * 
     * @param columnSort The ColumnSort to use.
     */
    public void setColumnSort(String columnSort) {
        this.columnSort = columnSort;
    }

    /**
     * @return The View to use.
     */
    public String getView() {
        return view;
    }

    /**
     * Set the View on the facade. This will override the View if it was previously set.
     * 
     * @param view The View to use.
     */
    public void setView(String view) {
        this.view = view;
    }

    /**
     * Get the Toolbar. If the Toolbar does not exist then one will be created.
     * 
     * @return The Toolbar to use.
     */
    public String getToolbar() {
        return toolbar;
    }

    /**
     * Set the Toolbar on the facade. This will override the Toolbar if it was previously set.
     * 
     * @param toolbar The Toolbar to use.
     */
    public void setToolbar(String toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * @return Get all the FilterMatchers needed for the current table.
     */
    public String getFilterMatcherMap() {
        return filterMatcherMap;
    }

    /**
     * Set the current Map of FilterMatchers.
     * 
     * @param filterMatcherMap The Map of current FilterMatchers.
     */
    public void setFilterMatcherMap(String filterMatcherMap) {
        this.filterMatcherMap = filterMatcherMap;
    }

    /**
     * @return Get the TableFacade.
     */
    TableFacade getTableFacade() {
        return tableFacade;
    }

    /**
     * @return The Table to use.
     */
    HtmlTable getTable() {
        return table;
    }

    /**
     * Set the Table on the facade.
     * 
     * @param table The HtmlTable to use.
     */
    void setTable(HtmlTable table) {
        this.table = table;
    }

    /**
     * @return Get the HtmlComponentFactory.
     */
    HtmlComponentFactory getComponentFactory() {
        if (componentFactory != null) {
            return componentFactory;
        }

        this.componentFactory = new HtmlComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());

        return componentFactory;
    }

    /**
     * These are the converted items. What happens is the regular items are passed to the columns,
     * but then the columns are resolved with the EL Expression Language. A new Map is swapped out
     * for each row Bean (or Map) and then used in place of the regular items. So in effect this is
     * a Collection of Maps that represents the original Collection of Beans (or Maps) after being
     * processed by the columns.
     * 
     * @return The converted items.
     */
    Collection<Map<String, Object>> getPageItems() {
        return pageItems;
    }

    /**
     * Process the table, rows, and columns. Will pass the current bean in the pageScope for each
     * bean in the Collection of Beans or Collection of Maps.
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
            throw new IllegalStateException("You need to wrap the table in the facade tag.");
        }

        this.tableFacade = createTableFacade();
        tableFacade.setEditable(isEditable());
        tableFacade.setItems(getItems());
        tableFacade.setMaxRows(getMaxRows());
        tableFacade.setStateAttr(getStateAttr());
        tableFacade.setLimit(getLimit());

        tableFacade.setMaxRowsIncrements(getTableFacadeMaxRowIncrements(getMaxRowsIncrements()));
        tableFacade.setExportTypes(null, getTableFacadeExportTypes(getExportTypes()));

        tableFacade.autoFilterAndSort(isAutoFilterAndSort());
        tableFacade.setPreferences(getTableFacadePreferences(getPreferences()));
        tableFacade.setColumnSort(getTableFacadeColumnSort(getColumnSort()));
        tableFacade.setRowFilter(getTableFacadeRowFilter(getRowFilter()));
        tableFacade.addFilterMatcherMap(getTableFacadeFilterMatcherMap(getFilterMatcherMap()));

        Collection<?> pi = tableFacade.getCoreContext().getPageItems();

        if (pi.size() == 0) {
            body.invoke(null);
            getPageItems().clear();
        } else {
            int count = HtmlUtils.startingRowcount(tableFacade.getCoreContext());
            for (Iterator<?> iterator = pi.iterator(); iterator.hasNext();) {
                Object item = iterator.next();
                getJspContext().setAttribute(getVar(), item);
                getJspContext().setAttribute("rowcount", ++count);
                body.invoke(null);
            }
        }

        getJspContext().removeAttribute(getVar()); // clean up the page scoped bean
        getJspContext().removeAttribute("rowcount");

        tableFacade.setTable(getTable());
        tableFacade.setToolbar(getTableFacadeToolbar(getToolbar()));
        tableFacade.setView(getTableFacadeView(getView()));
        tableFacade.getCoreContext().setPageItems(getPageItems());

        View v = tableFacade.getView();
        String html = v.render().toString();
        getJspContext().getOut().print(html);
    }

    protected WebContext getWebContext() {
        return new JspPageWebContext((PageContext) getJspContext());
    }

    protected TableFacade createTableFacade() {

        TableFacade facade = TableFacadeFactory.createTableFacade(getId(), getWebContext());
        facade.setMessages(getTableFacadeMessages(getMessages()));
        return facade;
    }
}
