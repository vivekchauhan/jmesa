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

import org.jmesa.core.filter.DefaultRowFilter;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherRegistry;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.filter.SimpleRowFilter;
import org.jmesa.core.filter.StringFilterMatcher;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesFactory;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PreferencesFactory;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.core.sort.DefaultColumnSort;
import org.jmesa.core.sort.MultiColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.util.SupportUtils;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;

/**
 * Used to create a CoreContext object.
 *
 * @since 2.0
 * @author Jeff Johnston
 */
public class CoreContextFactory {
    private WebContext webContext;
    private FilterMatcherRegistry registry;
    private RowFilter rowFilter;
    private ColumnSort columnSort;
    private Preferences preferences;
    private Messages messages;
    private boolean autoFilterAndSort;

    /**
     * <p>
     * You would call this contructor if you do not want the items filtered and
     * sorted. You would only use this if you manually filtered and sorted the
     * items to display a page of data and, in which case, there is no reason to
     * do it again.
     * </p>
     *
     * @param autoFilterAndSort Is false if you do not want to have the items filtered and sorted automatically by the API.
     * @param webContext The WebContext for the table.
     */
    public CoreContextFactory(boolean autoFilterAndSort, WebContext webContext) {
        this.autoFilterAndSort = autoFilterAndSort;
        this.webContext = webContext;
    }

    protected FilterMatcherRegistry getFilterMatcherRegistry() {
        if (registry == null) {
            registry = new FilterMatcherRegistry();
            StringFilterMatcher stringFilterMatcher = new StringFilterMatcher();
            registry.addFilterMatcher(new MatcherKey(Object.class), stringFilterMatcher);
        }

        return registry;
    }

    /**
     * Add a FilterMatcher to the FilterMatcherRegistry..
     *
     * @param key The MatcherKey instance.
     * @param matcher The FilterMatcher instance.
     */
    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        SupportUtils.setWebContext(matcher, webContext);
        getFilterMatcherRegistry().addFilterMatcher(key, matcher);
    }

    protected RowFilter getRowFilter() {
        if (rowFilter == null) {
            rowFilter = new SimpleRowFilter();
        }
        SupportUtils.setFilterMatcherRegistry(rowFilter, getFilterMatcherRegistry());

        return rowFilter;
    }

    /**
     * Set the RowFilter. Will override the default implementation.
     *
     * @param rowFilter The RowFilter instance.
     */
    public void setRowFilter(RowFilter rowFilter) {
        this.rowFilter = rowFilter;
    }

    protected ColumnSort getColumnSort() {
        if (columnSort == null) {
            columnSort = new MultiColumnSort();
        }

        return columnSort;
    }

    /**
     * Set the ColumnSort. Will override the default implementation.
     *
     * @param columnSort The ColumnSort instance.
     */
    public void setColumnSort(ColumnSort columnSort) {
        this.columnSort = columnSort;
    }

    protected Preferences getPreferences() {
        if (preferences == null) {
            preferences = PreferencesFactory.getPreferences(webContext);
        }

        return preferences;
    }

    /**
     * Set the Preferences. Will override the default implementation.
     *
     * @param preferences The Preferences instance.
     */
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    protected Messages getMessages() {
        if (messages == null) {
            messages = MessagesFactory.getMessages(webContext);
        }

        return messages;
    }

    /**
     * Set the Messages. Will override the default implementation.
     *
     * @param messages The Messages instance.
     */
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    /**
     * Take all the attributes of the factory and create a CoreContext object.
     *
     * @param items The Collection of Beans or Maps.
     * @param limit The Limit instance.
     * @return The newly created CoreContext object.
     */
    public CoreContext createCoreContext(Collection<?> items, Limit limit, Worksheet worksheet) {
        Items itemsImpl;

        if (autoFilterAndSort) {
            itemsImpl = new Items(items, limit, getRowFilter(), getColumnSort());
        } else {
            itemsImpl = new Items(items, limit, new DefaultRowFilter(), new DefaultColumnSort());
        }

        CoreContext coreContext = new CoreContext(itemsImpl, limit, worksheet, getPreferences(), getMessages());

        return coreContext;
    }
}
