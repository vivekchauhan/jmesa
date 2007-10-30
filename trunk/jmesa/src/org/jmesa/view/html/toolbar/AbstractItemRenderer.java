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
package org.jmesa.view.html.toolbar;

import org.jmesa.limit.Limit;
import org.jmesa.view.AbstractContextSupport;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractItemRenderer extends AbstractContextSupport implements ToolbarItemRenderer {
    private String onInvokeAction;
    private ToolbarItem toolbarItem;

    public String getOnInvokeAction() {
        return onInvokeAction;
    }

    public void setOnInvokeAction(String onInvokeAction) {
        this.onInvokeAction = onInvokeAction;
    }

    public String getOnInvokeActionJavaScript(Limit limit, ToolbarItem toolbarItem) {
        return getOnInvokeAction() + "('" + limit.getId() + "','" + toolbarItem.getCode() + "')";
    }

    public ToolbarItem getToolbarItem() {
        return toolbarItem;
    }

    public void setToolbarItem(ToolbarItem toolbarItem) {
        this.toolbarItem = toolbarItem;
    }
}
