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

import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
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
            WorksheetRow worksheetRow = worksheet.getRow(column.getRow().getUniqueProperties(item));
            if (worksheetRow != null) {
                WorksheetColumn worksheetColumn = worksheetRow.getColumn(property);
                if (worksheetColumn != null) {
                    return worksheetColumn.getChangedValue();
                }
            }
        }

        return cellEditor.getValue(item, property, rowcount);
    }
}
