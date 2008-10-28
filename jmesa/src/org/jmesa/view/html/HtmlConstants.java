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
package org.jmesa.view.html;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlConstants {
    private HtmlConstants() {
        // hide constructor
    }

    public static final String IMAGES_PATH = "html.imagesPath";
    public static final String TOOLBAR_MAX_ROWS_DROPLIST_INCREMENTS = "html.toolbar.maxRowsDroplist.increments";
    public static final String TOOLBAR_MAX_PAGE_NUMBERS = "html.toolbar.maxPageNumbers";

    // table
    public static final String TABLE_RENDERER_STYLE_CLASS = "html.table.renderer.styleClass";
    public static final String TABLE_COMPONENT_THEME = "html.table.component.theme";
    public static final String ON_INVOKE_ACTION = "html.onInvokeAction";
    public static final String ON_INVOKE_EXPORT_ACTION = "html.onInvokeExportAction";

    // row
    public static final String ROW_RENDERER_HIGHLIGHT_CLASS = "html.row.renderer.highlightClass";
    public static final String ROW_RENDERER_EVEN_CLASS = "html.row.renderer.evenClass";
    public static final String ROW_RENDERER_ODD_CLASS = "html.row.renderer.oddClass";

    // column
    public static final String SORT_ASC_IMAGE = "html.column.header.renderer.image.sortAsc";
    public static final String SORT_DESC_IMAGE = "html.column.header.renderer.image.sortDesc";
    public static final String HEADER_RENDERER_ELEMENT = "html.column.header.renderer.element";
    public static final String DROPLIST_HANDLE_IMAGE = "html.column.filter.renderer.image.droplistHandle";

    // css names
    public static final String TBODY_CLASS = "html.tbodyClass";
    public static final String TITLE_CLASS = "html.titleClass";
    public static final String FILTER_CLASS = "html.filterClass";
    public static final String HEADER_CLASS = "html.headerClass";
    public static final String TOOLBAR_CLASS = "html.toolbarClass";
    public static final String TOOLBAR_PAGE_NUMBER_CLASS = "html.toolbar.pageNumberClass";
    public static final String STATUS_BAR_CLASS = "html.statusBarClass";
    public static final String ROWCOUNT_INCLUDE_PAGINATION = "html.rowcount.includePagination";

    // toolbar image names
    public static final String TOOLBAR_IMAGE = "html.toolbar.image.";
    public static final String TOOLBAR_IMAGE_CLEAR = "html.toolbar.image.clear";
    public static final String TOOLBAR_IMAGE_FIRST_PAGE = "html.toolbar.image.firstPage";
    public static final String TOOLBAR_IMAGE_FIRST_PAGE_DISABLED = "html.toolbar.image.firstPageDisabled";
    public static final String TOOLBAR_IMAGE_LAST_PAGE = "html.toolbar.image.lastPage";
    public static final String TOOLBAR_IMAGE_LAST_PAGE_DISABLED = "html.toolbar.image.lastPageDisabled";
    public static final String TOOLBAR_IMAGE_NEXT_PAGE = "html.toolbar.image.nextPage";
    public static final String TOOLBAR_IMAGE_NEXT_PAGE_DISABLED = "html.toolbar.image.nextPageDisabled";
    public static final String TOOLBAR_IMAGE_PREV_PAGE = "html.toolbar.image.prevPage";
    public static final String TOOLBAR_IMAGE_PREV_PAGE_DISABLED = "html.toolbar.image.prevPageDisabled";
    public static final String TOOLBAR_IMAGE_FILTER = "html.toolbar.image.filter";
    public static final String TOOLBAR_IMAGE_SEPARATOR = "html.toolbar.image.separator";
    public static final String TOOLBAR_IMAGE_SAVE_WORKSHEET = "html.toolbar.image.saveWorksheet";
    public static final String TOOLBAR_IMAGE_FILTER_WORKSHEET = "html.toolbar.image.filterWorksheet";
    public static final String TOOLBAR_IMAGE_SAVE_WORKSHEET_DISABLED = "html.toolbar.image.saveWorksheetDisabled";
    public static final String TOOLBAR_IMAGE_FILTER_WORKSHEET_DISABLED = "html.toolbar.image.filterWorksheetDisabled";

    // toolbar text tooltip messages
    public static final String TOOLBAR_TOOLTIP = "html.toolbar.tooltip.";
    public static final String TOOLBAR_TOOLTIP_FIRST_PAGE = "html.toolbar.tooltip.firstPage";
    public static final String TOOLBAR_TOOLTIP_LAST_PAGE = "html.toolbar.tooltip.lastPage";
    public static final String TOOLBAR_TOOLTIP_PREV_PAGE = "html.toolbar.tooltip.prevPage";
    public static final String TOOLBAR_TOOLTIP_NEXT_PAGE = "html.toolbar.tooltip.nextPage";
    public static final String TOOLBAR_TOOLTIP_FILTER = "html.toolbar.tooltip.filter";
    public static final String TOOLBAR_TOOLTIP_CLEAR = "html.toolbar.tooltip.clear";
    public static final String TOOLBAR_TOOLTIP_SAVE_WORKSHEET = "html.toolbar.tooltip.saveWorksheet";
    public static final String TOOLBAR_TOOLTIP_FILTER_WORKSHEET = "html.toolbar.tooltip.filterWorksheet";

    // toolbar text messages
    public static final String TOOLBAR_TEXT_FIRST_PAGE = "html.toolbar.text.firstPage";
    public static final String TOOLBAR_TEXT_LAST_PAGE = "html.toolbar.text.lastPage";
    public static final String TOOLBAR_TEXT_NEXT_PAGE = "html.toolbar.text.nextPage";
    public static final String TOOLBAR_TEXT_PREV_PAGE = "html.toolbar.text.prevPage";
    public static final String TOOLBAR_TEXT_FILTER = "html.toolbar.text.filter";
    public static final String TOOLBAR_TEXT_CLEAR = "html.toolbar.text.clear";
    public static final String TOOLBAR_TEXT_MAX_ROWS_DROPLIST = "html.toolbar.text.maxRowsDroplist";
    public static final String TOOLBAR_TEXT_SAVE_WORKSHEET = "html.toolbar.text.saveWorksheet";
    public static final String TOOLBAR_TEXT_FILTER_WORKSHEET = "html.toolbar.text.filterWorksheet";

    // statusbar text messages
    public static final String STATUSBAR_RESULTS_FOUND = "html.statusbar.resultsFound";
    public static final String STATUSBAR_NO_RESULTS_FOUND = "html.statusbar.noResultsFound";
    
    public static final String SNIPPETS_INIT_JAVASCRIPT_LIMIT_USE_DOCUMENT_READY = "html.snippets.initJavascriptLimit.useDocumentReady";
}
