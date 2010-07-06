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

import groovy.lang.Closure;
import org.apache.commons.lang.StringUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.renderer.CellRenderer;
import org.jmesa.view.renderer.FilterRenderer;
import org.jmesa.view.renderer.HeaderRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class Column extends AbstractContextSupport {
    private String property;
    private String title;

    private CellRenderer cellRenderer;
    private CellEditor cellEditor;

    private HeaderRenderer headerRenderer;
    private HeaderEditor headerEditor;

    private FilterRenderer filterRenderer;
    private FilterEditor filterEditor;

    private Row row;

    public Column() {}

    public Column(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Column property(String property) {
    	setProperty(property);
    	return this;
    }
    
    public String getTitle() {
        if (StringUtils.isBlank(title)) {
            return ViewUtils.camelCaseToWord(property);
        }

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Column title(String title) {
    	setTitle(title);
    	return this;
    }
    
    public void setTitleKey(String key) {
        if (StringUtils.isNotBlank(key)) {
            this.title = getCoreContext().getMessage(key);
        }
    }

    public Column titleKey(String key) {
    	setTitleKey(key);
    	return this;
    }
    
    public CellRenderer getCellRenderer() {
        return cellRenderer;
    }

    public void setCellRenderer(CellRenderer cellRenderer) {
        this.cellRenderer = cellRenderer;

        //TODO: figure out how to get this removed here
        SupportUtils.setWebContext(cellRenderer, getWebContext());
        SupportUtils.setCoreContext(cellRenderer, getCoreContext());
        SupportUtils.setColumn(cellRenderer, this);
    }

    public Column cellRenderer(CellRenderer cellRenderer) {
    	setCellRenderer(cellRenderer);
    	return this;
    }

    public CellEditor getCellEditor() {
        return cellEditor;
    }

    public void setCellEditor(CellEditor cellEditor) {
        this.cellEditor = cellEditor;

        //TODO: figure out how to get this removed here
        SupportUtils.setWebContext(cellEditor, getWebContext());
        SupportUtils.setCoreContext(cellEditor, getCoreContext());
        SupportUtils.setColumn(cellEditor, this);
    }

    public Column cellEditor(CellEditor editor) {
    	setCellEditor(editor);
    	return this;
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
    public Column setCellEditor(final Closure closure) {
        setCellEditor(new CellEditor() {
            public Object getValue(Object item, String property, int rowcount) {
                return closure.call(new Object[] { item, property, rowcount });
            }
        });
        return this;
    }

    public HeaderRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    public void setHeaderRenderer(HeaderRenderer headerRenderer) {
        this.headerRenderer = headerRenderer;

        //TODO: figure out how to get this removed here
        SupportUtils.setWebContext(headerRenderer, getWebContext());
        SupportUtils.setCoreContext(headerRenderer, getCoreContext());
        SupportUtils.setColumn(headerRenderer, this);
    }

    public Column headerRenderer(HeaderRenderer headerRenderer) {
    	setHeaderRenderer(headerRenderer);
    	return this;
    }

    public HeaderEditor getHeaderEditor() {
        return headerEditor;
    }

    public void setHeaderEditor(HeaderEditor headerEditor) {
        this.headerEditor = headerEditor;

        //TODO: figure out how to get this removed here
        SupportUtils.setWebContext(headerEditor, getWebContext());
        SupportUtils.setCoreContext(headerEditor, getCoreContext());
        SupportUtils.setColumn(headerEditor, this);
    }

    public Column headerEditor(HeaderEditor headerEditor) {
    	setHeaderEditor(headerEditor);
    	return this;
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the headerEditor.
     * </p>
     *
     * @param closure The Groovy closure to use.
     */
    public Column setHeaderEditor(final Closure closure) {
        setHeaderEditor(new HeaderEditor() {
            public Object getValue() {
                return closure.call();
            }
        });
        return this;
    }

    public FilterRenderer getFilterRenderer() {
        return filterRenderer;
    }

    public void setFilterRenderer(FilterRenderer filterRenderer) {
        this.filterRenderer = filterRenderer;

        //TODO: figure out how to get this removed here
        SupportUtils.setWebContext(filterRenderer, getWebContext());
        SupportUtils.setCoreContext(filterRenderer, getCoreContext());
        SupportUtils.setColumn(filterRenderer, this);
    }

    public Column filterRenderer(FilterRenderer filterRenderer) {
    	setFilterRenderer(filterRenderer);
    	return this;
    }

    public FilterEditor getFilterEditor() {
        return filterEditor;
    }

    public void setFilterEditor(FilterEditor filterEditor) {
        this.filterEditor = filterEditor;

        //TODO: figure out how to get this removed here
        SupportUtils.setWebContext(filterEditor, getWebContext());
        SupportUtils.setCoreContext(filterEditor, getCoreContext());
        SupportUtils.setColumn(filterEditor, this);
    }

    public Column filterEditor(FilterEditor filterEditor) {
    	setFilterEditor(filterEditor);
    	return this;
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the filterEditor.
     * </p>
     *
     * @param closure The Groovy closure to use.
     */
    public Column setFilterEditor(final Closure closure) {
        setFilterEditor(new FilterEditor() {
            public Object getValue() {
                return closure.call();
            }
        });
        return this;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Column row(Row row) {
    	setRow(row);
    	return this;
    }
}
