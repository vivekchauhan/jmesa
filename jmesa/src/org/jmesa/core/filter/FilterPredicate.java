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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;

/**
 * Use the Jakarta Collections predicate pattern to filter out the table.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public final class FilterPredicate implements Predicate {
	private Logger logger = Logger.getLogger(FilterPredicate.class.getName());

	private Map<Filter, FilterMatch> matches;
	private FilterSet filterSet;

	public FilterPredicate(Map<Filter, FilterMatch> matches, FilterSet filterSet) {
		this.matches = matches;
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
				Object value = PropertyUtils.getProperty(item, property);

				if (value != null) {
					FilterMatch match = matches.get(filter);
					result = match.evaluate(value, filter.getValue());
				}
				
				// short circuit if does not match
				if (result == false) {
					return false;
				}
				
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Had problems evaluating the items.", e);
		}

		return result;
	}
}
