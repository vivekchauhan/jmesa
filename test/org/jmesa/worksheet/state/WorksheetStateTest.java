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
package org.jmesa.worksheet.state;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jmesa.limit.Limit;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetStateTest {
    private static String ID = "pres";

    @Test
    public void retrieveWorksheet() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebContext webContext = new HttpServletRequestWebContext(request);

        webContext.setSessionAttribute(ID, new Limit(ID));

        WorksheetState state = new SessionWorksheetState(ID, webContext);

        Worksheet worksheet = new Worksheet(ID);
        worksheet.setWebContext(webContext);

        assertNull("The worksheet is not null.", state.retrieveWorksheet());

        state.persistWorksheet(worksheet);

        assertNotNull("The worksheet is null.", state.retrieveWorksheet());
    }

}
