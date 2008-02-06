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

import java.util.List;

import org.jmesa.view.renderer.RowRenderer;
import org.jmesa.worksheet.UniqueProperty;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface Row {
    /**
     * @since 2.3
     */
    public UniqueProperty getUniqueProperty(Object item);

    /**
     * @since 2.3
     */
    public void setUniqueProperty(String uniqueProperty);

    public List<Column> getColumns();

    public Column getColumn(String property);

    public Column getColumn(int index);

    public void addColumn(Column column);

    public RowRenderer getRowRenderer();

    public void setRowRenderer(RowRenderer renderer);
}
