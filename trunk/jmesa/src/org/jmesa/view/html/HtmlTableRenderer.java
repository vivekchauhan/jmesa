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

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractTableRenderer;
import org.jmesa.view.Table;


public class HtmlTableRenderer extends AbstractTableRenderer {
	private String style;
	private String styleClass;
    private String border;
    private String cellpadding;
    private String cellspacing;
	
	public HtmlTableRenderer(Table table, CoreContext coreContext) {
		setTable(table);
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
	
	public String getBorder() {
		if (StringUtils.isBlank(border)) {
			return "0";
		}
		
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getCellpadding() {
		if (StringUtils.isBlank(cellpadding)) {
			return "0";
		}
		
		return cellpadding;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	public String getCellspacing() {
		if (StringUtils.isBlank(cellspacing)) {
			return "0";
		}
		
		return cellspacing;
	}

	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public Object render() {
		HtmlBuilder html = new HtmlBuilder();
		html.table(0);
		String id = getCoreContext().getLimit().getId();
		html.id(id);
		html.border(getBorder()).cellpadding(getCellpadding()).cellspacing(getCellspacing());
		html.style(style);
		html.styleClass(styleClass);
		html.close();

		return html;
	}
}
