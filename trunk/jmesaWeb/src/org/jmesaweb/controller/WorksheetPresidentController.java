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


import static org.jmesa.facade.TableFacadeFactory.createTableFacade;

import java.util.Collection;
import java.util.Date;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeTemplate;
import org.jmesa.facade.WorksheetTableFacadeTemplate;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import static org.jmesa.worksheet.WorksheetValidationType.REQUIRED;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.WorksheetValidation;
import static org.jmesa.worksheet.WorksheetValidation.TRUE;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesa.worksheet.editor.RemoveRowWorksheetEditor;
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
        TableFacadeTemplate template = new WorksheetPresidentTemplate(tableFacade);
        request.setAttribute("presidents", template.render()); // set the Html in the request for the JSP

        return mv;
    }

    private class WorksheetPresidentTemplate extends WorksheetTableFacadeTemplate {

        public WorksheetPresidentTemplate(TableFacade tableFacade) {
            super(tableFacade);
        }

        @Override
        protected void saveWorksheet(Worksheet worksheet) {
            String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
            List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);
            final Map<String, President> presidents =
                    presidentService.getPresidentsByUniqueIds(uniquePropertyName, uniquePropertyValues);

            worksheet.processRows(new WorksheetCallbackHandler() {
                public void process(WorksheetRow worksheetRow) {
                    Collection<WorksheetColumn> columns = worksheetRow.getColumns();
                    for (WorksheetColumn worksheetColumn : columns) {
                        String changedValue = worksheetColumn.getChangedValue();

                        validateColumn(worksheetColumn, changedValue);
                        if (worksheetColumn.hasError()) {
                            continue;
                        }

                        String uniqueValue = worksheetRow.getUniqueProperty().getValue();
                        President president = presidents.get(uniqueValue);
                        String property = worksheetColumn.getProperty();

                        try {
                            if (worksheetColumn.getProperty().equals("selected")) {
                                if (changedValue.equals(CheckboxWorksheetEditor.CHECKED)) {
                                    PropertyUtils.setProperty(president, property, "y");
                                } else {
                                    PropertyUtils.setProperty(president, property, "n");
                                }

                            } else {
                                PropertyUtils.setProperty(president, property, changedValue);
                            }
                        } catch (Exception ex) {
                            String msg = "Not able to set the property [" + property + "] when saving worksheet.";
                            throw new RuntimeException(msg);
                        }

                        presidentService.save(president);
                    }
                }
            });
        }

        /**
         * An example of how to validate the worksheet column cells.
         */
        private void validateColumn(WorksheetColumn worksheetColumn, String changedValue) {
            if (changedValue.equals("foo")) {
                worksheetColumn.setErrorKey("foo.error");
            } else {
                worksheetColumn.removeError();
            }
        }

        /**
         * Add a custom filter matcher to be the same pattern as the cell editor used.
         */
        @Override
        protected void addFilterMatchers(Map<MatcherKey, FilterMatcher> filterMatchers) {
            filterMatchers.put(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        }

        @Override
        protected String[] getColumnProperties() {
            return new String[]{"remove", "selected", "name.firstName", "name.lastName", "term", "career", "born"};
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
            HtmlTable htmlTable = (HtmlTable)table;
            htmlTable.setCaption("Presidents");
            htmlTable.getTableRenderer().setWidth("600px");

            HtmlRow htmlRow = htmlTable.getRow();
            htmlRow.setUniqueProperty("id"); // the unique worksheet properties to identify the row

            HtmlColumn remove = htmlRow.getColumn("remove");
            remove.getCellRenderer().setWorksheetEditor(new RemoveRowWorksheetEditor());
            remove.setTitle("&nbsp;");
            remove.setFilterable(false);
            remove.setSortable(false);

            HtmlColumn chkbox = htmlRow.getColumn("selected");
            chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
            chkbox.setTitle("&nbsp;");
            chkbox.setFilterable(false);
            chkbox.setSortable(false);

            HtmlColumn firstName = htmlRow.getColumn("name.firstName");
            firstName.setTitle("First Name");
            firstName.addWorksheetValidation(new WorksheetValidation(REQUIRED, TRUE));

            HtmlColumn lastName = htmlRow.getColumn("name.lastName");
            lastName.setTitle("Last Name");

            HtmlColumn born = htmlRow.getColumn("born");
            born.setEditable(false);
            born.getCellRenderer().setCellEditor(new DateCellEditor("MM/yyyy"));
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
