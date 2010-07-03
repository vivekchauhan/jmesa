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

import org.jmesa.limit.ExportType;
import static org.jmesa.limit.ExportType.CSV;
import static org.jmesa.limit.ExportType.JEXCEL;
import static org.jmesa.limit.ExportType.PDF;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.model.TableModel;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.editor.HtmlFilterEditor;
import org.jmesa.view.html.editor.HtmlHeaderEditor;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.view.html.renderer.HtmlFilterRenderer;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;
import org.jmesa.view.html.renderer.HtmlRowRenderer;
import org.jmesa.view.html.renderer.HtmlTableRenderer;
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

        TableModel tableModel = new TableModel(id, request);
        tableModel.setItems(presidentService.getPresidents());

        tableModel.setExportTypes(new ExportType[]{CSV, JEXCEL, PDF});
        tableModel.setStateAttr("restore");

        //filterMatchers.put(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));

        HtmlTable htmlTable = new HtmlTable();
        htmlTable.setCaption("Presidents");
        htmlTable.setTableRenderer(new HtmlTableRenderer(htmlTable));
        htmlTable.setWidth("600px");

        HtmlRow htmlRow = new HtmlRow();
        htmlRow.setRowRenderer(new HtmlRowRenderer(htmlRow));
        htmlTable.setRow(htmlRow);

        // first name

        HtmlColumn firstName = new HtmlColumn();
        firstName.setCellRenderer(new HtmlCellRenderer(firstName, new HtmlCellEditor()));
        
        HtmlFilterRenderer firstNameFilterRenderer = new HtmlFilterRenderer(firstName);
        firstNameFilterRenderer.setFilterEditor(new HtmlFilterEditor());
        firstName.setFilterRenderer(firstNameFilterRenderer);

        HtmlHeaderRenderer firstNameHeaderRenderer = new HtmlHeaderRenderer(firstName);
        firstNameHeaderRenderer.setHeaderEditor(new HtmlHeaderEditor());
        firstName.setHeaderRenderer(firstNameHeaderRenderer);

        firstName.setProperty("name.firstName");
        firstName.setTitle("First Name");
        firstName.getCellRenderer().setCellEditor(new CellEditor() {
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

        HtmlColumn lastName = new HtmlColumn();
        lastName.setCellRenderer(new HtmlCellRenderer(lastName, new HtmlCellEditor()));

        HtmlFilterRenderer lastNameFilterRenderer = new HtmlFilterRenderer(lastName);
        lastNameFilterRenderer.setFilterEditor(new HtmlFilterEditor());
        lastName.setFilterRenderer(lastNameFilterRenderer);

        HtmlHeaderRenderer lastNameHeaderRenderer = new HtmlHeaderRenderer(lastName);
        lastNameHeaderRenderer.setHeaderEditor(new HtmlHeaderEditor());
        lastName.setHeaderRenderer(lastNameHeaderRenderer);

        lastName.setProperty("name.lastName");
        lastName.setTitle("Last Name");
        htmlRow.addColumn(lastName);

        // career

        HtmlColumn career = new HtmlColumn();
        career.setCellRenderer(new HtmlCellRenderer(career, new HtmlCellEditor()));

        HtmlFilterRenderer careerFilterRenderer = new HtmlFilterRenderer(career);
        careerFilterRenderer.setFilterEditor(new HtmlFilterEditor());
        career.setFilterRenderer(careerFilterRenderer);

        HtmlHeaderRenderer  careerHeaderRenderer = new HtmlHeaderRenderer(career);
        careerHeaderRenderer.setHeaderEditor(new HtmlHeaderEditor());
        career.setHeaderRenderer(careerHeaderRenderer);

        career.setProperty("career");
        //career.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
        htmlRow.addColumn(career);

        // born

        HtmlColumn born = new HtmlColumn();
        born.setCellRenderer(new HtmlCellRenderer(born, new HtmlCellEditor()));

        HtmlFilterRenderer bornFilterRenderer = new HtmlFilterRenderer(born);
        bornFilterRenderer.setFilterEditor(new HtmlFilterEditor());
        born.setFilterRenderer(bornFilterRenderer);

        HtmlHeaderRenderer bornHeaderRenderer = new HtmlHeaderRenderer(born);
        bornHeaderRenderer.setHeaderEditor(new HtmlHeaderEditor());
        born.setHeaderRenderer(bornHeaderRenderer);

        born.setProperty("born");
        born.getCellRenderer().setCellEditor(new DateCellEditor("MM/yyyy"));
        htmlRow.addColumn(born);

        tableModel.setTable(htmlTable);

        String view = tableModel.render();

        request.setAttribute("presidents", view);

        return mv;
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
