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
	private ToolbarItemFactory toolbarItemFactory;
	private String toolbarClass;
	
	private ToolbarItem firstPageItem;
	private ToolbarItem prevPageItem;
	private ToolbarItem nextPageItem;
	private ToolbarItem lastPageItem;
	private ToolbarItem maxRowsItem;
	private ToolbarItem filterItem;
	private ToolbarItem clearItem;
	private List<ToolbarItem> exportItems;

	public ToolbarImpl(String imagesPath, CoreContext coreContext) {
		this.toolbarItemFactory = new ToolbarItemFactoryImpl(imagesPath, coreContext);
	}
	
	public void setToolbarClass(String toolbarClass) {
		this.toolbarClass = toolbarClass;
	}
	
	public List<ToolbarItemType> getToolbarItemTypes() {
		return toolbarItemTypes;
	}
	
	public void addToolbarItem(ToolbarItemType type) {
		toolbarItemTypes.add(type);
		
		switch (type) {
		case FIRST_PAGE_ITEM:
			firstPageItem = toolbarItemFactory.createFirstPageItem();
			break;
		case PREV_PAGE_ITEM:
			prevPageItem = toolbarItemFactory.createPrevPageItem();
			break;
		case NEXT_PAGE_ITEM:
			nextPageItem = toolbarItemFactory.createNextPageItem();
			break;
		case LAST_PAGE_ITEM:
			lastPageItem = toolbarItemFactory.createLastPageItem();
			break;
		case MAX_ROWS_ITEM:
			maxRowsItem = toolbarItemFactory.createMaxRowsItem(); 
			break;
		case FILTER_ITEM:
			filterItem = toolbarItemFactory.createFilterItem();
			break;
		case CLEAR_ITEM:
			clearItem = toolbarItemFactory.createClearItem();
			break;
		}
	}
	
	public ToolbarItem getToolbarItem(ToolbarItemType type) {
		switch (type) {
		case FIRST_PAGE_ITEM:
			return firstPageItem;
		case PREV_PAGE_ITEM:
			return prevPageItem;
		case NEXT_PAGE_ITEM:
			return nextPageItem;
		case LAST_PAGE_ITEM:
			return lastPageItem;
		case MAX_ROWS_ITEM:
			return maxRowsItem;
		case FILTER_ITEM:
			return filterItem;
		case CLEAR_ITEM:
			return clearItem;
		}
		
		return null;
	}
	
	public void addExportToolbarItems(String... exportTypes) {
		if (exportTypes == null || exportTypes.length == 0) {
			return;
		}
		
		toolbarItemTypes.add(ToolbarItemType.EXPORT_ITEMS);

		exportItems = new ArrayList<ToolbarItem>(exportTypes.length);
        
		for (int i = 0; i < exportTypes.length; i++) {
        	String exportType = exportTypes[i];
            ToolbarExport export = new ToolbarExport(exportType);
            ToolbarItem item = toolbarItemFactory.createExportItem(export);
            exportItems.add(item);
        }
	}
	
	public List<ToolbarItem> getExportToolbarItems() {
		return exportItems;
	}
	
	private String renderToolbarItem(ToolbarItemType type) {
		switch (type) {
		case FIRST_PAGE_ITEM:
			return renderFirstPageItem();
		case PREV_PAGE_ITEM:
			return renderPrevPageItem();
		case NEXT_PAGE_ITEM:
			return renderNextPageItem();
		case LAST_PAGE_ITEM:
			return renderLastPageItem();
		case MAX_ROWS_ITEM:
			return renderMaxRowsItem();
		case FILTER_ITEM:
			return renderFilterItem();
		case CLEAR_ITEM:
			return renderClearItem();
		case EXPORT_ITEMS:
			return renderExportItems();
		case CUSTOM_ITEMS:
			return renderCustomItems();
		case SEPARATOR:
			return renderSeparator();
		}
		
		return "";
	}
	
	protected String renderFirstPageItem() {
		return firstPageItem.getToolbarItemRenderer().render();
	}

	protected String renderPrevPageItem() {
		return prevPageItem.getToolbarItemRenderer().render();
	}

	protected String renderNextPageItem() {
		return nextPageItem.getToolbarItemRenderer().render();
	}

	protected String renderLastPageItem() {
		return lastPageItem.getToolbarItemRenderer().render();
	}

	protected String renderMaxRowsItem() {
		return maxRowsItem.getToolbarItemRenderer().render();
	}

	protected String renderFilterItem() {
		return filterItem.getToolbarItemRenderer().render();
	}

	protected String renderClearItem() {
		return clearItem.getToolbarItemRenderer().render();
	}

	protected String renderExportItems() {
		HtmlBuilder html = new HtmlBuilder();
		
		int size = exportItems.size();
		for (int i = 0; i < size; i++) {
			ToolbarItem item = exportItems.get(i);
            html.append(item.getToolbarItemRenderer().render());
            
            if (i+1 < size) {
            	html.tdEnd();
            	html.td(4).close();
            }
		}

		return html.toString();
	}

	protected String renderSeparator() {
        return toolbarItemFactory.createSeparatorImage();
	}

	/**
	 * Meant to be overridden so can incorporate custom items.
	 * 
	 * @return The markup for the custom items.
	 */
	protected String renderCustomItems() {
		return "";
	}

	public String render() {
		HtmlBuilder html = new HtmlBuilder();
		
        html.table(2).border("0").cellpadding("0").cellspacing("1").styleClass(toolbarClass).close();

        html.tr(3).close();
        
        for (Iterator iter = toolbarItemTypes.iterator(); iter.hasNext();) {
        	ToolbarItemType toolbarItemType = (ToolbarItemType) iter.next();
            html.td(4).close();
        	html.append(renderToolbarItem(toolbarItemType));
            html.tdEnd();
		}
		
        html.trEnd(3);

        html.tableEnd(2);
        html.newline();
        html.tabs(2);
		
		return html.toString();
	}
}
