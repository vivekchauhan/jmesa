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
package org.jmesa.worksheet;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.core.message.Messages;

/**
 * <p>
 * A WorksheetRow contains WorksheetColumns. A row also has a given status. It can be a newly added
 * row, a modified row, or a deleted row.
 * </p>
 *
 * <p>
 * If a row is modified and then later deleted (before being saved) then it will be deleted from the
 * Worksheet. There is no reason for anyone to be aware that a row was almost created. That should
 * make sense. On the other hand if you delete a row that existed previously (ie, the row exists in
 * the database) then you have to flag the row as being deleted so that you would know to delete the
 * row in the database.
 * </p>
 *
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetRow implements Serializable {
    private Worksheet worksheet;
    private UniqueProperty uniqueProperty;
    private WorksheetRowStatus rowStatus;
    private Object item;

    private Map<String, WorksheetColumn> worksheetColumns = new LinkedHashMap<String, WorksheetColumn>();

    public WorksheetRow(UniqueProperty uniqueProperty) {
        this.uniqueProperty = uniqueProperty;
    }

    public void setWorksheet(Worksheet worksheet) {
        this.worksheet = worksheet;
    }

    /**
     * @return Map in which the map keys are the item properties and the map values are the item
     *         values.
     */
    public UniqueProperty getUniqueProperty() {
        return uniqueProperty;
    }

    /**
     * Add a column to the worksheet row.
     *
     * @param worksheetColumn The worksheet row column.
     */
    public void addColumn(WorksheetColumn worksheetColumn) {
        worksheetColumns.put(worksheetColumn.getProperty(), worksheetColumn);
        worksheetColumn.setRow(this);
    }

    /**
     * @param property The column property.
     * @return The worksheet row column.
     */
    public WorksheetColumn getColumn(String property) {
        return worksheetColumns.get(property);
    }

    /**
     * @return All the row columns in the worksheet.
     */
    public Collection<WorksheetColumn> getColumns() {
        return worksheetColumns.values();
    }

    /**
     * Remove the specified worksheet row column.
     *
     * @param column The worksheet row column to remove.
     */
    public void removeColumn(WorksheetColumn column) {
        worksheetColumns.remove(column.getProperty());
    }

    /**
     * @return Get the row status.
     */
    public WorksheetRowStatus getRowStatus() {
        return rowStatus;
    }

    /**
     * Set the row status.
     *
     * @param rowStatus The row status.
     */
    public void setRowStatus(WorksheetRowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    /**
     * @return Is true if any of the row columns contain errors.
     */
    public boolean hasErrors() {
        for (WorksheetColumn worksheetColumn : worksheetColumns.values()) {
            boolean hasError = worksheetColumn.hasError();
            if (hasError) {
                return true;
            }
        }

        return false;
    }

    /**
     * Set the backing worksheet item on the row.
     *
     * @param item The backing worksheet item.
     */
	public void setItem(Object item) {
		this.item = item;
	}

    /**
     * @return Get the backing worksheet item.
     */
	public Object getItem() {
		return item;
	}

    public Messages getMessages() {
        return worksheet.getMessages();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("uniqueProperty", uniqueProperty);
        builder.append("columns", worksheetColumns);
        return builder.toString();
    }
}
