import org.jmesaweb.controller.HtmlTableTemplate;

import java.util.Collection;
import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.view.View;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.HtmlTableFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.WebContext;

import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.BasicCellEditor;

class BasicGroovyPresident implements HtmlTableTemplate {
    String id;
    int maxRows;
    
	String render(Collection items, WebContext webContext) {
        def limitFactory = [id, webContext] as LimitFactoryImpl;
        def limit = limitFactory.createLimitAndRowSelect(maxRows, items.size());

        def factory = [webContext] as CoreContextFactoryImpl;
        def coreContext = factory.createCoreContext(items, limit);

        def tableFactory = [webContext, coreContext] as HtmlTableFactory;
        def columns = ["name.firstName", "name.lastName", "term", "career"] as String[];
        def table = tableFactory.createHtmlTable(columns); // Must use createHtmlTable because createTable 
                                                           // does not work because of Groovy bug [GROOVY-1860].
        table.caption = "Presidents";
        table.tableRenderer.width = "600px";

        def firstName = table.row.getColumn("name.firstName");
        firstName.title = "First Name";

        firstName.headerRenderer.defaultSortOrderable = false;
		firstName.cellRenderer.setCellEditor({item, property, rowcount -> 
		 	def value = new BasicCellEditor().getValue(item, property, rowcount);
            return """
                    <a href="http://www.whitehouse.gov/history/presidents/">
                       $value
                    </a>
                   """});

        def lastName = table.row.getColumn("name.lastName");
        lastName.title = "Last Name";

        def toolbarFactory = [table, webContext, coreContext] as ToolbarFactoryImpl;
        def toolbar = toolbarFactory.createToolbar();
        def view = [table, toolbar, coreContext] as HtmlView;

        return view.render().toString();
    }
}
