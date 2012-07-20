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

import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.renderer.TableRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class Table extends AbstractContextSupport {
		
    private Row row;
    private String caption;
    private String captionKey;
    private TableRenderer tableRenderer;

    public String getCaption() {
		
        if (captionKey != null) {
            return getCoreContext().getMessage(captionKey);
        }

        return caption;
    }

    public void setCaption(String caption) {
		
        this.caption = caption;
    }

    public Table caption(String caption) {
		
    	setCaption(caption);
    	return this;
    }

    public void setCaptionKey(String captionKey) {
		
        this.captionKey = captionKey;
    }

    public Table captionKey(String captionKey) {
		
    	setCaptionKey(captionKey);
    	return this;
    }

    public Row getRow() {
		
        return row;
    }

    public void setRow(Row row) {
		
        this.row = row;
    }

    public Table row(Row row) {
		
    	setRow(row);
    	return this;
    }

    public TableRenderer getTableRenderer() {
		
        return tableRenderer;
    }

    public void setTableRenderer(TableRenderer tableRenderer) {
		
        this.tableRenderer = tableRenderer;
        this.tableRenderer.setTable(this);
    }

    public Table tableRenderer(TableRenderer tableRenderer) {
		
    	setTableRenderer(tableRenderer);
    	return this;
    }
}
