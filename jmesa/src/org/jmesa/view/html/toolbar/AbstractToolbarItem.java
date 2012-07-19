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

import org.jmesa.core.CoreContext;
import static org.jmesa.view.html.HtmlConstants.*;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractToolbarItem implements ToolbarItem {
		
    private final CoreContext coreContext;

    private String code;
    private String onmouseover;
    private String onmouseout;
    private String styleClass;
    private String style;
    private String tooltip;
    private String onInvokeAction;
    private String onInvokeExportAction;

    public AbstractToolbarItem(CoreContext coreContext) {
     
        this.coreContext = coreContext;
    }

    public String getCode() {
		
        return code;
    }

    public void setCode(String code) {
		
        this.code = code;
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

    public CoreContext getCoreContext() {
        
        return coreContext;
    }

    public String getOnInvokeAction() {
        
        if (onInvokeAction == null) {
            onInvokeAction = coreContext.getPreference(ON_INVOKE_ACTION);
        }
		
        return onInvokeAction;
    }

    public void setOnInvokeAction(String onInvokeAction) {
		
        this.onInvokeAction = onInvokeAction;
    }

    public String getOnInvokeActionJavaScript() {
        
        return getOnInvokeAction() + "('" + coreContext.getLimit().getId() + "','" + getCode() + "')";
    }
    
    public String getOnInvokeExportAction() {
		
        if (onInvokeExportAction == null) {
            onInvokeExportAction = coreContext.getPreference(ON_INVOKE_EXPORT_ACTION);
        }
		
        return onInvokeExportAction;
    }

    public void setOnInvokeExportAction(String onInvokeExportAction) {
		
        this.onInvokeExportAction = onInvokeExportAction;
    }

    public String getOnInvokeExportActionJavaScript() {
        
        return getOnInvokeExportAction() + "('" + coreContext.getLimit().getId() + "','" + getCode() + "')";
    }
    
}
