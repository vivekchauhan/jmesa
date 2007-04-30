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

import groovy.lang.GroovyObject;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class BasicGroovyPresidentController extends AbstractController {
    private PresidentService presidentService;
    private String successView;
    private GroovyObject basicGroovyPresident;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView(successView);
        WebContext webContext = new HttpServletRequestWebContext(request);
        Collection<Object> items = presidentService.getPresidents();
        Object presidents = basicGroovyPresident.invokeMethod("getHtmlTable", new Object[] {items, webContext});
        mv.addObject("presidents", presidents);
        return mv;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

	public void setPresidentService(PresidentService presidentService) {
		this.presidentService = presidentService;
	}

    public void setBasicGroovyPresident(GroovyObject basicGroovyPresident) {
        this.basicGroovyPresident = basicGroovyPresident;
    }
}