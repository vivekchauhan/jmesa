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

import static org.jmesa.view.html.HtmlConstants.*;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
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

    public ToolbarItem createPageNumberItem(int page) {
		
        PageNumberItem item = new PageNumberItem(page);
        item.setStyleClass(coreContext.getPreference(TOOLBAR_PAGE_NUMBER_CLASS));
        item.setCode(ToolbarItemType.PAGE_NUMBER_ITEMS.toCode());

        PageNumberItemRenderer renderer = new PageNumberItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createFirstPageItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.FIRST_PAGE_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FIRST_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_FIRST_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_FIRST_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FIRST_PAGE));

        ToolbarItemRenderer renderer = new FirstPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createPrevPageItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.PREV_PAGE_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_PREV_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_PREV_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_PREV_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_PREV_PAGE));

        ToolbarItemRenderer renderer = new PrevPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createNextPageItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.NEXT_PAGE_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_NEXT_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_NEXT_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_NEXT_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_NEXT_PAGE));

        ToolbarItemRenderer renderer = new NextPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createLastPageItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.LAST_PAGE_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_LAST_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_LAST_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_LAST_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_LAST_PAGE));

        ToolbarItemRenderer renderer = new LastPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createFilterItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.FILTER_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FILTER));
        item.setImage(getImage(TOOLBAR_IMAGE_FILTER));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FILTER));

        ToolbarItemRenderer renderer = new FilterItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createClearItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.CLEAR_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_CLEAR));
        item.setImage(getImage(TOOLBAR_IMAGE_CLEAR));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_CLEAR));

        ToolbarItemRenderer renderer = new ClearItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public MaxRowsItem createMaxRowsItem() {
		
        MaxRowsItem item = new MaxRowsItem();
        item.setCode(ToolbarItemType.MAX_ROWS_ITEM.toCode());
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_TEXT_MAX_ROWS_DROPLIST));

        MaxRowsItemRenderer renderer = new MaxRowsItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createExportItem(ToolbarExport export) {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.EXPORT_ITEM.toCode());

        item.setTooltip(getExportTooltip(export));
        item.setImage(imagesPath + getExportImage(export));

        item.setAlt(export.getText());

        ToolbarItemRenderer renderer = new ExportItemRenderer(item, export, coreContext);
        renderer.setOnInvokeAction(getOnInvokeExportAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createSeparatorItem() {
		
        ImageItem item = new SeparatorItem();

        item.setImage(getImage(TOOLBAR_IMAGE_SEPARATOR));
        item.setAlt("Separator");

        return item;
    }

    public ToolbarItem createSaveWorksheetItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.SAVE_WORKSHEET_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_SAVE_WORKSHEET));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_SAVE_WORKSHEET_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_SAVE_WORKSHEET));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_SAVE_WORKSHEET));

        ToolbarItemRenderer renderer = new SaveWorksheetItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public TextItem createFilterWorksheetItem() {
		
        TextItem item = new TextItem();
        item.setCode(ToolbarItemType.FILTER_WORKSHEET_ITEM.toCode());

        Worksheet worksheet = coreContext.getWorksheet();
        if (worksheet != null && worksheet.hasErrors()) {
            item.setStyleClass(coreContext.getPreference(TOOLBAR_TEXT_ITEM_ERROR_CLASS));
        } else {
            item.setStyleClass(coreContext.getPreference(TOOLBAR_TEXT_ITEM_CLASS));
        }

        item.setText(coreContext.getMessage(TOOLBAR_TEXT_FILTER_WORKSHEET));
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FILTER_WORKSHEET));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FILTER_WORKSHEET));

        ToolbarItemRenderer renderer = new FilterWorksheetItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    public ToolbarItem createClearWorksheetItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.CLEAR_WORKSHEET_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_CLEAR_WORKSHEET));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_CLEAR_WORKSHEET_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_CLEAR_WORKSHEET));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_CLEAR_WORKSHEET));
        
        ToolbarItemRenderer renderer = new ClearWorksheetItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }
    
    public ToolbarItem createAddWorksheetRowItem() {
		
        ImageItem item = new ImageItem();
        item.setCode(ToolbarItemType.ADD_WORKSHEET_ROW_ITEM.toCode());
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_ADD_WORKSHEET_ROW));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_ADD_WORKSHEET_ROW_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_ADD_WORKSHEET_ROW));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_ADD_WORKSHEET_ROW));

        ToolbarItemRenderer renderer = new AddWorksheetRowItemRenderer(item, coreContext);
        renderer.setOnInvokeAction(getOnInvokeAction());
        item.setToolbarItemRenderer(renderer);

        return item;
	}

    protected String getImage(String image) {
		
        return imagesPath + coreContext.getPreference(image);
    }

    protected String getExportImage(ToolbarExport export) {
		
        String image = export.getImage();
        if (StringUtils.isNotBlank(image)) {
            return image;
        }

        image = coreContext.getPreference(TOOLBAR_IMAGE + export.getExportType());

        return image;
    }

    protected String getExportTooltip(ToolbarExport export) {
		
        String tooltip = export.getTooltip();
        if (StringUtils.isNotBlank(tooltip)) {
            return tooltip;
        }

        tooltip = coreContext.getMessage(TOOLBAR_TOOLTIP + export.getExportType());

        return tooltip;
    }

    protected String getOnInvokeAction() {
		
        return coreContext.getPreference(ON_INVOKE_ACTION);
    }

    protected String getOnInvokeExportAction() {
		
        return coreContext.getPreference(ON_INVOKE_EXPORT_ACTION);
    }
}
