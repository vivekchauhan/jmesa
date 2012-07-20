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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class SortSetTest {
		

    @Test
    public void isSorted() {
		
        SortSet sortSet = new SortSet();
        boolean sorted = sortSet.isSorted();
        assertFalse("default constructor", sorted);

        sortSet = getSortSet();
        sorted = sortSet.isSorted();
        assertTrue("sorted", sorted);
    }

    @Test
    public void getSortOrder() {
		
        SortSet sortSet = getSortSet();
        Order order = sortSet.getSortOrder("nickname");
        assertNotNull(order);
        assertEquals(order.toParam(), "desc");
        assertEquals(order, Order.DESC);
    }

    @Test
    public void getSort() {
		
        SortSet sortSet = getSortSet();
        Sort sort = sortSet.getSort("nickname");
        assertNotNull(sort);
        assertEquals(sort.getOrder(), Order.DESC);
        assertEquals(sort.getProperty(), "nickname");
    }

    private SortSet getSortSet() {
		
        SortSet sortSet = new SortSet();

        sortSet.addSort(new Sort(2, "nickname", Order.DESC));
        sortSet.addSort(new Sort(1, "fullName", Order.ASC));
        sortSet.addSort(new Sort(3, "term", Order.ASC));

        Iterator<Sort> iterator = sortSet.getSorts().iterator();

        int position = (iterator.next()).getPosition();
        assertEquals(position, 1);

        position = (iterator.next()).getPosition();
        assertEquals(position, 2);

        position = (iterator.next()).getPosition();
        assertEquals(position, 3);

        return sortSet;
    }
}
