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

import java.util.List;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.GroupedCellEditor;

/**
 * @since 2.3.2
 * @author Jeff Johnston
 */
public class GroupedHtmlView extends HtmlView {
    /**
     * Go through and decorate all the column cell editors.
     */
    @Override
    public Object render() {
        List<Column> columns = getTable().getRow().getColumns();
        for (Column column : columns) {
            CellEditor decoratedCellEditor = column.getCellRenderer().getCellEditor();
            column.getCellRenderer().setCellEditor(new GroupedCellEditor(decoratedCellEditor));
        }

        return super.render();
    }
}