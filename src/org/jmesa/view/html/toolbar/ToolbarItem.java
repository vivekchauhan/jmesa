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

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface ToolbarItem {
    /**
     * @since 2.2
     * @return The name that identifies this toolbar item. Will be passed to the javascript
     *         functions.
     */
    public String getCode();

    public String getAction();

    public void setAction(String action);

    public String getTooltip();

    public void setTooltip(String tooltip);

    public String getOnmouseout();

    public void setOnmouseout(String onmouseout);

    public String getOnmouseover();

    public void setOnmouseover(String onmouseover);

    public String getStyle();

    public void setStyle(String style);

    public String getStyleClass();

    public void setStyleClass(String styleClass);

    public String disabled();

    public String enabled();

    public ToolbarItemRenderer getToolbarItemRenderer();

    public void setToolbarItemRenderer(ToolbarItemRenderer renderer);
}
