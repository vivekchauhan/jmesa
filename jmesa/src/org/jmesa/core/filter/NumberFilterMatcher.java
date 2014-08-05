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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.WebContext;

/**
 * The number filter matcher.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class NumberFilterMatcher extends AbstractPatternFilterMatcher {
		
    public NumberFilterMatcher() {
		
    	// default constructor
    }

    /**
     * @param pattern The pattern to use.
     */
    public NumberFilterMatcher(String pattern) {
		
        setPattern(pattern);
    }

    public NumberFilterMatcher(String pattern, WebContext webContext) {
		
        setPattern(pattern);
        setWebContext(webContext);
    }

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {
		
        if (itemValue == null) {
            return false;
        }

        Locale locale = null;

        WebContext webContext = getWebContext();
        if (webContext != null) {
            locale = webContext.getLocale();
        }

        NumberFormat nf;
        if (locale != null) {
            nf = NumberFormat.getInstance(locale);
        } else {
            nf = NumberFormat.getInstance();
        }

        DecimalFormat df = (DecimalFormat) nf;
        String pattern = getPattern();
        df.applyPattern(pattern);
        itemValue = df.format(itemValue);

        String item = String.valueOf(itemValue);
        String filter = String.valueOf(filterValue);
        if (StringUtils.contains(item, filter)) {
            return true;
        }

        return false;
    }
}
