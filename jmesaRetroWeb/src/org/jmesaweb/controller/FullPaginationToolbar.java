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

import org.jmesa.view.html.toolbar.HtmlToolbar;

/**
 * Shows how to override the HtmlToolbar and enable the page numbers.
 *
 * @since 2.3
 * @author Jeff Johnston
 */
public class FullPaginationToolbar extends HtmlToolbar {
    public String render() {
        enablePageNumbers(true);
        return super.render();
    }
}
