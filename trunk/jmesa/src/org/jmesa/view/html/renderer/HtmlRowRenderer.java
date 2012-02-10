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
package org.jmesa.view.html.renderer;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.event.MouseRowEvent;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.renderer.AbstractRowRenderer;
import org.jmesa.worksheet.Worksheet;
import static org.jmesa.worksheet.WorksheetUtils.isRowRemoved;
import static org.jmesa.worksheet.WorksheetUtils.hasRowError;
import static org.jmesa.worksheet.WorksheetUtils.getRowError;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlRowRenderer extends AbstractRowRenderer {
		
    public HtmlRowRenderer() {}

    public HtmlRowRenderer(HtmlRow row) {
		
        setRow(row);
    }

    @Override
    public HtmlRow getRow() {
		
        return (HtmlRow) super.getRow();
    }

    protected String getStyleClass(int rowcount) {
		
        HtmlRow row = getRow();
        String styleClass = row.getStyleClass();
        if (StringUtils.isNotBlank(styleClass)) {
            return styleClass;
        }

        if (ViewUtils.isRowEven(rowcount)) {
            return row.getEvenClass();
        }

        return row.getOddClass();
    }

    protected String getStyleClass(Object item, int rowcount) {
		
        Worksheet worksheet = getCoreContext().getWorksheet();
        HtmlRow row = getRow();
        if (isRowRemoved(worksheet, row, item)) {
            return getCoreContext().getPreference(HtmlConstants.ROW_RENDERER_REMOVED_CLASS);
        } else if (hasRowError(worksheet, row, item)) {
            return getCoreContext().getPreference(HtmlConstants.ROW_RENDERER_ERROR_CLASS);
        } else {
            return getStyleClass(rowcount);
        }
    }

    /**
     * The onclick, onmouseover, and onmouseout events for the row.
     *
     * @param item The bean or map for the currect row.
     * @param rowcount The current row count.
     * @return The row events.
     */
    protected String getRowEvents(Object item, int rowcount) {
		
        Worksheet worksheet = getCoreContext().getWorksheet();
        HtmlRow row = getRow();
        if (isRowRemoved(worksheet, row, item) ||
                hasRowError(worksheet, row, item)) {
            return "";
        }

        HtmlBuilder html = new HtmlBuilder();

        RowEvent onclick = row.getOnclick();
        if (onclick != null) {
            html.onclick(onclick.execute(item, rowcount));
        }

        RowEvent onmouseover = row.getOnmouseover();
        if (onmouseover != null) {
            if (onmouseover instanceof MouseRowEvent) {
                MouseRowEvent onmouseoverRowEvent = (MouseRowEvent) onmouseover;
                onmouseoverRowEvent.setStyleClass(getRow().getHighlightClass());
            }

            html.onmouseover(onmouseover.execute(item, rowcount));
        }

        RowEvent onmouseout = row.getOnmouseout();
        if (onmouseout != null) {
            if (onmouseout instanceof MouseRowEvent) {
                MouseRowEvent onmouseoutRowEvent = (MouseRowEvent) onmouseout;
                onmouseoutRowEvent.setStyleClass(getStyleClass(rowcount));
            }

            html.onmouseout(onmouseout.execute(item, rowcount));
        }

        return html.toString();
    }

    public Object render(Object item, int rowcount) {
		
        HtmlBuilder html = new HtmlBuilder();
        html.tr(1);

        Worksheet worksheet = getCoreContext().getWorksheet();
        HtmlRow row = getRow();
        html.id(getCoreContext().getLimit().getId() + "_row" + rowcount);
        html.style(getRow().getStyle());
        html.styleClass(getStyleClass(item, rowcount));

        html.append(getRowEvents(item, rowcount));

        if (hasRowError(worksheet, row, item)) {
            html.title(getRowError(worksheet, row, item));
        }

        html.close();

        return html.toString();
    }
}
