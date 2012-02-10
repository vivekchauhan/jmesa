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
public abstract class AbstractItem implements ToolbarItem {
		
    private String code;
    private String action;
    private String onmouseover;
    private String onmouseout;
    private String styleClass;
    private String style;
    private String tooltip;
    private ToolbarItemRenderer renderer;

    public String getCode() {
		
        return code;
    }

    public void setCode(String code) {
		
        this.code = code;
    }

    public String getAction() {
		
        return action;
    }

    public void setAction(String action) {
		
        this.action = action;
    }

    public String getTooltip() {
		
        return tooltip;
    }

    public void setTooltip(String tooltip) {
		
        this.tooltip = tooltip;
    }

    public String getOnmouseout() {
		
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
		
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
		
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
		
        this.onmouseover = onmouseover;
    }

    public String getStyle() {
		
        return style;
    }

    public void setStyle(String style) {
		
        this.style = style;
    }

    public String getStyleClass() {
		
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
		
        this.styleClass = styleClass;
    }

    public ToolbarItemRenderer getToolbarItemRenderer() {
		
        return renderer;
    }

    public void setToolbarItemRenderer(ToolbarItemRenderer renderer) {
		
        this.renderer = renderer;
    }

    public abstract String disabled();

    public abstract String enabled();
}
