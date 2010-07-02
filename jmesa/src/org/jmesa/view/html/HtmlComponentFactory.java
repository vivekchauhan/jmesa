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
import org.jmesa.util.SupportUtils;
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
 */
public class HtmlComponentFactory extends AbstractComponentFactory {
    public HtmlComponentFactory(WebContext webContext, CoreContext coreContext) {
        setWebContext(webContext);
        setCoreContext(coreContext);
    }

    @Override
    public HtmlTable createTable() {
        HtmlTable table = new HtmlTable();
        table.setWebContext(getWebContext());
        table.setCoreContext(getCoreContext());

        HtmlTableRendererImpl tableRenderer = new HtmlTableRendererImpl(table);
        tableRenderer.setWebContext(getWebContext());
        tableRenderer.setCoreContext(getCoreContext());
        table.setTableRenderer(tableRenderer);

        return table;
    }

    @Override
    public HtmlRow createRow() {
        HtmlRow row = new HtmlRow();
        row.setWebContext(getWebContext());
        row.setCoreContext(getCoreContext());

        HtmlRowRendererImpl rowRenderer = new HtmlRowRendererImpl(row);
        rowRenderer.setWebContext(getWebContext());
        rowRenderer.setCoreContext(getCoreContext());
        row.setRowRenderer(rowRenderer);

        return row;
    }

    @Override
    public CellEditor createBasicCellEditor() {
        HtmlCellEditor editor = new HtmlCellEditor();
        editor.setWebContext(getWebContext());
        editor.setCoreContext(getCoreContext());
        return editor;
    }

    /**
     * Create a column using the BasicCellEditor.
     * 
     * @param property The column property.
     * @return The HtmlColumn instance.
     */
    @Override
    public HtmlColumn createColumn(String property) {
        return createColumn(property, createBasicCellEditor());
    }

    /**
     * Create a column that does not require cell editor.
     * 
     * @return The HtmlColumn instance.
     */
    @Override
    public HtmlColumn createColumn(CellEditor editor) {
        return createColumn(null, editor);
    }

    public HtmlColumn createColumn(String property, CellEditor editor) {
        HtmlColumn column = new HtmlColumn(property);
        column.setWebContext(getWebContext());
        column.setCoreContext(getCoreContext());

        SupportUtils.setWebContext(editor, getWebContext());
        SupportUtils.setCoreContext(editor, getCoreContext());
        
        // cell

        HtmlCellRendererImpl cellRenderer = new HtmlCellRendererImpl(column, editor);
        cellRenderer.setWebContext(getWebContext());
        cellRenderer.setCoreContext(getCoreContext());
        column.setCellRenderer(cellRenderer);

        // filter
        
        HtmlFilterRendererImpl filterRenderer = new HtmlFilterRendererImpl(column);
        filterRenderer.setWebContext(getWebContext());
        filterRenderer.setCoreContext(getCoreContext());
        column.setFilterRenderer(filterRenderer);

        HtmlFilterEditor filterEditor = new HtmlFilterEditor();
        filterEditor.setWebContext(getWebContext());
        filterEditor.setCoreContext(getCoreContext());
        filterEditor.setColumn(column);
        filterRenderer.setFilterEditor(filterEditor);
        
        // header

        HtmlHeaderRendererImpl headerRenderer = new HtmlHeaderRendererImpl(column);
        headerRenderer.setWebContext(getWebContext());
        headerRenderer.setCoreContext(getCoreContext());
        column.setHeaderRenderer(headerRenderer);

        HtmlHeaderEditor headerEditor = new HtmlHeaderEditor();
        headerEditor.setWebContext(getWebContext());
        headerEditor.setCoreContext(getCoreContext());
        headerEditor.setColumn(column);
        headerRenderer.setHeaderEditor(headerEditor);

        return column;
    }
}
