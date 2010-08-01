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
package org.jmesa.facade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @since 2.1
 * @author Jeff Johnston
 *
 * @deprecated You should extend the TableFacade class directly.
 */
@Deprecated
public class TableFacadeImpl extends TableFacade {
    /**
     * @deprecated You should extend the TableFacade class directly.
     */
    @Deprecated
    public TableFacadeImpl(String id, HttpServletRequest request) {
        super(id, request);
    }

    /**
     * @deprecated You should extend the TableFacade class directly.
     */
    @Deprecated
    public TableFacadeImpl(String id, HttpServletRequest request, HttpServletResponse response) {
        super(id, request, response);
    }
}
