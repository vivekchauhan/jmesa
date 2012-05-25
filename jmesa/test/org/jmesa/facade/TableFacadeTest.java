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
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.model.TableModel;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.test.SpringParametersAdapter;
import org.jmesa.view.View;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.html.HtmlSnippets;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.SimpleToolbar;
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
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        String html = facade.render();
        assertNotNull(html);
        assertTrue("There is no html rendered", html.length() > 0);
    }

    @Test
    public void getWebContext() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

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
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        try {
            facade.getCoreContext();
            fail("You should have needed the items.");
        } catch (IllegalStateException e) {
        // pass
        }
    }

    @Test
    public void getCoreContext() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        CoreContext coreContext = facade.getCoreContext();
        assertNotNull(coreContext);

        CoreContext coreContextToSet = createCoreContext(facade.getWebContext());
        facade.setCoreContext(coreContextToSet); // The WebContext set should
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);
        
        // now be the one used.
        assertTrue("The core context is not the same.", coreContextToSet == facade.getCoreContext());
    }

    @Test
    public void addFilterMatcher() {
		
        Collection<President> items = PresidentDao.getPresidents();

        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.addFilter("born", "1732/02");

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        row.addColumn(new HtmlColumn("born"));
        table.setRow(row);
        facade.setTable(table);
        
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
		
        Collection<President> items = PresidentDao.getPresidents();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("restore", "true");
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        facade.setStateAttr("restore");
        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertTrue(limit.hasRowSelect());

        // Test the baking in of the State feature.
        TableFacade facadeStateLimit = new TableFacade("pres", request);
        facadeStateLimit.setStateAttr("restore");
        Limit stateLimit = facadeStateLimit.getLimit();
        assertTrue(stateLimit.hasRowSelect());

        LimitFactory limitFactory = new LimitFactory(ID, facade.getWebContext());
        Limit limitToSet = limitFactory.createLimit();
        facade.setLimit(limitToSet); // The Limit set should now be the one used.
        assertTrue("The limit is not the same.", limitToSet == facade.getLimit());
    }

    @Test
    public void getLimitAndExportable() {
		
        Collection<President> items = PresidentDao.getPresidents();

        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(TableModel.CSV);

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertTrue("The limit is not exportable.", limit.hasExport());
        assertTrue("The limit maxRows is not the same as the totalRows.", limit.getRowSelect().getMaxRows() == limit.getRowSelect().getTotalRows());
    }

    @Test
    public void getLimitAndNotExportable() {
		
        Collection<President> items = PresidentDao.getPresidents();

        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertTrue("The limit is exportable.", !limit.hasExport());
        assertTrue("The limit maxRows is the same as the totalRows.", limit.getRowSelect().getMaxRows() != limit.getRowSelect().getTotalRows());
    }

    @Test
    public void getLimitWithState() {
		
        Collection<President> items = PresidentDao.getPresidents();
        MockHttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        facade.setStateAttr("restore");

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertNotNull(request.getSession().getAttribute("pres_LIMIT"));

        TableFacade facadeWithState = TableFacadeFactory.createTableFacade("pres", request);
        
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
        builder.setExportType(TableModel.CSV);

        Collection<President> items = PresidentDao.getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        facade.setTotalRows(items.size());

        Limit limit = facade.getLimit();
        assertNotNull(limit);
        assertNotNull(limit.getRowSelect());
        assertTrue("The limit is not exportable.", limit.hasExport());
        assertTrue("The limit maxRows is not the same as the totalRows.", limit.getRowSelect().getMaxRows() == limit.getRowSelect().getTotalRows());
    }

    @Test
    public void getTableAndNoRowSelect() {
		
        HttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);

        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        try {
            facade.getTable();
            fail("Should be an invalid facade.");
        } catch (IllegalStateException e) {
        // pass
        }
    }

    @Test
    public void getTableAndNotExportable() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);

        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        assertNotNull(table);
    }

    @Test
    public void getTableAndExportable() {
		
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(TableModel.CSV);

        Collection<President> items = PresidentDao.getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);
        
        assertNotNull(facade.getTable());
    }

    @Test
    public void getToolbar() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);

        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        Toolbar toolbar = facade.getToolbar();
        assertNotNull(toolbar);

        SimpleToolbar toolbarToSet = new SimpleToolbar();
        toolbarToSet.setWebContext(facade.getWebContext());
        toolbarToSet.setCoreContext(facade.getCoreContext());
        facade.setToolbar(toolbarToSet); // The toolbar set should now be the
        // one used.
        assertTrue("The toolbar is not the same.", toolbarToSet == facade.getToolbar());
    }

    @Test
    public void getToolbarMaxRowsIncrements() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        facade.setMaxRowsIncrements(15, 25, 50, 75);

        Toolbar toolbar = facade.getToolbar();
        assertNotNull(toolbar);
    }

    @Test
    public void getView() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        View view = facade.getView();
        assertNotNull(view);

        HtmlView viewToSet = new HtmlView();
        viewToSet.setHtmlSnippets(new HtmlSnippets((HtmlTable) facade.getTable(), facade.getToolbar(), facade.getCoreContext()));
        facade.setView(viewToSet); // The view set should now be the one used.
        assertTrue("The view is not the same.", viewToSet == facade.getView());
    }

    @Test
    public void getViewAndExportable() {
		
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(TableModel.CSV);

        Collection<President> items = PresidentDao.getPresidents();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        View view = facade.getView();
        assertNotNull(view);
        assertTrue(view instanceof CsvView);
    }

    @Test
    public void render() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        String html = facade.render();
        assertNotNull(html);
        assertTrue("Did not return any markup.", html.length() > 0);
    }

    @Test
    public void renderWithFactory() {
		
        Collection<President> items = PresidentDao.getPresidents();
        HttpServletRequest request = new MockHttpServletRequest();

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request);
        facade.setItems(items);

        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);

        facade.setTable(table);

        String html = facade.render();
        assertNotNull(html);
        assertTrue("Did not return any markup.", html.length() > 0);
    }

    public void renderAndExportable() {
		
        MockHttpServletRequest request = new MockHttpServletRequest();
        SpringParametersAdapter parameters = new SpringParametersAdapter(request);
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(TableModel.CSV);

        Collection<President> items = PresidentDao.getPresidents();

        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue("The response is not empty.", response.getContentAsByteArray().length == 0);

        TableFacade facade = TableFacadeFactory.createTableFacade("pres", request, response);
        facade.setItems(items);
        
        HtmlTable table = new HtmlTable();
        HtmlRow row = new HtmlRow();
        row.addColumn(new HtmlColumn("name.firstName"));
        row.addColumn(new HtmlColumn("name.lastName"));
        row.addColumn(new HtmlColumn("term"));
        row.addColumn(new HtmlColumn("career"));
        table.setRow(row);
        facade.setTable(table);

        facade.setExportTypes(TableModel.CSV, TableModel.EXCEL);

        String markup = facade.render();
        assertNull(markup);
        assertTrue("There are no contents in the export.", response.getContentAsByteArray().length > 0);
    }

    @Test
    public void getMaxRows() {
		
        MockHttpServletRequest request = new MockHttpServletRequest();
        TableFacade facade = new TableFacade(ID, request);

        int maxRows = facade.getMaxRows();
        
        assertTrue("The maxRows is not set.", maxRows == 15);
    }
}
