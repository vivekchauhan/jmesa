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

import java.util.ArrayList;
import static org.jmesa.limit.ExportType.CSV;
import static org.jmesa.limit.ExportType.JEXCEL;
import static org.jmesa.limit.ExportType.PDF;

import static org.jmesa.facade.TableFacadeFactory.createTableFacade;

import java.util.Collection;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesaweb.domain.President;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Create an editable worksheet.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetPresidentController extends AbstractController {

    private PresidentService presidentService;
    private String successView;
    private String id; // the unique table id

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        TableFacade tableFacade = createTableFacade(id, request);
        tableFacade.setEditable(true); // switch to flip that turns the table editable
        
        saveWorksheet(tableFacade);
        
        Collection<President> items = presidentService.getPresidents();
        tableFacade.setItems(items); // set the items
        tableFacade.setStateAttr("restore"); // return to the table in the same state that the user left it

        String html = getHtml(tableFacade);
        mv.addObject("presidents", html); // set the Html in the request for the JSP

        return mv;
    }

    private void saveWorksheet(TableFacade tableFacade) 
        throws Exception {
        Worksheet worksheet = tableFacade.getWorksheet();
        if (!worksheet.isSaving()) {
            return;
        }

        String uniquePropery = getUniquePropery(worksheet);
        List<String> uniqueIds = getUniqueIds(worksheet);
        Map<String, President> presidents = presidentService.getPresidentsByUniqueIds(uniquePropery, uniqueIds);
        
        Iterator<WorksheetRow> worksheetRows = worksheet.getRows().iterator();
        
        while (worksheetRows.hasNext()) {
            WorksheetRow worksheetRow = worksheetRows.next();
            
            Iterator<WorksheetColumn> worksheetColumns = worksheetRow.getColumns().iterator();
            while (worksheetColumns.hasNext()) {
                 WorksheetColumn worksheetColumn = worksheetColumns.next();
                
                String changedValue = worksheetColumn.getChangedValue();

                validateColumn(worksheetColumn, changedValue);
                if (worksheetColumn.hasError()) {
                    continue;
                }

                UniqueProperty uniqueProperty = worksheetRow.getUniqueProperty();
                President president = presidents.get(uniqueProperty.getValue());
                String property = worksheetColumn.getProperty();
                PropertyUtils.setProperty(president, property, changedValue);
                presidentService.save(president);
                
                // remove the column after sucessfully saving item
                worksheetColumns.remove();
            }
            
            // remove the worksheet row if there are no columns left
            if (worksheetRow.getColumns().size() == 0) {
                worksheetRows.remove();
            }
        }
    }

    /**
     * @return Get the unique row property.
     */
    private String getUniquePropery(Worksheet worksheet) {
        return worksheet.getRows().iterator().next().getUniqueProperty().getProperty();
    }

    /**
     * @return Get the unique ids.
     */
    private List<String> getUniqueIds(Worksheet worksheet) {
        List<String> result = new ArrayList<String>();

        Collection<WorksheetRow> worksheetRows = worksheet.getRows();
        for (WorksheetRow worksheetRow : worksheetRows) {
            UniqueProperty uniqueProperty = worksheetRow.getUniqueProperty();
            result.add(uniqueProperty.getValue());
        }

        return result;
    }

    /**
     * Validate the worksheet column cells.
     */
    private void validateColumn(WorksheetColumn worksheetColumn, String changedValue) {
        if (changedValue.equals("foo")) {
            worksheetColumn.setErrorKey("foo.error");
        } else {
            worksheetColumn.removeError();
        }
    }

    /**
     * @return Get the html for the table.
     */
    private String getHtml(TableFacade tableFacade) {
        // add a custom filter matcher to be the same pattern as the cell editor used
        tableFacade.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));

        // set the column properties
        tableFacade.setColumnProperties("chkbox", "name.firstName", "name.lastName", "term", "career", "born");

        HtmlTable table = (HtmlTable) tableFacade.getTable();
        table.setCaption("Presidents");
        table.getTableRenderer().setWidth("600px");

        HtmlRow row = table.getRow();
        row.setUniqueProperty("id"); // the unique worksheet properties to identify the row

        HtmlColumn chkbox = row.getColumn("chkbox");
        chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
        chkbox.setTitle("&nbsp;");
        chkbox.setFilterable(false);
        chkbox.setSortable(false);

        HtmlColumn firstName = row.getColumn("name.firstName");
        firstName.setTitle("First Name");

        HtmlColumn lastName = row.getColumn("name.lastName");
        lastName.setTitle("Last Name");

        HtmlColumn born = row.getColumn("born");
        born.setEditable(false);
        born.getCellRenderer().setCellEditor(new DateCellEditor("MM/yyyy"));

        HtmlColumn career = row.getColumn("career");
        career.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());

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
