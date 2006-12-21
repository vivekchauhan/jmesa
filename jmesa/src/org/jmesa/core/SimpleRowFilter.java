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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jmesa.core.match.FilterMatch;
import org.jmesa.core.match.FilterMatchKey;
import org.jmesa.core.match.FilterMatchRegistry;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class SimpleRowFilter implements RowFilter {
	private Logger logger = Logger.getLogger(SimpleRowFilter.class.getName());
	
	private FilterMatchRegistry registry;
	
	public SimpleRowFilter(FilterMatchRegistry registry) {
		this.registry = registry;
	}
	
	public Collection filterItems(Collection items, Limit limit) {
        FilterSet filterSet = limit.getFilterSet();
		boolean filtered = filterSet.isFiltered();

        if (filtered) {
            Collection collection = new ArrayList();
            Map<Filter, FilterMatch> matches = getMatches(limit, filterSet, items);
            FilterPredicate filterPredicate = new FilterPredicate(matches, filterSet);
            CollectionUtils.select(items, filterPredicate, collection);

            return collection;
        }

        return items;
	}
	
	private Map<Filter, FilterMatch> getMatches(Limit limit, FilterSet filterSet, Collection items) {
		Map<Filter, FilterMatch> matches = new HashMap<Filter, FilterMatch>();
		
		if (items == null || !items.iterator().hasNext()) {
			return matches;
		}
		
        try {
    		Object item = items.iterator().next();

    		for (Filter filter : filterSet.getFilters()) {
                String property = filter.getProperty();
                Object value = PropertyUtils.getProperty(item, property);
                
                if(value != null) {
                	FilterMatchKey key = new FilterMatchKey(value.getClass(), limit.getId(), property);
                    FilterMatch match = registry.getMatch(key);
                    matches.put(filter, match);
                }
            }
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Had problems getting the Filter / Match values.", e);
        }
    	
    	return matches;
	}
}
