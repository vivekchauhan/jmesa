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
package org.jmesa.core;

import java.util.Collection;

import org.jmesa.core.match.FilterMatch;
import org.jmesa.core.match.MatchKey;
import org.jmesa.core.match.FilterMatchRegistry;
import org.jmesa.core.match.FilterMatchRegistryImpl;
import org.jmesa.core.match.StringMatch;
import org.jmesa.core.resource.ResourceBundleMessages;
import org.jmesa.limit.Limit;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultCoreContextFactory implements CoreContextFactory {
	private WebContext webContext;
	private FilterMatchRegistry registry;
	private RowFilter rowFilter;
	private ColumnSort columnSort;
	private Preferences preferences;
	private Messages messages;
	private boolean enableFilterAndSort;
	
	public DefaultCoreContextFactory(WebContext webContext) {
		this.webContext = webContext;
	}
	
	public DefaultCoreContextFactory(WebContext webContext, boolean enableFilterAndSort) {
		this.webContext = webContext;
		this.enableFilterAndSort = enableFilterAndSort;
	}

	protected FilterMatchRegistry getFilterMatchRegistry() {
		if (registry == null) {
			registry = new FilterMatchRegistryImpl();
			MatchKey key = new MatchKey(String.class);
			FilterMatch match = new StringMatch();
			registry.addFilterMatch(key, match);
		}

		return registry;
	}
	
	public void setFilterMatchRegistry(FilterMatchRegistry registry) {
		this.registry = registry;
	}
	
	public void addFilterMatch(MatchKey key, FilterMatch match) {
		getFilterMatchRegistry().addFilterMatch(key, match);
	}
	
	protected RowFilter getRowFilter() {
		if (rowFilter == null) {
			rowFilter = new SimpleRowFilter(getFilterMatchRegistry());
		}
		
		return rowFilter;
	}
	
	public void setRowFilter(RowFilter rowFilter) {
		this.rowFilter = rowFilter;
	}
	
	protected ColumnSort getColumnSort() {
		if (columnSort == null) {
			columnSort = new MultiColumnSort();
		}
		
		return columnSort;
	}
	
	public void setColumnSort(ColumnSort columnSort) {
		this.columnSort = columnSort;
	}
	
	protected Preferences getPreferences() {
		if (preferences == null) {
			String jmesaPreferencesLocation = (String) webContext.getApplicationInitParameter("jmesaPreferencesLocation");
			preferences = new PropertiesPreferences(null, jmesaPreferencesLocation);
		}
		
		return preferences;
	}
	
	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}
	
	protected Messages getMessages() {
		if (messages == null) {
			String jmesaMessagesLocation = (String) webContext.getApplicationInitParameter("jmesaMessagesLocation");
			messages = new ResourceBundleMessages(webContext, jmesaMessagesLocation);
		}
		
		return messages;
	}
	
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	
	public CoreContext createCoreContext(Collection<Object> items, Limit limit) {
		Items itemsImpl;
		
		if (enableFilterAndSort) {
			itemsImpl = new ItemsImpl(items, limit, getRowFilter(), getColumnSort());
		} else {
			itemsImpl = new ItemsImpl(items, limit, new DefaultRowFilter(), new DefaultColumnSort());
		}
		
		CoreContext coreContextImpl = new CoreContextImpl(itemsImpl, limit, getPreferences(), getMessages());
		return coreContextImpl;
	}
}
