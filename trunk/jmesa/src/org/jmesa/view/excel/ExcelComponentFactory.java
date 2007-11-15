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
package org.jmesa.view.excel;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractComponentFactory;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.ColumnImpl;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.excel.renderer.ExcelCellRenderer;
import org.jmesa.web.WebContext;

/**
 * @since 2.1
 * @author jeff jie
 */
public class ExcelComponentFactory extends AbstractComponentFactory {
    public ExcelComponentFactory(WebContext webContext, CoreContext coreContext) {
        setWebContext(webContext);
        setCoreContext(coreContext);
    }

    public Column createColumn(String property, CellEditor editor) {
        ColumnImpl column = new ColumnImpl(property);
        column.setWebContext(getWebContext());
        column.setCoreContext(getCoreContext());

        ExcelCellRenderer exr = new ExcelCellRenderer(column, editor);
        exr.setCoreContext(getCoreContext());
        exr.setWebContext(getWebContext());
        column.setCellRenderer(exr);
        return column;
    }
}
