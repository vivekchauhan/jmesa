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
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * The Struts 2 specific messages. Will use the default messages if they are not defined in Struts 2.
 *
 * @since 2.3.3
 * @author Oscar Perez
 */
public class Struts2Messages implements Messages {

    private Messages defaultMessages;
    private WebContext webContext;

    public Struts2Messages(Messages defaultMessages, WebContext webContext) {
        this.defaultMessages = defaultMessages;
        this.webContext = webContext;
    }

    /**
     * {@inheritDoc}
     */
    public String getMessage(String code) {
        return this.getMessage(code, null);
    }

    /**
     * {@inheritDoc}
     */
    public String getMessage(String code, Object[] args) {
        String message = LocalizedTextUtil.findDefaultText(code, webContext.getLocale(), args);
        if (message == null) {
            return defaultMessages.getMessage(code, args);
        }

        return message;
    }
}
