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
package org.jmesa.test;

import java.util.Collection;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.President;
import org.jmesa.core.PresidentDao;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.preference.PropertiesPreferences;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.RowSelect;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.worksheet.Worksheet;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractTestCase {
    protected static final String ID = "pres";
    protected static final int MAX_ROWS = 12;
    
    protected WebContext createWebContext() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        return new HttpServletRequestWebContext(request);
    }
    
    protected CoreContext createCoreContext(WebContext webContext) {
        Collection<President> items = PresidentDao.getPresidents();

        LimitFactory limitFactory = new LimitFactory(ID, webContext);
        Limit limit = limitFactory.createLimit();
        
        if (limit.isExported()) {
            RowSelect rowSelect = limitFactory.createRowSelect(items.size(), items.size());
            limit.setRowSelect(rowSelect);
            
        } else {
            RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, items.size());
            limit.setRowSelect(rowSelect);
        }
        
        Worksheet worksheet = getWorksheet();

        CoreContextFactory factory = new CoreContextFactory(false, webContext);
        Preferences preferences = new PropertiesPreferences("/org/jmesa/core/test.properties", webContext);
        factory.setPreferences(preferences);

        CoreContext coreContext = factory.createCoreContext(items, limit, worksheet);
        
        return coreContext;
    }
    
    protected Worksheet getWorksheet() {
        return null;
    }
}
