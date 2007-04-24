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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class SimpleRowFilter implements RowFilter {
    private static Log logger = LogFactory.getLog(SimpleRowFilter.class);

    private FilterMatcherRegistry registry;

    public SimpleRowFilter(FilterMatcherRegistry registry) {
        this.registry = registry;
    }

    public Collection<Object> filterItems(Collection<Object> items, Limit limit) {
        FilterSet filterSet = limit.getFilterSet();
        boolean filtered = filterSet.isFilterable();

        if (filtered) {
            Collection<Object> collection = new ArrayList<Object>();
            Map<Filter, FilterMatcher> matches = getMatches(items, limit, filterSet);
            FilterPredicate filterPredicate = new FilterPredicate(matches, filterSet);
            CollectionUtils.select(items, filterPredicate, collection);

            return collection;
        }

        return items;
    }

    private Map<Filter, FilterMatcher> getMatches(Collection items, Limit limit, FilterSet filterSet) {
        Map<Filter, FilterMatcher> matches = new HashMap<Filter, FilterMatcher>();

        if (items == null || !items.iterator().hasNext()) {
            return matches;
        }

        try {
            Object item = items.iterator().next();

            for (Filter filter : filterSet.getFilters()) {
                String property = filter.getProperty();
                Object value = PropertyUtils.getProperty(item, property);

                if (value != null) {
                    MatcherKey key = new MatcherKey(value.getClass(), property);
                    FilterMatcher match = registry.getFilterMatcher(key);
                    matches.put(filter, match);
                }
            }
        } catch (Exception e) {
            logger.error("Had problems getting the Filter / Match values.", e);
        }

        return matches;
    }
}
