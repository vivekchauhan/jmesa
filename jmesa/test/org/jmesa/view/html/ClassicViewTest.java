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
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeUtils;
import org.jmesa.limit.ExportType;
import org.jmesa.test.AbstractTestCase;
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

        TableFacade tableFacade = new TableFacade(ID, null);
        tableFacade.setWebContext(webContext);
        tableFacade.setCoreContext(coreContext);

        // create the table
        HtmlTable table = new HtmlTable();
        table.setTheme("jmesa");
        table.setCaption("Presidents");
        table.setWidth("500px");
        table.setStyleClass("table");

        // create the row
        HtmlRow row = new HtmlRow();
        row.setHighlighter(true);
        row.setHighlightClass("highlight");
        table.setRow(row);

        // create some reusable objects

        // create the columns
        HtmlColumn firstNameColumn = new HtmlColumn("name.firstName");
        row.addColumn(firstNameColumn);

        HtmlColumn lastNameColumn = new HtmlColumn("name.lastName");
        row.addColumn(lastNameColumn);

        HtmlColumn termColumn = new HtmlColumn("term");
        row.addColumn(termColumn);

        HtmlColumn careerColumn = new HtmlColumn("career");
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

        TableFacadeUtils.initTable(tableFacade, table);

        Object html = view.render();

        assertNotNull(html);
    }
}
