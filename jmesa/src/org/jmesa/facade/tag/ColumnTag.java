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
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.ContextSupport;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.1
 * @author jeff jie
 */
public class ColumnTag extends SimpleTagSupport {
    private Logger logger = LoggerFactory.getLogger(ColumnTag.class);

    private String property;
    private String title;
    private String titleKey;
    private boolean sortable = true;
    private boolean filterable = true;
    private String width;
    private String pattern;
    private String cellEditor;

    private HtmlColumn column;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCellEditor() {
        return cellEditor;
    }

    public void setCellEditor(String cellEditor) {
        this.cellEditor = cellEditor;
    }

    protected CellEditor getColumnCellEditor() {
        CellEditor editor = null;

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

        if (StringUtils.isEmpty(getCellEditor())) {
            HtmlComponentFactory factory = facadeTag.getComponentFactory();
            editor = factory.createBasicCellEditor();
        } else {
            try {
                Object obj = Class.forName(getCellEditor()).newInstance();
                if (pattern != null) {
                    PropertyUtils.setProperty(obj, "pattern", getPattern());
                }
                editor = (CellEditor) obj;
                if (obj instanceof ContextSupport) {
                    ((ContextSupport) editor).setCoreContext(facadeTag.getCoreContext());
                    ((ContextSupport) editor).setWebContext(facadeTag.getWebContext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Could not create the CellEditor [" + getCellEditor() + "]", e);
            }
        }

        return editor;
    }

    public HtmlColumn getColumn() {
        if (column != null) {
            return column;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        HtmlComponentFactory factory = facadeTag.getComponentFactory();
        CellEditor editor = getColumnCellEditor();
        this.column = factory.createColumn(getProperty(), editor);

        RowTag rowTag = (RowTag) findAncestorWithClass(this, RowTag.class);
        rowTag.getRow().addColumn(column);

        return column;
    }

    @SuppressWarnings("unchecked")
    public Object getValue() throws JspException, IOException {
        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        String var = facadeTag.getVar();
        Object item = getJspContext().getAttribute(var);

        if (item == null) {
            return null;
        }

        JspFragment body = getJspBody();
        if (body == null) {
            return ItemUtils.getItemValue(item, getProperty());
        } else {
            StringWriter value = new StringWriter();
            body.invoke(value);
            return value;
        }
    }

    @Override
    public void doTag() throws JspException, IOException {
        HtmlColumn column = getColumn();

        if (getTitleKey() != null) {
            column.setTitle(getTitleKey(), true);
        } else {
            column.setTitle(getTitle());
        }

        column.setSortable(isSortable());
        column.setFilterable(isFilterable());
        column.setWidth(getWidth());

        RowTag rowTag = (RowTag) findAncestorWithClass(this, RowTag.class);
        rowTag.getPageItem().put(getProperty(), getValue());
    }
}
