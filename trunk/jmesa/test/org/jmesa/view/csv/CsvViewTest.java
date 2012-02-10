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
package org.jmesa.view.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.ExportType;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.test.Parameters;
import org.jmesa.test.ParametersAdapter;
import org.jmesa.test.ParametersBuilder;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvViewTest extends AbstractTestCase {
    @Test
    public void render() {
        WebContext webContext = createWebContext();
        webContext.setParameterMap(getParameters());
        webContext.setLocale(Locale.US);

        CoreContext coreContext = createCoreContext(webContext);

        assertTrue(coreContext.getLimit().hasExport());
        assertTrue(coreContext.getLimit().getExportType() == ExportType.CSV);

        // create the table
        Table table = new Table();

        // create the row
        Row row = new Row();
        table.setRow(row);

        // create some reusable objects

        // create the columns
        Column firstNameColumn = new Column("name.firstName");
        row.addColumn(firstNameColumn);

        Column lastNameColumn = new Column("name.lastName");
        row.addColumn(lastNameColumn);

        Column termColumn = new Column("term");
        row.addColumn(termColumn);

        Column careerColumn = new Column("career");
        row.addColumn(careerColumn);

        // create the view
        CsvView view = new CsvView();
        view.setCoreContext(coreContext);
        view.setTable(table);

        Object csv = view.render();

        assertNotNull(csv);
    }

    private Map<?, ?> getParameters() {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ParametersAdapter parametersAdapter = new ParametersAdapter(results);
        createBuilder(parametersAdapter);
        return results;
    }

    private void createBuilder(Parameters parameters) {
        ParametersBuilder builder = new ParametersBuilder(ID, parameters);
        builder.setExportType(ExportType.CSV);
    }
}
