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

import java.util.Collection;

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
public interface WorksheetRow {
    /**
     * @return Map in which the map keys are the item properties and the map values are the item
     *         values.
     */
    public UniqueProperty getUniqueProperty();

    /**
     * Add a column to the worksheet row.
     * 
     * @param column The worksheet row column.
     */
    public void addColumn(WorksheetColumn column);

    /**
     * @param property The column property.
     * @return The worksheet row column.
     */
    public WorksheetColumn getColumn(String property);

    /**
     * @return All the row columns in the worksheet.
     */
    public Collection<WorksheetColumn> getColumns();

    /**
     * Remove the specified worksheet row column.
     * 
     * @param column The worksheet row column to remove.
     */
    public void removeColumn(WorksheetColumn column);

    /**
     * @return Get the row status.
     */
    public WorksheetRowStatus getRowStatus();

    /**
     * Set the row status.
     * 
     * @param rowStatus The row status.
     */
    public void setRowStatus(WorksheetRowStatus rowStatus);

    /**
     * @return Is true if any of the row columns contain errors.
     */
    public boolean hasErrors();

    /**
     * Set the backing worksheet item on the row.
     * 
     * @param item The backing worksheet item.
     */
    public void setItem(Object item);

    /**
     * @return Get the backing worksheet item.
     */
    public Object getItem();
}
