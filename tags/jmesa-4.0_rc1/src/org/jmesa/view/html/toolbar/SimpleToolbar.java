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

import java.util.ArrayList;
import java.util.List;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Row;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class SimpleToolbar extends AbstractToolbar {
		
    public String render() {
        
        HtmlBuilder html = new HtmlBuilder();

        html.table(2).border("0").cellpadding("0").cellspacing("1").close();

        html.tr(3).close();

        for (ToolbarItem item : getToolbarItems()) {
            html.td(4).close();
            html.append(item.render());
            html.tdEnd();
        }

        html.trEnd(3);

        html.tableEnd(2);
        html.newline();
        html.tabs(2);

        return html.toString();
    }    
    
    protected List<ToolbarItem> getToolbarItems() {
        
        List<ToolbarItem> toolbarItems = new ArrayList<ToolbarItem>();
        
        ToolbarItemFactory toolbarItemFactory = new ToolbarItemFactory(getWebContext(), getCoreContext());
		
        toolbarItems.add(toolbarItemFactory.createFirstPageToolbarItem());
        toolbarItems.add(toolbarItemFactory.createPrevPageToolbarItem());

        String pageNumbersEnabled = getCoreContext().getPreference(HtmlConstants.TOOLBAR_PAGE_NUMBERS_ENABLED);
        
        if (enablePageNumbers || (pageNumbersEnabled != null && pageNumbersEnabled.equals("true"))) {
            toolbarItems.addAll(toolbarItemFactory.createPageNumberToolbarItems());
        }
        
        toolbarItems.add(toolbarItemFactory.createNextPageToolbarItem());
        toolbarItems.add(toolbarItemFactory.createLastPageToolbarItem());
        
        if (enableSeparators) {
            toolbarItems.add(toolbarItemFactory.createSeparatorToolbarItem());
        }

        MaxRowsToolbarItem maxRowsItem = (MaxRowsToolbarItem) toolbarItemFactory.createMaxRowsToolbarItem();
        if (getMaxRowsIncrements() != null) {
            maxRowsItem.setIncrements(getMaxRowsIncrements());
        }
        toolbarItems.add(maxRowsItem);
        
        boolean exportable = ViewUtils.isExportable(getExportTypes());

        if (exportable && enableSeparators) {
            toolbarItems.add(toolbarItemFactory.createSeparatorToolbarItem());
        }
        
        if (exportable) {
            toolbarItems.addAll(toolbarItemFactory.createExportToolbarItems(getExportTypes()));
        }
        
        Row row = getTable().getRow();
        List columns = row.getColumns();
        
        boolean filterable = ViewUtils.isFilterable(columns);

        if (filterable && enableSeparators) {
            toolbarItems.add(toolbarItemFactory.createSeparatorToolbarItem());
        }

        if (filterable) {
            toolbarItems.add(toolbarItemFactory.createFilterToolbarItem());
            toolbarItems.add(toolbarItemFactory.createClearToolbarItem());
        }
        
        boolean editable = ViewUtils.isEditable(getCoreContext().getWorksheet());
        if (editable) {
            if (enableSeparators) {
                toolbarItems.add(toolbarItemFactory.createSeparatorToolbarItem());
            }

            toolbarItems.add(toolbarItemFactory.createSaveWorksheetToolbarItem());
            toolbarItems.add(toolbarItemFactory.createClearWorksheetToolbarItem());
            if (getCoreContext().getPreference(HtmlConstants.TOOLBAR_ADD_WORKSHEET_ROW_ENABLED).equals("true")) {
                toolbarItems.add(toolbarItemFactory.createAddWorksheetRowToolbarItem());
            }
            toolbarItems.add(toolbarItemFactory.createFilterWorksheetToolbarItem());
        }
        
        return toolbarItems;
    }
}
