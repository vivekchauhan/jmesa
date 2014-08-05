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
import org.jmesa.view.editor.*;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.jmesa.core.CoreContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Uses the BSF engine to evaluate expressions.
 *
 * @version 2.4
 * @author bgould
 */
public class BsfExpressionCellEditor extends AbstractCellEditor {
		
    private Logger logger = LoggerFactory.getLogger(BsfExpressionCellEditor.class);

    private final Language language;
    private final String var;
    private final Object template;

    public BsfExpressionCellEditor(Expression expression) {
		
        this(expression.getLanguage(), expression.getVar(), expression.getTemplate());
    }

    public BsfExpressionCellEditor(Language language, String var, Object template) {
		
        notNull("The language is required.", language);
        this.language = language;

        if (!BSFManager.isLanguageRegistered(language.toString().toLowerCase())) {
            throw new IllegalArgumentException("The language " + language + " is not supported.");
        }

        notNull("The var is required.", var);
        this.var = var;

        notNull("The template is required.", template);
        this.template = template;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {
		
        Object result = null;

        BSFManager manager = getBsfManager();
        try {
            manager.declareBean(var, item, Object.class);
            result = manager.eval(language.toString().toLowerCase(), "jmesa", 0, 0, template);
            manager.undeclareBean(var);
        } catch (BSFException e) {
            logger.warn("Could not process bsf expression editor with property " + property, e);
        } finally {
            manager.terminate();
        }

        return result;
    }

    /**
     * Cache the manager on a per-table basis to insure thread safety.
     */
    protected BSFManager getBsfManager() {
		
        CoreContext ctx = getCoreContext();
        BSFManager manager = (BSFManager) ctx.getAttribute("org.jmesa.BSFManager");
        if (manager == null) {
            manager = new BSFManager();
            ctx.setAttribute("org.jmesa.BSFManager", manager);
        }
        return manager;
    }
}
