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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.jmesa.test.AbstractTestCase;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterMatcherRegistryTest extends AbstractTestCase {
    @Test
    public void getMatchWithDateObject() {
        FilterMatcherRegistry registry = new FilterMatcherRegistry();
        
        registry.addFilterMatcher(new MatcherKey(Object.class), new StringFilterMatcher());
        registry.addFilterMatcher(new MatcherKey(Date.class), new DateFilterMatcher());
        
        MatcherKey key = new MatcherKey(Timestamp.class);
        FilterMatcher result = registry.getFilterMatcher(key);
        
        assertNotNull(result);
        assertTrue(result instanceof DateFilterMatcher);
    }

    @Test
    public void getMatchWithObject() {
        FilterMatcherRegistry registry = new FilterMatcherRegistry();
        registry.addFilterMatcher(new MatcherKey(Object.class), new StringFilterMatcher());
        MatcherKey key = new MatcherKey(String.class, "name");
        FilterMatcher result = registry.getFilterMatcher(key);
        assertNotNull(result);
    }

    @Test
	public void getMatchWithType() {
		FilterMatcherRegistry registry = new FilterMatcherRegistry();
		registry.addFilterMatcher(new MatcherKey(String.class), new StringFilterMatcher());
		MatcherKey key = new MatcherKey(String.class, "name");
		FilterMatcher result = registry.getFilterMatcher(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithProperty() {
		FilterMatcherRegistry registry = new FilterMatcherRegistry();
		registry.addFilterMatcher(new MatcherKey(String.class, "name"), new StringFilterMatcher());
		MatcherKey key = new MatcherKey(String.class, "name");
		FilterMatcher result = registry.getFilterMatcher(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithErrors() {
		FilterMatcherRegistry registry = new FilterMatcherRegistry();
		registry.addFilterMatcher(new MatcherKey(Date.class), new StringFilterMatcher());
		MatcherKey key = new MatcherKey(String.class);
		try {
			FilterMatcher result = registry.getFilterMatcher(key);
			assertTrue(key.equals(result));
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
}
