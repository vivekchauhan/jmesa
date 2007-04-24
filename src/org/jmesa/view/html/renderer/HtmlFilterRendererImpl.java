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

import org.jmesa.limit.Filter;
import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.renderer.AbstractFilterRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlFilterRendererImpl extends AbstractFilterRenderer implements HtmlFilterRenderer {
    private String style;
    private String styleClass;

    public HtmlFilterRendererImpl(HtmlColumn column) {
        setColumn(column);
    }

    public HtmlColumn getColumn() {
        return (HtmlColumn) super.getColumn();
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

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();

        Limit limit = getCoreContext().getLimit();
        HtmlColumn column = getColumn();
        String property = column.getProperty();
        Filter filter = limit.getFilterSet().getFilter(property);

        html.td(2);
        html.style(getStyle());
        html.styleClass(getStyleClass());
        html.close();
        
        String filterValue = "";
        if (filter != null) {
            filterValue = filter.getValue();
        }

        html.div();
        html.onclick("createDynFilter(this, '" + limit.getId() + "','" + column.getProperty() + "')");
        html.close();
        html.append(filterValue);

        String imagesPath = HtmlUtils.imagesPath(getWebContext(), getCoreContext());
        html.img().src(imagesPath + getCoreContext().getPreference(HtmlConstants.DROPLIST_HANDLE_IMAGE));
        html.alt("filter");
        html.end();
        html.divEnd();

        html.tdEnd();

        return html.toString();
    }
}
