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
package org.jmesa.core.sort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Order;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class MultiColumnSort implements ColumnSort {
		
    @SuppressWarnings("unchecked")
    @Override
    public Collection<?> sortItems(Collection<?> items, Limit limit) {
		
        ComparatorChain chain = new ComparatorChain();

        SortSet sortSet = limit.getSortSet();
        for (Sort sort : sortSet.getSorts()) {
            if (sort.getOrder() == Order.ASC) {
                chain.addComparator(new BeanComparator(sort.getProperty(), new NullComparator()));
            } else if (sort.getOrder() == Order.DESC) {
                chain.addComparator(new BeanComparator(sort.getProperty(), new NullComparator()), true);
            }
        }

        if (chain.size() > 0) {
            Collections.sort((List<?>) items, chain);
        }

        return items;
    }
}
