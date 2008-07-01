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
package org.jmesa.view.pdf;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jmesa.util.ExportUtils;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRLog;

/**
 * @since 2.2
 * @author Paul Horn
 */
public class PdfViewExporter extends AbstractViewExporter {
    private View view;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String fileName;

    public PdfViewExporter(View view, HttpServletRequest request, HttpServletResponse response) {
        this.view = view;
        this.request = request;
        this.response = response;
        this.fileName = ExportUtils.exportFileName(view, "pdf");
    }

    public PdfViewExporter(View view, String fileName, HttpServletRequest request, HttpServletResponse response) {
        this.view = view;
        this.fileName = fileName;
        this.request = request;
        this.response = response;
    }

    public View getView() {
        return this.view;
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
        response.setContentLength(contents.length);
    }

    public void export() throws Exception {
        byte[] contents = view.getBytes();
        responseHeaders(contents, response);

        System.setProperty("xr.load.xml-reader", "org.ccil.cowan.tagsoup.Parser");
        System.setProperty("xr.util-logging.loggingEnabled", "false");
        System.setProperty("xr.util-logging.java.util.logging.ConsoleHandler.level", "WARN");
        System.setProperty("xr.util-logging..level", "WARN");
        System.setProperty("xr.util-logging.plumbing.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.config.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.exception.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.general.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.init.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.load.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.load.xml-entities.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.match.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.cascade.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.css-parse.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.layout.level", "WARN");
        System.setProperty("xr.util-logging.plumbing.render.level", "WARN");

        XRLog.setLoggingEnabled(false);

        ITextRenderer renderer = new ITextRenderer();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(contents));

        renderer.setDocument(doc, getBaseUrl());
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }
    
    /**
     * @return The base url to the web application.
     */
    private String getBaseUrl() {
        return request.getRequestURL().toString();
    }
}
