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
package org.jmesa.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.limit.Limit;
import org.jmesa.limit.state.SessionState;
import org.jmesa.limit.state.State;
import org.jmesa.util.SupportUtils;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for the TableFacade.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class TableFacadeUtils {
    private static final Logger logger = LoggerFactory.getLogger(TableFacadeUtils.class);

    public static final String TABLE_REFRESHING = "tr_";
    
    private TableFacadeUtils() {}
    
    /**
     * There needs to be a way to tell if the table is refreshing, as opposed to being run for the first time.
     * 
     * @param id The table identifier.
     * @param webContext The web context.
     * @return Is true if the table is being refreshed.
     */
    static boolean isTableRefreshing(String id, WebContext webContext) {
        String refreshing = webContext.getParameter(id + "_" + TABLE_REFRESHING);
        if (StringUtils.isNotEmpty(refreshing) && refreshing.equals("true")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Filter the items by the rows in the worksheet.
     * 
     * @param items The collection of beans or maps.
     * @param worksheet The current worksheet.
     * @return The filtered items.
     */
    static Collection<?> filterWorksheetItems(Collection<?> items, Worksheet worksheet) {
        if (!worksheet.isFiltering()) {
            return items;
        }

        Collection<WorksheetRow> worksheetRows = worksheet.getRows();

        if (items.size() == worksheetRows.size()) {
            return items;
        }

        List<Object> results = new ArrayList<Object>();

        for (Object item : items) {
            for (WorksheetRow worksheetRow : worksheetRows) {
                String uniqueProperty = worksheetRow.getUniqueProperty().getName();
                String uniquePropertyValue = worksheetRow.getUniqueProperty().getValue();
                try {
                    Object value = PropertyUtils.getProperty(item, uniqueProperty);
                    if (value.toString().equals(uniquePropertyValue)) {
                        results.add(item);
                    }

                } catch (Exception e) {
                    logger.error("Had problems evaluating the items.", e);
                }
            }
        }

        return results;
    }

    /**
     * Retrieve the Limit from the State implemenation.
     * 
     * @param id The table identifier.
     * @param stateAttr The attribute used to retrieve the Limit.
     * @param request The servlet request.
     * @return The Limit stored by the State implementation.
     * @deprecated This method is not used in the core api and will be removed.
     */
    @Deprecated
    public static Limit retrieveLimit(String id, String stateAttr, HttpServletRequest request) {
        WebContext webContext = new HttpServletRequestWebContext(request);
        State state = new SessionState(id, stateAttr);
        SupportUtils.setWebContext(state, webContext);
        return state.retrieveLimit();
    }
}
