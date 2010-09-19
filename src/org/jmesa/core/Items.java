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
import java.util.List;

import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class Items {
    private Logger logger = LoggerFactory.getLogger(Items.class);

    private Collection<?> allItems;
    private Collection<?> filteredItems;
    private Collection<?> pageItems;
    private Collection<?> sortedItems;

    public Items(Collection<?> items, Limit limit, RowFilter rowFilter, ColumnSort columnSort) {
        this.allItems = new ArrayList<Object>(items); // copy for thread safety

        this.filteredItems = rowFilter.filterItems(allItems, limit);

        if (filteredItems.size() != allItems.size()) {
            recalculateRowSelect(filteredItems, limit);
        }

        this.sortedItems = columnSort.sortItems(filteredItems, limit);

        this.pageItems = getPageItems(sortedItems, limit);

        if (logger.isDebugEnabled()) {
            logger.debug(limit.toString());
        }
    }

    public Collection<?> getAllItems() {
        return allItems;
    }

    public Collection<?> getFilteredItems() {
        return filteredItems;
    }

    public Collection<?> getPageItems() {
        return pageItems;
    }

    public void setPageItems(Collection<?> pageItems) {
        this.pageItems = pageItems;
    }

    public Collection<?> getSortedItems() {
        return sortedItems;
    }

    /**
     * Need to recalculate the RowSelect object if the items needed to be
     * filtered.
     *
     * @param filteredItems
     * @param limit
     */
    private void recalculateRowSelect(Collection<?> filteredItems, Limit limit) {
        RowSelect rowSelect = limit.getRowSelect();
        int page = rowSelect.getPage();
        int maxRows = rowSelect.getMaxRows();
        RowSelect recalcRowSelect = new RowSelect(page, maxRows, filteredItems.size());
        limit.setRowSelect(recalcRowSelect);
    }

    private Collection<?> getPageItems(Collection<?> items, Limit limit) {
        int rowStart = limit.getRowSelect().getRowStart();
        int rowEnd = limit.getRowSelect().getRowEnd();

        // Normal case. Using Limit and paginating for a specific set of rows.
        if (rowStart >= items.size()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The Limit row start is >= items.size(). Return the items available.");
            }

            return items;
        }

        if (rowEnd > items.size()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The Limit row end is > items.size(). Return as many items as possible.");
            }

            rowEnd = items.size();
        }

        Collection<Object> results = new ArrayList<Object>();
        for (int i = rowStart; i < rowEnd; i++) {
            Object item = ((List<?>) items).get(i);
            results.add(item);
        }

        return results;
    }
}
