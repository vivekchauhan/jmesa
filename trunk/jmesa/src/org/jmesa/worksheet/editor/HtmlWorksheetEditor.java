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
package org.jmesa.worksheet.editor;

import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;

/**
 * Deals with CellEditors when the the table is being used as an editable worksheet. Wraps an
 * existing CellEditor. If the cell was edited then will return the edited value, otherwise return
 * the value of the regular CellEditor.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class HtmlWorksheetEditor extends AbstractWorksheetEditor {

    /**
     * Return either the edited worksheet value, or the value of the underlying CellEditor.
     */
    public Object getValue(Object item, String property, int rowcount) {

        Object value = getChangedValue(item, property);

        if (value == null) {
            value = getCellEditor().getValue(item, property, rowcount);
        }

        return getWsColumn(value, item);
    }

    private String getWsColumn(Object columnValue, Object item) {
        HtmlBuilder html = new HtmlBuilder();

        Limit limit = getCoreContext().getLimit();

        html.div().styleClass("wsColumn");
        html.onclick(getUniquePropertiesJavaScript(item) + "createWsColumn(this, '" + limit.getId() + "', uniqueProperties, '" 
            + getColumn().getProperty() + "')");
        html.close();
        html.append(columnValue);
        html.divEnd();

        return html.toString();
    }
}
