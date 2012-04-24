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
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import static org.jmesa.view.html.HtmlConstants.TOOLBAR_MAX_PAGE_NUMBERS;
import static org.jmesa.view.html.HtmlUtils.totalPages;

/**
 * The main logic to create toolbars.
 *
 * @since 2.2
 * @author Jeff Johnston
 */
public abstract class Toolbar extends AbstractContextSupport {
		
    private ToolbarItemFactory toolbarItemFactory;
    private List<ToolbarItem> toolbarItems = new ArrayList<ToolbarItem>();

    private ToolbarItemFactory getToolbarItemFactory() {
		
        if (toolbarItemFactory == null) {
            this.toolbarItemFactory = new ToolbarItemFactory(getWebContext(), getCoreContext());
        }

        return toolbarItemFactory;
    }

    protected boolean hasToolbarItems() {
		
        return toolbarItems != null && toolbarItems.size() > 0;
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
            case PAGE_NUMBER_ITEMS:
                addPageNumberItems();
                break;
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
            case CLEAR_WORKSHEET_ITEM:
                item = factory.createClearWorksheetItem();
                break;
            case ADD_WORKSHEET_ROW_ITEM:
                item = factory.createAddWorksheetRowItem();
                break;
        }

        if (item != null) {
            toolbarItems.add(item);
        }

        return item;
    }

    public List<ToolbarItem> addExportToolbarItems(String... exportTypes) {
		
        List<ToolbarItem> items = new ArrayList<ToolbarItem>();

        if (exportTypes == null || exportTypes.length == 0) {
            return items;
        }

        for (int i = 0; i < exportTypes.length; i++) {
            String exportType = exportTypes[i];
            items.add(addExportToolbarItem(exportType));
        }

        return items;
    }

    public ToolbarItem addExportToolbarItem(String exportType) {
		
        ToolbarExport export = new ToolbarExport(exportType);
        ToolbarItemFactory factory = getToolbarItemFactory();
        ToolbarItem item = factory.createExportItem(export);
        toolbarItems.add(item);
        return item;
    }

    private void addPageNumberItems() {
		
        ToolbarItemFactory factory = getToolbarItemFactory();

        Limit limit = getCoreContext().getLimit();
        RowSelect rowSelect = limit.getRowSelect();
        int page = rowSelect.getPage();
        int totalPages = totalPages(getCoreContext());

        int maxPages = Integer.valueOf(getCoreContext().getPreference(TOOLBAR_MAX_PAGE_NUMBERS));

        int centerPage = maxPages/2 + 1;
        int startEndPages = maxPages/2;

        if (totalPages > maxPages) {
            int start;
            int end;

            if (page <= centerPage) { // the start of the pages
                start = 1;
                end = maxPages;
            } else if (page >= totalPages - startEndPages) { // the last few pages
                start = totalPages - (maxPages - 1);
                end = totalPages;
            } else { // center everything else
                start = page - startEndPages;
                end = page + startEndPages;
            }

            for (int i = start; i <= end; i++) {
                addToolbarItem(factory.createPageNumberItem(i));
            }
        } else {
            for (int i = 1; i <= totalPages; i++) {
                addToolbarItem(factory.createPageNumberItem(i));
            }
        }
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
