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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class RowSelectImpl implements RowSelect {
    private int page;
    private int maxRows;
    private int rowEnd;
    private int rowStart;
    private int totalRows;

    public RowSelectImpl(int page, int maxRows, int totalRows) {
        this.maxRows = maxRows;
        this.totalRows = totalRows;
        init(page);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        init(page);
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public int getTotalRows() {
        return totalRows;
    }

    private void init(int page) {
        page = getValidPage(page, maxRows, totalRows);

        int rowStart = (page - 1) * maxRows;

        int rowEnd = rowStart + maxRows;

        if (rowEnd > totalRows) {
            rowEnd = totalRows;
        }

        this.page = page;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    /**
     * The page returned that is not greater than the pages that can display.
     */
    private int getValidPage(int page, int maxRows, int totalRows) {
        if (!isValidPage(page, maxRows, totalRows)) {
            return getValidPage(--page, maxRows, totalRows);
        }

        return page;
    }

    /**
     * Testing that the page returned is not greater than the pages that are able to be displayed.
     * The problem arises if using the state feature and rows are deleted.
     */
    private boolean isValidPage(int page, int maxRows, int totalRows) {
        if (page == 1) {
            return true;
        }

        int rowStart = (page - 1) * maxRows;
        int rowEnd = rowStart + maxRows;
        if (rowEnd > totalRows) {
            rowEnd = totalRows;
        }
        return rowEnd > rowStart;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("page", page);
        builder.append("maxRows", maxRows);
        builder.append("rowEnd", rowEnd);
        builder.append("rowStart", rowStart);
        builder.append("totalRows", totalRows);
        return builder.toString();
    }
}
