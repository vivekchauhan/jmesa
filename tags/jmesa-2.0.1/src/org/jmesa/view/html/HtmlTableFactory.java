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
package org.jmesa.view.html;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractTableFactory;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.web.WebContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlTableFactory extends AbstractTableFactory {
    public HtmlTableFactory(WebContext webContext, CoreContext coreContext) {
        setWebContext(webContext);
        setCoreContext(coreContext);
    }

    @Override
    public HtmlTable createTable(String... columnNames) {
        return (HtmlTable) super.createTable(columnNames);
    }

    /**
     * <p>
     * Returns the HtmlTable instance of a Table. This method call 
     * is only useful if using with Groovy because of Groovy bug [GROOVY-1860].
     * The nature of the bug is a method that is overridden and returns
     * a different return type is incorrectly referenced from Groovy, 
     * which makes it impossible to use the createTable() method.
     * </p>
     *  
     * <p>
     * Non-Groovy code should use the overridden createTable() method as this
     * method will be deprecated over time when the bug is fixed.
     * </p>
     * 
     * <p>
     * Referenece Groovy bug [GROOVY-1860] for more details. It is marked by
     * the Groovy team as a blocking bug so hopefully it will be fixed in
     * Groovy 1.1.
     * </p>
     * 
     * @param columnNames The array of columns, specified as varargs.
     * @return The HtmlTable instance.
     */
    public HtmlTable createHtmlTable(String... columnNames) {
        return (HtmlTable) super.createTable(columnNames);
    }

    @Override
    protected HtmlComponentFactory getComponentFactory() {
        return new HtmlComponentFactory(getWebContext(), getCoreContext());
    }
}
