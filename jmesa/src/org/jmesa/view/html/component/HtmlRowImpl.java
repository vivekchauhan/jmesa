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

import org.jmesa.view.component.RowImpl;
import org.jmesa.view.html.event.MouseRowEvent;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.html.renderer.HtmlRowRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlRowImpl extends RowImpl implements HtmlRow {
    private boolean highlighter = true;
    private RowEvent onclick;
    private RowEvent onmouseout;
    private RowEvent onmouseover;

    public boolean isHighlighter() {
        return highlighter;
    }

    public void setHighlighter(boolean highlighter) {
        this.highlighter = highlighter;
    }

    public RowEvent getOnclick() {
        return onclick;
    }

    public void setOnclick(RowEvent onclick) {
        this.onclick = onclick;
    }

    public RowEvent getOnmouseout() {
        if (onmouseout == null) {
            onmouseout = new MouseRowEvent(isHighlighter());
        }
        
        return onmouseout;
    }

    public void setOnmouseout(RowEvent onmouseout) {
        this.onmouseout = onmouseout;
    }

    public RowEvent getOnmouseover() {
        if (onmouseover == null) {
            onmouseover = new MouseRowEvent(isHighlighter());
        }
        
        return onmouseover;
    }

    public void setOnmouseover(RowEvent onmouseover) {
        this.onmouseover = onmouseover;
    }

    @Override
    public HtmlColumn getColumn(String property) {
        return (HtmlColumn) super.getColumn(property);
    }

    @Override
    public HtmlColumn getColumn(int index) {
        return (HtmlColumn) super.getColumn(index);
    }

    @Override
    public HtmlRowRenderer getRowRenderer() {
        return (HtmlRowRenderer) super.getRowRenderer();
    }
}
