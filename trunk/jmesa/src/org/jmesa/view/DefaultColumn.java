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
package org.jmesa.view;

import java.util.List;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultColumn implements Column {
	private String property;
	private String title;
	private boolean filterable;
	private boolean sortable;
	private ColumnRenderer columnRenderer;
	private FilterRenderer filterRenderer;
	private HeaderRenderer headerRenderer;
	private List<CalcRenderer> calcRenderers;
	
	public DefaultColumn(){}
	
	public DefaultColumn(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public ColumnRenderer getColumnRenderer() {
		return columnRenderer;
	}

	public void setColumnRenderer(ColumnRenderer columnRenderer) {
		this.columnRenderer = columnRenderer;
	}

	public FilterRenderer getFilterRenderer() {
		return filterRenderer;
	}

	public void setFilterRenderer(FilterRenderer filterRenderer) {
		this.filterRenderer = filterRenderer;
	}

	public HeaderRenderer getHeaderRenderer() {
		return headerRenderer;
	}

	public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		this.headerRenderer = headerRenderer;
	}

	public List<CalcRenderer> getCalcRenderers() {
		return calcRenderers;
	}

	public void addCalcRenderer(CalcRenderer calcRenderer) {
		calcRenderers.add(calcRenderer);
	}
}
