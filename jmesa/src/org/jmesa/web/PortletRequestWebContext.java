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

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * A JMesa context created in a portlet environment.
 * 
 * By default, session attributes are stored in APPLICATION_SCOPE.  This
 * behavior can be overriden during construction or using setSessionScope().
 *
 * @version 2.3.4
 * @author bgould
 */
public class PortletRequestWebContext implements WebContext {
		
    private final PortletRequest request;
    private final PortletContext context;
    private int sessionScope;
    private Map<?, ?> parameterMap;
    private Locale locale;

    /**
     * <p>Creates a WebContext from the given portlet request.  Note that this
     * method finds an instance of PortletContext by creating a PortletSession
     * object.  If you want to avoid creating a session, use an alternate
     * constructor.</p>
     */
    public PortletRequestWebContext(PortletRequest request) {
		
        this(request, request.getPortletSession().getPortletContext(), PortletSession.APPLICATION_SCOPE);
    }

    /**
     * An alternate constructor that does not create a session when called, and
     * allows a default session scope to be set.
     */
    public PortletRequestWebContext(PortletRequest request, PortletContext context, int sessionScope) {
		
        this.request = request;
        this.context = context;
        this.sessionScope = sessionScope;
    }

    @Override
    public Object getApplicationAttribute(String name) {
		
        return this.getPortletContext().getAttribute(name);
    }

    @Override
    public String getApplicationInitParameter(String name) {
		
        return this.getPortletContext().getInitParameter(name);
    }

    @Override
    public void removeApplicationAttribute(String name) {
		
        this.getPortletContext().removeAttribute(name);
    }

    @Override
    public void setApplicationAttribute(String name, Object value) {
		
        this.getPortletContext().setAttribute(name, value);
    }

    @Override
    public Object getPageAttribute(String name) {
		
        return getBackingObject().getAttribute(name);
    }

    @Override
    public void setPageAttribute(String name, Object value) {
		
        getBackingObject().setAttribute(name, value);
    }

    @Override
    public void removePageAttribute(String name) {
		
        getBackingObject().removeAttribute(name);
    }

    @Override
    public String getParameter(String name) {
		
        if (parameterMap != null) {
            String[] values = WebContextUtils.getValueAsArray(parameterMap.get(name));
            if (values != null && values.length > 0) {
                return values[0];
            }
        }

        return getBackingObject().getParameter(name);
    }

    @Override
    public Map<?, ?> getParameterMap() {
		
        if (parameterMap != null) {
            return parameterMap;
        }

        return getBackingObject().getParameterMap();
    }

    @Override
    public void setParameterMap(Map<?, ?> parameterMap) {
		
        this.parameterMap = parameterMap;
    }

    @Override
    public Object getRequestAttribute(String name) {
		
        return getBackingObject().getAttribute(name);
    }

    @Override
    public void setRequestAttribute(String name, Object value) {
		
        getBackingObject().setAttribute(name, value);
    }

    @Override
    public void removeRequestAttribute(String name) {
		
        getBackingObject().removeAttribute(name);
    }

    @Override
    public Object getSessionAttribute(String name) {
		
        return getBackingObject().getPortletSession().getAttribute(name, sessionScope);
    }

    @Override
    public void setSessionAttribute(String name, Object value) {
		
        getBackingObject().getPortletSession().setAttribute(name, value, sessionScope);
    }

    @Override
    public void removeSessionAttribute(String name) {
		
        getBackingObject().getPortletSession().removeAttribute(name, sessionScope);
    }

    @Override
    public Writer getWriter() {
		
        return new StringWriter();
    }

    @Override
    public Locale getLocale() {
		
        if (locale != null) {
            return locale;
        }

        return getBackingObject().getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
		
        if (this.locale == null) {
            this.locale = locale;
        }
    }

    @Override
    public String getContextPath() {
		
        return getBackingObject().getContextPath();
    }

    @Override
    public String getRealPath(String path) {
		
        return getPortletContext().getRealPath(path);
    }

    public PortletContext getPortletContext() {
		
        return context;
    }

    @Override
    public PortletRequest getBackingObject() {
		
        return request;
    }
}
