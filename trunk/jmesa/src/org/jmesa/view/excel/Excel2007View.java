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
package org.jmesa.view.excel;

import java.util.Collection;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jmesa.view.AbstractExportView;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.springframework.util.StringUtils;

/**
 * @since 4.0.3
 * @author Jeff Johnston
 */
public class Excel2007View extends AbstractExportView {
		
    @Override
    public Object render() {
		
        XSSFWorkbook workbook = new XSSFWorkbook();
        Table table = this.getTable();
        String caption = table.getCaption();
        if (!StringUtils.hasText(caption)) {
            caption = "JMesa Export";
        }
        XSSFSheet sheet = workbook.createSheet(caption);

        Row row = table.getRow();
        row.getRowRenderer();
        List<Column> columns = table.getRow().getColumns();

        // renderer header
        XSSFRow hssfRow = sheet.createRow(0);
        int columncount = 0;
        for (Column col : columns) {
            XSSFCell cell = hssfRow.createCell(columncount++);
            cell.setCellValue(new XSSFRichTextString(col.getTitle()));
        }

        // renderer body
        Collection<?> items = getCoreContext().getPageItems();
        int rowcount = 1;
        for (Object item : items) {
            XSSFRow r = sheet.createRow(rowcount++);
            columncount = 0;
            for (Column col : columns) {
                XSSFCell cell = r.createCell(columncount++);
                Object value = col.getCellRenderer().render(item, rowcount);
                if (value == null) {
                    value = "";
                }

                if (value instanceof Number) {
                    Double number = Double.valueOf(value.toString());
                    cell.setCellValue(number);
                } else {
                    cell.setCellValue(new XSSFRichTextString(value.toString()));
                }
            }
        }
        return workbook;
    }
}
