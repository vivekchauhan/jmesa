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
import org.jmesa.view.ContextSupport;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DateFilterMatch extends ContextSupport implements FilterMatch {
    private String pattern = "MM/dd/yyyy";

    public DateFilterMatch() {
    }

    public DateFilterMatch(String pattern) {
        this.pattern = pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean evaluate(Object itemValue, String matchValue) {
        if (itemValue == null) {
            return false;
        }

        Locale locale = getWebContext().getLocale();
        itemValue = DateFormatUtils.format((Date) itemValue, pattern, locale);

        String item = String.valueOf(itemValue);
        String match = String.valueOf(matchValue);
        if (StringUtils.contains(item, match)) {
            return true;
        }

        return false;
    }
}
