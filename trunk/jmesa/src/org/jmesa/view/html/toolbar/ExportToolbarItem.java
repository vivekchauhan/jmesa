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
import org.jmesa.limit.Limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ExportToolbarItem extends AbstractImageToolbarItem {
		
    private String exportType;
    private String image;
    private String text;
    private String tooltip;
    private String action;

    public ExportToolbarItem(CoreContext coreContext) {
		
        super(coreContext);
    }

    /**
     * If used for an image then this can be used for the alt option. Otherwise
     * it is used as straight text to display.
     * 
     * @return The text to display.
     */
    public String getText() {
		
        return text;
    }

    public void setText(String text) {
		
        this.text = text;
    }

    public String getExportType() {
		
        return exportType;
    }

    public void setExportType(String exportType) {

        this.exportType = exportType;
    }

    public String getTooltip() {
		
        return tooltip;
    }

    public void setTooltip(String tooltip) {
		
        this.tooltip = tooltip;
    }

    public String getImage() {
		
        return image;
    }

    public String getAction() {
		
        return action;
    }

    public void setAction(String action) {
		
        this.action = action;
    }

    public String render() {
		
        Limit limit = getCoreContext().getLimit();
        StringBuilder action = new StringBuilder("javascript:jQuery.jmesa.setExport('" + limit.getId() + "','" + getExportType() + "');"
                + getOnInvokeActionJavaScript());
        return enabled(action.toString());
    }
}
