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

import java.io.Serializable;

/**
 * <p>
 * The name Limit comes from the MySQL limit command, and the the purpose of 
 * the Limit interface is to know how to limit the table results. The 
 * implemenation of the Limit knows how the user interacted with the table 
 * with regards to sorting, filtering, paging, max rows to display, and 
 * exporting. With this information you will be able to display the requested 
 * page filtered and sorted correctly in the most efficient manner possible.
 * </p>
 * 
 * <p>
 * Be sure to pay attention to the RowSelect. The RowSelect needs to be added
 * to the Limit so that the row information is available. The catch is the RowSelect
 * cannot be created until the total rows is known, which is is calculated by using 
 * the Limit FilterSet. So first get the Limit and use the FilterSet to figure
 * out the total rows, create the RowSelect, and then add the RowSelect to the Limit.
 * </p>
 *
 * @since 2.0
 * @author Jeff Johnston
 */
public interface Limit extends Serializable {
	/**
	 * @return The code to uniquely identify the table.  
	 */
	public String getId();

	public RowSelect getRowSelect();

	/**
	 * <p>
	 * The RowSelect needs to be set on the Limit for the Limit to be useful.
	 * Of course the RowSelect cannot be created until the total rows is known.
	 * </p>
	 * 
	 * <p>
	 * The idea is you first create a Limit and use the FilterSet to retrieve
	 * the total rows. Once you have the total rows you can create a RowSelect 
	 * and pass it in here.
	 * </p>
	 * 
	 * @param rowSelect The RowSelect to use for this Limit.
	 */
	public void setRowSelect(RowSelect rowSelect);

	public FilterSetImpl getFilterSet();
	
	public void setFilterSet(FilterSetImpl filterSet);

	public SortSetImpl getSortSet();
	
	public void setSortSet(SortSetImpl sortSet);

	public boolean isExported();

	public Export getExport();
	
	public void setExport(Export export);
}
