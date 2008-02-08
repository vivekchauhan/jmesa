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

import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.worksheet.Worksheet;

/**
 * Used to create a CoreContext object.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface CoreContextFactory {
    /**
     * Add a FilterMatcher to the FilterMatcherRegistry..
     * 
     * @param key The MatcherKey instance.
     * @param matcher The FilterMatcher instance.
     */
    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher);

    /**
     * Set the RowFilter. Will override the default implementation.
     * 
     * @param rowFilter The RowFilter instance.
     */
    public void setRowFilter(RowFilter rowFilter);

    /**
     * Set the ColumnSort. Will override the default implementation.
     * 
     * @param columnSort The ColumnSort instance.
     */
    public void setColumnSort(ColumnSort columnSort);

    /**
     * Set the Preferences. Will override the default implementation.
     * 
     * @param preferences The Preferences instance.
     */
    public void setPreferences(Preferences preferences);

    /**
     * Set the Messages. Will override the default implementation.
     * 
     * @param messages The Messages instance.
     */
    public void setMessages(Messages messages);

    /**
     * Take all the attributes of the factory and create a CoreContext object.
     * 
     * @param items The Collection of Beans or Maps.
     * @param limit The Limit instance.
     * @return The newly created CoreContext object.
     */
    public CoreContext createCoreContext(Collection<?> items, Limit limit, Worksheet worksheet);
}
