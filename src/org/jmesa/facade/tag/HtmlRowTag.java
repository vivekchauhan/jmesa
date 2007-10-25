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
import org.jmesa.web.WebContext;

/**
 * Represents an HtmlRow.
 * 
 * @since 2.1
 * @author jeff jie
 */
public class HtmlRowTag extends SimpleTagSupport {
    private boolean highlighter = true;
    private String onclick;
    private String onmouseover;
    private String onmouseout;

    private Map<String, Object> pageItem;

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
     * Get the row Onclick RowEvent object.
     */
    private RowEvent getRowOnclick() {
        if (StringUtils.isBlank(getOnclick())) {
            return null;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

        RowEvent rowEvent = (RowEvent) ClassUtils.createInstance(getOnclick());
        SupportUtils.setCoreContext(rowEvent, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowEvent, facadeTag.getWebContext());

        return rowEvent;
    }

    /**
     * Get the row Onmouseover RowEvent object.
     */
    private RowEvent getRowOnmouseover() {
        if (StringUtils.isBlank(getOnmouseover())) {
            return null;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

        RowEvent rowEvent = (RowEvent) ClassUtils.createInstance(getOnmouseover());
        SupportUtils.setCoreContext(rowEvent, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowEvent, facadeTag.getWebContext());

        return rowEvent;
    }

    /**
     * Get the row Onmouseout RowEvent object.
     */
    private RowEvent getRowOnmouseout() {
        if (StringUtils.isBlank(getOnmouseout())) {
            return null;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

        RowEvent rowEvent = (RowEvent) ClassUtils.createInstance(getOnmouseout());
        SupportUtils.setCoreContext(rowEvent, facadeTag.getCoreContext());
        SupportUtils.setWebContext(rowEvent, facadeTag.getWebContext());

        return rowEvent;
    }

    /**
     * The row to use. If the row does not exist then one will be created.
     */
    private HtmlRow getRow() {
        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

        HtmlRow row = facadeTag.getComponentFactory().createRow();
        row.setHighlighter(isHighlighter());
        row.setOnclick(getRowOnclick());
        row.setOnmouseover(getRowOnmouseover());
        row.setOnmouseout(getRowOnmouseout());

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
        Collection<Object> pageItems = facadeTag.getPageItems();
        this.pageItem = new HashMap<String, Object>();
        pageItems.add(pageItem);

        String var = facadeTag.getVar();
        WebContext webContext = facadeTag.getWebContext();
        Object bean = webContext.getPageAttribute(var);
        pageItem.put(var, bean);

        HtmlTable table = facadeTag.getTable();
        HtmlRow row = table.getRow();
        if (row == null) {
            row = getRow();
            table.setRow(row);
        }

        body.invoke(null);
    }
}
