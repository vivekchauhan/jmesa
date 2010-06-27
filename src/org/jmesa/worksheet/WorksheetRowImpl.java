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
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetRowImpl implements WorksheetRow {
    private UniqueProperty uniqueProperty;
    private WorksheetRowStatus rowStatus;
    private Object item;
    
    private Map<String, WorksheetColumn> columns = new LinkedHashMap<String, WorksheetColumn>();

    public WorksheetRowImpl(UniqueProperty uniqueProperty) {
        this.uniqueProperty = uniqueProperty;
    }

    public UniqueProperty getUniqueProperty() {
        return uniqueProperty;
    }

    public void addColumn(WorksheetColumn column) {
        columns.put(column.getProperty(), column);
    }

    public WorksheetColumn getColumn(String property) {
        return columns.get(property);
    }

    public Collection<WorksheetColumn> getColumns() {
        return columns.values();
    }

    public void removeColumn(WorksheetColumn column) {
        columns.remove(column.getProperty());
    }

    public WorksheetRowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(WorksheetRowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    public boolean hasErrors() {
        Collection<WorksheetColumn> values = columns.values();
        for (WorksheetColumn worksheetColumn : values) {
            boolean hasError = worksheetColumn.hasError();
            if (hasError) {
                return true;
            }
        }

        return false;
    }

	public void setItem(Object item) {
		this.item = item;
	}
	
	public Object getItem() {
		return item;
	}
	
    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("uniqueProperty", uniqueProperty);
        builder.append("columns", columns);
        return builder.toString();
    }
}
