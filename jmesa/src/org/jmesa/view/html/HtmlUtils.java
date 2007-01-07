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
import org.jmesa.limit.Filter;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Sort;
import org.jmesa.web.WebContext;

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
    
	public static String initJavascriptLimit(Limit limit) {
		HtmlBuilder html = new HtmlBuilder();
		
		html.newline();
		html.script().type("text/javascript").close();
		html.newline();
		
		html.append("addLimitToManager('" + limit.getId() + "')").semicolon().newline();
		
		html.append("setPageToLimit('" + limit.getId() + "','" + limit.getRowSelect().getPage() + "')").semicolon().newline();
		
		html.append("setMaxRowsToLimit('" + limit.getId() + "','" + limit.getRowSelect().getMaxRows() + "')").semicolon().newline();
		
		for(Sort sort: limit.getSortSet().getSorts()) {
			html.append("addSortToLimit('" + limit.getId() + "','" + sort.getProperty() + "','" + sort.getOrder().toParam() + "','" + sort.getPosition() + "')").semicolon().newline();
		}

		for(Filter filter: limit.getFilterSet().getFilters()) {
			html.append("addFilterToLimit('" + limit.getId() + "','" + filter.getProperty() + "','" + filter.getValue() + "')").semicolon().newline();
		}
		
		html.scriptEnd();
		
		return html.toString();
	}
	
	public static String imagesPath(WebContext webContext, CoreContext coreContext) {
		String contextPath = webContext.getContextPath();
		String imagesPath = coreContext.getMessage(HtmlConstants.IMAGES_PATH);
		if (imagesPath == null) {
			imagesPath = coreContext.getPreference(HtmlConstants.IMAGES_PATH);
		}
		
		return contextPath + imagesPath;
	}    
}
