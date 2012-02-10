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

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;

public class DroplistWorksheetEditor extends AbstractWorksheetEditor {

    private Set<String> options;
    private boolean isFirstLabelEmpty = true;

    public DroplistWorksheetEditor(Set<String> options) {
		
        this.options = options;
        if (options == null) {
        	throw new RuntimeException("List of options can not be null for DroplistWorksheetEditor");
        }
    }

    public DroplistWorksheetEditor(Set<String> options, boolean isFirstLabelEmpty) {
		
    	this(options);
    	this.isFirstLabelEmpty = isFirstLabelEmpty;
    }
    
    public Object getValue(Object item, String property, int rowcount) {
		
        Object value = null;
        WorksheetColumn worksheetColumn = getWorksheetColumn(item, property);
        if (worksheetColumn != null) {
            value = worksheetColumn.getChangedValue();
        } else {
            value = getValueForWorksheet(item, property, rowcount);
        }

        return getWsColumn(worksheetColumn, value, item);
    }

    private String getWsColumn(WorksheetColumn worksheetColumn, Object value, Object item) {
		
        HtmlBuilder html = new HtmlBuilder();
        Limit limit = getCoreContext().getLimit();
        String firstLabel = null;

        Set<String> droplistLabels;
        
        if (isFirstLabelEmpty) {
        	droplistLabels = new LinkedHashSet<String>();
        	droplistLabels.add("");
        	droplistLabels.addAll(options);
        } else {
        	droplistLabels = options;
        }
        
        StringBuilder array = new StringBuilder();
        array.append("{");
        
        int i = 0;
        for (String label : droplistLabels) {
        	if (label == null) label = "";
        	
        	array.append("'").append(label).append("':'").append(label).append("'");

        	// store first label
            if (i == 0) {
            	firstLabel = label;
        	}
        	
        	if (i < droplistLabels.size() - 1) {
        		array.append(", ");
        	}
        
        	i++;
        }
        array.append("}");

        // If value is outside of the Set
        if (value == null || !droplistLabels.contains(value.toString())) {
        	value = firstLabel;
        }
        
        html.div();

        html.append(getStyleClass(worksheetColumn));
        
        html.onmouseover("$.jmesa.setTitle(this, event)");
        html.onclick(getUniquePropertyJavaScript(item) + "$.jmesa.createWsDroplistColumn(this, '" + limit.getId() + "'," + UNIQUE_PROPERTY + ",'" + getColumn().getProperty() + "', " + array + ")");
        html.close();
        html.append(escapeHtml(value.toString()));
        html.divEnd();
        
        return html.toString();
    }
}