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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jmesa.limit.Limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultItems implements Items {
	private Logger logger = Logger.getLogger(DefaultItems.class.getName());
	
	private Collection allItems;
	private Collection filteredItems;
	private Collection pageItems;
	private Collection sortedItems;
	
	public DefaultItems(Collection items, Limit limit, RowFilter rowFilter, ColumnSort columnSort) {
		init(items, limit, rowFilter, columnSort);
	}
	
	public Collection getAllItems() {
		return allItems;
	}

	public Collection getFilteredItems() {
		return filteredItems;
	}

	public Collection getPageItems() {
		return pageItems;
	}

	public Collection getSortedItems() {
		return sortedItems;
	}
	
	protected void init(Collection items, Limit limit, RowFilter rowFilter, ColumnSort columnSort) {
		this.allItems = new ArrayList(items); // copy for thread safety
		
		this.filteredItems = rowFilter.filterRows(allItems, limit);
        
		this.sortedItems = columnSort.sortColumns(filteredItems, limit);
        
        this.pageItems = getCurrentRows(sortedItems, limit);

		if (logger.isLoggable(Level.FINE)) {
            logger.fine(limit.toString());
        }
	}
	
	protected Collection getCurrentRows(Collection items, Limit limit) {
        int rowStart = limit.getRowSelect().getRowStart();
        int rowEnd = limit.getRowSelect().getRowEnd();

        // Normal case. Using Limit and paginating for a specific set of rows.
        if (rowStart >= items.size()) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("The Limit row start is >= items.size(). Return the items available.");
            }

            return items;
        }

        if (rowEnd > items.size()) {
        	if (logger.isLoggable(Level.FINE)) {
                logger.fine("The Limit row end is > items.size(). Return as many items as possible.");
            }

            rowEnd = items.size();
        }

        Collection results = new ArrayList();
        for (int i = rowStart; i < rowEnd; i++) {
            Object bean = ((List) items).get(i);
            results.add(bean);
        }

        return results;
    }	
}
