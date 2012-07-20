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
package org.jmesa.limit.state;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.jmesa.limit.Limit;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class SessionStateTest {
		
    private static String ID = "pres";

    @Test
    public void retrieveLimitFromParameter() {
		
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebContext webContext = new HttpServletRequestWebContext(request);

        webContext.setSessionAttribute(ID + "_LIMIT", new Limit(ID));

        SessionState state = new SessionState();
        state.setId(ID);
        state.setStateAttr("restore");
        state.setWebContext(webContext);

        Limit limit = state.retrieveLimit();

        assertNull("The limit is not null.", limit); // should be null until pass parameter

        request.addParameter("restore", "true");

        limit = state.retrieveLimit();

        assertNotNull("The limit is null.", limit); // should now not be null
    }

    @Test
    public void retrieveLimitFromRequest() {
		
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebContext webContext = new HttpServletRequestWebContext(request);

        webContext.setSessionAttribute(ID + "_LIMIT", new Limit(ID));

        SessionState state = new SessionState();
        state.setId(ID);
        state.setStateAttr("restore");
        state.setWebContext(webContext);

        Limit limit = state.retrieveLimit();

        assertNull("The limit is not null.", limit); // should be null until pass parameter

        request.setAttribute("restore", "true");

        limit = state.retrieveLimit();

        assertNotNull("The limit is null.", limit); // should now not be null
    }
}
