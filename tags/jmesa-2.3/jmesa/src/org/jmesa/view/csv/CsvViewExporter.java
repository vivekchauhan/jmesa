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
package org.jmesa.view.csv;

import javax.servlet.http.HttpServletResponse;

import org.jmesa.util.ExportUtils;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvViewExporter extends AbstractViewExporter {
    View view;
    HttpServletResponse response;
    String fileName;

    public CsvViewExporter(View view, HttpServletResponse response) {
        this.view = view;
        this.response = response;
        this.fileName = ExportUtils.exportFileName(view, "txt");
    }

    public CsvViewExporter(View view, String fileName, HttpServletResponse response) {
        this.view = view;
        this.fileName = fileName;
        this.response = response;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void export()
            throws Exception {
        String viewData = (String) view.render();
        byte[] contents = (viewData).getBytes();
        responseHeaders(contents, response);
    }

    @Override
    public void responseHeaders(byte[] contents, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
        response.setContentLength(contents.length);
        response.getOutputStream().write(contents);
    }
}
