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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author jeff jie
 */
public class ColumnTag extends BodyTagSupport {
    private Logger logger = LoggerFactory.getLogger(ColumnTag.class);

    private String property;
    private String title;
    private boolean sortable = true;
    private boolean filterable = true;
    private String width;
    private HtmlColumn column;

    private List<String> attributes = new ArrayList<String>();

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String name) {
        this.property = name;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getTitle() {
        return title == null ? property : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
    public void addAttribute(String name) {
        attributes.add(name);
    }

    public int doEndTag() throws JspException {
        TableTag table = (TableTag) getParent();

        if (getBodyContent() != null && !"".equals(getBodyContent().getString())) {
            HtmlRemarkerCellEditor editor = new HtmlRemarkerCellEditor();
            String body = getBodyContent().getString();
            editor.setBody(body);
            column = table.getComponentFactory().createColumn(getProperty(), editor);
        } else {
            column = table.getComponentFactory().createColumn(getProperty(), new BasicCellEditor());
        }
        column.setTitle(getTitle());
        column.setFilterable(isFilterable());
        column.setSortable(isSortable());
        column.setWidth(getWidth());

        table.addColumn(column);
        return EVAL_PAGE;
    }

    public class HtmlRemarkerCellEditor implements CellEditor {
        private String body;

        public void setBody(String body) {
            this.body = body;
        }

        public Object getValue(Object item, String property, int rowcount) {
            String ret = null;
            if (body == null) {// bodyContent is null,display the spicific
                                // property's value.
                if (property != null) {
                    try {
                        return PropertyUtils.getProperty(item, property);
                    } catch (Exception e) {
                        logger.warn("item class " + item.getClass().getName() + " doesn't have property " + property);
                    }
                }
            }
            ret = body;
            for (String attr : attributes) {
                Object itemValue = "";
                try {
                    itemValue = PropertyUtils.getProperty(item, attr);
                    ret = replace(ret, "#{" + attr + "}", itemValue.toString());
                } catch (Exception e) {
                    logger.warn("item class " + item.getClass().getName() + " doesn't have property " + property);
                    ret = replace(ret, "#{" + attr + "}", "");
                }
            }
            return ret;
        }
    }

    public String replace(String src, String regex, String replacement) {
        StringBuffer sb = new StringBuffer(src);
        while (sb.indexOf(regex) != -1) {
            int index = sb.indexOf(regex);
            sb.insert(index, replacement);
            sb.delete(index + replacement.length(), index + replacement.length() + regex.length());
        }
        return sb.toString();
    }
}
