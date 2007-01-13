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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterSetTest {
	@Test
	public void isFiltered() {
		FilterSetImpl filterSet = new FilterSetImpl();
		boolean filtered = filterSet.isFiltered();
		assertFalse("default constructor", filtered);

		filterSet = getFilterSet();
		filtered = filterSet.isFiltered();
		assertTrue("filtered", filtered);
	}
	
	@Test
	public void getFilterValue() {
		FilterSetImpl filterSet = getFilterSet();
		String nickname = filterSet.getFilterValue("nickname");
		assertNotNull(nickname);
		assertEquals(nickname, "Father Of His Country");
	}

	@Test
	public void getFilter() {
		FilterSetImpl filterSet = getFilterSet();
		Filter filter = filterSet.getFilter("nickname");
		assertNotNull(filter);
		assertEquals(filter.getValue(), "Father Of His Country");
	}

	private FilterSetImpl getFilterSet() {
		FilterSetImpl filters = new FilterSetImpl();
		filters.addFilter(new Filter("fullName", "George Washington"));
		filters.addFilter(new Filter("nickname", "Father Of His Country"));
		return filters;
	}
}
