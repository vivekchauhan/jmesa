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
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitImpl implements Limit {
	private final String id;
	private ExportType exportType;
	private RowSelect rowSelect;
	private FilterSet filterSet;
	private SortSet sortSet;

	public LimitImpl(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean isExported() {
		return getExportType() != null;
	}

	public ExportType getExportType() {
		return exportType;
	}

	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}

	public FilterSet getFilterSet() {
		return filterSet;
	}

	public void setFilterSet(FilterSet filterSet) {
		this.filterSet = filterSet;
	}

	public void addFilter(Filter filter) {
		filterSet.addFilter(filter);
	}

	public SortSet getSortSet() {
		return sortSet;
	}

	public void setSortSet(SortSet sortSet) {
		this.sortSet = sortSet;
	}

	public void addSort(Sort sort) {
		sortSet.addSort(sort);
	}

	public RowSelect getRowSelect() {
		return rowSelect;
	}

	public void setRowSelect(RowSelect rowSelect) {
		this.rowSelect = rowSelect;
	}
}
