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
import org.jmesa.view.csv.CsvTableFactory;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.csv.CsvViewExporter;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvTableUsingTableFactory {
    public void render(HttpServletResponse response, WebContext webContext, CoreContext coreContext)
            throws Exception {
        TableFactory tableFactory = new CsvTableFactory(webContext, coreContext);
        Table table = tableFactory.createTable("firstName", "lastName", "nickName", "term", "born", "died", "education", "career", "politicalParty");
        View view = new CsvView(table, coreContext);
        ViewExporter exporter = new CsvViewExporter(view, "presidents.txt", response);
        exporter.export();
    }
}
