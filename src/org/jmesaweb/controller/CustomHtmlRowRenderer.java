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
package org.jmesaweb.controller;

import org.jmesa.util.ItemUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.renderer.HtmlRowRendererImpl;

/**
 * @author Jeff Johnston
 */
public class CustomHtmlRowRenderer extends HtmlRowRendererImpl {

    @Override
    public Object render(Object item, int rowcount) {
        HtmlBuilder html = new HtmlBuilder();
        html.tr(1);
        html.id(getCoreContext().getLimit().getId() + "_row" + rowcount);

        Object value = ItemUtils.getItemValue(item, "career");
        String valueStr = String.valueOf(value).toLowerCase();
        if (valueStr.contains("soldier")) {
            html.style("background-color:#c0dba7");
        } else {
            html.style(getStyle());
        }

        html.styleClass(getStyleClass(rowcount));

        html.append(getRowEvents(item, rowcount));

        html.close();

        return html.toString();
    }
}
