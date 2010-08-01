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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.5.2
 * @author Siddhant Agrawal
 */
public class ResourceBundleMessagesMap implements Messages {
    private Logger logger = LoggerFactory.getLogger(ResourceBundleMessages.class);

    private static final String JMESA_RESOURCE_BUNDLE = "org/jmesa/core/message/resource/jmesaResourceBundle";

    private Map<String, String> defaultResourceBundleMap;
    private Map<String, String> customResourceBundleMap;
    private Locale locale;

    public ResourceBundleMessagesMap(String messagesLocation, WebContext webContext) {
        this.locale = webContext.getLocale();
        try {
            defaultResourceBundleMap = getResourceBundleMap(JMESA_RESOURCE_BUNDLE);
            if (StringUtils.isNotBlank(messagesLocation)) {
                customResourceBundleMap = getResourceBundleMap(messagesLocation);
            }
        } catch (MissingResourceException e) {
            if (logger.isErrorEnabled()) {
                logger.error("The resource bundle [" + messagesLocation + "] was not found. Make sure the path and resource name is correct.");
            }
        }
    }
    
    private Map<String, String> getResourceBundleMap(String messagesLocation) throws MissingResourceException {
    	ResourceBundle resourceBundle = ResourceBundle.getBundle(messagesLocation, locale, getClass().getClassLoader());
    	
    	if (resourceBundle == null) {
    		return Collections.emptyMap();
    	}

    	Map<String, String> resourceBundleMap = new HashMap<String, String>();
    	Enumeration<String> e = resourceBundle.getKeys();
    	while (e.hasMoreElements()) {
    		String code = e.nextElement();
    		resourceBundleMap.put(code, resourceBundle.getString(code));
    	}

    	return resourceBundleMap;
    }

    public String getMessage(String code) {
        return getMessage(code, null);
    }

    public String getMessage(String code, Object[] args) {
        String result = findResource(customResourceBundleMap, code);

        if (result == null) {
            result = findResource(defaultResourceBundleMap, code);
        }

        if (result != null && args != null) {
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(locale);
            formatter.applyPattern(result);
            result = formatter.format(args);
        }

        return result;
    }

    private String findResource(Map<String, String> resourceBundleMap, String code) {

        if (resourceBundleMap == null) {
            return null;
        }

        return resourceBundleMap.get(code);
    }
}