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
package org.jmesa.view.renderer;

import groovy.lang.Closure;

import org.jmesa.view.ContextSupport;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractCellRenderer extends ContextSupport implements CellRenderer {
    private Column column;
    private CellEditor editor;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public CellEditor getCellEditor() {
        return editor;
    }

    public void setCellEditor(CellEditor editor) {
        this.editor = editor;
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the CellEditor.
     * </p>
     * 
     * <p>
     * And example is as follows:
     * </p>
     * 
     * <pre>
     * firstName.cellRenderer.setCellEditor({item, property, rowcount -&gt; 
     *      def value = new BasicCellEditor().getValue(item, property, rowcount);
     *      return &quot;&quot;&quot;
     *              &lt;a href=&quot;http://www.whitehouse.gov/history/presidents/&quot;&gt;
     *                 $value
     *              &lt;/a&gt;
     *             &quot;&quot;&quot;});
     * </pre>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setCellEditor(final Closure closure) {
        this.editor = new CellEditor() {
            public Object getValue(Object item, String property, int rowcount) {
                return closure.call(new Object[] { item, property, rowcount });
            }
        };
    }
}
