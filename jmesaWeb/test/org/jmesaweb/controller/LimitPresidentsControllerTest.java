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
package org.jmesaweb.controller;

import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.jmesa.core.PresidentDao;
import org.jmesa.limit.Order;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.test.SpringParametersAdapter;
import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.service.PresidentService;
import org.junit.Test;
import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitPresidentsControllerTest {
    private static String ID = "pres";

    private PresidentService presidentService;

    @Before
    public void onSetup() {
        presidentService = createMock(PresidentService.class);
    }

    @Test
    public void handleRequestInternal() {
        PresidentDao dao = new PresidentDao();
        Collection items = dao.getPresidents();

        expect(presidentService.getPresidentsCountWithFilter(isA(PresidentFilter.class))).andReturn(43);
        expect(presidentService.getPresidentsWithFilterAndSort(isA(PresidentFilter.class), isA(PresidentSort.class), anyInt(), anyInt())).andReturn(items);

        replay(presidentService);

        LimitCoreContext limitCoreContext = new LimitCoreContext();
        limitCoreContext.setPresidentService(presidentService);
        limitCoreContext.setId(ID);
        limitCoreContext.setMaxRows(12);

        LimitPresidentController controller = new LimitPresidentController();
        controller.setLimitCoreContext(limitCoreContext);
        controller.setSuccessView("jsp/limit.jsp");

        MockHttpServletRequest request = new MockHttpServletRequest();
        Parameters parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);

        builder.addSort("firstName", Order.ASC);
        builder.addSort("lastName", Order.DESC);

        try {
            ModelAndView mav = controller.handleRequestInternal(request, null);
            String obj = (String)mav.getModel().get("presidents");
            assertNotNull(obj);
            assertTrue(obj.indexOf("Johnson") < obj.indexOf("Jackson"));
        } catch (Exception e) {
            fail(); // should not happen
        }

        verify(presidentService);
    }
}
