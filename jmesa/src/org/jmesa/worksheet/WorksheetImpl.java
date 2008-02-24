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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.core.message.Messages;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetImpl implements Worksheet {
    
    private String id;
    private Messages messages;

    private Map<UniqueProperty, WorksheetRow> rows = new HashMap<UniqueProperty, WorksheetRow>();

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

    public WorksheetRow getRow(UniqueProperty uniqueProperty) {
        return rows.get(uniqueProperty);
    }

    public Collection<WorksheetRow> getRows() {
        return rows.values();
    }

    public void removeRow(WorksheetRow row) {
        rows.remove(row.getUniqueProperty());
    }
    
    public boolean isSaving() {
        throw new UnsupportedOperationException("A request is needed to check for save logic.");
    }
    
    public boolean isFiltering() {
        throw new UnsupportedOperationException("A request is needed to check for filter logic.");
    }

    public boolean hasChanges() {
        return rows.size() > 0;
    }

    public void removeAllChanges() {
        rows.clear();
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
}
