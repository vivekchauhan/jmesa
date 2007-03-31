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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * A complete example in creating a JMesa table using Spring.
 * 
 * @author Jeff Johnston
 */
public class AjaxLimitPresidentController extends AbstractController {
    private LimitCoreContext limitCoreContext;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        WebContext webContext = new HttpServletRequestWebContext(request);
        CoreContext coreContext = limitCoreContext.getCoreContext(webContext);

        Object presidents = new HtmlTableUsingComponentFactory().render(webContext, coreContext);
        byte[] contents = presidents.toString().getBytes();
        response.getOutputStream().write(contents);
        return null;
    }

    public void setLimitCoreContext(LimitCoreContext limitCoreContext) {
        this.limitCoreContext = limitCoreContext;
    }
}