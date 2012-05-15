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

import org.jmesa.core.CoreContext;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlHeaderRendererTest extends AbstractTestCase {
		
	@Test
	public void getStyleClass() {
		
		WebContext webContext = createWebContext();
		CoreContext coreContext = createCoreContext(webContext);
		
		HtmlColumn column = new HtmlColumn("firstName");
		column.setWebContext(webContext);
		column.setCoreContext(coreContext);
		
		HtmlHeaderRenderer headerRenderer = new HtmlHeaderRenderer();
        headerRenderer.setColumn(column);
		headerRenderer.setWebContext(webContext);
		headerRenderer.setCoreContext(coreContext);
	}
}
