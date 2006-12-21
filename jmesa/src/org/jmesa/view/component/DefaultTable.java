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

import org.jmesa.view.ContextSupport;
import org.jmesa.view.renderer.TableRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultTable extends ContextSupport implements Table {
	private Row row;
	private String title;
	private TableRenderer tableRenderer;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public TableRenderer getTableRenderer() {
		return tableRenderer;
	}

	public void setTableRenderer(TableRenderer tableRenderer) {
		this.tableRenderer = tableRenderer;
	}
}
