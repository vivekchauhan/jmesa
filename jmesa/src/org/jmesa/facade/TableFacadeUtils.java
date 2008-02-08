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
import static org.jmesa.core.CoreContextFactoryImpl.JMESA_MESSAGES_LOCATION;

import org.jmesa.core.message.Messages;
import org.jmesa.core.message.ResourceBundleMessages;
import org.jmesa.limit.Limit;
import org.jmesa.limit.state.SessionState;
import org.jmesa.limit.state.State;
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

    private static Logger logger = LoggerFactory.getLogger(TableFacadeUtils.class);

    private TableFacadeUtils() {
    }

    /**
     * @return The default messages.
     */
    public static Messages getMessages(WebContext webContext) {
        String jmesaMessagesLocation = (String) webContext.getApplicationInitParameter(JMESA_MESSAGES_LOCATION);
        return new ResourceBundleMessages(jmesaMessagesLocation, webContext);
    }

    /**
     * Filter the items by the rows in the worksheet.
     * 
     * @param items The collection of beans or maps.
     * @param worksheet The current worksheet.
     * @return The filtered items.
     */
    static Collection<?> filterWorksheetItems(Collection<?> items, Worksheet worksheet) {
        if (!worksheet.isFiltered()) {
            return items;
        }

        Collection<WorksheetRow> worksheetRows = worksheet.getRows();

        if (items.size() == worksheetRows.size()) {
            return items;
        }

        List results = new ArrayList();

        for (Object item : items) {
            for (WorksheetRow worksheetRow : worksheetRows) {
                String uniqueProperty = worksheetRow.getUniqueProperty().getProperty();
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

    public static Limit retrieveLimit(String id, String stateAttr, HttpServletRequest request) {
        WebContext webContext = new HttpServletRequestWebContext(request);
        State state = new SessionState(id, stateAttr, webContext);
        return state.retrieveLimit();
    }
}
