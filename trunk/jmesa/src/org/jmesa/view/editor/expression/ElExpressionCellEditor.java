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
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.el.ExpressionString;
import org.apache.commons.el.parser.ELParser;
import org.apache.commons.el.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses commons-el to evaluate EL expressions.
 * 
 * @version 2.4
 * @author bgould
 */
public class ElExpressionCellEditor extends AbstractCellEditor {
		
    private Logger logger = LoggerFactory.getLogger(ElExpressionCellEditor.class);
    private org.apache.commons.el.Logger pLogger = new org.apache.commons.el.Logger(System.err);

    private String var;
    private Object template;

    public ElExpressionCellEditor(Expression expression) {
		
        this(expression.getVar(), expression.getTemplate());
    }

    public ElExpressionCellEditor(String var, Object template) {
		
        notNull("The var is required.", var);
        this.var = var;

        notNull("The template is required.", template);
        this.template = template;

        try {
            this.template = new ELParser(new StringReader(String.valueOf(template))).ExpressionString();
        } catch (ParseException e) {
            this.template = null;
            throw new RuntimeException(e);
        }
    }

    public Object getValue(Object item, String property, int rowcount) {
		
        Object result = null;

        try {
            // ExpressionString is a mixture of template text and EL
            // expressions; ex. ${lastName}, ${firstName}
            if (template instanceof ExpressionString) {
                result = ((ExpressionString) template).evaluate(getVariableResolver(item), getFunctionMapper(), pLogger);

            // Expression is a single EL expression with no template text;
            // ex. ${lastName + ', ' + firstName}
            } else if (template instanceof org.apache.commons.el.Expression) {
                result = ((org.apache.commons.el.Expression) template).evaluate(getVariableResolver(item), getFunctionMapper(), pLogger);

            // If the expression parsed to a String, it is just template text 
            } else if (template instanceof String) {
                result = template;
            }
        } catch (ELException e) {
            logger.warn("Could not process el expression editor with property " + property, e);
        }

        return result;
    }

    /**
     * Creates a VariableResolver based on the current row bean and variable name.
     * 
     * @param item The row's backing bean.
     */
    protected VariableResolver getVariableResolver(Object item) {
		
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(var, item);
        return new VariableResolverMap(context);
    }

    /**
     * Override this method to make EL functions available to your expressions.
     */
    protected FunctionMapper getFunctionMapper() {
		
        return null;
    }

    /**
     * VariableResolver that resolves implicit objects based on a Map.
     */
    public static class VariableResolverMap implements VariableResolver {
		

        private final Map<?, ?> context;

        public VariableResolverMap(Map<?, ?> context) {
		
            this.context = context;
        }

        public Object resolveVariable(String var) {
		
            return context.get(var);
        }
    }
}
