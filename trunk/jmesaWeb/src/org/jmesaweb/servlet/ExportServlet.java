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
import org.jmesa.limit.ExportType;
import org.jmesa.model.TableModel;
import org.jmesa.model.TableModelUtils;
import org.jmesa.view.component.Table;

/**
 * A quick example to show how exports would work in a servlet.
 *
 * @author Jeff Johnston
 */
public class ExportServlet extends HttpServlet {

    private PresidentDao dao = new PresidentDao();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        TableModel tableModel = new TableModel("basic", request, response);
        Table table = TableModelUtils.createTable("name.firstName", "name.lastName", "term", "career", "born");
        tableModel.setTable(table);
        tableModel.setItems(dao.getPresidents());
        tableModel.setExportTypes(ExportType.CSV, ExportType.EXCEL, ExportType.PDF);
        tableModel.render();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}

