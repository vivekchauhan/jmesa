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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.view.View;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.JspPageWebContext;
import org.jmesa.web.WebContext;

/**
 * @since 2.1
 * @author jeff jie
 */
public class TableTag extends SimpleTagSupport {
    // core attributes
    private String id;
    private Collection<Object> items;
    private String var;
    private int maxRows = 15;
    private Limit limit;
    private boolean performFilterAndSort = true;
    private String caption;
    private String exportTypes;

    // style attributes
    private String theme;
    private String style;
    private String styleClass;
    private String width;
    private String border;
    private String cellpadding;
    private String cellspacing;

    // other attributes
    WebContext webContext;
    CoreContext coreContext;
    HtmlComponentFactory componentFactory;
    HtmlTable table;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<Object> getItems() {
        return items;
    }

    public void setItems(Collection<Object> items) {
        this.items = items;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public Limit getLimit() {
        if (limit != null) {
            return limit;
        }

        LimitFactory limitFactory = new LimitFactoryImpl(getId(), getWebContext());
        this.limit = limitFactory.createLimit();
        limitFactory.createRowSelect(getMaxRows(), getItems().size(), limit);

        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public boolean isPerformFilterAndSort() {
        return performFilterAndSort;
    }

    public void setPerformFilterAndSort(boolean performFilterAndSort) {
        this.performFilterAndSort = performFilterAndSort;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getExportTypes() {
        return exportTypes;
    }

    public void setExportTypes(String exportTypes) {
        this.exportTypes = exportTypes;
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

    public WebContext getWebContext() {
        if (webContext != null) {
            return webContext;
        }

        this.webContext = new JspPageWebContext((PageContext) getJspContext());

        return webContext;
    }

    public CoreContext getCoreContext() {
        if (coreContext != null) {
            return coreContext;
        }

        CoreContextFactory factory = new CoreContextFactoryImpl(isPerformFilterAndSort(), getWebContext());
        this.coreContext = factory.createCoreContext(getItems(), getLimit());

        return coreContext;
    }

    public HtmlComponentFactory getComponentFactory() {
        if (componentFactory != null) {
            return componentFactory;
        }

        this.componentFactory = new HtmlComponentFactory(getWebContext(), getCoreContext());

        return componentFactory;
    }

    public HtmlTable getTable() {
        if (table != null) {
            return table;
        }

        this.table = getComponentFactory().createTable();

        return table;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HtmlTable table = getTable();
        table.setCaption(getCaption());
        table.setTheme(getTheme());
        table.getTableRenderer().setWidth(getWidth());
        table.getTableRenderer().setStyle(getStyle());
        table.getTableRenderer().setStyleClass(getStyleClass());
        table.getTableRenderer().setBorder(getBorder());
        table.getTableRenderer().setCellpadding(getCellpadding());
        table.getTableRenderer().setCellspacing(getCellspacing());

        JspFragment body = getJspBody();
        if (body == null) {
            return;
        }

        // for (Iterator<Object> iterator = getItems().iterator();
        // iterator.hasNext();) {
        // Object item = iterator.next();
        // getWebContext().setPageAttribute(getVar(), item);
        // }
        body.invoke(null);

        String[] exportTypes = StringUtils.split(getExportTypes(), ",");
        ToolbarFactoryImpl toolbarFactory = new ToolbarFactoryImpl(table, getWebContext(), getCoreContext(), exportTypes);
        toolbarFactory.enableSeparators(true); // TODO: this could be an
                                                // attribute
        Toolbar toolbar = toolbarFactory.createToolbar();
        View view = new HtmlView(table, toolbar, getCoreContext());

        String html = view.render().toString();
        getJspContext().getOut().print(html);
    }
}
