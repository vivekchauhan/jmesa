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

import org.jmesa.view.editor.CellEditor;

/**
 * @version 2.4
 * @author bgould
 */
public class ExpressionCellEditorFactoryImpl implements ExpressionCellEditorFactory {

    public CellEditor createCellEditor(Expression expression) {
        Language language = expression.getLanguage();

        if (language == Language.EL) {
            return new ElExpressionCellEditor(expression);
        }

        return new BsfExpressionCellEditor(expression);
    }
}
