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
package org.jmesa.view.jexcel;

import java.io.File;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.springframework.util.FileCopyUtils;

/**
 * <p>
 * Create the JExcel view.
 * </p>
 * 
 * @since 2.2
 * @author Paul Horn
 */
public class JExcelView implements View {
    private Table table;
    private CoreContext coreContext;
    private OutputStream out;

    public JExcelView(Table table, CoreContext coreContext) {
        this.table = table;
        this.coreContext = coreContext;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    /**
     * A utility method used to debug views.
     * 
     * @return The byte array that represents the JExcel view.
     */
    public byte[] getBytes() {
        WritableWorkbook workbook = (WritableWorkbook) render();
        File temp;
        try {
            temp = File.createTempFile(System.currentTimeMillis() + "_" + (Math.random() * 1000), null);
            workbook.setOutputFile(temp);
            workbook.write();
            workbook.close();
            return FileCopyUtils.copyToByteArray(temp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object render() {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(out);
            String caption = table.getCaption();
            if (StringUtils.isEmpty(caption)) {
                caption = "JMesa Export";
            }
            workbook.createSheet(caption, 0);

            WritableCellFormat headerFmt = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10));
            headerFmt.setBackground(Colour.GREY_25_PERCENT);
            headerFmt.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableSheet sheet = workbook.getSheet(caption);

            int colidx = 0;
            int rowidx = 0;

            Row row = table.getRow();
            row.getRowRenderer();
            List<Column> columns = table.getRow().getColumns();

            for (Column col : columns) {
                sheet.addCell(new Label(colidx, rowidx, col.getTitle(), headerFmt));
                sheet.setColumnView(colidx++, 20);
            }

            rowidx++;

            WritableCellFormat rowFmt = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10));

            Collection<?> items = coreContext.getPageItems();
            for (Object item : items) {
                colidx = 0;
                for (Column col : columns) {
                    Object value = col.getCellRenderer().render(item, rowidx);
                    if (value == null) {
                        value = "";
                    }

                    WritableCell cell = null;
                    if (value instanceof Number) {
                        cell = new Number(colidx++, rowidx, Double.valueOf(value.toString()), rowFmt);
                    } else {
                        cell = new Label(colidx++, rowidx, value + "", rowFmt);
                    }

                    sheet.addCell(cell);
                }
                rowidx++;
            }

            return workbook;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
