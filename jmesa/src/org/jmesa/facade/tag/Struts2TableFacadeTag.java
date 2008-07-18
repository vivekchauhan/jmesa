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
package org.jmesa.facade.tag;

import org.jmesa.core.message.MessagesFactory;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.Struts2Messages;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.web.WebContext;

/**
 * A way to get the Struts2 specific functionality.
 *
 * @since 2.3.3
 * @author Jeff Johnston
 */
public class Struts2TableFacadeTag extends TableFacadeTag {

    @Override
    TableFacade createTableFacade() {

        TableFacade facade = TableFacadeFactory.createTableFacade(getId(), null);
        WebContext webContext = getWebContext();
        Messages messages = MessagesFactory.getMessages(webContext);
        Struts2Messages struts2Messages = new Struts2Messages(messages, webContext);
        facade.setMessages(struts2Messages);
        return facade;
    }
}
