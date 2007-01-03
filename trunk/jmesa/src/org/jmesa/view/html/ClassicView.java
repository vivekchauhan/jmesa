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
import org.jmesa.limit.Filter;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.Sort;
import org.jmesa.view.View;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.ClearItemRenderer;
import org.jmesa.view.html.toolbar.ExportItemRenderer;
import org.jmesa.view.html.toolbar.ToolbarExport;
import org.jmesa.view.html.toolbar.FilterItemRenderer;
import org.jmesa.view.html.toolbar.FirstPageItemRenderer;
import org.jmesa.view.html.toolbar.LastPageItemRenderer;
import org.jmesa.view.html.toolbar.NextPageItemRenderer;
import org.jmesa.view.html.toolbar.PrevPageItemRenderer;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactory;
import org.jmesa.view.html.toolbar.ToolbarItemRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ClassicView implements View {
	private Table table;
	private CoreContext coreContext;
	private String imagesPath;

	public ClassicView(HtmlTable table, CoreContext coreContext) {
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
	
	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}
	
	protected String getImagesPath() {
		return imagesPath;
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
		html.newline();
		html.script().type("text/javascript").close();
		html.newline();
		html.append("addLimitToManager('" + limit.getId() + "')").semicolon().newline();
		html.append("setPageToLimit('" + limit.getId() + "','" + limit.getRowSelect().getPage() + "')").semicolon().newline();
		html.append("setMaxRowsToLimit('" + limit.getId() + "','" + limit.getRowSelect().getMaxRows() + "')").semicolon().newline();
		
		for(Sort sort: limit.getSortSet().getSorts()) {
			html.append("addSortToLimit('" + limit.getId() + "','" + sort.getProperty() + "','" + sort.getOrder().toParam() + "','" + sort.getPosition() + "')").semicolon().newline();
		}

		for(Filter filter: limit.getFilterSet().getFilters()) {
			html.append("addFilterToLimit('" + limit.getId() + "','" + filter.getProperty() + "','" + filter.getValue() + "')").semicolon().newline();
		}
		
		html.scriptEnd();
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
	
	protected void filter(HtmlBuilder html, List<HtmlColumn> columns) {
		html.tr(1).styleClass("filter").close();
		
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

        html.td(2).close();
        
        // start of title
        
        html.span().styleClass("title").close().append(table.getTitle()).spanEnd();
        
        html.tdEnd();

        // end of title

        // start of toolbar

        html.td(2).align("right").close();

        html.table(2).border("0").cellpadding("0").cellspacing("1").styleClass(HtmlConstants.TOOLBAR_CSS).close();

        html.tr(3).close();
        
        ToolbarItemFactory toolbarItemFactory = new ToolbarItemFactory(imagesPath, coreContext);

        html.td(4).close();
        ToolbarItem firstPageItem = toolbarItemFactory.createFirstPageItemAsImage();
        ToolbarItemRenderer firstPageItemRenderer = new FirstPageItemRenderer(coreContext);
        html.append(firstPageItemRenderer.render(firstPageItem));
        html.tdEnd();

        html.td(4).close();
        ToolbarItem prevPageItem = toolbarItemFactory.createPrevPageItemAsImage();
        ToolbarItemRenderer prevPageItemRenderer = new PrevPageItemRenderer(coreContext);
        html.append(prevPageItemRenderer.render(prevPageItem));
        html.tdEnd();

        html.td(4).close();
        ToolbarItem nextPageItem = toolbarItemFactory.createNextPageItemAsImage();
        ToolbarItemRenderer nextPageItemRenderer = new NextPageItemRenderer(coreContext);
        html.append(nextPageItemRenderer.render(nextPageItem));
        html.tdEnd();

        html.td(4).close();
        ToolbarItem lastPageItem = toolbarItemFactory.createLastPageItemAsImage();
        ToolbarItemRenderer lastPageItemRenderer = new LastPageItemRenderer(coreContext);
        html.append(lastPageItemRenderer.render(lastPageItem));
        html.tdEnd();
        
        // separator 
        
        html.td(4).close();
        html.img();
        html.src(imagesPath + HtmlConstants.TOOLBAR_SEPARATOR_IMAGE);
        html.style("border:0");
        html.alt("Separator");
        html.end();
        html.tdEnd();

        // rows displayed
        
        html.td(4).style("width:20px").close();
        rowsDisplayedDroplist(html);
        html.img();
        html.src(imagesPath + HtmlConstants.TOOLBAR_ROWS_DISPLAYED_IMAGE);
        html.style("border:0");
        html.alt("Rows Displayed");
        html.end();
        html.tdEnd();
        
        // separator 
        
        html.td(4).close();
        html.img();
        html.src(imagesPath + HtmlConstants.TOOLBAR_SEPARATOR_IMAGE);
        html.style("border:0");
        html.alt("Separator");
        html.end();
        html.tdEnd();
        
        html.td(4).close();
        ToolbarExport export = new ToolbarExport("csv");
        export.setImageName("csv.gif");
        ToolbarItem exportItem = toolbarItemFactory.createExportItemAsImage(export);
        ToolbarItemRenderer exportItemRenderer = new ExportItemRenderer(export, coreContext);
        html.append(exportItemRenderer.render(exportItem));
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
            html.src(imagesPath + HtmlConstants.TOOLBAR_FILTER_ARROW_IMAGE);
            html.style("border:0");
            html.alt("Arrow");
            html.end();

            html.nbsp();
            
            ToolbarItemFactory toolbarItemFactory = new ToolbarItemFactory(imagesPath, coreContext);

            ToolbarItem filterItem = toolbarItemFactory.createFilterItemAsImage();
            ToolbarItemRenderer filterItemRenderer = new FilterItemRenderer(coreContext);
            html.append(filterItemRenderer.render(filterItem));

            html.nbsp();

            ToolbarItem clearItem = toolbarItemFactory.createClearItemAsImage();
            ToolbarItemRenderer clearItemRenderer = new ClearItemRenderer(coreContext);
            html.append(clearItemRenderer.render(clearItem));
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
    
    protected void rowsDisplayedDroplist(HtmlBuilder html) {
        Limit limit = coreContext.getLimit();
    	
    	int rowsDisplayed = 12;
        int medianRowsDisplayed = 24;
        int maxRowsDisplayed = 36;
        int currentRowsDisplayed = limit.getRowSelect().getMaxRows();

        html.select().name("maxRows");

        StringBuffer onchange = new StringBuffer();
        onchange.append("setMaxRowsToLimit('" + limit.getId() + "', this.options[this.selectedIndex].value);onInvokeAction('" + limit.getId() + "')");
        html.onchange(onchange.toString());

        html.close();

        html.newline();
        html.tabs(4);

        // default rows
        html.option().value(String.valueOf(rowsDisplayed));
        if (currentRowsDisplayed == rowsDisplayed) {
            html.selected();
        }
        html.close();
        html.append(String.valueOf(rowsDisplayed));
        html.optionEnd();

        // median rows
        html.option().value(String.valueOf(medianRowsDisplayed));
        if (currentRowsDisplayed == medianRowsDisplayed) {
            html.selected();
        }
        html.close();
        html.append(String.valueOf(medianRowsDisplayed));
        html.optionEnd();

        // max rows
        html.option().value(String.valueOf(maxRowsDisplayed));
        if (currentRowsDisplayed == maxRowsDisplayed) {
            html.selected();
        }
        html.close();
        html.append(String.valueOf(maxRowsDisplayed));
        html.optionEnd();

        html.newline();
        html.tabs(4);
        html.selectEnd();
    }    
}
