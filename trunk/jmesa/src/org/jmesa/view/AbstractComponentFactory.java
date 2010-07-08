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
package org.jmesa.view;

import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.editor.NumberCellEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 *
 * @deprecated Should build components directly now instead of using factory.
 */
@Deprecated
public abstract class AbstractComponentFactory implements ComponentFactory {
    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public Table createTable() {
        return new Table();
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public Row createRow() {
        return new Row();
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public CellEditor createBasicCellEditor() {
        return new BasicCellEditor();
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public CellEditor createDateCellEditor(String pattern) {
        return new DateCellEditor(pattern);
    }

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public CellEditor createNumberCellEditor(String pattern) {
        return new NumberCellEditor(pattern);
    }

    /**
     * Create a column using the BasicCellEditor.
     * 
     * @param property The column property.
     * @return The HtmlColumn instance.
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public Column createColumn(String property) {
        return createColumn(property, createBasicCellEditor());
    }

    /**
     * Create a column that does not require a property.
     * 
     * @return The HtmlColumn instance.
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public Column createColumn(CellEditor editor) {
        return createColumn(null, editor);
    }
}
