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
package org.jmesa.view.html.component;

import org.jmesa.view.component.ColumnImpl;
import org.jmesa.view.html.renderer.HtmlColumnRenderer;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;
import org.jmesa.view.renderer.FilterRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlColumnImpl extends ColumnImpl implements HtmlColumn {
	private boolean filterable = true;
	private boolean sortable = true;
	private FilterRenderer filterRenderer;

	public HtmlColumnImpl(){}
	
	public HtmlColumnImpl(String property) {
		setProperty(property);
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

	public FilterRenderer getFilterRenderer() {
		return filterRenderer;
	}

	public void setFilterRenderer(FilterRenderer filterRenderer) {
		this.filterRenderer = filterRenderer;
	}
	
	@Override
	public HtmlColumnRenderer getColumnRenderer() {
		return (HtmlColumnRenderer)super.getColumnRenderer();
	}
	
	@Override
	public HtmlHeaderRenderer getHeaderRenderer() {
		return (HtmlHeaderRenderer)super.getHeaderRenderer();
	}
}
