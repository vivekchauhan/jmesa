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

import org.jmesa.test.MapParameters;
import org.jmesa.test.ParametersBuilder;
import org.junit.Test;

/**
 * @author Jeff Johnston
 */
public class LimitFactoryTest {
	private static final String ID = "pres";
	private static final int MAX_ROWS = 20;
	private static final int TOTAL_ROWS = 60;
	private static final int PAGE = 3;

	@Test
	public void createLimitAndRowSelect() {
		LimitFactory limitFactory = new LimitFactoryImpl(ID, getParameters());
		Limit limit = limitFactory.createLimit();
		
		assertNotNull(limit);
		assertTrue(limit.getFilterSet().getFilters().size() > 0);
		assertTrue(limit.getSortSet().getSorts().size() > 0);
		
		RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, TOTAL_ROWS);
		limit.setRowSelect(rowSelect);

		assertNotNull(rowSelect);
		assertTrue(rowSelect.getPage() > 0);
		assertTrue(rowSelect.getRowStart() > 0);
		assertTrue(rowSelect.getRowEnd() > 0);
		assertTrue(rowSelect.getMaxRows() > 0);
		assertTrue(rowSelect.getTotalRows() > 0);
	}
	
	private Map<String, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		ParametersBuilder builder = new ParametersBuilder(ID, new MapParameters(results));
		
		builder.setMaxRows(MAX_ROWS);
		builder.setPage(PAGE);
		builder.addFilter("name", "George Washington");
		builder.addFilter("nickName", "Father of His Country");
		builder.addSort("name", Order.ASC);
		builder.addSort("nickName", Order.DESC);

		return results;
	}	
	
}
