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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.context.Context;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultLimitActionFactory implements LimitActionFactory {
	private Logger logger = Logger.getLogger(DefaultLimitActionFactory.class.getName());
	private final Map<?, ?> parameters;
	private final String id;
	private final String prefixId;
	
	public DefaultLimitActionFactory(String id, Context context) {
		this.id = id;
		this.parameters = context.getParameterMap();
		this.prefixId = id + "_";
	}

	public String getId() {
		return id; 
	}


	/**
	 * @return The max rows based on what the user selected. A null returned implies the
	 * default must be used.
	 */
	public Integer getMaxRows() {
        String maxRows = LimitUtils.getValue(parameters.get(prefixId + Action.MAX_ROWS.getCode()));
        if (StringUtils.isNotBlank(maxRows)) {
        	logger.log(Level.FINE, "Max Rows: {0}", maxRows);
            return Integer.parseInt(maxRows);
        }

        return null;
	}

	/**
	 * @return The current page based on what the user selected. The default is to return
	 * the first page.
	 */
	public int getPage() {
        String page = LimitUtils.getValue(parameters.get(prefixId + Action.PAGE.getCode()));
        if (StringUtils.isNotBlank(page)) {
        	logger.log(Level.FINE, "On Page: {0}", page);
            return Integer.parseInt(page);
        }

    	logger.fine("Defaulting to Page 1");

    	return 1;
	}

	public FilterSet getFilterSet() {
		FilterSet filterSet = new FilterSet();
		
        String clear = LimitUtils.getValue(parameters.get(prefixId + Action.CLEAR.getCode()));
        if (StringUtils.isNotEmpty(clear)) {
        	logger.fine("Cleared out the filters.");
            return filterSet;
        }

		
		for (Object param: parameters.keySet()) {
			String parameter = (String)param;
			if (parameter.startsWith(prefixId + Action.FILTER.getCode())) {
				String value = LimitUtils.getValue(parameters.get(parameter));
				if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, prefixId + Action.FILTER.getCode());
                    Filter filter = new Filter(property, value); 
                    filterSet.addFilter(filter);
                }                    
			}
		}
		
		return filterSet;
	}

	public SortSet getSortSet() {
		SortSet sortSet = new SortSet();
		
		for (Object param: parameters.keySet()) {
			String parameter = (String)param;
			if (parameter.startsWith(prefixId + Action.SORT.getCode())) {
				String value = LimitUtils.getValue(parameters.get(parameter));
				if (StringUtils.isNotBlank(value)) {
                    String position = StringUtils.substringBetween(parameter, prefixId + Action.SORT.getCode(), "_");
                    String property = StringUtils.substringAfter(parameter, prefixId + Action.SORT.getCode() + position + "_");
                    Order order = Order.getOrder(value);
					Sort sort = new Sort(property, order, new Integer(position));
                    sortSet.addSort(sort);
                }                    
			}
		}
		
		return sortSet;
	}

	public ExportType getExportType() {
		return null;
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
