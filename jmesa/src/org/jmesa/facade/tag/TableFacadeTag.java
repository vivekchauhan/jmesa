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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.core.CoreContextImpl;
import org.jmesa.core.Items;
import org.jmesa.core.ItemsImpl;
import org.jmesa.core.filter.DefaultRowFilter;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.core.sort.DefaultColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.View;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
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
    private Collection<?> items;
    private Limit limit;
    private int maxRows;
    private String maxRowsIncrements;
    private String stateAttr;
    private boolean performFilterAndSort = true;
    private String exportTypes;
    private String messages;
    private String preferences;
    private String rowFilter;
    private String columnSort;
    private String filterMatcherMap;
    private String view;
    private String toolbar;

    // tag attributes
    private String var;

    // core attributes
    private WebContext webContext;
    private CoreContext coreContext;
    private HtmlTable table;
    private HtmlComponentFactory componentFactory;
    private Collection<Map<String, ?>> pageItems = new ArrayList<Map<String, ?>>();

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
    public Collection<?> getItems() {
        return items;
    }

    /**
     * Set the items, the Collection of Beans (or Maps), if not already set on the constructor.
     * Useful if performing the sorting and filtering manually and need to set the items on the
     * facade. If you are performing the sorting and filtering manually you should also set the
     * performFilterAndSort() to false.
     * 
     * @param items The Collecton of Beans (or Maps) to use.
     */
    public void setItems(Collection<?> items) {
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
     * True if should sort and filter the Collection of Beans (or Maps).
     */
    public boolean isPerformFilterAndSort() {
        return performFilterAndSort;
    }

    /**
     * Set if the table needs to be filtered and sorted. By default the facade will sort and filter
     * the Collection of Beans (or Maps).
     * 
     * @param performFilterAndSort True if should sort and filter the Collection of Beans (or Maps).
     */
    public void setPerformFilterAndSort(boolean performFilterAndSort) {
        this.performFilterAndSort = performFilterAndSort;
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
     * Get the Limit. If the Limit does not exist then one will be created.
     * </p>
     * 
     * @return The Limit to use.
     */
    public Limit getLimit() {
        if (limit != null) {
            return limit;
        }

        LimitFactory limitFactory = new LimitFactoryImpl(getId(), getWebContext());
        limitFactory.setStateAttr(stateAttr);
        this.limit = limitFactory.createLimit();
        if (limit.isComplete()) {
            return limit;
        }

        limitFactory.createRowSelect(getMaxRows(), getItems().size(), limit);

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
     * Get the WebContext. If the WebContext does not exist then one will be created.
     * 
     * @return Get the WebContext object.
     */
    WebContext getWebContext() {
        if (webContext != null) {
            return webContext;
        }

        this.webContext = new JspPageWebContext((PageContext) getJspContext());

        return webContext;
    }

    /**
     * Get the CoreContext. If the CoreContext does not exist then one will be created.
     * 
     * @return Get the CoreContext object.
     */
    CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        TagCoreContextFactory factory = new TagCoreContextFactory(isPerformFilterAndSort(), getWebContext());

        factory.setPreferences(getTableFacadePreferences());
        factory.setMessages(getTableFacadeMessages());
        factory.setColumnSort(getTableFacadeColumnSort());
        factory.setRowFilter(getTableFacadeRowFilter());

        FilterMatcherMap filterMatcherMap = getTableFacadeFilterMatcherMap();
        if (filterMatcherMap != null) {
            Map<MatcherKey, FilterMatcher> filterMatchers = filterMatcherMap.getFilterMatchers();
            Set<MatcherKey> keys = filterMatchers.keySet();
            for (MatcherKey key : keys) {
                FilterMatcher matcher = filterMatchers.get(key);
                factory.addFilterMatcher(key, matcher);
            }
        }

        this.coreContext = factory.createCoreContext(getItems(), getLimit());

        return coreContext;
    }

    /**
     * @return Get the FilterMatcherMap object.
     */
    private FilterMatcherMap getTableFacadeFilterMatcherMap() {
        if (StringUtils.isEmpty(getFilterMatcherMap())) {
            return null;
        }

        FilterMatcherMap filterMatcherMap = (FilterMatcherMap) ClassUtils.createInstance(getFilterMatcherMap());
        SupportUtils.setWebContext(filterMatcherMap, getWebContext());

        return filterMatcherMap;
    }

    /**
     * @return Get the Messages object.
     */
    private Messages getTableFacadeMessages() {
        if (StringUtils.isEmpty(getMessages())) {
            return null;
        }

        Messages messages = (Messages) ClassUtils.createInstance(getMessages());
        SupportUtils.setWebContext(messages, getWebContext());

        return messages;
    }

    /**
     * @return Get the Preferences object.
     */
    private Preferences getTableFacadePreferences() {
        if (StringUtils.isEmpty(getPreferences())) {
            return null;
        }

        Preferences preferences = (Preferences) ClassUtils.createInstance(getPreferences());
        SupportUtils.setWebContext(preferences, getWebContext());

        return preferences;
    }

    /**
     * @return Get the RowFilter object.
     */
    private RowFilter getTableFacadeRowFilter() {
        if (StringUtils.isEmpty(getRowFilter())) {
            return null;
        }

        RowFilter rowFilter = (RowFilter) ClassUtils.createInstance(getRowFilter());
        SupportUtils.setWebContext(rowFilter, getWebContext());

        return rowFilter;
    }

    /**
     * @return Get the ColumnSort object.
     */
    private ColumnSort getTableFacadeColumnSort() {
        if (StringUtils.isEmpty(getColumnSort())) {
            return null;
        }

        ColumnSort columnSort = (ColumnSort) ClassUtils.createInstance(getColumnSort());
        SupportUtils.setWebContext(columnSort, getWebContext());

        return columnSort;
    }

    /**
     * Get the Toolbar. If the Toolbar does not exist then one will be created.
     * 
     * @return Get the Toolbar object.
     */
    private Toolbar getTableFacadeToolbar() {
        if (StringUtils.isEmpty(getToolbar())) {
            ToolbarFactoryImpl toolbarFactory;

            String[] exportTypes = StringUtils.split(getExportTypes(), ",");

            int[] toolbarMaxRowIncrements = getTableFacadeMaxRowIncrements();
            if (toolbarMaxRowIncrements != null && toolbarMaxRowIncrements.length > 0) {
                toolbarFactory = new ToolbarFactoryImpl(getTable(), toolbarMaxRowIncrements, getWebContext(), getCoreContext(), exportTypes);
            } else {
                toolbarFactory = new ToolbarFactoryImpl(getTable(), getWebContext(), getCoreContext(), exportTypes);
            }

            return toolbarFactory.createToolbar();
        }

        Toolbar toolbar = (Toolbar) ClassUtils.createInstance(getToolbar());
        SupportUtils.setTable(toolbar, getTable());
        SupportUtils.setCoreContext(toolbar, getCoreContext());
        SupportUtils.setWebContext(toolbar, getWebContext());

        return toolbar;
    }

    /**
     * Convert the max row increments from a string to an int array.
     * 
     * @return Get the max row increments.
     */
    private int[] getTableFacadeMaxRowIncrements() {
        if (StringUtils.isEmpty(getMaxRowsIncrements())) {
            return null;
        }

        String[] maxRowIncrements = StringUtils.split(getMaxRowsIncrements(), ",");

        int[] toolbarMaxRowIncrements = new int[maxRowIncrements.length];

        for (int i = 0; i < maxRowIncrements.length; i++) {
            toolbarMaxRowIncrements[i] = Integer.parseInt(maxRowIncrements[i]);
        }

        return toolbarMaxRowIncrements;
    }

    /**
     * Get the View. If the View does not exist then one will be created.
     * 
     * @return Get the View object.
     */
    private View getTableFacadeView() {
        if (StringUtils.isEmpty(getView())) {
            return new HtmlView(getTable(), getTableFacadeToolbar(), getCoreContext());
        }

        View view = (View) ClassUtils.createInstance(getView());
        SupportUtils.setTable(view, getTable());
        SupportUtils.setToolbar(view, getTableFacadeToolbar());
        SupportUtils.setCoreContext(view, getCoreContext());
        SupportUtils.setWebContext(view, getWebContext());

        return view;
    }

    /**
     * @return Get the HtmlComponentFactory.
     */
    HtmlComponentFactory getComponentFactory() {
        if (componentFactory != null) {
            return componentFactory;
        }

        this.componentFactory = new HtmlComponentFactory(getWebContext(), getCoreContext());

        return componentFactory;
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
     * These are the converted items. What happens is the regular items are passed to the columns,
     * but then the columns are resolved with the EL Expression Language. A new Map is swapped out
     * for each row Bean (or Map) and then used in place of the regular items. So in effect this is
     * a Collection of Maps that represents the original Collection of Beans (or Maps) after being
     * processed by the columns.
     * 
     * @return The converted items.
     */
    Collection<Map<String, ?>> getPageItems() {
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

        TagCoreContext tagCoreContext = (TagCoreContext) getCoreContext();

        if (tagCoreContext.getPageItems().size() == 0) {
            body.invoke(null);
        } else {
            for (Iterator<?> iterator = tagCoreContext.getPageItems().iterator(); iterator.hasNext();) {
                Object item = iterator.next();
                getWebContext().setPageAttribute(getVar(), item);
                body.invoke(null);
            }
        }

        getWebContext().removePageAttribute(getVar()); // clean up the page scoped bean

        tagCoreContext.setPageItems(getPageItems()); // morph the items

        View view = getTableFacadeView();
        String html = view.render().toString();
        getJspContext().getOut().print(html);
    }

    /**
     * An abstract to work with the items in a polymorphic manner. Will work with the regular items
     * until all the rows and columns are processed. Then will swap out regular items with the new
     * page items. See the getPageItems() method for more information.
     */
    private static class TagCoreContext extends CoreContextImpl {
        private Collection<?> pageItems;

        public TagCoreContext(Items items, Limit limit, Preferences preferences, Messages messages) {
            super(items, limit, preferences, messages);
        }

        public void setPageItems(Collection<?> pageItems) {
            this.pageItems = pageItems;
        }

        @Override
        public Collection<?> getPageItems() {
            if (pageItems == null) {
                return super.getPageItems();
            }

            return pageItems;
        }
    }

    /**
     * An implementation specifically for the tags to work with the TagCoreContext.
     */
    private static class TagCoreContextFactory extends CoreContextFactoryImpl {
        public TagCoreContextFactory(boolean performFilterAndSort, WebContext webContext) {
            super(performFilterAndSort, webContext);
        }

        @Override
        public CoreContext createCoreContext(Collection<?> items, Limit limit) {
            Items itemsImpl;

            if (isPerformFilterAndSort()) {
                itemsImpl = new ItemsImpl(items, limit, getRowFilter(), getColumnSort());
            } else {
                itemsImpl = new ItemsImpl(items, limit, new DefaultRowFilter(), new DefaultColumnSort());
            }

            CoreContext coreContextImpl = new TagCoreContext(itemsImpl, limit, getPreferences(), getMessages());
            return coreContextImpl;
        }
    }
}
