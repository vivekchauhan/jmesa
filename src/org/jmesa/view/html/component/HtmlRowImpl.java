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

import groovy.lang.Closure;

import org.jmesa.util.SupportUtils;
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
        SupportUtils.setCoreContext(onclick, getCoreContext());
        SupportUtils.setWebContext(onclick, getWebContext());
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the onclick RowEvent.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setOnclick(final Closure closure) {
        this.onclick = new RowEvent() {
            public String execute(Object item, int rowcount) {
                return closure.call(new Object[] { item, rowcount }).toString();
            }
        };
    }

    public RowEvent getOnmouseout() {
        if (onmouseout == null) {
            onmouseout = new MouseRowEvent(isHighlighter());
        }

        return onmouseout;
    }

    public void setOnmouseout(RowEvent onmouseout) {
        this.onmouseout = onmouseout;
        SupportUtils.setCoreContext(onmouseout, getCoreContext());
        SupportUtils.setWebContext(onmouseout, getWebContext());
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the onmouseout RowEvent.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setOnmouseout(final Closure closure) {
        this.onmouseout = new RowEvent() {
            public String execute(Object item, int rowcount) {
                return closure.call(new Object[] { item, rowcount }).toString();
            }
        };
    }

    public RowEvent getOnmouseover() {
        if (onmouseover == null) {
            onmouseover = new MouseRowEvent(isHighlighter());
        }

        return onmouseover;
    }

    public void setOnmouseover(RowEvent onmouseover) {
        this.onmouseover = onmouseover;
        SupportUtils.setCoreContext(onmouseover, getCoreContext());
        SupportUtils.setWebContext(onmouseover, getWebContext());
    }

    /**
     * <p>
     * Added Groovy support in the form of Closures for the onmouseover RowEvent.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setOnmouseover(final Closure closure) {
        this.onmouseover = new RowEvent() {
            public String execute(Object item, int rowcount) {
                return closure.call(new Object[] { item, rowcount }).toString();
            }
        };
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
