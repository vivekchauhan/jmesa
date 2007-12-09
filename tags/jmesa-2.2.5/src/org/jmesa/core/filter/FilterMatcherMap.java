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

import java.util.Map;

/**
 * Get a Map of FilterMatcher objects keyed by a MatcherKey.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public interface FilterMatcherMap {
    /**
     * @return The FilterMatcher objects.
     */
    public Map<MatcherKey, FilterMatcher> getFilterMatchers();
}
