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
package org.jmesa.view.csv.renderer;

import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.renderer.AbstractCellRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 *
 * @deprecated This renderer will be easier to work with as a view.
 */
@Deprecated
public class CsvCellRenderer extends AbstractCellRenderer {
    private String delimiter;

    /**
     * @deprecated This renderer will be easier to work with as a view.
     */
    @Deprecated
    public CsvCellRenderer(Column column, CellEditor editor) {
        setColumn(column);
        setCellEditor(editor);
    }

    /**
     * @deprecated This renderer will be easier to work with as a view.
     */
    @Deprecated
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * @deprecated This renderer will be easier to work with as a view.
     */
    @Deprecated
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * @deprecated This renderer will be easier to work with as a view.
     */
    @Deprecated
    public Object render(Object item, int rowcount) {
        StringBuilder data = new StringBuilder();

        String property = getColumn().getProperty();

        Object value = getCellEditor().getValue(item, property, rowcount);
        data.append("\"").append(value).append("\"");
        data.append(delimiter);

        return data.toString();
    }
}
