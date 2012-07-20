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

import org.jmesa.util.SupportUtils;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.component.TableSupport;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarSupport;

/**
 * Abstract view for building html tables.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public abstract class AbstractHtmlView extends AbstractContextSupport implements View, ToolbarSupport, TableSupport {
		
    private HtmlTable table;
    private Toolbar toolbar;
    private HtmlSnippets snippets;

    public HtmlTable getTable() {
		
        return table;
    }

    public void setTable(Table table) {
		
        this.table = (HtmlTable) table;
    }

    public Toolbar getToolbar() {
		
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
		
        this.toolbar = toolbar;
    }

    public byte[] getBytes() {
		
        String render = (String) render();
        return render.getBytes();
    }

    /**
     * Return the HtmlSnippets object. If the snippets is null then the default one will be created.
     * 
     * @return The HtmlSnippets object.
     */
    public HtmlSnippets getHtmlSnippets() {
		
        if (snippets == null) {
            this.snippets = new HtmlSnippets(getTable(), getToolbar(), getCoreContext());
        }
        SupportUtils.setWebContext(this.snippets, getWebContext());
        return snippets;
    }

    public void setHtmlSnippets(HtmlSnippets snippets) {
		
        this.snippets = snippets;
    }
}
