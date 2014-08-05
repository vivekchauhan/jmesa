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

import org.jmesa.view.component.Row;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;

/**
 * The abstraction for the worksheet editors. Custom implementations should extend this.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public abstract class AbstractWorksheetEditor extends AbstractCellEditor implements WorksheetEditor {
		
    protected String UNIQUE_PROPERTY = "up";

    private CellEditor cellEditor;
    
    /**
     * @return The wrapped CellEditor.
     */
    @Override
    public CellEditor getCellEditor() {
		
        return cellEditor;
    }

    /**
     * @param cellEditor The CellEditor to wrap.
     */
    @Override
    public void setCellEditor(CellEditor cellEditor) {
		
        this.cellEditor = cellEditor;
    }
    
    @Override
    public Object getValueForWorksheet(Object item, String property, int rowcount) {
		
    	return getCellEditor().getValue(item, property, rowcount);
    }

    /**
     * @param item The Bean or Map.
     * @param property The column property.
     * @return The WorksheetColumn for this column.
     */
    protected WorksheetColumn getWorksheetColumn(Object item, String property) {
		
        Worksheet worksheet = getCoreContext().getWorksheet();
        if (worksheet == null) {
            return null;
        }
        
        UniqueProperty uniqueProperty = getColumn().getRow().getUniqueProperty(item);
        WorksheetRow worksheetRow = worksheet.getRow(uniqueProperty);

        if (worksheetRow == null) {
            return null;
        }
        
        return worksheetRow.getColumn(property);
    }
    
    /**
     * @param item The Bean or Map.
     * @return The JavaScript for the unique properties.
     */
    protected String getUniquePropertyJavaScript(Object item) {
		
        Row row = getColumn().getRow();
        UniqueProperty uniqueProperty = row.getUniqueProperty(item);
        if (uniqueProperty == null) {
            throw new IllegalStateException("The row unique property value is null! You need to specify the uniqueProperty on the row.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("var ").append(UNIQUE_PROPERTY).append(" = {};");
        sb.append(UNIQUE_PROPERTY).append("['").append(uniqueProperty.getName()).append("']='").append(uniqueProperty.getValue()).append("';");
        return sb.toString();
    }

    protected String getStyleClass(WorksheetColumn worksheetColumn) {
		
        if (worksheetColumn != null) {
        	String originalValue = worksheetColumn.getOriginalValue();
            if (worksheetColumn.hasError()) {
               return "worksheetColumnError";
            } else {
            	if (originalValue.equals(worksheetColumn.getChangedValue())) {
                    return "worksheetColumn";
                } else {
                    return "worksheetColumnChange";
                }
            }
        }
        
        return "worksheetColumn";
    }
}
