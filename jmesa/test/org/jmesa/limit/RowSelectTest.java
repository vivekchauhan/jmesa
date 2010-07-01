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
package org.jmesa.limit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Jeff Johnston
 */
public class RowSelectTest {

    @Test
    public void createRowSelect() {
        int maxRows = 2;
        int totalRows = 5;
        int page = 5;

        RowSelect rowSelect = new RowSelect(page, maxRows, totalRows);

        assertTrue("page", rowSelect.getPage() == 3);
        assertTrue("max rows", rowSelect.getMaxRows() == 2);

        int rowStart = rowSelect.getRowStart();
        int rowEnd = rowSelect.getRowEnd();

        assertTrue("row end greater than row start", rowEnd >= rowStart);
    }
}
