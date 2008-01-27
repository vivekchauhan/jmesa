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

    private Map<Map<String, ?>, WorksheetRow> rows = new HashMap<Map<String, ?>, WorksheetRow>();

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
        rows.put(row.getUniqueProperties(), row);
    }

    public WorksheetRow getRow(Map<String, ?> uniqueProperties) {
        return rows.get(uniqueProperties);
    }

    public Collection<WorksheetRow> getRows() {
        return rows.values();
    }

    public void removeRow(WorksheetRow row) {
        rows.remove(row.getUniqueProperties());
    }
    
    public boolean isSaving() {
        throw new UnsupportedOperationException("A request is needed to check for save logic.");
    }

    public boolean hasChanges() {
        return rows.size() > 0;
    }

    public void removeAllChanges() {
        rows.clear();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("rows", rows);
        return builder.toString();
    }
}
