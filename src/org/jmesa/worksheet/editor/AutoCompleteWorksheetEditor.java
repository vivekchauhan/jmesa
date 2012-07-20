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
package org.jmesa.worksheet.editor;

import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;

/**
 * Defines autocomplete functionality for the worksheet editor.
 * 
 * @since 3.0.4
 * @author Jeff Johnston
 */
public class AutoCompleteWorksheetEditor extends InputWorksheetEditor {

    @Override
    protected String getWsColumn(WorksheetColumn worksheetColumn, Object item, String id, String property, String uniqueProperty, String uniqueValue, Object originalValue, Object changedValue) {
        
        HtmlBuilder html = new HtmlBuilder();

        html.div().styleClass(getStyleClass(worksheetColumn)).close();

        html.input().type("text");
        html.name("autocomplete_" + property);
        
        if (changedValue == null) {
            html.value(String.valueOf(originalValue));
        } else {
            html.value(String.valueOf(changedValue));
        }
        
        html.onblur("jQuery.jmesa.submitWorksheetColumn(this, '" + id + "','" + property + "','" + uniqueProperty + "','" + uniqueValue + "','" + originalValue + "');");
        html.end();
        
        html.divEnd();

        return html.toString();
    }
}

