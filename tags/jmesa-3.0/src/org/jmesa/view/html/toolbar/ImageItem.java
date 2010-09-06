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

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.html.HtmlBuilder;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ImageItem extends AbstractItem {
    String image;
    String disabledImage;
    String alt;

    public String getDisabledImage() {
        return disabledImage;
    }

    public void setDisabledImage(String disabledImage) {
        this.disabledImage = disabledImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    public String disabled() {
        HtmlBuilder html = new HtmlBuilder();
        html.img().src(getDisabledImage()).styleClass(getStyleClass()).style(getStyle()).alt(getAlt()).end();
        return html.toString();
    }

    @Override
    public String enabled() {
        HtmlBuilder html = new HtmlBuilder();
        html.a().href();
        html.quote();
        html.append(getAction());
        html.quote().close();

        if (StringUtils.isNotBlank(getTooltip())) {
            html.img().src(getImage()).styleClass(getStyleClass()).style(getStyle()).title(getTooltip())
                    .onmouseover(getOnmouseover()).onmouseout(getOnmouseout()).alt(getAlt()).end();
        } else {
            html.img().src(getImage()).styleClass(getStyleClass()).style(getStyle())
                    .onmouseover(getOnmouseover()).onmouseout(getOnmouseout()).alt(getAlt()).end();
        }

        html.aEnd();

        return html.toString();
    }
}
