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
import org.jmesa.facade.TableFacade;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesaweb.domain.President;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create an editable worksheet.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetPresidentController extends AbstractController {
    private Logger logger = LoggerFactory.getLogger(WorksheetPresidentController.class);

    private PresidentService presidentService;
    private String successView;
    private String id; // the unique table id

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);
        Collection<President> items = presidentService.getPresidents();

        TableFacade tableFacade = createTableFacade(id, request);
        tableFacade.setItems(items); // set the items
        tableFacade.setStateAttr("restore"); // return to the table in the same state that the user left it
        tableFacade.setEditable(true); // switch to flip that turns the table editable
        
        saveWorksheet(tableFacade);
        
        String html = getHtml(tableFacade);
        mv.addObject("presidents", html); // set the Html in the request for the JSP

        return mv;
    }
    
    private void saveWorksheet(TableFacade tableFacade) {
        Worksheet worksheet = tableFacade.getWorksheet();
        
        if (worksheet != null) {
            logger.debug(worksheet.toString());
        }
    }

    private String getHtml(TableFacade tableFacade) {
        // add a custom filter matcher to be the same pattern as the cell editor used
        tableFacade.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));

        // set the column properties
        tableFacade.setColumnProperties("chkbox", "name.firstName", "name.lastName", "term", "career", "born");
        
        HtmlTable table = (HtmlTable) tableFacade.getTable();
        table.setCaption("Presidents");
        table.getTableRenderer().setWidth("600px");

        HtmlRow row = table.getRow();
        row.setHighlighter(false); // turn off highlighting of rows for better worksheet effect
        row.setUniqueProperties("id"); // the unique worksheet properties to identify the row
        
        HtmlColumn chkbox = row.getColumn("chkbox");
        chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
        chkbox.setTitle("&nbsp;");
        chkbox.setFilterable(false);
        chkbox.setSortable(false);

        HtmlColumn firstName = row.getColumn("name.firstName");
        firstName.setTitle("First Name");

        HtmlColumn lastName = row.getColumn("name.lastName");
        lastName.setTitle("Last Name");

        Column born = row.getColumn("born");
        born.getCellRenderer().setCellEditor(new DateCellEditor("MM/yyyy"));

        // using an anonymous class to implement a custom editor
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

        return tableFacade.render(); // return the Html
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
