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

/**
 * @since 2.3.2
 * @author Jeff Johnston
 */
public class PageNumberItemRenderer extends AbstractItemRenderer {

    public PageNumberItemRenderer(ToolbarItem item, CoreContext coreContext) {
        setToolbarItem(item);
        setCoreContext(coreContext);
    }

    public String render() {
        PageNumberItem item = (PageNumberItem) getToolbarItem();
        Limit limit = getCoreContext().getLimit();
        int currentPage = limit.getRowSelect().getPage();
        int page = item.getPage();

        if (currentPage == page) {
            return item.disabled();
        }

        StringBuilder action = new StringBuilder("javascript:");
        action.append("setPageToLimit('" + limit.getId() + "','" + page + "');" + getOnInvokeActionJavaScript(limit, item));
        item.setAction(action.toString());
        return item.enabled();
    }
}
