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
package org.jmesa.model.tag;

import static org.jmesa.model.tag.TagUtils.getTableTableRenderer;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.renderer.HtmlTableRenderer;

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
    private String tableRenderer;
    private String style;
    private String styleClass;
    private String width;
    private String border;
    private String cellpadding;
    private String cellspacing;

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

    /**
     * @since 2.2
     */
    public String getTableRenderer() {
        return tableRenderer;
    }

    /**
     * @since 2.2
     */
    public void setTableRenderer(String tableRenderer) {
        this.tableRenderer = tableRenderer;
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
    private HtmlTable getTable(HtmlComponentFactory factory) {
        HtmlTable table = factory.createTable();
        table.setCaption(getCaption());
        table.setCaptionKey(getCaptionKey());
        table.setTheme(getTheme());

        HtmlTableRenderer renderer = getTableTableRenderer(table, getTableRenderer());
        table.setTableRenderer(renderer);

        renderer.setWidth(getWidth());
        renderer.setStyle(getStyle());
        renderer.setStyleClass(getStyleClass());
        renderer.setBorder(getBorder());
        renderer.setCellpadding(getCellpadding());
        renderer.setCellspacing(getCellspacing());

        return table;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
            throw new IllegalStateException("You need to wrap the table in the facade tag.");
        }

        TableModelTag facadeTag = (TableModelTag) findAncestorWithClass(this, TableModelTag.class);
        HtmlTable table = facadeTag.getTable();
        if (table == null) {
            HtmlComponentFactory factory = facadeTag.getComponentFactory();
            table = getTable(factory);
            facadeTag.setTable(table);
        }

        body.invoke(null);
    }
}
