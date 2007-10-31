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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities to create objects and throw runtime exceptions.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
class ClassUtils {
    private static Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    private ClassUtils() {
        // do not instatiate
    }

    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error("Could not create the columnSort [" + className + "]", e);
            throw new RuntimeException("Could not create the class [" + className + "]", e);
        }
    }

}
