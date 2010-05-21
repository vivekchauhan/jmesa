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
import static org.jmesa.limit.ExportType.EXCEL;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.facade.TableFacadeTemplate;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlTable;
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

        TableFacade tableFacade = new TableFacadeImpl(id, request);
        TableFacadeTemplate template = new LimitPresidentTemplate(tableFacade, response);
        String view = template.render();
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
     * <p>
     * We are manually filtering and sorting the rows here. In addition we are only returning one
     * page of data. To do this we must use the Limit to tell us where the rows start and end.
     * However, to do that we must set the RowSelect object using the maxRows and totalRows to
     * create a valid Limit object.
     * </p>
     *
     * <p>
     * The idea is to first find the total rows. The total rows can only be figured out after
     * filtering out the data. The sorting does not effect the total row count but is needed to
     * return the correct set of sorted rows.
     * </p>
     *
     * @param tableFacade The TableFacade to use.
     */
    private class LimitPresidentTemplate extends TableFacadeTemplate {

        public LimitPresidentTemplate(TableFacade tableFacade, HttpServletResponse response) {
            super(tableFacade, response);
        }
        
        /**
         * The array of available exports.
         */
        @Override
        protected ExportType[] getExportTypes() {
            return new ExportType[]{CSV, EXCEL};
        }

        /**
         * Add a custom filter matcher to be the same pattern as the cell editor used.
         */
        @Override
        protected void addFilterMatchers(Map<MatcherKey, FilterMatcher> filterMatchers) {
            filterMatchers.put(new MatcherKey(Date.class, "born"), new DateFilterMatcher("MM/yyyy"));
        }

        /**
         * Make it so that the table state is saved.
         */
        @Override
        protected String getStateAttr() {
            return "restore";
        }

        @Override
        protected String[] getColumnProperties() {
            return new String[]{"name.firstName", "name.lastName", "term", "career"};
        }

        @Override
        protected void modifyTable(Table table) {
            table.setCaption("Presidents");

            Column firstName = table.getRow().getColumn("name.firstName");
            firstName.setTitle("First Name");

            Column lastName = table.getRow().getColumn("name.lastName");
            lastName.setTitle("Last Name");

            if (!isExporting()) {
                HtmlTable htmlTable = (HtmlTable) table;
                htmlTable.getTableRenderer().setWidth("600px");

                // Using an anonymous class to implement a custom editor.
                firstName.getCellRenderer().setCellEditor(new CellEditor() {
                    public Object getValue(Object item, String property, int rowcount) {
                        Object value = new BasicCellEditor().getValue(item, property, rowcount);
                        HtmlBuilder html = new HtmlBuilder();
                        html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
                        html.append(value);
                        html.aEnd();
                        return html.toString();
                    }
                });
            }
        }

        /**
         * Very important to set the totalRow
         * before trying to get the
         * row start and row end variables.
         */
        @Override
        protected int getTotalRows(Limit limit) {
            PresidentFilter presidentFilter = getPresidentFilter(limit);
            return presidentService.getPresidentsCountWithFilter(presidentFilter);
        }

        @Override
        protected Collection<?> getItems(Limit limit) {
            PresidentFilter presidentFilter = getPresidentFilter(limit);
            PresidentSort presidentSort = getPresidentSort(limit);
            int rowStart = limit.getRowSelect().getRowStart();
            int rowEnd = limit.getRowSelect().getRowEnd();
            return presidentService.getPresidentsWithFilterAndSort(presidentFilter, presidentSort, rowStart, rowEnd);
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