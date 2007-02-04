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
import org.jmesa.view.ViewExporter;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvComponentFactory;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.csv.CsvViewExporter;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvTableUsingComponentFactory {
    public void render(HttpServletResponse response, WebContext webContext, CoreContext coreContext)
            throws Exception {
        CsvComponentFactory factory = new CsvComponentFactory(webContext, coreContext);

        // create the table
        Table table = factory.createTable();

        // create the row
        Row row = factory.createRow();
        table.setRow(row);

        // create the editor
        CellEditor editor = factory.createBasicCellEditor();

        // create the columns
        Column firstNameColumn = factory.createColumn("firstName", editor);
        row.addColumn(firstNameColumn);

        Column lastNameColumn = factory.createColumn("lastName", editor);
        row.addColumn(lastNameColumn);

        Column termColumn = factory.createColumn("term", editor);
        row.addColumn(termColumn);

        Column careerColumn = factory.createColumn("career", editor);
        row.addColumn(careerColumn);

        // create the view
        CsvView view = new CsvView(table, coreContext);
        ViewExporter exporter = new CsvViewExporter(view, "presidents.txt", response);
        exporter.export();
    }
}
