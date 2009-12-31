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

import org.jmesa.core.message.Messages;

/**
 * <p>
 * The Worksheet represents what the user changed on the table. It will contain WorksheetRows which
 * contain WorksheetColumns. A WorksheetColumn represents the edited HtmlColumn. As a developer you
 * will use the Worksheet to know what was modified, added, and deleted. You will also have a way to
 * add an error to individual columns.
 * </p>
 * 
 * <p>
 * To get this functionality you will have to tell the TableFacade that it is editable.
 * </p>
 * 
 * <pre>
 * tableFacade.setEditable(true);
 * </pre>
 * 
 * <p>
 * You will also have to add the unique item properties to the HtmlRow.
 * </p>
 * 
 * <pre>
 * htmlRow.setUniqueProperty(&quot;id&quot;);
 * </pre>
 * 
 * <p>
 * In this example "id" is the item property that is used to uniquely identify the row.
 * </p>
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public interface Worksheet {
    /**
     * @return The worksheet id, which is the same as the table id.
     */
    public String getId();

    /**
     * @return The messages to use.
     */
    public Messages getMessages();

    /**
     * @param uniqueProperty The property that uniquely identifies this row.
     * @return The worksheet row.
     */
    public WorksheetRow getRow(UniqueProperty uniqueProperty);

    /**
     * Add a row to the worksheet.
     * 
     * @param row The worksheet row.
     */
    public void addRow(WorksheetRow row);

    /**
     * @return All the rows in the worksheet.
     */
    public Collection<WorksheetRow> getRows();

    /**
     * Remove the specified worksheet row.
     * 
     * @param row The worksheet row to remove.
     */
    public void removeRow(WorksheetRow row);
    
    /**
     * @return Is true if the user is requesting that the worksheet be saved.
     */
    public boolean isSaving();

    /**
     * @return Is true if the user is requesting that the worksheet filter changes.
     */
    public boolean isFiltering();
    
    /**
     * @return Is true if the worksheet contains changes, which really means the worksheet is
     *         populated.
     */
    public boolean hasChanges();

    /**
     * Remove all the changes from the worksheet...clears the worksheet.
     */
    public void removeAllChanges();
    
    /**
     * @return Is true if any of the worksheet columns contain errors.
     */
    public boolean hasErrors();

    /**
     * A convenience method to handle the worksheet iteration.
     * 
     * @param handler The callback handler.
     */
    public void processRows(WorksheetCallbackHandler handler);
}
