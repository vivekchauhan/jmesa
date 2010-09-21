package org.jmesalive.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.model.TableModel;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesalive.dao.PresidentDao;
import org.jmesalive.table.BasicExportPresident;
import org.jmesalive.table.BasicPresident;
import org.jmesalive.table.SimplestPresident;
import org.jmesalive.table.WorksheetPresident;


public class PresidentController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String type = request.getParameter("type");
		
	    String html = getTable(type, request, response); // set the Html in the request for the JSP
	    // export will return null
	    if (html == null) {
	        return;
	    }
	    request.setAttribute("presidents", html);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/" + type + ".jsp");
	    dispatcher.forward(request, response);
	}

	public String getTable(String id, HttpServletRequest request, HttpServletResponse response) {
	    PresidentDao presidentDao = new PresidentDao(new HttpServletRequestWebContext(request));
		
		// respose is required for export
	    TableModel tableModel = new TableModel(id, request, response);
	    tableModel.setItems(presidentDao.getPresidents());
	    
		WorksheetSaverImpl worksheetSaver = null;
	    
		if (id.equals("simplest")) {
			SimplestPresident.setTableProperties(tableModel);
		} else if (id.equals("basic")) {
			BasicPresident.setTableProperties(tableModel);
		} else if (id.equals("basicExport")) {
			BasicExportPresident.setTableProperties(tableModel);
		} else if (id.equals("worksheet")) {
			worksheetSaver = new WorksheetSaverImpl(presidentDao);
			
            tableModel.saveWorksheet(worksheetSaver);
            tableModel.addRowObject(PresidentDao.getNewPresident());
            
			WorksheetPresident.setTableProperties(tableModel);
		}
		
		// this will do all the work behind the scenes (e.g., save worksheet, add row object, render etc)
		String html = tableModel.render();
		
		if (id.equals("worksheet")) {
    		// it has be called after render
    		request.setAttribute("saveResults", worksheetSaver.getSaveResults());
		}
		
		return html;
	}
}