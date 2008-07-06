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
package org.jmesa.core.message;

import org.jmesa.web.WebContext;

/**
 * The Spring specific messages. Will use the default messages if they are not defined in Spring.
 *
 * @since 2.0
 * @author Jeff Johnston
 */
public class SpringMessages implements Messages {

    private Messages defaultMessages;
    private WebContext webContext;

    public SpringMessages(Messages defaultMessages, WebContext webContext) {

        this.defaultMessages = defaultMessages;
        this.webContext = webContext;
    }

    public String getMessage(String code) {

        // Try to get the messages from Spring first.
        //WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(webContext.getServletContext());
        //webApplicationContext.getBean("messageSource");

        return defaultMessages.getMessage(code);
    }

    public String getMessage(String code, Object[] args) {

        // Try to get the messages from Spring first.
        //WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(webContext.getServletContext());
        //webApplicationContext.getBean("messageSource");

        return defaultMessages.getMessage(code, args);
    }
}
