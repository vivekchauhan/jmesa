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

import org.jmesa.util.ItemUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.renderer.RowRenderer;
import org.jmesa.worksheet.UniqueProperty;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class RowImpl extends AbstractContextSupport implements Row {
    private String uniqueProperty;

    private RowRenderer rowRenderer;

    private List<Column> columns = new ArrayList<Column>();

    /**
     * <p>
     * Until the 2.3 release is officially out this code is in a very alpha state.
     * </p>
     * 
     * @param item The Bean (or Map) for the current row.
     * @since 2.3
     */
    public UniqueProperty getUniqueProperty(Object item) {
        if (uniqueProperty != null) {
            Object value = ItemUtils.getItemValue(item, uniqueProperty);
            if (value != null) {
                return new UniqueProperty(uniqueProperty, value.toString());
            }
        }

        return null;
    }

    /**
     * <p>
     * Until the 2.3 release is officially out this feature is in an alpha state.
     * </p>
     * 
     * @param uniqueProperty The property that uniquely identifies the row.
     * @since 2.3
     */
    public void setUniqueProperty(String uniqueProperty) {
        this.uniqueProperty = uniqueProperty;
    }

    public Column getColumn(String property) {
        for (Column column : columns) {
            if (column.getProperty() == null) {
                continue;
            }

            if (column.getProperty().equals(property)) {
                return column;
            }
        }
        
        throw new IllegalStateException("The column property [" + property + "] does not exist.");
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
        SupportUtils.setWebContext(rowRenderer, getWebContext());
        SupportUtils.setCoreContext(rowRenderer, getCoreContext());
        rowRenderer.setRow(this);
    }
}
