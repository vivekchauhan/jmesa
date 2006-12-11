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
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.Column;
import org.jmesa.view.Row;
import org.jmesa.view.Table;
import org.jmesa.view.View;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
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
	
	protected Table getTable() {
		return table;
	}

	protected CoreContext getCoreContext() {
		return coreContext;
	}
	
	public Object render() {
		HtmlBuilder html = new HtmlBuilder();
		Row row = table.getRow();
		List<Column> columns = table.getRow().getColumns();

		themeStart(html, table);
		
		tableStart(html, table);
		
		theadStart(html);
		
		statusBar(html, columns.size());
		
		header(html, columns);
		
		theadEnd(html);
		
		tbodyStart(html);
		
		body(html, row, columns);
		
		tbodyEnd(html);

		tableEnd(html);
		
		themeEnd(html);

		return html;
	}
	
	protected void themeStart(HtmlBuilder html, Table table) {
		html.div().styleClass(table.getTheme()).close();
	}
	
	protected void themeEnd(HtmlBuilder html) {
		html.newline();
		html.divEnd();
	}
	
	protected void tableStart(HtmlBuilder html, Table table) {
		html.append(table.getTableRenderer().render(table));
	}
	
	protected void tableEnd(HtmlBuilder html) {
		html.tableEnd(0);
	}

	protected void theadStart(HtmlBuilder html) {
        html.thead(1).close();
    }

	protected void theadEnd(HtmlBuilder html) {
        html.theadEnd(1);
    }

	protected void tbodyStart(HtmlBuilder html) {
        html.tbody(1).styleClass(HtmlConstants.TABLE_BODY_CSS).close();
    }

	protected void tbodyEnd(HtmlBuilder html) {
        html.tbodyEnd(1);
    }
	
	protected void header(HtmlBuilder html, List<Column> columns) {
		html.tr(1).close();
		
		for (Column column : columns) {
			html.append(column.getHeaderRenderer().render(column));
		}
		
		html.trEnd(1);
    }
	
	protected void body(HtmlBuilder html, Row row, List<Column> columns) {
		int rowcount = 0;
		Collection items = coreContext.getPageItems();
		for (Object item : items) {
			rowcount++;
			
			html.append(row.getRowRenderer().render(row, item, rowcount));

			for (Column column : columns) {
				html.append(column.getColumnRenderer().render(column, item, rowcount));
			}

			html.trEnd(1);
		}
	}
	
    protected void toolbar() {
    }

    protected void statusBar(HtmlBuilder html, int columns) {
        Limit limit = coreContext.getLimit();
        RowSelect rowSelect = limit.getRowSelect();

        html.tr(1).style("padding: 0px;").close();
        html.td(2).colspan(String.valueOf(columns)).close();
        
        if (rowSelect.getTotalRows() == 0) {
            html.append(coreContext.getMessage(HtmlConstants.STATUSBAR_NO_RESULTS_FOUND));
        } else {
            Integer total = new Integer(rowSelect.getTotalRows());
            Integer from = new Integer(rowSelect.getRowStart() + 1);
            Integer to = new Integer(rowSelect.getRowEnd());
            Object[] messageArguments = { total, from, to };
            html.append(coreContext.getMessage(HtmlConstants.STATUSBAR_RESULTS_FOUND, messageArguments));
        }
        
        html.tdEnd();
        html.trEnd(1);
    }
}
