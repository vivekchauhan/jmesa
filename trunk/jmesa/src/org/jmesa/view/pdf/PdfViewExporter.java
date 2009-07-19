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

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRLog;
import org.xhtmlrenderer.resource.FSEntityResolver;

/**
 * @since 2.2
 * @author Paul Horn
 */
public class PdfViewExporter extends AbstractViewExporter {

    private HttpServletRequest request;

    public PdfViewExporter(View view, CoreContext coreContext, HttpServletRequest request, HttpServletResponse response) {
        super(view, coreContext, response);
        this.request = request;
    }

    public PdfViewExporter(View view, CoreContext coreContext, HttpServletRequest request, HttpServletResponse response, String fileName) {
        super(view, coreContext, response, fileName);
        this.request = request;
    }

    public void export() throws Exception {
        byte[] contents = getView().getBytes();
        HttpServletResponse response = getResponse();
        responseHeaders(response);

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
        FSEntityResolver er= FSEntityResolver.instance();
        builder.setEntityResolver(er);
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

    @Override
    public String getContextType() {
        return "application/pdf";
    }

    @Override
    public String getExtensionName() {
        return "pdf";
    }
}
