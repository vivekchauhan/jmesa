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
package org.jmesa.view.html.renderer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jmesa.core.CoreContext;
import org.jmesa.core.President;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.event.AbstractRowEvent;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class HtmlRowRendererTest extends AbstractTestCase {
    @Test
    public void renderWithRowSelect() {
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        HtmlRow row = new HtmlRow();
        row.setCoreContext(coreContext);

        row.setOnclick(new AbstractRowEvent() {
            public String execute(Object item, int rowcount) {
                President president = (President) item;
                Integer id = president.getId();
                return "document.location='jmesa.org?id=" + id + "'";
            }
        });

        HtmlRowRendererImpl renderer = new HtmlRowRendererImpl(row);
        renderer.setCoreContext(coreContext);

        President item = new President();
        item.setId(new Integer(4));

        String html = (String) renderer.render(item, 1);
        assertNotNull(html);
        assertTrue(html.contains("onclick"));
        assertTrue(html.contains("document.location='jmesa.org?id=4'"));
    }
}
