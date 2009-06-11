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
package org.jmesa.view.html;

import static org.junit.Assert.assertNotNull;

import org.jmesa.core.CoreContext;
import org.jmesa.limit.ExportType;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.HtmlToolbar;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ClassicViewTest extends AbstractTestCase {
    @Test
    public void render() {
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        HtmlComponentFactory factory = new HtmlComponentFactory(webContext, coreContext);

        // create the table
        HtmlTable table = factory.createTable();
        table.setTheme("jmesa");
        table.setCaption("Presidents");
        table.getTableRenderer().setWidth("500px");
        table.getTableRenderer().setStyleClass("table");

        // create the row
        HtmlRow row = factory.createRow();
        row.setHighlighter(true);
        row.getRowRenderer().setHighlightClass("highlight");
        table.setRow(row);

        // create some reusable objects

        CellEditor editor = factory.createBasicCellEditor();

        // create the columns
        HtmlColumn firstNameColumn = factory.createColumn("name.firstName", editor);
        row.addColumn(firstNameColumn);

        HtmlColumn lastNameColumn = factory.createColumn("name.lastName", editor);
        row.addColumn(lastNameColumn);

        HtmlColumn termColumn = factory.createColumn("term", editor);
        row.addColumn(termColumn);

        HtmlColumn careerColumn = factory.createColumn("career", editor);
        row.addColumn(careerColumn);

        // create the view
        HtmlToolbar toolbar = new HtmlToolbar();
        toolbar.setMaxRowsIncrements(new int[]{12,24,36});
        toolbar.setTable(table);
        toolbar.setWebContext(webContext);
        toolbar.setCoreContext(coreContext);
        toolbar.setExportTypes(ExportType.CSV);
        HtmlView view = new HtmlView();
        view.setTable(table);
        view.setToolbar(toolbar);
        view.setCoreContext(coreContext);
        Object html = view.render();

        assertNotNull(html);
    }
}
