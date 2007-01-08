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
package org.jmesa.view.html.toolbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.html.HtmlBuilder;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ToolbarImpl implements Toolbar {
	private List<ToolbarItemType> toolbarItemTypes = new ArrayList<ToolbarItemType>();
	ToolbarItemFactory toolbarItemFactory;
	private String[] exportTypes;
	private String toolbarClass;

	public ToolbarImpl(String imagesPath, CoreContext coreContext) {
		this(null, imagesPath, coreContext);
	}

	public ToolbarImpl(String toolbarClass, String imagesPath, CoreContext coreContext) {
		this.toolbarItemFactory = new ToolbarItemFactoryImpl(imagesPath, coreContext);
		this.toolbarClass = toolbarClass;
	}
	
	public List<ToolbarItemType> getToolbarItemTypes() {
		return toolbarItemTypes;
	}
	
	public void addToolbarItem(ToolbarItemType type) {
		toolbarItemTypes.add(type);
	}
	
	public void addExportItemTypes(String... exportTypes) {
		this.exportTypes = exportTypes;
	}
	
	protected void renderToolbarItem(HtmlBuilder html, ToolbarItemType type) {
		switch (type) {
		case FIRST_PAGE_ITEM:
			renderFirstPage(html);
			break;
		case PREV_PAGE_ITEM:
			renderPrevPage(html);
			break;
		case NEXT_PAGE_ITEM:
			renderNextPage(html);
			break;
		case LAST_PAGE_ITEM:
			renderLastPage(html);
			break;
		case MAX_ROWS_ITEM:
			renderMaxRows(html);
			break;
		case SEPARATOR:
			renderSeparator(html);
			break;
		}
	}
	
	protected void renderFirstPage(HtmlBuilder html) {
        html.td(4).close();
        ToolbarItem firstPageItem = toolbarItemFactory.createFirstPageItem();
        html.append(firstPageItem.getToolbarItemRenderer().render());
        html.tdEnd();
	}

	protected void renderPrevPage(HtmlBuilder html) {
		html.td(4).close();
		ToolbarItem prevPageItem = toolbarItemFactory.createPrevPageItem();
		html.append(prevPageItem.getToolbarItemRenderer().render());
		html.tdEnd();
	}

	protected void renderNextPage(HtmlBuilder html) {
		html.td(4).close();
		ToolbarItem nextPageItem = toolbarItemFactory.createNextPageItem();
		html.append(nextPageItem.getToolbarItemRenderer().render());
		html.tdEnd();
	}

	protected void renderLastPage(HtmlBuilder html) {
        html.td(4).close();
        ToolbarItem lastPageItem = toolbarItemFactory.createLastPageItem();
        html.append(lastPageItem.getToolbarItemRenderer().render());
        html.tdEnd();
	}

	protected void renderSeparator(HtmlBuilder html) {
        html.td(4).close();
        html.append(toolbarItemFactory.createSeparatorImage());
        html.tdEnd();
	}

	protected void renderMaxRows(HtmlBuilder html) {
        html.td(4).close();
        ToolbarItem maxRowsItem = toolbarItemFactory.createMaxRowsItem();
        html.append(maxRowsItem.getToolbarItemRenderer().render());
        html.tdEnd();
	}

	protected void renderExportItems(HtmlBuilder html) {
        for (int i = 0; i < exportTypes.length; i++) {
        	String exportType = exportTypes[i];
            html.td(4).close();
            ToolbarExport export = new ToolbarExport(exportType);
            ToolbarItem exportItem = toolbarItemFactory.createExportItem(export);
            html.append(exportItem.getToolbarItemRenderer().render());
            html.tdEnd();
		}
	}

	public String render() {
		HtmlBuilder html = new HtmlBuilder();
		
        html.table(2).border("0").cellpadding("0").cellspacing("1").styleClass(toolbarClass).close();

        html.tr(3).close();
        
        for (Iterator iter = toolbarItemTypes.iterator(); iter.hasNext();) {
        	ToolbarItemType toolbarItemType = (ToolbarItemType) iter.next();
        	renderToolbarItem(html, toolbarItemType);
		}
        
        renderExportItems(html);
		
        html.trEnd(3);

        html.tableEnd(2);
        html.newline();
        html.tabs(2);
		
		return html.toString();
	}
}
