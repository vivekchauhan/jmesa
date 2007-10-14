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
import java.util.List;
import java.util.Map;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetRowImpl implements WorksheetRow {
    private Map<String, Object> uniqueProperties;
    private WorksheetRowStatus rowStatus;

    private List<WorksheetColumn> columns = new ArrayList();
    
    public WorksheetRowImpl(Map<String, Object> uniqueProperties) {
        this.uniqueProperties = uniqueProperties;
    }

    public Map<String, Object> getUniqueProperties() {
        return uniqueProperties;
    }

    public void addColumn(WorksheetColumn column) {
        columns.add(column);
    }

    public WorksheetColumn getColumn(String property) {
        for (WorksheetColumn column : columns) {
            if (column.getProperty().equals(property)) {
                return column;
            }
        }

        return null;
    }

    public List<WorksheetColumn> getColumns() {
        return columns;
    }

    public void removeColumn(WorksheetColumn column) {
        columns.remove(column);
    }

    public WorksheetRowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(WorksheetRowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
}
