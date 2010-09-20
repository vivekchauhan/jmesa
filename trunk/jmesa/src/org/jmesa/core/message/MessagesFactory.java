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

import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PreferencesFactory;
import org.jmesa.web.WebContext;

/**
 * A factory to create different Messages.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class MessagesFactory {
    private static final String JMESA_MESSAGES_LOCATION = "jmesaMessagesLocation";

    private MessagesFactory() {}

    /**
     * @return The default messages.
     */
    public static Messages getMessages(WebContext webContext) {
        String jmesaMessagesLocation = (String) webContext.getApplicationInitParameter(JMESA_MESSAGES_LOCATION);
        Preferences preferences = PreferencesFactory.getPreferences(webContext);

        return new ResourceBundleMessages(jmesaMessagesLocation, webContext);
    }
}
