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
package org.jmesa.data;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.jmesa.data.match.Match;
import org.jmesa.data.match.MatchKey;
import org.jmesa.data.match.MatchRegistry;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;

/**
 * Use the Jakarta Collections predicate pattern to filter out the table.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public final class FilterPredicate implements Predicate {
	private Logger logger = Logger.getLogger(FilterPredicate.class.getName());
	
	private MatchRegistry registry;
	private FilterSet filterSet;
	private Limit limit;

    public FilterPredicate(MatchRegistry registry, Limit limit, FilterSet filterSet) {
    	this.registry = registry;
    	this.limit = limit;
    	this.filterSet = filterSet;
    }

    /**
     * Use the filter parameters to filter out the table.
     */
    public boolean evaluate(Object bean) {
        boolean result = false;
        
        try {
        	for (Filter filter : filterSet.getFilters()) {
                String property = filter.getProperty();
                Object value = PropertyUtils.getProperty(bean, property);
                
                if(value != null) {
                	MatchKey key = new MatchKey(value.getClass(), limit.getId(), property);
                    Match match = registry.getMatch(key);
                    result = match.evaluate(value, filter.getValue());
                }
            }
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "FilterPredicate.evaluate() had problems", e);
        }

        return result;
    }
}
