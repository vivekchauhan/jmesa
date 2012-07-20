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
package org.jmesa.limit;

import java.util.List;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitUtils {
		
    /**
     * Convert the input value to a String. A String[] or List will be converted
     * to a String by using the value in the first position. In addition will
     * attempt to do a String conversion for other object types using the
     * String.valueOf() method.
     * 
     * @param value
     *            The input object to convert to a String.
     * @return The converted value.
     */
    public static String getValue(Object value) {
		
        if (value instanceof Object[]) {
            if (((Object[]) value).length > 0) {
                return String.valueOf(((Object[]) value)[0]);
            }
        } else if (value instanceof List) {
            List<?> valueList = (List<?>) value;
            if (((List<?>) valueList).size() > 0) {
                return String.valueOf(((List<?>) valueList).get(0));
            }
        }

        if (value != null) {
            return String.valueOf(value);
        }

        return "";
    }
}
