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
        WorksheetColumn firstName = new WorksheetColumnImpl("name.firstName", null, null);
        WorksheetColumn born = new WorksheetColumnImpl("born", null, null);
        WorksheetColumn lastName = new WorksheetColumnImpl("name.lastName", null, null);

        WorksheetRow row = new WorksheetRowImpl(null);
        row.addColumn(firstName);
        row.addColumn(born);
        row.addColumn(lastName);

        assertTrue("The columns are not accounted for.", row.getColumns().size() == 3);

        row.removeColumn(row.getColumn("born"));

        assertTrue("The columns are not accounted for.", row.getColumns().size() == 2);

        Iterator<WorksheetColumn> iter = row.getColumns().iterator();
        assertTrue("The column first name exists.", iter.next().getProperty().equals("name.firstName"));
        assertTrue("The column last name exists.", iter.next().getProperty().equals("name.lastName"));
    }
}
