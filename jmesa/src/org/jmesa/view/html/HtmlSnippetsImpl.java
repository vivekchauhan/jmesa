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
package org.jmesa.view.html;

import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;

import org.apache.commons.lang.StringEscapeUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.Filter;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.Sort;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Column;
import static org.jmesa.view.html.HtmlConstants.ON_INVOKE_ACTION;
import static org.jmesa.view.html.HtmlConstants.ON_INVOKE_EXPORT_ACTION;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.worksheet.Worksheet;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlSnippetsImpl extends AbstractContextSupport implements HtmlSnippets {

    private HtmlTable table;
    private Toolbar toolbar;

    public HtmlSnippetsImpl(HtmlTable table, Toolbar toolbar, CoreContext coreContext) {
        this.table = table;
        this.toolbar = toolbar;
        setCoreContext(coreContext);
    }

    protected HtmlTable getHtmlTable() {
        return table;
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    public String themeStart() {
        HtmlBuilder html = new HtmlBuilder();
        html.div().styleClass(table.getTheme()).close();
        return html.toString();
    }

    public String themeEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.newline();
        html.divEnd();
        return html.toString();
    }

    public String tableStart() {
        HtmlBuilder html = new HtmlBuilder();
        html.append(table.getTableRenderer().render());
        return html.toString();
    }

    public String tableEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.tableEnd(0);
        return html.toString();
    }

    public String theadStart() {
        HtmlBuilder html = new HtmlBuilder();
        html.thead(1).close();
        return html.toString();
    }

    public String theadEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.theadEnd(1);
        return html.toString();
    }

    public String tbodyStart() {
        HtmlBuilder html = new HtmlBuilder();
        String tbodyClass = getCoreContext().getPreference(HtmlConstants.TBODY_CLASS);
        html.tbody(1).styleClass(tbodyClass).close();
        return html.toString();
    }

    public String tbodyEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.tbodyEnd(1);
        return html.toString();
    }

    @SuppressWarnings("unchecked")
    public String filter() {
        HtmlRow row = table.getRow();
        List columns = row.getColumns();

        if (!ViewUtils.isFilterable(columns)) {
            return "";
        }

        HtmlBuilder html = new HtmlBuilder();
        String filterClass = getCoreContext().getPreference(HtmlConstants.FILTER_CLASS);
        html.tr(1).styleClass(filterClass).close();


        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            HtmlColumn column = (HtmlColumn) iter.next();
            if (column.isFilterable()) {
                html.append(column.getFilterRenderer().render());
            } else {
                html.td(2).close().tdEnd();
            }
        }

        html.trEnd(1);
        return html.toString();
    }

    public String header() {
        HtmlBuilder html = new HtmlBuilder();
        String headerClass = getCoreContext().getPreference(HtmlConstants.HEADER_CLASS);
        html.tr(1).styleClass(headerClass).close();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            HtmlColumn column = (HtmlColumn) iter.next();
            html.append(column.getHeaderRenderer().render());
        }

        html.trEnd(1);
        return html.toString();
    }

    public String footer() {
        return null;
    }

    public String body() {
        HtmlBuilder html = new HtmlBuilder();

        CoreContext coreContext = getCoreContext();

        html.append(worksheetRowsAdded());

        int rowcount = HtmlUtils.startingRowcount(coreContext);

        Collection<?> items = coreContext.getPageItems();
        for (Object item : items) {
            rowcount++;

            HtmlRow row = table.getRow();
            List<Column> columns = row.getColumns();

            html.append(row.getRowRenderer().render(item, rowcount));

            for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
                HtmlColumn column = (HtmlColumn) iter.next();
                html.append(column.getCellRenderer().render(item, rowcount));
            }

            html.trEnd(1);
        }
        return html.toString();
    }

    private String worksheetRowsAdded() {
        HtmlBuilder html = new HtmlBuilder();

        CoreContext coreContext = getCoreContext();
        Worksheet worksheet = coreContext.getWorksheet();
        if (worksheet == null) {
            return "";
        }

        List<WorksheetRow> worksheetRows = worksheet.getRowsByStatus(WorksheetRowStatus.ADD);
        if (worksheetRows.isEmpty()) {
            return "";
        }

        int rowcount = 0;

        for (WorksheetRow worksheetRow : worksheetRows) {
            Object item = worksheetRow.getItem();

            HtmlRow row = table.getRow();
            List<Column> columns = row.getColumns();

            html.append(row.getRowRenderer().render(item, --rowcount));

            for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
                HtmlColumn column = (HtmlColumn) iter.next();
                html.append(column.getCellRenderer().render(item, 0));
            }

            html.trEnd(1);
        }

        html.append(worksheetRowsAddedHeader("", table.getRow().getColumns().size() + 1));

        return html.toString();
    }

    private String worksheetRowsAddedHeader(String title, int colspan) {
        HtmlBuilder html = new HtmlBuilder();

        html.tr(1).styleClass("addRow").close();
        html.td(2).colspan(String.valueOf(colspan)).close().append(title).tdEnd();
        html.trEnd(1);

        return html.toString();
    }

    public String statusBarText() {
        CoreContext coreContext = getCoreContext();
        Limit limit = coreContext.getLimit();
        RowSelect rowSelect = limit.getRowSelect();

        if (rowSelect.getTotalRows() == 0) {
            return coreContext.getMessage(HtmlConstants.STATUSBAR_NO_RESULTS_FOUND);
        }

        Integer total = rowSelect.getTotalRows();
        Integer from = rowSelect.getRowStart() + 1;
        Integer to = rowSelect.getRowEnd();
        Object[] messageArguments = { total, from, to };
        return coreContext.getMessage(HtmlConstants.STATUSBAR_RESULTS_FOUND, messageArguments);
    }

    public String toolbar() {
        HtmlBuilder html = new HtmlBuilder();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        String toolbarClass = getCoreContext().getPreference(HtmlConstants.TOOLBAR_CLASS);
        html.tr(1).styleClass(toolbarClass).close();
        html.td(2).colspan(String.valueOf(columns.size())).close();

        html.append(toolbar.render());

        html.tdEnd();
        html.trEnd(1);

        return html.toString();
    }

    public String statusBar() {
        HtmlBuilder html = new HtmlBuilder();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        html.tbody(1).close();

        String toolbarClass = getCoreContext().getPreference(HtmlConstants.STATUS_BAR_CLASS);
        html.tr(1).styleClass(toolbarClass).close();
        html.td(2).align("left").colspan(String.valueOf(columns.size())).close();

        html.append(statusBarText());

        html.tdEnd();
        html.trEnd(1);

        html.tbodyEnd(1);

        return html.toString();
    }

    /**
     * Create a Limit implementation in JavaScript. Will be invoked when the page is loaded.
     *
     * @return The JavaScript Limit.
     */
    public String initJavascriptLimit() {
        HtmlBuilder html = new HtmlBuilder();

        html.newline();
        html.script().type("text/javascript").close();

        html.newline();

        CoreContext coreContext = getCoreContext();
        Limit limit = coreContext.getLimit();

        boolean useDocumentReady = HtmlUtils.useDocumentReadyToInitJavascriptLimit(coreContext);

        if (useDocumentReady) {
            html.append("$(document).ready(function(){").newline();
        }

        html.tab().append("jQuery.jmesa.addTableFacade('" + limit.getId() + "')").semicolon().newline();

        html.tab().append("jQuery.jmesa.setMaxRowsToLimit('" + limit.getId() + "','" + limit.getRowSelect().getMaxRows() + "')").semicolon().newline();
        html.tab().append("jQuery.jmesa.setTotalRowsToLimit('" + limit.getId() + "','" + limit.getRowSelect().getTotalRows() + "')").semicolon().newline();

        for (Sort sort : limit.getSortSet().getSorts()) {
            html.tab().append(
                    "jQuery.jmesa.addSortToLimit('" + limit.getId() + "','" + sort.getPosition() + "','" + sort.getProperty() + "','" + sort.getOrder().toParam()
                    + "')").semicolon().newline();
        }

        for (Filter filter : limit.getFilterSet().getFilters()) {
            String value = escapeJavaScript(filter.getValue());
            html.tab().append("jQuery.jmesa.addFilterToLimit('" + limit.getId() + "','" + filter.getProperty() + "','" + value + "')").semicolon().newline();
        }

        Worksheet worksheet = coreContext.getWorksheet();
        if (worksheet != null && worksheet.isFiltering()) {
            html.tab().append("jQuery.jmesa.setFilterToWorksheet('" + limit.getId() + "')").semicolon().newline();
        }

        html.tab().append("jQuery.jmesa.setPageToLimit('" + limit.getId() + "','" + limit.getRowSelect().getPage() + "')").semicolon().newline();

        html.tab().append("jQuery.jmesa.setOnInvokeAction('" + limit.getId() + "','" + coreContext.getPreference(ON_INVOKE_ACTION) + "')").semicolon().newline();
        html.tab().append("jQuery.jmesa.setOnInvokeExportAction('" + limit.getId() + "','" + coreContext.getPreference(ON_INVOKE_EXPORT_ACTION) + "')").semicolon().newline();

        // I'm allowing getWebContext() to be null for backwards compatibility
        if (getWebContext() != null) {
            html.tab().append("jQuery.jmesa.setContextPath('" + limit.getId() + "','" + StringEscapeUtils.escapeJavaScript(getWebContext().getContextPath()) + "')").semicolon().newline();
        }

        if (useDocumentReady) {
            html.append("});").newline();
        }

        html.scriptEnd();

        return html.toString();
    }
}
