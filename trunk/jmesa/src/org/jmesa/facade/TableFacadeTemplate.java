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
package org.jmesa.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.state.State;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.HtmlTableBuilder;
import org.jmesa.view.html.toolbar.Toolbar;

/**
 * The template is useful to abstract out the interaction with the TableFacade. So
 * instead of having to get the order of interaction with the TableFacade right you
 * just override the method that represents what you want to customize and
 *
 * @deprecated Use the new TableModel for building tables.
 */
@Deprecated
public abstract class TableFacadeTemplate {

    final TableFacade tableFacade;

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public TableFacadeTemplate(TableFacade tableFacade) {
        this.tableFacade = tableFacade;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    public String render() {
        setup();

        return tableFacade.render();
    }

    void setup() {
        Preferences preferences = getPreferences();
        if (preferences != null) {
            tableFacade.setPreferences(preferences);
        }

        Messages messages = getMessages();
        if (messages != null) {
            tableFacade.setMessages(messages);
        }

        ExportType[] exportTypes = getExportTypes();
        if (exportTypes != null) {
            tableFacade.setExportTypes(exportTypes);
        }

        String stateAttr = getStateAttr();
        if (stateAttr != null) {
            tableFacade.setStateAttr(stateAttr);
        }

        State state = getState();
        if (state != null) {
            tableFacade.setState(state);
        }

        Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
        addFilterMatchers(filterMatcherMap);
        if (!filterMatcherMap.isEmpty()) {
            for (Entry<MatcherKey, FilterMatcher> entry : filterMatcherMap.entrySet()) {
                tableFacade.addFilterMatcher(entry.getKey(), entry.getValue());
            }
        }

        ColumnSort columnSort = createColumnSort();
        if (columnSort != null) {
            tableFacade.setColumnSort(columnSort);
        }

        RowFilter rowFilter = createRowFilter();
        if (rowFilter != null) {
            tableFacade.setRowFilter(rowFilter);
        }

        int maxRows = getMaxRows();
        if (maxRows != -1) {
            tableFacade.setMaxRows(maxRows);
        }

        int[] maxRowsIncrements = getMaxRowsIncrements();
        if (maxRowsIncrements != null) {
            tableFacade.setMaxRowsIncrements(maxRowsIncrements);
        }

        Limit limit = tableFacade.getLimit();
        int totalRows = getTotalRows(limit);
        if (totalRows != -1) {
            if (limit.isComplete()) {
                int p = limit.getRowSelect().getPage();
                int mr = limit.getRowSelect().getMaxRows();
                limit.setRowSelect(new RowSelect(p, mr, totalRows));
            } else {
                tableFacade.setTotalRows(totalRows);
            }
            Collection<?> items = getItems(limit);
            tableFacade.setItems(items);
        } else {
            Collection<?> items = getItems();
            tableFacade.setItems(items);
            if (limit.isComplete()) {
                int p = limit.getRowSelect().getPage();
                int mr = limit.getRowSelect().getMaxRows();
                limit.setRowSelect(new RowSelect(p, mr, items.size()));
            } else {
                tableFacade.setTotalRows(items.size());
            }
        }

        String[] columnProperties = getColumnProperties();
        if (columnProperties != null) {
            tableFacade.setColumnProperties(columnProperties);
            modifyTable(tableFacade.getTable());
        } else {
            Table table = createTable();
            if (table == null) {
                table = createTable(new HtmlTableBuilder(tableFacade));
            }
            if (table != null) {
                tableFacade.setTable(table);
            }
        }

        Toolbar toolbar = createToolbar();
        if (toolbar != null) {
            tableFacade.setToolbar(toolbar);
        }

        View view = createView();
        if (view != null) {
            tableFacade.setView(view);
        }
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected boolean isExporting() {
        return tableFacade.getLimit().isExported();
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Preferences getPreferences() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Messages getMessages() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected ExportType[] getExportTypes() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected String getStateAttr() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected State getState() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected void addFilterMatchers(Map<MatcherKey, FilterMatcher> filterMatchers) {
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected ColumnSort createColumnSort() {
        return null;
    }
    
    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected RowFilter createRowFilter() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected int getTotalRows(Limit limit) {
        return -1;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected int getMaxRows() {
        return -1;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected int[] getMaxRowsIncrements() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected String[] getColumnProperties() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Table createTable(HtmlTableBuilder builder) {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Table createTable() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected void modifyTable(Table table) {
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Toolbar createToolbar() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected View createView() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Collection<?> getItems() {
        return null;
    }

    /**
     * @deprecated Use the new TableModel for building tables.
     */
    @Deprecated
    protected Collection<?> getItems(Limit limit) {
        return null;
    }
}
