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

import static org.jmesa.view.html.HtmlConstants.TOOLBAR_MAX_ROWS_DROPLIST_INCREMENTS;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.Limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class MaxRowsItemRenderer extends AbstractItemRenderer {
    public MaxRowsItemRenderer(ToolbarItem item, CoreContext coreContext) {
        setToolbarItem(item);
        setCoreContext(coreContext);
    }

    @Override
    public MaxRowsItem getToolbarItem() {
        return (MaxRowsItem) super.getToolbarItem();
    }

    public String render() {
        MaxRowsItem item = getToolbarItem();

        if (item.getIncrements().length == 0) {
            String increments[] = StringUtils.split(getCoreContext().getPreference(TOOLBAR_MAX_ROWS_DROPLIST_INCREMENTS), ",");
            int[] values = new int[increments.length];
            for (int i = 0; i < increments.length; i++) {
                values[i] = Integer.valueOf(increments[i]);
            }
            item.setIncrements(values);
        }

        Limit limit = getCoreContext().getLimit();
        int maxRows = limit.getRowSelect().getMaxRows();
        item.setMaxRows(maxRows);

        if (!incrementsContainsMaxRows(item, maxRows)) {
            throw new IllegalStateException("The maxRowIncrements does not contain the maxRows.");
        }

        StringBuilder action = new StringBuilder("jQuery.jmesa.setMaxRowsToLimit('"
                + limit.getId() + "', this.options[this.selectedIndex].value);"
                + getOnInvokeActionJavaScript(limit, item));
        item.setAction(action.toString());

        return item.enabled();
    }

    private boolean incrementsContainsMaxRows(MaxRowsItem item, int maxRows) {

        boolean found = false;

        int[] increments = item.getIncrements();
        for (int increment : increments) {
            if (increment == maxRows) {
                found = true;
            }
        }

        return found;
    }
}
