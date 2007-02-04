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
package org.jmesaweb.controller;

import java.util.Collection;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.web.WebContext;
import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.service.PresidentService;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitCoreContext {
    private PresidentService presidentService;
    private String id;
    private int maxRows;

    public CoreContext getCoreContext(WebContext webContext) {
        LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
        Limit limit = limitFactory.createLimit();

        PresidentFilter presidentFilter = getPresidentFilter(limit);
        PresidentSort presidentSort = getPresidentSort(limit);

        int totalRows = presidentService.getPresidentsCountWithFilter(presidentFilter);

        if (limit.isExportable()) {
            RowSelect rowSelect = new RowSelectImpl(1, totalRows, totalRows);
            limit.setRowSelect(rowSelect);
        } else {
            RowSelect rowSelect = limitFactory.createRowSelect(maxRows, totalRows);
            limit.setRowSelect(rowSelect);
        }

        int rowStart = limit.getRowSelect().getRowStart();
        int rowEnd = limit.getRowSelect().getRowEnd();

        Collection items = presidentService.getPresidentsWithFilterAndSort(presidentFilter, presidentSort, rowStart, rowEnd);

        CoreContextFactory factory = new CoreContextFactoryImpl(webContext);
        return factory.createCoreContext(items, limit);
    }

    protected PresidentFilter getPresidentFilter(Limit limit) {
        PresidentFilter presidentFilter = new PresidentFilter();
        FilterSet filterSet = limit.getFilterSet();
        Collection<Filter> filters = filterSet.getFilters();
        for (Filter filter : filters) {
            String property = filter.getProperty();
            String value = filter.getValue();
            presidentFilter.addFilter(property, value);
        }

        return presidentFilter;
    }

    protected PresidentSort getPresidentSort(Limit limit) {
        PresidentSort presidentSort = new PresidentSort();
        SortSet sortSet = limit.getSortSet();
        Collection<Sort> sorts = sortSet.getSorts();
        for (Sort sort : sorts) {
            String property = sort.getProperty();
            String order = sort.getOrder().toParam();
            presidentSort.addSort(property, order);
        }

        return presidentSort;
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }
}
