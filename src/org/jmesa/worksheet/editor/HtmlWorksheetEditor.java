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

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;
import static org.jmesa.worksheet.WorksheetUtils.isRowRemoved;

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
        
        Object value = null;
        
        WorksheetColumn worksheetColumn = getWorksheetColumn(item, property);
        if (worksheetColumn != null) {
            value = escapeHtml(worksheetColumn.getChangedValue());
        } else {
            value = getValueForWorksheet(item, property, rowcount);
        }

        return getWsColumn(worksheetColumn, value, item);
    }

    protected String getWsColumn(WorksheetColumn worksheetColumn, Object value, Object item) {
        if (isRowRemoved(getCoreContext().getWorksheet(), getColumn().getRow(), item)) {
            if (value == null) {
                return "";
            }
            return value.toString();
        }
    	
        HtmlBuilder html = new HtmlBuilder();

        Limit limit = getCoreContext().getLimit();

        html.div();

        html.append(getStyleClass(worksheetColumn));
        
        html.onmouseover("$.jmesa.setTitle(this, event)");
        html.onclick(getUniquePropertyJavaScript(item) + "$.jmesa.createWsColumn(this, '" + limit.getId() + "'," + UNIQUE_PROPERTY + ",'" 
            + getColumn().getProperty() + "')");
        html.close();
        html.append(value);
        html.divEnd();

        return html.toString();
    }
}
