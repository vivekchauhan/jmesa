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
package org.jmesa.model;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.jmesa.core.CoreContext;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.state.State;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.View;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumnImpl;
import org.jmesa.view.html.component.HtmlRowImpl;
import org.jmesa.view.html.component.HtmlTableImpl;
import org.jmesa.view.html.editor.HtmlFilterEditor;
import org.jmesa.view.html.editor.HtmlHeaderEditor;
import org.jmesa.view.html.renderer.HtmlCellRendererImpl;
import org.jmesa.view.html.renderer.HtmlFilterRendererImpl;
import org.jmesa.view.html.renderer.HtmlHeaderRendererImpl;
import org.jmesa.view.html.renderer.HtmlRowRendererImpl;
import org.jmesa.view.html.renderer.HtmlTableRendererImpl;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;

/**
 * @since 3.0
 * @author Jeff Johnston
 */
public class TableModel {

    private final String id;
    private HttpServletRequest request;

    private Collection<?> items;
    private Table table;

    private TableFacade tableFacade;

    public TableModel(String id, HttpServletRequest request) {
        this.id = id;
        this.request = request;
        this.tableFacade = new TableFacadeImpl(id, request);
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    public void setPreferences(Preferences preferences) {
        
    }

    public void setMessages(Messages messages) {
        
    }

    public void setExportTypes(ExportType... exportTypes) {
        
    }

    public void setState(State state) {
        
    }

    public void setStateAttr(String stateAttr) {
        
    }

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher) {
        
    }

    public void setColumnSort(ColumnSort columnSort) {
        
    }

    public void setRowFilter(RowFilter rowFilter) {
        
    }

    public void setMaxRows(int maxRows) {
        
    }

    public void setMaxRowsIncrements(int... maxRowsIncrements) {
        
    }

    public void setToolbar(Toolbar toolbar) {
        
    }

    public void setView(View view) {
        
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String render() {
        tableFacade.setItems(items);

        renderers((HtmlTableImpl)table);
        tableFacade.setTable(table);

        return tableFacade.render();
    }

    /**
     * This is a hack until we we can refactor back
     * the components to deal better with this.
     */
    private void renderers(HtmlTableImpl htmlTable) {
        WebContext webContext = tableFacade.getWebContext();
        CoreContext coreContext = tableFacade.getCoreContext();

        // get the table set up
        
        htmlTable.setWebContext(webContext);
        htmlTable.setCoreContext(coreContext);

        HtmlTableRendererImpl tableRenderer = (HtmlTableRendererImpl)htmlTable.getTableRenderer();
        tableRenderer.setWebContext(webContext);
        tableRenderer.setCoreContext(coreContext);

        // get the row set up
        
        HtmlRowImpl htmlRow = (HtmlRowImpl)htmlTable.getRow();
        htmlRow.setWebContext(webContext);
        htmlRow.setCoreContext(coreContext);

        HtmlRowRendererImpl rowRenderer = (HtmlRowRendererImpl)htmlRow.getRowRenderer();
        rowRenderer.setWebContext(webContext);
        rowRenderer.setCoreContext(coreContext);

        // get the column set up

        for (Column column : htmlRow.getColumns()) {
            HtmlColumnImpl htmlColumn = (HtmlColumnImpl)column;
            htmlColumn.setWebContext(webContext);
            htmlColumn.setCoreContext(coreContext);

            // cell

            HtmlCellRendererImpl cellRenderer = (HtmlCellRendererImpl)htmlColumn.getCellRenderer();
            cellRenderer.setWebContext(webContext);
            cellRenderer.setCoreContext(coreContext);

            CellEditor editor = cellRenderer.getCellEditor();
            SupportUtils.setWebContext(editor, webContext);
            SupportUtils.setCoreContext(editor, coreContext);

            // filter

            HtmlFilterRendererImpl filterRenderer = (HtmlFilterRendererImpl)htmlColumn.getFilterRenderer();
            filterRenderer.setWebContext(webContext);
            filterRenderer.setCoreContext(coreContext);

            HtmlFilterEditor filterEditor = (HtmlFilterEditor)filterRenderer.getFilterEditor();
            filterEditor.setWebContext(webContext);
            filterEditor.setCoreContext(coreContext);
            filterEditor.setColumn(column);

            // header

            HtmlHeaderRendererImpl headerRenderer = (HtmlHeaderRendererImpl)htmlColumn.getHeaderRenderer();
            headerRenderer.setWebContext(webContext);
            headerRenderer.setCoreContext(coreContext);

            HtmlHeaderEditor headerEditor = (HtmlHeaderEditor)headerRenderer.getHeaderEditor();
            headerEditor.setWebContext(webContext);
            headerEditor.setCoreContext(coreContext);
            headerEditor.setColumn(column);
        }
    }
}
