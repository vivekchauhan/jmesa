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
    PAGE_NUMBER_ITEMS("page_number"),
    FIRST_PAGE_ITEM("first_page"),
    PREV_PAGE_ITEM("prev_page"),
    NEXT_PAGE_ITEM("next_page"),
    LAST_PAGE_ITEM("last_page"),
    MAX_ROWS_ITEM("max_rows"),
    FILTER_ITEM("filter"),
    CLEAR_ITEM("clear"),
    SORT_ITEM("sort"),
    EXPORT_ITEM("export"),
    SEPARATOR("separator"),
    SAVE_WORKSHEET_ITEM("save_worksheet"),
    FILTER_WORKSHEET_ITEM("filter_worksheet"),
    CLEAR_WORKSHEET_ITEM("clear_worksheet"),
    ADD_WORKSHEET_ROW_ITEM("add_worksheet_row");

    private final String code;

    private ToolbarItemType(String code) {
        this.code = code;
    }

    public String toCode() {
        return code;
    }
}
