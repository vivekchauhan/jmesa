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
package org.jmesa.core.preference;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public final class PropertiesPreferences implements Preferences {
    private final Logger logger = LoggerFactory.getLogger(PropertiesPreferences.class);

    private static final String JMESA_PROPERTIES = "jmesa.properties";

    private Properties properties = new Properties();

    public PropertiesPreferences(String preferencesLocation, WebContext webContext) {
        try {
            InputStream resourceAsStream = getInputStream(JMESA_PROPERTIES, webContext);
            try {
                properties.load(resourceAsStream);
            } finally {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    logger.error("Could not close the JMesa default preferences.", e);
                }
            }
            if (StringUtils.isNotBlank(preferencesLocation)) {
                InputStream input = getInputStream(preferencesLocation, webContext);
                if (input != null) {
                    try {
                        properties.load(input);
                    } finally {
                        try {
                            input.close();
                        } catch (IOException e) {
                            logger.error("Could not close the JMesa preferences.", e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Could not load the JMesa preferences.", e);
        }
    }

    private InputStream getInputStream(String preferencesLocation, WebContext webContext)
            throws IOException {
        if (preferencesLocation.startsWith("WEB-INF")) {
            String path = webContext.getRealPath("WEB-INF");
            String name = StringUtils.substringAfter(preferencesLocation, "WEB-INF/");
            return new FileInputStream(path + "/" + name);
        }

        return this.getClass().getResourceAsStream(preferencesLocation);
    }

    public String getPreference(String name) {
        return (String) properties.get(name);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("properties", properties);
        return builder.toString();
    }
}
