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

import org.jmesa.core.match.Match;
import org.jmesa.core.match.MatchKey;
import org.jmesa.core.match.MatchRegistry;
import org.jmesa.core.match.MatchRegistryImpl;
import org.jmesa.core.match.StringMatch;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class MatchRegistryTest {
	@Test
	public void getMatch() {
		MatchRegistry registry = new DefaultMatchRegistry();
		registry.addMatch(new MatchKey(String.class), new StringMatch());
		MatchKey key = new MatchKey(String.class, "pres", "name");
		Match result = registry.getMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchWithId() {
		MatchRegistry registry = new DefaultMatchRegistry();
		registry.addMatch(new MatchKey(String.class, "pres"), new StringMatch());
		MatchKey key = new MatchKey(String.class, "pres", "name");
		Match result = registry.getMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithIdAndProperty() {
		MatchRegistry registry = new DefaultMatchRegistry();
		registry.addMatch(new MatchKey(String.class, "pres", "name"), new StringMatch());
		MatchKey key = new MatchKey(String.class, "pres", "name");
		Match result = registry.getMatch(key);
		assertNotNull(result);
	}

	@Test
	public void getMatchKeyWithErrors() {
		MatchRegistry registry = new DefaultMatchRegistry();
		registry.addMatch(new MatchKey(Date.class), new StringMatch());
		MatchKey key = new MatchKey(String.class);
		try {
			Match result = registry.getMatch(key);
			assertTrue(key.equals(result));
		} catch (IllegalArgumentException e) {
			// pa