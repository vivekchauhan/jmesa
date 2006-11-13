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
package org.jmesa.data;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jmesa.data.match.DefaultMatchRegistry;
import org.jmesa.data.match.Match;
import org.jmesa.data.match.MatchKey;
import org.jmesa.data.match.MatchRegistry;
import org.jmesa.data.match.StringMatch;
import org.jmesa.limit.DefaultLimitFactory;
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
		MatchRegistry registry = new DefaultMatchRegistry();
		MatchKey key = new MatchKey(String.class);
		Match match = new StringMatch();
		registry.addMatch(key, match);
		
		SimpleRowFilter itemsFilter = new SimpleRowFilter(registry);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebContext context = new HttpServletRequestWebContext(request, getParameters());
		LimitFactory limitFactory = new DefaultLimitFactory(ID, context);
		Limit limit = limitFactory.createLimit();
		
		PresidentsDao dao = new PresidentsDao();
		Collection items = dao.getPresidents();
		items = itemsFilter.filterItems(limit, items);

		assertTrue(items.size() == 3);
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		ParametersAdapter parametersAdapter = new ParametersAdapter(results);
		createBuilder(parametersAdapter);
		return results;
	}
	
	private void createBuilder(Parameters parameters) {
		ParametersBuilder builder = new ParametersBuilder(ID, parameters);
		builder.addFilter("fullName", "george");
	}
}
