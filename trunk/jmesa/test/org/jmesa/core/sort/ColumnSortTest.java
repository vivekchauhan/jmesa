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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jmesa.core.President;
import org.jmesa.core.PresidentDao;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.Order;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersAdapter;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ColumnSortTest extends AbstractTestCase {
	@Test
	public void sortItems() {
		WebContext webContext = createWebContext();
		webContext.setParameterMap(getParameters());
		LimitFactory limitFactory = new LimitFactoryImpl(ID, webContext);
		Limit limit = limitFactory.createLimit();
		
		MultiColumnSort itemsSort = new MultiColumnSort();
		
		PresidentDao dao = new PresidentDao();
		Collection items = dao.getPresidents();
		items = itemsSort.sortItems(items, limit);

		assertNotNull(items);
		
		Iterator iterator = items.iterator();
		
		President asc = (President)iterator.next();
		assertTrue("the asc sort order is wrong", asc.getName().getFirstName().equals("Abraham"));
		
		President desc = (President)iterator.next();
		assertTrue("the desc sort order is wrong", desc.getName().getLastName().equals("Johnson"));
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		ParametersAdapter parametersAdapter = new ParametersAdapter(results);
		createBuilder(parametersAdapter);
		return results;
	}
	
	private void createBuilder(Parameters parameters) {
		ParametersBuilder builder = new ParametersBuilder(ID, parameters);
		builder.addSort("name.firstName", Order.ASC);
		builder.addSort("name.lastName", Order.DESC);
	}
}
