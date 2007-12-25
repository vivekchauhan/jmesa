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
import org.jmesa.view.editor.PatternSupport;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

/**
 * The number filter matcher.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class NumberFilterMatcher implements FilterMatcher, PatternSupport, WebContextSupport {
    private String pattern;
    private WebContext webContext;

    public NumberFilterMatcher() {
    	// default constructor
    }

    /**
     * @param pattern The pattern to use.
     */
    public NumberFilterMatcher(String pattern) {
        this.pattern = pattern;
    }

    public NumberFilterMatcher(String pattern, WebContext webContext) {
        this.pattern = pattern;
        this.webContext = webContext;
    }

    public WebContext getWebContext() {
        return webContext;
    }

    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean evaluate(Object itemValue, String filterValue) {
        if (itemValue == null) {
            return false;
        }

        Locale locale = null;
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
