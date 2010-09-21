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

import static org.jmesa.limit.ExportType.CSV;
import static org.jmesa.limit.ExportType.JEXCEL;

import java.util.Date;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.limit.ExportType;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.DateCellEditor;


/**
 * Create a new TableModel and tweak it out.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class BasicExportPresident {
    
    public static void setTableProperties(TableModel tableModel) {
        tableModel.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        
        // Google App Engine (http://jmesa-live.appspot.com) does not support iText.jar, which is used for pdf export
        // tableModel.setExportTypes(new ExportType[]{CSV, JEXCEL, PDF});
        tableModel.setExportTypes(new ExportType[]{CSV, JEXCEL});
        
        tableModel.setStateAttr("restore");

        if (tableModel.isExporting()) {
            tableModel.setTable(getBasicExportTable());
        } else {
            // reuse the table of BasicPresident
            tableModel.setTable(BasicPresident.getBasicHtmlTable());
        }
    }
    
    private static Table getBasicExportTable() {
        Table table = new Table().caption("Presidents");

        Row row = new Row();
        table.setRow(row);

        Column firstName = new Column("name.firstName").title("First Name");
        Column lastName = new Column("name.lastName").title("Last Name");
        Column career = new Column("career");
        Column term = new Column("term");
        Column born = new Column("born").cellEditor(new DateCellEditor("MM/yyyy"));

        row.addColumn(firstName);
        row.addColumn(lastName);
        row.addColumn(term);
        row.addColumn(career);
        row.addColumn(born);

        return table;
    }
}
