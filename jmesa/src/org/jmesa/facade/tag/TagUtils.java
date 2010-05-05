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
package org.jmesa.facade.tag;

import java.util.Map;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.RowFilter;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Order;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.View;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.view.html.renderer.HtmlFilterRenderer;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;
import org.jmesa.view.html.renderer.HtmlRowRenderer;
import org.jmesa.view.html.renderer.HtmlTableRenderer;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.worksheet.editor.WorksheetEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common utilities for the tag classes.
 * 
 * @since 2.2.1
 * @author Jeff Johnston
 */
class TagUtils {

    private static Logger logger = LoggerFactory.getLogger(TagUtils.class);

    static Object createInstance(String className) {
        if (StringUtils.isEmpty(className)) {
            return null;
        }

        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error("Could not create the class [" + className + "]", e);
            throw new RuntimeException("Could not create the class [" + className + "]", e);
        }
    }

    /**
     * @return Is true is the validation passes
     */
    static boolean validateColumn(SimpleTagSupport simpleTagSupport, String property) {
        if (property == null) {
            return true; // no coflicts
        }

        HtmlRowTag rowTag = (HtmlRowTag) SimpleTagSupport.findAncestorWithClass(simpleTagSupport, HtmlRowTag.class);
        Map<String, ?> pageItem = rowTag.getPageItem();
        if (pageItem.get(property) != null) {
            String msg = "The column property [" + property + "] is not unique. One column value will overwrite another.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        TableFacadeTag facadeTag = (TableFacadeTag) SimpleTagSupport.findAncestorWithClass(simpleTagSupport, TableFacadeTag.class);
        String var = facadeTag.getVar();
        if (var.equals(property)) {
            String msg = "The column property [" + property + "] is the same as the TableFacadeTag var attribute [" + var + "].";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        return true;
    }

    /**
     * @return Get the FilterMatcherMap object.
     */
    static FilterMatcherMap getTableFacadeFilterMatcherMap(String filterMatcherMap) {
        return (FilterMatcherMap) createInstance(filterMatcherMap);
    }

    /**
     * @return Get the Messages object.
     */
    static Messages getTableFacadeMessages(String messages) {
        return (Messages) createInstance(messages);
    }

    /**
     * @return Get the Preferences object.
     */
    static Preferences getTableFacadePreferences(String preferences) {
        return (Preferences) createInstance(preferences);
    }

    /**
     * @return Get the RowFilter object.
     */
    static RowFilter getTableFacadeRowFilter(String rowFilter) {
        return (RowFilter) createInstance(rowFilter);
    }

    /**
     * @return Get the ColumnSort object.
     */
    static ColumnSort getTableFacadeColumnSort(String columnSort) {
        return (ColumnSort) createInstance(columnSort);
    }

    /**
     * Get the Toolbar. If the Toolbar does not exist then one will be created.
     * 
     * @return Get the Toolbar object.
     */
    static Toolbar getTableFacadeToolbar(String toolbar) {
        return (Toolbar) createInstance(toolbar);
    }

    /**
     * Convert the max row increments from a string to an int array.
     * 
     * @return Get the max row increments.
     */
    public static int[] getTableFacadeMaxRowIncrements(String maxRowsIncrements) {
        if (StringUtils.isEmpty(maxRowsIncrements)) {
            return null;
        }

        String[] results = StringUtils.split(maxRowsIncrements, ",");

        int[] toolbarMaxRowIncrements = new int[results.length];

        for (int i = 0; i < results.length; i++) {
            toolbarMaxRowIncrements[i] = Integer.parseInt(results[i]);
        }

        return toolbarMaxRowIncrements;
    }

    /**
     * Get the View. If the View does not exist then one will be created.
     * 
     * @return Get the View object.
     */
    static View getTableFacadeView(String view) {
        return (View) createInstance(view);
    }

    /**
     * Get the table TableRenderer object.
     * 
     * @since 2.2
     */
    static HtmlTableRenderer getTableTableRenderer(HtmlTable table, String tableRenderer) {
        if (StringUtils.isBlank(tableRenderer)) {
            return table.getTableRenderer();
        }

        return (HtmlTableRenderer) createInstance(tableRenderer);
    }

    /**
     * Get the row RowRenderer object.
     * 
     * @since 2.2
     */
    static HtmlRowRenderer getRowRowRenderer(HtmlRow row, String rowRenderer) {
        if (StringUtils.isBlank(rowRenderer)) {
            return row.getRowRenderer();
        }

        return (HtmlRowRenderer) createInstance(rowRenderer);
    }

    /**
     * Get the row Onclick RowEvent object.
     */
    static RowEvent getRowOnclick(HtmlRow row, String onclick) {
        if (StringUtils.isBlank(onclick)) {
            return row.getOnclick();
        }

        return (RowEvent) createInstance(onclick);
    }

    /**
     * Get the row Onmouseover RowEvent object.
     */
    static RowEvent getRowOnmouseover(HtmlRow row, String onmouseover) {
        if (StringUtils.isBlank(onmouseover)) {
            return row.getOnmouseover();
        }

        return (RowEvent) createInstance(onmouseover);
    }

    /**
     * Get the row Onmouseout RowEvent object.
     */
    static RowEvent getRowOnmouseout(HtmlRow row, String onmouseout) {
        if (StringUtils.isBlank(onmouseout)) {
            return row.getOnmouseout();
        }

        return (RowEvent) createInstance(onmouseout);
    }

    /**
     * @since 2.2
     * @return The column CellRenderer object.
     */
    static HtmlCellRenderer getColumnCellRenderer(HtmlColumn column, String cellRenderer) {
        if (StringUtils.isBlank(cellRenderer)) {
            return column.getCellRenderer();
        }

        HtmlCellRenderer result = (HtmlCellRenderer) createInstance(cellRenderer);
        result.setCellEditor(column.getCellRenderer().getCellEditor()); // reset the default

        return result;
    }

    /**
     * <p>
     * If the worksheetEditor is not defined then create a WorksheetEditor.
     * </p>
     * 
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * worksheetEditor.
     * </p>
     * 
     * @return The WorksheetEditor to use.
     */
    static WorksheetEditor getColumnWorksheetEditor(HtmlColumn column, String worksheetEditor) {
        if (StringUtils.isEmpty(worksheetEditor)) {
            return null;
        }

        WorksheetEditor result = (WorksheetEditor) createInstance(worksheetEditor);

        return result;
    }

    /**
     * <p>
     * If the cellEditor is not defined then create a BasicCellEditor.
     * </p>
     * 
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * editor. If a setPattern() method is defined on your editor and you have defined the column
     * pattern attribute on the tag it will be set on your CellEditor automatically.
     * </p>
     * 
     * @return The CellEditor to use.
     */
    static CellEditor getColumnCellEditor(HtmlColumn column, String cellEditor, String pattern, boolean hasBody) {
        boolean cellEditorNotDefined = StringUtils.isEmpty(cellEditor);
        if (hasBody && cellEditorNotDefined) {
            return new BasicCellEditor();
        } else if (cellEditorNotDefined) {
            return new HtmlCellEditor();
        }

        CellEditor result = (CellEditor) createInstance(cellEditor);
        SupportUtils.setPattern(result, pattern);

        return result;
    }

    /**
     * @since 2.2
     * @return The column FilterRenderer object.
     */
    static HtmlFilterRenderer getColumnFilterRenderer(HtmlColumn column, String filterRenderer) {
        if (StringUtils.isBlank(filterRenderer)) {
            return column.getFilterRenderer();
        }

        HtmlFilterRenderer result = (HtmlFilterRenderer) createInstance(filterRenderer);
        result.setFilterEditor(column.getFilterRenderer().getFilterEditor()); // reset the default

        return result;
    }

    /**
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * editor.
     * </p>
     * 
     * @return The FilterEditor to use.
     */
    static FilterEditor getColumnFilterEditor(HtmlColumn column, String filterEditor) {
        if (StringUtils.isEmpty(filterEditor)) {
            return column.getFilterRenderer().getFilterEditor();
        }

        return (FilterEditor) createInstance(filterEditor);
    }

    /**
     * @since 2.2
     * @return The column HeaderRenderer object.
     */
    static HtmlHeaderRenderer getColumnHeaderRenderer(HtmlColumn column, String headerRenderer) {
        if (StringUtils.isBlank(headerRenderer)) {
            return column.getHeaderRenderer();
        }

        HtmlHeaderRenderer result = (HtmlHeaderRenderer) createInstance(headerRenderer);
        result.setHeaderEditor(column.getHeaderRenderer().getHeaderEditor()); // reset the default

        return result;
    }

    /**
     * <p>
     * If it is defined and it extends ContextSupport then set the WebContext and CoreContext on the
     * editor.
     * </p>
     * 
     * @return The HeaderEditor to use.
     */
    static HeaderEditor getColumnHeaderEditor(HtmlColumn column, String headerEditor) {
        if (StringUtils.isEmpty(headerEditor)) {
            return column.getHeaderRenderer().getHeaderEditor();
        }

        return (HeaderEditor) createInstance(headerEditor);
    }

    /**
     * <p>
     * Sets validations on the column
     * </p>
     * 
     */
    static void setWorksheetValidations(HtmlColumn column, String worksheetValidationString) {
        if (!StringUtils.isEmpty(worksheetValidationString)) {
            String worksheetValidations[] = worksheetValidationString.split(";");
            for (String validation: worksheetValidations) {
                String validationParams[] = validation.split(":");
                
                if (validationParams.length < 2 || validationParams.length > 3) {
                    throw new IllegalArgumentException("Required format of worksheet validation is \"validationType:value:[errorMessage]\"");
                }
                
                String validationType = validationParams[0].trim();
                String value = validationParams[1].trim();
                String errorMessage = null;
                
                if (validationParams.length == 3) {
                    errorMessage = validationParams[2].trim();
                }
                
                column.addWorksheetValidation(validationType, value, errorMessage);
            }
        }
    }
    
    /**
     * <p>
     * Sets custom validations on the column
     * </p>
     * 
     */
    static void setCustomWorksheetValidations(HtmlColumn column, String customWorksheetValidationString) {
        if (!StringUtils.isEmpty(customWorksheetValidationString)) {
            String worksheetValidations[] = customWorksheetValidationString.split(";");
            for (String validation: worksheetValidations) {
                String validationParams[] = validation.split(":");
                
                if (validationParams.length != 3) {
                    throw new IllegalArgumentException("Required format of custom worksheet validation is \"customValidationType:value:errorMessage\"");
                }
                
                // Trim has be used here since spaces in validationType will lead to javascript error 
                String validationType = validationParams[0].trim();
                String value = validationParams[1].trim();
                String errorMessage = validationParams[2].trim();
                
                column.addCustomWorksheetValidation(validationType, value, errorMessage);
            }
        }
    }
    
    /**
     * Take the comma separted Order values and convert them into an Array. The legal values include
     * "none, asc, desc".
     * 
     * @since 2.2
     * @return The sort order array for the column.
     */
    public static Order[] getColumnSortOrder(String sortOrder) {
        if (StringUtils.isBlank(sortOrder)) {
            return null;
        }

        String[] orders = StringUtils.split(sortOrder, ",");

        Order[] results = new Order[orders.length];

        for (int i = 0; i < orders.length; i++) {
            String order = orders[i];
            Order valueOfParam = Order.valueOfParam(order);
            if (valueOfParam == null) {
                throw new IllegalStateException("The sortOrder must consist of comma separated values of asc, desc, and none.");
            }
            results[i] = valueOfParam;
        }

        return results;
    }

    public static ExportType[] getTableFacadeExportTypes(String exportTypes) {
        if (StringUtils.isBlank(exportTypes)) {
            return null;
        }

        String[] types = StringUtils.split(exportTypes, ",");

        ExportType[] et = new ExportType[types.length];
        for (int i = 0; i < types.length; i++) {
            et[i] = ExportType.valueOfParam(types[i]);
        }

        return et;
    }
}
