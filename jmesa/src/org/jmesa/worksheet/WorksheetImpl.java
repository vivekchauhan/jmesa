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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.core.message.Messages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.worksheet.editor.WorksheetEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetImpl implements Worksheet {
    
    private static final Logger logger = LoggerFactory.getLogger(WorksheetImpl.class);

    private String id;
    private Messages messages;
    private int lastAddedRowId = -1;

    private Map<UniqueProperty, WorksheetRow> rows = new LinkedHashMap<UniqueProperty, WorksheetRow>();

    public WorksheetImpl(String id, Messages messages) {
        this.id = id;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public Messages getMessages() {
        return messages;
    }

    public void addRow(WorksheetRow row) {
        rows.put(row.getUniqueProperty(), row);
    }

    public void addRow(Object item, Table table) {
    	Map<String, Object> itemMap = new HashMap<String, Object>();

    	Row row = table.getRow();

    	String upName = row.getUniqueProperty();
    	if (upName == null) {
            throw new IllegalStateException("Item does not have the uniqueProperty");
    	}

        // get a random value for unique property
    	String upValue = Integer.toString(lastAddedRowId--);
    	UniqueProperty uniqueProperty = new UniqueProperty(upName, upValue);

    	WorksheetRow wsr = new WorksheetRowImpl(uniqueProperty);
        if (logger.isDebugEnabled()) {
        	logger.debug("Unique Property for added row: " + wsr.getUniqueProperty());
        }

    	// put unique property
    	itemMap.put(upName, upValue);

    	// Navigate through the columns and add either in worksheet column or in Map
    	for (Column column: row.getColumns()) {
    		HtmlColumn htmlColumn = (HtmlColumn)column;
    		String property = htmlColumn.getProperty();

			Object value = (item == null) ? null : ItemUtils.getItemValue(item, property);
			if (value == null) {
				value = "";
			}

    		itemMap.put(property, value);

    		if (htmlColumn.isEditable()) {
    			WorksheetEditor wse = (WorksheetEditor) htmlColumn.getCellRenderer().getCellEditor();
    			value = (item == null) ? null : wse.getValueForWorksheet(item, property, -1);
    			
    			if (value == null) {
    				value = "";
    			}
    			
    			WorksheetColumn wsc = new WorksheetColumnImpl(property, value.toString(), getMessages());
    			wsc.setChangedValue(value.toString());
    			wsr.addColumn(wsc);
    		}
    	}

    	// add the orginal item
        if (item != null) {
        	itemMap.put(ItemUtils.JMESA_ITEM, item);
        }
    	wsr.setRowStatus(WorksheetRowStatus.ADD);
    	wsr.setItem(itemMap);

    	addRow(wsr);
    }

    public List<WorksheetRow> getRowsByStatus(WorksheetRowStatus rowStatus) {
        List<WorksheetRow> results = new ArrayList<WorksheetRow>();

        for (WorksheetRow row: getRows()) {
    		if (rowStatus.equals(row.getRowStatus())) {
    			results.add(row);
            }
        }

    	return results;
    }

    public WorksheetRow getRow(UniqueProperty uniqueProperty) {
        return rows.get(uniqueProperty);
    }

    public Collection<WorksheetRow> getRows() {
        return rows.values();
    }

    public void removeRow(WorksheetRow row) {
        rows.remove(row.getUniqueProperty());
    }
    
    public void removeRow(UniqueProperty uniqueProperty) {
        WorksheetRow row = getRow(uniqueProperty);
        if (row != null) {
            removeRow(row);
        }
    }

    public boolean isSaving() {
        throw new UnsupportedOperationException("A request is needed to check for save logic.");
    }
    
    public boolean isFiltering() {
        throw new UnsupportedOperationException("A request is needed to check for filter logic.");
    }

    public boolean isAddingRow() {
        throw new UnsupportedOperationException("A request is needed to check for add row logic.");
    }
    
    public boolean isRemovingRow() {
        throw new UnsupportedOperationException("A request is needed to check for remove row logic.");
    }
    
    public boolean hasChanges() {
        return rows.size() > 0;
    }

    public void removeAllChanges() {
        rows.clear();
    }
    
    public boolean hasErrors() {
        Collection<WorksheetRow> worksheetRows = rows.values();
        for (WorksheetRow worksheetRow : worksheetRows) {
            boolean hasErrors = worksheetRow.hasErrors();
            if (hasErrors) {
                return true;
            }
        }
        
        return false;
    }

    public void processRows(WorksheetCallbackHandler handler) {
        Iterator<WorksheetRow> worksheetRows = getRows().iterator();
        while (worksheetRows.hasNext()) {
            WorksheetRow worksheetRow = worksheetRows.next();
            handler.process(worksheetRow);
            
            Iterator<WorksheetColumn> worksheetColumns = worksheetRow.getColumns().iterator();
            while (worksheetColumns.hasNext()) {
                WorksheetColumn worksheetColumn = worksheetColumns.next();
                if (!worksheetColumn.hasError()) {
                    worksheetColumns.remove();
                }
            }
            
            if (worksheetRow.getColumns().size() == 0) {
                worksheetRows.remove();
            }
        }
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("rows", rows);
        return builder.toString();
    }
    
    public Worksheet getWorksheetImpl() {
        return this;
    }
}
