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
@Deprecated
public class ToolbarFactoryImpl implements ToolbarFactory {
    private HtmlTable table;
    private int[] maxRowsIncrements;
    private String[] exportTypes;
    private WebContext webContext;
    private CoreContext coreContext;
    private boolean enableSeparators = true;

    @Deprecated
    public ToolbarFactoryImpl(HtmlTable table, WebContext webContext, CoreContext coreContext, String... exportTypes) {
        this.table = table;
        this.webContext = webContext;
        this.coreContext = coreContext;
        this.exportTypes = exportTypes;
    }

    @Deprecated
    public ToolbarFactoryImpl(HtmlTable table, int[] maxRowsIncrements, WebContext webContext, CoreContext coreContext, String... exportTypes) {
        this.table = table;
        this.maxRowsIncrements = maxRowsIncrements;
        this.webContext = webContext;
        this.coreContext = coreContext;
        this.exportTypes = exportTypes;
    }
    
    @Deprecated
    public void enableSeparators(boolean isEnabled) {
        this.enableSeparators = isEnabled;
    }

    @Deprecated
    public Toolbar createToolbar() {
        DefaultToolbar toolbar = new DefaultToolbar();
        toolbar.setWebContext(webContext);
        toolbar.setCoreContext(coreContext);

        toolbar.addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);
        toolbar.addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);
        
        if (enableSeparators) {
            toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        }

        MaxRowsItem maxRowsItem = (MaxRowsItem) toolbar.addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);
        if (maxRowsIncrements != null) {
            maxRowsItem.setIncrements(maxRowsIncrements);
        }
        
        boolean exportable = ViewUtils.isExportable(exportTypes);

        if (exportable && enableSeparators) {
            toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        }
        
        if (exportable) {
            toolbar.addExportToolbarItems(exportTypes);
        }
        
        Row row = table.getRow();
        List columns = row.getColumns();
        
        boolean filterable = ViewUtils.isFilterable(columns);

        if (filterable && enableSeparators) {
            toolbar.addToolbarItem(ToolbarItemType.SEPARATOR);
        }

        if (filterable) {
            toolbar.addToolbarItem(ToolbarItemType.FILTER_ITEM);
            toolbar.addToolbarItem(ToolbarItemType.CLEAR_ITEM);
        }

        return toolbar;
    }
}
