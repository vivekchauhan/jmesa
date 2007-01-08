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

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.view.html.HtmlBuilder;
import static org.jmesa.view.html.HtmlConstants.*;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ToolbarItemFactoryImpl implements ToolbarItemFactory {
	private String imagesPath;
	private CoreContext coreContext;

	public ToolbarItemFactoryImpl(WebContext webContext, CoreContext coreContext) {
		this(HtmlUtils.imagesPath(webContext, coreContext), coreContext);
	}
	
	public ToolbarItemFactoryImpl(String imagePath, CoreContext coreContext) {
		this.imagesPath = imagePath;
		this.coreContext = coreContext;
	}
	
    public ImageItemImpl createFirstPageItemAsImage() {
        ImageItemImpl item = new ImageItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FIRST_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_FIRST_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_FIRST_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FIRST_PAGE));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new FirstPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItemImpl createFirstPageItemAsText() {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FIRST_PAGE));
        item.setText(coreContext.getMessage(TOOLBAR_TEXT_FIRST_PAGE));
        return item;
    }

    public ImageItemImpl createPrevPageItemAsImage() {
        ImageItemImpl item = new ImageItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_PREV_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_PREV_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_PREV_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_PREV_PAGE));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new PrevPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItemImpl createPrevPageItemAsText() {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_PREV_PAGE));
        item.setText(coreContext.getMessage(TOOLBAR_TEXT_PREV_PAGE));
        return item;
    }

    public ImageItemImpl createNextPageItemAsImage() {
        ImageItemImpl item = new ImageItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_NEXT_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_NEXT_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_NEXT_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_NEXT_PAGE));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new NextPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }
    
    public TextItemImpl createNextPageItemAsText() {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_NEXT_PAGE));
        item.setText(coreContext.getMessage(TOOLBAR_TEXT_NEXT_PAGE));
        return item;
    }

    public ImageItemImpl createLastPageItemAsImage() {
        ImageItemImpl item = new ImageItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_LAST_PAGE));
        item.setDisabledImage(getImage(TOOLBAR_IMAGE_LAST_PAGE_DISABLED));
        item.setImage(getImage(TOOLBAR_IMAGE_LAST_PAGE));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_LAST_PAGE));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new LastPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItemImpl createLastPageItemAsText() {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_LAST_PAGE));
        item.setText(coreContext.getMessage(TOOLBAR_TEXT_LAST_PAGE));
        return item;
    }

    public ImageItemImpl createFilterItemAsImage() {
        ImageItemImpl item = new ImageItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FILTER));
        item.setImage(getImage(TOOLBAR_IMAGE_FILTER));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_FILTER));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new FilterItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItemImpl createFilterItemAsText() {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_FILTER));
        item.setText(coreContext.getMessage(TOOLBAR_TEXT_FILTER));
        return item;
    }

    public ImageItemImpl createClearItemAsImage() {
        ImageItemImpl item = new ImageItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_CLEAR));
        item.setImage(getImage(TOOLBAR_IMAGE_CLEAR));
        item.setAlt(coreContext.getMessage(TOOLBAR_TEXT_CLEAR));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new ClearItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItemImpl createClearItemAsText() {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(coreContext.getMessage(TOOLBAR_TOOLTIP_CLEAR));
        item.setText(coreContext.getMessage(TOOLBAR_TEXT_CLEAR));
        return item;
    }

    public ImageItemImpl createExportItemAsImage(ToolbarExport export) {
        ImageItemImpl item = new ImageItemImpl();
        
        item.setTooltip(getExportTooltip(export));
        item.setImage(imagesPath + getExportImage(export));
        
        item.setAlt(export.getText());
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new ExportItemRenderer(item, export, coreContext);
        renderer.setOnInvokeAction("onInvokeExportAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItemImpl createExportItemAsText(ToolbarExport export) {
        TextItemImpl item = new TextItemImpl();
        item.setTooltip(export.getTooltip());
        item.setText(export.getText());
        return item;
    }
    
    public String createSeparatorImage() {
    	HtmlBuilder html = new HtmlBuilder();
        html.img();
        html.src(getImage(TOOLBAR_IMAGE_SEPARATOR));
        html.style("border:0");
        html.alt("Separator");
        html.end();
        return html.toString();
    }

    public String createFilterArrowImage() {
    	HtmlBuilder html = new HtmlBuilder();
        html.img();
        html.src(getImage(TOOLBAR_IMAGE_FILTER_ARROW));
        html.style("border:0");
        html.alt("Arrow");
        html.end();
        return html.toString();
    }

    public MaxRowsItemImpl createMaxRowsItem() {
    	MaxRowsItemImpl item = new MaxRowsItemImpl();

        MaxRowsItemRenderer renderer = new MaxRowsItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
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
    	
		image = coreContext.getPreference(TOOLBAR_IMAGE + export.getType());
		
		return image;
    }

    protected String getExportTooltip(ToolbarExport export) {
    	String tooltip = export.getTooltip();
		if (StringUtils.isNotBlank(tooltip)) {
    		return tooltip;
    	}
    	
		tooltip = coreContext.getMessage(TOOLBAR_TOOLTIP + export.getType());
		
		return tooltip;
    }
}
