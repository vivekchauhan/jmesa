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
import org.jmesa.view.ContextSupport;
import org.jmesa.view.html.HtmlUtils;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class NextPageItemRenderer extends ContextSupport implements ToolbarItemRenderer {
	public NextPageItemRenderer(CoreContext coreContext) {
		setCoreContext(coreContext);
	}
	
	public String render(ToolbarItem item) {
        Limit limit = getCoreContext().getLimit();
		int page = limit.getRowSelect().getPage();
        
        StringBuffer action = new StringBuffer("javascript:");
        action.append("LimitManager.getLimit('" + limit.getId() + "').setPage('" + (page + 1) + "');onInvokeAction()");
        
        item.setAction(action.toString());

        int totalPages = HtmlUtils.getTotalPages(getCoreContext());
        if (!HtmlUtils.isNextPageEnabled(page, totalPages)) {
            return item.disabled();
        } 
        
        return item.enabled();
	}
}
