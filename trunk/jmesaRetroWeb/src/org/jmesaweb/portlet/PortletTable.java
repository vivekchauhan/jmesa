package org.jmesaweb.portlet;

import java.util.Collection;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;

public class PortletTable {

    private final TableFacade facade;
    private final String id;

    public PortletTable(RenderRequest request, RenderResponse response, Collection items) {
        id = response.getNamespace() + "testTable";
        facade = TableFacadeFactory.createPortletTableFacade(id, request);
        facade.setItems(items);
        init(null);
    }

    public PortletTable(HttpServletRequest request, HttpServletResponse response, Collection items) {
        id = request.getParameter("id");
        facade = TableFacadeFactory.createTableFacade(id, request);
        facade.setItems(items);
        init(response);
    }

    private void init(HttpServletResponse response) {

        facade.setColumnProperties(new String[]{"name.firstName", "name.lastName", "term", "career"});
        facade.setExportTypes(response, new ExportType[]{ExportType.CSV, ExportType.PDFP, ExportType.EXCEL});

        Table table = facade.getTable();
        table.setCaption("Presidents");

        Row row = table.getRow();

        Column firstName = row.getColumn("name.firstName");
        firstName.setTitle("First Name");

        Column lastName = row.getColumn("name.lastName");
        lastName.setTitle("Last Name");
    }
    // This is very important if you're not using AJAX
    public static void updateState(String id, ActionRequest request, ActionResponse response) {
        for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            if (name.startsWith(id)) {
                response.setRenderParameter(name, request.getParameterValues(name));
            }
        }
    }

    public String getMarkup() {
        return facade.render();
    }

    public String getId() {
        return id;
    }
}
