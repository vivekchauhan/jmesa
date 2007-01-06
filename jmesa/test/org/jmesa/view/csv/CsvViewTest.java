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
package org.jmesa.view.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.core.PresidentsDao;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersAdapter;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvViewTest {
	private static final String ID = "pres";

	@Test
	public void render() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		webContext.setLocale(Locale.US);
		
		CoreContext coreContext = createCoreContext(webContext);
		
		CsvComponentFactory factory = new CsvComponentFactory(webContext, coreContext);
		
		// create the table
		Table table = factory.createTable();
		
		// create the row
		Row row = factory.createRow();
		table.setRow(row);
		
		// create some reusable objects

		CellEditor editor = factory.createBasicCellEditor();
		
		// create the columns
		Column firstNameColumn = factory.createColumn("firstName", editor);
		row.addColumn(firstNameColumn);
		
		Column lastNameColumn = factory.createColumn("lastName", editor);
		row.addColumn(lastNameColumn);

		Column termColumn = factory.createColumn("term", editor);
		row.addColumn(termColumn);

		Column careerColumn = factory.createColumn("career", editor);
		row.addColumn(careerColumn);

		// create the view
		CsvView view = new CsvView(table, coreContext);

		Object csv = view.render();
		
		assertNotNull(csv);
	}
	
	public CoreContext createCoreContext(WebContext webContext) {
		Collection items = new PresidentsDao().getPresidents();

		LimitFactory limitFactory = new LimitFactoryImpl(ID, webContext);
		Limit limit = limitFactory.createLimit();
		RowSelect rowSelect = limitFactory.createRowSelect(items.size(), items.size());
		limit.setRowSelect(rowSelect);

		CoreContextFactory factory = new CoreContextFactoryImpl(webContext, false);
		CoreContext coreContext = factory.createCoreContext(items, limit);
		
		assertTrue(limit.isExported());
		assertTrue(limit.getExport().getType().equals("csv"));
		
		return coreContext;
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		ParametersAdapter parametersAdapter = new ParametersAdapter(results);
		createBuilder(parametersAdapter);
		return results;
	}
	
	private void createBuilder(Parameters parameters) {
		ParametersBuilder builder = new ParametersBuilder(ID, parameters);
		builder.setExport("csv");
	}
}
