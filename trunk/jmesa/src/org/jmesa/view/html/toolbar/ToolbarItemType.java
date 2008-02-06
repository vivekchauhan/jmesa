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

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public enum ToolbarItemType {
    FIRST_PAGE_ITEM, 
    PREV_PAGE_ITEM, 
    NEXT_PAGE_ITEM, 
    LAST_PAGE_ITEM, 
    MAX_ROWS_ITEM, 
    FILTER_ITEM, 
    CLEAR_ITEM, 
    SORT_ITEM, 
    EXPORT_ITEM, 
    SEPARATOR,
    SAVE_WORKSHEET_ITEM,
    FILTER_WORKSHEET_ITEM,
    UNDO_ITEM;

    public String toCode() {
        switch (this) {
        case FIRST_PAGE_ITEM:
            return "first_page";
        case PREV_PAGE_ITEM:
            return "prev_page";
        case NEXT_PAGE_ITEM:
            return "next_page";
        case LAST_PAGE_ITEM:
            return "last_page";
        case MAX_ROWS_ITEM:
            return "max_rows";
        case FILTER_ITEM:
            return "filter";
        case CLEAR_ITEM:
            return "clear";
        case SORT_ITEM:
            return "sort";
        case EXPORT_ITEM:
            return "export";
        case SAVE_WORKSHEET_ITEM:
            return "save_worksheet";
        case FILTER_WORKSHEET_ITEM:
            return "filter_worksheet";
        case UNDO_ITEM:
            return "undo";
        default:
            return "";
        }
    }
}
