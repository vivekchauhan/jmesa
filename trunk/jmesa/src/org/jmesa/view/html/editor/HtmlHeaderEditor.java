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
package org.jmesa.view.html.editor;

import org.jmesa.limit.Limit;
import org.jmesa.limit.Order;
import org.jmesa.limit.Sort;
import org.jmesa.view.editor.AbstractHeaderEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class HtmlHeaderEditor extends AbstractHeaderEditor {
    @Override
    public HtmlHeaderRenderer getHeaderRenderer() {
        return (HtmlHeaderRenderer) super.getHeaderRenderer();
    }

    public Object getValue() {
        HtmlBuilder html = new HtmlBuilder();

        Limit limit = getCoreContext().getLimit();
        HtmlHeaderRenderer headerRenderer = getHeaderRenderer();
        HtmlColumn column = headerRenderer.getColumn();

        if (column.isSortable()) {
            Sort sort = limit.getSortSet().getSort(column.getProperty());

            html.onmouseover("this.style.cursor='pointer'");
            html.onmouseout("this.style.cursor='default'");
            int position = column.getRow().getColumns().indexOf(column);

            if (sort != null) {
                if (sort.getOrder() == Order.ASC) {
                    html.onclick("addSortToLimit('" + limit.getId() + "','" + position + "','" + column.getProperty() + "','" + Order.DESC.toParam()
                            + "');onInvokeAction('" + limit.getId() + "')");
                } else if (sort.getOrder() == Order.DESC) {
                    if (headerRenderer.isDefaultSortOrderable()) {
                        html.onclick("removeSortFromLimit('" + limit.getId() + "','" + column.getProperty() + "');onInvokeAction('" + limit.getId()
                                + "')");
                    } else {
                        html.onclick("addSortToLimit('" + limit.getId() + "','" + position + "','" + column.getProperty() + "','"
                                + Order.ASC.toParam() + "');onInvokeAction('" + limit.getId() + "')");
                    }
                }
            } else {
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

        return html.toString();
    }
}
