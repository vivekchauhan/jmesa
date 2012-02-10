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
 * @since 3.0
 * @author Siddhant Agrawal
 */
public class TextItem extends AbstractItem {
		
    String text;
    String alt;

    public String getText() {
		
        return text;
    }

    public void setText(String text) {
		
        this.text = text;
    }

    public String getAlt() {
		
        return alt;
    }

    public void setAlt(String alt) {
		
        this.alt = alt;
    }

    @Override
    public String disabled() {
		
        return "";
    }

    @Override
    public String enabled() {
		
        HtmlBuilder html = new HtmlBuilder();

        String action = getAction();

        if (action != null) {
            html.span().styleClass(getStyleClass()).style(getStyle()).title(getTooltip()).alt(getAlt()).close();
            html.a().href();
            html.quote();
            html.append(getAction());
            html.quote().close();
            html.append(String.valueOf(getText()));
            html.aEnd();
            html.spanEnd();
        } else {
            html.span().styleClass(getStyleClass()).style(getStyle()).title(getTooltip()).alt(getAlt()).close();
            html.append(String.valueOf(getText()));
            html.spanEnd();
        }

        return html.toString();
    }
}
