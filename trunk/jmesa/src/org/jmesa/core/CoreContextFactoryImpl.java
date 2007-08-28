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
package org.jmesa.core;

import java.util.Collection;
import java.util.Date;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.DefaultRowFilter;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherRegistry;
import org.jmesa.core.filter.FilterMatcherRegistryImpl;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.filter.SimpleRowFilter;
import org.jmesa.core.filter.StringFilterMatcher;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.ResourceBundleMessages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PropertiesPreferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.core.sort.DefaultColumnSort;
import org.jmesa.core.sort.MultiColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.web.WebContext;

/**
 * <p>
 * Used to construct a CoreContext.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class CoreContextFactoryImpl implements CoreContextFactory {
    private WebContext webContext;
    private FilterMatcherRegistry registry;
    private RowFilter rowFilter;
    private ColumnSort columnSort;
    private Preferences preferences;
    private Messages messages;
    private boolean performFilterAndSort;

    /**
     * @param webContext The WebContext for the table.
     */
    public CoreContextFactoryImpl(WebContext webContext) {
        this(true, webContext);
    }

    /**
     * <p>
     * You would call this contructor if you do not want the items filtered and
     * sorted. You would only use this if you manually filtered and sorted the
     * items to display a page of data and, in which case, there is no reason to
     * do it again.
     * </p>
     * 
     * @param Is false if you do not want to have the items filtered and sorted.
     * @param webContext The WebContext for the table.
     */
    public CoreContextFactoryImpl(boolean performFilterAndSort, WebContext webContext) {
        this.performFilterAndSort = performFilterAndSort;
        this.webContext = webContext;
    }

    protected FilterMatcherRegistry getFilterMatcherRegistry() {
        if (registry == null) {
            registry = new FilterMatcherRegistryImpl();
            StringFilterMatcher stringFilterMatcher = new StringFilterMatcher();
            registry.addFilterMatcher(new MatcherKey(Object.class), stringFilterMatcher);

            DateFilterMatcher dateFilterMatcher = new DateFilterMatcher();
            dateFilterMatcher.setWebContext(webContext);
            registry.addFilterMatcher(new MatcherKey(Date.class), dateFilterMatcher);
        }

        return registry;
    }

    public void setFilterMatcherRegistry(FilterMatcherRegistry registry) {
        this.registry = registry;
    }

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        getFilterMatcherRegistry().addFilterMatcher(key, matcher);
    }

    protected RowFilter getRowFilter() {
        if (rowFilter == null) {
            rowFilter = new SimpleRowFilter(getFilterMatcherRegistry());
        }

        return rowFilter;
    }

    public void setRowFilter(RowFilter rowFilter) {
        this.rowFilter = rowFilter;
    }

    protected ColumnSort getColumnSort() {
        if (columnSort == null) {
            columnSort = new MultiColumnSort();
        }

        return columnSort;
    }

    public void setColumnSort(ColumnSort columnSort) {
        this.columnSort = columnSort;
    }

    protected Preferences getPreferences() {
        if (preferences == null) {
            String jmesaPreferencesLocation = (String) webContext.getApplicationInitParameter("jmesaPreferencesLocation");
            preferences = new PropertiesPreferences(jmesaPreferencesLocation, webContext);
        }

        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    protected Messages getMessages() {
        if (messages == null) {
            String jmesaMessagesLocation = (String) webContext.getApplicationInitParameter("jmesaMessagesLocation");
            messages = new ResourceBundleMessages(jmesaMessagesLocation, webContext);
        }

        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    /**
     * Is false if you do not want to have the items filtered and sorted. For
     * instance you would not want the API to filter and sort the items if you
     * did that step manually.
     */
    protected boolean isPerformFilterAndSort() {
        return performFilterAndSort;
    }

    /**
     * @param items The Collection of Beans or the Collection of Maps.
     * @param limit The Limit object.
     * @return The CoreContext object.
     */
    public CoreContext createCoreContext(Collection<Object> items, Limit limit) {
        Items itemsImpl;

        if (isPerformFilterAndSort()) {
            itemsImpl = new ItemsImpl(items, limit, getRowFilter(), getColumnSort());
        } else {
            itemsImpl = new ItemsImpl(items, limit, new DefaultRowFilter(), new DefaultColumnSort());
        }

        CoreContext coreContextImpl = new CoreContextImpl(itemsImpl, limit, getPreferences(), getMessages());
        return coreContextImpl;
    }
}
