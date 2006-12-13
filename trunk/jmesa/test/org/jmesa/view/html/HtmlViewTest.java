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
package org.jmesa.view.html;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.DefaultCoreContextFactory;
import org.jmesa.core.PresidentsDao;
import org.jmesa.limit.DefaultLimitFactory;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.ColumnValue;
import org.jmesa.view.DefaultColumnValue;
import org.jmesa.view.View;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlViewTest {
	private static final String ID = "pres";
	private static final int MAX_ROWS = 15;
	
	@Test
	public void render() {
		CoreContext coreContext = createCoreContext();
		
		// create the table
		HtmlTable table = new DefaultHtmlTable();
		table.setTheme("jmesa");
		HtmlTableRenderer tableRenderer = new DefaultHtmlTableRenderer(coreContext);
		tableRenderer.setStyleClass("table");
		table.setTableRenderer(tableRenderer);
		
		// create the row
		HtmlRow row = new DefaultHtmlRow();
		row.setHighlighter(true);
		HtmlRowRenderer rowRenderer = new DefaultHtmlRowRenderer(coreContext);
		rowRenderer.setHighlightClass("highlight");
		row.setRowRenderer(rowRenderer);
		table.setRow(row);
		
		// create some reusable objects
		ColumnValue columnValue = new DefaultColumnValue();
		HtmlColumnRenderer columnRenderer = new DefaultHtmlColumnRenderer(columnValue, coreContext);
		HtmlHeaderRenderer headerRenderer = new DefaultHtmlHeaderRenderer(coreContext);
		headerRenderer.setStyleClass("header");

		// create the columns
		HtmlColumn firstNameColumn = new DefaultHtmlColumn("firstName");
		firstNameColumn.setColumnRenderer(columnRenderer);
		firstNameColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(firstNameColumn);
		
		HtmlColumn lastNameColumn = new DefaultHtmlColumn("lastName");
		lastNameColumn.setColumnRenderer(columnRenderer);
		lastNameColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(lastNameColumn);

		HtmlColumn termColumn = new DefaultHtmlColumn("term");
		termColumn.setColumnRenderer(columnRenderer);
		termColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(termColumn);

		HtmlColumn careerColumn = new DefaultHtmlColumn("career");
		careerColumn.setColumnRenderer(columnRenderer);
		careerColumn.setHeaderRenderer(headerRenderer);
		row.addColumn(careerColumn);

		// create the view
		View view = new HtmlView(table, coreContext);

		Object html = view.render();
		
		assertNotNull(html);
	}
	
	public CoreContext createCoreContext() {
		Collection data = new PresidentsDao().getPresidents();

		MockHttpServletRequest request = new MockHttpServletRequest();
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		webContext.setLocale(Locale.US);
		
		LimitFactory limitFactory = new DefaultLimitFactory(ID, webContext);
		Limit limit = limitFactory.createLimit();
		RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, data.size());
		limit.setRowSelect(rowSelect);

		CoreContextFactory factory = new DefaultCoreContextFactory(webContext);
		CoreContext coreContext = factory.createCoreContext(data, limit);
		
		return coreContext;
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		return results;
	}
}
