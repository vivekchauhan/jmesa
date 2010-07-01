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
import java.util.List;

import org.jmesa.limit.Order;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.component.ColumnImpl;
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
public class HtmlColumnImpl extends ColumnImpl implements HtmlColumn {
    private Boolean filterable;
    private Boolean sortable;
    private Boolean editable;
    private String width;
    private FilterRenderer filterRenderer;
    private Order[] sortOrder;
    private boolean generatedOnTheFly;
    private List<WorksheetValidation> validations = new ArrayList<WorksheetValidation>();

    public HtmlColumnImpl() {
        // default constructor
    }

    public HtmlColumnImpl(String property) {
        setProperty(property);
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

    public HtmlColumnImpl filterable(Boolean filterable) {
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

	public HtmlColumnImpl sortable(Boolean sortable) {
		setSortable(sortable);
		return this;
	}
	
    /**
     * @since 2.3
     */
    public boolean isEditable() {
        if (editable != null) {
            return editable.booleanValue();
        }
        
        return true;
    }

    /**
     * @since 2.3
     */
    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

	public HtmlColumnImpl editable(Boolean editable) {
		setEditable(editable);
		return this;
	}
	
    public Order[] getSortOrder() {
        if (sortOrder == null) {
            sortOrder = new Order[] { Order.NONE, Order.ASC, Order.DESC };
        }

        return sortOrder;
    }

    public void setSortOrder(Order... sortOrder) {
        this.sortOrder = sortOrder;
    }

	public HtmlColumnImpl sortOrder(Order... sortOrder) {
		setSortOrder(sortOrder);
		return this;
	}
	
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

	public HtmlColumnImpl width(String width) {
		setWidth(width);
		return this;
	}

    public HtmlFilterRenderer getFilterRenderer() {
        return (HtmlFilterRenderer) filterRenderer;
    }

    public void setFilterRenderer(FilterRenderer filterRenderer) {
        this.filterRenderer = filterRenderer;
        SupportUtils.setWebContext(filterRenderer, getWebContext());
        SupportUtils.setCoreContext(filterRenderer, getCoreContext());
        SupportUtils.setColumn(filterRenderer, this);
    }

	public HtmlColumnImpl filterRenderer(FilterRenderer filterRenderer) {
		setFilterRenderer(filterRenderer);
		return this;
	}

    public boolean isGeneratedOnTheFly() {
        return generatedOnTheFly;
    }

    public void setGeneratedOnTheFly(boolean generatedOnTheFly) {
        this.generatedOnTheFly = generatedOnTheFly;
    }

	public HtmlColumnImpl generatedOnTheFly(boolean generatedOnTheFly) {
		setGeneratedOnTheFly(generatedOnTheFly);
		return this;
	}

    @Override
    public HtmlCellRenderer getCellRenderer() {
        return (HtmlCellRenderer) super.getCellRenderer();
    }

    @Override
    public HtmlHeaderRenderer getHeaderRenderer() {
        return (HtmlHeaderRenderer) super.getHeaderRenderer();
    }

    @Override
    public HtmlRow getRow() {
        return (HtmlRow) super.getRow();
    }

    public List<WorksheetValidation> getWorksheetValidations() {
        return validations;
    }

    public HtmlColumnImpl addWorksheetValidation(WorksheetValidation worksheetValidation) {
        worksheetValidation.setCoreContext(getCoreContext());
        validations.add(worksheetValidation);
        return this;
    }
    
    public HtmlColumnImpl addCustomWorksheetValidation(WorksheetValidation worksheetValidation) {
        worksheetValidation.setCoreContext(getCoreContext());
        worksheetValidation.setCustom(true);
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
    
    public void setWorksheetEditor(WorksheetEditor editor){
    	getCellRenderer().setWorksheetEditor(editor);
    }

    public HtmlColumnImpl worksheetEditor(WorksheetEditor editor){
    	setWorksheetEditor(editor);
    	return this;
    }
    
    public void setStyle(String style) {
    	getCellRenderer().setStyle(style);
    }
    
    public HtmlColumnImpl style(String style) {
    	setStyle(style);
    	return this;
    }
    
    public void setStyleClass(String styleClass) {
    	getCellRenderer().setStyleClass(styleClass);
    }

    public HtmlColumnImpl styleClass(String styleClass) {
    	setStyleClass(styleClass);
    	return this;
    }

    public void setFilterStyle(String filterStyle) {
    	getFilterRenderer().setStyle(filterStyle);
    }
    
    public HtmlColumnImpl filterStyle(String filterStyle) {
    	setFilterStyle(filterStyle);
    	return this;
    }
    
    public void setFilterStyleClass(String filterStyleClass) {
    	getFilterRenderer().setStyleClass(filterStyleClass);
    }
    
    public HtmlColumnImpl filterStyleClass(String filterStyleClass) {
    	setFilterStyleClass(filterStyleClass);
    	return this;
    }

    public void setHeaderStyle(String headerStyle) {
    	getHeaderRenderer().setStyle(headerStyle);
    }
    
    public HtmlColumnImpl headerStyle(String headerStyle) {
    	setHeaderStyle(headerStyle);
    	return this;
    }
    
    public void setHeaderStyleClass(String headerStyleClass) {
    	getHeaderRenderer().setStyleClass(headerStyleClass);
    }
    
    public HtmlColumnImpl headerStyleClass(String headerStyleClass) {
    	setHeaderStyleClass(headerStyleClass);
    	return this;
    }
}
