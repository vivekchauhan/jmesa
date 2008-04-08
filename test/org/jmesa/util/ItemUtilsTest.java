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
package org.jmesa.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jmesa.core.President;
import org.jmesa.core.PresidentDao;
import org.jmesa.test.AbstractTestCase;
import org.junit.Test;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class ItemUtilsTest extends AbstractTestCase {

    @Test
    public void getItemValueBean() {
        Collection<President> items = new PresidentDao().getPresidents();
        President president = items.iterator().next();
        Object value = ItemUtils.getItemValue(president, "term");
        assertNotNull("Cannot retrieve a bean value.", value);
    }

    @Test
    public void getItemValueMap() {
        Map president = getMap();
        Object value = ItemUtils.getItemValue(president, "term");
        assertNotNull("Cannot retrieve a map value.", value);
    }

    @Test
    public void getItemValueBeanMap() {
        Collection<President> items = new PresidentDao().getPresidents();
        President bean = items.iterator().next();        
        
        Map president = getMap();
        president.put(ItemUtils.JMESA_ITEM, bean);
        Object value = ItemUtils.getItemValue(president, "career");
        
        assertNotNull("Cannot retrieve a map bean value.", value);
    }

    @Test
    public void getItemValueBeanMapWithNullValue() {
        Map president = getMap();
        Object value = ItemUtils.getItemValue(president, "career");
        
        assertNull("could retrieve a map bean value.", value);
    }
    
    private Map getMap() {
        Map result = new HashMap();
        result.put("id", 1);
        result.put("term", "1789-1797");
        return result;
    }
}
