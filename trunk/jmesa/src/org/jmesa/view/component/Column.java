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
    private HeaderRenderer headerRenderer;
    private FilterRenderer filterRenderer;
    private Row row;

    public Column() {
        // default constructor
    }

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
        SupportUtils.setWebContext(cellRenderer, getWebContext());
        SupportUtils.setCoreContext(cellRenderer, getCoreContext());
        SupportUtils.setColumn(cellRenderer, this);
    }

    public Column cellRenderer(CellRenderer cellRenderer) {
    	setCellRenderer(cellRenderer);
    	return this;
    }
    
    public HeaderRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    public void setHeaderRenderer(HeaderRenderer headerRenderer) {
        this.headerRenderer = headerRenderer;
        SupportUtils.setWebContext(headerRenderer, getWebContext());
        SupportUtils.setCoreContext(headerRenderer, getCoreContext());
        SupportUtils.setColumn(headerRenderer, this);
    }

    public Column headerRenderer(HeaderRenderer headerRenderer) {
    	setHeaderRenderer(headerRenderer);
    	return this;
    }

    public FilterRenderer getFilterRenderer() {
        return filterRenderer;
    }

    public void setFilterRenderer(FilterRenderer filterRenderer) {
        this.filterRenderer = filterRenderer;
        SupportUtils.setWebContext(filterRenderer, getWebContext());
        SupportUtils.setCoreContext(filterRenderer, getCoreContext());
        SupportUtils.setColumn(filterRenderer, this);
    }

    public Column filterRenderer(FilterRenderer filterRenderer) {
    	setFilterRenderer(filterRenderer);
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

    public void setCellEditor(CellEditor editor) {
    	getCellRenderer().setCellEditor(editor);
    }

    public Column cellEditor(CellEditor editor) {
    	setCellEditor(editor);
    	return this;
    }

    public void setHeaderEditor(HeaderEditor headerEditor) {
    	getHeaderRenderer().setHeaderEditor(headerEditor);
    }

    public Column headerEditor(HeaderEditor headerEditor) {
    	setHeaderEditor(headerEditor);
    	return this;
    }
    
    public void setFilterEditor(FilterEditor filterEditor) {
    	getFilterRenderer().setFilterEditor(filterEditor);
    }

    public Column filterEditor(FilterEditor filterEditor) {
    	setFilterEditor(filterEditor);
    	return this;
    }
}
