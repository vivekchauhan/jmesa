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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * A complete example in creating a JMesa table using Spring.
 * 
 * @author Jeff Johnston
 */
public class BasicPresidentController extends AbstractController {
    private static Log logger = LogFactory.getLog(BasicPresidentController.class);

    private static String CSV = "csv";

    private PresidentService presidentService;
    private String successView;
    private String id;
    private int maxRows;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        WebContext webContext = new HttpServletRequestWebContext(request);
        Collection items = presidentService.getPresidents();
        Limit limit = getLimit(items, webContext);
        CoreContext coreContext = getCoreContext(items, limit, webContext);

        if (limit.isExportable()) {
            String type = limit.getExport().getType();
            if (type.equals(CSV)) {
                new CsvTableUsingTableFactory().render(response, webContext, coreContext);
                return null;
            }
        }

        Object presidents = new HtmlTableUsingTableFactory().render(webContext, coreContext);
        mv.addObject("presidents", presidents);

        return mv;
    }

    public Limit getLimit(Collection items, WebContext webContext) {
        LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
        Limit limit = limitFactory.createLimit();

        if (limit.isExportable()) {
            RowSelect rowSelect = new RowSelectImpl(1, items.size(), items.size());
            limit.setRowSelect(rowSelect);
        } else {
            RowSelect rowSelect = limitFactory.createRowSelect(maxRows, items.size());
            limit.setRowSelect(rowSelect);
        }

        return limit;
    }

    public CoreContext getCoreContext(Collection items, Limit limit, WebContext webContext) {
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