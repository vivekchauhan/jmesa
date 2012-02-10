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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jmesa.core.CoreContext;

import org.jmesa.limit.Order;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.editor.HtmlFilterEditor;
import org.jmesa.view.html.editor.HtmlHeaderEditor;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.view.html.renderer.HtmlFilterRenderer;
import org.jmesa.view.html.renderer.HtmlHeaderRenderer;
import org.jmesa.view.renderer.CellRenderer;
import org.jmesa.view.renderer.FilterRenderer;
import org.jmesa.view.renderer.HeaderRenderer;
import org.jmesa.worksheet.WorksheetValidation;
import org.jmesa.worksheet.editor.HtmlWorksheetEditor;
import org.jmesa.worksheet.editor.WorksheetEditor;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlColumn extends Column {
		
    private Boolean filterable;
    private Boolean sortable;
    private Boolean editable;
    private String width;
    private Order[] sortOrder;
    private boolean generatedOnTheFly;
    private String style;
    private String styleClass;
    private String headerStyle;
    private String headerClass;
    private String filterStyle;
    private String filterClass;
    private List<WorksheetValidation> validations;

    /*
     * Store locally because the super class
     * will create defaults.
     */
    private CellRenderer cellRenderer;
    private CellEditor cellEditor;

    private WorksheetEditor worksheetEditor;

    public HtmlColumn() {}

    public HtmlColumn(String property) {
		
        setProperty(property);
    }

    @Override
    public HtmlColumn property(String property) {
		
    	setProperty(property);
    	return this;
    }

    @Override
    public HtmlColumn title(String title) {
		
    	setTitle(title);
    	return this;
    }

    @Override
    public HtmlColumn titleKey(String key) {
		
    	setTitleKey(key);
    	return this;
    }

    public boolean isFilterable() {
		
        if (filterable != null) {
            return filterable.booleanValue();
        }
        
        HtmlRow row = getRow();
        if (row != null && row.isFilterable() != null) {
            return row.isFilterable().booleanValue();
        }
        
        return true;
    }

    public void setFilterable(Boolean filterable) {
		
        this.filterable = filterable;
    }

    public HtmlColumn filterable(Boolean filterable) {
		
    	setFilterable(filterable);
    	return this;
    }
    
    public boolean isSortable() {
		
        if (sortable != null) {
            return sortable.booleanValue();
        }
        
        HtmlRow row = getRow();
        if (row != null && row.isSortable() != null) {
            return row.isSortable().booleanValue();
        }
        
        return true;
    }

    public void setSortable(Boolean sortable) {
		
        this.sortable = sortable;
    }

	public HtmlColumn sortable(Boolean sortable) {
		
		setSortable(sortable);
		return this;
	}
	
    /**
     * @return Is true if the column is editable.
     * @since 2.3
     */
    public boolean isEditable() {
		
        if (editable != null) {
            return editable.booleanValue();
        }
        
        return true;
    }

    /**
     * Set if column is editable.
     * 
     * @since 2.3
     * @param editable Is true if the column is editable.
     */
    public void setEditable(Boolean editable) {
		
        this.editable = editable;
    }

	public HtmlColumn editable(Boolean editable) {
		
		setEditable(editable);
		return this;
	}
	
    /**
     * @since 2.2
     * @return The sort order for the column.
     */
    public Order[] getSortOrder() {
		
        if (sortOrder == null) {
            sortOrder = new Order[] { Order.NONE, Order.ASC, Order.DESC };
        }

        return sortOrder;
    }

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
    public void setSortOrder(Order... sortOrder) {
		
        this.sortOrder = sortOrder;
    }

	public HtmlColumn sortOrder(Order... sortOrder) {
		
		setSortOrder(sortOrder);
		return this;
	}
	
    public String getWidth() {
		
        return width;
    }

    public void setWidth(String width) {
		
        this.width = width;
    }

	public HtmlColumn width(String width) {
		
		setWidth(width);
		return this;
	}

    /**
     * @return Is true if generated on the fly through the api.
     */
    public boolean isGeneratedOnTheFly() {
		
        return generatedOnTheFly;
    }

    /**
     * Flag the column that it was generated on the fly. Only useful for the internal api so
     * developers should not use or override this variable.
     * 
     * @since 2.2.1
     */
    public void setGeneratedOnTheFly(boolean generatedOnTheFly) {
		
        this.generatedOnTheFly = generatedOnTheFly;
    }

	public HtmlColumn generatedOnTheFly(boolean generatedOnTheFly) {
		
		setGeneratedOnTheFly(generatedOnTheFly);
		return this;
	}

    @Override
    public HtmlCellRenderer getCellRenderer() {
		
        if (cellRenderer == null) {
            HtmlCellRenderer htmlCellRenderer = new HtmlCellRenderer(this);
            setCellRenderer(htmlCellRenderer);
            return htmlCellRenderer;
        }
        return (HtmlCellRenderer) cellRenderer;
    }

    @Override
    public void setCellRenderer(CellRenderer cellRenderer) {
		
        this.cellRenderer = cellRenderer;
        this.cellRenderer.setColumn(this);
    }

    /**
     * @return The CellEditor for this column. If this is an editable worksheet then return the WorksheetEditor.
     */
    @Override
    public CellEditor getCellEditor() {
		
        if (cellEditor == null) {
            this.cellEditor = new HtmlCellEditor();
        }

        CoreContext coreContext = getCoreContext();
        if (coreContext != null && (ViewUtils.isEditable(coreContext.getWorksheet()) && isEditable())) {
            if (worksheetEditor == null) {
                this.worksheetEditor = new HtmlWorksheetEditor();
            }

            if (worksheetEditor.getCellEditor() == null) {
                worksheetEditor.setCellEditor(cellEditor);
            }

            return worksheetEditor;
        }

        return cellEditor;
    }

    @Override
    public HtmlColumn cellEditor(CellEditor editor) {
		
    	setCellEditor(editor);
    	return this;
    }

    @Override
    public void setCellEditor(CellEditor cellEditor) {
		
        this.cellEditor = cellEditor;

        /*
         * This is useful for editors that are decorated at 
         * runtime. Most of the support classes are handled
         * in the TableFacadeUtils.init() method though.
         */
        SupportUtils.setWebContext(cellEditor, getWebContext());
        SupportUtils.setCoreContext(cellEditor, getCoreContext());
        SupportUtils.setColumn(cellEditor, this);
    }

    public WorksheetEditor getWorksheetEditor() {
		
        return worksheetEditor;
    }

    public void setWorksheetEditor(WorksheetEditor worksheetEditor) {
		
        this.worksheetEditor = worksheetEditor;
    }

    public HtmlColumn worksheetEditor(WorksheetEditor editor){
		
    	setWorksheetEditor(editor);
    	return this;
    }

    @Override
    public HtmlHeaderRenderer getHeaderRenderer() {
		
        HeaderRenderer headerRenderer = super.getHeaderRenderer();
        if (headerRenderer == null) {
            HtmlHeaderRenderer htmlHeaderRenderer = new HtmlHeaderRenderer(this);
            super.setHeaderRenderer(htmlHeaderRenderer);
            return htmlHeaderRenderer;
        }
        return (HtmlHeaderRenderer) headerRenderer;
    }

    @Override
    public HeaderEditor getHeaderEditor() {
		
        HeaderEditor headerEditor = super.getHeaderEditor();
        if (headerEditor == null) {
            HtmlHeaderEditor htmlHeaderEditor = new HtmlHeaderEditor();
            super.setHeaderEditor(htmlHeaderEditor);
            return htmlHeaderEditor;
        }
        return headerEditor;
    }

    @Override
    public HtmlFilterRenderer getFilterRenderer() {
		
        FilterRenderer filterRenderer = super.getFilterRenderer();
        if (filterRenderer == null) {
            HtmlFilterRenderer htmlFilterRenderer = new HtmlFilterRenderer(this);
            super.setFilterRenderer(htmlFilterRenderer);
            return htmlFilterRenderer;
        }
        return (HtmlFilterRenderer) filterRenderer;
    }

    @Override
    public FilterEditor getFilterEditor() {
		
        FilterEditor filterEditor = super.getFilterEditor();
        if (filterEditor == null) {
            HtmlFilterEditor htmlFilterEditor = new HtmlFilterEditor();
            super.setFilterEditor(htmlFilterEditor);
            return htmlFilterEditor;
        }
        return filterEditor;
    }

    @Override
    public HtmlColumn filterEditor(FilterEditor filterEditor) {
		
    	setFilterEditor(filterEditor);
    	return this;
    }

    @Override
    public HtmlRow getRow() {
		
        return (HtmlRow) super.getRow();
    }

    public List<WorksheetValidation> getWorksheetValidations() {
		
        if (validations == null) {
            return Collections.emptyList();
        }

        return validations;
    }

    public HtmlColumn addWorksheetValidation(WorksheetValidation worksheetValidation) {
		
        worksheetValidation.setCoreContext(getCoreContext());
        if (validations == null) {
             validations = new ArrayList<WorksheetValidation>();
        }
        validations.add(worksheetValidation);
        return this;
    }
    
    public HtmlColumn addCustomWorksheetValidation(WorksheetValidation worksheetValidation) {
		
        worksheetValidation.setCoreContext(getCoreContext());
        worksheetValidation.setCustom(true);
        if (validations == null) {
             validations = new ArrayList<WorksheetValidation>();
        }
        validations.add(worksheetValidation);
        return this;
    }

	public String getWorksheetValidationRules() {
		
        return prepareJsonString("rule");
	}

	public String getWorksheetValidationMessages() {
		
        return prepareJsonString("message");
	}

    private String prepareJsonString(String type) {
		
        if (validations == null) {
            return "";
        }

        StringBuffer json = new StringBuffer();

        boolean firstOccurance = true;
        for (WorksheetValidation validation: validations) {
            String nameValuePair = null;
            
            if ("rule".equals(type)) {
                nameValuePair = validation.getRule();
            } else if ("message".equals(type)) {
                nameValuePair = validation.getMessage();
            }
            
            if (!"".equals(nameValuePair)) {
                if (firstOccurance) {
                    json.append("'" + getProperty() + "' : { ");
                    firstOccurance = false;
                } else {
                    json.append(", ");
                }
                json.append(nameValuePair);
            }
        }

        if (!"".equals(json.toString())) {
            json.append(" }");
        }

        return json.toString();
    }
    
    public String getStyle() {
		
        return style;
    }

    public void setStyle(String style) {
		
    	this.style = style;
    }
    
    public HtmlColumn style(String style) {
		
    	setStyle(style);
    	return this;
    }
    
    public String getStyleClass() {
		
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
		
    	this.styleClass = styleClass;
    }

    public HtmlColumn styleClass(String styleClass) {
		
    	setStyleClass(styleClass);
    	return this;
    }

    public String getHeaderStyle() {
		
        return headerStyle;
    }
    
    public void setHeaderStyle(String headerStyle) {
		
    	this.headerStyle = headerStyle;
    }
    
    public HtmlColumn headerStyle(String headerStyle) {
		
    	setHeaderStyle(headerStyle);
    	return this;
    }

    public String getHeaderClass() {
		
        return headerClass;
    }
    
    public void setHeaderClass(String headerClass) {
		
    	this.headerClass = headerClass;
    }
    
    public HtmlColumn headerClass(String headerClass) {
		
    	setHeaderClass(headerClass);
    	return this;
    }

    public String getFilterStyle() {
		
        return filterStyle;
    }

    public void setFilterStyle(String filterStyle) {
		
    	this.filterStyle = filterStyle;
    }

    public HtmlColumn filterStyle(String filterStyle) {
		
    	setFilterStyle(filterStyle);
    	return this;
    }

    public String getFilterClass() {
		
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
		
    	this.filterClass = filterClass;
    }

    public HtmlColumn filterClass(String filterClass) {
		
    	setFilterClass(filterClass);
    	return this;
    }
}
