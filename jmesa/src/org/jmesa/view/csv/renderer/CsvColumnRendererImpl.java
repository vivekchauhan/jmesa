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
package org.jmesa.view.csv.renderer;

import org.jmesa.view.component.Column;
import org.jmesa.view.editor.ColumnEditor;
import org.jmesa.view.renderer.AbstractColumnRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvColumnRendererImpl extends AbstractColumnRenderer implements CsvColumnRenderer {
	private String delimiter;

	public CsvColumnRendererImpl(Column column, ColumnEditor editor) {
		setColumn(column);
		setColumnEditor(editor);
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Object render(Object item, int rowcount) {
		StringBuilder data = new StringBuilder();

		String property = getColumn().getProperty();
		Object value = getColumnEditor().getValue(item, property, rowcount);

		data.append(value);
		data.append(delimiter);

		return data.toString();
	}
}
