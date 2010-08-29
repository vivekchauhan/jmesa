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

import org.jmesa.core.CoreContext;
import org.jmesa.limit.Limit;
import org.jmesa.worksheet.Worksheet;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterWorksheetItemRenderer extends AbstractItemRenderer {
    public FilterWorksheetItemRenderer(ToolbarItem item, CoreContext coreContext) {
        setToolbarItem(item);
        setCoreContext(coreContext);
    }

    public String render() {

        Worksheet worksheet = getCoreContext().getWorksheet();

        if (worksheet != null && worksheet.hasChanges()) {
            Limit limit = getCoreContext().getLimit();
            ToolbarItem item = getToolbarItem();
            StringBuilder action = new StringBuilder("javascript:");
            action.append("jQuery.jmesa.setFilterToWorksheet('" + limit.getId() + "');" + getOnInvokeActionJavaScript(limit, item));
            item.setAction(action.toString());
            return item.enabled();
        } else {
            return getToolbarItem().disabled();
        }
    }
}
