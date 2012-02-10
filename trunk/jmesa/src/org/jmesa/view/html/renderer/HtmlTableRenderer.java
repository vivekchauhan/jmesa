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
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.renderer.AbstractTableRenderer;

public class HtmlTableRenderer extends AbstractTableRenderer {
    public HtmlTableRenderer() {}

    public HtmlTableRenderer(HtmlTable table) {
        setTable(table);
    }

    @Override
    public HtmlTable getTable() {
        return (HtmlTable) super.getTable();
    }

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();
        
        HtmlTable table = getTable();
        
        html.table(0);
        String id = getCoreContext().getLimit().getId();
        html.id(id);
        html.border(table.getBorder()).cellpadding(table.getCellpadding()).cellspacing(table.getCellspacing());
        html.style(table.getStyle());
        html.styleClass(table.getStyleClass());
        html.width(table.getWidth());
        html.close();

        if (StringUtils.isNotBlank(getTable().getCaption())) {
            html.caption().close().append(getTable().getCaption()).captionEnd();
        }

        return html;
    }
}
