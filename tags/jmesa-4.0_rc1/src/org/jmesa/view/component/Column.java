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

import org.apache.commons.lang.StringUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.renderer.BasicCellRenderer;
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
    private String titleKey;

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
		
        if (titleKey != null) {
            return getCoreContext().getMessage(titleKey);
        }

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
    
    public void setTitleKey(String titleKey) {
		
        this.titleKey = titleKey;
    }

    public Column titleKey(String titleKey) {
		
    	setTitleKey(titleKey);
    	return this;
    }
    
    public CellRenderer getCellRenderer() {
		
        if (cellRenderer == null) {
            this.cellRenderer = new BasicCellRenderer(this);
        }
        return cellRenderer;
    }

    public void setCellRenderer(CellRenderer cellRenderer) {
		
        this.cellRenderer = cellRenderer;
        this.cellRenderer.setColumn(this);
    }

    public Column cellRenderer(CellRenderer cellRenderer) {
		
    	setCellRenderer(cellRenderer);
    	return this;
    }

    public CellEditor getCellEditor() {
		
        if (cellEditor == null) {
            this.cellEditor = new BasicCellEditor();
        }
        return cellEditor;
    }

    public void setCellEditor(CellEditor cellEditor) {
		
        this.cellEditor = cellEditor;

        /*
         * This is useful for editors that are decorated at
         * runtime. Most of the support classes are handled
         * in the TableFacadeUtils.init() method though.
         */
        SupportUtils.setWebContext(cellEditor, getWebContext());
        SupportUtils.setCoreContext(cellEditor, getCoreContext());
        SupportUtils.setColumn(cellEditor, this);
    }

    public Column cellEditor(CellEditor editor) {
		
    	setCellEditor(editor);
    	return this;
    }

    public HeaderRenderer getHeaderRenderer() {
		
        return headerRenderer;
    }

    public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		
        this.headerRenderer = headerRenderer;
        this.headerRenderer.setColumn(this);
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
    }

    public Column headerEditor(HeaderEditor headerEditor) {
		
    	setHeaderEditor(headerEditor);
    	return this;
    }

    public FilterRenderer getFilterRenderer() {
		
        return filterRenderer;
    }

    public void setFilterRenderer(FilterRenderer filterRenderer) {
		
        this.filterRenderer = filterRenderer;
        this.filterRenderer.setColumn(this);
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
    }

    public Column filterEditor(FilterEditor filterEditor) {
		
    	setFilterEditor(filterEditor);
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
