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

import org.apache.commons.lang.StringUtils;
import org.jmesa.limit.state.SessionState;
import org.jmesa.limit.state.State;
import org.jmesa.util.SupportUtils;
import org.jmesa.web.WebContext;

/**
 * <p>
 * Uses the default implementation of the Limit and RowSelect to construct a Limit and a RowSelect
 * implementation object.
 * </p>
 * 
 * <p>
 * An example to create a Limit using the LimitFactory is as follows:
 * </p>
 * 
 * <p>
 * First you need to pass in the table id and the WebContext. The table id is the unique identifier
 * for the current table being built. The WebContext is an adapter for the servlet request.
 * </p>
 * 
 * <pre>
 * String id = &quot;pres&quot;;
 * WebContext webContext = new HttpServletRequestWebContext(request);
 * 
 * LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
 * Limit limit = limitFactory.createLimit();
 * </pre>
 * 
 * <p>
 * Once you have the Limit you still need to create and set a RowSelect object on the Limit. The
 * RowSelect needs to be added to the Limit so that the row information is available. The catch is
 * the RowSelect cannot be created until the total rows is known. If you are not manually filtering
 * and sorting the table then it is easy because the totalRows is just the size of your items
 * (Collection of Beans or Collection of Maps). However, if you are manually filtering and sorting
 * the table to bring back one page of data the you first need to use the FilterSet on the Limit to
 * figure out the total rows.
 * </p>
 * 
 * <p>
 * Either way, once you have the total rows you can now create a RowSelect with the factory. Lastly,
 * set the RowSelect on the Limit and your done!
 * </p>
 * 
 * <pre>
 * RowSelect rowSelect = limitFactory.createRowSelect(maxRows, totalRows);
 * limit.setRowSelect(rowSelect);
 * </pre>
 * 
 * <p>
 * Note: if you are not manually filtering and sorting there is a convenience method to create the
 * Limit and RowSelect at once.
 * </p>
 * 
 * <pre>
 * LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
 * Limit limit = limitFactory.createLimitAndRowSelect(maxRows, totalRows);
 * </pre>
 * 
 * <p>
 * Also, if you are manually filtering and sorting and are doing an export then you should create
 * the RowSelect object yourself and set it on the Limit.
 * </p>
 * 
 * <pre>
 * RowSelect rowSelect = new RowSelectImpl(1, totalRows, totalRows);
 * limit.setRowSelect(rowSelect);
 * </pre>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitFactoryImpl implements LimitFactory {
    private final LimitActionFactory limitActionFactory;
    private final String id;
    private final WebContext webContext;
    private State state;

    /**
     * @param id The unique identifier for the current table being built.
     * @param webContext The adapter for the servlet request.
     */
    public LimitFactoryImpl(String id, WebContext webContext) {
        this.id = id;
        this.webContext = webContext;
        this.limitActionFactory = new LimitActionFactoryImpl(id, webContext.getParameterMap());
    }

    public void setState(State state) {
        this.state = state;
    }

    @Deprecated
    public void setStateAttr(String stateAttr) {
        if (StringUtils.isNotEmpty(stateAttr)) {
            this.state = new SessionState(id, stateAttr);
            SupportUtils.setWebContext(state, webContext);
        }
    }

    public Limit createLimit() {
        Limit limit = getStateLimit();

        if (limit != null) {
            return limit;
        }

        limit = new LimitImpl(limitActionFactory.getId());

        FilterSet filterSet = limitActionFactory.getFilterSet();
        limit.setFilterSet(filterSet);

        SortSet sortSet = limitActionFactory.getSortSet();
        limit.setSortSet(sortSet);

        ExportType exportType = limitActionFactory.getExportType();
        limit.setExportType(exportType);
        
        if (state != null && !limit.isExported()) {
            state.persistLimit(limit);
        }

        return limit;
    }

    public RowSelect createRowSelect(int maxRows, int totalRows) {
        int page = limitActionFactory.getPage();

        maxRows = getMaxRows(maxRows);

        return new RowSelectImpl(page, maxRows, totalRows);
    }

    public RowSelect createRowSelect(int maxRows, int totalRows, Limit limit) {
        RowSelect rowSelect = createRowSelect(maxRows, totalRows);
        limit.setRowSelect(rowSelect);
        return rowSelect;
    }

    public Limit createLimitAndRowSelect(int maxRows, int totalRows) {
        Limit limit = createLimit();

        if (limit.isComplete()) {
            return limit;
        }

        if (limit.isExported()) {
            RowSelect rowSelect = new RowSelectImpl(1, totalRows, totalRows);
            limit.setRowSelect(rowSelect);
        } else {
            RowSelect rowSelect = createRowSelect(maxRows, totalRows);
            limit.setRowSelect(rowSelect);
        }

        return limit;
    }

    private int getMaxRows(int maxRows) {
        Integer currentMaxRows = limitActionFactory.getMaxRows();
        if (currentMaxRows == null) {
            return maxRows;
        }

        return currentMaxRows;
    }

    private Limit getStateLimit() {
        if (state != null) {
            Limit l = state.retrieveLimit();
            if (l != null) {
                return l;
            }
        }

        return null;
    }
}
