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
package org.jmesaweb.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jmesa.limit.Limit;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.AbstractFilterEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesaweb.domain.President;

/**
 * @author Jeff Johnston
 */
public class CareerFilterEditor extends AbstractFilterEditor {

    public Object getValue() {

        Collection options = getOptions();

        Limit limit = getCoreContext().getLimit();
        Column column = getColumn();

        
        String selected = getWebContext().getParameter("career");
        if (!limit.getFilterSet().isFiltered()) {
            selected = null;
        }

        // build the select box
        
        HtmlBuilder html = new HtmlBuilder();

        html.select().name("career").onchange(
                "addFilterToLimit('" + limit.getId() + "','" + column.getProperty() + "', this.options[this.selectedIndex].value);onInvokeAction('"
                        + limit.getId() + "')").close();
        
        html.option().close().optionEnd();
        
        for (Iterator iterator = options.iterator(); iterator.hasNext();) {
            String career = (String) iterator.next();
            html.option().value(career);
            if (selected != null && selected.equals(career)) {
                html.selected();
            }
            html.close();
            html.append(career).optionEnd();
        }

        html.selectEnd();

        return html.toString();
    }
    
    private Collection getOptions() {
        Set options = new HashSet();

        // filter out like values
        Collection items = getCoreContext().getAllItems();
        
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            President president = (President) iterator.next();
            options.add(president.getCareer());
        }
        
        return options;
    }
}
