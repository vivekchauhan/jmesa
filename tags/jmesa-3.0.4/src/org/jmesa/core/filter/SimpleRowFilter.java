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

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import static org.jmesa.util.ItemUtils.getPropertyClassType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class SimpleRowFilter implements RowFilter, FilterMatcherRegistrySupport {
    private Logger logger = LoggerFactory.getLogger(SimpleRowFilter.class);

    private FilterMatcherRegistry registry;

    public Collection<?> filterItems(Collection<?> items, Limit limit) {
        FilterSet filterSet = limit.getFilterSet();
        boolean filtered = filterSet.isFiltered();

        if (filtered) {
            Collection<?> collection = new ArrayList<Object>();
            Map<Filter, FilterMatcher> filterMatchers = getFilterMatchers(items, filterSet);
            Predicate filterPredicate = getPredicate(filterMatchers, filterSet);
            CollectionUtils.select(items, filterPredicate, collection);

            return collection;
        }

        return items;
    }

    protected Map<Filter, FilterMatcher> getFilterMatchers(Collection<?> items, FilterSet filterSet) {
        Map<Filter, FilterMatcher> filterMatchers = new HashMap<Filter, FilterMatcher>();

        if (items == null || !items.iterator().hasNext()) {
            return filterMatchers;
        }

        try {
            for (Filter filter : filterSet.getFilters()) {
                String property = filter.getProperty();
                Class<?> type = getPropertyClassType(items, property);
                MatcherKey key = new MatcherKey(type, property);
                FilterMatcher filterMatcher = registry.getFilterMatcher(key);
                filterMatchers.put(filter, filterMatcher);
            }
        } catch (Exception e) {
            logger.error("Had problems getting the Filter / FilterMatcher values.", e);
        }

        return filterMatchers;
    }

    protected Predicate getPredicate(Map<Filter, FilterMatcher> filterMatchers, FilterSet filterSet) {
        return new FilterPredicate(filterMatchers, filterSet);
    }

    public FilterMatcherRegistry getFilterMatcherRegistry() {
        return registry;
    }

    public void setFilterMatcherRegistry(FilterMatcherRegistry registry) {
        this.registry = registry;
    }
}
