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
package org.jmesa.view.html.component;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.component.TableImpl;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.renderer.HtmlTableRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlTableImpl extends TableImpl implements HtmlTable {
    private String theme;

    @Override
    public HtmlRow getRow() {
        return (HtmlRow) super.getRow();
    }

    public String getTheme() {
        if (StringUtils.isBlank(theme)) {
            return getCoreContext().getPreference(HtmlConstants.TABLE_COMPONENT_THEME);
        }

        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public HtmlTableImpl theme(String theme) {
    	setTheme(theme);
    	return this;
    }
    
    @Override
    public HtmlTableRenderer getTableRenderer() {
        return (HtmlTableRenderer) super.getTableRenderer();
    }

    public void setStyle(String style) {
    	getTableRenderer().setStyle(style);
    }
    
    public HtmlTableImpl style(String style) {
    	setStyle(style);
    	return this;
    }

    public void setStyleClass(String styleClass) {
    	getTableRenderer().setStyleClass(styleClass);
    }
    
    public HtmlTableImpl styleClass(String styleClass) {
    	setStyleClass(styleClass);
    	return this;
    }

    public void setBorder(String border) {
    	getTableRenderer().setBorder(border);
    }
    
    public HtmlTableImpl border(String border) {
    	setBorder(border);
    	return this;
    }

    public void setCellpadding(String cellpadding) {
    	getTableRenderer().setCellpadding(cellpadding);
    }
    
    public HtmlTableImpl cellpadding(String cellpadding) {
    	setCellpadding(cellpadding);
    	return this;
    }

    public void setCellspacing(String cellspacing) {
    	getTableRenderer().setCellspacing(cellspacing);
    }
    
    public HtmlTableImpl cellspacing(String cellspacing) {
    	setCellspacing(cellspacing);
    	return this;
    }

    public void setWidth(String width) {
    	getTableRenderer().setWidth(width);
    }

    public HtmlTableImpl width(String width) {
    	setWidth(width);
    	return this;
    }
}