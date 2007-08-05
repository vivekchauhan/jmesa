import org.jmesaweb.controller.HtmlTableTemplate;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.limit.Limit;
import org.jmesa.view.TableFacade;
import org.jmesa.view.TableFacadeImpl;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesaweb.controller.HtmlTableTemplate;

class BasicGroovyPresident implements HtmlTableTemplate {
    String id;
    int maxRows;
    
	String build(Collection<Object> items, HttpServletRequest request) {
        def facade = [id, request, maxRows, items, "name.firstName", "name.lastName", "term", "career"] as TableFacadeImpl

        def table = facade.table
        table.caption = "Presidents"

        def firstName = table.row.getColumn("name.firstName")
        firstName.title = "First Name"

        def lastName = table.row.getColumn("name.lastName")
        lastName.title = "Last Name"

        table.tableRenderer.width = "600px"

        firstName.headerRenderer.defaultSortOrderable = false
		firstName.cellRenderer.setCellEditor({item, property, rowcount -> 
		 	def value = new BasicCellEditor().getValue(item, property, rowcount);
            return """
                    <a href="http://www.whitehouse.gov/history/presidents/">
                       $value
                    </a>
                   """})

        return facade.render()
    }
}
