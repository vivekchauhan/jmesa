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
package org.jmesaweb.controller;

import org.jmesa.worksheet.editor.AbstractWorksheetEditor;
import org.jmesa.worksheet.editor.WorksheetEditor;

/**
 * @author Jeff Johnston
 */
public class ProxyWorksheetEditor extends AbstractWorksheetEditor {

    private WorksheetEditor decoratedWorksheetEditor;

    public ProxyWorksheetEditor(WorksheetEditor decoratedWorksheetEditor) {
        this.decoratedWorksheetEditor = decoratedWorksheetEditor;
    }

    public Object getValue(Object item, String property, int rowcount) {

        Object value = getCellEditor().getValue(item, property, rowcount);
        if (String.valueOf(value).contains("Lawyer")) {
            return value;
        }

        return decoratedWorksheetEditor.getValue(item, property, rowcount);
    }
}
