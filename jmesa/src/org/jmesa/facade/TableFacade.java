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

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public interface TableFacade {
    /**
     * Set the comma separated list of export types. The currently supported types are
     * TableFacadeImpl.CVS and TableFacadeImpl.EXCEL.
     */
    public void setExportTypes(HttpServletResponse response, String... exportTypes);

    /**
     * Get the WebContext. If the WebContext does not exist then one will be created.
     * 
     * @return The WebContext to use.
     */
    public WebContext getWebContext();

    /**
     * Set the WebContext on the facade. This will override the WebContext if it was previously set.
     * 
     * @param webContext The WebContext to use.
     */
    public void setWebContext(WebContext webContext);

    /**
     * Set to editable worksheet mode on the facade.
     * 
     * @param editable Is true if the table is editable.
     * @since 2.3
     */
    public void setEditable(boolean editable);

    /**
     * <p>
     * Get the Limit. If the Limit does not exist then one will be created. If you are manually
     * sorting and filtering the table then as much of the Limit will be created as is possible. You
     * still might need to set the RowSelect on the facade, which will set it on the Limit.
     * </p>
     * 
     * <p>
     * If using the State interface then be sure to call the setState() method on the facade before
     * calling the Limit.
     * </p>
     * 
     * @return The Limit to use.
     */
    public Limit getLimit();

    /**
     * Set the Limit on the facade. This will override the Limit if it was previously set.
     * 
     * @param limit The Limit to use.
     */
    public void setLimit(Limit limit);

    /**
     * If you are manually sorting and filtering the table then you still need to ensure that you
     * set the RowSelect on the Limit. Using this method will set the RowSelect on the Limit. You
     * can also override any previously set RowSelect object.
     * 
     * @return The RowSelect set on the Limit.
     */
    public RowSelect setRowSelect(int maxRows, int totalRows);

    /**
     * Utilize the State interface to persist the Limit in the users HttpSession. Will persist the
     * Limit by the id.
     * 
     * @param stateAttr The parameter that will be searched to see if the state should be used.
     */
    public void setStateAttr(String stateAttr);

    /**
     * Get the CoreContext. If the CoreContext does not exist then one will be created.
     * 
     * @return The CoreContext to use.
     */
    public CoreContext getCoreContext();

    /**
     * Set the CoreContext on the facade. This will override the CoreContext if it was previously
     * set.
     * 
     * @param coreContext The CoreContext to use.
     */
    public void setCoreContext(CoreContext coreContext);

    /**
     * Set if the table needs to be filtered and sorted. By default the facade will sort and filter
     * the Collection of Beans (or Maps).
     * 
     * @param performFilterAndSort True if should sort and filter the Collection of Beans (or Maps).
     */
    public void performFilterAndSort(boolean performFilterAndSort);

    /**
     * Set the Messages on the facade. This will override the Messages if it was previously set.
     * 
     * @param messages The Messages to use.
     */
    public void setMessages(Messages messages);

    /**
     * Set the Preferences on the facade. This will override the Preferences if it was previously
     * set.
     * 
     * @param preferences The Preferences to use.
     */
    public void setPreferences(Preferences preferences);

    /**
     * Add a FilterMatcher on the facade. This will override the FilterMatcher if it was previously
     * set.
     * 
     * @param key The MatcherKey to use.
     * @param matcher The FilterMatcher to use.
     */
    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher);

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
     * 
     * @param filterMatcherMap The FilterMatcherMap to use.
     */
    public void addFilterMatcherMap(FilterMatcherMap filterMatcherMap);

    /**
     * Set the items, the Collection of Beans (or Maps), if not already set on the constructor.
     * Useful if performing the sorting and filtering manually and need to set the items on the
     * facade. If you are performing the sorting and filtering manually you should also set the
     * performFilterAndSort() to false because there is no reason to have the API try to sort and
     * filter if you have already done so.
     * 
     * @param items The Collecton of Beans (or Maps) to use.
     */
    public void setItems(Collection<Object> items);

    /**
     * Get the Table. If the Table does not exist then one will be created.
     * 
     * @return The Table to use.
     */
    public Table getTable();

    /**
     * Set the Table on the facade. This will override the Table if it was previously set.
     * 
     * @param table The Table to use.
     */
    public void setTable(Table table);

    /**
     * Get the Toolbar. If the Toolbar does not exist then one will be created.
     * 
     * @return The Toolbar to use.
     */
    public Toolbar getToolbar();

    /**
     * Set the Toolbar on the facade. This will override the Toolbar if it was previously set.
     * 
     * @param toolbar The Toolbar to use.
     */
    public void setToolbar(Toolbar toolbar);

    /**
     * Set the comma separated list of values to use for the max rows droplist. Be sure that one of
     * the values is the same as the maxRows set on the facade.
     * 
     * @param maxRowsIncrements The max rows increments to use.
     */
    public void setMaxRowsIncrements(int... maxRowsIncrements);

    /**
     * Get the View. If the View does not exist then one will be created.
     * 
     * @return The View to use.
     */
    public View getView();

    /**
     * Set the View on the facade. This will override the View if it was previously set.
     * 
     * @param view The View to use.
     */
    public void setView(View view);

    /**
     * Generate the view.
     * 
     * @return An html generated table will return the String markup. An export will be written out
     *         to the response and this method will return null.
     */
    public String render();
}
