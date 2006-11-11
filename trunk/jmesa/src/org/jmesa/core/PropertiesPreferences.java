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
package org.jmesa.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.jmesa.context.Context;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class PropertiesPreferences implements Preferences {
	private Logger logger = Logger.getLogger(PropertiesPreferences.class.getName());

    private final static String JMESA_PROPERTIES = "jmesa.properties";

    private Properties properties = new Properties();

    public void init(Context context, String preferencesLocation) {
        try {
            InputStream resourceAsStream = this.getClass().getResourceAsStream(JMESA_PROPERTIES);
			properties.load(resourceAsStream);
            if (StringUtils.isNotBlank(preferencesLocation)) {
                InputStream input = this.getClass().getResourceAsStream(preferencesLocation);
                if (input != null) {
                    properties.load(input);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load the eXtremeTable preferences.", e);
        }
    }

    /**
     * Get the default property.
     */
    public String getPreference(String name) {
        return (String) properties.get(name);
    }
}
