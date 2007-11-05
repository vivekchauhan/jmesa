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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.renderer.RowRenderer;
import org.jmesa.web.WebContext;

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

    private String onclick;
    private String onmouseover;
    private String onmouseout;

    private Map<String, ? super Object> pageItem;

    /**
     * @since 2.3
     */
    public String getRowRenderer() {
        return rowRenderer;
    }

    /**
     * @since 2.3
     */
    public void setRowRenderer(String rowRenderer) {
        this.rowRenderer = rowRenderer;
    }

    /**
     * @since 2.3
     */
    public String getStyle() {
        return style;
    }

    /**
     * @since 2.3
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @since 2.3
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * @since 2.3
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @since 2.3
     */
    public String getEvenClass() {
        return evenClass;
    }

    /**
     * @since 2.3
     */
    public void setEvenClass(String evenClass) {
        this.evenClass = evenClass;
    }

    /**
     * @since 2.3
     */
    public String getOddClass() {
        return oddClass;
    }

    /**
     * @since 2.3
     */
    public void setOddClass(String oddClass) {
        this.oddClass = oddClass;
    }

    /**
     * @since 2.3
     */
    public String getHighlightStyle() {
        return highlightStyle;
    }

    /**
     * @since 2.3
     */
    public void setHighlightStyle(String highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    /**
     * @since 2.3
     */
    public String getHighlightClass() {
        return highlightClass;
    }

    /**
     * @since 2.3
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

    /**
     * Get the row RowRenderer object.
     * 
     * @since 2.3
     */
    private RowRenderer getRowRowRenderer(TableFacadeTag facadeTag) {
        if (StringUtils.isBlank(getRowRenderer())) {
            return null;
        }

        RowRenderer rowRenderer = (RowRenderer) ClassUtils.createInstance(getRowRenderer());
        SupportUtils.setCoreContext(rowRenderer, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowRenderer, facadeTag.getWebContext());

        return rowRenderer;
    }

    /**
     * Get the row Onclick RowEvent object.
     */
    private RowEvent getRowOnclick(TableFacadeTag facadeTag) {
        if (StringUtils.isBlank(getOnclick())) {
            return null;
        }

        RowEvent rowEvent = (RowEvent) ClassUtils.createInstance(getOnclick());
        SupportUtils.setCoreContext(rowEvent, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowEvent, facadeTag.getWebContext());

        return rowEvent;
    }

    /**
     * Get the row Onmouseover RowEvent object.
     */
    private RowEvent getRowOnmouseover(TableFacadeTag facadeTag) {
        if (StringUtils.isBlank(getOnmouseover())) {
            return null;
        }

        RowEvent rowEvent = (RowEvent) ClassUtils.createInstance(getOnmouseover());
        SupportUtils.setCoreContext(rowEvent, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowEvent, facadeTag.getWebContext());

        return rowEvent;
    }

    /**
     * Get the row Onmouseout RowEvent object.
     */
    private RowEvent getRowOnmouseout(TableFacadeTag facadeTag) {
        if (StringUtils.isBlank(getOnmouseout())) {
            return null;
        }

        RowEvent rowEvent = (RowEvent) ClassUtils.createInstance(getOnmouseout());
        SupportUtils.setCoreContext(rowEvent, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowEvent, facadeTag.getWebContext());

        return rowEvent;
    }

    /**
     * The row to use. If the row does not exist then one will be created.
     */
    private HtmlRow getRow(TableFacadeTag facadeTag) {
        HtmlRow row = facadeTag.getComponentFactory().createRow();

        RowRenderer rowRenderer = getRowRowRenderer(facadeTag);
        if (rowRenderer != null) {
            row.setRowRenderer(rowRenderer);
            rowRenderer.setRow(row);
        }

        row.getRowRenderer().setStyle(getStyle());
        row.getRowRenderer().setStyleClass(getStyleClass());
        row.getRowRenderer().setEvenClass(getEvenClass());
        row.getRowRenderer().setOddClass(getOddClass());
        row.getRowRenderer().setHighlightStyle(getHighlightClass());
        row.getRowRenderer().setStyleClass(getStyleClass());

        row.setHighlighter(isHighlighter());

        row.setOnclick(getRowOnclick(facadeTag));
        row.setOnmouseover(getRowOnmouseover(facadeTag));
        row.setOnmouseout(getRowOnmouseout(facadeTag));

        return row;
    }

    /**
     * @return The current page item.
     */
    Map<String, ? super Object> getPageItem() {
        return pageItem;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
            throw new IllegalStateException("You need to wrap the columns in the row tag.");
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        Collection<Map<String, ?>> pageItems = facadeTag.getPageItems();
        this.pageItem = new HashMap<String, Object>();
        pageItems.add(pageItem);

        String var = facadeTag.getVar();
        WebContext webContext = facadeTag.getWebContext();
        Object bean = webContext.getPageAttribute(var);
        pageItem.put(var, bean);

        HtmlTable table = facadeTag.getTable();
        HtmlRow row = table.getRow();
        if (row == null) {
            row = getRow(facadeTag);
            table.setRow(row);
        }

        body.invoke(null);
    }
}
