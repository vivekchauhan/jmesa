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

import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.view.TableFactory;
import org.jmesa.view.View;
import org.jmesa.view.ViewExporter;
import org.jmesa.view.component.Table;
import org.jmesa.view.excel.ExcelTableFactory;
import org.jmesa.view.excel.ExcelView;
import org.jmesa.view.excel.ExcelViewExporter;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ExcelTableUsingTableFactory {
    public void render(HttpServletResponse response, WebContext webContext, CoreContext coreContext) throws Exception {
        TableFactory tableFactory = new ExcelTableFactory(webContext, coreContext);
        Table table = tableFactory.createTable("name.firstName", "name.lastName", "name.nickName", "term", "born", "died", "education", "career");
        table.setCaption("presidents");
        table.getRow().getColumn("name.firstName").setTitle("First Name");
        table.getRow().getColumn("name.lastName").setTitle("Last Name");
        table.getRow().getColumn("name.nickName").setTitle("Nick Name");
        View view = new ExcelView(table, coreContext);
        ViewExporter exporter = new ExcelViewExporter(view, "presidents.xls", response);
        exporter.export();
    }
}
