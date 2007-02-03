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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.jmesa.view.TableFactory;
import org.jmesa.view.View;
import org.jmesa.view.ViewExporter;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvTableFactory;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.csv.CsvViewExporter;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlTableFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactory;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesaweb.dao.PresidentFilter;
import org.jmesaweb.dao.PresidentSort;
import org.jmesaweb.service.PresidentsService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * A complete example in creating a JMesa table using Spring.
 * 
 * @author Jeff Johnston
 */
public class LimitPresidentsController extends AbstractController {
    private static Log logger = LogFactory.getLog(LimitPresidentsController.class);

    private static String CSV = "csv";

    private PresidentsService presidentsService;
    private String successView;
    private String id;
    private int maxRows;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        WebContext webContext = new HttpServletRequestWebContext(request);

        LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
        Limit limit = limitFactory.createLimit();

        PresidentFilter presidentFilter = getPresidentFilter(limit);
        PresidentSort presidentSort = getPresidentSort(limit);

        int totalRows = presidentsService.getPresidentsCountWithFilter(presidentFilter);

        if (limit.isExportable()) {
            RowSelect rowSelect = new RowSelectImpl(1, totalRows, totalRows);
            limit.setRowSelect(rowSelect);
        } else {
            RowSelect rowSelect = limitFactory.createRowSelect(maxRows, totalRows);
            limit.setRowSelect(rowSelect);
        }

        int rowStart = limit.getRowSelect().getRowStart();
        int rowEnd = limit.getRowSelect().getRowEnd();
        Collection items = presidentsService.getPresidentsWithFilterAndSort(presidentFilter, presidentSort, rowStart, rowEnd);

        CoreContextFactory factory = new CoreContextFactoryImpl(webContext);
        CoreContext coreContext = factory.createCoreContext(items, limit);

        if (limit.isExportable()) {
            String type = limit.getExport().getType();
            if (type.equals(CSV)) {
                csvTable(webContext, coreContext, response);
                return null;
            }
        }

        Object presidents = htmlTable(webContext, coreContext);
        mv.addObject("presidents", presidents);

        return mv;
    }

    public Object htmlTable(WebContext webContext, CoreContext coreContext) {
        HtmlTableFactory tableFactory = new HtmlTableFactory(webContext, coreContext);

        HtmlTable table = tableFactory.createTable("firstName", "lastName", "term", "career");
        table.setCaption("Presidents");
        table.getTableRenderer().setWidth("600px;");

        CellEditor editor = new PresidentsLinkEditor(new BasicCellEditor());
        table.getRow().getColumn("firstName").getCellRenderer().setCellEditor(editor);

        ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, "csv");
        Toolbar toolbar = toolbarFactory.createToolbar();
        View view = new HtmlView(table, toolbar, coreContext);

        return view.render();
    }

    public void csvTable(WebContext webContext, CoreContext coreContext, HttpServletResponse response)
            throws Exception {
        TableFactory tableFactory = new CsvTableFactory(webContext, coreContext);
        Table table = tableFactory.createTable("firstName", "lastName", "nickName", "term", "born", "died", "education", "career", "politicalParty");
        View view = new CsvView(table, coreContext);
        ViewExporter exporter = new CsvViewExporter(view, "presidents.txt", response);
        exporter.export();
    }

    /**
     * Create a link for the first name column. Using the decorator pattern so
     * that can wrap any kind of editor with a link.
     */
    private static class PresidentsLinkEditor implements CellEditor {
        CellEditor cellEditor;

        public PresidentsLinkEditor(CellEditor cellEditor) {
            this.cellEditor = cellEditor;
        }

        public Object getValue(Object item, String property, int rowcount) {
            Object value = cellEditor.getValue(item, property, rowcount);
            HtmlBuilder html = new HtmlBuilder();
            html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
            html.append(value);
            html.aEnd();
            return html.toString();
        }
    }

    private PresidentFilter getPresidentFilter(Limit limit) {
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

    private PresidentSort getPresidentSort(Limit limit) {
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

    public void setPresidentsService(PresidentsService presidentsService) {
        this.presidentsService = presidentsService;
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