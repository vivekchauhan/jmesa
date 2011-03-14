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
package org.jmesa.limit.state;

import org.jmesa.core.IdSupport;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

/**
 * @since 2.5.1
 * @author Jeff Johnston
 */
public abstract class AbstractState implements State, WebContextSupport, IdSupport, StateAttrSupport {
    private String id;
    private String stateAttr;
    private WebContext webContext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateAttr() {
        return stateAttr;
    }

    public void setStateAttr(String stateAttr) {
        this.stateAttr = stateAttr;
    }

    public WebContext getWebContext() {
        return webContext;
    }

    public void setWebContext(WebContext webContext) {
        this.webContext = webContext;
    }
}
