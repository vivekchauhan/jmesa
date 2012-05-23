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

import static org.jmesa.worksheet.WorksheetUtils.isRowRemoved;

import org.jmesa.view.html.HtmlBuilder;

/**
 * Defines a checkbox for the worksheet editor.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class CheckboxWorksheetEditor extends InputWorksheetEditor {
    
    public static final String CHECKED = "checked";
    public static final String UNCHECKED = "unchecked";
    
    /**
     * Interpret the value of the column to be checked or unchecked. The acceptable 
     * values for checked is 'y', 'yes', 'true', and '1'. The acceptable values for 
     * unchecked is 'n', 'no', 'false' and '0'. 
     * 
     * @return Either the value 'checked' or 'unchecked'.
     */
    @Override
    public String getValueForWorksheet(Object item, String property, int rowcount) {
        
        Object value = super.getOriginalCellEditorValue(item, property, rowcount);
        
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
    
    @Override
    protected String getWsColumn(Object item, String id, String property, String uniqueProperty, String uniqueValue, Object originalValue, Object changedValue) {
        
        HtmlBuilder html = new HtmlBuilder();
        
        html.input().type("checkbox");
        
        if (originalValue != null && originalValue.equals(CHECKED)) {
            html.checked();
        }

        if (isRowRemoved(getCoreContext().getWorksheet(), getColumn().getRow(), item)) {
            html.disabled();
        } else {
            html.onclick("jQuery.jmesa.submitWorksheetCheckableColumn(this.checked, '" + id + "','" + property + "','"
                    + uniqueProperty + "','" + uniqueValue + "')");
        }
        
        html.end();
        
        return html.toString();
    }
}
