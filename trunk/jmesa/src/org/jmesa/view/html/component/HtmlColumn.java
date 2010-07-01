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
package org.jmesa.view.html.component;

import java.util.List;
import org.jmesa.limit.Order;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.view.html.renderer.HtmlFilterRenderer;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;
import org.jmesa.view.renderer.FilterRenderer;
import org.jmesa.worksheet.WorksheetValidation;
import org.jmesa.worksheet.editor.WorksheetEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public interface HtmlColumn extends Column {
    public boolean isFilterable();

    public void setFilterable(Boolean filterable);

    public boolean isSortable();

    public void setSortable(Boolean sortable);
    
    /**
     * @return Is true if the column is editable.
     */
    public boolean isEditable();

    /**
     * Set if column is editable.
     * 
     * @param editable Is true if the column is editable.
     */
    public void setEditable(Boolean editable);

    /**
     * @since 2.2
     * @return The sort order for the column.
     */
    public Order[] getSortOrder();

    /**
     * <p>
     * Set the sort order for the column. This restricts the sorting to only the types listed here.
     * Typically you would use this to exclude the 'none' Order so that the user can only sort
     * ascending and decending once invoked.
     * </p>
     * 
     * <p>
     * Note though that initially this does not change the look of the column, or effect the
     * sorting, when the table is first displayed. For instance, if you only want to sort asc and
     * then desc then when the table is initially displayed you need to make sure you set the Limit
     * to be ordered. The reason is, by design, the limit does not look at the view for any
     * information. The syntax to set the limit would be: limit.getSortSet().addSort();. If you do
     * not do this then the effect will be that the once the column is sorted then it will just flip
     * between asc and desc, which is still a really nice effect and is what I would mostly do.
     * </p>
     * 
     * @since 2.2
     * @param sortOrder The order array.
     */
    public void setSortOrder(Order... sortOrder);

    public String getWidth();

    public void setWidth(String width);

    public HtmlFilterRenderer getFilterRenderer();

    public void setFilterRenderer(FilterRenderer filterRenderer);

    public HtmlCellRenderer getCellRenderer();

    public HtmlHeaderRenderer getHeaderRenderer();

    public HtmlRow getRow();

    /**
     * @return Is true if generated on the fly through the api.
     */
    public boolean isGeneratedOnTheFly();

    /**
     * Flag the column that it was generated on the fly. Only useful for the internal api so
     * developers should not use or override this variable.
     * 
     * @since 2.2.1
     */
    public void setGeneratedOnTheFly(boolean generated);

    public HtmlColumn addWorksheetValidation(WorksheetValidation worksheetValidation);
    public HtmlColumn addCustomWorksheetValidation(WorksheetValidation worksheetValidation);
    public List<WorksheetValidation> getWorksheetValidations();
    public String getWorksheetValidationRules();
    public String getWorksheetValidationMessages();

    public void setWorksheetEditor(WorksheetEditor editor);
    public void setStyle(String style);
    public void setStyleClass(String styleClass);
    public void setFilterStyle(String filterStyle);
    public void setFilterStyleClass(String filterStyleClass);
    public void setHeaderStyle(String headerStyle);
    public void setHeaderStyleClass(String headerStyleClass);
}
