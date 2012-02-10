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
package org.jmesa.model;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitActionFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.worksheet.Worksheet;

/**
 * A series of utilities that complements the TableModel object. Especially useful
 * for developers that use the tag library and use features like the Worksheet
 * and Limit.
 *
 * @since 3.0
 * @author Jeff Johnston
 */
public class TableModelUtils {
		
    public static String LIMIT_ATTR = "_LIMIT_ATTR";

    protected TableModelUtils(){}

    public static boolean isExporting(String id, HttpServletRequest request) {
		
        return getExportType(id, request) != null;
    }

    public static ExportType getExportType(String id, HttpServletRequest request) {
		
        LimitActionFactory actionFactory = new LimitActionFactory(id, request.getParameterMap());
        return actionFactory.getExportType();
    }

    public static Limit getLimit(String id, HttpServletRequest request, Collection<?> items) {
		
        TableFacade tableFacade = new TableFacade(id, request);
        tableFacade.setItems(items);
        return tableFacade.getLimit();
    }

    public static Limit getLimit(String id, String stateAttr, HttpServletRequest request, Collection<?> items) {
		
        TableFacade tableFacade = new TableFacade(id, request);
        tableFacade.setItems(items);
        tableFacade.setStateAttr(stateAttr);
        return tableFacade.getLimit();
    }

    public static Collection<?> getItems(String id, String stateAttr, HttpServletRequest request, PageItems pageItems) {
		
        TableFacade tableFacade = new TableFacade(id, request);
        tableFacade.setStateAttr(stateAttr);
        Collection<?> items = getItems(tableFacade, pageItems);
        request.setAttribute(tableFacade.getId() + LIMIT_ATTR, tableFacade.getLimit());
        return items;
    }

    public static Collection<?> getItems(String id, HttpServletRequest request, PageItems pageItems) {
		
        TableFacade tableFacade = new TableFacade(id, request);
        Collection<?> items = getItems(tableFacade, pageItems);
        request.setAttribute(tableFacade.getId() + LIMIT_ATTR, tableFacade.getLimit());
        return items;
    }

    protected static Collection<?> getItems(TableFacade tableFacade, PageItems pageItems) {
		
        Limit limit = tableFacade.getLimit();
        int totalRows = pageItems.getTotalRows(limit);
        if (limit.hasRowSelect()) {
            int page = limit.getRowSelect().getPage();
            int maxRows = limit.getRowSelect().getMaxRows();
            limit.setRowSelect(new RowSelect(page, maxRows, totalRows));
        } else {
            tableFacade.setTotalRows(totalRows);
        }

        return pageItems.getItems(limit);
    }

    public static boolean saveWorksheet(String id, HttpServletRequest request, WorksheetSaver worksheetSaver) {
		
        TableFacade tableFacade = new TableFacade(id, request);
        return saveWorksheet(tableFacade, worksheetSaver);
    }

    protected static boolean saveWorksheet(TableFacade tableFacade, WorksheetSaver worksheetSaver) {
		
        tableFacade.setEditable(true);
        Worksheet worksheet = tableFacade.getWorksheet();
        if (worksheet.isSaving() && worksheet.hasChanges()) {
            worksheetSaver.saveWorksheet(worksheet);
            tableFacade.persistWorksheet(worksheet);
        }
        return !worksheet.hasErrors();
    }

    public static Table createTable(String... columnProperties) {
		
        Table table = new Table();

        Row row = new Row();
        table.setRow(row);

        for (int i = 0; i < columnProperties.length; i++) {
            String property = columnProperties[i];
            Column column = new Column(property);
            row.addColumn(column);
        }

        return table;
    }

    public static HtmlTable createHtmlTable(String... columnProperties) {
		
        HtmlTable htmlTable = new HtmlTable();

        HtmlRow htmlRow = new HtmlRow();
        htmlTable.setRow(htmlRow);

        for (int i = 0; i < columnProperties.length; i++) {
            String property = columnProperties[i];
            HtmlColumn htmlColumn = new HtmlColumn(property);
            htmlRow.addColumn(htmlColumn);
        }

        return htmlTable;
    }
}
