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

import org.jmesa.limit.Limit;
import org.jmesa.limit.Order;
import org.jmesa.limit.Sort;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.renderer.AbstractHeaderRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlHeaderRendererImpl extends AbstractHeaderRenderer implements HtmlHeaderRenderer {
    private String style;
    private String styleClass;

    public HtmlHeaderRendererImpl(HtmlColumn column) {
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

        html.td(2);
        html.style(getStyle());
        html.styleClass(getStyleClass());

        if (column.isSortable()) {
            Sort sort = limit.getSortSet().getSort(column.getProperty());
            if (sort != null) {
                if (sort.getOrder() == Order.ASC) {
                    html.onmouseover("this.style.cursor='pointer'");
                    html.onmouseout("this.style.cursor='default'");
                    int position = column.getRow().getColumns().indexOf(column);
                    html.onclick("addSortToLimit('" + limit.getId() + "','" + position + "','" + column.getProperty() + "','" + Order.DESC.toParam()
                            + "');onInvokeAction('" + limit.getId() + "')");
                } else if (sort.getOrder() == Order.DESC) {
                    html.onmouseover("this.style.cursor='pointer'");
                    html.onmouseout("this.style.cursor='default'");
                    html.onclick("removeSortFromLimit('" + limit.getId() + "','" + column.getProperty() + "');onInvokeAction('"
                            + limit.getId() + "')");
                }
            } else {
                html.onmouseover("this.style.cursor='pointer'");
                html.onmouseout("this.style.cursor='default'");
                int position = column.getRow().getColumns().indexOf(column);
                html.onclick("addSortToLimit('" + limit.getId() + "','" + position + "','" + column.getProperty() + "','" + Order.ASC.toParam()
                        + "');onInvokeAction('" + limit.getId() + "')");
            }
        }

        html.close();
        html.append(column.getTitle());

        if (column.isSortable()) {
            String imagesPath = HtmlUtils.imagesPath(getWebContext(), getCoreContext());
            Sort sort = limit.getSortSet().getSort(column.getProperty());
            if (sort != null) {
                if (sort.getOrder() == Order.ASC) {
                    html.nbsp();
                    html.img();
                    html.src(imagesPath + getCoreContext().getPreference(HtmlConstants.SORT_ASC_IMAGE));
                    html.style("border:0");
                    html.alt("Arrow");
                    html.end();
                } else if (sort.getOrder() == Order.DESC) {
                    html.nbsp();
                    html.img();
                    html.src(imagesPath + getCoreContext().getPreference(HtmlConstants.SORT_DESC_IMAGE));
                    html.style("border:0");
                    html.alt("Arrow");
                    html.end();
                }
            }
        }

        html.tdEnd();

        return html;
    }
}
