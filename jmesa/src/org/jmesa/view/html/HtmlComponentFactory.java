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

import org.jmesa.core.CoreContext;
import org.jmesa.view.editor.ColumnEditor;
import org.jmesa.view.editor.DefaultColumnEditor;
import org.jmesa.view.html.component.DefaultHtmlColumn;
import org.jmesa.view.html.component.DefaultHtmlRow;
import org.jmesa.view.html.component.DefaultHtmlTable;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.renderer.DefaultHtmlColumnRenderer;
import org.jmesa.view.html.renderer.DefaultHtmlFilterRenderer;
import org.jmesa.view.html.renderer.DefaultHtmlHeaderRenderer;
import org.jmesa.view.html.renderer.DefaultHtmlRowRenderer;
import org.jmesa.view.html.renderer.DefaultHtmlTableRenderer;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlComponentFactory {
	private WebContext webContext;
	private CoreContext coreContext;
	
	public HtmlComponentFactory(WebContext webContext, CoreContext coreContext) {
		this.webContext = webContext;
		this.coreContext = coreContext;
	}
	
	public HtmlTable createHtmlTable() {
		DefaultHtmlTable table = new DefaultHtmlTable();
		table.setWebContext(webContext);
		table.setCoreContext(coreContext);
		
		DefaultHtmlTableRenderer tableRenderer = new DefaultHtmlTableRenderer(table);
		tableRenderer.setWebContext(webContext);
		tableRenderer.setCoreContext(coreContext);
		table.setTableRenderer(tableRenderer);
		
		return table;
	}

	public HtmlRow createHtmlRow() {
		DefaultHtmlRow row = new DefaultHtmlRow();
		row.setWebContext(webContext);
		row.setCoreContext(coreContext);
		
		DefaultHtmlRowRenderer rowRenderer = new DefaultHtmlRowRenderer(row);
		rowRenderer.setWebContext(webContext);
		rowRenderer.setCoreContext(coreContext);
		row.setRowRenderer(rowRenderer);
		
		return row;
	}

	public HtmlColumn createHtmlColumn(String property, ColumnEditor editor) {
		DefaultHtmlColumn column = new DefaultHtmlColumn(property);
		column.setWebContext(webContext);
		column.setCoreContext(coreContext);
		
		DefaultHtmlColumnRenderer columnRenderer = new DefaultHtmlColumnRenderer(column, editor);
		columnRenderer.setWebContext(webContext);
		columnRenderer.setCoreContext(coreContext);
		column.setColumnRenderer(columnRenderer);
		
		DefaultHtmlHeaderRenderer headerRenderer = new DefaultHtmlHeaderRenderer(column);
		headerRenderer.setWebContext(webContext);
		headerRenderer.setCoreContext(coreContext);
		column.setHeaderRenderer(headerRenderer);

		DefaultHtmlFilterRenderer filterRenderer = new DefaultHtmlFilterRenderer(column);
		filterRenderer.setWebContext(webContext);
		filterRenderer.setCoreContext(coreContext);
		column.setFilterRenderer(filterRenderer);

		return column;
	}

	public ColumnEditor createDefaultColumnEditor() {
		DefaultColumnEditor editor = new DefaultColumnEditor();
		editor.setWebContext(webContext);
		editor.setCoreContext(coreContext);
		return editor;
	}
}
