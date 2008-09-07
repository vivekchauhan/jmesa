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

import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;

/**
 * Defines a checkbox for the worksheet editor.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class CheckboxWorksheetEditor extends AbstractWorksheetEditor {
    public static final String CHECKED = "checked";
    public static final String UNCHECKED = "unchecked";
    
    public Object getValue(Object item, String property, int rowcount) {
        Object value = null;
        
        WorksheetColumn worksheetColumn = getWorksheetColumn(item, property);
        if (worksheetColumn != null) {
            value = worksheetColumn.getChangedValue();
        } else {
            value = getCheckboxValue(getCellEditor().getValue(item, property, rowcount));
        }

        return getWsColumn(value, item);
    }
    
    /**
     * Interpret the value of the column to be checked or unchecked. The acceptable 
     * values for checked is 'y', 'yes', 'true', and '1'. The acceptable values for 
     * unchecked is 'n', 'no', 'false' and '0'. 
     * 
     * @return Either the value 'checked' or 'unchecked'.
     */
    protected String getCheckboxValue(Object value) {
        if (value == null) {
            return UNCHECKED;
        }
        
        String valueToConvert = String.valueOf(value);
        
        if (valueToConvert.equalsIgnoreCase("y") ||
            valueToConvert.equalsIgnoreCase("yes") || 
            valueToConvert.equalsIgnoreCase("true") || 
            valueToConvert.equals("1")) {
            return CHECKED;
        }
        
        if (valueToConvert.equalsIgnoreCase("n") ||
            valueToConvert.equalsIgnoreCase("no") || 
            valueToConvert.equalsIgnoreCase("false") || 
            valueToConvert.equals("0")) {
            return UNCHECKED;
        }

        throw new IllegalStateException("Not able to convert the value for the checkbox.");
    }
    
    private String getWsColumn(Object value, Object item) {
        HtmlBuilder html = new HtmlBuilder();
        
        Limit limit = getCoreContext().getLimit();
        
        html.input().type("checkbox");
        
        if (value != null && value.equals(CHECKED)) {
            html.checked();
        }

        html.onclick(getUniquePropertyJavaScript(item) + "jQuery.jmesa.submitWsCheckboxColumn(this,'" + limit.getId() + "'," + UNIQUE_PROPERTY + ",'" 
            + getColumn().getProperty() + "')");
        html.end();
        
        return html.toString();
    }
}
