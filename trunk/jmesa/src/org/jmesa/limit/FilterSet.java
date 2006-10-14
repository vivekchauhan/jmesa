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
package org.jmesa.limit;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Jeff Johnston
 */
public class FilterSet {
	private Set<Filter> filters = new HashSet<Filter>();

	public FilterSet() {
		filters = new HashSet<Filter>();
	}

	public FilterSet(Set<Filter> filters) {
		this.filters = filters;
	}

	public boolean isFiltered() {
		return filters != null && !filters.isEmpty();
	}

	public Set<Filter> getFilters() {
		return filters;
	}

	/**
	 * For a given filter, referenced by the alias, retrieve the value.
	 * 
	 * @param alias
	 *            The Filter alias
	 * @return The Filter value
	 */
	public String getFilterValue(String property) {
		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			Filter filter = (Filter) iter.next();
			if (filter.getProperty().equals(property)) {
				return filter.getValue();
			}
		}

		return "";
	}

	/**
	 * For a given filter, referenced by the alias, retrieve the Filter.
	 * 
	 * @param alias
	 *            The Filter alias
	 * @return The Filter value
	 */
	public Filter getFilter(String property) {
		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			Filter filter = (Filter) iter.next();
			if (filter.getProperty().equals(property)) {
				return filter;
			}
		}

		return null;
	}
	
	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			Filter filter = (Filter) iter.next();
			builder.append(filter.toString());
		}

		return builder.toString();
	}
}
