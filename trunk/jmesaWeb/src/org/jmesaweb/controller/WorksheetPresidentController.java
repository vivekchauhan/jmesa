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

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.model.AllItems;
import org.jmesa.model.TableModel;
import org.jmesa.model.WorksheetSaver;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import static org.jmesa.worksheet.WorksheetValidationType.REQUIRED;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.WorksheetValidation;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import static org.jmesa.worksheet.WorksheetValidation.TRUE;
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

        TableModel tableModel = new TableModel(id, request);
        tableModel.setEditable(true);
        tableModel.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));

        tableModel.saveWorksheet(new WorksheetSaver() {
            public void saveWorksheet(Worksheet worksheet) {
                saveWorksheetChanges(worksheet);
            }
        });

        tableModel.setItems(new AllItems() {
            public Collection<?> getItems() {
                return presidentService.getPresidents();
            }
        });

        HtmlTable htmlTable = new HtmlTable().caption("Presidents").width("600px");

        HtmlRow htmlRow = new HtmlRow().uniqueProperty("id");
        htmlTable.setRow(htmlRow);

        // non-fluent columns
        HtmlColumn remove = new HtmlColumn("remove");
        remove.setWorksheetEditor(new RemoveRowWorksheetEditor());
        remove.setTitle("&nbsp;");
        remove.setFilterable(false);
        remove.setSortable(false);
        htmlRow.addColumn(remove);

        // fluent columns
        HtmlColumn chkbox = new HtmlColumn("selected").title("&nbsp;");
        chkbox.setWorksheetEditor(new CheckboxWorksheetEditor());
        chkbox.filterable(false).sortable(false);
        htmlRow.addColumn(chkbox);

        HtmlColumn firstName = new HtmlColumn("name.firstName").title("First Name");
        firstName.addWorksheetValidation(new WorksheetValidation(REQUIRED, TRUE));
        htmlRow.addColumn(firstName);

        HtmlColumn lastName = new HtmlColumn("name.lastName").title("Last Name");
        htmlRow.addColumn(lastName);

        HtmlColumn career = new HtmlColumn("career").filterEditor(new DroplistFilterEditor());
        htmlRow.addColumn(career);

        HtmlColumn born = new HtmlColumn("born").editable(false);
        born.setCellEditor(new DateCellEditor("MM/yyyy"));
        htmlRow.addColumn(born);

        tableModel.setTable(htmlTable);

        request.setAttribute("presidents", tableModel.render()); // set the Html in the request for the JSP

        return mv;
    }

    protected void saveWorksheetChanges(Worksheet worksheet) {
        String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
        List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);
        final Map<String, President> presidents =
                presidentService.getPresidentsByUniqueIds(uniquePropertyName, uniquePropertyValues);

        worksheet.processRows(new WorksheetCallbackHandler() {
            public void process(WorksheetRow worksheetRow) {
                if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
                    // would save the new President here
                } else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.REMOVE)) {
                    // would delete the President here
                } else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.MODIFY)) {
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
