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

import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.ColumnSupport;
import org.jmesa.view.editor.FilterEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractFilterRenderer extends AbstractContextSupport implements FilterRenderer, ColumnSupport {
    private Column column;
    private FilterEditor filterEditor;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public FilterEditor getFilterEditor() {
        return filterEditor;
    }

    public void setFilterEditor(FilterEditor filterEditor) {
        this.filterEditor = filterEditor;
        SupportUtils.setWebContext(filterEditor, getWebContext());
        SupportUtils.setCoreContext(filterEditor, getCoreContext());
        SupportUtils.setColumn(filterEditor, getColumn());
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the filterEditor.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setFilterEditor(final Closure closure) {
        this.filterEditor = new FilterEditor() {
            public Object getValue() {
                return closure.call();
            }
        };
    }
}
