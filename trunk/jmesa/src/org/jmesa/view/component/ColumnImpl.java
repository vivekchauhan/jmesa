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
package org.jmesa.view.component;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.ContextSupport;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.renderer.ColumnRenderer;
import org.jmesa.view.renderer.HeaderRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ColumnImpl extends ContextSupport implements Column {
	private String property;
	private String title;
	private ColumnRenderer columnRenderer;
	private HeaderRenderer headerRenderer;
	private Row row;
	
	public ColumnImpl(){}
	
	public ColumnImpl(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getTitle() {
		if (StringUtils.isBlank(title)) {
			return ViewUtils.camelCaseToWord(property);	
		} else {
			
		}
		
		return null;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public ColumnRenderer getColumnRenderer() {
		return columnRenderer;
	}

	public void setColumnRenderer(ColumnRenderer columnRenderer) {
		this.columnRenderer = columnRenderer;
	}

	public HeaderRenderer getHeaderRenderer() {
		return headerRenderer;
	}

	public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		this.headerRenderer = headerRenderer;
	}

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}
}
