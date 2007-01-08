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

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface ToolbarItemFactory {
	public ImageItemImpl createFirstPageItemAsImage();

	public TextItemImpl createFirstPageItemAsText();

	public ImageItemImpl createPrevPageItemAsImage();

	public TextItemImpl createPrevPageItemAsText();

	public ImageItemImpl createNextPageItemAsImage();

	public TextItemImpl createNextPageItemAsText();

	public ImageItemImpl createLastPageItemAsImage();

	public TextItemImpl createLastPageItemAsText();

	public ImageItemImpl createFilterItemAsImage();

	public TextItemImpl createFilterItemAsText();

	public ImageItemImpl createClearItemAsImage();

	public TextItemImpl createClearItemAsText();

	public ImageItemImpl createExportItemAsImage(ToolbarExport export);

	public TextItemImpl createExportItemAsText(ToolbarExport export);
	
	public String createSeparatorImage();
	
	public String createFilterArrowImage();
	
	public MaxRowsItemImpl createMaxRowsItem();
}