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

import static org.jmesa.model.tag.TagUtils.createInstance;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.jmesa.core.CoreContext;
import org.jmesa.util.ItemUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlColumnsGenerator;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.web.WebContext;

/**
 * Used to generate columns on the fly for the tag library.
 * 
 * @since 2.2.1
 * @author Jeff Johnston
 */
public class HtmlColumnsTag extends SimpleTagSupport {

    private String htmlColumnsGenerator;

    public String getHtmlColumnsGenerator() {
        return htmlColumnsGenerator;
    }

    public void setHtmlColumnsGenerator(String htmlColumnsGenerator) {
        this.htmlColumnsGenerator = htmlColumnsGenerator;
    }

    private Object getValue(String property, String var) {
        Object item = getJspContext().getAttribute(var);

        if (item == null) {
            return null;
        }

        return ItemUtils.getItemValue(item, property);
    }

    /**
     * @return The list of columns generated on the fly.
     */
    private List<HtmlColumn> getColumns(WebContext webContext, CoreContext coreContext) {
        HtmlColumnsGenerator columnsGenerator = (HtmlColumnsGenerator) createInstance(getHtmlColumnsGenerator());
        SupportUtils.setWebContext(columnsGenerator, webContext);
        SupportUtils.setCoreContext(columnsGenerator, coreContext);

        return columnsGenerator.getColumns();
    }

    /**
     * Process the list of columns that are generated on the fly.
     */
    @Override
    public void doTag() {
        TableModelTag facadeTag = (TableModelTag) findAncestorWithClass(this, TableModelTag.class);
        Collection<Map<String, Object>> pageItems = facadeTag.getPageItems();
        if (pageItems.size() == 1) {
            HtmlRow row = facadeTag.getTable().getRow();
            WebContext webContext = facadeTag.getTableFacade().getWebContext();
            CoreContext coreContext = facadeTag.getTableFacade().getCoreContext();
            List<HtmlColumn> columns = getColumns(webContext, coreContext);
            for (HtmlColumn column : columns) {
                column.setGeneratedOnTheFly(true);
                TagUtils.validateColumn(this, column.getProperty());
                row.addColumn(column);
            }
        }

        HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();

        HtmlRow row = facadeTag.getTable().getRow();
        List<Column> columns = row.getColumns();
        for (Column column : columns) {
            HtmlColumn htmlColumn = (HtmlColumn) column;
            if (htmlColumn.isGeneratedOnTheFly()) {
                String property = htmlColumn.getProperty();
                if (property != null) {
                    String var = facadeTag.getVar();
                    pageItem.put(property, getValue(property, var));
                }
            }
        }
    }
}
