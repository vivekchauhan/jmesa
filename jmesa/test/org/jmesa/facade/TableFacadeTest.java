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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.CoreContext;
import org.jmesa.core.President;
import org.jmesa.core.PresidentDao;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.test.SpringParametersAdapter;
import org.jmesa.view.View;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.excel.ExcelComponentFactory;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.HtmlSnippetsImpl;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.HtmlToolbar;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public class TableFacadeTest extends AbstractTestCase {

    private static final String ID = "pres";

    @Test
    public void createHtmlTableFacade() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        String html = facade.render();
        assertNotNull(html);
        assertTrue("There is no html rendered", html.length() > 0);
    }

    @Test
    public void getWebContext() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        WebContext webContext = facade.getWebContext();
        assertNotNull(webContext);

        WebContext webContextToSet = new HttpServletRequestWebContext(request);
        facade.setWebContext(webContextToSet); // The WebContext set should now
        // be the one used.
        assertTrue("The web context is not the same.", webContextToSet == facade.getWebContext());
    }

    @Test
    public void getCoreContextWithItemsNotSet() {
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        try {
            facade.getCoreContext();
            fail("You should have needed the items.");
        } catch (IllegalStateException e) {
        // pass
        }
    }

    @Test
    public void getCoreContext() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        CoreContext coreContext = facade.getCoreContext();
        assertNotNull(coreContext);

        CoreContext coreContextToSet = createCoreContext(facade.getWebContext());
        facade.setCoreContext(coreContextToSet); // The WebContext set should
        // now be the one used.
        assertTrue("The core context is not the same.", coreContextToSet == facade.getCoreContext());
    }

    @Test
    public void addFilterMatcher() {
        Collection<President> items = new PresidentDao().getPresidents();

        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.addFilter("born", "1732/02");

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career", "born");

        MatcherKey key = new MatcherKey(Date.class);
        DateFilterMatcher matcher = new DateFilterMatcher("yyyy/MM", facade.getWebContext());
        facade.addFilterMatcher(key, matcher);

        assertNotNull(facade.getCoreContext());

        Collection<?> filteredObjects = facade.getCoreContext().getPageItems();
        assertNotNull(filteredObjects);
        assertTrue("There are two many filtered items.", filteredObjects.size() == 1);
    }

    @Test
    public void getLimit() {
        Collection<President> items = new PresidentDao().getPresidents();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("restore", "true");
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        facade.setStateAttr("restore");
        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertTrue(limit.isComplete());

        // Test the baking in of the State feature.
        TableFacade facadeStateLimit = new TableFacadeImpl("pres", request);
        facadeStateLimit.setStateAttr("restore");
        Limit stateLimit = facadeStateLimit.getLimit();
        assertTrue(stateLimit.isComplete());

        LimitFactory limitFactory = new LimitFactoryImpl(ID, facade.getWebContext());
        Limit limitToSet = limitFactory.createLimit();
        facade.setLimit(limitToSet); // The Limit set should now be the one used.
        assertTrue("The limit is not the same.", limitToSet == facade.getLimit());
    }

    @Test
    public void getLimitAndExportable() {
        Collection<President> items = new PresidentDao().getPresidents();

        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.CSV);

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertTrue("The limit is not exportable.", limit.isExported());
        assertTrue("The limit maxRows is not the same as the totalRows.", limit.getRowSelect().getMaxRows() == limit.getRowSelect().getTotalRows());
    }

    @Test
    public void getLimitAndNotExportable() {
        Collection<President> items = new PresidentDao().getPresidents();

        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertTrue("The limit is exportable.", !limit.isExported());
        assertTrue("The limit maxRows is the same as the totalRows.", limit.getRowSelect().getMaxRows() != limit.getRowSelect().getTotalRows());
    }

    @Test
    public void getLimitWithState() {
        Collection<President> items = new PresidentDao().getPresidents();
        MockHttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        facade.setStateAttr("restore");

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertNotNull(request.getSession().getAttribute("pres_LIMIT"));

        TableFacade facadeWithState = TableFacadeFactory.createTableFacade("pres", request);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        
        facadeWithState.setStateAttr("restore");
        request.addParameter("restore", "true");

        limit = facadeWithState.getLimit();
        assertNotNull(limit);
    }

    @Test
    public void setRowSelectAndExportable() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.CSV);

        Collection<President> items = new PresidentDao().getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        facade.setTotalRows(items.size());

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertTrue("The limit is not exportable.", limit.isExported());
        assertTrue("The limit maxRows is not the same as the totalRows.", limit.getRowSelect().getMaxRows() == limit.getRowSelect().getTotalRows());
    }

    @Test
    public void getTableAndNoRowSelect() {
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        try {
            facade.getTable();
            fail("Should be an invalid facade.");
        } catch (IllegalStateException e) {
        // pass
        }
    }

    @Test
    public void getTableAndNotExportable() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        Table table = facade.getTable();
        assertNotNull(table);
    }

    @Test
    public void getTableAndExportable() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.CSV);

        Collection<President> items = new PresidentDao().getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        Table table = facade.getTable();
        assertNotNull(table);
    }

    @Test
    public void getToolbar() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        Toolbar toolbar = facade.getToolbar();
        assertNotNull(toolbar);

        HtmlToolbar toolbarToSet = new HtmlToolbar();
        toolbarToSet.setWebContext(facade.getWebContext());
        toolbarToSet.setCoreContext(facade.getCoreContext());
        facade.setToolbar(toolbarToSet); // The toolbar set should now be the
        // one used.
        assertTrue("The toolbar is not the same.", toolbarToSet == facade.getToolbar());
    }

    @Test
    public void getToolbarMaxRowsIncrements() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        facade.setMaxRowsIncrements(15, 25, 50, 75);

        Toolbar toolbar = facade.getToolbar();
        assertNotNull(toolbar);
    }

    @Test
    public void getView() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        View view = facade.getView();
        assertNotNull(view);

        HtmlView viewToSet = new HtmlView();
        viewToSet.setHtmlSnippets(new HtmlSnippetsImpl((HtmlTable) facade.getTable(), facade.getToolbar(), facade.getCoreContext()));
        facade.setView(viewToSet); // The view set should now be the one used.
        assertTrue("The view is not the same.", viewToSet == facade.getView());
    }

    @Test
    public void getViewAndExportable() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.CSV);

        Collection<President> items = new PresidentDao().getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");
        View view = facade.getView();
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void render() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        String html = facade.render();
        assertNotNull(html);
        assertTrue("Did not return any markup.", html.length() > 0);
    }

    @Test
    public void renderWithFactory() {
        Collection<President> items = new PresidentDao().getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);

        HtmlComponentFactory factory = new HtmlComponentFactory(facade.getWebContext(), facade.getCoreContext());

        HtmlTable table = factory.createTable();
        HtmlRow row = factory.createRow();
        row.addColumn(factory.createColumn("name.firstName"));
        row.addColumn(factory.createColumn("name.lastName"));
        row.addColumn(factory.createColumn("term"));
        row.addColumn(factory.createColumn("career"));
        table.setRow(row);

        facade.setTable(table);

        String html = facade.render();
        assertNotNull(html);
        assertTrue("Did not return any markup.", html.length() > 0);
    }

    @Test
    public void renderExportsWithFactory() {
        Collection<President> items = new PresidentDao().getPresidents();

        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue("The response is not empty.", response.getContentAsByteArray().length == 0);

        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.EXCEL);

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setExportTypes(response, ExportType.EXCEL);

        Limit limit = facade.getLimit();
        assertTrue("The limit is not exportable", limit.isExported());

        if (limit.isExported()) {
            assertTrue("The limit is not an Excel file.", limit.getExportType() == ExportType.EXCEL);

            ExcelComponentFactory factory = new ExcelComponentFactory(facade.getWebContext(), facade.getCoreContext());

            Table table = factory.createTable();
            Row row = factory.createRow();
            row.addColumn(factory.createColumn("name.firstName"));
            row.addColumn(factory.createColumn("name.lastName"));
            row.addColumn(factory.createColumn("term"));
            row.addColumn(factory.createColumn("career"));
            table.setRow(row);

            facade.setTable(table);
            assertNull(facade.render()); // exports do not render anything

            assertTrue("There are no contents in the export.", response.getContentAsByteArray().length > 0);
        }
    }

    @Test
    public void renderAndExportable() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.CSV);

        Collection<President> items = new PresidentDao().getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        facade.setColumnProperties("name.firstName", "name.lastName", "term", "career");

        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue("The response is not empty.", response.getContentAsByteArray().length == 0);
        facade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL);

        String markup = facade.render();
        assertNull(markup);
        assertTrue("There are no contents in the export.", response.getContentAsByteArray().length > 0);
    }

    @Test
    public void getMaxRows() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        TableFacadeImpl facade = new TableFacadeImpl(ID, request);

        int maxRows = facade.getMaxRows();
        
        assertTrue("The maxRows is not set.", maxRows == 15);
    }
}
