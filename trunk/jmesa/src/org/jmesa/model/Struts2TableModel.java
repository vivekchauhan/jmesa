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
package org.jmesa.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.web.WebContext;

/**
 * @since 3.0
 * @author Jeff Johnston
 */
public class Struts2TableModel extends TableModel {
		
    public Struts2TableModel(String id, HttpServletRequest request) {
		
        TableFacade tableFacade = TableFacadeFactory.createStruts2TableFacade(id, request);
        super.setTableFacade(tableFacade);
    }

    public Struts2TableModel(String id, HttpServletRequest request, HttpServletResponse response) {
		
        TableFacade tableFacade = TableFacadeFactory.createStruts2TableFacade(id, request, response);
        super.setTableFacade(tableFacade);
    }

    public Struts2TableModel(String id, WebContext webContext) {
		
        TableFacade tableFacade = TableFacadeFactory.createStruts2TableFacade(id, webContext);
        super.setTableFacade(tableFacade);
    }

    public Struts2TableModel(String id, WebContext webContext, HttpServletResponse response) {
		
        TableFacade tableFacade = TableFacadeFactory.createStruts2TableFacade(id, webContext, response);
        super.setTableFacade(tableFacade);
    }
}
