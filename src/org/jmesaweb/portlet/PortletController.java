package org.jmesaweb.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jmesaweb.service.PresidentService;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

public class PortletController extends AbstractController {
    
    private PresidentService presidentService;
    
    private String portletView;
    
    protected boolean isAjaxEnabled(PortletRequest request) {
        return "true".equalsIgnoreCase(
            request.getPreferences().getValue("useAjax", "false")
        );
    }
    
    @Override
    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        PortletTable.updateState(request.getParameter("jmesaId"), request, response);
    }

    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        response.setContentType(request.getResponseContentType());
        
        PortletTable table = new PortletTable(request, response, presidentService.getPresidents());
        
        request.setAttribute("table", table);
        request.setAttribute("useAjax", isAjaxEnabled(request));
        request.setAttribute("action", response.createActionURL());
        
        getPortletContext().getRequestDispatcher(portletView)
            .include(request, response);
        
        return null;
    }
    
    public void setPresidentService(PresidentService svc) {
        this.presidentService = svc;
    }
    
    public void setPortletView(String view) {
        this.portletView = view;
    }
    
}
