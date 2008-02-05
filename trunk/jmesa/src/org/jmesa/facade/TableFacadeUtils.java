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

import static org.jmesa.core.CoreContextFactoryImpl.JMESA_MESSAGES_LOCATION;

import org.jmesa.core.message.Messages;
import org.jmesa.core.message.ResourceBundleMessages;
import org.jmesa.web.WebContext;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class TableFacadeUtils {

    private TableFacadeUtils() {
    }

    /**
     * @return The default messages.
     */
    public static Messages getMessages(WebContext webContext) {
        String jmesaMessagesLocation = (String) webContext.getApplicationInitParameter(JMESA_MESSAGES_LOCATION);
        return new ResourceBundleMessages(jmesaMessagesLocation, webContext);
    }
}
