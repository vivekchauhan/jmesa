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

import static org.jmesa.view.ViewUtils.isRowEven;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;

import com.lowagie.text.Font;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.FontFactory.HELVETICA;
import static com.lowagie.text.FontFactory.getFont;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import static com.lowagie.text.pdf.BaseFont.NOT_EMBEDDED;
import static com.lowagie.text.pdf.BaseFont.createFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.jmesa.view.AbstractExportView;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jmesa.view.ExportConstants.PDF_FONT_NAME;
import static org.jmesa.view.ExportConstants.PDF_FONT_ENCODING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A PDF view that uses the iText PdfPTable.
 *
 * @since 2.3.4
 * @author Ismail Seyfi
 */
public class PdfPView extends AbstractExportView {
    private Logger logger = LoggerFactory.getLogger(PdfPView.class);

    private Color evenCellBackgroundColor;
    private Color oddCellBackgroundColor;
    private Color headerBackgroundColor;
    private Color headerFontColor;
    private Color captionFontColor;
    private String captionAlignment;

    public PdfPView() {
        this.evenCellBackgroundColor = new Color(227, 227, 227);
        this.oddCellBackgroundColor = new Color(255, 255, 255);
        this.headerBackgroundColor = new Color(114, 159, 207);
        this.headerFontColor = new Color(255, 255, 255);
        this.captionFontColor = new Color(0, 0, 0);
        this.captionAlignment = "center";
    }

    public byte[] getBytes() {
        return null;
    }

    public Paragraph getTableCaption() throws Exception {
        Paragraph p = new Paragraph(getTable().getCaption(), getFont(HELVETICA, 18, BOLD, getCaptionFontColor()));
        p.setAlignment(getCaptionAlignment());
        return p;
    }

    public PdfPTable render() {
        PdfPTable pdfpTable = new PdfPTable(getTable().getRow().getColumns().size());
        pdfpTable.setSpacingBefore(3);

        Row row = getTable().getRow();

        List<Column> columns = row.getColumns();

        // build table headers
        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            Column column = iter.next();
            PdfPCell cell = new PdfPCell(new Paragraph(column.getTitle(), getHeaderCellFont()));
            cell.setPadding(3.0f);
            cell.setBackgroundColor(getHeaderBackgroundColor());
            pdfpTable.addCell(cell);
        }

        // build table body
        Collection<?> items = getCoreContext().getPageItems();
        int rowcount = 0;
        for (Object item : items) {
            rowcount++;

            columns = row.getColumns();

            for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
                Column column = iter.next();

                String property = column.getProperty();
                Object value = column.getCellRenderer().getCellEditor().getValue(item, property, rowcount);
                PdfPCell cell = new PdfPCell(new Paragraph(value == null ? "" : String.valueOf(value), getCellFont()));
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

    /**
     * Create either the default helvetica 12 point font, or specify the
     * font name and encoding in the preferences. Either way it will use
     * the header font color.
     *
     * <p>
     *  The preference settings are the following:
     *  export.pdf.fontName
     *  export.pdf.fontEncoding
     * </p>
     */
    public Font getHeaderCellFont() {
        return getFontWithColor(getHeaderFontColor());
    }

    public Color getHeaderFontColor() {
        return headerFontColor;
    }

    public void setHeaderFontColor(Color headerFontColor) {
        this.headerFontColor = headerFontColor;
    }

    /**
     * Create either the default helvetica 12 point font, or specify the
     * font name and encoding in the preferences.
     *
     * <p>
     *  The preference settings are the following:
     *  export.pdf.fontName
     *  export.pdf.fontEncoding
     * </p>
     */
    public Font getCellFont() {
        return getFontWithColor(null);
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

    private Font getFontWithColor(Color color) {
        String fontName = getCoreContext().getPreference(PDF_FONT_NAME);
        String fontEncoding = getCoreContext().getPreference(PDF_FONT_ENCODING);
        if (isNotBlank(fontName) && isNotBlank(fontEncoding)) {
            try {
                BaseFont baseFont = createFont(fontName, fontEncoding, NOT_EMBEDDED);
                if (color != null) {
                    return new Font(baseFont, 12, 0, color);
                }
                return new Font(baseFont, 12, 0);
            } catch (Exception e) {
                logger.warn("Not able to create the requested font for the PDF export...will use the export.");
            }
        }

        if (color != null) {
            return getFont(HELVETICA, 12, NORMAL, color);
        }

        return getFont(HELVETICA, 12, NORMAL);
    }
}
