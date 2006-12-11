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
package org.jmesa.view.html;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractColumnRenderer;
import org.jmesa.view.Column;
import org.jmesa.view.ColumnValue;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlColumnRenderer extends AbstractColumnRenderer {
	private String style;
	private String styleClass;
	
	public HtmlColumnRenderer(ColumnValue columnValue, CoreContext coreContext) {
		setColumnValue(columnValue);
		setCoreContext(coreContext);
	}
	
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public Object render(Column column, Object item, int rowcount) {
		HtmlBuilder html = new HtmlBuilder();
		html.td(2);
		html.style(style);
		html.styleClass(styleClass);
		html.close();
		
		String property = column.getProperty();
		Object value = getColumnValue().getValue(item, property, rowcount);
		if (value != null) {
			html.append(value.toString());
		}
		
		html.tdEnd();
		
		return html;
	}
}
