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

import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarImpl;
import org.jmesa.view.html.toolbar.ToolbarItemType;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ClassicView implements View {
	private Table table;
	private WebContext webContext;
	private CoreContext coreContext;
	private String[] exportTypes;
	private String imagesPath;

	public ClassicView(Table table, WebContext webContext, CoreContext coreContext, String... exportTypes) {
		this.table = table;
		this.webContext = webContext;
		this.coreContext = coreContext;
		this.exportTypes = exportTypes;
		imagesPath = HtmlUtils.imagesPath(webContext, coreContext);
	}

	public HtmlTable getTable() {
		return (HtmlTable)table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	protected WebContext getWebContext() {
		return webContext;
	}

	protected CoreContext getCoreContext() {
		return coreContext;
	}
	
	public Object render() {
		StringBuilder builder = new StringBuilder();
		
		HtmlSnippets snippets = new HtmlSnippetsImpl(getTable(), getCoreContext());

		builder.append(snippets.themeStart());
		
		builder.append(snippets.tableStart());
		
		builder.append(snippets.theadStart());
		
        Toolbar toolbar = new ToolbarImpl(imagesPath, coreContext);
        toolbar.addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        toolbar.addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        toolbar.addExportToolbarItems(exportTypes);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);

		HtmlRow row = getTable().getRow();
		List columns = row.getColumns();
        if (ViewUtils.isFilterable(columns)) {
            toolbar.addToolbarItem(ToolbarItemType.FILTER_ITEM);
            toolbar.addToolbarItem(ToolbarItemType.CLEAR_ITEM);
        }
		
		builder.append(snippets.statusBar(toolbar));
		
		builder.append(snippets.header());
		
		builder.append(snippets.filter());
		
		builder.append(snippets.theadEnd());
		
		builder.append(snippets.tbodyStart());
		
		builder.append(snippets.body());
		
		builder.append(snippets.tbodyEnd());

		builder.append(snippets.tableEnd());
		
		builder.append(snippets.themeEnd());
		
		builder.append(snippets.initJavascriptLimit());

		return builder;
	}
}
