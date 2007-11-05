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

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetImpl;
import org.jmesa.worksheet.state.SessionWorksheetState;
import org.jmesa.worksheet.state.WorksheetState;

/**
 * Will store the Worksheet object in the users session by the table id. However, once the servlet
 * is set up the developer will not ever have to deal with the fact that the Worksheet object is in
 * session. The ajax calls will abstract that out from the html table side. Then the TableFacade
 * will abstract out the retrieve of the Worksheet in the controller.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        WebContext webContext = new HttpServletRequestWebContext(request);

        Worksheet worksheet = getWorksheet(webContext);

        System.out.println("The worksheet is: " + worksheet.getId());

        Map<?, ?> parameters = webContext.getParameterMap();
        for (Object param : parameters.keySet()) {
            String parameter = (String) param;
            if (parameter.startsWith("up_")) {
                String value = webContext.getParameter(parameter);
                if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, "up_");

                    System.out.println("The row prop is: " + property + " - " + value);
                }
            }
        }

    }

    Worksheet getWorksheet(WebContext webContext) {
        String id = webContext.getParameter("id");

        WorksheetState state = new SessionWorksheetState(id, webContext);

        Worksheet worksheet = state.retrieveWorksheet();

        if (worksheet == null) {
            worksheet = new WorksheetImpl(id, null);
            state.persistWorksheet(worksheet);
        }

        return worksheet;

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
