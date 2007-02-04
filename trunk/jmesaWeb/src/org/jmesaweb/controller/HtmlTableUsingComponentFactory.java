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

import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactory;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlTableUsingComponentFactory {
    public static String CSV = "csv";

    public Object render(WebContext webContext, CoreContext coreContext) {
        HtmlComponentFactory factory = new HtmlComponentFactory(webContext, coreContext);

        // create the table
        HtmlTable table = factory.createTable();
        table.setCaption("Presidents");
        table.getTableRenderer().setWidth("600px");

        // create the row
        HtmlRow row = factory.createRow();
        table.setRow(row);

        // create the editor
        CellEditor editor = factory.createBasicCellEditor();

        // create the columns
        CellEditor customEditor = new PresidentsLinkEditor(editor);
        HtmlColumn firstNameColumn = factory.createColumn("firstName", customEditor);
        row.addColumn(firstNameColumn);

        HtmlColumn lastNameColumn = factory.createColumn("lastName", editor);
        row.addColumn(lastNameColumn);

        HtmlColumn termColumn = factory.createColumn("term", editor);
        row.addColumn(termColumn);

        HtmlColumn careerColumn = factory.createColumn("career", editor);
        row.addColumn(careerColumn);

        // create the view
        ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, CSV);
        Toolbar toolbar = toolbarFactory.createToolbar();
        View view = new HtmlView(table, toolbar, coreContext);

        return view.render();
    }

    /**
     * Create a link for the first name column. Using the decorator pattern so
     * that can wrap any kind of editor with a link.
     */
    private static class PresidentsLinkEditor implements CellEditor {
        CellEditor cellEditor;

        public PresidentsLinkEditor(CellEditor cellEditor) {
            this.cellEditor = cellEditor;
        }

        public Object getValue(Object item, String property, int rowcount) {
            Object value = cellEditor.getValue(item, property, rowcount);
            HtmlBuilder html = new HtmlBuilder();
            html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
            html.append(value);
            html.aEnd();
            return html.toString();
        }
    }
}
