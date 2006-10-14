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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jeff Johnston
 */
public class LimitFactoryTest {
	private static final String ID = "pres";
	private static final String PREFIX_ID = "pres_";
	private static final int MAXROWS = 20;
	private static final int PAGE = 3;

	private LimitFactoryImpl factory;
	
	@Before
	public void setUp() {
		Map<String, ?> parameters = getParameters();
		factory = new LimitFactoryImpl(ID, parameters);
	}

	@Test
	public void getMaxRows() {
		int maxRows = factory.getMaxRows();
		assertTrue(maxRows == MAXROWS);
	}

	@Test
	public void getPage() {
		int page = factory.getPage();
		assertTrue(page == PAGE);
	}

	@Test
	public void getFilterSet() {
		FilterSet filterSet = factory.getFilterSet();
		assertNotNull(filterSet);
		assertTrue(filterSet.getFilters().size() == 2);
	}

	@Test
	public void getSortSet() {
		SortSet sortSet = factory.getSortSet();
		assertNotNull(sortSet);
		assertTrue(sortSet.getSorts().size() == 2);
	}

	private Map<String, ?> getParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();

		String maxRows = PREFIX_ID + Action.MAX_ROWS;
		parameters.put(maxRows, MAXROWS);

		String page = PREFIX_ID + Action.PAGE;
		parameters.put(page, new Integer[] { PAGE });

		String filter = PREFIX_ID + Action.FILTER + "name";
		List<String> filterList = new ArrayList<String>();
		filterList.add(filter);
		parameters.put(filter, filterList);

		String filter2 = PREFIX_ID + Action.FILTER + "nickName";
		List<String> filterList2 = new ArrayList<String>();
		filterList.add(filter);
		parameters.put(filter2, filterList2);

		String sort = PREFIX_ID + Action.SORT + "name";
		parameters.put(sort, new String[]{Order.ASC.getCode()});

		String sort2 = PREFIX_ID + Action.SORT + "nickName";
		parameters.put(sort2, new String[]{Order.DESC.getCode()});

		return parameters;
	}
}
