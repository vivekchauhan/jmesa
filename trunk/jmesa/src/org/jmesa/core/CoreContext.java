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

import java.util.Locale;

import org.jmesa.data.Items;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.SortSet;

/**
 * @TODO add comment
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface CoreContext extends Items, Messages, Preferences {
	public String getId();
	
	public Locale getLocale();
	
	public FilterSet getFilterSet();

	public SortSet getSortSet();

	public ExportType getExportType();

	public boolean isExported();
	
	public int getRowStart();

	public int getRowEnd();

	public int getTotalRows();

	public int getMaxRows();

	public int getPage();
}
