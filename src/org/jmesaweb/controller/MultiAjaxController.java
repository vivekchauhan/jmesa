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

import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.facade.TableFacade;
import static org.jmesa.facade.TableFacadeFactory.createTableFacade;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesaweb.domain.President;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @since 2.3.2
 * @author Jeff Johnston
 */
public class MultiAjaxController extends MultiActionController {

    private PresidentService presidentService;
    private String successView;

    public ModelAndView load(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        ModelAndView mv = new ModelAndView(successView);

        TableFacade tableFacade1 = createTableFacade("table1", request);
        String html1 = getHtml(tableFacade1);
        request.setAttribute("table1", html1);

        TableFacade tableFacade2 = createTableFacade("table2", request);
        String html2 = getHtml(tableFacade2);
        request.setAttribute("table2", html2);

        return mv;
    }

    public ModelAndView table1(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        TableFacade tableFacade = createTableFacade("table1", request);
        String html = getHtml(tableFacade);
        byte[] contents = html.getBytes();
        response.getOutputStream().write(contents);
        return null;
    }

    public ModelAndView table2(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        TableFacade tableFacade = createTableFacade("table2", request);
        String html = getHtml(tableFacade);
        byte[] contents = html.getBytes();
        response.getOutputStream().write(contents);
        return null;
    }

    private String getHtml(TableFacade tableFacade) {
        // add a custom filter matcher to be the same pattern as the cell editor used.
        Collection<President> items = presidentService.getPresidents();
        tableFacade.setItems(items);
        tableFacade.setMaxRows(8);
        tableFacade.setMaxRowsIncrements(8,16,24);

        tableFacade.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));

        // set the column properties
        tableFacade.setColumnProperties("name.firstName", "name.lastName", "term", "career", "born");

        HtmlTable table = (HtmlTable) tableFacade.getTable();
        table.setCaption("Presidents");
        table.getTableRenderer().setWidth("600px");

        HtmlRow row = table.getRow();

        HtmlColumn firstName = row.getColumn("name.firstName");
        firstName.setTitle("First Name");

        HtmlColumn lastName = row.getColumn("name.lastName");
        lastName.setTitle("Last Name");

        HtmlColumn career = row.getColumn("career");
        career.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());

        Column born = row.getColumn("born");
        born.getCellRenderer().setCellEditor(new DateCellEditor("MM/yyyy"));

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

        return tableFacade.render(); // Return the Html.
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }
}
