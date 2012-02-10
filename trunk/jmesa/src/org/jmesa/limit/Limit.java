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
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * The name Limit comes from the MySQL limit command, and the the purpose of the Limit interface is
 * to know how to limit the table results. The implemenation of the Limit knows how the user
 * interacted with the table with regards to sorting, filtering, paging, max rows to display, and
 * exporting. With this information you will be able to display the requested page filtered and
 * sorted correctly in the most efficient manner possible.
 * </p>
 *
 * <p>
 * The RowSelect needs to be added to the Limit so that the row information is available.
 * </p>
 *
 * @since 2.0
 * @author Jeff Johnston
 */
public class Limit implements Serializable {
		
    private final String id;
    private RowSelect rowSelect;
    private FilterSet filterSet;
    private SortSet sortSet;
    private ExportType exportType;

    /**
     * @param id The code to uniquely identify the table.
     */
    public Limit(String id) {
		
        this.id = id;
    }

    /**
     * @return The code to uniquely identify the table.
     */
    public String getId() {
		
        return id;
    }

    /**
     * <p>
     * A FilterSet represents the set of Filter objects.
     * </p>
     */
    public FilterSet getFilterSet() {
		
        if (filterSet == null) {
            filterSet = new FilterSet();
        }

        return filterSet;
    }

    public void setFilterSet(FilterSet filterSet) {
		
        this.filterSet = filterSet;
    }

    /**
     * <p>
     * A SortSet represents the set of Sort objects.
     * </p>
     */
    public SortSet getSortSet() {
		
        if (sortSet == null) {
            sortSet = new SortSet();
        }

        return sortSet;
    }

    public void setSortSet(SortSet sortSet) {
		
        this.sortSet = sortSet;
    }

    /**
     * <p>
     * A RowSelect represents the row information.
     * </p>
     */
    public RowSelect getRowSelect() {
		
        if (rowSelect == null) {
            throw new IllegalStateException("The RowSelect object is null. You need to set a valid RowSelect on the Limit.");
        }

        return rowSelect;
    }

    /**
     * <p>
     * The RowSelect needs to be set on the Limit for the Limit to be useful. Of course the
     * RowSelect cannot be created until the total rows is known.
     * </p>
     *
     * <p>
     * The idea is you first create a Limit and use the FilterSet to retrieve the total rows. Once
     * you have the total rows you can create a RowSelect and pass it in here.
     * </p>
     *
     * @param rowSelect The RowSelect to use for this Limit.
     */
    public void setRowSelect(RowSelect rowSelect) {
		
        this.rowSelect = rowSelect;
    }

    /**
     * <p>
     * Check to see if the user is trying to export a table.
     * </p>
     *
     * @return Is true if the user invoked an export.
     */
    public boolean hasExport() {
		
        return getExportType() != null;
    }

    /**
     * <p>
     * The ExportType represents the export that the user invoked.
     * </p>
     */
    public ExportType getExportType() {
		
        return exportType;
    }

    public void setExportType(ExportType exportType) {
		
        this.exportType = exportType;
    }

    public boolean hasRowSelect() {
		
        return rowSelect != null;
    }

    @Override
    public String toString() {
		
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", getId());
        builder.append("export", getExportType());
        builder.append("rowSelect", getRowSelect());
        builder.append("filterSet", getFilterSet());
        builder.append("sortSet", getSortSet());
        return builder.toString();
    }
}
