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

import org.jmesa.worksheet.Worksheet;

/**
 * Extends the TableFacadeTemplate to add support for the worksheet. Specifically
 * this will automatically add and remove rows and offer a callback method called
 * saveWorksheet() so that a worksheet can be saved before rendering the new
 * table.
 *
 * @since 2.1
 * @author Jeff Johnston
 *
 * @deprecated Use the new TableModel for building tables.
 */
@Deprecated
public class WorksheetTableFacadeTemplate extends TableFacadeTemplate {
    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public WorksheetTableFacadeTemplate(TableFacade tableFacade) {
        super(tableFacade);
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    @Override
    public String render() {
        tableFacade.setEditable(true);

        Worksheet worksheet = tableFacade.getWorksheet();
        if (worksheet.isSaving() && worksheet.hasChanges()) {
            saveWorksheet(worksheet);
            tableFacade.persistWorksheet(worksheet);
        }
        
        setup();

        if (worksheet.isAddingRow()) {
            Object addedRowObject = getAddedRowObject();
        	if (addedRowObject != null) {
        		tableFacade.addWorksheetRow(addedRowObject);
        	} else {
        		tableFacade.addWorksheetRow();
        	}
        }

        if (worksheet.isRemovingRow()) {
            tableFacade.removeWorksheetRow();
        }

        return tableFacade.render();
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected void saveWorksheet(Worksheet worksheet) {
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Object getAddedRowObject() {
    	return null;
    }
}
