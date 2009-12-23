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

import static org.jmesa.facade.tag.TagUtils.getRowRowRenderer;
import static org.jmesa.facade.tag.TagUtils.getRowOnclick;
import static org.jmesa.facade.tag.TagUtils.getRowOnmouseover;
import static org.jmesa.facade.tag.TagUtils.getRowOnmouseout;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.util.ItemUtils;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.renderer.HtmlRowRenderer;

/**
 * Represents an HtmlRow.
 * 
 * @since 2.1
 * @author jeff jie
 */
public class HtmlRowTag extends SimpleTagSupport {

    private String rowRenderer;
    private String style;
    private String styleClass;
    private String evenClass;
    private String oddClass;
    private String highlightStyle;
    private String highlightClass;
    private boolean highlighter = true;
    private Boolean sortable;
    private Boolean filterable;
    private String onclick;
    private String onmouseover;
    private String onmouseout;
    private String uniqueProperty;
    private Map<String, Object> pageItem;

    /**
     * @since 2.2
     */
    public String getRowRenderer() {
        return rowRenderer;
    }

    /**
     * @since 2.2
     */
    public void setRowRenderer(String rowRenderer) {
        this.rowRenderer = rowRenderer;
    }

    /**
     * @since 2.2
     */
    public String getStyle() {
        return style;
    }

    /**
     * @since 2.2
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @since 2.2
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * @since 2.2
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @since 2.2
     */
    public String getEvenClass() {
        return evenClass;
    }

    /**
     * @since 2.2
     */
    public void setEvenClass(String evenClass) {
        this.evenClass = evenClass;
    }

    /**
     * @since 2.2
     */
    public String getOddClass() {
        return oddClass;
    }

    /**
     * @since 2.2
     */
    public void setOddClass(String oddClass) {
        this.oddClass = oddClass;
    }

    /**
     * @since 2.2
     */
    public String getHighlightStyle() {
        return highlightStyle;
    }

    /**
     * @since 2.2
     */
    public void setHighlightStyle(String highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    /**
     * @since 2.2
     */
    public String getHighlightClass() {
        return highlightClass;
    }

    /**
     * @since 2.2
     */
    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }

    public boolean isHighlighter() {
        return highlighter;
    }

    public void setHighlighter(boolean highlighter) {
        this.highlighter = highlighter;
    }

    /**
     * @since 2.2
     */
    public Boolean isSortable() {
        return sortable;
    }

    /**
     * @since 2.2
     */
    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * @since 2.2
     */
    public Boolean isFilterable() {
        return filterable;
    }

    /**
     * @since 2.2
     */
    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }
    
    public String getUniqueProperty() {
        return uniqueProperty;
    }

    public void setUniqueProperty(String uniqueProperty) {
        this.uniqueProperty = uniqueProperty;
    }

    /**
     * The row to use. If the row does not exist then one will be created.
     */
    private HtmlRow getRow(HtmlComponentFactory factory) {
        HtmlRow row = factory.createRow();
        row.setUniqueProperty(getUniqueProperty());
        row.setHighlighter(isHighlighter());
        row.setSortable(isSortable());
        row.setFilterable(isFilterable());
        row.setOnclick(getRowOnclick(row, getOnclick()));
        row.setOnmouseover(getRowOnmouseover(row, getOnmouseover()));
        row.setOnmouseout(getRowOnmouseout(row, getOnmouseout()));

        HtmlRowRenderer rowRenderer = getRowRowRenderer(row, getRowRenderer());
        row.setRowRenderer(rowRenderer);

        rowRenderer.setStyle(getStyle());
        rowRenderer.setStyleClass(getStyleClass());
        rowRenderer.setEvenClass(getEvenClass());
        rowRenderer.setOddClass(getOddClass());
        rowRenderer.setHighlightStyle(getHighlightStyle());
        rowRenderer.setHighlightClass(getHighlightClass());

        return row;
    }

    /**
     * @return The current page item.
     */
    Map<String, Object> getPageItem() {
        return pageItem;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
            throw new IllegalStateException("You need to wrap the columns in the row tag.");
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        Collection<Map<String, Object>> pageItems = facadeTag.getPageItems();
        this.pageItem = new HashMap<String, Object>();
        pageItems.add(pageItem);

        String var = facadeTag.getVar();
        Object bean = getJspContext().getAttribute(var);
        pageItem.put(var, bean);
        pageItem.put(ItemUtils.JMESA_ITEM, bean);

        HtmlTable table = facadeTag.getTable();
        HtmlRow row = table.getRow();
        if (row == null) {
            HtmlComponentFactory factory = facadeTag.getComponentFactory();
            row = getRow(factory);
            table.setRow(row);
        }

        body.invoke(null);
    }
}
