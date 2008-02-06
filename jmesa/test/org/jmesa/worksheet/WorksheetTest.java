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

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetTest {
    @Test
    public void removeRow() {

        UniqueProperty firstRowMap = new UniqueProperty("id", "1");
        WorksheetRow firstRow = new WorksheetRowImpl(firstRowMap);
        firstRow.setRowStatus(WorksheetRowStatus.ADD);

        UniqueProperty secondRowMap = new UniqueProperty("id", "2");
        WorksheetRow secondRow = new WorksheetRowImpl(secondRowMap);
        secondRow.setRowStatus(WorksheetRowStatus.MODIFY);

        UniqueProperty thirdRowMap = new UniqueProperty("id", "3");
        WorksheetRow thirdRow = new WorksheetRowImpl(thirdRowMap);
        thirdRow.setRowStatus(WorksheetRowStatus.DELETE);

        Worksheet worksheet = new WorksheetImpl("pres", null);
        worksheet.addRow(firstRow);
        worksheet.addRow(secondRow);
        worksheet.addRow(thirdRow);

        assertTrue("The rows are not accounted for.", worksheet.getRows().size() == 3);

        worksheet.removeRow(worksheet.getRow(secondRowMap));

        assertTrue("The rows are not accounted for.", worksheet.getRows().size() == 2);

        Iterator<WorksheetRow> iter = worksheet.getRows().iterator();
        WorksheetRowStatus status = iter.next().getRowStatus();
        assertTrue("The first row exists.",  status == WorksheetRowStatus.ADD || status == WorksheetRowStatus.DELETE);
        status = iter.next().getRowStatus();
        assertTrue("The third row exists.",  status == WorksheetRowStatus.ADD || status == WorksheetRowStatus.DELETE);
    }
}
