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

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class LimitActionFactoryImpl implements LimitActionFactory {
	private final Map<String, ?> parameters;
	private final String id;
	private final String prefixId;
	
	public LimitActionFactoryImpl(String id, Map<String, ?> parameters) {
		this.id = id;
		this.parameters = parameters;
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
        String maxRows = LimitUtils.getValue(parameters.get(prefixId + Action.MAX_ROWS));
        if (StringUtils.isNotBlank(maxRows)) {
            return Integer.parseInt(maxRows);
        }

        return null;
	}

	/**
	 * @return The current page based on what the user selected. The default is to return
	 * the first page.
	 */
	public int getPage() {
        String page = LimitUtils.getValue(parameters.get(prefixId + Action.PAGE));
        if (StringUtils.isNotBlank(page)) {
            return Integer.parseInt(page);
        }

        return 1;
	}

	public FilterSet getFilterSet() {
		FilterSet filterSet = new FilterSet();
		
        String clear = LimitUtils.getValue(parameters.get(prefixId + Action.CLEAR));
        if (StringUtils.isNotEmpty(clear)) {
            return filterSet;
        }

		
		for (String parameter: parameters.keySet()) {
			if (parameter.startsWith(prefixId + Action.FILTER)) {
				String value = LimitUtils.getValue(parameters.get(parameter));
				if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, prefixId + Action.FILTER);
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
			if (parameter.startsWith(prefixId + Action.SORT)) {
				String value = LimitUtils.getValue(parameters.get(parameter));
				if (StringUtils.isNotBlank(value)) {
                    String property = StringUtils.substringAfter(parameter, prefixId + Action.SORT);
                    Sort sort = new Sort(property, Order.getOrder(value), sortSet.getSorts().size()); //TODO: need to implement the sorting functionality
                    sortSet.addSort(sort);
                }                    
			}
		}
		
		return sortSet;
	}

	public ExportType getExportType() {
		return null;
	}
}
