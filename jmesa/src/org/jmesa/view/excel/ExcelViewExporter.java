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
package org.jmesa.view.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jmesa.view.AbstractViewExporter;

/**
 * @since 2.1
 * @author jeff jie
 */
public class ExcelViewExporter extends AbstractViewExporter {
		
    public void export()
            throws Exception {

        HSSFWorkbook workbook = (HSSFWorkbook) this.getView().render();
        responseHeaders();
        workbook.write(getHttpServletResponse().getOutputStream());
    }

    protected String getContextType() {
		
        return "application/vnd.ms-excel;charset=UTF-8";
    }

    protected String getExtensionName() {

        return "xls";
    }
}
