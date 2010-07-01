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

import org.jmesa.view.renderer.CellRenderer;
import org.jmesa.view.renderer.FilterRenderer;
import org.jmesa.view.renderer.HeaderRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface Column {
    public String getProperty();

    public void setProperty(String property);

    public String getTitle();

    public void setTitle(String title);

    public void setTitleKey(String key);

    public CellRenderer getCellRenderer();

    public void setCellRenderer(CellRenderer cellRenderer);

    public HeaderRenderer getHeaderRenderer();

    public void setHeaderRenderer(HeaderRenderer headerRenderer);

    public FilterRenderer getFilterRenderer();

    public void setFilterRenderer(FilterRenderer filterRenderer);

    public Row getRow();

    public void setRow(Row row);
}
