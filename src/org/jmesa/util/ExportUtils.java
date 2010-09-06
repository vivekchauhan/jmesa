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
package org.jmesa.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.view.View;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRLog;

/**
 * <p>
 * Utility class to work with the Exports.
 * </p>
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class ExportUtils {
    /**
     * Use the view caption for the export. If the caption is not defined then use a default.
     * 
     * @param view The view to export.
     * @param exportType The type of view to export.
     * @return The file name of export.
     */
    public static String exportFileName(View view, String exportType) {
        String caption = view.getTable().getCaption();
        if (StringUtils.isNotBlank(caption)) {
            StringUtils.replace(caption, " ", "_");
            return caption.toLowerCase() + "." + exportType;
        } 
        
        return "table-data." + exportType;
    }

    /**
     * @deprecated This method has not turned out to be very useful and will be returned in a future release.
     */
    @Deprecated
    public static void exportToFile(TableFacade tableFacade, String filePath) {
        Limit limit = tableFacade.getLimit();
        if (limit.isExported() && limit.getExportType() == ExportType.PDF) {
            exportPdf(tableFacade, filePath);
        }
    }

    /**
     * @deprecated This method has not turned out to be very useful and will be returned in a future release.
     */
    @Deprecated
    private static void exportPdf(TableFacade tableFacade, String filePath) {
        View view = tableFacade.getView();
        byte[] contents = view.getBytes();

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

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(contents));

            HttpServletRequest request = (HttpServletRequest) tableFacade.getWebContext().getBackingObject();
            renderer.setDocument(doc, request.getRequestURL().toString());
            renderer.layout();

            File file = new File(filePath);
            FileOutputStream output = new FileOutputStream(file);
            renderer.createPDF(output);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to generate the PDF");
        }
    }
}
