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
package org.jmesa.core.filter;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.PresidentDao;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.filter.SimpleRowFilter;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersAdapter;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.web.WebContext;
import org.jmesa.web.HttpServletRequestWebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class RowFilterTest {
	private static final String ID = "pres";
	
	@Test
	public void filterItems() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		MatchKey key = new MatchKey(String.class);
		FilterMatch match = new StringMatch();
		registry.addFilterMatch(key, match);
		
		RowFilter itemsFilter = new SimpleRowFilter(registry);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		LimitFactory limitFactory = new LimitFactoryImpl(ID, webContext);
		Limit limit = limitFactory.createLimit();
		
		PresidentDao dao = new PresidentDao();
		Collection items = dao.getPresidents();
		items = itemsFilter.filterItems(items, limit);

		assertTrue(items.size() == 3);
	}
	
	private Map<?, ?> getParameters() {
		Map<String, Object> results = new HashMap<String, Object>();
		ParametersAdapter parametersAdapter = new ParametersAdapter(results);
		createBuilder(parametersAdapter);
		return results;
	}
	
	private void createBuilder(Parameters parameters) {
		ParametersBuilder builder = new ParametersBuilder(ID, parameters);
		builder.addFilter("fullName", "george");
	}
}
