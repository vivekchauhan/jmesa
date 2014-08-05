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

import org.jmesa.view.html.HtmlBuilder;

/**
 * To be able to work with the Toolbar the separator image needs to actually be
 * a ToolbarItem.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class SeparatorToolbarItem implements ToolbarItem {

    private String image;
    private String style;
    private String alt;

    public String getAlt() {
        
        return alt;
    }

    public void setAlt(String alt) {
        
        this.alt = alt;
    }

    public String getImage() {
        
        return image;
    }

    public void setImage(String image) {
     
        this.image = image;
    }

    public String getStyle() {
        
        return style;
    }

    public void setStyle(String style) {
     
        this.style = style;
    }
    
    @Override
    public String render() {

        HtmlBuilder html = new HtmlBuilder();
        html.img();
        html.src(getImage());
        html.style(getStyle());
        html.alt(getAlt());
        html.end();
        return html.toString();
    }
}
