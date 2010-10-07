package org.jmesa.worksheet.editor;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.util.Set;

import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;

public class DroplistWorksheetEditor extends AbstractWorksheetEditor {

    private Set<String> options;

    public DroplistWorksheetEditor(Set<String> options) {
        this.options = options;
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

        StringBuilder array = new StringBuilder();
        array.append("{");
        
        int i = 0;
        
        // If the value is outside of Set, treat as null 
        if (!options.contains(value)) {
            value = null;
        }
        
        for (String label : options) {
        	array.append("'").append(label).append("':'").append(label).append("'");

        	// if value is null, get first label from the Set 
            if (value == null && i == 0) {
        		value = label;
        	}
        	
        	if (i < options.size() - 1) {
        		array.append(", ");
        	}
        
        	i++;
        }
        
        array.append("}");

        html.div();
        
        if (worksheetColumn != null) {
        	String originalValue = worksheetColumn.getOriginalValue();
            if (worksheetColumn.hasError()) {
                html.styleClass("wsColumnError");
                // use custom attributes for original value & error message
                html.append("data-ov=\"" + originalValue + "\" ");
                html.append("data-em=\"" + worksheetColumn.getError() + "\" ");
            } else {
            	if (worksheetColumn.getOriginalValue().equals(worksheetColumn.getChangedValue())) {
                    html.styleClass("wsColumn");
                } else {
            		html.styleClass("wsColumnChange");
            		html.append("data-ov=\"" + originalValue + "\" ");
                }
            }
        } else {
            html.styleClass("wsColumn");
        }
        
        html.onmouseover("$.jmesa.setTitle(this, event)");
        html.onclick(getUniquePropertyJavaScript(item) + "$.jmesa.createWsDroplistColumn(this, '" + limit.getId() + "'," + UNIQUE_PROPERTY + ",'" + getColumn().getProperty() + "', " + array + ")");
        html.close();
        html.append(escapeHtml(value.toString()));
        html.divEnd();
        
        return html.toString();
    }
}