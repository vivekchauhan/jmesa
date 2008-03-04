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
package org.jmesa.core.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterMatcherRegistryImpl implements FilterMatcherRegistry {
    private Map<MatcherKey, FilterMatcher> matchers = new HashMap<MatcherKey, FilterMatcher>();

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        matchers.put(key, matcher);
    }

    public FilterMatcher getFilterMatcher(MatcherKey key) {
        FilterMatcher matcher = getFilterMatcherByProperty(key.getProperty());

        if (matcher != null) {
            return matcher;
        }

        matcher = getFilterMatcherByType(key.getType());

        if (matcher != null) {
            return matcher;
        }

        matcher = getFilterMatcherByObject(key.getType());

        if (matcher != null) {
            return matcher;
        }

        throw new IllegalArgumentException("There is no FilterMatcher with the MatcherKey [" + key.toString() + "]");
    }

    /**
     * If there is a FilterMatcher that is registered by the specific property
     * name then use that, otherwise return null. The most specific search
     * because we matching based on the property.
     * 
     * @param property The column property for the current column item.
     * @return The FilterMatcher object that will do the comparison.
     */
    private FilterMatcher getFilterMatcherByProperty(String property) {
        if (property == null) {
            return null;
        }

        for (MatcherKey key : matchers.keySet()) {
            String prop = key.getProperty();
            if (prop != null && prop.equals(property)) {
                return matchers.get(key);
            }
        }

        return null;
    }

    /**
     * If there is a FilterMatcher that is registered by the specific class type
     * then use that, otherwise return null. The intermediate search. To find a
     * match here means that a FilterMatcher only has to match by the class type.
     * 
     * @param type The Class type for the current column item.
     * @return The FilterMatcher object that will do the comparison.
     */
    private FilterMatcher getFilterMatcherByType(Class<?> type) {
        for (MatcherKey key : matchers.keySet()) {
            Class<?> typ = key.getType();
            if (typ.equals(type)) {
                return matchers.get(key);
            }
        }

        return null;
    }

    /**
     * If there is a FilterMatcher that is registered by the specific class
     * instance then use that, otherwise return null. Is the most general search
     * because will return the first match that is an instanceof the current
     * column.
     * 
     * @param type The Class type for the current column item.
     * @return The FilterMatcher object that will do the comparison.
     */
    private FilterMatcher getFilterMatcherByObject(Class<?> type) {
        FilterMatcher result = null;

        for (MatcherKey key : matchers.keySet()) {
            Class<?> typ = key.getType();
            if (typ.isAssignableFrom(type)) {
                FilterMatcher matcher = matchers.get(key);
                if (key.getType().equals(Object.class)) {
                    result = matcher; // If Object matches then make sure
                                        // there is not something more specific
                    continue;
                }

                return matcher;
            }
        }

        return result;
    }
}
