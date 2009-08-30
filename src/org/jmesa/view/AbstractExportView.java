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
package org.jmesa.view;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextSupport;
import org.jmesa.view.component.Table;

/**
 * @since 2.4.2
 * @author qxodream
 */
public abstract class AbstractExportView implements View, CoreContextSupport {

    private Table table;
    private CoreContext coreContext;

    protected AbstractExportView(Table table, CoreContext coreContext) {
        this.table = table;
        this.coreContext = coreContext;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public CoreContext getCoreContext() {
        return coreContext;
    }

    public void setCoreContext(CoreContext coreContext) {
        this.coreContext = coreContext;
    }
}
