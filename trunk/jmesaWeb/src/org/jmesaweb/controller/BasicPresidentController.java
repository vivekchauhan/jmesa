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

import static org.jmesa.facade.TableFacadeImpl.CSV;
import static org.jmesa.facade.TableFacadeImpl.EXCEL;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.Limit;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlTable;
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
    private int maxRows; // The max rows to display on the page.

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);
        Collection<Object> items = presidentService.getPresidents();

        TableFacade facade = new TableFacadeImpl(id, request, maxRows, items, "name.firstName", "name.lastName", "term", "career");
        facade.setExportTypes(response, CSV, EXCEL);
        facade.setStateAttr("restore");

        Table table = facade.getTable();
        table.setCaption("Presidents");

        Column firstName = table.getRow().getColumn("name.firstName");
        firstName.setTitle("First Name");

        Column lastName = table.getRow().getColumn("name.lastName");
        lastName.setTitle("Last Name");

        Limit limit = facade.getLimit();
        if (limit.isExportable()) {
            facade.render(); // Will write the export data out to the response.
            return null; // In Spring returning null tells the controller not to do anything.
        } else {
            HtmlTable htmlTable = (HtmlTable) table;
            htmlTable.getTableRenderer().setWidth("600px");

            // Using an anonymous class to implement a custom editor.
            firstName.getCellRenderer().setCellEditor(new CellEditor() {
                public Object getValue(Object item, String property, int rowcount) {
                    Object value = new BasicCellEditor().getValue(item, property, rowcount);
                    HtmlBuilder html = new HtmlBuilder();
                    html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
                    html.append(value);
                    html.aEnd();
                    return html.toString();
                }
            });

            String html = facade.render(); // Return the Html.
            mv.addObject("presidents", html); // Set the Html in the request for the JSP.
        }

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

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }
}