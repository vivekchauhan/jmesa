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
import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.worksheet.WorksheetColumn;
import static org.jmesa.worksheet.WorksheetUtils.isRowRemoved;

/**
 * Defines autocomplete functionality for the worksheet editor.
 * 
 * @since 3.0.4
 * @author Jeff Johnston
 */
public abstract class AutoCompleteWorksheetEditor extends AbstractWorksheetEditor {

    private String url;
    private String options;

    public AutoCompleteWorksheetEditor() {}
    
    public AutoCompleteWorksheetEditor(String url) {
        this.url = url;
    }

    /**
     * Return either the edited worksheet value, or the value of the underlying CellEditor.
     */
    public Object getValue(Object item, String property, int rowcount) {
        Object value = null;

        WorksheetColumn worksheetColumn = getWorksheetColumn(item, property);
        if (worksheetColumn != null) {
            value = escapeHtml(worksheetColumn.getChangedValue());
        } else {
            value = getValueForWorksheet(item, property, rowcount);
        }

        return getWsColumn(worksheetColumn, value, item, property);
    }

    protected String getWsColumn(WorksheetColumn worksheetColumn, Object value, Object item, String property) {
        if (isRowRemoved(getCoreContext().getWorksheet(), getColumn().getRow(), item)) {
            if (value == null) {
                return "";
            }
            return value.toString();
        }

        HtmlBuilder html = new HtmlBuilder();

        Limit limit = getCoreContext().getLimit();

        html.div();

        html.append(getStyleClass(worksheetColumn));

        String urlValue = getUrl(item, property);
        if (urlValue == null) {
            throw new IllegalStateException("You need to set the url when using the AutoCompleteWorksheetEditor.");
        }

        html.onmouseover("$.jmesa.setTitle(this, event)");
        html.onclick(getUniquePropertyJavaScript(item) + "$.jmesa.createWsAutoCompleteColumn(this, '" + limit.getId() + "'," + UNIQUE_PROPERTY + ",'"
            + getColumn().getProperty() + "','" + urlValue + "','" + getOptions(item, property) + "')");
        html.close();
        html.append(value);
        html.divEnd();

        return html.toString();
    }

    protected String getUrl(Object item, String property) {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected String getOptions(Object item, String property) {
        if (options == null) {
            return "{max:50}";
        }
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}

