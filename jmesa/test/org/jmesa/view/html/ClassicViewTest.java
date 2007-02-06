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
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.core.PresidentDao;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.View;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactory;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ClassicViewTest {
	private static final String ID = "pres";
	private static final int MAX_ROWS = 15;
	
	@Test
	public void render() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		webContext.setLocale(Locale.US);
		
		CoreContext coreContext = createCoreContext(webContext);
		
		HtmlComponentFactory factory = new HtmlComponentFactory(webContext, coreContext);
		
		// create the table
		HtmlTable table = factory.createTable();
		table.setTheme("jmesa");
		table.setCaption("Presidents");
		table.getTableRenderer().setWidth("500px");
		table.getTableRenderer().setStyleClass("table");
		
		// create the row
		HtmlRow row = factory.createRow();
		row.setHighlighter(true);
		row.getRowRenderer().setHighlightClass("highlight");
		table.setRow(row);
		
		// create some reusable objects

		CellEditor editor = factory.createBasicCellEditor();
		
		// create the columns
		HtmlColumn firstNameColumn = factory.createColumn("firstName", editor);
		firstNameColumn.getHeaderRenderer().setStyleClass("header"); //TODO: this should default
		row.addColumn(firstNameColumn);
		
		HtmlColumn lastNameColumn = factory.createColumn("lastName", editor);
		lastNameColumn.getHeaderRenderer().setStyleClass("header");
		row.addColumn(lastNameColumn);

		HtmlColumn termColumn = factory.createColumn("term", editor);
		termColumn.getHeaderRenderer().setStyleClass("header");
		row.addColumn(termColumn);

		HtmlColumn careerColumn = factory.createColumn("career", editor);
		careerColumn.getHeaderRenderer().setStyleClass("header");
		row.addColumn(careerColumn);

		// create the view
		ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, "csv");
		Toolbar toolbar = toolbarFactory.createToolbar();
		View view = new HtmlView(table, toolbar, coreContext);
		Object html = view.render();
		
		assertNotNull(html);
	}
	
	public CoreContext createCoreContext(WebContext webContext) {
		Collection items = new PresidentDao().getPresidents();

		LimitFactory limitFactory = new LimitFactoryImpl(ID, webContext);
		Limit limit = limitFactory.createLimit();
		RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, items.size());
		limit.setRowSelect(rowSelect);

		CoreContextFactory factory = new CoreContextFactoryImpl(webContext, false);
		CoreContext coreContext = factory.createCoreContext(items, limit);
		
		return coreContext;
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		return results;
	}
}
