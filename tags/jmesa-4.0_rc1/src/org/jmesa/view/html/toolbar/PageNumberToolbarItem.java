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
import org.jmesa.view.html.HtmlBuilder;

/**
 * @since 2.3.2
 * @author Jeff Johnston
 */
public class PageNumberToolbarItem extends AbstractToolbarItem {
		
    private int page;

    public PageNumberToolbarItem(CoreContext coreContext, int page) {
		
        super(coreContext);
        this.page = page;
    }

    public String render() {
		
        Limit limit = getCoreContext().getLimit();
        int currentPage = limit.getRowSelect().getPage();

        if (currentPage == page) {
            return disabled(page);
        }

        StringBuilder action = new StringBuilder("javascript:");
        action.append("jQuery.jmesa.setPage('" + limit.getId() + "','" + page + "');" + getOnInvokeActionJavaScript());
        return enabled(action.toString(), page);
    }    
    
    protected String disabled(int page) {
		
        HtmlBuilder html = new HtmlBuilder();

        html.span();
        html.styleClass(getStyleClass());
        html.style(getStyle());
        html.close();
        html.append(String.valueOf(page));
        html.spanEnd();

        return html.toString();
    }

    protected String enabled(String action, int page) {
		
        HtmlBuilder html = new HtmlBuilder();

        html.span();
        html.styleClass(getStyleClass());
        html.style(getStyle());
        html.close();

        html.a().href();
        html.quote();
        html.append(action);
        html.quote().close();
        html.append(String.valueOf(page));
        html.aEnd();

        html.spanEnd();

        return html.toString();
    }
}
