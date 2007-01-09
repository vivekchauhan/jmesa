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
		HtmlBuilder html = new HtmlBuilder();
		HtmlRow row = getTable().getRow();
		List columns = row.getColumns();

		themeStart(html, getTable());
		
		toolbar(html, getTable());
		
		tableStart(html, getTable());
		
		theadStart(html);
		
		statusBar(html, columns);
		
		header(html, columns);
		
		filter(html, columns);
		
		theadEnd(html);
		
		tbodyStart(html);
		
		body(html, row, columns);
		
		tbodyEnd(html);

		tableEnd(html);
		
		themeEnd(html);
		
		script(html);

		return html;
	}

	protected void script(HtmlBuilder html) {
		Limit limit = coreContext.getLimit();
		html.append(HtmlUtils.initJavascriptLimit(limit));
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
        String tbodyClass = getCoreContext().getPreference(HtmlConstants.TBODY_CLASS);
		html.tbody(1).styleClass(tbodyClass).close();
    }

	protected void tbodyEnd(HtmlBuilder html) {
        html.tbodyEnd(1);
    }
	
	protected void filter(HtmlBuilder html, List<HtmlColumn> columns) {
        String filterClass = getCoreContext().getPreference(HtmlConstants.FILTER_CLASS);
		html.tr(1).styleClass(filterClass).close();
		
		for (HtmlColumn column : columns) {
			html.append(column.getFilterRenderer().render());
		}
		
		html.trEnd(1);
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
				html.append(column.getCellRenderer().render(item, rowcount));
			}

			html.trEnd(1);
		}
	}
	
    protected void toolbar(HtmlBuilder html, HtmlTable table) {
        html.table(0).border("0").cellpadding("0").cellspacing("0");
        
        html.width(table.getTableRenderer().getWidth()).close();

        html.tr(1).close();

        // start of title

        html.td(2).close();
        
        String titleClass = getCoreContext().getPreference(HtmlConstants.TITLE_CLASS);
        html.span().styleClass(titleClass).close().append(table.getTitle()).spanEnd();
        
        html.tdEnd();

        // end of title

        // start of toolbar

        html.td(2).align("right").close();

        String toolbarClass = getCoreContext().getPreference(HtmlConstants.TOOLBAR_CLASS);
        
        Toolbar toolbar = new ToolbarImpl(imagesPath, coreContext);
        toolbar.setToolbarClass(toolbarClass);
        toolbar.addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        toolbar.addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        toolbar.addExportToolbarItems(exportTypes);
        html.append(toolbar.render());
        
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

        String statusBarClass = getCoreContext().getPreference(HtmlConstants.STATUS_BAR_CLASS);
        
        html.tr(3).styleClass(statusBarClass).close();
        
        // start of status bar
        
		html.td(4).close();
        
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
        
        // end of status bar
        
        // start of filter buttons
        
		html.td(4).align("right").close();
        
        if (ViewUtils.isFilterable(columns)) {
            Toolbar toolbar = new ToolbarImpl(imagesPath, coreContext);
            toolbar.addToolbarItem(ToolbarItemType.FILTER_ITEM);
            toolbar.addToolbarItem(ToolbarItemType.CLEAR_ITEM);
            html.append(toolbar.render());
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
