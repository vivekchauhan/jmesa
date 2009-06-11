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
package org.jmesa.util;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextSupport;
import org.jmesa.core.filter.FilterMatcherRegistry;
import org.jmesa.core.filter.FilterMatcherRegistrySupport;
import org.jmesa.limit.ExportType;
import org.jmesa.view.ExportTypesSupport;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.ColumnSupport;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.RowSupport;
import org.jmesa.view.component.Table;
import org.jmesa.view.component.TableSupport;
import org.jmesa.view.editor.PatternSupport;
import org.jmesa.view.html.toolbar.MaxRowsIncrementsSupport;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarSupport;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

/**
 * Utility to first check to see if the object being inspected is an instance of the support class.
 * If it is then will do a check to see if the object to be injected is already set. If it is not
 * then it will be injected.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class SupportUtils {

    private SupportUtils() {
    // cannot instantiate object.
    }

    /**
     * Set the WebContext on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param webContext The object to be injected.
     */
    public static void setWebContext(Object obj, WebContext webContext) {
        if ((obj instanceof WebContextSupport) && ((WebContextSupport) obj).getWebContext() == null) {
            ((WebContextSupport) obj).setWebContext(webContext);
        }
    }

    /**
     * Set the CoreContext on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param coreContext The object to be injected.
     */
    public static void setCoreContext(Object obj, CoreContext coreContext) {
        if ((obj instanceof CoreContextSupport) && ((CoreContextSupport) obj).getCoreContext() == null) {
            ((CoreContextSupport) obj).setCoreContext(coreContext);
        }
    }

    /**
     * Set the String pattern on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param pattern The object to be injected.
     */
    public static void setPattern(Object obj, String pattern) {
        if (obj instanceof PatternSupport && ((PatternSupport) obj).getPattern() == null) {
            ((PatternSupport) obj).setPattern(pattern);
        }
    }

    /**
     * Set the Table on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param table The object to be injected.
     */
    public static void setTable(Object obj, Table table) {
        if ((obj instanceof TableSupport) && ((TableSupport) obj).getTable() == null) {
            ((TableSupport) obj).setTable(table);
        }
    }

    /**
     * Set the Row on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param row The object to be injected.
     */
    public static void setRow(Object obj, Row row) {
        if (obj instanceof RowSupport && ((RowSupport) obj).getRow() == null) {
            ((RowSupport) obj).setRow(row);
        }
    }

    /**
     * Set the Column on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param column The object to be injected.
     */
    public static void setColumn(Object obj, Column column) {
        if (obj instanceof ColumnSupport && ((ColumnSupport) obj).getColumn() == null) {
            ((ColumnSupport) obj).setColumn(column);
        }
    }

    /**
     * Set the Toolbar on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param toolbar The object to be injected.
     */
    public static void setToolbar(Object obj, Toolbar toolbar) {
        if ((obj instanceof ToolbarSupport) && ((ToolbarSupport) obj).getToolbar() == null) {
            ((ToolbarSupport) obj).setToolbar(toolbar);
        }
    }

    /**
     * Set the exportTypes on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param exportTypes The object to be injected.
     */
    public static void setExportTypes(Object obj, ExportType... exportTypes) {
        if ((obj instanceof ExportTypesSupport) && ((ExportTypesSupport) obj).getExportTypes() == null) {
            ((ExportTypesSupport) obj).setExportTypes(exportTypes);
        }
    }

    /**
     * Set the maxRowsIncrements on the object being inspected if it is not already set.
     * 
     * @param obj The object being inspected.
     * @param maxRowsIncrements The object to be injected.
     */
    public static void setMaxRowsIncrements(Object obj, int[] maxRowsIncrements) {
        if ((obj instanceof MaxRowsIncrementsSupport) && ((MaxRowsIncrementsSupport) obj).getMaxRowsIncrements() == null) {
            ((MaxRowsIncrementsSupport) obj).setMaxRowsIncrements(maxRowsIncrements);
        }
    }

    /**
     * Set the filterMatcherRegistry on the object being inspected if it is not already set.
     *
     * @param obj The object being inspected.
     * @param registry The object to be injected.
     */
    public static void setFilterMatcherRegistry(Object obj, FilterMatcherRegistry registry) {
        if (obj instanceof FilterMatcherRegistrySupport && ((FilterMatcherRegistrySupport) obj).getFilterMatcherRegistry() == null) {
            ((FilterMatcherRegistrySupport) obj).setFilterMatcherRegistry(registry);
        }
    }
}
