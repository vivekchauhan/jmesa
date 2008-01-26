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
import org.jmesa.view.html.editor.*;

/**
 * Defines a checkbox for the worksheet editor.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class CheckboxWorksheetEditor extends AbstractWorksheetEditor {
    public Object getValue(Object item, String property, int rowcount) {
        HtmlBuilder html = new HtmlBuilder();
        
        Limit limit = getCoreContext().getLimit();
        
        html.input().type("checkbox");
        
        String value = getChangedValue(item, property);
        if (value != null && value.equals("true")) {
            html.checked();
        }
        
        html.onclick(getUniquePropertiesJavaScript(item) + "submitWsCheckboxColumn(this, '" + limit.getId() + "', uniqueProperties, '" 
            + getColumn().getProperty() + "')");
        html.end();
        
        return html.toString();
    }
}
