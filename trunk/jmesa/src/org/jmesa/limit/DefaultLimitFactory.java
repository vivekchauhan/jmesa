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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jeff Johnston
 */
public class DefaultLimitFactory implements LimitFactory {
	private Map<String, ?> parameters;
	private String prefix;

	public DefaultLimitFactory(Map<String, ?> parameters, String id) {
		this.parameters = parameters;
		this.prefix = id + "_";
	}

	public int getMaxRows() {
        String maxRows = getValue(parameters.get(prefix + Action.MAX_ROWS));
        if (StringUtils.isNotBlank(maxRows)) {
            return Integer.parseInt(maxRows);
        }

        return -1;
	}

	public int getPage() {
        String page = getValue(parameters.get(prefix + Action.PAGE));
        if (StringUtils.isNotBlank(page)) {
            return Integer.parseInt(page);
        }

        return 1;
	}

	public FilterSet getFilterSet() {
		FilterSet filterSet = new FilterSet();
		
		for (String parameter: parameters.keySet()) {
			if (parameter.startsWith(prefix + Action.FILTER)) {
				String value = getValue(parameters.get(parameter));
				if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, prefix + Action.FILTER);
                    Filter filter = new Filter(property, value); 
                    filterSet.addFilter(filter);
                }                    
			}
		}
		
		return filterSet;
	}

	public SortSet getSortSet() {
		SortSet sortSet = new SortSet();
		
		for (String parameter: parameters.keySet()) {
			if (parameter.startsWith(prefix + Action.SORT)) {
				String value = getValue(parameters.get(parameter));
				if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, prefix + Action.SORT);
                    Sort sort = new Sort(property, Order.getOrder(value));
                    sortSet.addSort(sort);
                }                    
			}
		}
		
		return sortSet;
	}

	public boolean isExported() {
		return false;
	}

	public ExportType getExportType() {
		return null;
	}
	
    /**
     * The value needs to be a String. A String[] or List will be
     * converted to a String. In addition it will attempt to do a String
     * conversion for other object types.
     * 
     * @param value The value to convert to an String.
     * @return A String[] value.
     */
    private String getValue(Object value) {
        if (value instanceof Object[]) {
        	if (((Object[])value).length == 1) {
        		return String.valueOf(((Object[])value)[0]);
        	}
        } else if (value instanceof List) {
            List<?> valueList = (List<?>) value;
            if (((List)valueList).size() == 1) {
            	return String.valueOf(((List)valueList).get(0));
            }
        }

        return String.valueOf(value);
    }
}
