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
import org.jmesa.worksheet.UniqueProperty;
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
public class InputWorksheetEditor extends AbstractWorksheetEditor {
    
    /**
     * Return either the edited worksheet value, or the value of the underlying CellEditor.
     */
    public Object getValue(Object item, String property, int rowcount) {
        
        Object changedValue = null;
        
        WorksheetColumn worksheetColumn = getWorksheetColumn(item, property);
        if (worksheetColumn != null) {
            changedValue = escapeHtml(worksheetColumn.getChangedValue());
        }
        
        Object originalValue = getValueForWorksheet(item, property, rowcount);

        Limit limit = getCoreContext().getLimit();
        String id = limit.getId();
        UniqueProperty uniqueProperty = getColumn().getRow().getUniqueProperty(item);

        return getWsColumn(worksheetColumn, item, id, property, uniqueProperty.getName(), uniqueProperty.getValue(), originalValue, changedValue);
    }

    protected String getWsColumn(WorksheetColumn worksheetColumn, Object item, String id, String property, String uniqueProperty, String uniqueValue, Object originalValue, Object changedValue) {
        
        String originalStringValue = (originalValue == null ? "" : originalValue.toString().trim());
        
        if (isRowRemoved(getCoreContext().getWorksheet(), getColumn().getRow(), item)) {
            return originalStringValue;
        }
        
        HtmlBuilder html = new HtmlBuilder();

        html.div().styleClass(getStyleClass(worksheetColumn)).close();
        
        html.input().type("text");
        
        Object value = changedValue == null ? originalStringValue : changedValue;
        html.value(value == null ? "" : String.valueOf(value));
        
        html.onblur("jQuery.jmesa.submitWorksheetColumn(this, '" + id + "','" + property + "','" + uniqueProperty + "','" + uniqueValue + "','" + originalStringValue + "');");
        html.end();
        
        html.divEnd();

        return html.toString();
    }
}
