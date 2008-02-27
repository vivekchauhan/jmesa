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
package org.jmesaweb.controller;

import static org.jmesa.limit.ExportType.CSV;
import static org.jmesa.limit.ExportType.EXCEL;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.Limit;
import org.jmesaweb.domain.President;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Because we are using tags for this example the two things we need to do in the controller is get
 * the items to use, and process the exports. The reason we do not need to pass the maxRows to the
 * tableFacade is because the exports only care about the total rows because exports always export
 * everything.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class TagPresidentController extends AbstractController {
    private PresidentService presidentService;
    private String successView;
    private String id; // The unique table id.

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        Collection<President> items = presidentService.getPresidents();

        TableFacade tableFacade = new TableFacadeImpl(id, request);
        tableFacade.setItems(items);
        tableFacade.setColumnProperties("name.firstName", "name.lastName", "term", "career", "born");
        tableFacade.setExportTypes(response, CSV, EXCEL); // Tell the tableFacade what exports to use.
        tableFacade.addFilterMatcherMap(new TagFilterMatcherMap());

        Limit limit = tableFacade.getLimit();
        if (limit.isExported()) {
            tableFacade.getTable().setCaption("Presidents");
            tableFacade.getTable().getRow().getColumn("name.firstName").setTitle("First Name");
            tableFacade.getTable().getRow().getColumn("name.lastName").setTitle("Last Name");
            tableFacade.render();
            return null;
        }

        request.setAttribute("presidents", items); // Set the items in the request for the TableTag to use.

        return mv;
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setId(String id) {
        this.id = id;
    }
}
