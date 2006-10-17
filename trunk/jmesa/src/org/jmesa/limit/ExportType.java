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
package org.jmesa.limit;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public enum ExportType {
	HTML, 
	PDF, 
	XLS, 
	XML;
	
	public String getCode() {
		switch(this) {
		case HTML: return "html";
		case PDF: return "pdf";
		case XLS: return "xls";
		case XML: return "xml";
		default: return "";
		}
	}
	
	public ExportType getExportType(String code) {
		for(ExportType exportType: ExportType.values()) {
			if (exportType.getCode().equals(code)) {
				return exportType;
			}
		}
		
		return null;
	}	
}
