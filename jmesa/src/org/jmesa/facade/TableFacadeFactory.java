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
package org.jmesa.facade;

import javax.servlet.http.HttpServletRequest;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesFactory;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.core.message.Struts2Messages;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;

/**
 * A factory to create TableFacade implementations.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class TableFacadeFactory {

    public static TableFacade createTableFacade(String id, HttpServletRequest request) {
        TableFacade tableFacade = new TableFacadeImpl(id, request);
        return tableFacade;
    }

    public static TableFacade createSpringTableFacade(String id, HttpServletRequest request) {

        TableFacade tableFacade = new TableFacadeImpl(id, request);
        WebContext webContext = new HttpServletRequestWebContext(request);
        Messages messages = MessagesFactory.getMessages(webContext);
        SpringMessages springMessages = new SpringMessages(messages, webContext);
        tableFacade.setMessages(springMessages);

        return tableFacade;
    }

    public static TableFacade createStruts2TableFacade(String id, HttpServletRequest request) {

        TableFacade tableFacade = new TableFacadeImpl(id, request);
        WebContext webContext = new HttpServletRequestWebContext(request);
        Messages messages = MessagesFactory.getMessages(webContext);
        Struts2Messages struts2Messages = new Struts2Messages(messages, webContext);
        tableFacade.setMessages(struts2Messages);

        return tableFacade;
    }
}
