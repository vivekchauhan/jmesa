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
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Row;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class HtmlToolbar extends AbstractToolbar {

    @Override
    public String render() {
        if (hasToolbarItems()) { // already has items
            return super.render();
        }
        
        addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);
        
        if (enablePageItems) {
            addToolbarItem(ToolbarItemType.PAGE_ITEMS);
        }
        
        addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);
        addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);
        
        if (enableSeparators) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
        }

        MaxRowsItem maxRowsItem = (MaxRowsItem) addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);
        if (getMaxRowsIncrements() != null) {
            maxRowsItem.setIncrements(getMaxRowsIncrements());
        }
        
        boolean exportable = ViewUtils.isExportable(getExportTypes());

        if (exportable && enableSeparators) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
        }
        
        if (exportable) {
            addExportToolbarItems(getExportTypes());
        }
        
        Row row = getTable().getRow();
        List columns = row.getColumns();
        
        boolean filterable = ViewUtils.isFilterable(columns);

        if (filterable && enableSeparators) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
        }

        if (filterable) {
            addToolbarItem(ToolbarItemType.FILTER_ITEM);
            addToolbarItem(ToolbarItemType.CLEAR_ITEM);
        }
        
        boolean editable = ViewUtils.isEditable(getCoreContext().getWorksheet());
        if (editable && enableSeparators) {
            addToolbarItem(ToolbarItemType.SEPARATOR);
        }

        if (editable) {
            addToolbarItem(ToolbarItemType.SAVE_WORKSHEET_ITEM);
            addToolbarItem(ToolbarItemType.FILTER_WORKSHEET_ITEM);
        }

        return super.render();
    }    
}
