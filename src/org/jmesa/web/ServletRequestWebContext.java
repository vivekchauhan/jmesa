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

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 *  
 * This class has been deprecated in favor of using the HttpServletRequestWebContext.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
@Deprecated
public class ServletRequestWebContext implements WebContext {
    private ServletRequest request;
    private Map<?,?> parameterMap;
    private Locale locale;

    @Deprecated
    public ServletRequestWebContext(ServletRequest request) {
        this.request = request;
    }

    @Deprecated
    public Object getApplicationInitParameter(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public Object getApplicationAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public void setApplicationAttribute(String name, Object value) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public void removeApplicationAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public Object getPageAttribute(String name) {
        return request.getAttribute(name);
    }

    @Deprecated
    public void setPageAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    @Deprecated
    public void removePageAttribute(String name) {
        request.removeAttribute(name);
    }

    @Deprecated
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Deprecated
    public Map<?,?> getParameterMap() {
        if (parameterMap != null) {
            return parameterMap;
        }

        return request.getParameterMap();
    }

    @Deprecated
    public void setParameterMap(Map<?,?> parameterMap) {
        this.parameterMap = parameterMap;
    }

    @Deprecated
    public Object getRequestAttribute(String name) {
        return request.getAttribute(name);
    }

    @Deprecated
    public void setRequestAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    @Deprecated
    public void removeRequestAttribute(String name) {
        request.removeAttribute(name);
    }

    @Deprecated
    public Object getSessionAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public void setSessionAttribute(String name, Object value) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public void removeSessionAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    @Deprecated
    public Writer getWriter() {
        return new StringWriter();
    }

    @Deprecated
    public Locale getLocale() {
        if (locale != null) {
            return locale;
        }

        return request.getLocale();
    }

    @Deprecated
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Deprecated
    public String getContextPath() {
        throw new UnsupportedOperationException("There is no context path associated with the request.");
    }
    
    @Deprecated
    public String getRealPath(String path) {
        throw new UnsupportedOperationException("There is no real path associated with the request.");
    }

    @Deprecated
    public Object getBackingObject() {
        return request;
    }
}
