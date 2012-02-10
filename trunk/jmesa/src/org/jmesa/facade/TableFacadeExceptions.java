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
package org.jmesa.facade;

import java.util.Collection;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.Limit;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.toolbar.Toolbar;

/**
 * Helper to contain all the exceptions returned from the TableFacade.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
final class TableFacadeExceptions {

    private TableFacadeExceptions() {}

    static void validateCoreContextIsNull(CoreContext coreContext, String object) {
		
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the CoreContext.");
        }
    }

    static void validateTableIsNull(Table table, String object) {
		
        if (table != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the Table.");
        }
    }

    static void validateViewIsNull(View view, String object) {
		
        if (view != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the View.");
        }
    }

    static void validateToolbarIsNull(Toolbar toolbar, String object) {
		
        if (toolbar != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the Toolbar.");
        }
    }

    static void validateLimitIsNull(Limit limit, String object) {
		
        if (limit != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the Limit.");
        }
    }

    static void validateRowSelectIsNotNull(Limit limit) {
		
        if (limit.getRowSelect() == null) {
            throw new IllegalStateException(
                "The RowSelect is null. You need to set the totalRows on the facade.");
        }
    }

    static void validateItemsIsNull(Collection<?> items) {
		
        if (items != null) {
            throw new IllegalStateException(
                "It is too late to set editable. You need to set editable before using the Limit.");
        }
    }

    static void validateItemsIsNotNull(Collection<?> items) {
		
        if (items == null) {
            throw new IllegalStateException("The items are null. You need to set the items on the facade (or model).");
        }
    }
}
