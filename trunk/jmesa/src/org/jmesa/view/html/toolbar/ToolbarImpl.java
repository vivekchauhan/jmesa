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
import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.html.HtmlBuilder;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ToolbarImpl implements Toolbar {
	private ToolbarItemFactory toolbarItemFactory;
	private List<ToolbarItem> toolbarItems = new ArrayList<ToolbarItem>();
	private String toolbarClass;
	
	public ToolbarImpl(String imagesPath, CoreContext coreContext) {
		this.toolbarItemFactory = new ToolbarItemFactoryImpl(imagesPath, coreContext);
	}
	
	public void addToolbarItem(ToolbarItem item) {
		toolbarItems.add(item);
	}
	
	public void addToolbarItem(ToolbarItemType type) {
		switch (type) {
		case FIRST_PAGE_ITEM:
			addToolbarItem(toolbarItemFactory.createFirstPageItem());
			break;
		case PREV_PAGE_ITEM:
			addToolbarItem(toolbarItemFactory.createPrevPageItem());
			break;
		case NEXT_PAGE_ITEM:
			addToolbarItem(toolbarItemFactory.createNextPageItem());
			break;
		case LAST_PAGE_ITEM:
			addToolbarItem(toolbarItemFactory.createLastPageItem());
			break;
		case MAX_ROWS_ITEM:
			addToolbarItem(toolbarItemFactory.createMaxRowsItem()); 
			break;
		case FILTER_ITEM:
			addToolbarItem(toolbarItemFactory.createFilterItem());
			break;
		case CLEAR_ITEM:
			addToolbarItem(toolbarItemFactory.createClearItem());
			break;
		case SEPARATOR:
			addToolbarItem(toolbarItemFactory.createSeparatorItem());
			break;
		}
	}
	
	public void addExportToolbarItems(String... exportTypes) {
		if (exportTypes == null || exportTypes.length == 0) {
			return;
		}
		
		for (int i = 0; i < exportTypes.length; i++) {
        	String exportType = exportTypes[i];
            ToolbarExport export = new ToolbarExport(exportType);
            ToolbarItem item = toolbarItemFactory.createExportItem(export);
            addToolbarItem(item);
        }
	}
	
	public void setToolbarClass(String toolbarClass) {
		this.toolbarClass = toolbarClass;
	}
	
	public String render() {
		HtmlBuilder html = new HtmlBuilder();
		
        html.table(2).border("0").cellpadding("0").cellspacing("1").styleClass(toolbarClass).close();

        html.tr(3).close();
        
        for (ToolbarItem item : toolbarItems) {
            html.td(4).close();
        	html.append(item.getToolbarItemRenderer().render());
            html.tdEnd();
        }
		
        html.trEnd(3);

        html.tableEnd(2);
        html.newline();
        html.tabs(2);
		
		return html.toString();
	}
}
