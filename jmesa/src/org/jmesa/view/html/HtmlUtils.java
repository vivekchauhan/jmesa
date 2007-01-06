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

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlUtils {
	private HtmlUtils(){}
	
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
}
