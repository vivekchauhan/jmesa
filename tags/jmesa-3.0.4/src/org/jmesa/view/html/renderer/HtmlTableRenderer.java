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
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.renderer.AbstractTableRenderer;

public class HtmlTableRenderer extends AbstractTableRenderer {
    public HtmlTableRenderer() {}

    public HtmlTableRenderer(HtmlTable table) {
        setTable(table);
    }

    @Override
    public HtmlTable getTable() {
        return (HtmlTable) super.getTable();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public String getStyle() {
        return getTable().getStyle();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public void setStyle(String style) {
        getTable().setStyle(style);
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public String getStyleClass() {
        return getTable().getStyleClass();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public void setStyleClass(String styleClass) {
        getTable().setStyleClass(styleClass);
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public String getBorder() {
        return getTable().getBorder();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public void setBorder(String border) {
        getTable().setBorder(border);
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public String getCellpadding() {
        return getTable().getCellpadding();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public void setCellpadding(String cellpadding) {
        getTable().setCellpadding(cellpadding);
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public String getCellspacing() {
        return getTable().getCellspacing();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public void setCellspacing(String cellspacing) {
        getTable().setCellspacing(cellspacing);
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public String getWidth() {
        return getTable().getWidth();
    }

    /**
     * @deprecated Should get/set the value on the HtmlTable.
     */
    @Deprecated
    public void setWidth(String width) {
        getTable().setWidth(width);
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
