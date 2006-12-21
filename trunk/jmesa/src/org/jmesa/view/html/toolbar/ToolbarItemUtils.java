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
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlUtils;

/**
 * @author Jeff Johnston
 */
public final class ToolbarItemUtils {
    public static void buildFirstPage(HtmlBuilder html, CoreContext coreContext, ToolbarItem item) {
        //String action = new TableActions(coreContext).getPageAction(1);
        //item.setAction(action);

        int page = coreContext.getLimit().getRowSelect().getPage();
        if (!HtmlUtils.isFirstPageEnabled(page)) {
            item.disabled(html);
        } else {
            item.enabled(html, coreContext);
        }
    }

    public static void buildPrevPage(HtmlBuilder html, CoreContext coreContext, ToolbarItem item) {
        int page = coreContext.getLimit().getRowSelect().getPage();
        //String action = new TableActions(coreContext).getPageAction(page - 1);
        //item.setAction(action);

        if (!HtmlUtils.isPrevPageEnabled(page)) {
            item.disabled(html);
        } else {
            item.enabled(html, coreContext);
        }
    }

    public static void buildNextPage(HtmlBuilder html, CoreContext coreContext, ToolbarItem item) {
        int page = coreContext.getLimit().getRowSelect().getPage();
        //String action = new TableActions(coreContext).getPageAction(page + 1);
        //item.setAction(action);

        int totalPages = HtmlUtils.getTotalPages(coreContext);
        if (!HtmlUtils.isNextPageEnabled(page, totalPages)) {
            item.disabled(html);
        } else {
            item.enabled(html, coreContext);
        }
    }

    public static void buildLastPage(HtmlBuilder html, CoreContext coreContext, ToolbarItem item) {
        int totalPages = HtmlUtils.getTotalPages(coreContext);
        //String action = new TableActions(coreContext).getPageAction(totalPages);
        //item.setAction(action);

        int page = coreContext.getLimit().getRowSelect().getPage();
        if (!HtmlUtils.isLastPageEnabled(page, totalPages)) {
            item.disabled(html);
        } else {
            item.enabled(html, coreContext);
        }
    }

    public static void buildFilter(HtmlBuilder html, CoreContext coreContext, ToolbarItem item) {
        //item.setAction(new TableActions(coreContext).getFilterAction());
        item.enabled(html, coreContext);
    }

    public static void buildClear(HtmlBuilder html, CoreContext coreContext, ToolbarItem item) {
        //item.setAction(new TableActions(coreContext).getClearAction());
        item.enabled(html, coreContext);
    }

    public static void buildExport(HtmlBuilder html, CoreContext coreContext, ToolbarItem item, Object export) {
        //String action = new TableActions(coreContext).getExportAction(export.getView(), export.getFileName());
        //item.setAction(action);
        item.enabled(html, coreContext);
    }
}
