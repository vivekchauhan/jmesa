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
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;

/**
 * Build an HtmlTable using the Fluent pattern.
 *
 * @since 2.4.6
 * @author Jeff Johnston
 */
public class HtmlTableBuilder {

    private final HtmlComponentFactory componentFactory;

    private HtmlTable table;

    public HtmlTableBuilder(TableFacade tableFacade) {
        this.componentFactory = new HtmlComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
    }

    public TableBuilder htmlTable() {
        TableBuilder tableBuilder = new TableBuilder();
        table = componentFactory.createTable();
        return tableBuilder;
    }

    public HtmlTable build() {
        return table;
    }

    public class TableBuilder {

        public RowBuilder htmlRow() {
            RowBuilder rowBuilder = new RowBuilder();
            return rowBuilder;
        }

        public TableBuilder caption(String caption) {
            table.setCaption(caption);
            return this;
        }
    }

    public class RowBuilder {

        private final HtmlRow row;

        public RowBuilder() {
            this.row = componentFactory.createRow();
            table.setRow(row);
        }

        public ColumnBuilder htmlColumn(String property) {
            ColumnBuilder columnBuilder = new ColumnBuilder(property);
            return columnBuilder;
        }
    }

    public class ColumnBuilder {
        
        private final HtmlColumn column;

        public ColumnBuilder(String property) {
            this.column = componentFactory.createColumn(property);
            table.getRow().addColumn(column);
        }

        public ColumnBuilder title(String title) {
            column.setTitle(title);
            return this;
        }

        public ColumnBuilder htmlColumn(String property) {
            ColumnBuilder columnBuilder = new ColumnBuilder(property);
            return columnBuilder;
        }
    }
}
