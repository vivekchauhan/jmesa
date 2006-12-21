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

import org.jmesa.core.CoreContext;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlUtils;

/**
 * @author Jeff Johnston
 */
public class ToolbarBuilder {
    private HtmlBuilder html;
    private CoreContext coreContext;
    private String imagePath;

    public ToolbarBuilder(HtmlBuilder html, CoreContext coreContext, String imagePath) {
        this.html = html;
        this.coreContext = coreContext;
        this.imagePath = imagePath;
    }
    
    public void firstPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TOOLTIP));
        item.setDisabledImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_FIRST_PAGE_DISABLED_IMAGE));
        item.setImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_FIRST_PAGE_IMAGE));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TEXT));
        item.setStyle("border:0");
        ToolbarItemUtils.buildFirstPage(html, coreContext, item);
    }

    public void firstPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_FIRST_PAGE_TEXT));
        ToolbarItemUtils.buildFirstPage(html, coreContext, item);
    }

    public void prevPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TOOLTIP));
        item.setDisabledImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_PREV_PAGE_DISABLED_IMAGE));
        item.setImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_PREV_PAGE_IMAGE));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TEXT));
        item.setStyle("border:0");
        ToolbarItemUtils.buildPrevPage(html, coreContext, item);
    }

    public void prevPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_PREV_PAGE_TEXT));
        ToolbarItemUtils.buildPrevPage(html, coreContext, item);
    }

    public void nextPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TOOLTIP));
        item.setDisabledImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_NEXT_PAGE_DISABLED_IMAGE));
        item.setImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_NEXT_PAGE_IMAGE));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TEXT));
        item.setStyle("border:0");
        ToolbarItemUtils.buildNextPage(html, coreContext, item);
    }

    public void nextPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_NEXT_PAGE_TEXT));
        ToolbarItemUtils.buildNextPage(html, coreContext, item);
    }

    public void lastPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TOOLTIP));
        item.setDisabledImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_LAST_PAGE_DISABLED_IMAGE));
        item.setImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_LAST_PAGE_IMAGE));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TEXT));
        item.setStyle("border:0");
        ToolbarItemUtils.buildLastPage(html, coreContext, item);
    }

    public void lastPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_LAST_PAGE_TEXT));
        ToolbarItemUtils.buildLastPage(html, coreContext, item);
    }

    public void filterItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TOOLTIP));
        item.setImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_FILTER_IMAGE));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TEXT));
        item.setStyle("border:0");
        ToolbarItemUtils.buildFilter(html, coreContext, item);
    }

    public void filterItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_FILTER_TEXT));
        ToolbarItemUtils.buildFilter(html, coreContext, item);
    }

    public void clearItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TOOLTIP));
        item.setImage(HtmlUtils.getImage(imagePath, HtmlConstants.TOOLBAR_CLEAR_IMAGE));
        item.setAlt(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TEXT));
        item.setStyle("border:0");
        ToolbarItemUtils.buildClear(html, coreContext, item);
    }

    public void clearItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TOOLTIP));
        item.setText(coreContext.getMessage(HtmlConstants.TOOLBAR_CLEAR_TEXT));
        ToolbarItemUtils.buildClear(html, coreContext, item);
    }

    public void exportItemAsImage(Object export) {
        ImageItem item = new ImageItem();
//        item.setTooltip(export.getTooltip());
//        item.setImage(HtmlUtils.getImage(model, export.getImageName()));
//        item.setAlt(export.getText());
//        item.setStyle("border:0");
//        ToolbarItemUtils.buildExport(html, model, item, export);
    }

    public void exportItemAsText(Object export) {
        TextItem item = new TextItem();
//        item.setTooltip(export.getTooltip());
//        item.setText(export.getText());
//        ToolbarItemUtils.buildExport(html, model, item, export);
    }
    
    public String toString() {
        return html.toString();
    }
}
