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

import org.jmesa.view.component.DefaultRow;
import org.jmesa.view.component.DefaultTable;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.ColumnEditor;
import org.jmesa.view.editor.DefaultColumnEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class AbstractComponentFactory extends ContextSupport {
	public Table createDefaultTable() {
		DefaultTable table = new DefaultTable();
		table.setWebContext(getWebContext());
		table.setCoreContext(getCoreContext());
		
		return table;
	}

	public Row createDefaultRow() {
		DefaultRow row = new DefaultRow();
		row.setWebContext(getWebContext());
		row.setCoreContext(getCoreContext());
		
		return row;
	}	
	
	
	public ColumnEditor createDefaultColumnEditor() {
		DefaultColumnEditor editor = new DefaultColumnEditor();
		editor.setWebContext(getWebContext());
		editor.setCoreContext(getCoreContext());
		return editor;
	}
}
