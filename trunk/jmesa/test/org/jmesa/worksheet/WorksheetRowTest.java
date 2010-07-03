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

import java.util.Iterator;

import org.junit.Test;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetRowTest {
    @Test
    public void removeColumn() {
        WorksheetColumn firstName = new WorksheetColumn("name.firstName", null, null);
        WorksheetColumn born = new WorksheetColumn("born", null, null);
        WorksheetColumn lastName = new WorksheetColumn("name.lastName", null, null);

        WorksheetRow row = new WorksheetRow(null);
        row.addColumn(firstName);
        row.addColumn(born);
        row.addColumn(lastName);

        assertTrue(row.getColumns().size() == 3);

        row.removeColumn(row.getColumn("born"));

        assertTrue(row.getColumns().size() == 2);

        Iterator<WorksheetColumn> iter = row.getColumns().iterator();
        assertTrue(iter.next().getProperty().equals("name.firstName"));
        assertTrue(iter.next().getProperty().equals("name.lastName"));
    }
}
