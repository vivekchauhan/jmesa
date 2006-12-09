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

import org.jmesa.core.CoreContext;


public abstract class AbstractColumnRenderer implements ColumnRenderer {
	private Column column;
	private ColumnValue columnValue;
	private CoreContext coreContext;

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public ColumnValue getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(ColumnValue columnValue) {
		this.columnValue = columnValue;
	}
	
	public CoreContext getCoreContext() {
		return coreContext;
	}

	public void setCoreContext(CoreContext coreContext) {
		this.coreContext = coreContext;
	}
}
