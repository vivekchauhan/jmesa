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
package org.jmesaweb.controller;

import java.util.Date;
import org.jmesa.limit.ExportType;
import static org.jmesa.limit.ExportType.CSV;
import static org.jmesa.limit.ExportType.JEXCEL;
import static org.jmesa.limit.ExportType.PDF;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;

import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Create a new TableFacade and tweak it out.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class BasicPresidentController extends AbstractController {

    private PresidentService presidentService;
    private String successView;
    private String id; // The unique table id.

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        TableModel tableModel = new TableModel(id, request, response);
        tableModel.setItems(presidentService.getPresidents());
        tableModel.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        tableModel.setExportTypes(new ExportType[]{CSV, JEXCEL, PDF});
        tableModel.setStateAttr("restore");


        if (tableModel.isExporting()) {
            Table table = getExportTable();
            tableModel.setTable(table);

            if (tableModel.getExportType().equals(ExportType.CSV)) {
                CsvView csvView = new CsvView("|");
                tableModel.setView(csvView);
            }
        } else {
            Table table = getHtmlTable();
            tableModel.setTable(table);
        }

        String view = tableModel.render();

        request.setAttribute("presidents", view);

        return mv;
    }

    private Table getExportTable() {
        Table table = new Table().caption("Presidents");

        Row row = new Row();
        table.setRow(row);

        // first name

        Column firstName = new Column("name.firstName").title("First Name");
        row.addColumn(firstName);

        // last name

        Column lastName = new Column("name.lastName").title("Last Name");
        row.addColumn(lastName);

        // career

        Column career = new Column("career").filterEditor(new DroplistFilterEditor());
        row.addColumn(career);

        // born

        Column born = new Column("born").cellEditor(new DateCellEditor("MM/yyyy"));
        row.addColumn(born);

        return table;
    }

    private Table getHtmlTable() {
        HtmlTable htmlTable = new HtmlTable().caption("Presidents").width("600px");

        HtmlRow htmlRow = new HtmlRow();
        htmlTable.setRow(htmlRow);

        // first name

        HtmlColumn firstName = new HtmlColumn("name.firstName").title("First Name");
        firstName.setCellEditor(new CellEditor() {
            public Object getValue(Object item, String property, int rowcount) {
                Object value = new HtmlCellEditor().getValue(item, property, rowcount);
                HtmlBuilder html = new HtmlBuilder();
                html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
                html.append(value);
                html.aEnd();
                return html.toString();
            }
        });
        htmlRow.addColumn(firstName);

        // last name

        HtmlColumn lastName = new HtmlColumn("name.lastName").title("Last Name");
        htmlRow.addColumn(lastName);

        // career

        HtmlColumn career = new HtmlColumn("career").filterEditor(new DroplistFilterEditor());
        htmlRow.addColumn(career);

        // born

        HtmlColumn born = new HtmlColumn("born").cellEditor(new DateCellEditor("MM/yyyy"));
        htmlRow.addColumn(born);

        return htmlTable;
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setId(String id) {
        this.id = id;
    }
}
