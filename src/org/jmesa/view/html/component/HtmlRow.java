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
package org.jmesa.view.html.component;

import org.jmesa.view.component.Row;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.html.renderer.HtmlRowRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface HtmlRow extends Row {

    public Boolean isFilterable();

    public void setFilterable(Boolean filterable);

    public Boolean isSortable();

    public void setSortable(Boolean sortable);

    public boolean isHighlighter();

    public void setHighlighter(boolean highlighter);

    public RowEvent getOnclick();

    public void setOnclick(RowEvent onclick);

    public RowEvent getOnmouseover();

    public void setOnmouseover(RowEvent onmouseover);

    public RowEvent getOnmouseout();

    public void setOnmouseout(RowEvent onmouseout);

    public HtmlColumn getColumn(String property);

    public HtmlColumn getColumn(int index);

    public HtmlRowRenderer getRowRenderer();
}
