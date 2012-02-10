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

import org.jmesa.view.html.HtmlBuilder;
import static org.jmesa.view.html.HtmlConstants.CELL_RENDERER_INCLUDE_ID;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.renderer.AbstractCellRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlCellRenderer extends AbstractCellRenderer {

    public HtmlCellRenderer() {}

    public HtmlCellRenderer(HtmlColumn column) {
        setColumn(column);
    }

    @Override
    public HtmlColumn getColumn() {
        return (HtmlColumn) super.getColumn();
    }

    protected String getId(int rowcount) {
        if (getCoreContext().getPreference(CELL_RENDERER_INCLUDE_ID).equals("false")) {
            return null;
        }

        String id = getCoreContext().getLimit().getId() + "_column_" + getColumn().getProperty() + "_" + rowcount;
        id = id.replaceAll("\\.", "_");
        return id;
    }

    public Object render(Object item, int rowcount) {
        HtmlBuilder html = new HtmlBuilder();

        HtmlColumn column = getColumn();
        
        html.td(2).id(getId(rowcount));
        html.width(getColumn().getWidth());
        html.style(column.getStyle());
        html.styleClass(column.getStyleClass());
        html.close();

        String property = getColumn().getProperty();
        Object value = column.getCellEditor().getValue(item, property, rowcount);
        if (value != null) {
            html.append(value.toString());
        } else {
            html.nbsp();
        }

        html.tdEnd();

        return html.toString();
    }
}
