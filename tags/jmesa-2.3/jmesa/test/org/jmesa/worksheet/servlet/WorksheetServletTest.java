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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowImpl;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetServletTest {
    protected static final String ID = "pres";

    @Test
    public void getWorksheet() {
        WorksheetServlet servlet = new WorksheetServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("id", ID);
        
        WebContext webContext = new HttpServletRequestWebContext(request);

        Worksheet worksheet = servlet.getWorksheet(null, webContext);
        
        WorksheetRow row = new WorksheetRowImpl(new UniqueProperty(null, null));
        worksheet.addRow(row);

        assertNotNull(worksheet);
        assertTrue("There are no rows in the worksheet.", worksheet.getRows().size() == 1);
        
        Worksheet worksheet2 = servlet.getWorksheet(null, webContext);
        assertNotNull(worksheet2);
        assertTrue("Did not return the same worksheet.", worksheet == worksheet2);
    }
}
