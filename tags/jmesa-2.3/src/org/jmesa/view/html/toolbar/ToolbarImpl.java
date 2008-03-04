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
import org.jmesa.limit.ExportType;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.html.HtmlBuilder;

/**
 * The main logic to create toolbars.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public abstract class ToolbarImpl extends AbstractContextSupport implements Toolbar {
    private ToolbarItemFactory toolbarItemFactory;
    private List<ToolbarItem> toolbarItems = new ArrayList<ToolbarItem>();

    private ToolbarItemFactory getToolbarItemFactory() {
        if (toolbarItemFactory == null) {
            this.toolbarItemFactory = new ToolbarItemFactoryImpl(getWebContext(), getCoreContext());
        }

        return toolbarItemFactory;
    }

    public List<ToolbarItem> getToolbarItems() {
        return toolbarItems;
    }

    public void addToolbarItem(ToolbarItem item) {
        toolbarItems.add(item);
    }
    
    public ToolbarItem addToolbarItem(ToolbarItemType type) {
        ToolbarItem item = null;

        ToolbarItemFactory factory = getToolbarItemFactory();

        switch (type) {
            case FIRST_PAGE_ITEM:
                item = factory.createFirstPageItem();
                break;
            case PREV_PAGE_ITEM:
                item = factory.createPrevPageItem();
                break;
            case NEXT_PAGE_ITEM:
                item = factory.createNextPageItem();
                break;
            case LAST_PAGE_ITEM:
                item = factory.createLastPageItem();
                break;
            case MAX_ROWS_ITEM:
                item = factory.createMaxRowsItem();
                break;
            case FILTER_ITEM:
                item = factory.createFilterItem();
                break;
            case CLEAR_ITEM:
                item = factory.createClearItem();
                break;
            case SEPARATOR:
                item = factory.createSeparatorItem();
                break;
            case SAVE_WORKSHEET_ITEM:
                item = factory.createSaveWorksheetItem();
                break;
            case FILTER_WORKSHEET_ITEM:
                item = factory.createFilterWorksheetItem();
                break;
        }

        if (item != null) {
            toolbarItems.add(item);
        }

        return item;
    }

    public List<ToolbarItem> addExportToolbarItems(ExportType... exportTypes) {
        List<ToolbarItem> items = new ArrayList<ToolbarItem>();

        if (exportTypes == null || exportTypes.length == 0) {
            return items;
        }

        for (int i = 0; i < exportTypes.length; i++) {
            ExportType exportType = exportTypes[i];
            items.add(addExportToolbarItem(exportType));
        }

        return items;
    }

    public ToolbarItem addExportToolbarItem(ExportType exportType) {
        ToolbarExport export = new ToolbarExport(exportType);
        ToolbarItemFactory factory = getToolbarItemFactory();
        ToolbarItem item = factory.createExportItem(export);
        toolbarItems.add(item);
        return item;
    }

    public String render() {
        HtmlBuilder html = new HtmlBuilder();

        html.table(2).border("0").cellpadding("0").cellspacing("1").close();

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
