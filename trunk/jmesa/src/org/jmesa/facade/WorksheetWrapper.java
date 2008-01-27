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
package org.jmesa.facade;

import static org.jmesa.worksheet.servlet.WorksheetServlet.SAVE_WORKSHEET;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.Messages;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetRow;

/**
 * A wrapper for the Worksheet to implement the methods that need a request.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetWrapper implements Worksheet {

    private Worksheet worksheet;
    private WebContext webContext;

    public WorksheetWrapper(Worksheet worksheet, WebContext webContext) {
        this.worksheet = worksheet;
        this.webContext = webContext;
    }

    public String getId() {
        return worksheet.getId();
    }

    public Messages getMessages() {
        return worksheet.getMessages();
    }

    public WorksheetRow getRow(Map<String, String> uniqueProperties) {
        return worksheet.getRow(uniqueProperties);
    }

    public void addRow(WorksheetRow row) {
        worksheet.addRow(row);
    }

    public Collection<WorksheetRow> getRows() {
        return worksheet.getRows();
    }

    public void removeRow(WorksheetRow row) {
        worksheet.removeRow(row);
    }

    /**
     * The main reason to wrap the Worksheet is to give access to the request
     * so can dynamically check to see if the user is saving the worksheet.
     * 
     * @return Is true is saving worksheet.
     */
    public boolean isSaved() {
        String save = webContext.getParameter(getId()  + "_" + SAVE_WORKSHEET);
        return StringUtils.isNotEmpty(save);
    }

    public boolean hasChanges() {
        return worksheet.hasChanges();
    }

    public void removeAllChanges() {
        worksheet.removeAllChanges();
    }

    @Override
    public String toString() {
        return worksheet.toString();
    }
}
