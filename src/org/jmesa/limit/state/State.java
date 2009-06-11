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
package org.jmesa.limit.state;

import org.jmesa.limit.Limit;

/**
 * <p>
 * The interface to set and retrieve the table Limit. Implementations will set the Limit so that it
 * can be retrieved at a later time. This is useful so a user can return to a specific table with it
 * filtered, sorted, and paged exactly like they left it.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface State {
    /**
     * @return The Limit that will be used to render the table.
     */
    public Limit retrieveLimit();

    /**
     * @param limit The Limit that represents the current state of the table. Typically the Limit is
     *            persisted (long term or temporary) by being keyed with the table id.
     */
    public void persistLimit(Limit limit);
}
