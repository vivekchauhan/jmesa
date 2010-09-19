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
import org.jmesa.view.AbstractComponentFactory;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.editor.HtmlFilterEditor;
import org.jmesa.view.html.editor.HtmlHeaderEditor;
import org.jmesa.view.html.renderer.HtmlCellRendererImpl;
import org.jmesa.view.html.renderer.HtmlFilterRendererImpl;
import org.jmesa.view.html.renderer.HtmlHeaderRendererImpl;
import org.jmesa.view.html.renderer.HtmlRowRendererImpl;
import org.jmesa.view.html.renderer.HtmlTableRendererImpl;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 *
 * @deprecated Should build components directly now instead of using factory.
 */
@Deprecated
public class HtmlComponentFactory extends AbstractComponentFactory {
    public HtmlComponentFactory(WebContext webContext, CoreContext coreContext) {}

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    @Override
    public HtmlTable createTable() {
        HtmlTable table = new HtmlTable();

        HtmlTableRendererImpl tableRenderer = new HtmlTableRendererImpl(table);
        table.setTableRenderer(tableRenderer);

        return table;
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    @Override
    public HtmlRow createRow() {
        HtmlRow row = new HtmlRow();

        HtmlRowRendererImpl rowRenderer = new HtmlRowRendererImpl(row);
        row.setRowRenderer(rowRenderer);

        return row;
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    @Override
    public CellEditor createBasicCellEditor() {
        HtmlCellEditor editor = new HtmlCellEditor();
        return editor;
    }

    /**
     * Create a column using the BasicCellEditor.
     * 
     * @param property The column property.
     * @return The HtmlColumn instance.
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    @Override
    public HtmlColumn createColumn(String property) {
        return createColumn(property, createBasicCellEditor());
    }

    /**
     * Create a column that does not require cell editor.
     * 
     * @return The HtmlColumn instance.
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    @Override
    public HtmlColumn createColumn(CellEditor editor) {
        return createColumn(null, editor);
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public HtmlColumn createColumn(String property, CellEditor editor) {
        HtmlColumn column = new HtmlColumn(property);
        
        // cell

        HtmlCellRendererImpl cellRenderer = new HtmlCellRendererImpl(column, editor);
        column.setCellRenderer(cellRenderer);

        // filter
        
        HtmlFilterRendererImpl filterRenderer = new HtmlFilterRendererImpl(column);
        column.setFilterRenderer(filterRenderer);

        HtmlFilterEditor filterEditor = new HtmlFilterEditor();
        filterEditor.setColumn(column);
        filterRenderer.setFilterEditor(filterEditor);
        
        // header

        HtmlHeaderRendererImpl headerRenderer = new HtmlHeaderRendererImpl(column);
        column.setHeaderRenderer(headerRenderer);

        HtmlHeaderEditor headerEditor = new HtmlHeaderEditor();
        headerEditor.setColumn(column);
        headerRenderer.setHeaderEditor(headerEditor);

        return column;
    }
}
