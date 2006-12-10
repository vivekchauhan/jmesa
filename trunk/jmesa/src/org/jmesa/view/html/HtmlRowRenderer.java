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
import org.jmesa.view.AbstractRowRenderer;
import org.jmesa.view.Row;
import org.jmesa.view.ViewUtils;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlRowRenderer extends AbstractRowRenderer {
	private String style;
	private String styleClass;
	private String highlightStyle;
	private String highlightClass;
	
	public HtmlRowRenderer(Row row, CoreContext coreContext) {
		setRow(row);
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

	protected String getStyleClass(int rowcount) {
        String styleClass = getStyleClass();
        if (StringUtils.isNotBlank(styleClass)) {
            return styleClass;
        }

        if (ViewUtils.isRowEven(rowcount)) {
            return HtmlConstants.ROW_EVEN_CSS;
        }

        return HtmlConstants.ROW_ODD_CSS;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public String getHighlightClass() {
		return highlightClass;
	}

	public void setHighlightClass(String highlightClass) {
		this.highlightClass = highlightClass;
	}

	public String getHighlightStyle() {
		return highlightStyle;
	}

	public void setHighlightStyle(String highlightStyle) {
		this.highlightStyle = highlightStyle;
	}

	protected String getOnmouseover() {
        boolean highlightRow = getRow().isHighlighter();
        if (highlightRow) {
            String highlightClass = getHighlightClass();
            if (StringUtils.isNotBlank(getRow().getOnmouseover())) {
                return "this.className='" + highlightClass + "';" + getRow().getOnmouseover();
            } else {
                return "this.className='" + highlightClass + "'";
            }
        } else {
            return getRow().getOnmouseover();
        }
    }

	protected String getOnmouseout(int rowcount) {
    	boolean highlightRow = getRow().isHighlighter();
        if (highlightRow) {
            String styleClass = getStyleClass(rowcount);
            if (StringUtils.isNotBlank(getRow().getOnmouseout())) {
                return "this.className='" + styleClass + "';" + getRow().getOnmouseout();
            } else {
                return "this.className='" + styleClass + "'";
            }
        } else {
            return getRow().getOnmouseout();
        }
    }	

	public Object render(Object item, int rowcount) {
		HtmlBuilder html = new HtmlBuilder();
		html.tr(1);
		html.style(style);
		html.styleClass(getStyleClass(rowcount));
		html.onmouseover(getOnmouseover());
		html.onmouseout(getOnmouseout(rowcount));
		html.close();
		
		return html;
	}
}
