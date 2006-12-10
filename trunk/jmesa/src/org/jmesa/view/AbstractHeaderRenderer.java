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

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.CoreContext;


public abstract class AbstractHeaderRenderer implements HeaderRenderer {
	private Column column;
	private CoreContext coreContext;

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
	
	public CoreContext getCoreContext() {
		return coreContext;
	}

	public void setCoreContext(CoreContext coreContext) {
		this.coreContext = coreContext;
	}
	
	protected String getTitle() {
		String title = column.getTitle();
		if (StringUtils.isBlank(title)) {
			return ViewUtils.camelCaseToWord(column.getProperty());	
		} else {
			
		}
		
		return null;
	}
}
