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

import static org.jmesa.limit.ExportType.CSV;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;

import org.jmesa.limit.ExportType;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.model.PageResults;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * This example sorts and filters the results manually and only returns one page of data. In
 * addition Ajax is used. Hopefully adding Ajax does not complicate the example very much. Really
 * the whole process is the same, except instead of sending the html out on the request we have to
 * send it back out on the response.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class LimitPresidentController extends AbstractController {
    private PresidentService presidentService;
    private String successView;
    private String id; // The unique table id.

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        TableModel tableModel = new TableModel(id, request, response);
        tableModel.setItems(presidentService.getPresidents());
        tableModel.addFilterMatcher(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        tableModel.setStateAttr("restore");
        tableModel.setExportTypes(new ExportType[]{CSV});

        /*
         * We are only returning one page of data. To do this we must first find the total rows. 
         * The total rows can only be figured out after filtering out the data. The sorting does
         * not effect the total row count but is needed to return the correct set of sorted rows.
         */
        tableModel.setItems(new PageResults() {
            public int getTotalRows(Limit limit) {
                PresidentFilter presidentFilter = getPresidentFilter(limit);
                return presidentService.getPresidentsCountWithFilter(presidentFilter);
            }

            public Collection<?> getItems(Limit limit) {
                PresidentFilter presidentFilter = getPresidentFilter(limit);
                PresidentSort presidentSort = getPresidentSort(limit);
                int rowStart = limit.getRowSelect().getRowStart();
                int rowEnd = limit.getRowSelect().getRowEnd();
                return presidentService.getPresidentsWithFilterAndSort(presidentFilter, presidentSort, rowStart, rowEnd);
            }
        });

        if (tableModel.isExporting()) {
            tableModel.setTable(getExportTable());
        } else {
            tableModel.setTable(getHtmlTable());
        }
        
        String view = tableModel.render();
        if (view == null) {
            return null; // an export
        } else {
            // Setting a parameter to signal that this is an Ajax request.
            String ajax = request.getParameter("ajax");
            if (ajax != null && ajax.equals("true")) {
                byte[] contents = view.getBytes();
                response.getOutputStream().write(contents);
                return null;
            } else { // Not using Ajax if invoke the controller for the first time.
                request.setAttribute("presidents", view); // Set the Html in the request for the JSP.
            }
        }

        return mv;
    }

    /**
     * A very custom way to filter the items. The PresidentFilter acts as a command for the
     * Hibernate criteria object. There are probably many ways to do this, but this is the most
     * flexible way I have found. The point is you need to somehow take the Limit information and
     * filter the rows.
     *
     * @param limit The Limit to use.
     */
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

    /**
     * A very custom way to sort the items. The PresidentSort acts as a command for the Hibernate
     * criteria object. There are probably many ways to do this, but this is the most flexible way I
     * have found. The point is you need to somehow take the Limit information and sort the rows.
     *
     * @param limit The Limit to use.
     */
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

    private Table getExportTable() {
        Table table = new Table().caption("Presidents");

        Row row = new Row();
        table.setRow(row);

        Column firstName = new Column("name.firstName").title("First Name");
        row.addColumn(firstName);

        Column lastName = new Column("name.lastName").title("Last Name");
        row.addColumn(lastName);

        Column career = new Column("career").filterEditor(new DroplistFilterEditor());
        row.addColumn(career);

        Column born = new Column("born").cellEditor(new DateCellEditor("MM/yyyy"));
        row.addColumn(born);

        return table;
    }

    private Table getHtmlTable() {
        HtmlTable htmlTable = new HtmlTable().caption("Presidents").width("600px");

        HtmlRow htmlRow = new HtmlRow();
        htmlTable.setRow(htmlRow);

        HtmlColumn firstName = new HtmlColumn("name.firstName").title("First Name");
        firstName.setCellEditor(new CellEditor() {
            public Object getValue(Object item, String property, int rowcount) {
                Object value = new HtmlCellEditor().getValue(item, property, rowcount);
                HtmlBuilder html = new HtmlBuilder();
                html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
                html.append(value);
                html.aEnd();
                return html.toString();
            }
        });
        htmlRow.addColumn(firstName);

        HtmlColumn lastName = new HtmlColumn("name.lastName").title("Last Name");
        htmlRow.addColumn(lastName);

        HtmlColumn career = new HtmlColumn("career").filterEditor(new DroplistFilterEditor());
        htmlRow.addColumn(career);

        HtmlColumn born = new HtmlColumn("born").cellEditor(new DateCellEditor("MM/yyyy"));
        htmlRow.addColumn(born);

        return htmlTable;
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setId(String id) {
        this.id = id;
    }
}