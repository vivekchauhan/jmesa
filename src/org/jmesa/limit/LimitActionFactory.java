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
package org.jmesa.limit;

/**
 * Figure out how the user interacted with the table.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface LimitActionFactory {

    public String getId();

    /**
     * @return The max rows based on what the user selected. A null returned implies the default
     *         must be used.
     */
    public Integer getMaxRows();

    /**
     * @return The current page based on what the user selected. The default is to return the first
     *         page.
     */
    public int getPage();

    public FilterSet getFilterSet();

    public SortSet getSortSet();

    /**
     * @return The current export type based on what the user selected.
     */
    public ExportType getExportType();
}
