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
import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.CoreContext;
import org.jmesa.core.President;
import org.jmesa.core.PresidentDao;
import org.jmesa.test.AbstractTestCase;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor.Option;
import org.jmesa.web.WebContext;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
@Ignore
public class DroplistFilterEditorTest extends AbstractTestCase {

    @Test
    public void getOptions() {
		
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        DroplistFilterEditor editor = new DroplistFilterEditor();
        editor.setCoreContext(coreContext);
        editor.setWebContext(webContext);

        Column column = new HtmlColumn("name.firstName");

        editor.setColumn(column);

        Collection<Option> options = editor.getOptions();

        assertNotNull("The options are null.", options);
        assertTrue("Do not have the correct options size.", options.size() == 35);
        assertTrue(options.iterator().next().getLabel().equals("Abraham"));
    }
    
    @Test
    public void getPresetOption() {
		
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        DroplistFilterEditor editor = new DroplistFilterEditor();
        editor.setCoreContext(coreContext);
        editor.setWebContext(webContext);

        Column column = new HtmlColumn("name.firstName");

        editor.setColumn(column);

        Map<String, String> testBean = new HashMap<String, String>();
        testBean.put("name.firstName", "Abraham");
        editor.addOption(testBean, "name.firstName", "name.firstName");
        
        Collection<Option> options = editor.getOptions();

        assertNotNull("The options are null.", options);
        assertTrue("Do not have the correct options size.", options.size() == 1);
        assertTrue(options.iterator().next().getLabel().equals("Abraham"));
    }
    
    @Test
    public void getPresetOptions() {
		
        WebContext webContext = createWebContext();
        CoreContext coreContext = createCoreContext(webContext);

        DroplistFilterEditor editor = new DroplistFilterEditor();
        editor.setCoreContext(coreContext);
        editor.setWebContext(webContext);

        Column column = new HtmlColumn("name.firstName");

        editor.setColumn(column);

        Collection<President> presidents = PresidentDao.getPresidents();
        editor.addOptions(presidents, "name.firstName", "name.firstName");
        
        Collection<Option> options = editor.getOptions();
        
        assertNotNull("The options are null.", options);
        assertTrue("Do not have the correct options size: " + options.size(), options.size() == 35);
        
        Option option = options.iterator().next();
        assertTrue(option.getLabel().equals("Abraham"));
    }
}
