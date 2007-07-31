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
package org.jmesa.view;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.CoreContext;
import org.jmesa.core.PresidentDao;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public class TableFacadeTest extends AbstractTestCase {
    @Test
    public void createHtmlTableFacade() {
        Collection<Object> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = new TableFacadeImpl("pres", request, 15, items, "name.firstName", "name.lastName", "term", "career");
        String html = facade.render();
        assertNotNull(html);
        assertTrue("There is no html rendered", html.length() > 0);
    }

    @Test
    public void getWebContext() {
        Collection<Object> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = new TableFacadeImpl("pres", request, 15, items, "name.firstName", "name.lastName", "term", "career");

        WebContext webContext = facade.getWebContext();
        assertNotNull(webContext);

        WebContext webContextToSet = new HttpServletRequestWebContext(request);
        facade.setWebContext(webContextToSet); // The WebContext set should now
        // be the one used.
        assertTrue("The web context is not the same.", webContextToSet == facade.getWebContext());
    }

    @Test
    public void getCoreContextWithItemsNotSet() {
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = new TableFacadeImpl("pres", request, "name.firstName", "name.lastName", "term", "career");

        try {
            facade.getCoreContext();
            fail("You should have needed the items.");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    @Test
    public void getCoreContextNormal() {
        Collection<Object> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = new TableFacadeImpl("pres", request, 15, items, "name.firstName", "name.lastName", "term", "career");

        CoreContext coreContext = facade.getCoreContext();
        assertNotNull(coreContext);

        CoreContext coreContextToSet = createCoreContext(facade.getWebContext());
        facade.setCoreContext(coreContextToSet); // The WebContext set should
                                                    // now be the one used.
        assertTrue("The core context is not the same.", coreContextToSet == facade.getCoreContext());
    }
}
