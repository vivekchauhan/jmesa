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

import java.util.HashMap;
import java.util.Map;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterMatchRegistryImpl implements FilterMatchRegistry {
	private Map<MatchKey, FilterMatch> matches = new HashMap<MatchKey, FilterMatch>();
	
	public void addFilterMatch(MatchKey key, FilterMatch match) {
		matches.put(key, match);
	}

	public FilterMatch getFilterMatch(MatchKey key) {
		FilterMatch match = matches.get(key);
		
		if (match == null) {
			// take off property and see if find match
			key = new MatchKey(key.getType(), key.getId(), null); 
			match = matches.get(key);
		}
		
		if (match == null) {
			// take off id and property and see if find match
			key = new MatchKey(key.getType(), null, null);
			match = matches.get(key);
		}

		if (match != null) {
			return match;
		}

		throw new IllegalArgumentException("There is no Match with the MatchKey [" + key.toString() + "]");
	}
}
