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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class SortSetImpl implements Serializable, SortSet {
    private Logger logger = LoggerFactory.getLogger(SortSetImpl.class);
    private List<Sort> sorts;

    public SortSetImpl() {
        sorts = new ArrayList<Sort>();
    }

    public boolean isSortable() {
        return isSorted();
    }
    
    public boolean isSorted() {
        return sorts != null && !sorts.isEmpty();
    }

    public Collection<Sort> getSorts() {
        return sorts;
    }

    public Sort getSort(String property) {
        for (Iterator<Sort> iter = sorts.iterator(); iter.hasNext();) {
            Sort sort = iter.next();
            if (sort.getProperty().equals(property)) {
                return sort;
            }
        }

        return null;
    }

    public Order getSortOrder(String property) {
        return getSort(property).getOrder();
    }

    public void addSort(int position, String property, Order order) {
        addSort(new Sort(position, property, order));
    }

    public void addSort(String property, Order order) {
        addSort(new Sort(sorts.size(), property, order));
    }

    public void addSort(Sort sort) {
        if (sorts.contains(sort)) {
            sorts.remove(sort);
            if (logger.isDebugEnabled()) {
                logger.debug("Removing Sort: " + sort.toString());
            }
        }

        sorts.add(sort);
        Collections.sort(sorts);
        if (logger.isDebugEnabled()) {
            logger.debug("Added Sort: " + sort.toString());
        }
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);

        if (sorts != null) {
            for (Iterator<Sort> iter = sorts.iterator(); iter.hasNext();) {
                Sort sort = iter.next();
                builder.append(sort.toString());
            }
        }

        return builder.toString();
    }
}
