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
package org.jmesa.web;

import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * <p>A general-purpose decorator object for a WebContext.  By default, all
 * methods delegate to the underlying WebContext.  Subclasses can override this
 * default behavior to mix in functionality to an existing context.</p> 
 * 
 * @version 2.3.4
 * @author bgould
 */
public class WebContextWrapper implements WebContext {
    private WebContext webContext;

    public WebContextWrapper(WebContext webContext) {
        this.webContext = webContext;
    }

    public WebContext getWebContext() {
        return webContext;
    }

    public Object getApplicationAttribute(String name) {
        return getWebContext().getApplicationAttribute(name);
    }

    public Object getApplicationInitParameter(String name) {
        return getWebContext().getApplicationInitParameter(name);
    }

    public Object getBackingObject() {
        return getWebContext().getBackingObject();
    }

    public String getContextPath() {
        return getWebContext().getContextPath();
    }

    public Locale getLocale() {
        return getWebContext().getLocale();
    }

    public Object getPageAttribute(String name) {
        return getWebContext().getPageAttribute(name);
    }

    public String getParameter(String name) {
        return getWebContext().getParameter(name);
    }

    public Map<?, ?> getParameterMap() {
        return getWebContext().getParameterMap();
    }

    public String getRealPath(String path) {
        return getWebContext().getRealPath(path);
    }

    public Object getRequestAttribute(String name) {
        return getWebContext().getRequestAttribute(name);
    }

    public Object getSessionAttribute(String name) {
        return getWebContext().getSessionAttribute(name);
    }

    public Writer getWriter() {
        return getWebContext().getWriter();
    }

    public void removeApplicationAttribute(String name) {
        getWebContext().removeApplicationAttribute(name);
    }

    public void removePageAttribute(String name) {
        getWebContext().removePageAttribute(name);
    }

    public void removeRequestAttribute(String name) {
        getWebContext().removeRequestAttribute(name);
    }

    public void removeSessionAttribute(String name) {
        getWebContext().removeSessionAttribute(name);
    }

    public void setApplicationAttribute(String name, Object value) {
        getWebContext().setApplicationAttribute(name, value);
    }

    public void setLocale(Locale locale) {
        getWebContext().setLocale(locale);
    }

    public void setPageAttribute(String name, Object value) {
        getWebContext().setPageAttribute(name, value);
    }

    public void setParameterMap(Map<?, ?> parameterMap) {
        getWebContext().setParameterMap(parameterMap);
    }

    public void setRequestAttribute(String name, Object value) {
        getWebContext().setRequestAttribute(name, value);
    }

    public void setSessionAttribute(String name, Object value) {
        getWebContext().setSessionAttribute(name, value);
    }
}
