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
package org.jmesa.view.html.editor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.jmesa.core.CoreContext;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.component.HtmlColumnImpl;
import org.jmesa.view.html.editor.DroplistFilterEditor.Option;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class DroplistFilterEditorTest extends AbstractTestCase {

    @Test
    public void getOptions() {
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        DroplistFilterEditor editor = new DroplistFilterEditor();
        editor.setCoreContext(coreContext);
        editor.setWebContext(webContext);

        Column column = new HtmlColumnImpl("name.firstName");

        editor.setColumn(column);

        Collection<Option> options = editor.getOptions();

        assertNotNull("The options are null.", options);
        assertTrue("Do not have the correct options size.", options.size() == 35);
        options.iterator().next().getLabel().equals("Abraham");
    }
}
