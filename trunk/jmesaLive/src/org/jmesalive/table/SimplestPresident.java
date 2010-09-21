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

import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;

public class SimplestPresident {
	public static void setTableProperties(TableModel tableModel) {
	    tableModel.setTable(getSimpleHtmlTable());
	}
	
	private static Table getSimpleHtmlTable() {
        HtmlTable htmlTable = new HtmlTable();

        HtmlRow htmlRow = new HtmlRow();
        htmlTable.setRow(htmlRow);
        
        HtmlColumn firstName = new HtmlColumn("name.firstName").title("First Name");
        HtmlColumn lastName = new HtmlColumn("name.lastName").title("Last Name");
        
		htmlRow.addColumn(firstName);
		htmlRow.addColumn(lastName);
		htmlRow.addColumn(new HtmlColumn("term"));
		htmlRow.addColumn(new HtmlColumn("career"));
		htmlRow.addColumn(new HtmlColumn("born"));

		return htmlTable;
	}
}
