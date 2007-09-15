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
package org.jmesa.view.component;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.renderer.RowRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class RowImpl extends AbstractContextSupport implements Row {
    List<Column> columns = new ArrayList<Column>();
    private RowRenderer rowRenderer;

    public Column getColumn(String property) {
        for (Column column : columns) {
            if (column.getProperty() == null) {
                continue;
            }

            if (column.getProperty().equals(property)) {
                return column;
            }
        }

        return null;
    }

    public Column getColumn(int index) {
        return columns.get(index);
    }

    public void addColumn(Column column) {
        column.setRow(this);
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public RowRenderer getRowRenderer() {
        return rowRenderer;
    }

    public void setRowRenderer(RowRenderer rowRenderer) {
        this.rowRenderer = rowRenderer;
    }
}
