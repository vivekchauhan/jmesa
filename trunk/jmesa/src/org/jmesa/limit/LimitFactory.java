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
     * Create a Limit object that is populated with the FilterSet, SortSet, and
     * Export. Be aware though that the Limit object is still incomplete. You
     * still need to set a RowSelect on the Limit to make the object complete.
     * </p>
     * 
     * <p>
     * One reason to create the Limit separately from the RowSelect is if you
     * are going to manually filter and sort the table to only return one page
     * of data. If you are doing that then you should use the FilterSet to
     * manually filter the table to figure out the totalRows.
     * </p>
     * 
     * @return The created Limit object.
     */
    public Limit createLimit();

    public RowSelect createRowSelect(int maxRows, int totalRows);

    public RowSelect createRowSelect(int maxRows, int totalRows, Limit limit);

    public Limit createLimitAndRowSelect(int maxRows, int totalRows);
}
