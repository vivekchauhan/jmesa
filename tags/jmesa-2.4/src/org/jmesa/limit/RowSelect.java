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

import java.io.Serializable;

/**
 * <p>
 * Used to figure out the row information so the proper page of information can be retrieved.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface RowSelect extends Serializable {
    /**
     * @return The first row to display.
     */
    public int getRowStart();

    /**
     * @return The last row to display.
     */
    public int getRowEnd();

    /**
     * @return The maximum possible rows that could be displayed on one page.
     */
    public int getMaxRows();

    /**
     * @return The total possible rows, including those that are paginated.
     */
    public int getTotalRows();

    /**
     * @return The current page that is being displayed.
     */
    public int getPage();

    /**
     * @param page The page that should be displayed. Implementations are responsible for
     *            recalculating the row information if a page is set.
     */
    public void setPage(int page);
}
