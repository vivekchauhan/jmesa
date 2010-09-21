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


import static org.jmesa.worksheet.WorksheetValidation.TRUE;
import static org.jmesa.worksheet.WorksheetValidationType.EMAIL;
import static org.jmesa.worksheet.WorksheetValidationType.REQUIRED;

import java.util.Date;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.worksheet.WorksheetValidation;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesa.worksheet.editor.RemoveRowWorksheetEditor;

/**
 * Create an editable worksheet.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetPresident {

    public static void setTableProperties(TableModel tableModel) {
        tableModel.setEditable(true);
        tableModel.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        tableModel.setStateAttr("restore");

        tableModel.setTable(getWorksheetTable());
    }
    
	private static Table getWorksheetTable() {
		HtmlTable worksheetTable = new HtmlTable().caption("Presidents").width("700px");

		// unique property to identify the row
		HtmlRow htmlRow = new HtmlRow().uniqueProperty("id");
		worksheetTable.setRow(htmlRow);

		// "remove" is a dummy column for the purpose of row deletion
		// non-fluent columns
        HtmlColumn remove = new HtmlColumn("remove");
        remove.setWorksheetEditor(new RemoveRowWorksheetEditor());
        remove.setTitle("&nbsp;");
        remove.setFilterable(false);
        remove.setSortable(false);
		
//        Map<String, String> options = new LinkedHashMap<String, String>();
//        options.put("1", "one");
//        options.put("2", "two");
//        options.put("3", "three");
//        remove.setWorksheetEditor(new DroplistWorksheetEditor(options));

        // fluent columns
		HtmlColumn chkbox = new HtmlColumn("selected").title("&nbsp;");
		chkbox.filterable(false).sortable(false);
		chkbox.setWorksheetEditor(new CheckboxWorksheetEditor());

		HtmlColumn firstName = new HtmlColumn("name.firstName").title("First Name");
		firstName.addWorksheetValidation(new WorksheetValidation(REQUIRED, TRUE));
		// this will require a javascript function validateFirstName()
		firstName.addCustomWorksheetValidation(new WorksheetValidation("customFirstNameValidation", "validateFirstName")
			.errorMessage("You cannot use foo as a value."));

		HtmlColumn lastName = new HtmlColumn("name.lastName").title("Last Name");
		lastName.addWorksheetValidation(new WorksheetValidation(REQUIRED, TRUE).errorMessage("Last name is required."));

		HtmlColumn email = new HtmlColumn("email");
		email.addWorksheetValidation(new WorksheetValidation(EMAIL, TRUE));
		
		HtmlColumn born = new HtmlColumn("born").editable(false).cellEditor(new DateCellEditor("MM/yyyy"));
		
		htmlRow.addColumn(remove);
		htmlRow.addColumn(chkbox);
		htmlRow.addColumn(firstName);
		htmlRow.addColumn(lastName);
		htmlRow.addColumn(email);
		htmlRow.addColumn(new HtmlColumn("term"));
		htmlRow.addColumn(new HtmlColumn("career"));
		htmlRow.addColumn(born);

		return worksheetTable;
	}
}