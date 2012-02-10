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
package org.jmesaweb.portlet;

import java.util.Collection;
import java.util.Enumeration;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;

public class PortletTable {
    
    private final TableFacade facade;
    
    private final String id;
        
    public PortletTable(RenderRequest request, RenderResponse response, Collection items) {
        id = response.getNamespace() + "testTable";
        facade = TableFacadeFactory.createPortletTableFacade(id, request);
        facade.setItems(items);
        init();
    }
    
    public PortletTable(HttpServletRequest request, HttpServletResponse response, Collection items) {
        id = request.getParameter("id");
        facade = TableFacadeFactory.createTableFacade(id, request, response);
        facade.setItems(items);
        init();
    }
    
    private void init() {        
        Table table = new Table();
        table.setCaption("Presidents");
        
        Row row = new Row();
        table.setRow(row);
        
        Column firstName = row.getColumn("name.firstName");
        firstName.setTitle("First Name");
        row.addColumn(firstName);

        Column lastName = row.getColumn("name.lastName");
        lastName.setTitle("Last Name");
        row.addColumn(lastName);
        
        facade.setTable(table);
        facade.setExportTypes(ExportType.CSV, ExportType.PDFP, ExportType.EXCEL);
    }
    
    // This is very important if you're not using AJAX
    public static void updateState(
            String id, ActionRequest request, ActionResponse response) {
        for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
            String name = (String) e.nextElement();
            if (name.startsWith(id)) {
                //System.out.println("Setting: " + name +" => " + Arrays.asList(request.getParameterValues(name)));
                response.setRenderParameter(name, request.getParameterValues(name));    
            }
        }
    }
    
    public String getMarkup() {
        return facade.render();
    }
    
    public String getId() {
        return id;
    }
    
}
