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
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitImpl implements Limit, Serializable {
    private final String id;
    private RowSelect rowSelect;
    private FilterSet filterSet;
    private SortSet sortSet;
    private ExportType exportType;

    /**
     * @param id The code to uniquely identify the table.
     */
    public LimitImpl(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public FilterSet getFilterSet() {
        return filterSet;
    }

    public void setFilterSet(FilterSet filterSet) {
        this.filterSet = filterSet;
    }

    public SortSet getSortSet() {
        return sortSet;
    }

    public void setSortSet(SortSet sortSet) {
        this.sortSet = sortSet;
    }

    public RowSelect getRowSelect() {
        return rowSelect;
    }

    public void setRowSelect(RowSelect rowSelect) {
        this.rowSelect = rowSelect;
    }

    public boolean isExported() {
        return getExportType() != null;
    }

    public ExportType getExportType() {
        return exportType;
    }
    
    public void setExportType(ExportType exportType) {
        this.exportType = exportType;
    }

    public boolean isComplete() {
        if (rowSelect != null) {
            return true;
        }

        return false;
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
