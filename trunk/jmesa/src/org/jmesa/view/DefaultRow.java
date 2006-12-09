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
package org.jmesa.view;

import java.util.ArrayList;
import java.util.List;


/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DefaultRow implements Row {
	List<Column> columns = new ArrayList<Column>();
	private boolean highlighter;
	private RowRenderer rowRenderer;
	private String onclick;
	private String onmouseout;
	private String onmouseover;

	public void addColumn(Column column) {
		columns.add(column);
	}

	public List<Column> getColumns() {
		return columns;
	}

	public boolean isHighlighter() {
		return highlighter;
	}

	public void setHighlighter(boolean highlighter) {
		this.highlighter = highlighter;
	}
	
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	
	public RowRenderer getRowRenderer() {
		return rowRenderer;
	}

	public void setRowRenderer(RowRenderer rowRenderer) {
		this.rowRenderer = rowRenderer;
	}
}
