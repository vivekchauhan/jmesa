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

import org.jmesa.util.SupportUtils;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.renderer.AbstractCellRenderer;
import org.jmesa.worksheet.editor.HtmlWorksheetEditor;
import org.jmesa.worksheet.editor.WorksheetEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlCellRendererImpl extends AbstractCellRenderer implements HtmlCellRenderer {

    private String style;
    private String styleClass;
    private WorksheetEditor worksheetEditor;

    public HtmlCellRendererImpl() {
    // default constructor
    }

    public HtmlCellRendererImpl(HtmlColumn column, CellEditor editor) {
        setColumn(column);
        setCellEditor(editor);
    }

    @Override
    public HtmlColumn getColumn() {
        return (HtmlColumn) super.getColumn();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @return The CellEditor for this column. If this is an editable worksheet then return the WorksheetEditor.
     */
    @Override
    public CellEditor getCellEditor() {
        boolean editable = ViewUtils.isEditable(getCoreContext().getWorksheet());
        if (editable && getColumn().isEditable()) {
            if (worksheetEditor == null) {
                setWorksheetEditor(new HtmlWorksheetEditor());
            }

            return worksheetEditor;
        }

        return super.getCellEditor();
    }

    public WorksheetEditor getWorksheetEditor() {
        return worksheetEditor;
    }

    public void setWorksheetEditor(WorksheetEditor worksheetEditor) {
        this.worksheetEditor = worksheetEditor;
        if (worksheetEditor != null) {
            worksheetEditor.setCellEditor(super.getCellEditor());
            SupportUtils.setWebContext(worksheetEditor, getWebContext());
            SupportUtils.setCoreContext(worksheetEditor, getCoreContext());
            SupportUtils.setColumn(worksheetEditor, getColumn());
        }
    }

    public Object render(Object item, int rowcount) {
        HtmlBuilder html = new HtmlBuilder();
        html.td(2);
        html.width(getColumn().getWidth());
        html.style(getStyle());
        html.styleClass(getStyleClass());
        html.close();

        String property = getColumn().getProperty();
        Object value = getCellEditor().getValue(item, property, rowcount);
        if (value != null) {
            html.append(value.toString());
        }

        html.tdEnd();

        return html.toString();
    }
}
