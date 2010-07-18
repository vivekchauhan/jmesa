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
package org.jmesa.model;

import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesFactory;
import org.jmesa.core.message.Struts2Messages;
import org.jmesa.facade.TableFacade;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;

/**
 * @since 3.0
 * @author Jeff Johnston
 */
public class Struts2TableModel extends TableModel {
    public Struts2TableModel(String id, HttpServletRequest request) {
        this(id, new HttpServletRequestWebContext(request));
    }

    public Struts2TableModel(String id, HttpServletRequest request, HttpServletResponse response) {
        this(id, new HttpServletRequestWebContext(request), response);
    }

    public Struts2TableModel(String id, WebContext webContext) {
        webContext.setLocale(ActionContext.getContext().getLocale());

        TableFacade tableFacade = new TableFacade(id, null);
        tableFacade.setWebContext(webContext);

        Messages messages = MessagesFactory.getMessages(webContext);
        Struts2Messages struts2Messages = new Struts2Messages(messages, webContext);
        tableFacade.setMessages(struts2Messages);

        super.setTableFacade(tableFacade);
    }

    public Struts2TableModel(String id, WebContext webContext, HttpServletResponse response) {
        webContext.setLocale(ActionContext.getContext().getLocale());

        TableFacade tableFacade = new TableFacade(id, null, response);
        tableFacade.setWebContext(webContext);
        
        Messages messages = MessagesFactory.getMessages(webContext);
        Struts2Messages struts2Messages = new Struts2Messages(messages, webContext);
        tableFacade.setMessages(struts2Messages);

        super.setTableFacade(tableFacade);
    }
}
