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

import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlSnippets;
import org.jmesa.view.html.HtmlSnippetsImpl;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;

/**
 * Use the Flying Saucer API to generate Pdf documents.
 * 
 * @since 2.3
 * @author Paul Horn
 */
public class PdfView implements View {
    private HtmlTable table;
    private HtmlSnippets snippets;
    private WebContext webContext;

    private String css;
    private String style;

    public PdfView(HtmlTable table, Toolbar toolbar, WebContext webContext, CoreContext coreContext) {
        this.table = table;
        this.webContext = webContext;
        this.snippets = new HtmlSnippetsImpl(table, toolbar, coreContext);

        this.css = coreContext.getPreference("pdf.css");
        this.style = coreContext.getPreference("pdf.style");
    }

    public HtmlTable getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = (HtmlTable) table;
    }

    /**
     * @return The stylesheet to use for this pdf.
     */
    public String getCss() {
        return css;
    }

    /**
     * <p>
     * The css to use for this pdf file. Will be relative to the servlet context.
     * </p>
     * 
     * <p>
     * example: /css/jmesa.css
     * <p>
     * 
     * @param css The path and name of the jmesa css file.
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * @return The inline page stylesheet for this pdf.
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style The inline page stylesheet for this pdf.
     */
    public void setStyle(String style) {
        this.style = style;
    }

    public byte[] getBytes() {
        String render = (String) render();
        return render.getBytes();
    }

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();

        String contextPath = webContext.getContextPath();

        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" ");
        html.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");

        html.html().close();

        html.head().close();

        html.link().rel("stylesheet").type("text/css").href(contextPath + css).media("print").end();
        html.style().type("text/css").media("print").close();
        html.append("@page { ").append(style).append(" }");
        html.append(".jmesa .table {width: 100%;border: none;-fs-table-paginate: paginate;}");
        html.styleEnd();

        html.headEnd();

        html.body().close();

        html.append(snippets.themeStart());

        html.append(snippets.tableStart());

        html.append(snippets.theadStart());

        html.append(snippets.header());

        html.append(snippets.theadEnd());

        html.append(snippets.tbodyStart());

        html.append(snippets.body());

        html.append(snippets.tbodyEnd());

        html.append(snippets.footer());

        html.append(snippets.statusBar());

        html.append(snippets.tableEnd());

        html.append(snippets.themeEnd());

        html.bodyEnd();
        html.htmlEnd();

        return html.toString();
    }
}
