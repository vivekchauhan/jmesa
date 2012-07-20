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

import static org.jmesa.model.tag.TagUtils.getRowRowRenderer;
import static org.jmesa.model.tag.TagUtils.getRowOnclick;
import static org.jmesa.model.tag.TagUtils.getRowOnmouseover;
import static org.jmesa.model.tag.TagUtils.getRowOnmouseout;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.jmesa.util.ItemUtils;
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
    private HtmlRow getHtmlRow() {
		
        HtmlRow htmlRow = new HtmlRow();
        
        htmlRow.setUniqueProperty(getUniqueProperty());
        htmlRow.setHighlighter(isHighlighter());
        htmlRow.setSortable(isSortable());
        htmlRow.setFilterable(isFilterable());
        htmlRow.setOnclick(getRowOnclick(htmlRow, getOnclick()));
        htmlRow.setOnmouseover(getRowOnmouseover(htmlRow, getOnmouseover()));
        htmlRow.setOnmouseout(getRowOnmouseout(htmlRow, getOnmouseout()));

        HtmlRowRenderer renderer = getRowRowRenderer(htmlRow, getRowRenderer());
        htmlRow.setRowRenderer(renderer);

        htmlRow.setStyle(getStyle());
        htmlRow.setStyleClass(getStyleClass());
        htmlRow.setEvenClass(getEvenClass());
        htmlRow.setOddClass(getOddClass());
        htmlRow.setHighlightStyle(getHighlightStyle());
        htmlRow.setHighlightClass(getHighlightClass());

        return htmlRow;
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

        TableModelTag facadeTag = (TableModelTag) findAncestorWithClass(this, TableModelTag.class);
        Collection<Map<String, Object>> pageItems = facadeTag.getPageItems();
        this.pageItem = new HashMap<String, Object>();
        pageItems.add(pageItem);

        String var = facadeTag.getVar();
        Object bean = getJspContext().getAttribute(var);
        pageItem.put(var, bean);
        pageItem.put(ItemUtils.JMESA_ITEM, bean);

        HtmlTable htmlTable = facadeTag.getTable();
        HtmlRow htmlRow = htmlTable.getRow();
        if (htmlRow == null) {
            htmlRow = getHtmlRow();
            htmlTable.setRow(htmlRow);
        }

        body.invoke(null);
    }
}
