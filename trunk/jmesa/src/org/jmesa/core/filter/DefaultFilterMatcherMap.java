package org.jmesa.core.filter;

import java.util.HashMap;
import java.util.Map;

public class DefaultFilterMatcherMap implements FilterMatcherMap {

	@Override
	public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

		Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
        filterMatcherMap.put(new MatcherKey(Object.class), new StringFilterMatcher());
        return filterMatcherMap;
	}
}
