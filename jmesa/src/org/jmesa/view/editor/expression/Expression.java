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
package org.jmesa.view.editor.expression;

import static org.jmesa.util.AssertUtils.notNull;

/**
 * @version 2.4
 * @author bgould
 */
public class Expression {
    private final Language language;
    private final String var;
    private final Object template;

    public Expression(Language language, String var, Object template) {
        notNull("The language is required.", language);
        this.language = language;

        notNull("The var is required.", var);
        this.var = var;

        notNull("The template is required.", template);
        this.template = template;
    }

    public Language getLanguage() {
        return language;
    }

    public Object getTemplate() {
        return template;
    }

    public String getVar() {
        return var;
    }
}
