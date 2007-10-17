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

/**
 * @since 2.3
 * @author Paul Horn
 */
public class PdfView implements View {
    private HtmlTable table;
    private HtmlSnippets snippets;

    private String baseURL;
    private String jmesaCssURL;
    private String pageStyles;

    public PdfView(HtmlTable table, Toolbar toolbar, CoreContext coreContext) {
        this.table = table;
        this.snippets = new HtmlSnippetsImpl(table, toolbar, coreContext);

        this.baseURL = coreContext.getPreference("pdf.baseURL");
        this.jmesaCssURL = coreContext.getPreference("pdf.jmesaCssURL");
        this.pageStyles = coreContext.getPreference("pdf.pageStyles");
    }

    public HtmlTable getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = (HtmlTable) table;
    }

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();

        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" ");
        html.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html>");
        html.append("<head>");
        html.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(jmesaCssURL).append("\" media=\"print\"/>");
        html.append("<style type=\"text/css\" media=\"print\">");
        html.append("@page { ").append(pageStyles).append(" }");
        html.append(".jmesa .table {width: 100%;border: none;-fs-table-paginate: paginate;}");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

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

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    public String getBaseURL() {
        return this.baseURL;
    }

}
