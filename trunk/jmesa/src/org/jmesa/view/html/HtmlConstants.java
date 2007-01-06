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
    }
    
    // toolbar image names
    public final static String TOOLBAR_CLEAR_IMAGE = "clear";
    public final static String TOOLBAR_FIRST_PAGE_IMAGE = "firstPage";
    public final static String TOOLBAR_FIRST_PAGE_DISABLED_IMAGE = "firstPageDisabled";
    public final static String TOOLBAR_LAST_PAGE_IMAGE = "lastPage";
    public final static String TOOLBAR_LAST_PAGE_DISABLED_IMAGE = "lastPageDisabled";
    public final static String TOOLBAR_NEXT_PAGE_IMAGE = "nextPage";
    public final static String TOOLBAR_NEXT_PAGE_DISABLED_IMAGE = "nextPageDisabled";
    public final static String TOOLBAR_PREV_PAGE_IMAGE = "prevPage";
    public final static String TOOLBAR_PREV_PAGE_DISABLED_IMAGE = "prevPageDisabled";
    public final static String TOOLBAR_FILTER_ARROW_IMAGE = "filterArrow";
    public final static String TOOLBAR_FILTER_IMAGE = "filter";
    public final static String TOOLBAR_SEPARATOR_IMAGE = "separator";
    public final static String SORT_ASC_IMAGE = "sortAsc";
    public final static String SORT_DESC_IMAGE = "sortDesc";

    // toolbar text tooltip messages
    public final static String TOOLBAR_FIRST_PAGE_TOOLTIP = "toolbar.tooltip.firstPage";
    public final static String TOOLBAR_LAST_PAGE_TOOLTIP = "toolbar.tooltip.lastPage";
    public final static String TOOLBAR_PREV_PAGE_TOOLTIP = "toolbar.tooltip.prevPage";
    public final static String TOOLBAR_NEXT_PAGE_TOOLTIP = "toolbar.tooltip.nextPage";
    public final static String TOOLBAR_FILTER_TOOLTIP = "toolbar.tooltip.filter";
    public final static String TOOLBAR_CLEAR_TOOLTIP = "toolbar.tooltip.clear";

    // toolbar text messages
    public final static String TOOLBAR_FIRST_PAGE_TEXT = "toolbar.text.firstPage";
    public final static String TOOLBAR_LAST_PAGE_TEXT = "toolbar.text.lastPage";
    public final static String TOOLBAR_NEXT_PAGE_TEXT = "toolbar.text.nextPage";
    public final static String TOOLBAR_PREV_PAGE_TEXT = "toolbar.text.prevPage";
    public final static String TOOLBAR_FILTER_TEXT = "toolbar.text.filter";
    public final static String TOOLBAR_CLEAR_TEXT = "toolbar.text.clear";

    // statusbar text messages
    public final static String STATUSBAR_RESULTS_FOUND = "statusbar.resultsFound";
    public final static String STATUSBAR_NO_RESULTS_FOUND = "statusbar.noResultsFound";
}
