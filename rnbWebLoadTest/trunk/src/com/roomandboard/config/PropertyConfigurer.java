/*
 * This Software is confidential and copyrighted.
 * Copyright  2003, 2007 Room & Board, Inc.  All Rights Reserved.
 * 
 * This software is the proprietary information of Room & Board, Inc.  Use 
 * is subject to license terms.
 * 
 * Title to Software and all associated intellectual property rights is retained 
 * by Room & Board, Inc. and/or its licensors.
 * 
 * Except as specifically authorized in any Supplemental License Terms, this 
 * software may not be copied.  Unless enforcement is prohibited by 
 * applicable law, you may not modify, decompile, reverse engineer this 
 * Software without the express written permission of Room & Board, Inc.
 * 
 * No right, title or interest in or to any trademark, service mark, logo or 
 * trade name of Room & Board, Inc. or its licensors is granted under 
 * this Agreement.
 */
package com.roomandboard.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * A special properties configurator for testting that loads the spring and log4j property
 * files based on a jvm parameter.
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean, ApplicationContextAware {
    
    public static final String jvmVariableName = "configLocation";

    private String configLocation;
    
    private List<String> systemAttributes;

    private ApplicationContext applicationContext;

    /**
     * After the Spring bean factory loads then configure project specific
     * settings.
     */
    public void afterPropertiesSet()
        throws Exception {

        String location = System.getProperty(jvmVariableName);

        if (StringUtils.isEmpty(location)) {
            throw new IllegalStateException("The configLocation variable is not specified in the jvm settings!");
        }

        Properties properties = loadConfigurationFiles(location);

        loadSystemAttributes(properties);
        loadLog4j(location);

        logger.info("Property loading is now complete.");
    }

    private Properties loadConfigurationFiles(String location)
        throws IOException {
        
        Resource resource = applicationContext.getResource(configLocation + "/config" + "-" + location + ".properties");
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        setProperties(properties);
        logger.info("Loading the " + resource.getFilename() + " configuration file.");
        return properties;
    }

    /**
     * <p>
     * Load the system attributes in the 'System' scope. The following is an
     * example of attributes that will be pulled from the config folder property
     * file and registered as a system property.
     * </p>
     * 
     * <pre>
     * &lt;property name=&quot;systemAttributes&quot;&gt;
     *     &lt;list&gt;
     *         &lt;value&gt;smtpserver&lt;/value&gt;
     *     &lt;/list&gt;
     * &lt;/property&gt;
     * </pre>
     * 
     * @param properties The config resource properties.
     */
    private void loadSystemAttributes(Properties properties) {

        if (systemAttributes == null) {
            return;
        }

        for (String key : systemAttributes) {
            String value = (String) properties.get(key);
            if (value != null) {
                System.setProperty(key, value);
            }
        }
    }

    /**
     * <p>
     * Configure the log4j at runtime.
     * </p>
     * 
     * @param location The environment the application is loading up in. (ie: dev, prod, local)
     */
    public void loadLog4j(String location) {

        try {
            Resource resource = applicationContext.getResource(configLocation + "/log4j-" + location + ".properties");
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            PropertyConfigurator.configure(properties);
            logger.info("Loading the " + resource.getFilename() + " configuration file.");
        } catch (Exception e) {
        // this is ok
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {

        this.applicationContext = applicationContext;
    }

    public void setSystemAttributes(List<String> systemAttributes) {

        this.systemAttributes = systemAttributes;
    }

    public void setConfigLocation(String configLocation) {

        this.configLocation = configLocation;
    }
}
