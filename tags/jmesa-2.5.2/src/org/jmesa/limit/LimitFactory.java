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

/**
 * Create a Limit and RowSelect object.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface LimitFactory {
    /**
     * <p>
     * Create a Limit object that is populated with the FilterSet, SortSet, and Export. Be aware
     * though that the Limit object is still incomplete. You still need to set a RowSelect on the
     * Limit to make the object complete.
     * </p>
     * 
     * <p>
     * One reason to create the Limit separately from the RowSelect is if you are going to manually
     * filter and sort the table to only return one page of data. If you are doing that then you
     * should use the FilterSet to manually filter the table to figure out the totalRows.
     * </p>
     * 
     * @return The created Limit object.
     */
    public Limit createLimit();

    /**
     * Utilize the State interface to persist the Limit in the users HttpSession. Will persist the
     * Limit by the id.
     *
     * @param stateAttr The parameter that will be searched to see if the state should be used.
     * @deprecated The State should be set directly on the Limit. This really should not be a part of the interface because
     *             it is an implementation detail.
     */
    @Deprecated
    public void setStateAttr(String stateAttr);

    /**
     * <p>
     * Create the RowSelect object. This is created with dynamic values for the page and maxRows.
     * What this means is if the user is paginating or selected a different maxRows on the table
     * then the RowSelect will be created using those values.
     * </p>
     * 
     * <p>
     * If you do not want the RowSelect to be created based on how the user is interacting with the
     * table, then do not use this method. Instead you should instantiate the RowSelectImpl object
     * yourself and set it on the Limit.
     * </p>
     * 
     * @param maxRows The maximum page rows that should be displayed on the current page.
     * @param totalRows The total rows across all the pages.
     * 
     * @return The created RowSelect object.
     */
    public RowSelect createRowSelect(int maxRows, int totalRows);

    /**
     * <p>
     * Create the RowSelect object. This is created with dynamic values for the page and maxRows.
     * What this means is if the user is paginating or selected a different maxRows on the table
     * then the RowSelect will be created using those values.
     * </p>
     * 
     * <p>
     * In additon set the RowSelect on the Limit at the same time. This is a convenience method to
     * make things a little easier.
     * </p>
     * 
     * <p>
     * If you do not want the RowSelect to be created based on how the user is interacting with the
     * table, then do not use this method. Instead you should instantiate the RowSelectImpl object
     * yourself and set it on the Limit.
     * </p>
     * 
     * @param maxRows The maximum page rows that should be displayed on the current page.
     * @param totalRows The total rows across all the pages.
     * 
     * @return The created RowSelect object.
     */
    public RowSelect createRowSelect(int maxRows, int totalRows, Limit limit);

    /**
     * <p>
     * A convenience method to create the Limit and RowSelect at the same time. This takes in
     * account whether or not the table is being exported. If the table is being exported a new
     * RowSelect object is created, starting at page one, and the maxRows and totalRows set to the
     * value of the totalRows. If the table is not being exported then the RowSelect will be created
     * based on how the user is interacting with the table. What this means is if the user is
     * paginating or selected a different maxRows on the table then the RowSelect will be created
     * using those values.
     * </p>
     * 
     * <p>
     * If you do not want the RowSelect to be created based on how the user is interacting with the
     * table, then do not use this method. Instead create the Limit using the createLimit() method
     * and then create a RowSelect object separately.
     * </p>
     * 
     * <p>
     * This method is also not useful if you are using the Limit to filter and sort manually and
     * only return one page of data. This is because when you are manually filtering you do not know
     * the totalRows until you use the FilterSet to do the filtering.
     * </p>
     * 
     * @param maxRows The maximum page rows that should be displayed on the current page.
     * @param totalRows The total rows across all the pages.
     * 
     * @return The created Limit that is populated with the RowSelect object.
     */
    public Limit createLimitAndRowSelect(int maxRows, int totalRows);
}
