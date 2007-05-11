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
package org.jmesa.view.html.renderer;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.renderer.AbstractRowRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlRowRendererImpl extends AbstractRowRenderer implements HtmlRowRenderer {
    private String style;
    private String styleClass;
    private String highlightStyle;
    private String highlightClass;
    private String evenClass;
    private String oddClass;

    public HtmlRowRendererImpl(HtmlRow row) {
        setRow(row);
    }

    public HtmlRow getRow() {
        return (HtmlRow) super.getRow();
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

    public String getEvenClass() {
        if (StringUtils.isBlank(evenClass)) {
            return getCoreContext().getPreference(HtmlConstants.ROW_RENDERER_EVEN_CLASS);
        }

        return evenClass;
    }

    public void setEvenClass(String evenClass) {
        this.evenClass = evenClass;
    }

    public String getOddClass() {
        if (StringUtils.isBlank(oddClass)) {
            return getCoreContext().getPreference(HtmlConstants.ROW_RENDERER_ODD_CLASS);
        }

        return oddClass;
    }

    public void setOddClass(String oddClass) {
        this.oddClass = oddClass;
    }

    protected String getStyleClass(int rowcount) {
        String styleClass = getStyleClass();
        if (StringUtils.isNotBlank(styleClass)) {
            return styleClass;
        }

        if (ViewUtils.isRowEven(rowcount)) {
            return getEvenClass();
        }

        return getOddClass();
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getHighlightClass() {
        if (StringUtils.isBlank(highlightClass)) {
            return getCoreContext().getPreference(HtmlConstants.ROW_RENDERER_HIGHLIGHT_CLASS);
        }

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

    protected String getOnmouseover(boolean highlighter, String onmouseover) {
        if (highlighter) {
            String highlightClass = getHighlightClass();
            if (StringUtils.isNotBlank(onmouseover)) {
                return "this.className='" + highlightClass + "';" + onmouseover;
            } else {
                return "this.className='" + highlightClass + "'";
            }
        } else {
            return onmouseover;
        }
    }

    protected String getOnmouseout(boolean highlighter, String onmouseout, int rowcount) {
        if (highlighter) {
            String styleClass = getStyleClass(rowcount);
            if (StringUtils.isNotBlank(onmouseout)) {
                return "this.className='" + styleClass + "';" + onmouseout;
            } else {
                return "this.className='" + styleClass + "'";
            }
        } else {
            return onmouseout;
        }
    }

    public Object render(Object item, int rowcount) {
        HtmlBuilder html = new HtmlBuilder();
        html.tr(1);
        html.id(getCoreContext().getLimit().getId() + "_row" + rowcount);
        html.style(getStyle());
        html.styleClass(getStyleClass(rowcount));
        html.onclick(getRow().getOnclick());
        html.onmouseover(getOnmouseover(getRow().isHighlighter(), getRow().getOnmouseover()));
        html.onmouseout(getOnmouseout(getRow().isHighlighter(), getRow().getOnmouseout(), rowcount));
        html.close();

        return html;
    }
}
