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

import org.jmesa.core.CoreContext;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.renderer.ExportCellRenderer;
import org.jmesa.web.WebContext;

/**
 * <p>
 * A generic export component factory.
 * </p>
 *
 * @since 2.3.4
 * @author Jeff Johnston
 *
 * @deprecated Should build components directly now instead of using factory.
 */
@Deprecated
public class ExportComponentFactory extends AbstractComponentFactory {
    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public ExportComponentFactory(WebContext webContext, CoreContext coreContext) {}

    /**
     * @deprecated Should build components directly now instead of using factory.
     */
    @Deprecated
    public Column createColumn(String property, CellEditor editor) {
        Column column = new Column(property);

        ExportCellRenderer exr = new ExportCellRenderer(column, editor);
        column.setCellRenderer(exr);
        
        return column;
    }
}
