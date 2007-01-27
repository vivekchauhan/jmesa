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

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.RowSelectImpl;
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
import org.jmesaweb.service.PresidentsService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * A complete example in creating a JMesa table using Spring.
 * 
 * @author Jeff Johnston
 */
public class PresidentsController extends AbstractController {
    private static String CSV = "csv";

    private PresidentsService presidentsService;
    private String successView;
    private String id;
    private int maxRows;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        WebContext webContext = new HttpServletRequestWebContext(request);
        Collection items = presidentsService.getPresidents();
        Limit limit = getLimit(items, webContext);
        CoreContext coreContext = getCoreContext(items, limit, webContext);

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
        Table table = tableFactory.createTable("firstName", "lastName", "term", "career");
        View view = new CsvView(table, coreContext);
        ViewExporter exporter = new CsvViewExporter(view, "presidents.txt", response);
        exporter.export();
    }

    public Limit getLimit(Collection items, WebContext webContext) {
        LimitFactory limitFactory = new LimitFactoryImpl(id, webContext);
        Limit limit = limitFactory.createLimit();

        if (limit.isExportable()) {
            RowSelect rowSelect = new RowSelectImpl(1, items.size(), items.size());
            limit.setRowSelect(rowSelect);
        } else {
            RowSelect rowSelect = limitFactory.createRowSelect(maxRows, items.size());
            limit.setRowSelect(rowSelect);
        }

        return limit;
    }

    public CoreContext getCoreContext(Collection items, Limit limit, WebContext webContext) {
        CoreContextFactory factory = new CoreContextFactoryImpl(webContext);
        CoreContext coreContext = factory.createCoreContext(items, limit);
        return coreContext;
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