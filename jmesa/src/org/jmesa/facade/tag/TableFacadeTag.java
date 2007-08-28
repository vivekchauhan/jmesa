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
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.sort.DefaultColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
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
    private Collection<Object> items;
    private int maxRows;
    private String maxRowsIncrements;
    private String stateAttr;
    private boolean performFilterAndSort = true;
    private String exportTypes;

    // tag attributes
    private String var;

    // core attributes
    private WebContext webContext;
    private Limit limit;
    private CoreContext coreContext;
    private FilterMatcherMap filterMatcherMap;
    private HtmlTable table;
    private View view;
    private Toolbar toolbar;
    private HtmlComponentFactory componentFactory;
    private Collection<Object> pageItems = new ArrayList<Object>();

    /**
     * The id to use.
     */
    public String getId() {
        return id;
    }

    /**
     * The unique identifier for this table. If you are using the stateAttr (State feature) then be
     * sure that this value is unique.
     * 
     * @return The id to use.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The Collecton of Beans (or Maps) to use.
     */
    public Collection<Object> getItems() {
        return items;
    }

    /**
     * Set the items, the Collection of Beans (or Maps), if not already set on the constructor.
     * Useful if performing the sorting and filtering manually and need to set the items on the
     * facade. If you are performing the sorting and filtering manually you should also set the
     * performFilterAndSort() to false.
     * 
     * @param The Collecton of Beans (or Maps) to use.
     */
    public void setItems(Collection<Object> items) {
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
     * Get the WebContext. If the WebContext does not exist then one will be created.
     * 
     * @return The WebContext to use.
     */
    public WebContext getWebContext() {
        if (webContext != null) {
            return webContext;
        }

        this.webContext = new JspPageWebContext((PageContext) getJspContext());

        return webContext;
    }

    /**
     * Set the WebContext on the facade. This will override the WebContext if it was previously set.
     * 
     * @param webContext The WebContext to use.
     */
    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
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
     * Get the CoreContext. If the CoreContext does not exist then one will be created.
     * 
     * @return The CoreContext to use.
     */
    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        TagCoreContextFactory coreContextFactory = new TagCoreContextFactory(isPerformFilterAndSort(), getWebContext());

        if (filterMatcherMap != null) {
            Map<MatcherKey, FilterMatcher> filterMatchers = filterMatcherMap.getFilterMatchers();
            Set<MatcherKey> keys = filterMatchers.keySet();
            for (MatcherKey key : keys) {
                FilterMatcher matcher = filterMatchers.get(key);
                coreContextFactory.addFilterMatcher(key, matcher);
            }
        }

        this.coreContext = coreContextFactory.createCoreContext(getItems(), getLimit());

        return coreContext;
    }

    /**
     * Set the CoreContext on the facade. This will override the CoreContext if it was previously
     * set.
     * 
     * @param coreContext The CoreContext to use.
     */
    public void setCoreContext(CoreContext coreContext) {
        this.coreContext = coreContext;
    }

    /**
     * @return Get all the FilterMatchers needed for the current table.
     */
    public FilterMatcherMap getFilterMatcherMap() {
        return filterMatcherMap;
    }

    /**
     * Set the current Map of FilterMatchers.
     * 
     * @param filterMatcherMap The Map of current FilterMatchers.
     */
    public void setFilterMatcherMap(FilterMatcherMap filterMatcherMap) {
        this.filterMatcherMap = filterMatcherMap;
    }

    /**
     * Get the Table. If the Table does not exist then one will be created.
     * 
     * @return The Table to use.
     */
    public HtmlTable getTable() {
        return table;
    }

    /**
     * Set the Table on the facade.
     * 
     * @param table The HtmlTable to use.
     */
    public void setTable(HtmlTable table) {
        this.table = table;
    }

    /**
     * Get the Toolbar. If the Toolbar does not exist then one will be created.
     * 
     * @return The Toolbar to use.
     */
    public Toolbar getToolbar() {
        if (toolbar != null) {
            return toolbar;
        }

        String[] exportTypes = StringUtils.split(getExportTypes(), ",");

        ToolbarFactoryImpl toolbarFactory;

        int[] toolbarMaxRowIncrements = getToolbarMaxRowIncrements();
        if (toolbarMaxRowIncrements != null && toolbarMaxRowIncrements.length > 0) {
            toolbarFactory = new ToolbarFactoryImpl((HtmlTable) getTable(), toolbarMaxRowIncrements, getWebContext(), getCoreContext(), exportTypes);
        } else {
            toolbarFactory = new ToolbarFactoryImpl((HtmlTable) getTable(), getWebContext(), getCoreContext(), exportTypes);
        }

        this.toolbar = toolbarFactory.createToolbar();

        return toolbar;
    }

    /**
     * Set the Toolbar on the facade. This will override the Toolbar if it was previously set.
     * 
     * @param toolbar The Toolbar to use.
     */
    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    protected int[] getToolbarMaxRowIncrements() {
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
     * @return The View to use.
     */
    public View getView() {
        if (view != null) {
            return view;
        }

        this.view = new HtmlView(getTable(), getToolbar(), getCoreContext());

        return view;
    }

    /**
     * Set the View on the facade. This will override the View if it was previously set.
     * 
     * @param view The View to use.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * @return The HtmlComponentFactory to use.
     */
    public HtmlComponentFactory getComponentFactory() {
        if (componentFactory != null) {
            return componentFactory;
        }

        this.componentFactory = new HtmlComponentFactory(getWebContext(), getCoreContext());

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
    public Collection<Object> getPageItems() {
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
            for (Iterator<Object> iterator = tagCoreContext.getPageItems().iterator(); iterator.hasNext();) {
                Object item = iterator.next();
                getWebContext().setPageAttribute(getVar(), item);
                body.invoke(null);
            }
        }

        tagCoreContext.setPageItems(getPageItems()); // morph the items

        View view = getView();
        String html = view.render().toString();
        getJspContext().getOut().print(html);
    }

    /**
     * An abstract to work with the items in a polymorphic manner. Will work with the regular items
     * until all the rows and columns are processed. Then will swap out regular items with the new
     * page items. See the getPageItems() method for more information.
     */
    protected static class TagCoreContext extends CoreContextImpl {
        private Collection<Object> pageItems;

        public TagCoreContext(Items items, Limit limit, Preferences preferences, Messages messages) {
            super(items, limit, preferences, messages);
        }

        public void setPageItems(Collection<Object> pageItems) {
            this.pageItems = pageItems;
        }

        @Override
        public Collection<Object> getPageItems() {
            if (pageItems == null) {
                return super.getPageItems();
            }

            return pageItems;
        }
    }

    /**
     * An implementation specifically for the tags to work with the TagCoreContext.
     * 
     */
    protected static class TagCoreContextFactory extends CoreContextFactoryImpl {
        public TagCoreContextFactory(boolean performFilterAndSort, WebContext webContext) {
            super(performFilterAndSort, webContext);
        }

        @Override
        public CoreContext createCoreContext(Collection<Object> items, Limit limit) {
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
