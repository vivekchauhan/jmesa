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

import org.jmesa.facade.TableFacade;
import org.jmesa.limit.Order;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.view.html.renderer.HtmlFilterRenderer;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;
import org.jmesa.view.html.renderer.HtmlRowRenderer;
import org.jmesa.view.html.renderer.HtmlTableRenderer;
import org.jmesa.worksheet.WorksheetValidation;
import org.jmesa.worksheet.editor.WorksheetEditor;

/**
 * Build an HtmlTable using the Fluent pattern.
 *
 * @since 2.4.6
 * @author Jeff Johnston
 * 
 * @deprecated Use the new TableModel for building tables.
 */
@Deprecated
public class HtmlTableBuilder {
    private final HtmlComponentFactory componentFactory;

    private HtmlTable table;

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public HtmlTableBuilder(TableFacade tableFacade) {
        this.componentFactory = new HtmlComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public TableBuilder htmlTable() {
        table = componentFactory.createTable();
        return new TableBuilder();
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public HtmlTable build() {
        return table;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public class TableBuilder {

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder htmlRow() {
            return new RowBuilder();
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder caption(String caption) {
            table.setCaption(caption);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder captionKey(String captionKey) {
            table.setCaptionKey(captionKey);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder theme(String theme) {
            table.setTheme(theme);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder tableRenderer(HtmlTableRenderer tableRenderer) {
            // reset in case other variables were set
            HtmlTableRenderer tr = table.getTableRenderer();
            tableRenderer.setWidth(tr.getWidth());
            tableRenderer.setStyle(tr.getStyle());
            tableRenderer.setStyleClass(tr.getStyleClass());
            tableRenderer.setBorder(tr.getBorder());
            tableRenderer.setCellpadding(tr.getCellpadding());
            tableRenderer.setCellspacing(tr.getCellspacing());

            table.setTableRenderer(tableRenderer);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder width(String width) {
            table.getTableRenderer().setWidth(width);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder style(String style) {
            table.getTableRenderer().setStyle(style);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder styleClass(String styleClass) {
            table.getTableRenderer().setStyleClass(styleClass);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder border(String border) {
            table.getTableRenderer().setBorder(border);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder cellpadding(String cellpadding) {
            table.getTableRenderer().setCellpadding(cellpadding);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public TableBuilder cellspacing(String cellspacing) {
            table.getTableRenderer().setCellspacing(cellspacing);
            return this;
        }
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public class RowBuilder {

        private final HtmlRow row;

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder() {
            this.row = componentFactory.createRow();
            table.setRow(row);
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder htmlColumn(String property) {
            return new ColumnBuilder(property);
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder uniqueProperty(String uniqueProperty) {
            row.setUniqueProperty(uniqueProperty);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder highlighter(boolean highlighter) {
            row.setHighlighter(highlighter);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder sortable(boolean sortable) {
            row.setSortable(sortable);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder filterable(boolean filterable) {
            row.setFilterable(filterable);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder onclick(RowEvent onclick) {
            row.setOnclick(onclick);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder onmouseover(RowEvent onmouseover) {
            row.setOnmouseover(onmouseover);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder onmouseout(RowEvent onmouseout) {
            row.setOnmouseout(onmouseout);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder rowRenderer(HtmlRowRenderer rowRenderer) {
            HtmlRowRenderer rr = row.getRowRenderer();
            rowRenderer.setStyle(rr.getStyle());
            rowRenderer.setStyleClass(rr.getStyleClass());
            rowRenderer.setEvenClass(rr.getEvenClass());
            rowRenderer.setOddClass(rr.getOddClass());
            rowRenderer.setHighlightStyle(rr.getHighlightStyle());
            rowRenderer.setHighlightClass(rr.getHighlightClass());

            row.setRowRenderer(rowRenderer);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder style(String style) {
            row.getRowRenderer().setStyle(style);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder styleClass(String styleClass) {
            row.getRowRenderer().setStyleClass(styleClass);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder evenClass(String evenClass) {
            row.getRowRenderer().setEvenClass(evenClass);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder oddClass(String oddClass) {
            row.getRowRenderer().setOddClass(oddClass);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder highlightStyle(String highlightStyle) {
            row.getRowRenderer().setHighlightStyle(highlightStyle);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public RowBuilder highlightClass(String highlightClass) {
            row.getRowRenderer().setHighlightClass(highlightClass);
            return this;
        }
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public class ColumnBuilder {
        
        private final HtmlColumn column;

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder(String property) {
            this.column = componentFactory.createColumn(property);
            table.getRow().addColumn(column);
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder title(String title) {
            column.setTitle(title);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder titleKey(String titleKey) {
            column.setTitleKey(titleKey);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder sortable(boolean sortable) {
            column.setSortable(sortable);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder sortOrder(Order sortOrder) {
            column.setSortOrder(sortOrder);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder filterable(boolean filterable) {
            column.setFilterable(filterable);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder editable(boolean editable) {
            column.setEditable(editable);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder width(String width) {
            column.setWidth(width);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder cellRenderer(HtmlCellRenderer cellRenderer) {
            // reset in case other variables were set
            HtmlCellRenderer cr = column.getCellRenderer();
            cellRenderer.setStyle(cr.getStyle());
            cellRenderer.setStyleClass(cr.getStyleClass());
            cellRenderer.setWorksheetEditor(cr.getWorksheetEditor());
            cellRenderer.setCellEditor(cr.getCellEditor());

            column.setCellRenderer(cellRenderer);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder style(String style) {
            column.getCellRenderer().setStyle(style);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder styleClass(String styleClass) {
            column.getCellRenderer().setStyleClass(styleClass);
            return this;
        }

        // worksheet

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder worksheetEditor(WorksheetEditor worksheetEditor) {
            column.getCellRenderer().setWorksheetEditor(worksheetEditor);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder addWorksheetValidation(WorksheetValidation worksheetValidation) {
            column.addWorksheetValidation(worksheetValidation);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder addCustomWorksheetValidation(WorksheetValidation worksheetValidation) {
            column.addCustomWorksheetValidation(worksheetValidation);
            return this;
        }

        // cell
        
        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder cellEditor(CellEditor cellEditor) {
            column.getCellRenderer().setCellEditor(cellEditor);
            return this;
        }

        // filter

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder filterRenderer(HtmlFilterRenderer filterRenderer) {
            // reset in case other variables were set
            HtmlFilterRenderer fr = column.getFilterRenderer();
            filterRenderer.setStyle(fr.getStyle());
            filterRenderer.setStyleClass(fr.getStyleClass());
            filterRenderer.setFilterEditor(fr.getFilterEditor());

            column.setFilterRenderer(filterRenderer);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder filterStyle(String filterStyle) {
            column.getFilterRenderer().setStyle(filterStyle);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder filterClass(String filterClass) {
            column.getFilterRenderer().setStyleClass(filterClass);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder filterEditor(FilterEditor filterEditor) {
            column.getFilterRenderer().setFilterEditor(filterEditor);
            return this;
        }

        // header

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder headerRenderer(HtmlHeaderRenderer headerRenderer) {

            // reset in case other variables were set
            HtmlHeaderRenderer hr = column.getHeaderRenderer();
            headerRenderer.setStyle(hr.getStyle());
            headerRenderer.setStyleClass(hr.getStyleClass());
            headerRenderer.setHeaderEditor(hr.getHeaderEditor());

            column.setHeaderRenderer(headerRenderer);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder headerStyle(String headerStyle) {
            column.getHeaderRenderer().setStyle(headerStyle);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder headerClass(String headerClass) {
            column.getHeaderRenderer().setStyleClass(headerClass);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder headerEditor(HeaderEditor headerEditor) {
            column.getHeaderRenderer().setHeaderEditor(headerEditor);
            return this;
        }

        /**
         * @deprecated Use the new TableModel for building tables.
         */
        @Deprecated
        public ColumnBuilder htmlColumn(String property) {
            return new ColumnBuilder(property);
        }
    }
}
