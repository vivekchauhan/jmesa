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
package org.jmesa.view.jexcel;

import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;

/**
 * <p>
 *  Output the JExcel results out to the response.
 * </p>
 *
 * @since 2.2
 * @author Paul Horn
 */
public class JExcelViewExporter extends AbstractViewExporter {
    public JExcelViewExporter(View view, CoreContext coreContext, HttpServletResponse response) {
        super(view, coreContext, response);
    }

    public JExcelViewExporter(View view, CoreContext coreContext, HttpServletResponse response, String fileName) {
        super(view, coreContext, response, fileName);
    }

    public void export()
            throws Exception {
        HttpServletResponse response = getResponse();
        JExcelView view = (JExcelView) getView();
        view.setOutputStream(response.getOutputStream());
        WritableWorkbook workbook = (WritableWorkbook) view.render();
        responseHeaders(response);
        workbook.write();
        response.getOutputStream().flush();
        workbook.close();
    }

    public String getContextType() {
        return "application/vnd.ms-excel;charset=UTF-8";
    }

    public String getExtensionName() {
        return "xls";
    }
}
