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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterSetImpl implements Serializable, FilterSet {
    private Logger logger = LoggerFactory.getLogger(FilterSetImpl.class);

    private Set<Filter> filters;

    public FilterSetImpl() {
        filters = new HashSet<Filter>();
    }

    public boolean isFilterable() {
        return isFiltered();
    }

    public boolean isFiltered() {
        return filters != null && !filters.isEmpty();
    }

    public Collection<Filter> getFilters() {
        return filters;
    }

    public Filter getFilter(String property) {
        for (Iterator<Filter> iter = filters.iterator(); iter.hasNext();) {
            Filter filter = iter.next();
            if (filter.getProperty().equals(property)) {
                return filter;
            }
        }

        return null;
    }

    public String getFilterValue(String property) {
        return getFilter(property).getValue();
    }

    public void addFilter(String property, String value) {
        addFilter(new Filter(property, value));
    }

    public void addFilter(Filter filter) {
        if (filters.contains(filter)) {
            filters.remove(filter);
            if (logger.isDebugEnabled()) {
                logger.debug("Removing Filter: " + filter.toString());
            }
        }

        filters.add(filter);
        if (logger.isDebugEnabled()) {
            logger.debug("Added Filter: " + filter.toString());
        }
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);

        for (Iterator<Filter> iter = filters.iterator(); iter.hasNext();) {
            Filter filter = iter.next();
            builder.append(filter.toString());
        }

        return builder.toString();
    }
}
