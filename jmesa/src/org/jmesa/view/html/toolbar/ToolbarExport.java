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
public class ToolbarExport {
		
    private String exportType;
    private String text;
    private String tooltip;
    private String image;
    private String action;

    public ToolbarExport(String exportType) {
		
        this.exportType = exportType;
    }

    public ToolbarExport(String exportType, String image) {
		
        this.exportType = exportType;
        this.image = image;
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

    public String getExportType() {
		
        return exportType;
    }

    public void setText(String text) {
		
        this.text = text;
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

    public void setImage(String imageName) {
		
        this.image = imageName;
    }

    public String getAction() {
		
        return action;
    }

    public void setAction(String action) {
		
        this.action = action;
    }
}
