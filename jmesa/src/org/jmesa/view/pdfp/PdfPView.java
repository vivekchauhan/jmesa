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
package org.jmesa.view.pdfp;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import static org.jmesa.view.ViewUtils.isRowEven;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;

/**
 * A PDF view that uses the iText PdfPTable.
 *
 * @since 2.3.4
 * @author acimasiz
 */
public class PdfPView implements View {

    private Table table;
    private WebContext webContext;
    private CoreContext coreContext;
    private Color evenCellBackgroundColor;
    private Color oddCellBackgroundColor;
    private Color headerBackgroundColor;
    private Color headerFontColor;
    private Color captionFontColor;
    private String captionAlignment;

    public PdfPView(Table table, Toolbar toolbar, WebContext webContext, CoreContext coreContext) {
        this.table = table;
        this.webContext = webContext;
        this.coreContext = coreContext;

        this.evenCellBackgroundColor = new Color(227, 227, 227);
        this.oddCellBackgroundColor = new Color(255, 255, 255);
        this.headerBackgroundColor = new Color(114, 159, 207);
        this.headerFontColor = new Color(255, 255, 255);
        this.captionFontColor = new Color(0, 0, 0);
        this.captionAlignment = "center";
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public byte[] getBytes() {
        return null;
    }

    public Paragraph getTableCaption() throws Exception {
        Paragraph p = new Paragraph(this.table.getCaption(), FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, this.captionFontColor));
        p.setAlignment(getCaptionAlignment());
        return p;
    }

    public PdfPTable render() {
        PdfPTable pdfpTable = new PdfPTable(this.table.getRow().getColumns().size());

        Row row = getTable().getRow();

        List<Column> columns = row.getColumns();

        // build table headers
        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            Column column = iter.next();
            PdfPCell cell = new PdfPCell(new Paragraph(column.getTitle(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, getHeaderFontColor())));
            cell.setPadding(3.0f);
            cell.setBackgroundColor(getHeaderBackgroundColor());
            pdfpTable.addCell(cell);
        }

        // build table body
        Collection<?> items = coreContext.getPageItems();
        int rowcount = 0;
        for (Object item : items) {
            rowcount++;

            columns = row.getColumns();

            for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
                Column column = iter.next();

                String property = column.getProperty();
                Object value = column.getCellRenderer().getCellEditor().getValue(item, property, rowcount);
                PdfPCell cell = new PdfPCell(new Paragraph(value.toString(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
                cell.setPadding(3.0f);

                if (isRowEven(rowcount)) {
                    cell.setBackgroundColor(getEvenCellBackgroundColor());
                } else {
                    cell.setBackgroundColor(getOddCellBackgroundColor());
                }

                pdfpTable.addCell(cell);
            }
        }

        return pdfpTable;
    }

    public String getCaptionAlignment() {
        return captionAlignment;
    }

    public void setCaptionAlignment(String captionAlignment) {
        this.captionAlignment = captionAlignment;
    }

    public Color getCaptionFontColor() {
        return captionFontColor;
    }

    public void setCaptionFontColor(Color captionFontColor) {
        this.captionFontColor = captionFontColor;
    }

    public Color getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public void setHeaderBackgroundColor(Color headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    public Color getHeaderFontColor() {
        return headerFontColor;
    }

    public void setHeaderFontColor(Color headerFontColor) {
        this.headerFontColor = headerFontColor;
    }

    public Color getEvenCellBackgroundColor() {
        return evenCellBackgroundColor;
    }

    public void setEvenCellBackgroundColor(Color evenCellBackgroundColor) {
        this.evenCellBackgroundColor = evenCellBackgroundColor;
    }

    public Color getOddCellBackgroundColor() {
        return oddCellBackgroundColor;
    }

    public void setOddCellBackgroundColor(Color oddCellBackgroundColor) {
        this.oddCellBackgroundColor = oddCellBackgroundColor;
    }
}
