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
package org.jmesa.worksheet.servlet;

import static org.jmesa.core.message.MessagesFactory.getMessages;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.Messages;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetColumnImpl;
import org.jmesa.worksheet.WorksheetImpl;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowImpl;
import org.jmesa.worksheet.state.SessionWorksheetState;
import org.jmesa.worksheet.state.WorksheetState;

/**
 * Will store the Worksheet object in the users session by the table id. However, once the servlet
 * is set up the developer will not ever have to deal with the fact that the Worksheet object is in
 * session. The ajax calls will abstract that out from the html table side. Then the TableFacade
 * will abstract out the retrieve of the Worksheet in the controller.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetServlet extends HttpServlet {
    private static String UNIQUE_PROPERTIES = "up_";
    private static String COLUMN_PROPERTY = "cp_";
    private static String ORIGINAL_VALUE = "ov_";
    private static String CHANGED_VALUE = "cv_";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebContext webContext = new HttpServletRequestWebContext(request);
        Messages messages = getMessages(webContext);
        Worksheet worksheet = getWorksheet(messages, webContext);
        WorksheetRow row = getWorksheetRow(worksheet, webContext);
        WorksheetColumn column = getWorksheetColumn(row, messages, webContext);
        validateWorksheet(worksheet, row, column);
    }

    Worksheet getWorksheet(Messages messages, WebContext webContext) {
        String id = webContext.getParameter("id");
        WorksheetState state = new SessionWorksheetState(id, webContext);
        Worksheet worksheet = state.retrieveWorksheet();
        if (worksheet == null) {
            worksheet = new WorksheetImpl(id, messages);
            state.persistWorksheet(worksheet);
        }

        return worksheet;
    }

    WorksheetRow getWorksheetRow(Worksheet worksheet, WebContext webContext) {
        Map<?, ?> parameters = webContext.getParameterMap();
        for (Object param : parameters.keySet()) {
            String parameter = (String) param;
            if (parameter.startsWith(UNIQUE_PROPERTIES)) {
                String value = webContext.getParameter(parameter);
                String property = StringUtils.substringAfter(parameter, UNIQUE_PROPERTIES);

                UniqueProperty uniqueProperty = new UniqueProperty(property, value);
                WorksheetRow row = worksheet.getRow(uniqueProperty);
                if (row == null) {
                    row = new WorksheetRowImpl(uniqueProperty);
                    worksheet.addRow(row);
                }

                return row;
            }
        }
        
        return null;
    }

    WorksheetColumn getWorksheetColumn(WorksheetRow row, Messages messages, WebContext webContext) {
        String property = webContext.getParameter(COLUMN_PROPERTY);
        WorksheetColumn column = row.getColumn(property);
        if (column == null) {
            String orginalValue = webContext.getParameter(ORIGINAL_VALUE);
            column = new WorksheetColumnImpl(property, orginalValue, messages);
            row.addColumn(column);
        }

        String changedValue = webContext.getParameter(CHANGED_VALUE);
        column.setChangedValue(changedValue);
        
        return column;
    }
    
    /**
     * <p>
     * Validate that the columns original value is not the same as the changed value. If it is
     * then remove the column from the row.
     * </p>
     * 
     * <p>
     * If the column is removed then validate that there are still other columns in the row. If 
     * there is not then remove the row from the worksheet.
     * </p>
     * 
     * @param worksheet The current worksheet.
     * @param row The current row.
     * @param column The current column.
     */
    void validateWorksheet(Worksheet worksheet, WorksheetRow row, WorksheetColumn column) {
        if (column.getChangedValue().equals(column.getOriginalValue())) {
            row.removeColumn(column);
        }
        
        if (row.getColumns().size() == 0) {
            worksheet.removeRow(row);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
