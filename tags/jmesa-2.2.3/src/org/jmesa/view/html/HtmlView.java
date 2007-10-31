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
package org.jmesa.view.html;

import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlView implements View {
    private HtmlTable table;
    private HtmlSnippets snippets;

    public HtmlView(HtmlTable table, Toolbar toolbar, CoreContext coreContext) {
        this.table = table;
        this.snippets = new HtmlSnippetsImpl(table, toolbar, coreContext);
    }

    public HtmlView(HtmlTable table, HtmlSnippets snippets) {
        this.table = table;
        this.snippets = snippets;
    }

    public HtmlTable getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = (HtmlTable) table;
    }

    public byte[] getBytes() {
        String render = (String) render();
        return render.getBytes();
    }

    public Object render() {
        HtmlBuilder html = new HtmlBuilder();

        html.append(snippets.themeStart());

        html.append(snippets.tableStart());

        html.append(snippets.theadStart());

        html.append(snippets.toolbar());

        html.append(snippets.filter());

        html.append(snippets.header());

        html.append(snippets.theadEnd());

        html.append(snippets.tbodyStart());

        html.append(snippets.body());

        html.append(snippets.tbodyEnd());

        html.append(snippets.footer());

        html.append(snippets.statusBar());

        html.append(snippets.tableEnd());

        html.append(snippets.themeEnd());

        html.append(snippets.initJavascriptLimit());

        return html.toString();
    }
}
