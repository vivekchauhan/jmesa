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

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The date filter matcher.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class DateFilterMatcher extends AbstractPatternFilterMatcher {
    private Logger logger = LoggerFactory.getLogger(DateFilterMatcher.class);

    public DateFilterMatcher() {
        // default constructor
    }

    /**
     * @param pattern The pattern to use.
     */
    public DateFilterMatcher(String pattern) {
        setPattern(pattern);
    }

    public DateFilterMatcher(String pattern, WebContext webContext) {
        setPattern(pattern);
        setWebContext(webContext);
    }

    public boolean evaluate(Object itemValue, String filterValue) {
        if (itemValue == null) {
            return false;
        }

        String pattern = getPattern();
        if (pattern == null) {
            logger.debug("The filter (value " + filterValue + ") is trying to match against a date column using " +
                    "the DateFilterMatcher, but there is no pattern defined. You need to register a DateFilterMatcher " +
                    "to be able to filter against this column.");
            return false;
        }

        Locale locale = null;

        WebContext webContext = getWebContext();
        if (webContext != null) {
            locale = webContext.getLocale();
        }

        if (locale != null) {
            itemValue = DateFormatUtils.format((Date) itemValue, pattern, locale);
        } else {
            itemValue = DateFormatUtils.format((Date) itemValue, pattern);
        }

        String item = String.valueOf(itemValue);
        String filter = String.valueOf(filterValue);
        if (StringUtils.contains(item, filter)) {
            return true;
        }

        return false;
    }
}
