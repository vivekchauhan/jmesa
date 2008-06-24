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
package org.jmesaweb.controller;

import java.util.List;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Row;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.toolbar.AbstractItem;
import org.jmesa.view.html.toolbar.AbstractItemRenderer;
import org.jmesa.view.html.toolbar.MaxRowsItem;
import org.jmesa.view.html.toolbar.AbstractToolbar;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemType;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class FullPaginationToolbar extends AbstractToolbar {

    @Override
    public String render() {
        if (hasToolbarItems()) { // already has items
            return super.render();
        }

        addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);
        addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);

        addPaginationItems();

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

    private void addPaginationItems() {
        Limit limit = getCoreContext().getLimit();
        RowSelect rowSelect = limit.getRowSelect();
        int page = rowSelect.getPage();
        int totalPages = HtmlUtils.totalPages(getCoreContext());

        if (totalPages > 5) {
            int start;
            int end;

            if (page <= 3) { // the first three
                start = 1;
                end = 5;
            } else if (page >= totalPages - 2) { // the last two
                start = totalPages - 4;
                end = totalPages;
            } else { // center everything else
                start = page - 2;
                end = page + 2;
            }

            for (int i = start; i <= end; i++) {
                addToolbarItem(createPaginationItem(i));
            }
        } else {
            for (int i = 1; i <= totalPages; i++) {
                addToolbarItem(createPaginationItem(i));
            }
        }
    }

    private PaginationItem createPaginationItem(int i) {
        PaginationItem item = new PaginationItem(i);
        item.setStyleClass("paginationItem");
        item.setCode("page_number");

        PaginationItemRenderer renderer = new PaginationItemRenderer(item, getCoreContext());
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        return item;
    }

    private static class PaginationItem extends AbstractItem {

        private int page;

        public PaginationItem(int page) {
            this.page = page;
        }

        public int getPage() {
            return page;
        }

        @Override
        public String disabled() {
            HtmlBuilder html = new HtmlBuilder();

            html.span();
            html.styleClass(getStyleClass());
            html.style(getStyle());
            html.close();
            html.append(String.valueOf(page));
            html.spanEnd();

            return html.toString();
        }

        @Override
        public String enabled() {
            HtmlBuilder html = new HtmlBuilder();

            html.span();
            html.styleClass(getStyleClass());
            html.style(getStyle());
            html.close();

            html.a().href();
            html.quote();
            html.append(getAction());
            html.quote().close();
            html.append(String.valueOf(page));
            html.aEnd();

            html.spanEnd();

            return html.toString();
        }
    }

    private static class PaginationItemRenderer extends AbstractItemRenderer {

        public PaginationItemRenderer(ToolbarItem item, CoreContext coreContext) {
            setToolbarItem(item);
            setCoreContext(coreContext);
        }

        public String render() {
            PaginationItem item = (PaginationItem) getToolbarItem();
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
}