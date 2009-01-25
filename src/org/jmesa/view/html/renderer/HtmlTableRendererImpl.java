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
package org.jmesa.view.html.renderer;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.renderer.AbstractTableRenderer;

public class HtmlTableRendererImpl extends AbstractTableRenderer implements HtmlTableRenderer {
    private String style;
    private String styleClass;
    private String border;
    private String cellpadding;
    private String cellspacing;
    private String width;

    public HtmlTableRendererImpl() {
        // default constructor
    }

    public HtmlTableRendererImpl(HtmlTable table) {
        setTable(table);
    }

    @Override
    public HtmlTable getTable() {
        return (HtmlTable) super.getTable();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        if (StringUtils.isBlank(styleClass)) {
            return getCoreContext().getPreference(HtmlConstants.TABLE_RENDERER_STYLE_CLASS);
        }

        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getBorder() {
        if (StringUtils.isBlank(border)) {
            return "0";
        }

        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCellpadding() {
        if (StringUtils.isBlank(cellpadding)) {
            return "0";
        }

        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        if (StringUtils.isBlank(cellspacing)) {
            return "0";
        }

        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();
        html.table(0);
        String id = getCoreContext().getLimit().getId();
        html.id(id);
        html.border(getBorder()).cellpadding(getCellpadding()).cellspacing(getCellspacing());
        html.style(getStyle());
        html.styleClass(getStyleClass());
        html.width(getWidth());
        html.close();

        if (StringUtils.isNotBlank(getTable().getCaption())) {
            html.caption().close().append(getTable().getCaption()).captionEnd();
        }

        return html;
    }
}
