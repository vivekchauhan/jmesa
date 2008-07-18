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
 * @since 2.3.2
 * @author Jeff Johnston
 */
public class PageNumberItem extends AbstractItem {

    private int page;

    public PageNumberItem(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    @Override
    public String disabled() {
        HtmlBuilder html = new HtmlBuilder();

        html.span();
        html.styleClass(getStyleClass());
        html.style(getStyle());
        html.close();
        html.append(String.valueOf(page));
        html.spanEnd();

        return html.toString();
    }

    @Override
    public String enabled() {
        HtmlBuilder html = new HtmlBuilder();

        html.span();
        html.styleClass(getStyleClass());
        html.style(getStyle());
        html.close();

        html.a().href();
        html.quote();
        html.append(getAction());
        html.quote().close();
        html.append(String.valueOf(page));
        html.aEnd();

        html.spanEnd();

        return html.toString();
    }
}
