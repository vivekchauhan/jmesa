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

/**
 * <p>
 * The FilterSet is an Collection of Filter objects. A Filter contains a bean property and the
 * filter value. Or, in other words, it is simply the column that the user is trying to filter and
 * the value that they entered.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface FilterSet {
    /**
     * @return Is true if there are any columns that need to be filtered.
     */
    public boolean isFiltered();

    /**
     * @return The set of Filter objects.
     */
    public Collection<Filter> getFilters();

    /**
     * <p>
     * For a given item property, retrieve the Filter object based on the property.
     * </p>
     * 
     * @param property The Filter property, which is also a column property.
     * @return The Filter object.
     */
    public Filter getFilter(String property);

    /**
     * <p>
     * For a given property, retrieve the Filter value.
     * </p>
     * 
     * @param property The Filter property, which is also a column property.
     * @return The Filter value.
     */
    public String getFilterValue(String property);

    /**
     * <p>
     * The Filter to add to the set.
     * </p>
     * 
     * @param property The column property to filter.
     * @param value The value to filter the column.
     */
    public void addFilter(String property, String value);

    /**
     * @param filter The Filter to add to the set.
     */
    public void addFilter(Filter filter);
}
