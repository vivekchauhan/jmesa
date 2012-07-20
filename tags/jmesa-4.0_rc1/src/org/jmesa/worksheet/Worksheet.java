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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesSupport;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;
import org.jmesa.worksheet.editor.WorksheetEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The Worksheet represents what the user changed on the table. It will contain WorksheetRows which
 * contain WorksheetColumns. A WorksheetColumn represents the edited HtmlColumn. As a developer you
 * will use the Worksheet to know what was modified, added, and deleted. You will also have a way to
 * add an error to individual columns.
 * </p>
 *
 * <p>
 * To get this functionality you will have to tell the TableModel that it is editable.
 * </p>
 *
 * <pre>
 * tableModel.setEditable(true);
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
public class Worksheet implements WebContextSupport, MessagesSupport, Serializable {
		
    private static final Logger logger = LoggerFactory.getLogger(Worksheet.class);

    public static final String SAVE_WORKSHEET = "sw_";
    public static final String FILTER_WORKSHEET = "fw_";
    public static final String ADD_WORKSHEET_ROW = "awr_";
    public static final String REMOVE_WORKSHEET_ROW = "rwr_";

    private String id;
    private transient WebContext webContext;
    private transient Messages messages;
    private int lastAddedRowId = -1;

    private Map<UniqueProperty, WorksheetRow> worksheetRows = new LinkedHashMap<UniqueProperty, WorksheetRow>();

    public Worksheet(String id) {
		
        this.id = id;
    }

    /**
     * @return The worksheet id, which is the same as the table id.
     */
    public String getId() {
		
        return id;
    }

    public WebContext getWebContext() {
		
        return webContext;
    }

    public void setWebContext(WebContext webContext) {
		
        this.webContext = webContext;
    }

    public Messages getMessages() {
		
        return messages;
    }

    public void setMessages(Messages messages) {
		
        this.messages = messages;
    }

    public void addRow(WorksheetRow worksheetRow) {
		
        worksheetRows.put(worksheetRow.getUniqueProperty(), worksheetRow);
        worksheetRow.setWorksheet(this);
    }

    /**
     * Add a new object to the worksheet.
     *
     * @param item The bean or map.
     * @param table The worksheet table.
     */
    public void addRow(Object item, Table table) {
		
    	Map<String, Object> itemMap = new HashMap<String, Object>();

    	Row row = table.getRow();

    	String upName = row.getUniqueProperty();
    	if (upName == null) {
            throw new IllegalStateException("Item does not have the uniqueProperty");
    	}

        // generate a unique value
    	String upValue = Integer.toString(lastAddedRowId--);
    	UniqueProperty uniqueProperty = new UniqueProperty(upName, upValue);

    	WorksheetRow worksheetRow = new WorksheetRow(uniqueProperty);
        if (logger.isDebugEnabled()) {
        	logger.debug("Unique Property for added row: " + worksheetRow.getUniqueProperty());
        }

    	// Navigate through the columns and add in Map (and in editable column)
    	for (Column column: row.getColumns()) {
    		HtmlColumn htmlColumn = (HtmlColumn)column;
    		String property = htmlColumn.getProperty();

			Object value = (item == null) ? null : ItemUtils.getItemValue(item, property);
    		itemMap.put(property, value);

    		if (htmlColumn.isEditable()) {
    			WorksheetEditor worksheetEditor = (WorksheetEditor) htmlColumn.getCellEditor();
    			value = (item == null) ? null : worksheetEditor.getValueForWorksheet(item, property, -1);

    			if (value == null) {
    				value = "";
    			}

    			WorksheetColumn worksheetColumn = new WorksheetColumn(property, value.toString());
    			worksheetColumn.setChangedValue(value.toString());
    			worksheetRow.addColumn(worksheetColumn);
    		}
    	}

    	// add the orginal item
        if (item != null) {
        	itemMap.put(ItemUtils.JMESA_ITEM, item);
        }

        // put unique property (overwrite if displayed in table)
        itemMap.put(upName, upValue);

    	worksheetRow.setRowStatus(WorksheetRowStatus.ADD);
    	worksheetRow.setItem(itemMap);

    	addRow(worksheetRow);
    }

    /**
     * Returns the list of worksheet rows by the given row status
     *
     * @param rowStatus The worksheet row status.
     */
    public List<WorksheetRow> getRowsByStatus(WorksheetRowStatus rowStatus) {
		
        List<WorksheetRow> results = new ArrayList<WorksheetRow>();

        for (WorksheetRow worksheetRow: getRows()) {
    		if (rowStatus.equals(worksheetRow.getRowStatus())) {
    			results.add(worksheetRow);
            }
        }

    	return results;
    }

    /**
     * @param uniqueProperty The property that uniquely identifies this row.
     */
    public WorksheetRow getRow(UniqueProperty uniqueProperty) {
		
        return worksheetRows.get(uniqueProperty);
    }

     /**
     * @return All the rows in the worksheet.
     */
    public Collection<WorksheetRow> getRows() {
		
        return worksheetRows.values();
    }

    /**
     * Remove the specified worksheet row.
     *
     * @param worksheetRow The worksheet row to remove.
     */
    public void removeRow(WorksheetRow worksheetRow) {
		
        worksheetRows.remove(worksheetRow.getUniqueProperty());
        worksheetRow.removeError();
    }

    /**
     * Remove the specified worksheet row.
     *
     * @param uniqueProperty The unique property to recognize the row.
     */
    public void removeRow(UniqueProperty uniqueProperty) {
		
        WorksheetRow worksheetRow = getRow(uniqueProperty);
        if (worksheetRow != null) {
            removeRow(worksheetRow);
        }
    }

    /**
     * @return Is true if the user is requesting that the worksheet be saved.
     */
    public boolean isSaving() {
		
        String value = webContext.getParameter(getId()  + "_" + SAVE_WORKSHEET);
        return StringUtils.isNotEmpty(value) && "true".equals(value);
    }

    /**
     * @return Is true if the user is requesting to add a row in worksheet.
     */
    public boolean isAddingRow() {
		
        String value = webContext.getParameter(getId()  + "_" + ADD_WORKSHEET_ROW);
        return StringUtils.isNotEmpty(value) && "true".equals(value);
    }

    /**
     * @return Is true if the user is requesting to remove a row from worksheet.
     */
    public boolean isRemovingRow() {
		
        String value = webContext.getParameter(getId()  + "_" + REMOVE_WORKSHEET_ROW);
        return StringUtils.isNotEmpty(value);
    }

    /**
     * @return Is true if the user is requesting that the worksheet filter changes.
     */
    public boolean isFiltering() {
		
        String value = webContext.getParameter(getId()  + "_" + FILTER_WORKSHEET);
        return StringUtils.isNotEmpty(value) && "true".equals(value);
    }

    /**
     * @return Is true if the worksheet contains changes, which really means the worksheet is
     *         populated.
     */
    public boolean hasChanges() {
		
        return worksheetRows.size() > 0;
    }

    /**
     * Remove all the changes from the worksheet...clears the worksheet.
     */
    public void removeAllChanges() {
		
        worksheetRows.clear();
    }

    /**
     * @return Is true if any of the worksheet columns contain errors.
     */
    public boolean hasErrors() {
		
        for (WorksheetRow worksheetRow : worksheetRows.values()) {
            if (worksheetRow.hasError()) {
                return true;
            }

            boolean hasErrors = worksheetRow.hasColumnErrors();
            if (hasErrors) {
                return true;
            }
        }

        return false;
    }

    /**
     * A convenience method to handle the worksheet iteration. Be sure to clear any
     * previous row and column errors that no longer exist so that the worksheet row
     * is removed from the worksheet.
     *
     * @param handler The callback handler.
     */
    public void processRows(WorksheetCallbackHandler handler) {
		
        Iterator<WorksheetRow> iterator = getRows().iterator();
        while (iterator.hasNext()) {
            WorksheetRow worksheetRow = iterator.next();
            handler.process(worksheetRow);

            if (worksheetRow.hasError() || worksheetRow.hasColumnErrors()) {
                continue;
            }

            Iterator<WorksheetColumn> worksheetColumns = worksheetRow.getColumns().iterator();
            while (worksheetColumns.hasNext()) {
                WorksheetColumn worksheetColumn = worksheetColumns.next();
                if (!worksheetColumn.hasError()) {
                    worksheetColumns.remove();
                }
            }

            if (worksheetRow.getColumns().isEmpty()) {
                iterator.remove();
            }
        }
    }

    @Override
    public String toString() {
		
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("rows", worksheetRows);
        return builder.toString();
    }
}
