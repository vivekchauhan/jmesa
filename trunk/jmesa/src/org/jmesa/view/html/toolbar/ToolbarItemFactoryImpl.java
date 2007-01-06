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
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlViewUtils;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ToolbarItemFactoryImpl implements ToolbarItemFactory {
	private String imagesPath;
	private CoreContext coreContext;

	public ToolbarItemFactoryImpl(WebContext webContext, CoreContext coreContext) {
		this(HtmlViewUtils.imagesPath(webContext, coreContext), coreContext);
	}
	
	public ToolbarItemFactoryImpl(String imagePath, CoreContext coreContext) {
		this.imagesPath = imagePath;
		this.coreContext = coreContext;
	}
	
    public ImageItem createFirstPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TOOLTIP));
        item.setDisabledImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_FIRST_PAGE_DISABLED_IMAGE, coreContext));
        item.setImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_FIRST_PAGE_IMAGE, coreContext));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TEXT));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new FirstPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItem createFirstPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TEXT));
        return item;
    }

    public ImageItem createPrevPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TOOLTIP));
        item.setDisabledImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_PREV_PAGE_DISABLED_IMAGE, coreContext));
        item.setImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_PREV_PAGE_IMAGE, coreContext));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TEXT));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new PrevPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItem createPrevPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TEXT));
        return item;
    }

    public ImageItem createNextPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TOOLTIP));
        item.setDisabledImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_NEXT_PAGE_DISABLED_IMAGE, coreContext));
        item.setImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_NEXT_PAGE_IMAGE, coreContext));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TEXT));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new NextPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }
    
    public TextItem createNextPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TEXT));
        return item;
    }

    public ImageItem createLastPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TOOLTIP));
        item.setDisabledImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_LAST_PAGE_DISABLED_IMAGE, coreContext));
        item.setImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_LAST_PAGE_IMAGE, coreContext));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TEXT));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new LastPageItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItem createLastPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TEXT));
        return item;
    }

    public ImageItem createFilterItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TOOLTIP));
        item.setImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_FILTER_IMAGE, coreContext));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TEXT));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new FilterItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItem createFilterItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TEXT));
        return item;
    }

    public ImageItem createClearItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TOOLTIP));
        item.setImage(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_CLEAR_IMAGE, coreContext));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TEXT));
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new ClearItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    public TextItem createClearItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TEXT));
        return item;
    }

    public ImageItem createExportItemAsImage(ToolbarExport export) {
        ImageItem item = new ImageItem();
        item.setTooltip(export.getTooltip());
        
        item.setImage(imagesPath + getExportImage(export));
        
        item.setAlt(export.getText());
        item.setStyle("border:0");
        
        ToolbarItemRenderer renderer = new ExportItemRenderer(item, export, coreContext);
        renderer.setOnInvokeAction("onInvokeExportAction");
        item.setToolbarItemRenderer(renderer);
        
        return item;
    }

    protected String getExportImage(ToolbarExport export) {
    	String image = export.getImage();
		if (StringUtils.isNotBlank(image)) {
    		return image;
    	}
    	
		image = coreContext.getPreference("view.html.toolbar.imageItem.image." + export.getType());
		
		return image;
    }

    public TextItem createExportItemAsText(ToolbarExport export) {
        TextItem item = new TextItem();
        item.setTooltip(export.getTooltip());
        item.setText(export.getText());
        return item;
    }
    
    public String createSeparatorImage() {
    	HtmlBuilder html = new HtmlBuilder();
        html.img();
        html.src(imagesPath + HtmlViewUtils.toolbarImage(HtmlConstants.TOOLBAR_SEPARATOR_IMAGE, coreContext));
        html.style("border:0");
        html.alt("Separator");
        html.end();
        return html.toString();
    }
    
}
