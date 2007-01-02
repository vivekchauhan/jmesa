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

import org.jmesa.web.WebContext;

/**
 * <p>
 * Uses the default implementation of the Limit and RowSelect to construct
 * a LimitImpl and a BasicRowSelect object.
 * </p>
 * 
 * <p>
 * An example is as follows:
 * </p>
 * 
 * <p>
 * First you need to pass in a String id and Map of parameters to get a Limit.
 * </p> 
 * 
 * <pre>
 * String id = "pres";
 * Map parameters = request.getParameterMap();
 * 
 * LimitFactory limitFactory = new LimitFactoryImpl(id, parameters);
 * Limit limit = limitFactory.createLimit();
 * </pre>
 * 
 * Once you have a Limit you can use the FilterSet on the Limit to figure out 
 * the total rows. With the total rows you can now create a RowSelect. Lastly, 
 * set the RowSelect on the Limit and your done!
 * 
 * <pre>
 * int maxRows = 15;
 * int totalRows = getTotalRows();
 * 
 * RowSelect rowSelect = limitFactory.createRowSelect(maxRows, totalRows);
 * limit.setRowSelect(rowSelect);
 * </pre>
 * 
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultLimitFactory implements LimitFactory {
	private final LimitActionFactory limitActionFactory;

	public DefaultLimitFactory(String id, WebContext context) {
		this.limitActionFactory = new LimitActionFactoryImpl(id, context.getParameterMap());
	}

	public Limit createLimit() {
		Limit limit = new LimitImpl(limitActionFactory.getId());

		FilterSet filterSet = limitActionFactory.getFilterSet();
		limit.setFilterSet(filterSet);

		SortSet sortSet = limitActionFactory.getSortSet();
		limit.setSortSet(sortSet);

		Export export = limitActionFactory.getExport();
		limit.setExport(export);

		return limit;
	}

	public RowSelect createRowSelect(int maxRows, int totalRows) {
		int page = limitActionFactory.getPage();

		maxRows = getMaxRows(maxRows);

		return new DefaultRowSelect(page, maxRows, totalRows);
	}

	protected int getMaxRows(int maxRows) {
		Integer currentMaxRows = limitActionFactory.getMaxRows();
		if (currentMaxRows == null) {
			return maxRows;
		}

		return currentMaxRows;
	}
}
