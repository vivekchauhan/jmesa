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
package org.jmesa.view.html.toolbar;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.core.PresidentDao;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.limit.RowSelect;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class MaxRowsItemRendererTest {
	private static final String ID = "pres";
	private static final int MAX_ROWS = 15;
	
	@Test
	public void render() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		WebContext webContext = new HttpServletRequestWebContext(request);
		webContext.setParameterMap(getParameters());
		webContext.setLocale(Locale.US);
		
		CoreContext coreContext = createCoreContext(webContext);
		
    	MaxRowsItemImpl item = new MaxRowsItemImpl();

        MaxRowsItemRenderer renderer = new MaxRowsItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);
        
        // render will set everything.
        renderer.render();
		
        int[] increments = item.getIncrements();
        int maxRows = item.getMaxRows();
        
        assertTrue(increments[0] == 15);
        assertTrue(increments[1] == 50);
        assertTrue(increments[2] == 100);
        
        assertTrue(maxRows == 15);
	}
	
	public CoreContext createCoreContext(WebContext webContext) {
		Collection items = new PresidentDao().getPresidents();

		LimitFactory limitFactory = new LimitFactoryImpl(ID, webContext);
		Limit limit = limitFactory.createLimit();
		RowSelect rowSelect = limitFactory.createRowSelect(MAX_ROWS, items.size());
		limit.setRowSelect(rowSelect);

		CoreContextFactory factory = new CoreContextFactoryImpl(webContext, false);
		CoreContext coreContext = factory.createCoreContext(items, limit);
		
		return coreContext;
	}
	
	private Map<?, ?> getParameters() {
		HashMap<String, Object> results = new HashMap<String, Object>();
		return results;
	}
}
