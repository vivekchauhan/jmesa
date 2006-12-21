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
package org.jmesa.core.match;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.jmesa.core.match.FilterMatch;
import org.jmesa.core.match.FilterMatchKey;
import org.jmesa.core.match.FilterMatchRegistry;
import org.jmesa.core.match.FilterMatchRegistryImpl;
import org.jmesa.core.match.StringMatch;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterMatchRegistryTest {
	@Test
	public void getMatch() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new FilterMatchKey(String.class), new StringMatch());
		FilterMatchKey key = new FilterMatchKey(String.class, "pres", "name");
		FilterMatch result = registry.getFilterMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchWithId() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new FilterMatchKey(String.class, "pres"), new StringMatch());
		FilterMatchKey key = new FilterMatchKey(String.class, "pres", "name");
		FilterMatch result = registry.getFilterMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithIdAndProperty() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new FilterMatchKey(String.class, "pres", "name"), new StringMatch());
		FilterMatchKey key = new FilterMatchKey(String.class, "pres", "name");
		FilterMatch result = registry.getFilterMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithErrors() {
		FilterMatchRegistry registry = new FilterMatchRegistryImpl();
		registry.addFilterMatch(new FilterMatchKey(Date.class), new StringMatch());
		FilterMatchKey key = new FilterMatchKey(String.class);
		try {
			FilterMatch result = registry.getFilterMatch(key);
			assertTrue(key.equals(result));
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
}
