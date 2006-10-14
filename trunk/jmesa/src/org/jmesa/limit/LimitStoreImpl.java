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

/**
 * @author Jeff Johnston
 */
public class LimitStoreImpl implements LimitStore {
	private final LimitFactory limitFactory;

	public LimitStoreImpl(LimitFactory limitFactory) {
		this.limitFactory = limitFactory;
	}

	public Limit createLimit() {
		LimitImpl limit = new LimitImpl(limitFactory.getId());

		FilterSet filterSet = limitFactory.getFilterSet();
		limit.setFilterSet(filterSet);

		SortSet sortSet = limitFactory.getSortSet();
		limit.setSortSet(sortSet);

		ExportType exportType = limitFactory.getExportType();
		limit.setExportType(exportType);

		return limit;
	}

	public RowSelect createRowSelect(int maxRows, int totalRows) {
		int page = limitFactory.getPage();
		maxRows = getMaxRows(maxRows);

		return new BasicRowSelect(page, maxRows, totalRows);
	}

	public int getMaxRows(int maxRows) {
		Integer currentMaxRows = limitFactory.getMaxRows();
		if (currentMaxRows == null) {
			return maxRows;
		}

		return currentMaxRows;
	}
}
