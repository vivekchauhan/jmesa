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

import java.util.Date;

import org.jmesa.core.filter.FilterMatch;
import org.jmesa.core.filter.FilterMatchRegistry;
import org.jmesa.core.filter.FilterMatchRegistryImpl;
import org.jmesa.core.filter.MatchKey;
import org.jmesa.core.filter.StringFilterMatch;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterMatchRegistryTest {
	@Test
	public void getMatch() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new MatchKey(String.class), new StringFilterMatch());
		MatchKey key = new MatchKey(String.class, "name");
		FilterMatch result = registry.getFilterMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithProperty() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new MatchKey(String.class, "name"), new StringFilterMatch());
		MatchKey key = new MatchKey(String.class, "name");
		FilterMatch result = registry.getFilterMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithErrors() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new MatchKey(Date.class), new StringFilterMatch());
		MatchKey key = new MatchKey(String.class);
		try {
			FilterMatch result = registry.getFilterMatch(key);
			assertTrue(key.equals(result));
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
}
