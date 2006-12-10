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

import java.util.Collection;
import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.Column;
import org.jmesa.view.Row;
import org.jmesa.view.Table;
import org.jmesa.view.View;

public class HtmlView implements View {
	private Table table;
	private CoreContext coreContext;

	public HtmlView(Table table, CoreContext coreContext) {
		this.table = table;
		this.coreContext = coreContext;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Object render() {
		HtmlBuilder html = new HtmlBuilder();

		html.div().styleClass(table.getTheme()).close();
		
		html.append(table.getTableRenderer().render());
		
		html.thead(1).close();
		
		Row row = table.getRow();
		List<Column> columns = table.getRow().getColumns();
		
		html.tr(1).close();
		
		for (Column column : columns) {
			html.append(column.getHeaderRenderer().render());
		}
		
		html.trEnd(1);
		
		html.theadEnd(1);
		
		html.tbody(1).styleClass("tableBody").close();

		int rowcount = 0;

		Collection items = coreContext.getPageItems();
		for (Object item : items) {
			rowcount++;
			
			html.append(row.getRowRenderer().render(item, rowcount));

			for (Column column : columns) {
				html.append(column.getColumnRenderer().render(item, rowcount));
			}

			html.trEnd(1);
		}
		
		html.tbodyEnd(1);

		html.tableEnd(0);
		
		html.newline();
		html.divEnd();

		return html;
	}
}
