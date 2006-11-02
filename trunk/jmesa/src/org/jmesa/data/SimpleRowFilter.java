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
package org.jmesa.data;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.jmesa.data.match.MatchRegistry;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class SimpleRowFilter implements RowFilter {
	private MatchRegistry matchRegistry;
	
	public SimpleRowFilter(MatchRegistry matchRegistry) {
		this.matchRegistry = matchRegistry;
	}
	
	public Collection filterRows(Collection items, Limit limit) {
        FilterSet filterSet = limit.getFilterSet();
		boolean filtered = filterSet.isFiltered();

        if (filtered) {
            Collection collection = new ArrayList();
            FilterPredicate filterPredicate = new FilterPredicate(filterSet, matchRegistry);
            CollectionUtils.select(items, filterPredicate, collection);

            return collection;
        }

        return items;
	}
}
