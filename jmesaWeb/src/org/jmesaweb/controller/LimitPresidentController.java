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

import static org.jmesa.view.TableFacadeImpl.CSV;
import static org.jmesa.view.TableFacadeImpl.EXCEL;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.view.TableFacade;
import org.jmesa.view.TableFacadeImpl;
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
 * A complete example in creating a JMesa table using Spring.
 * 
 * @author Jeff Johnston
 */
public class LimitPresidentController extends AbstractController {
    private PresidentService presidentService;
    private String successView;
    private String id;
    private int maxRows;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);
        
        String content = render(request, response);
        if (content == null) {
            return null; //an export
        } else {
            String ajax = request.getParameter("ajax");
            if (ajax != null && ajax.equals("true")) {
                byte[] contents = content.getBytes();
                response.getOutputStream().write(contents);
                return null;
            } else {
                mv.addObject("presidents", content);
            }
        }

        return mv;
    }
    
    protected String render(HttpServletRequest request, HttpServletResponse response) {
        TableFacade facade = new TableFacadeImpl(id, request, "name.firstName", "name.lastName", "term", "career");
        facade.setExportTypes(response, CSV, EXCEL);

        Limit limit = facade.getLimit();
        
        setLimitVariables(facade, limit);

        Table table = facade.getTable();
        table.setCaption("Presidents");

        Column firstName = table.getRow().getColumn("name.firstName");
        firstName.setTitle("First Name");

        Column lastName = table.getRow().getColumn("name.lastName");
        lastName.setTitle("Last Name");

        if (limit.isExportable()) {
            return null;
        } else {
            HtmlTable htmlTable = (HtmlTable) table;
            htmlTable.getTableRenderer().setWidth("600px");

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
        
        return facade.render();
    }

    protected void setLimitVariables(TableFacade facade, Limit limit) {
        PresidentFilter presidentFilter = getPresidentFilter(limit);
        PresidentSort presidentSort = getPresidentSort(limit);
        int totalRows = presidentService.getPresidentsCountWithFilter(presidentFilter);
        facade.setRowSelect(maxRows, totalRows);
        
        int rowStart = limit.getRowSelect().getRowStart();
        int rowEnd = limit.getRowSelect().getRowEnd();
        Collection<Object> items = presidentService.getPresidentsWithFilterAndSort(presidentFilter, presidentSort, rowStart, rowEnd);
        facade.setItems(items);
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

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }
}