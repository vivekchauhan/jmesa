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
package org.jmesa.view.csv;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractComponentFactory;
import org.jmesa.view.component.Column;
import org.jmesa.view.csv.renderer.CsvCellRenderer;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 *
 * @deprecated Should build components directly now instead of using factory.
 */
@Deprecated
public class CsvComponentFactory extends AbstractComponentFactory {
    private static final String DEFAULT_DELIMITER = ",";

    private String delimiter = DEFAULT_DELIMITER;

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public CsvComponentFactory(String delimiter, WebContext webContext, CoreContext coreContext) {
        this.delimiter = delimiter;
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public CsvComponentFactory(WebContext webContext, CoreContext coreContext) {}

    public Column createColumn(String property, CellEditor editor) {
        Column column = new Column(property);

        CsvCellRenderer columnRenderer = new CsvCellRenderer(column, editor);
        columnRenderer.setDelimiter(delimiter);
        column.setCellRenderer(columnRenderer);

        return column;
    }
}
