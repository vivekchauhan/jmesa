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

import java.util.Collection;

public interface FilterSet {

	/**
	 * @return Is true if there are any columns that need to be filtered.
	 */
	public boolean isFiltered();

	public Collection<Filter> getFilters();

	/**
	 * For a given property, retrieve the Filter.
	 * 
	 * @param property The Filter property.
	 * @return The Filter value.
	 */
	public Filter getFilter(String property);

	/**
	 * For a given property, retrieve the Filter value.
	 * 
	 * @param property The Filter property.
	 * @return The Filter value.
	 */
	public String getFilterValue(String property);

	/**
	 * 
	 * @param property The column property to filter.
	 * @param value The value to filter the column.
	 */
	public void addFilter(String property, String value);

	/**
	 * @param filter The Filter to add to the Set.  
	 */
	public void addFilter(Filter filter);

}