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

import java.util.Map;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.limit.ExportType;
import static org.jmesa.limit.ExportType.CSV;
import static org.jmesa.limit.ExportType.JEXCEL;
import static org.jmesa.limit.ExportType.PDF;

import static org.jmesa.facade.TableFacadeFactory.createTableFacade;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.facade.TableFacadeTemplate;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
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
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        TableFacade tableFacade = createTableFacade(id, request);
        TableFacadeTemplate template = new BasicPresidentTemplate();

        String view = template.render(tableFacade, response);
        if (view == null) { // an export will return null
            return null;
        }

        request.setAttribute("presidents", view); // Set the Html in the request for the JSP.

        return mv;
    }

    private class BasicPresidentTemplate extends TableFacadeTemplate {

        /**
         * The array of available exports.
         */
        @Override
        protected ExportType[] getExportTypes() {
            return new ExportType[]{CSV, JEXCEL, PDF};
        }

        /**
         * Add a custom filter matcher to be the same pattern as the cell editor used.
         */
        @Override
        protected void addFilterMatchers(Map<MatcherKey, FilterMatcher> filterMatchers) {
            filterMatchers.put(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        }

        /**
         * Make it so that the table state is saved.
         */
        @Override
        protected String getStateAttr() {
            return "restore";
        }

        /**
         * Set the column properties. Will be different based on if this is an export.
         */
        @Override
        protected String[] getColumnProperties() {
            if (isExporting()) {
                return new String[]{"name.firstName", "name.lastName", "term", "career"};
            }
            return new String[]{"name.firstName", "name.lastName", "term", "career", "born"};
        }

        /**
         * After the column properties are set then we can modify the table.
         *
         * Note: a new (and better) way to build an html table would be to override the createTable()
         * method and use the HtmlBuilder. Exports would still have to use this method to modify
         * the table though.
         */
        @Override
        protected void modifyTable(Table table) {
            if (isExporting()) {
                table.setCaption("Presidents");

                Row row = table.getRow();

                Column firstName = row.getColumn("name.firstName");
                firstName.setTitle("First Name");

                Column lastName = row.getColumn("name.lastName");
                lastName.setTitle("Last Name");
            } else {
                HtmlTable htmlTable = (HtmlTable)table;
                htmlTable.setCaption("Presidents");
                htmlTable.getTableRenderer().setWidth("600px");

                HtmlRow htmlRow = htmlTable.getRow();

                HtmlColumn firstName = htmlRow.getColumn("name.firstName");
                firstName.setTitle("First Name");

                HtmlColumn lastName = htmlRow.getColumn("name.lastName");
                lastName.setTitle("Last Name");

                HtmlColumn career = htmlRow.getColumn("career");
                career.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());

                Column born = htmlRow.getColumn("born");
                born.getCellRenderer().setCellEditor(new DateCellEditor("MM/yyyy"));

                // Using an anonymous class to implement a custom editor.
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
            }
        }

        @Override
        protected Collection<?> getItems() {
            return presidentService.getPresidents();
        }
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
