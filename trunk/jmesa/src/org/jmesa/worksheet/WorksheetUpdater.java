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
package org.jmesa.worksheet;

import java.io.UnsupportedEncodingException;
import static java.net.URLDecoder.decode;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.Messages;
import org.jmesa.web.WebContext;
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
public class WorksheetUpdater {
    protected static String UNIQUE_PROPERTIES = "up_";
    protected static String COLUMN_PROPERTY = "cp_";
    protected static String ORIGINAL_VALUE = "ov_";
    protected static String CHANGED_VALUE = "cv_";

    protected static String ERROR_MESSAGE = "em_";

    protected static String COL_REMOVED = "_rm_";
    protected static String COL_UPDATED = "_uu_";
    protected static String COL_HAS_ERROR = "_ue_";
    protected WorksheetState wst;

    public String update(Messages messages, WebContext webContext) {
        Worksheet worksheet = getWorksheet(messages, webContext);
        WorksheetRow row = getWorksheetRow(worksheet, webContext);

        WorksheetColumn column = getWorksheetColumn(row, messages, webContext);
        String columnStatus = validateWorksheet(worksheet, row, column, webContext.getParameter(ERROR_MESSAGE));

        // for GAE
        getWorksheetState(webContext).persistWorksheet(worksheet);
        return columnStatus;
    }

    protected Worksheet getWorksheet(Messages messages, WebContext webContext) {
        wst = getWorksheetState(webContext);
        Worksheet worksheet = wst.retrieveWorksheet();
        if (worksheet == null) {
        	String id = webContext.getParameter("id");
            worksheet = new WorksheetImpl(id, messages);
            wst.persistWorksheet(worksheet);
        }

        return worksheet;
    }

    protected WorksheetState getWorksheetState(WebContext webContext) {
    	if (wst == null) {
    		String id = webContext.getParameter("id");
    		return new SessionWorksheetState(id, webContext);
    	}

    	return wst;
    }

    protected WorksheetRow getWorksheetRow(Worksheet worksheet, WebContext webContext) {
        Map<?, ?> parameters = webContext.getParameterMap();
        for (Object param : parameters.keySet()) {
            String parameter = (String) param;
            if (parameter.startsWith(UNIQUE_PROPERTIES)) {
                String value = webContext.getParameter(parameter);
                String property = StringUtils.substringAfter(parameter, UNIQUE_PROPERTIES);

                UniqueProperty uniqueProperty = new UniqueProperty(property, value);
                WorksheetRow row = worksheet.getRow(uniqueProperty);
                if (row == null) {
                    row = new WorksheetRow(uniqueProperty);
                    row.setRowStatus(WorksheetRowStatus.MODIFY);
                    worksheet.addRow(row);
                }

                return row;
            }
        }

        return null;
    }

    protected WorksheetColumn getWorksheetColumn(WorksheetRow row, Messages messages, WebContext webContext) {
        String property = webContext.getParameter(COLUMN_PROPERTY);
        WorksheetColumn column = row.getColumn(property);
        if (column == null) {
            String orginalValue = webContext.getParameter(ORIGINAL_VALUE);
            try {
                orginalValue = decode(orginalValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            column = new WorksheetColumn(property, orginalValue, messages);
            row.addColumn(column);
        }

        String changedValue = webContext.getParameter(CHANGED_VALUE);
        try {
            changedValue = decode(changedValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        column.setChangedValue(changedValue);

        return column;
    }

    /**
     * Validate that the columns original value is not the same as the changed value. If it is
     * then remove the column from the row.
     */
    protected String validateWorksheet(Worksheet worksheet, WorksheetRow row, WorksheetColumn column, String errorMessage) {
        String columnStatus = COL_UPDATED;

        if (StringUtils.isNotEmpty(errorMessage)) {
            try {
                column.setError(decode(errorMessage, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            columnStatus = COL_HAS_ERROR;
        } else if (column.hasError()) {
            column.removeError();
        }

        if (column.getChangedValue().equals(column.getOriginalValue())) {
            if (!row.getRowStatus().equals(WorksheetRowStatus.ADD)) {
                row.removeColumn(column);

                if (row.getColumns().size() == 0) {
                    worksheet.removeRow(row);
                }
            }

            if (!columnStatus.equals(COL_HAS_ERROR)) {
                columnStatus = COL_REMOVED;
            }
        }

        return columnStatus;
    }
}
