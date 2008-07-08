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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ResourceBundleMessages implements Messages {
    private Logger logger = LoggerFactory.getLogger(ResourceBundleMessages.class);

    private final static String JMESA_RESOURCE_BUNDLE = "org/jmesa/core/message/resource/jmesaResourceBundle";

    private ResourceBundle defaultResourceBundle;
    private ResourceBundle customResourceBundle;
    private Locale locale;

    public ResourceBundleMessages(String messagesLocation, WebContext webContext) {
        this.locale = webContext.getLocale();
        try {
            defaultResourceBundle = getResourceBundle(JMESA_RESOURCE_BUNDLE);
            if (StringUtils.isNotBlank(messagesLocation)) {
                customResourceBundle = getResourceBundle(messagesLocation);
            }
        } catch (MissingResourceException e) {
            if (logger.isErrorEnabled()) {
                logger.error("The resource bundle [" + messagesLocation + "] was not found. Make sure the path and resource name is correct.");
            }
        }
    }

    private ResourceBundle getResourceBundle(String messagesLocation)
            throws MissingResourceException {
        return ResourceBundle.getBundle(messagesLocation, locale, getClass().getClassLoader());
    }

    public String getMessage(String code) {
        return getMessage(code, null);
    }

    public String getMessage(String code, Object[] args) {
        String result = findResource(customResourceBundle, code);

        if (result == null) {
            result = findResource(defaultResourceBundle, code);
        }

        if (result != null && args != null) {
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(locale);
            formatter.applyPattern(result);
            result = formatter.format(args);
        }

        return result;
    }

    private String findResource(ResourceBundle resourceBundle, String code) {
        String result = null;

        if (resourceBundle == null) {
            return result;
        }

        try {
            result = resourceBundle.getString(code);
        } catch (MissingResourceException e) {
            // nothing we can do so eat the message
        }

        return result;
    }
}
