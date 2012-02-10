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
package org.jmesa.view.editor.expression;

import static org.junit.Assert.assertEquals;
import java.awt.Dimension;
import org.jmesa.core.CoreContext;
import org.jmesa.test.AbstractTestCase;
import org.junit.Test;

/**
 * @version 2.4
 * @author bgould
 */
public class BsfExpressionCellEditorTest extends AbstractTestCase {

    @Test
    public void testSetItemVariableName() {
		
        CoreContext ctx = createCoreContext(createWebContext());

        // First just test a basic expression
        BsfExpressionCellEditor editor = new BsfExpressionCellEditor(new Expression(Language.JAVASCRIPT, "number", "number.height + number.width"));
        editor.setCoreContext(ctx);

        Object result = editor.getValue(new Dimension(100, 50), "test", 0);
        assertEquals(Double.class, result.getClass());
        assertEquals(new Double(150), result);
    }

    @Test
    public void testGetValue() {
		
        CoreContext ctx = createCoreContext(createWebContext());

        // First just test a basic expression
        BsfExpressionCellEditor editor = new BsfExpressionCellEditor(new Expression(Language.JAVASCRIPT, "item", "item.height + item.width"));
        editor.setCoreContext(ctx);

        Object result = editor.getValue(new Dimension(100, 50), "test", 0);
        assertEquals(Double.class, result.getClass());
        assertEquals(new Double(150), result);

        result = editor.getValue(new Dimension(200, 200), "test", 1);
        assertEquals(new Double(400), result);

        // Now test just to make sure we can re-use the BSFManager
        editor = new BsfExpressionCellEditor(Language.JAVASCRIPT, "item", "item.toLowerCase()");
        editor.setCoreContext(ctx);
        result = editor.getValue("JMesa", "test", 0);
        assertEquals("jmesa", result);
    }
}
