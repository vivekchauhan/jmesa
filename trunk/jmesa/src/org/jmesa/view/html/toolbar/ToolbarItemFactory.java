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
import static org.jmesa.view.html.HtmlConstants.*;
import static org.jmesa.view.html.HtmlConstants.TOOLBAR_MAX_PAGE_NUMBERS;
import static org.jmesa.view.html.HtmlUtils.totalPages;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ToolbarItemFactory {
		
    private String imagesPath;
    private CoreContext coreContext;

    public ToolbarItemFactory(WebContext webContext, CoreContext coreContext) {
		
        this.imagesPath = HtmlUtils.imagesPath(webContext, coreContext);
        this.coreContext = coreContext;
    }

    public ToolbarItem createPageNumberToolbarItem(int page) {
		
        PageNumberToolbarItem toolbarItem = new PageNumberToolbarItem(coreContext, page);
        toolbarItem.setStyleClass(coreContext.getPreference(TOOLBAR_PAGE_NUMBER_CLASS));
        toolbarItem.setCode(ToolbarItemType.PAGE_NUMBER_ITEMS.toCode());
        return toolbarItem;
    }

    public ToolbarItem createFirstPageToolbarItem() {
		
        FirstPageToolbarItem toolbarItem = new FirstPageToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.FIRST_PAGE_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FIRST_PAGE));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_FIRST_PAGE_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_FIRST_PAGE));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FIRST_PAGE));
        return toolbarItem;
    }

    public ToolbarItem createPrevPageToolbarItem() {
		
        PrevPageToolbarItem toolbarItem = new PrevPageToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.PREV_PAGE_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_PREV_PAGE));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_PREV_PAGE_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_PREV_PAGE));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_PREV_PAGE));
        return toolbarItem;
    }

    public ToolbarItem createNextPageToolbarItem() {
		
        NextPageToolbarItem toolbarItem = new NextPageToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.NEXT_PAGE_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_NEXT_PAGE));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_NEXT_PAGE_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_NEXT_PAGE));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_NEXT_PAGE));
        return toolbarItem;
    }

    public ToolbarItem createLastPageToolbarItem() {
		
        LastPageToolbarItem toolbarItem = new LastPageToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.LAST_PAGE_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_LAST_PAGE));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_LAST_PAGE_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_LAST_PAGE));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_LAST_PAGE));
        return toolbarItem;
    }

    public ToolbarItem createFilterToolbarItem() {
		
        FilterToolbarItem toolbarItem = new FilterToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.FILTER_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FILTER));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_FILTER));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FILTER));
        return toolbarItem;
    }

    public ToolbarItem createClearToolbarItem() {
		
        ClearToolbarItem toolbarItem = new ClearToolbarItem(coreContext);        
        toolbarItem.setCode(ToolbarItemType.CLEAR_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_CLEAR));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_CLEAR));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_CLEAR));        
        return toolbarItem;
    }

    public ToolbarItem createMaxRowsToolbarItem() {
		
        MaxRowsToolbarItem toolbarItem = new MaxRowsToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.MAX_ROWS_ITEM.toCode());
        toolbarItem.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_TEXT_MAX_ROWS_DROPLIST));
        return toolbarItem;
    }

    public ToolbarItem createExportToolbarItem(String exportType) {
		
        ExportToolbarItem toolbarItem = new ExportToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.EXPORT_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP + exportType));
        toolbarItem.setImage(getImage(coreContext.getPreference(TOOLBAR_IMAGE + exportType)));
        toolbarItem.setAlt(exportType);
        return toolbarItem;
    }

    public ToolbarItem createSeparatorToolbarItem() {
		
        SeparatorToolbarItem toolbarItem = new SeparatorToolbarItem();
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_SEPARATOR));
        toolbarItem.setAlt("Separator");
        return toolbarItem;
    }

    public ToolbarItem createSaveWorksheetToolbarItem() {
		
        SaveWorksheetToolbarItem toolbarItem = new SaveWorksheetToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.SAVE_WORKSHEET_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_SAVE_WORKSHEET));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_SAVE_WORKSHEET_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_SAVE_WORKSHEET));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_SAVE_WORKSHEET));
        return toolbarItem;
    }

    public AbstractTextToolbarItem createFilterWorksheetToolbarItem() {
		
        FilterWorksheetToolbarItem toolbarItem = new FilterWorksheetToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.FILTER_WORKSHEET_ITEM.toCode());

        Worksheet worksheet = coreContext.getWorksheet();
        if (worksheet != null && worksheet.hasErrors()) {
            toolbarItem.setStyleClass(coreContext.getPreference(TOOLBAR_TEXT_ITEM_ERROR_CLASS));
        } else {
            toolbarItem.setStyleClass(coreContext.getPreference(TOOLBAR_TEXT_ITEM_CLASS));
        }

        toolbarItem.setText(coreContext.getMessage(TOOLBAR_TEXT_FILTER_WORKSHEET));
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FILTER_WORKSHEET));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FILTER_WORKSHEET));

        return toolbarItem;
    }

    public ToolbarItem createClearWorksheetToolbarItem() {
		
        ClearWorksheetToolbarItem toolbarItem = new ClearWorksheetToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.CLEAR_WORKSHEET_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_CLEAR_WORKSHEET));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_CLEAR_WORKSHEET_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_CLEAR_WORKSHEET));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_CLEAR_WORKSHEET));
        return toolbarItem;
    }
    
    public ToolbarItem createAddWorksheetRowToolbarItem() {
		
        AddWorksheetRowToolbarItem toolbarItem = new AddWorksheetRowToolbarItem(coreContext);
        toolbarItem.setCode(ToolbarItemType.ADD_WORKSHEET_ROW_ITEM.toCode());
        toolbarItem.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_ADD_WORKSHEET_ROW));
        toolbarItem.setDisabledImage(getImage(TOOLBAR_IMAGE_ADD_WORKSHEET_ROW_DISABLED));
        toolbarItem.setImage(getImage(TOOLBAR_IMAGE_ADD_WORKSHEET_ROW));
        toolbarItem.setAlt(coreContext.getMessage(TOOLBAR_TEXT_ADD_WORKSHEET_ROW));
        return toolbarItem;
	}
    
    public List<ToolbarItem> createPageNumberToolbarItems() {

        List<ToolbarItem> items = new ArrayList<ToolbarItem>();

        Limit limit = coreContext.getLimit();
        RowSelect rowSelect = limit.getRowSelect();
        int page = rowSelect.getPage();
        int totalPages = totalPages(coreContext);

        int maxPages = Integer.valueOf(coreContext.getPreference(TOOLBAR_MAX_PAGE_NUMBERS));

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
                ToolbarItem pageNumberItem = createPageNumberToolbarItem(i);
                items.add(pageNumberItem);
            }
        } else {
            for (int i = 1; i <= totalPages; i++) {
                items.add(createPageNumberToolbarItem(i));
            }
        }

        return items;
    }
    
    public List<ToolbarItem> createExportToolbarItems(String... exportTypes) {
		
        List<ToolbarItem> items = new ArrayList<ToolbarItem>();

        if (exportTypes == null || exportTypes.length == 0) {
            return items;
        }

        for (int i = 0; i < exportTypes.length; i++) {
            String exportType = exportTypes[i];
            ToolbarItem toolbarItem = createExportToolbarItem(exportType);
            items.add(toolbarItem);
        }

        return items;
    }

    protected String getImage(String image) {
		
        return imagesPath + coreContext.getPreference(image);
    }
}
