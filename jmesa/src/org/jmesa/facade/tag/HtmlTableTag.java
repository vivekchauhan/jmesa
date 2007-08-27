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
package org.jmesa.facade.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.view.html.component.HtmlTable;

/**
 * Represents an HtmlTable.
 * 
 * @since 2.1
 * @author jeff jie
 */
public class HtmlTableTag extends SimpleTagSupport {
    // core attributes
    private String caption;
    private String captionKey;

    // style attributes
    private String theme;
    private String style;
    private String styleClass;
    private String width;
    private String border;
    private String cellpadding;
    private String cellspacing;

    private HtmlTable table;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaptionKey() {
        return captionKey;
    }

    public void setCaptionKey(String captionKey) {
        this.captionKey = captionKey;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    /**
     * The table to use. If the table does not exist then one will be created.
     */
    public HtmlTable getTable() {
        if (table != null) {
            return table;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        this.table = facadeTag.getComponentFactory().createTable();
        table.setCaption(getCaption());
        table.setCaptionKey(getCaptionKey());
        table.setTheme(getTheme());
        table.getTableRenderer().setWidth(getWidth());
        table.getTableRenderer().setStyle(getStyle());
        table.getTableRenderer().setStyleClass(getStyleClass());
        table.getTableRenderer().setBorder(getBorder());
        table.getTableRenderer().setCellpadding(getCellpadding());
        table.getTableRenderer().setCellspacing(getCellspacing());

        facadeTag.setTable(table);

        return table;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
            throw new IllegalStateException("You need to wrap the table in the facade tag.");
        }

        getTable();

        body.invoke(null);
    }
}
