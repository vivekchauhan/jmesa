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
package org.jmesa.limit;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Figure out how the user interacted with the table.
 *
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitActionFactory {
		
    private Logger logger = LoggerFactory.getLogger(LimitActionFactory.class);
    
    private final Map<?, ?> parameters;
    private final String id;
    private final String prefixId;

    public LimitActionFactory(String id, Map<?, ?> parameters) {
		
        this.id = id;
        this.parameters = parameters;
        this.prefixId = id + "_";
    }

    public String getId() {
		
        return id;
    }

    /**
     * @return The max rows based on what the user selected. A null returned implies the default
     *         must be used.
     */
    public Integer getMaxRows() {
		
        String maxRows = LimitUtils.getValue(parameters.get(prefixId + Action.MAX_ROWS.toParam()));
        if (StringUtils.isNotBlank(maxRows)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Max Rows:" + maxRows);
            }
            return Integer.parseInt(maxRows);
        }

        return null;
    }

    /**
     * @return The current page based on what the user selected. The default is to return the first
     *         page.
     */
    public int getPage() {
		
        String page = LimitUtils.getValue(parameters.get(prefixId + Action.PAGE.toParam()));
        if (StringUtils.isNotBlank(page)) {
            if (logger.isDebugEnabled()) {
                logger.debug("On Page :" + page);
            }
            return Integer.parseInt(page);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Defaulting to Page 1");
        }

        return 1;
    }

    public FilterSet getFilterSet() {
		
        FilterSet filterSet = new FilterSet();

        String clear = LimitUtils.getValue(parameters.get(prefixId + Action.CLEAR.toParam()));
        if (StringUtils.isNotEmpty(clear)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Cleared out the filters.");
            }
            return filterSet;
        }

        for (Object param : parameters.keySet()) {
            String parameter = (String) param;
            if (parameter.startsWith(prefixId + Action.FILTER.toParam())) {
                String value = LimitUtils.getValue(parameters.get(parameter));
                if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, prefixId + Action.FILTER.toParam());
                    Filter filter = new Filter(property, value);
                    filterSet.addFilter(filter);
                }
            }
        }

        return filterSet;
    }

    public SortSet getSortSet() {
		
        SortSet sortSet = new SortSet();

        for (Object param : parameters.keySet()) {
            String parameter = (String) param;
            if (parameter.startsWith(prefixId + Action.SORT.toParam())) {
                String value = LimitUtils.getValue(parameters.get(parameter));
                if (StringUtils.isNotBlank(value)) {
                    String position = StringUtils.substringBetween(parameter, prefixId + Action.SORT.toParam(), "_");
                    String property = StringUtils.substringAfter(parameter, prefixId + Action.SORT.toParam() + position + "_");
                    Order order = Order.valueOfParam(value);
                    Sort sort = new Sort(new Integer(position), property, order);
                    sortSet.addSort(sort);
                }
            }
        }

        return sortSet;
    }

    /**
     * @return The current export type based on what the user selected.
     */
    public String getExportType() {
		
        String exportType = LimitUtils.getValue(parameters.get(prefixId + Action.EXPORT.toParam()));

        if (logger.isDebugEnabled()) {
            logger.debug("Export Type: " + exportType == null ? "" : exportType);
        }

        return exportType;
    }

    @Override
    public String toString() {
		
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("prefixId", prefixId);
        if (parameters != null) {
            parameters.toString();
        }
        return builder.toString();
    }
}
