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
import org.jmesa.core.IdSupport;
import org.jmesa.core.filter.FilterMatcherRegistry;
import org.jmesa.core.filter.FilterMatcherRegistrySupport;
import org.jmesa.core.message.Messages;
import org.jmesa.core.message.MessagesSupport;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.state.StateAttrSupport;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.jmesa.web.HttpServletRequestSupport;
import org.jmesa.web.HttpServletResponseSupport;
/**
 * Utility to first check to see if the object being inspected is an instance of the support class.
 * If it is then will do a check to see if the object to be injected is already set. If it is not
 * then it will be injected.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class SupportUtils {
		
    private SupportUtils() {}

    /**
     * Set the HttpServletRequest on the object being inspected if it is not already set.
     *
     * @param obj The object being inspected.
     * @param request The object to be injected.
     */
    public static void setHttpServletRequest(Object obj, HttpServletRequest request) {

        if ((obj instanceof HttpServletRequestSupport) && ((HttpServletRequestSupport) obj).getHttpServletRequest() == null) {
            ((HttpServletRequestSupport) obj).setHttpServletRequest(request);
        }
    }

    /**
     * Set the HttpServletResponse on the object being inspected if it is not already set.
     *
     * @param obj The object being inspected.
     * @param response The object to be injected.
     */
    public static void setHttpServletResponse(Object obj, HttpServletResponse response) {

        if ((obj instanceof HttpServletResponseSupport) && ((HttpServletResponseSupport) obj).getHttpServletResponse() == null) {
            ((HttpServletResponseSupport) obj).setHttpServletResponse(response);
        }
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
     * Set the Messages on the object being inspected if it is not already set.
     *
     * @param obj The object being inspected.
     * @param messages The object to be injected.
     */
    public static void setMessages(Object obj, Messages messages) {
		
        if ((obj instanceof MessagesSupport) && ((MessagesSupport) obj).getMessages() == null) {
            ((MessagesSupport) obj).setMessages(messages);
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
     * Set the id on the object being inspected if it is not already set.
     *
     * @param obj The object being inspected.
     * @param id The object to be injected.
     */
    public static void setId(Object obj, String id) {
		
        if ((obj instanceof IdSupport) && ((IdSupport) obj).getId() == null) {
            ((IdSupport) obj).setId(id);
        }
    }

    /**
     * Set the stateAttr on the object being inspected if it is not already set.
     *
     * @param obj The object being inspected.
     * @param stateAttr The object to be injected.
     */
    public static void setStateAttr(Object obj, String stateAttr) {
		
        if ((obj instanceof StateAttrSupport) && ((StateAttrSupport) obj).getStateAttr() == null) {
            ((StateAttrSupport) obj).setStateAttr(stateAttr);
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
