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
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * The SortSet is an Collection of Sort objects. A Sort contains a bean 
 * property and the sort order. Or, in other words it is simply the column 
 * that the user is trying to sort in the correcct order.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class SortSet implements Serializable {
    private Set<Sort> sorts;
    
    public SortSet() {
    	BeanComparator comparator = new BeanComparator("position", new NullComparator());
    	sorts = new TreeSet<Sort>(comparator);
    }
    
    /**
     * @return Is true if there are any columns that need to be sorted.
     */
    public boolean isSorted() {
        return sorts != null && !sorts.isEmpty();
    }

    /**
     * @return The Set of Sort objects.
     */
    public Set<Sort> getSorts() {
        return sorts;
    }

    /**
     * For a given property, retrieve the Sort. 
     * 
     * @param property The Sort property.
     * @return The Sort object.
     */
    public Sort getSort(String property) {
    	for (Iterator iter = sorts.iterator(); iter.hasNext();) {
    		Sort sort = (Sort) iter.next();
            if (sort.getProperty().equals(property)) {
                return sort;
            }
		}

        throw new RuntimeException("There is no Sort with the property [" + property + "]"); //TODO: pick a better exception
    }
    
    /**
     * For a given property, retrieve the Sort Order. 
     * 
     * @param property The Sort property.
     * @return The Sort Order.
     */
    public Order getSortOrder(String property) {
    	return getSort(property).getOrder();
    }
    
    /**
     * @param sort The Sort to add to the Set.  
     */
    public void addSort(Sort sort) {
    	sorts.add(sort);
    }
    
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        
        if (sorts != null) {
        	for (Iterator iter = sorts.iterator(); iter.hasNext();) {
        		Sort sort = (Sort) iter.next();
                builder.append(sort.toString());
            }
        }
        
        return builder.toString();
    }
}
