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
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jmesa.limit.Order;
import org.jmesa.util.ItemUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an HtmlColumn.
 * 
 * @since 2.1
 * @author jeff jie
 */
public class HtmlColumnTag extends SimpleTagSupport {
    private Logger logger = LoggerFactory.getLogger(HtmlColumnTag.class);

    private String property;
    private String title;
    private String titleKey;
    private boolean sortable = true;
    private String sortOrder;
    private boolean filterable = true;
    private String width;
    private String pattern;
    private String cellEditor;
    private String headerEditor;
    private String filterEditor;

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

    /**
     * @since 2.2
     * @return The sort order for the column.
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @since 2.2
     * @param sortOrder The order array.
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
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

    /**
     * @since 2.2
     * @return The header editor for the column.
     */
    public String getHeaderEditor() {
        return headerEditor;
    }

    /**
     * @since 2.2
     * @param headerEditor The header editor to use.
     */
    public void setHeaderEditor(String headerEditor) {
        this.headerEditor = headerEditor;
    }

    /**
     * @since 2.2
     * @return The filter editor for the column.
     */
    public String getFilterEditor() {
        return filterEditor;
    }

    /**
     * @since 2.2
     * @param filterEditor The filter editor to use.
     */
    public void setFilterEditor(String filterEditor) {
        this.filterEditor = filterEditor;
    }

    /**
     * Take the comma separted Order values and convert them into an Array. The legal values include
     * "none, asc, desc".
     * 
     * @since 2.2
     * @return The sort order array for the column.
     */
    protected Order[] getColumnSortOrder() {
        if (StringUtils.isBlank(sortOrder)) {
            return null;
        }

        String[] orders = StringUtils.split(sortOrder, ",");

        Order[] results = new Order[orders.length];

        for (int i = 0; i < orders.length; i++) {
            String order = orders[i];
            Order valueOfParam = Order.valueOfParam(order);
            if (valueOfParam == null) {
                throw new IllegalStateException("The sortOrder must consist of comma separated values of asc, desc, and none.");
            }
            results[i] = valueOfParam;
        }

        return results;
    }

    /**
     * <p>
     * If the cellEditor is not defined then create a BasicCellEditor.
     * </p>
     * 
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * editor. If a setPattern() method is defined on your editor and you have defined the column
     * pattern attribute on the tag it will be set on your CellEditor automatically.
     * </p>
     * 
     * @return The CellEditor to use.
     */
    protected CellEditor getColumnCellEditor() {
        CellEditor editor = null;

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

        if (StringUtils.isEmpty(getCellEditor())) {
            HtmlComponentFactory factory = facadeTag.getComponentFactory();
            editor = factory.createBasicCellEditor();
        } else {
            try {
                Object obj = Class.forName(getCellEditor()).newInstance();
                editor = (CellEditor) obj;
                SupportUtils.setPattern(editor, getPattern());
                SupportUtils.setCoreContext(editor, facadeTag.getCoreContext());
                SupportUtils.setWebContext(editor, facadeTag.getWebContext());
            } catch (Exception e) {
                logger.error("Could not create the cellEditor [" + getCellEditor() + "]", e);
            }
        }

        return editor;
    }

    /**
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * editor.
     * </p>
     * 
     * @return The HeaderEditor to use.
     */
    protected HeaderEditor getColumnHeaderEditor() {
        HeaderEditor editor = null;

        if (StringUtils.isEmpty(getHeaderEditor())) {
            return null;
        } else {
            try {
                TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
                Object obj = Class.forName(getHeaderEditor()).newInstance();
                editor = (HeaderEditor) obj;
                SupportUtils.setCoreContext(editor, facadeTag.getCoreContext());
                SupportUtils.setWebContext(editor, facadeTag.getWebContext());
            } catch (Exception e) {
                logger.error("Could not create the headerEditor [" + getHeaderEditor() + "]", e);
            }
        }

        return editor;
    }

    /**
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * editor.
     * </p>
     * 
     * @return The FilterEditor to use.
     */
    protected FilterEditor getColumnFilterEditor() {
        FilterEditor editor = null;

        if (StringUtils.isEmpty(getFilterEditor())) {
            return null;
        } else {
            try {
                TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
                Object obj = Class.forName(getFilterEditor()).newInstance();
                editor = (FilterEditor) obj;
                SupportUtils.setCoreContext(editor, facadeTag.getCoreContext());
                SupportUtils.setWebContext(editor, facadeTag.getWebContext());
            } catch (Exception e) {
                logger.error("Could not create the filterEditor [" + getFilterEditor() + "]", e);
            }
        }

        return editor;
    }

    /**
     * The column to use. If the column does not exist then one will be created.
     */
    public HtmlColumn getColumn() {
        if (column != null) {
            return column;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        HtmlComponentFactory factory = facadeTag.getComponentFactory();
        CellEditor editor = getColumnCellEditor();
        this.column = factory.createColumn(getProperty(), editor);

        HeaderEditor headerEditor = getColumnHeaderEditor();
        if (headerEditor != null) {
            SupportUtils.setColumn(headerEditor, column);
            SupportUtils.setCoreContext(headerEditor, facadeTag.getCoreContext());
            SupportUtils.setWebContext(headerEditor, facadeTag.getWebContext());
            column.getHeaderRenderer().setHeaderEditor(headerEditor);
        }

        FilterEditor filterEditor = getColumnFilterEditor();
        if (filterEditor != null) {
            SupportUtils.setColumn(filterEditor, column);
            SupportUtils.setCoreContext(filterEditor, facadeTag.getCoreContext());
            SupportUtils.setWebContext(filterEditor, facadeTag.getWebContext());
            column.getFilterRenderer().setFilterEditor(filterEditor);
        }

        HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        rowTag.getRow().addColumn(column);

        return column;
    }

    /**
     * If a ColumnTag body is defined then use it as the current page item value. If it is not
     * defined then get the page item value from the regular bean by using the property
     * 
     * @return The item to use.
     */
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
        }

        StringWriter value = new StringWriter();
        body.invoke(value);
        return value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HtmlColumn column = getColumn();
        column.setTitle(getTitle());
        column.setTitleKey(getTitleKey());
        column.setSortable(isSortable());
        column.setSortOrder(getColumnSortOrder());
        column.setFilterable(isFilterable());
        column.setWidth(getWidth());

        validate();

        HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();
        pageItem.put(getProperty(), getValue());
    }

    /**
     * Validate that the HtmlColumnTag is in an expected state.
     * 
     * @return Is true is the validation passes
     */
    protected boolean validate() {
        HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();
        if (pageItem.get(getProperty()) != null) {
            TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
            String var = facadeTag.getVar();
            String msg = "The column property [" + getProperty() + "] is not unique. One column value will overwrite another.";
            if (var.equals(getProperty())) {
                msg = "The column property [" + getProperty() + "] is the same as the TableFacadeTag var attribute [" + var + "].";
            }

            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        return true;
    }
}
