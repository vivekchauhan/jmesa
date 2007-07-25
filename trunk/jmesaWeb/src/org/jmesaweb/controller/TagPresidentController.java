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

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author Jeff Johnston
 */
public class TagPresidentController extends AbstractController {
    private static String CSV = "csv";
    private static String EXCEL = "excel";

    private PresidentService presidentService;
    private String successView;
    private String id;
    private int maxRows;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        WebContext webContext = new HttpServletRequestWebContext(request);
        Collection<Object> items = presidentService.getPresidents();
        Limit limit = getLimit(items, webContext);
        CoreContext coreContext = getCoreContext(items, limit, webContext);

        if (limit.isExportable()) {
            String type = limit.getExport().getType();
            if (type.equals(CSV)) {
                new CsvTableUsingTableFactory().render(response, webContext, coreContext);
                return null;
            }
            else if (type.equals(EXCEL)) {
                new ExcelTableUsingTableFactory().render(response, webContext, coreContext);
                return null;
            }
        }

        mv.addObject("presidents", items);

        return mv;
    }

    public Limit getLimit(Collection items, WebContext webContext) {
        LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
        Limit limit = limitFactory.createLimitAndRowSelect(maxRows, items.size());
        return limit;
    }

    public CoreContext getCoreContext(Collection<Object> items, Limit limit, WebContext webContext) {
        CoreContextFactory factory = new CoreContextFactoryImpl(webContext);
        CoreContext coreContext = factory.createCoreContext(items, limit);
        return coreContext;
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

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

}
