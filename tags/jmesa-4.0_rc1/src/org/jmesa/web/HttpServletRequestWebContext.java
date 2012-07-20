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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HttpServletRequestWebContext implements WebContext {
		
    private final HttpServletRequest request;
	private final ServletContext ctx;
    private Map<?,?> parameterMap;
    private Locale locale;

	public HttpServletRequestWebContext(HttpServletRequest request, ServletContext ctx) {
		
		this.request = request;
		this.ctx = ctx;
	}
	
    public HttpServletRequestWebContext(HttpServletRequest request) {
		
        this.request = request;
		this.ctx = request.getSession().getServletContext();
    }

    protected HttpServletRequest getHttpServletRequest() {
		
        return request;
    }

    public Object getApplicationInitParameter(String name) {
		
        return ctx.getInitParameter(name);
    }

    public Object getApplicationAttribute(String name) {
		
        return ctx.getAttribute(name);
    }

    public void setApplicationAttribute(String name, Object value) {
		
        ctx.setAttribute(name, value);
    }

    public void removeApplicationAttribute(String name) {
		
        ctx.removeAttribute(name);
    }

    public Object getPageAttribute(String name) {
		
        return request.getAttribute(name);
    }

    public void setPageAttribute(String name, Object value) {
		
        request.setAttribute(name, value);
    }

    public void removePageAttribute(String name) {
		
        request.removeAttribute(name);
    }

    public String getParameter(String name) {
		
        if (parameterMap != null) {
            String[] values = WebContextUtils.getValueAsArray(parameterMap.get(name));
            if (values != null && values.length > 0) {
                return values[0];
            }
        }

        return request.getParameter(name);
    }

    public Map<?,?> getParameterMap() {
		
        if (parameterMap != null) {
            return parameterMap;
        }

        return request.getParameterMap();
    }

    public void setParameterMap(Map<?,?> parameterMap) {
		
        this.parameterMap = parameterMap;
    }

    public Object getRequestAttribute(String name) {
		
        return request.getAttribute(name);
    }

    public void setRequestAttribute(String name, Object value) {
		
        request.setAttribute(name, value);
    }

    public void removeRequestAttribute(String name) {
		
        request.removeAttribute(name);
    }

    public Object getSessionAttribute(String name) {
		
        return request.getSession().getAttribute(name);
    }

    public void setSessionAttribute(String name, Object value) {
		
        request.getSession().setAttribute(name, value);
    }

    public void removeSessionAttribute(String name) {
		
        request.getSession().removeAttribute(name);
    }

    public Writer getWriter() {
		
        return new StringWriter();
    }

    public Locale getLocale() {
		
        if (locale != null) {
            return locale;
        }

        return request.getLocale();
    }

    public void setLocale(Locale locale) {
		
        if (this.locale == null) {
            this.locale = locale;
        }
    }

    public String getContextPath() {
		
        return request.getContextPath();
    }

    public String getRealPath(String path) {
		
        return ctx.getRealPath(path);
    }
    
    public HttpServletRequest getBackingObject() {
		
        return request;
    }
}
