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
import org.jmesa.view.component.Row;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.state.WorksheetState;

/**
 * The abstraction for the worksheet editors. Custom implementations should extend this.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public abstract class AbstractWorksheetEditor extends AbstractCellEditor implements WorksheetEditor {
    protected String UNIQUE_PROPERTIES = "up";

    private CellEditor cellEditor;
    
    /**
     * @return The wrapped CellEditor.
     */
    public CellEditor getCellEditor() {
        return cellEditor;
    }

    /**
     * @param cellEditor The CellEditor to wrap.
     */
    public void setCellEditor(CellEditor cellEditor) {
        this.cellEditor = cellEditor;
    }
    
    protected String getChangedValue(Object item, String property) {
        WorksheetState worksheetState = getCoreContext().getWorksheetState();
        Worksheet worksheet = worksheetState.retrieveWorksheet();
        if (worksheet == null) {
            return null;
        }
        
        Map<String, ?> uniqueProperties = getColumn().getRow().getUniqueProperties(item);
        WorksheetRow worksheetRow = worksheet.getRow(uniqueProperties);

        if (worksheetRow == null) {
            return null;
        }
        
        WorksheetColumn worksheetColumn = worksheetRow.getColumn(property);
        if (worksheetColumn != null) {
            return worksheetColumn.getChangedValue();
        }
        
        return null;
    }
    
    /**
     * @param item The Bean or Map.
     * @return The JavaScript for the unique properties.
     */
    protected String getUniquePropertiesJavaScript(Object item) {
        StringBuilder sb = new StringBuilder();

        Row row = getColumn().getRow();
        Map<String, ?> uniqueProperties = row.getUniqueProperties(item);

        sb.append("var " + UNIQUE_PROPERTIES + " = {};");

        for (String key : uniqueProperties.keySet()) {
            Object value = uniqueProperties.get(key);
            sb.append(UNIQUE_PROPERTIES + "['" + key + "']='" + value + "';");
        }

        return sb.toString();
    }
}
