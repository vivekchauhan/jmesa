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

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlColumnsGenerator;

public class TagHtmlColumnsGenerator extends AbstractContextSupport implements HtmlColumnsGenerator {    
    public List<HtmlColumn> getColumns() {
        List<HtmlColumn> columns = new ArrayList<HtmlColumn>();

        HtmlColumn firstName = new HtmlColumn("name.firstName");
        firstName.setTitle("First Name");
        columns.add(firstName);

        HtmlColumn lastName = new HtmlColumn("name.lastName");
        lastName.setTitle("Last Name");
        columns.add(lastName);

        HtmlColumn born = new HtmlColumn("born");
        columns.add(born);

        return columns;
    }
}