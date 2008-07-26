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
import org.jmesaweb.controller.HtmlTableTemplate;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.limit.Limit;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesaweb.controller.HtmlTableTemplate;

/**
 * This example is exactly like the basic example except it is using Groovy. It is 
 * also not doing any exports so it is not using the Limit object to check for
 * exports.
 *
 * @since 2.1
 * @author Jeff Johnston
 */
class BasicGroovyPresident implements HtmlTableTemplate {
    String id;
    
    String build(Collection<Object> items, HttpServletRequest request) {
        def tableFacade = [id, request] as TableFacadeImpl
        tableFacade.items = items
        tableFacade.columnProperties = ["name.firstName", "name.lastName", "term", "career"]
        tableFacade.stateAttr = "restore"

        def table = tableFacade.table
        table.caption = "Presidents"

        def firstName = table.row.getColumn("name.firstName")
        firstName.title = "First Name"

        def lastName = table.row.getColumn("name.lastName")
        lastName.title = "Last Name"

        table.tableRenderer.width = "600px"

        // Using a closure to implement a custom editor.
        firstName.cellRenderer.setCellEditor({item, property, rowcount -> 
            def value = new BasicCellEditor().getValue(item, property, rowcount);
            return """
                    <a href="http://www.whitehouse.gov/history/presidents/">
                       $value
                    </a>
                   """})

        return tableFacade.render() // Return the Html.
    }
}
