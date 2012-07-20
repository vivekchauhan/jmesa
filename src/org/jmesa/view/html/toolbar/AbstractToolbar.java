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
package org.jmesa.view.html.toolbar;

import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.ExportTypesSupport;
import org.jmesa.view.component.Table;
import org.jmesa.view.component.TableSupport;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractToolbar extends AbstractContextSupport implements Toolbar, TableSupport, ExportTypesSupport, MaxRowsIncrementsSupport  {
		
    private Table table;
    private String[] exportTypes;
    private int[] maxRowsIncrements;
    protected boolean enableSeparators = true;
    protected boolean enablePageNumbers = false;

    public Table getTable() {
		
        return table;
    }

    public void setTable(Table table) {
		
        this.table = table;
    }

    public String[] getExportTypes() {
		
        return exportTypes;
    }

    public void setExportTypes(String... exportTypes) {
		
        this.exportTypes = exportTypes; 
    }

    public int[] getMaxRowsIncrements() {
		
        return maxRowsIncrements;
    }

    public void setMaxRowsIncrements(int[] maxRowsIncrements) {
		
        this.maxRowsIncrements = maxRowsIncrements;
    }
    
    public void enableSeparators(boolean isEnabled) {
		
        this.enableSeparators = isEnabled;
    }

    public void enablePageNumbers(boolean isEnabled) {
		
        this.enablePageNumbers = isEnabled;
    }
}
