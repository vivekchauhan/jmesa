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
package org.jmesa.view.pdfp;

import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.util.ExportUtils;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;

/**
 * A PDF view that uses the iText PdfPTable.
 *
 * @since 2.3.4
 * @author acimasiz
 */
public class PdfPViewExporter extends AbstractViewExporter {
    private View view;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String fileName;

    public PdfPViewExporter(View view, HttpServletRequest request, HttpServletResponse response) {
        this.view = view;
        this.request = request;
        this.response = response;
        this.fileName = ExportUtils.exportFileName(view, "pdf");
    }

    public PdfPViewExporter(View view, String fileName, HttpServletRequest request, HttpServletResponse response) {
        this.view = view;
        this.fileName = fileName;
        this.request = request;
        this.response = response;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void responseHeaders(byte[] contents, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
    }

    public void export() throws Exception {

        com.lowagie.text.Document document = new com.lowagie.text.Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(((PdfPView) view).getTableCaption());
        document.add(((PdfPView) view).render());
        document.close();

        this.responseHeaders(baos.toByteArray(), response);
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
}
