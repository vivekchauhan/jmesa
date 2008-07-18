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

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.jmesa.core.President;
import org.jmesa.core.PresidentDao;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmesa.test.AbstractTestCase;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.test.SpringParametersAdapter;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetImpl;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowImpl;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class TableFacadeUtilsTest extends AbstractTestCase {

    @Test
    public void filterWorksheetItems() {
        Collection<President> items = new PresidentDao().getPresidents();
        Worksheet worksheet = getWorksheet();

        Collection<?> results = TableFacadeUtils.filterWorksheetItems(items, worksheet);
        assertNotNull(results);
        assertTrue(results.size() == 3);
    }

    @Override
    protected Worksheet getWorksheet() {
        UniqueProperty firstRowMap = new UniqueProperty("id", "1");
        WorksheetRow firstRow = new WorksheetRowImpl(firstRowMap);

        UniqueProperty secondRowMap = new UniqueProperty("id", "2");
        WorksheetRow secondRow = new WorksheetRowImpl(secondRowMap);

        UniqueProperty thirdRowMap = new UniqueProperty("id", "3");
        WorksheetRow thirdRow = new WorksheetRowImpl(thirdRowMap);

        HttpServletRequest request = getSpringRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade(ID, request);

        Worksheet worksheet = new WorksheetWrapper(new WorksheetImpl(ID, null), facade.getWebContext());

        worksheet.addRow(firstRow);
        worksheet.addRow(secondRow);
        worksheet.addRow(thirdRow);

        return worksheet;
    }

    private HttpServletRequest getSpringRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter springParametersAdapter = new SpringParametersAdapter(request);
        createBuilder(springParametersAdapter);
        return request;
    }

    private void createBuilder(Parameters parameters) {
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setFilterWorksheet();
    }
}
