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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesFactory;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.facade.TableFacade;
import org.jmesa.web.HttpServletRequestSpringWebContext;
import org.jmesa.web.SpringWebContext;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @since 3.0
 * @author Jeff Johnston
 */
public class SpringTableModel extends TableModel {
    public SpringTableModel(String id, HttpServletRequest request) {
        this(id, new HttpServletRequestSpringWebContext(request));
    }

    public SpringTableModel(String id, HttpServletRequest request, HttpServletResponse response) {
        this(id, new HttpServletRequestSpringWebContext(request), response);
    }
    
    public SpringTableModel(String id, SpringWebContext springWebContext) {
        springWebContext.setLocale(LocaleContextHolder.getLocale());

        TableFacade tableFacade = new TableFacade(id, null);
        tableFacade.setWebContext(springWebContext);

        Messages messages = MessagesFactory.getMessages(springWebContext);
        SpringMessages springMessages = new SpringMessages(messages, springWebContext);
        tableFacade.setMessages(springMessages);

        super.setTableFacade(tableFacade);
    }

    public SpringTableModel(String id, SpringWebContext springWebContext, HttpServletResponse response) {
        springWebContext.setLocale(LocaleContextHolder.getLocale());

        TableFacade tableFacade = new TableFacade(id, null, response);
        tableFacade.setWebContext(springWebContext);

        Messages messages = MessagesFactory.getMessages(springWebContext);
        SpringMessages springMessages = new SpringMessages(messages, springWebContext);
        tableFacade.setMessages(springMessages);

        super.setTableFacade(tableFacade);
    }
}
