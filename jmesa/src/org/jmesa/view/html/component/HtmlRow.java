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
import org.jmesa.view.component.Row;
import org.jmesa.view.html.event.AbstractRowEvent;
import org.jmesa.view.html.event.MouseRowEvent;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.html.renderer.HtmlRowRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlRow extends Row {
    private Boolean filterable;
    private Boolean sortable;
    private boolean highlighter = true;
    private RowEvent onclick;
    private RowEvent onmouseout;
    private RowEvent onmouseover;

    public Boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

	public HtmlRow filterable(Boolean filterable) {
		setFilterable(filterable);
		return this;
	}

    public Boolean isSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

	public HtmlRow sortable(Boolean sortable) {
		setSortable(sortable);
		return this;
	}

    public boolean isHighlighter() {
        return highlighter;
    }

    public void setHighlighter(boolean highlighter) {
        this.highlighter = highlighter;
    }

	public HtmlRow highlighter(boolean highlighter) {
		setHighlighter(highlighter);
		return this;
	}

    public RowEvent getOnclick() {
        return onclick;
    }

    public void setOnclick(RowEvent onclick) {
        this.onclick = onclick;
        SupportUtils.setRow(onclick, this);
        SupportUtils.setCoreContext(onclick, getCoreContext());
        SupportUtils.setWebContext(onclick, getWebContext());
    }

	public HtmlRow onclick(RowEvent onclick) {
		setOnclick(onclick);
		return this;
	}

    /**
     * <p>
     * Added Groovy support in the form of Closures for the onclick RowEvent.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setOnclick(final Closure closure) {
        this.onclick = new AbstractRowEvent() {
            public String execute(Object item, int rowcount) {
                return closure.call(new Object[] { item, rowcount }).toString();
            }
        };
    }

    public RowEvent getOnmouseout() {
        if (onmouseout == null) {
            onmouseout = new MouseRowEvent();
            SupportUtils.setRow(onmouseout, this);
        }

        return onmouseout;
    }

    public void setOnmouseout(RowEvent onmouseout) {
        this.onmouseout = onmouseout;
        SupportUtils.setRow(onmouseout, this);
        SupportUtils.setCoreContext(onmouseout, getCoreContext());
        SupportUtils.setWebContext(onmouseout, getWebContext());
    }

	public HtmlRow onmouseout(RowEvent onmouseout) {
		setOnmouseout(onmouseout);
		return this;
	}

    /**
     * <p>
     * Added Groovy support in the form of Closures for the onmouseout RowEvent.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setOnmouseout(final Closure closure) {
        this.onmouseout = new AbstractRowEvent() {
            public String execute(Object item, int rowcount) {
                return closure.call(new Object[] { item, rowcount }).toString();
            }
        };
    }

    public RowEvent getOnmouseover() {
        if (onmouseover == null) {
            onmouseover = new MouseRowEvent();
            SupportUtils.setRow(onmouseover, this);
        }

        return onmouseover;
    }

    public void setOnmouseover(RowEvent onmouseover) {
        this.onmouseover = onmouseover;
        SupportUtils.setRow(onmouseover, this);
        SupportUtils.setCoreContext(onmouseover, getCoreContext());
        SupportUtils.setWebContext(onmouseover, getWebContext());
    }

	public HtmlRow onmouseover(RowEvent onmouseover) {
		setOnmouseover(onmouseover);
		return this;
	}

    /**
     * <p>
     * Added Groovy support in the form of Closures for the onmouseover RowEvent.
     * </p>
     * 
     * @param closure The Groovy closure to use.
     */
    public void setOnmouseover(final Closure closure) {
        this.onmouseover = new AbstractRowEvent() {
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

    public void setStyle(String style) {
    	getRowRenderer().setStyle(style);
    }
    
    public HtmlRow style(String style) {
    	setStyle(style);
    	return this;
    }

    public void setStyleClass(String styleClass) {
    	getRowRenderer().setStyleClass(styleClass);
    }
    
    public HtmlRow styleClass(String styleClass) {
    	setStyleClass(styleClass);
    	return this;
    }

    public void setHighlightClass(String highlightClass) {
    	getRowRenderer().setHighlightClass(highlightClass);
    }
    
    public HtmlRow highlightClass(String highlightClass) {
    	setHighlightClass(highlightClass);
    	return this;
    }

    public void setHighlightStyle(String highlightStyle) {
    	getRowRenderer().setHighlightStyle(highlightStyle);
    }
    
    public HtmlRow highlightStyle(String highlightStyle) {
    	setHighlightStyle(highlightStyle);
    	return this;
    }

    public void setEvenClass(String evenClass) {
    	getRowRenderer().setEvenClass(evenClass);
    }
    
    public HtmlRow evenClass(String evenClass) {
    	setEvenClass(evenClass);
    	return this;
    }

    public void setOddClass(String oddClass) {
    	getRowRenderer().setOddClass(oddClass);
    }
    
    public HtmlRow oddClass(String oddClass) {
    	setOddClass(oddClass);
    	return this;
    }
}
