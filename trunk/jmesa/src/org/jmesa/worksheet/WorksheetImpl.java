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

import java.util.List;
import java.util.Map;

import org.jmesa.core.message.Messages;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetImpl implements Worksheet {

    @Override
    public String getId() {
        return null;
    }

    public Messages getMessages() {
        return null;
    }

    public void setMessages(Messages messages) {

    }

    public void addRow(WorksheetRow row) {
    }

    public WorksheetRow getRow(Map<String, Object> uniqueProperties) {
        return null;
    }

    public List<WorksheetRow> getRows() {
        return null;
    }

    public void removeRow(WorksheetRow row) {
    }

    public boolean hasChanges() {
        return false;
    }

    public void removeAllChanges() {
    }
}
