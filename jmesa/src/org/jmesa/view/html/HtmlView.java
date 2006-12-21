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
import org.jmesa.view.View;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.ToolbarBuilder;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlView implements View {
	private Table table;
	private CoreContext coreContext;
	private String imagePath;

	public HtmlView(HtmlTable table, CoreContext coreContext) {
		this.table = table;
		this.coreContext = coreContext;
	}

	public HtmlTable getTable() {
		return (HtmlTable)table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	protected CoreContext getCoreContext() {
		return coreContext;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	protected String getImagePath() {
		return imagePath;
	}
	
	public Object render() {
		HtmlBuilder html = new HtmlBuilder();
		HtmlRow row = (HtmlRow)table.getRow();
		List columns = table.getRow().getColumns();

		themeStart(html, getTable());
		
		toolbar(html, getTable());
		
		tableStart(html, getTable());
		
		theadStart(html);
		
		statusBar(html, columns);
		
		header(html, columns);
		
		theadEnd(html);
		
		tbodyStart(html);
		
		body(html, row, columns);
		
		tbodyEnd(html);

		tableEnd(html);
		
		themeEnd(html);

		return html;
	}
	
	protected void themeStart(HtmlBuilder html, HtmlTable table) {
		html.div().styleClass(table.getTheme()).close();
	}
	
	protected void themeEnd(HtmlBuilder html) {
		html.newline();
		html.divEnd();
	}
	
	protected void tableStart(HtmlBuilder html, HtmlTable table) {
		html.append(table.getTableRenderer().render());
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
	
	protected void header(HtmlBuilder html, List<HtmlColumn> columns) {
		html.tr(1).close();
		
		for (HtmlColumn column : columns) {
			html.append(column.getHeaderRenderer().render());
		}
		
		html.trEnd(1);
    }
	
	protected void body(HtmlBuilder html, HtmlRow row, List<HtmlColumn> columns) {
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
	}
	
    protected void toolbar(HtmlBuilder html, HtmlTable table) {
        html.table(0).border("0").cellpadding("0").cellspacing("0");
        
        html.width(table.getTableRenderer().getWidth()).close();

        html.tr(1).close();

        html.td(2).close();
        
        // start of title
        
        html.span().styleClass("title").close().append(table.getTitle()).spanEnd();
        
        html.tdEnd();

        // end of title

        html.td(2).align("right").close();

        // start of toolbar

        html.table(2).border("0").cellpadding("0").cellspacing("1").styleClass(HtmlConstants.TOOLBAR_CSS).close();

        html.tr(3).close();
        
        ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, coreContext, imagePath);

        html.td(4).close();
        toolbarBuilder.firstPageItemAsImage();
        html.tdEnd();

        html.td(4).close();
        toolbarBuilder.prevPageItemAsImage();
        html.tdEnd();

        html.td(4).close();
        toolbarBuilder.nextPageItemAsImage();
        html.tdEnd();

        html.td(4).close();
        toolbarBuilder.lastPageItemAsImage();
        html.tdEnd();

        html.trEnd(3);

        html.tableEnd(2);
        html.newline();
        html.tabs(2);

        html.tdEnd();

        // end of toolbar

        html.trEnd(1);

        html.tableEnd(0);
        html.newline();
    }

    protected void statusBar(HtmlBuilder html, List<HtmlColumn> columns) {
        Limit limit = coreContext.getLimit();
        RowSelect rowSelect = limit.getRowSelect();

        html.tr(1).style("padding: 0px;").close();
        html.td(2).colspan(String.valueOf(columns.size())).close();
        
        html.table(2).border("0").cellpadding("0").cellspacing("0").width("100%").close();
        html.tr(3).close();
        
        // start of status bar
        
        html.td(4).styleClass(HtmlConstants.STATUS_BAR_CSS).close();
        
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
        
        //end of status bar
        
        // start of filter buttons
        
        html.td(4).styleClass(HtmlConstants.FILTER_BUTTONS_CSS).close();
        
        if (ViewUtils.isFilterable(columns)) {
            html.img();
            html.src(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_FILTER_ARROW_IMAGE));
            html.style("border:0");
            html.alt("Arrow");
            html.end();

            html.nbsp();

            ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, coreContext, imagePath);
            
            toolbarBuilder.filterItemAsImage();

            html.nbsp();

            toolbarBuilder.clearItemAsImage();
        }

        html.tdEnd();
        
        // end of filter buttons
        
        html.trEnd(3);
        html.tableEnd(2);
        html.newline();
        html.tabs(2);
        
        html.tdEnd();
        html.trEnd(1);
    }
}
