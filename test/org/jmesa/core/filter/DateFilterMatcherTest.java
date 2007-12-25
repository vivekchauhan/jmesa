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
package org.jmesa.core.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmesa.test.AbstractTestCase;
import org.jmesa.web.WebContext;
import org.junit.Test;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DateFilterMatcherTest extends AbstractTestCase {
    @Test
    public void evaluateTest() {
        WebContext webContext = createWebContext();
        DateFilterMatcher matcher = new DateFilterMatcher("MM/dd/yyyy");
        matcher.setWebContext(webContext);
        
        boolean evaluate = matcher.evaluate(null, "07/");
        assertFalse(evaluate);

        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", webContext.getLocale());
            date = simpleDateFormat.parse("07/04/2007");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        evaluate = matcher.evaluate(date, null);
        assertFalse(evaluate);

        evaluate = matcher.evaluate(date, "/04/");
        assertTrue(evaluate);
    }
}
