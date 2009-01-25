package org.jmesaweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesaweb.portlet.PortletTable;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * <p>For JSR 168 (Portlet 1.0) we need a helper servlet to handle exports and
 * AJAX request.  If this was JSR 286, this logic could be handled using a 
 * resource request.</p>
 * 
 * <p>In this class, we render the table if it is not an export (AJAX request).
 * If it is an export, we serve that up instead.</p>
 * 
 * @author bgould 
 */
public class PortletHelperController extends AbstractController {

    private PresidentService svc;
    
    @Override
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        PortletTable table = new PortletTable(request, response, svc.getPresidents());
        
        String markup = table.getMarkup();
        if (markup != null) {
            response.getWriter().write(markup);
        }
        
        return null;
    }

    public void setPresidentService(PresidentService svc) {
        this.svc = svc;
    }
    
}
