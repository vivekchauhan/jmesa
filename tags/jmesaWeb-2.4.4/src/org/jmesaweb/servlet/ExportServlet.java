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
package org.jmesaweb.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;

/**
 * A quick example to show how exports would work in a servlet.
 *
 * @author Jeff Johnston
 */
public class ExportServlet extends HttpServlet {

    private PresidentDao dao = new PresidentDao();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        TableFacade tableFacade = TableFacadeFactory.createTableFacade("basic", request);
        tableFacade.setColumnProperties("name.firstName", "name.lastName", "term", "career", "born");
        tableFacade.setItems(dao.getPresidents());
        tableFacade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL, ExportType.PDF);
        tableFacade.render();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}

