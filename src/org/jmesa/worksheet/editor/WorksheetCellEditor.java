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

import java.util.Map;

import org.jmesa.limit.Limit;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.state.WorksheetState;

/**
 * Deals with CellEditors when the the table is being used as an editable worksheet. Wraps an
 * existing CellEditor. If the cell was edited then will return the edited value, otherwise return
 * the value of the regular CellEditor.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetCellEditor extends AbstractContextSupport implements CellEditor {
    private CellEditor cellEditor;
    private Column column;

    /**
     * @param cellEditor The CellEditor to wrap.
     */
    public void setCellEditor(CellEditor cellEditor) {
        this.cellEditor = cellEditor;
    }

    /**
     * @param column The current column.
     */
    public void setColumn(Column column) {
        this.column = column;
    }

    /**
     * Return either the edited worksheet value, or the value of the underlying CellEditor.
     */
    public Object getValue(Object item, String property, int rowcount) {
        WorksheetState worksheetState = getCoreContext().getWorksheetState();
        Worksheet worksheet = worksheetState.retrieveWorksheet();
        if (worksheet != null) {
            Map<String, ?> uniqueProperties = column.getRow().getUniqueProperties(item);
            if (uniqueProperties != null) {
                WorksheetRow worksheetRow = worksheet.getRow(uniqueProperties);
                if (worksheetRow != null) {
                    WorksheetColumn worksheetColumn = worksheetRow.getColumn(property);
                    if (worksheetColumn != null) {
                        String changedValue = worksheetColumn.getChangedValue();
                        return getWsColumn(changedValue, item);
                    }
                }
            }
        }

        Object value = cellEditor.getValue(item, property, rowcount);

        return getWsColumn(value, item);
    }

    private String getWsColumn(Object columnValue, Object item) {
        HtmlBuilder html = new HtmlBuilder();

        Limit limit = getCoreContext().getLimit();

        html.div().styleClass("wsColumn");
        html.onmouseover("if ($('#wsColumnDiv').size() > 0){return;};$(this).parent().css('border-color', '#605a54')");
        html.onmouseout("$(this).parent().removeAttr('style')");
        html.onclick(getUniqueProperties(item) + "createWsColumn(this, '" + limit.getId() + "', uniqueProperties, '" + column.getProperty() + "')");
        html.close();
        html.append(columnValue);
        html.divEnd();

        return html.toString();
    }

    private String getUniqueProperties(Object item) {
        StringBuilder sb = new StringBuilder();

        Row row = column.getRow();
        Map<String, ?> uniqueProperties = row.getUniqueProperties(item);

        sb.append("var uniqueProperties = {};");

        for (String key : uniqueProperties.keySet()) {
            Object value = uniqueProperties.get(key);
            sb.append("uniqueProperties['" + key + "']='" + value + "';");
        }

        return sb.toString();
    }
}
