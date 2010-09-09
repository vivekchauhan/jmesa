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

import org.jmesaweb.domain.President;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * The real work for this example is done in the GroovyPresident.groovy file. The HtmlTableTemplate
 * interface is used to bridge the gap between Java and Groovy. Because of this the Groovy code is
 * effectively injected into this controller.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class GroovyPresidentController extends AbstractController {
    private PresidentService presidentService;
    private String successView;
    private HtmlTableTemplate htmlTableTemplate;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);
        Collection<President> items = presidentService.getPresidents();
        String html = htmlTableTemplate.build(items, request);
        request.setAttribute("presidents", html); // Set the Html in the request for the JSP.
        return mv;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setHtmlTableTemplate(HtmlTableTemplate htmlTableTemplate) {
        this.htmlTableTemplate = htmlTableTemplate;
    }
}