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
package org.jmesa.view.editor;

import java.util.List;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;

/**
 * @since 2.3.2
 * @author Jeff Johnston
 */
public class GroupCellEditor extends AbstractCellEditor {
		
    private static final String LAST_GROUPED_COLUMN = "LAST_GROUPED_COLUMN";

    private Row currentRow;
    private Object lastColumnValue;
    private CellEditor decoratedCellEditor;

    public GroupCellEditor(CellEditor decoratedCellEditor) {
		
        this.decoratedCellEditor = decoratedCellEditor;
    }

    public Object getValue(Object item, String property, int rowcount) {
		
        this.currentRow = getColumn().getRow();

        Object columnValue = decoratedCellEditor.getValue(item, property, rowcount);

        boolean repeating = isRepeating(columnValue);
        this.lastColumnValue = columnValue;

        Column column = getColumn();
        boolean firstColumn = isFirstColumn(column);
        boolean previousColumnGrouped = isPreviousColumnGrouped(column);

        // reset at the first column
        if (firstColumn && !repeating) {
            setLastGroupedColumn(null);
        }

        // check for duplicates
        if ((firstColumn || previousColumnGrouped) && repeating) {
            setLastGroupedColumn(column);
            columnValue = null;
        }

        return columnValue;
    }

    private boolean isRepeating(Object columnValue) {
		
        return String.valueOf(columnValue).equals(String.valueOf(lastColumnValue));
    }

    private boolean isPreviousColumnGrouped(Column column) {
		
        Column lastGroupedColumn = getLastGroupedColumn();
        if (lastGroupedColumn == null) {
            return false;
        }

        List<Column> columns = currentRow.getColumns();
        int position = columns.indexOf(column);
        int lastGroupedColumnPosition = columns.indexOf(lastGroupedColumn);

        return lastGroupedColumnPosition == position - 1;
    }

    private boolean isFirstColumn(Column column) {
		
        Column firstColumn = null;

        List<Column> columns = currentRow.getColumns();
        for (Column col : columns) {
            if (col.getCellEditor() instanceof GroupCellEditor) {
                firstColumn = col;
                break;
            }
        }

        return column.equals(firstColumn);
    }

    private Column getLastGroupedColumn() {
		
        return (Column) getCoreContext().getAttribute(LAST_GROUPED_COLUMN);
    }

    private void setLastGroupedColumn(Column column) {
		
        getCoreContext().setAttribute(LAST_GROUPED_COLUMN, column);
    }
}
