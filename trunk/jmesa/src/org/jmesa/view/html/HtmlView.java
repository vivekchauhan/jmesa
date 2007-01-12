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
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlView implements View {
	private Table table;
	private Toolbar toolbar;
	private CoreContext coreContext;

	public HtmlView(Table table, Toolbar toolbar, CoreContext coreContext) {
		this.table = table;
		this.toolbar = toolbar;
		this.coreContext = coreContext;
	}

	public HtmlTable getTable() {
		return (HtmlTable)table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Object render() {
		StringBuilder builder = new StringBuilder();
		
		HtmlSnippets snippets = new HtmlSnippetsImpl(getTable(), coreContext);

		builder.append(snippets.themeStart());
		
		builder.append(snippets.tableStart());
		
		builder.append(snippets.theadStart());

        builder.append(snippets.toolbar(toolbar));
		
		builder.append(snippets.filter());

		builder.append(snippets.header());
		
		builder.append(snippets.theadEnd());
		
		builder.append(snippets.tbodyStart());
		
		builder.append(snippets.body());
		
		builder.append(snippets.tbodyEnd());

		builder.append(snippets.statusBar());

		builder.append(snippets.tableEnd());
		
		builder.append(snippets.themeEnd());
		
		builder.append(snippets.initJavascriptLimit());

		return builder;
	}
}
