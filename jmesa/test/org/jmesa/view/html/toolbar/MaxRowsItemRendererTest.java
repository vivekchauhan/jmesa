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

import org.jmesa.core.CoreContext;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class MaxRowsItemRendererTest extends AbstractTestCase {
		
    @Test
    public void render() {
		
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        MaxRowsItem item = new MaxRowsItem();

        MaxRowsItemRenderer renderer = new MaxRowsItemRenderer(item, coreContext);
        renderer.setOnInvokeAction("onInvokeAction");
        item.setToolbarItemRenderer(renderer);

        // render will set everything.
        renderer.render();

        int[] increments = item.getIncrements();
        int maxRows = item.getMaxRows();

        // the defaults
        assertTrue(increments[0] == 12);
        assertTrue(increments[1] == 24);
        assertTrue(increments[2] == 36);

        assertTrue(maxRows == 12);
    }
}
