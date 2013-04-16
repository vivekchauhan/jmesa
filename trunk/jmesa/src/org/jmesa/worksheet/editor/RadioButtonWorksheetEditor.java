/*
 * Copyright 2012 original author or authors.
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

import static org.jmesa.worksheet.WorksheetUtils.isRowRemoved;

import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;

/**
 * Defines a radio button for the worksheet editor.
 * 
 * @since 4.0
 * @author Jason Ward
 */

public class RadioButtonWorksheetEditor extends InputWorksheetEditor {
    
    @Override
    public String getValueForWorksheet(Object item, String property, int rowcount) {

        Object value = super.getValueForWorksheet(item, property, rowcount);
        return value == null ? Boolean.FALSE.toString() : String.valueOf(value);
    }
    
    @Override
    protected String getWsColumn(WorksheetColumn worksheetColumn, Object item, String id, String property, String uniqueProperty, String uniqueValue, Object originalValue, Object changedValue) {

        HtmlBuilder html = new HtmlBuilder();
        
        html.div().styleClass(getStyleClass(worksheetColumn)).close();
        
        html.input().type("radio").name(property);

        boolean isChecked = originalValue != null && Boolean.parseBoolean(originalValue.toString());
        if (isChecked) {
            html.checked();
        }
        
        String unCheckFunction = "unCheckRadio_" + property.replace(".", "_") + "()";
        if (isRowRemoved(getCoreContext().getWorksheet(), getColumn().getRow(), item)) {
            html.disabled();
        } else {
            html.onclick("jQuery.jmesa.submitWorksheetCheckableColumn(this.checked, '" + id + "','" + property + "','"
                    + uniqueProperty + "','" + uniqueValue + "');" + unCheckFunction);
        }

        html.end();

        if (isChecked) {
            html.br();
            html.script().type("text/javascript").close();
            html.append(" function " + unCheckFunction + " {");
            html.append("jQuery.jmesa.submitWorksheetCheckableColumn(false, '" + id + "','" + property + "','"
                    + uniqueProperty + "','" + uniqueValue + "')};");
            html.scriptEnd();
            html.br();
        }
        
        html.divEnd();
        
        return html.toString();
    }

}
