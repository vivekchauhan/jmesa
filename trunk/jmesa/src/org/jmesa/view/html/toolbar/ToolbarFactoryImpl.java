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
package org.jmesa.view.html.toolbar;

import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Row;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ToolbarFactoryImpl implements ToolbarFactory {
    private HtmlTable table;
    private int[] maxRowsIncrements;
    private String[] exportTypes;
    private WebContext webContext;
    private CoreContext coreContext;

    public ToolbarFactoryImpl(HtmlTable table, WebContext webContext, CoreContext coreContext, String... exportTypes) {
        this.table = table;
        this.webContext = webContext;
        this.coreContext = coreContext;
        this.exportTypes = exportTypes;
    }

    public ToolbarFactoryImpl(HtmlTable table, int[] maxRowsIncrements, WebContext webContext, CoreContext coreContext, String... exportTypes) {
        this.table = table;
        this.maxRowsIncrements = maxRowsIncrements;
        this.webContext = webContext;
        this.coreContext = coreContext;
        this.exportTypes = exportTypes;
    }

    public Toolbar createToolbar() {
        Toolbar toolbar = new ToolbarImpl(webContext, coreContext);
        toolbar.addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);

        MaxRowsItem maxRowsItem = (MaxRowsItem) toolbar.addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);
        if (maxRowsIncrements != null) {
            maxRowsItem.setIncrements(maxRowsIncrements);
        }

        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        toolbar.addExportToolbarItems(exportTypes);
        toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);

        Row row = table.getRow();
        List columns = row.getColumns();
        if (ViewUtils.isFilterable(columns)) {
            toolbar.addToolbarItem(ToolbarItemType.FILTER_ITEM);
            toolbar.addToolbarItem(ToolbarItemType.CLEAR_ITEM);
        }

        return toolbar;
    }
}
