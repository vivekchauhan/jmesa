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
package org.jmesa.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jmesa.core.filter.DefaultRowFilter;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.ResourceBundleMessages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PropertiesPreferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.core.sort.DefaultColumnSort;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.Order;
import org.jmesa.limit.RowSelect;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersAdapter;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class CoreContextTest {
	private static final String ID = "pres";
	private static final int MAX_ROWS = 20;
	private static final int TOTAL_ROWS = 60;
	
	@Test
	public void createCoreContext() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		webContext.setLocale(Locale.US);
		LimitFactory limitFactory = new LimitFactoryImpl(ID, webContext);
		Limit limit = limitFactory.createLimit();
		
		RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, TOTAL_ROWS);
		limit.setRowSelect(rowSelect);

		RowFilter rowFilter = new DefaultRowFilter();
		ColumnSort columnSort = new DefaultColumnSort();
		
		List<Object> data = new ArrayList<Object>();
		
		Items items = new ItemsImpl(data, limit, rowFilter, columnSort);
		
		Preferences preferences = new PropertiesPreferences("/org/jmesa/core/test.properties", webContext);
		Messages messages = new ResourceBundleMessages("org.jmesa.core.message.testResourceBundle", webContext);

		CoreContextImpl coreContext = new CoreContextImpl(items, limit, null, preferences, messages);
		
		assertNotNull(coreContext);
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		ParametersAdapter parametersAdapter = new ParametersAdapter(results);
		createBuilder(parametersAdapter);
		return results;
	}
	
	private void createBuilder(Parameters parameters) {
		ParametersBuilder builder = new ParametersBuilder(ID, parameters);
		builder.addSort("firstName", Order.ASC);
		builder.addSort("lastName", Order.DESC);
	}

}
