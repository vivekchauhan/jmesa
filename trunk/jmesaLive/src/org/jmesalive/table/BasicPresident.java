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
package org.jmesalive.table;

import java.util.Date;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

/**
 * Create a new TableFacade and tweak it out.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class BasicPresident {
    public static void setTableProperties(TableModel tableModel) {
        tableModel.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        tableModel.setStateAttr("restore");

        tableModel.setTable(getBasicHtmlTable());
    }
    
    public static Table getBasicHtmlTable() {
        HtmlTable htmlTable = new HtmlTable().caption("Presidents").width("700px");

        HtmlRow htmlRow = new HtmlRow();
        htmlTable.setRow(htmlRow);

        HtmlColumn firstName = new HtmlColumn("name.firstName").title("First Name");
        firstName.setCellEditor(new CellEditor() {
            public Object getValue(Object item, String property, int rowcount) {
                Object value = new HtmlCellEditor().getValue(item, property, rowcount);
                HtmlBuilder html = new HtmlBuilder();
                html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
                html.append(value);
                html.aEnd();
                return html.toString();
            }
        });

        HtmlColumn lastName = new HtmlColumn("name.lastName").title("Last Name");
        HtmlColumn term = new HtmlColumn("term");
        HtmlColumn career = new HtmlColumn("career").filterEditor(new DroplistFilterEditor());
        HtmlColumn born = new HtmlColumn("born").cellEditor(new DateCellEditor("MM/yyyy"));
        
        htmlRow.addColumn(firstName);
        htmlRow.addColumn(lastName);
        htmlRow.addColumn(term);
        htmlRow.addColumn(career);
        htmlRow.addColumn(born);

        return htmlTable;
    }
}
