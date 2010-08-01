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

import org.jmesa.view.editor.*;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

/**
 * Abstract class to hold the pattern information for filter matcher classes.
 * 
 * @since 2.4.4
 * @author Jeff Johnston
 */
public abstract class AbstractPatternFilterMatcher implements FilterMatcher, PatternSupport, WebContextSupport {
    private String pattern;
    private WebContext webContext;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public WebContext getWebContext() {
        return webContext;
    }

    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
    }
}
