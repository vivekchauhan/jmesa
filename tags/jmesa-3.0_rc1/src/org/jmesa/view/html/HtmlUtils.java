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

import org.jmesa.core.CoreContext;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlUtils {
    private HtmlUtils() {}

    public static boolean isFirstPageEnabled(int page) {
        if (page == 1) {
            return false;
        }

        return true;
    }

    public static boolean isPrevPageEnabled(int page) {
        if (page - 1 < 1) {
            return false;
        }

        return true;
    }

    public static boolean isNextPageEnabled(int page, int totalPages) {
        if (page + 1 > totalPages) {
            return false;
        }

        return true;
    }

    public static boolean isLastPageEnabled(int page, int totalPages) {
        if (page == totalPages || totalPages == 0) {
            return false;
        }

        return true;
    }

    public static int totalPages(CoreContext coreContext) {
        int maxRows = coreContext.getLimit().getRowSelect().getMaxRows();

        if (maxRows == 0) {
            maxRows = coreContext.getLimit().getRowSelect().getTotalRows();
        }

        int totalRows = coreContext.getLimit().getRowSelect().getTotalRows();

        int totalPages = 1;

        if (maxRows != 0) {
            totalPages = totalRows / maxRows;
        }

        if ((maxRows != 0) && ((totalRows % maxRows) > 0)) {
            totalPages++;
        }

        return totalPages;
    }

    /**
     * Find the starting rowcount for each page. The default is to start at 0. However, if the includePagination
     * preference (html.rowcount.includePagination) is set to true then each page will start at the row
     * that corresponds with the current page.
     */
    public static int startingRowcount(CoreContext coreContext) {
        int rowcount = 0;

        boolean rowcountIncludePagination = Boolean.valueOf(coreContext.getPreference(HtmlConstants.ROWCOUNT_INCLUDE_PAGINATION));
        if (rowcountIncludePagination) {
            int page = coreContext.getLimit().getRowSelect().getPage();
            int maxRows = coreContext.getLimit().getRowSelect().getMaxRows();
            rowcount = (page - 1) * maxRows;
        }

        return rowcount;
    }

    /**
     * Look in the preferences to find out if the document.ready script should be used.
     * 
     * @since 2.2
     * @return Is true if including the document.ready script to initialize limit.
     */
    public static boolean useDocumentReadyToInitJavascriptLimit(CoreContext coreContext) {
        String useDocumentReady = coreContext.getPreference(HtmlConstants.SNIPPETS_INIT_JAVASCRIPT_LIMIT_USE_DOCUMENT_READY);
        return useDocumentReady.equals("true");
    }

    public static String imagesPath(WebContext webContext, CoreContext coreContext) {
        String imagesUrl = coreContext.getMessage(HtmlConstants.IMAGES_URL);
        if (imagesUrl == null) {
            imagesUrl = coreContext.getPreference(HtmlConstants.IMAGES_URL);
        }
        if (imagesUrl != null) {
            return imagesUrl;
        }

        String contextPath = webContext.getContextPath();
        String imagesPath = coreContext.getMessage(HtmlConstants.IMAGES_PATH);
        if (imagesPath == null) {
            imagesPath = coreContext.getPreference(HtmlConstants.IMAGES_PATH);
        }

        return contextPath + imagesPath;
    }
}
