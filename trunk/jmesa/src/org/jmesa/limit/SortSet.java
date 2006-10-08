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

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Jeff Johnston
 */
public class SortSet {
    private Set<Sort> sorts;
    
    public SortSet() {
    }

    public SortSet(Set<Sort> sorts) {
        this.sorts = sorts;
    }

    public boolean isSorted() {
        return sorts != null && !sorts.isEmpty();
    }

    public Set<Sort> getSorts() {
        return sorts;
    }

    /**
     * For a given filter, referenced by the alias, retrieve the value. 
     * 
     * @param alias The Filter alias
     * @return The Filter value
     */
    public Order getSortOrder(String alias) {
    	for (Iterator iter = sorts.iterator(); iter.hasNext();) {
    		Sort sort = (Sort) iter.next();
            if (sort.getAlias().equals(alias)) {
                return sort.getOrder();
            }
		}

        return Order.UNORDERED;
    }
    
    /**
     * For a given filter, referenced by the alias, retrieve the Filter. 
     * 
     * @param alias The Filter alias
     * @return The Filter value
     */
    public Sort getSort(String alias) {
    	for (Iterator iter = sorts.iterator(); iter.hasNext();) {
    		Sort sort = (Sort) iter.next();
            if (sort.getAlias().equals(alias)) {
                return sort;
            }
		}

        return new Sort();
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
