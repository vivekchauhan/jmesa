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
package org.jmesa.view.pdf;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractExportView;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlSnippets;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;

/**
 * Use the Flying Saucer API to generate Pdf documents.
 *
 * @since 2.2
 * @author Paul Horn
 */
public class PdfView extends AbstractExportView {
		
    private String cssLocation;

    /**
     * @return The stylesheet to use for this pdf.
     */
    public String getCssLocation() {
		
        if (StringUtils.isEmpty(cssLocation)) {
            cssLocation = getCoreContext().getPreference("pdf.cssLocation");
        }
        return cssLocation;
    }

    /**
     * <p>
     * The css to use for this pdf file. Will be relative to the servlet context.
     * </p>
     *
     * <p>
     * example: /css/jmesa-pdf-landscape.css
     * <p>
     *
     * @param cssLocation The path and name of the jmesa css file.
     */
    public void setCssLocation(String cssLocation) {
		
        this.cssLocation = cssLocation;
    }

    public Object render() {
		
        HtmlBuilder html = new HtmlBuilder();

        String contextPath = getWebContext().getContextPath();

        html.append(getCoreContext().getPreference("pdf.doctype"));

        html.html().close();

        html.head().close();

        html.link().rel("stylesheet").type("text/css").href(contextPath + getCssLocation()).media("print").end();

        html.headEnd();

        html.body().close();

        HtmlSnippets snippets = new HtmlSnippets((HtmlTable)getTable(), null, getCoreContext());
        SupportUtils.setWebContext(snippets, getWebContext());

        html.append(snippets.themeStart());

        html.append(snippets.tableStart());

        html.append(snippets.theadStart());

        html.append(snippets.header());

        html.append(snippets.theadEnd());

        html.append(snippets.tbodyStart());

        decorateCellEditors();

        html.append(snippets.body());

        html.append(snippets.tbodyEnd());

        html.append(snippets.footer());

        html.append(snippets.statusBar());

        html.append(snippets.tableEnd());

        html.append(snippets.themeEnd());

        html.bodyEnd();

        html.htmlEnd();

        return html.toString();
    }

    /**
     * Decorate the cellEditors to escape xml values.
     */
    protected void decorateCellEditors() {
		
        HtmlTable table = (HtmlTable) getTable();
        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();
        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            HtmlColumn column = (HtmlColumn) iter.next();
            CellEditor cellEditor = column.getCellEditor();

            // decorate the cell editors
            EscapeXmlCellEditor escapeXmlCellEditor = new EscapeXmlCellEditor();
            escapeXmlCellEditor.setCellEditor(cellEditor);
            column.setCellEditor(escapeXmlCellEditor);
        }
    }

    /**
     * Decorate the cell editor with one that can escape values.
     */
    private static class EscapeXmlCellEditor implements CellEditor {
		
        private CellEditor cellEditor;

        public void setCellEditor(CellEditor cellEditor) {
		
            this.cellEditor = cellEditor;
        }

        public Object getValue(Object item, String property, int rowcount) {
		
            Object value = cellEditor.getValue(item, property, rowcount);
            if (value != null) {
                return StringEscapeUtils.escapeXml(value.toString());
            }

            return null;
        }
    }
}
