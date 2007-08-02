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
package org.jmesa.web.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.view.View;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactory;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author jeff jie
 */
public class TableTag extends BodyTagSupport {
    private Logger logger = LoggerFactory.getLogger(ColumnTag.class);

    // core attributes
    private String id;
    private String items = "items";
    private int maxRows = 15;
    private String caption;
    private String exportTypes;
    private String limit = "limit";

    // style attributes
    private String theme;
    private String style;
    private String styleClass;
    private String border;
    private String width;
    private String cellpadding;
    private String cellspacing;

    // other menbers
    private HtmlComponentFactory componentFactory;
    private HtmlTable table;

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public String getExportTypes() {
        return exportTypes;
    }

    public void setExportTypes(String export) {
        this.exportTypes = export;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
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

    public HtmlComponentFactory getComponentFactory() {
        return componentFactory;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void addColumn(HtmlColumn column) {
        table.getRow().addColumn(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        Collection v_items = (Collection) pageContext.getRequest().getAttribute(items);

        WebContext webContext = new HttpServletRequestWebContext((HttpServletRequest) pageContext.getRequest());

        CoreContextFactory factory = new CoreContextFactoryImpl(webContext);
        CoreContext coreContext = factory.createCoreContext(v_items, getLimit(v_items, webContext));

        componentFactory = new HtmlComponentFactory(webContext, coreContext);

        // create the table
        table = componentFactory.createTable();
        table.setCaption(getCaption());
        table.setTheme(getTheme());
        table.getTableRenderer().setWidth(getWidth());
        table.getTableRenderer().setBorder(getBorder());
        table.getTableRenderer().setStyle(getStyle());
        table.getTableRenderer().setStyleClass(getStyleClass());
        table.getTableRenderer().setCellpadding(getCellpadding());
        table.getTableRenderer().setCellspacing(getCellspacing());

        HtmlRow row = componentFactory.createRow();

        table.setRow(row);

        return EVAL_BODY_INCLUDE;
    }

    /**
     * get limit from the request, if there is no limit,create one.
     */
    private Limit getLimit(Collection<Object> items, WebContext webContext) {
        Limit m_limit = (Limit) pageContext.getRequest().getAttribute(limit);

        if (m_limit == null) { // in case there is no limit store in the request scope.
            logger.info("could not found limit object in the request scope.");
            LimitFactory limitFactory = new LimitFactoryImpl(getId(), webContext);
            m_limit = limitFactory.createLimit();
            limitFactory.createRowSelect(maxRows, items.size(), m_limit);
        }
        
        return m_limit;
    }

    @Override
    public int doEndTag() throws JspException {
        Toolbar toolbar = null;

        String[] exports = null;
        if (null != exportTypes && !"".equals(exportTypes)) {
            exports = exportTypes.split(",");
        }
        
        ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, componentFactory.getWebContext(), componentFactory.getCoreContext(), exports);
        toolbar = toolbarFactory.createToolbar();
        View view = new HtmlView(table, toolbar, componentFactory.getCoreContext());
        
        try {
            pageContext.getOut().write(view.render().toString());
        } catch (IOException e) {
            logger.warn("write table to responese fail!");
        }
        
        return EVAL_PAGE;
    }
}
