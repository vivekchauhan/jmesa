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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.jmesa.test.ParametersAdapter;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.web.WebContext;
import org.jmesa.web.HttpServletRequestWebContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jeff Johnston
 */
public class LimitActionFactoryTest {
    private static final String ID = "pres";
    private static final int MAX_ROWS = 20;
    private static final int PAGE = 3;

    private LimitActionFactoryImpl limitActionFactory;

    @Before
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebContext webContext = new HttpServletRequestWebContext(request);
        webContext.setParameterMap(getParameters());
        limitActionFactory = new LimitActionFactoryImpl(ID, webContext.getParameterMap());
    }

    @Test
    public void getMaxRows() {
        int maxRows = limitActionFactory.getMaxRows();
        assertTrue(maxRows == MAX_ROWS);
    }

    @Test
    public void getPage() {
        int page = limitActionFactory.getPage();
        assertTrue(page == PAGE);
    }

    @Test
    public void getFilterSet() {
        FilterSet filterSet = limitActionFactory.getFilterSet();
        assertNotNull(filterSet);
        assertTrue(filterSet.getFilters().size() == 2);
    }

    @Test
    public void getSortSet() {
        SortSet sortSet = limitActionFactory.getSortSet();
        assertNotNull(sortSet);
        assertTrue(sortSet.getSorts().size() == 2);
    }

    private Map<String, ?> getParameters() {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ParametersBuilder builder = new ParametersBuilder(ID, new ParametersAdapter(results));

        builder.setMaxRows(MAX_ROWS);
        builder.setPage(PAGE);
        builder.addFilter("name", "George Washington");
        builder.addFilter("nickName", "Father of His Country");
        builder.addSort("name", Order.ASC);
        builder.addSort("nickName", Order.DESC);

        return results;
    }
}
