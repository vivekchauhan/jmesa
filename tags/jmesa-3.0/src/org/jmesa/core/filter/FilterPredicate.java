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
package org.jmesa.core.filter;

import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.util.ItemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use the Jakarta Collections predicate pattern to filter out the table.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public final class FilterPredicate implements Predicate {
    private Logger logger = LoggerFactory.getLogger(FilterPredicate.class);

    private Map<Filter, FilterMatcher> filterMatchers;
    private FilterSet filterSet;

    public FilterPredicate(Map<Filter, FilterMatcher> filterMatchers, FilterSet filterSet) {
        this.filterMatchers = filterMatchers;
        this.filterSet = filterSet;
    }

    /**
     * Use the filter parameters to filter out the table.
     */
    public boolean evaluate(Object item) {
        boolean result = false;

        try {
            for (Filter filter : filterSet.getFilters()) {
                String property = filter.getProperty();
                Object value = ItemUtils.getItemValue(item, property);

                if (value != null) {
                    FilterMatcher filterMatcher = filterMatchers.get(filter);
                    result = filterMatcher.evaluate(value, filter.getValue());
                } else {
                    result = false;
                }

                // short circuit if does not match
                if (result == false) {
                    return false;
                }

            }
        } catch (Exception e) {
            logger.error("Had problems evaluating the items.", e);
        }

        return result;
    }
}
