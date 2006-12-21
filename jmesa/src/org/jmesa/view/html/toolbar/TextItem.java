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
import org.jmesa.view.html.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class TextItem extends AbstractItem implements ToolbarItem {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void disabled(HtmlBuilder html) {
        html.span().close().append(getText()).spanEnd();
    }

    public void enabled(HtmlBuilder html, CoreContext model) {
        html.a();
        html.quote();
        html.append(getAction());
        html.quote().close();

//        boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
//        if (showTooltips) {
            html.span().title(getTooltip());
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(getText());
            html.spanEnd();
//        } else {
//            html.span();
//            html.styleClass(getStyleClass()).style(getStyle());
//            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
//            html.close();
//            html.append(getText());
//            html.spanEnd();
//        }

        html.aEnd();
    }
}
