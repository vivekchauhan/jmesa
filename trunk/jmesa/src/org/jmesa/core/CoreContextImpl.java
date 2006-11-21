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
import java.util.Locale;

import org.jmesa.limit.ExportType;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.limit.SortSet;

/**
 * @TODO add comment
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class CoreContextImpl implements CoreContext {
	private Items items;
	private Limit limit;
	private Preferences preferences;
	private Messages messages;
	private Locale locale;

	public CoreContextImpl(Items items, Limit limit, Preferences preferences, Messages messages, Locale locale) {
		this.items = items;
		this.limit = limit;
		this.preferences = preferences;
		this.messages = messages;
		this.locale = locale;
	}
	
	public String getId() {
		return limit.getId();
	}
	
	public Locale getLocale() {
		return locale;
	}

	public Collection getAllItems() {
		return items.getAllItems();
	}

	public Collection getFilteredItems() {
		return items.getFilteredItems();
	}

	public Collection getSortedItems() {
		return items.getSortedItems();
	}

	public Collection getPageItems() {
		return items.getPageItems();
	}

	public String getMessage(String code) {
		return messages.getMessage(code);
	}

	public String getMessage(String code, Object[] args) {
		return messages.getMessage(code);
	}

	public String getPreference(String code) {
		return preferences.getPreference(code);
	}

	public FilterSet getFilterSet() {
		return limit.getFilterSet();
	}

	public SortSet getSortSet() {
		return limit.getSortSet();
	}

	public ExportType getExportType() {
		return limit.getExportType();
	}

	public boolean isExported() {
		return limit.isExported();
	}

	public int getRowEnd() {
		return limit.getRowSelect().getRowEnd();
	}

	public int getRowStart() {
		return limit.getRowSelect().getRowStart();
	}

	public int getMaxRows() {
		return limit.getRowSelect().getMaxRows();
	}

	public int getPage() {
		return limit.getRowSelect().getPage();
	}

	public int getTotalRows() {
		return limit.getRowSelect().getTotalRows();
	}
}
