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
import org.jmesa.worksheet.Validation;

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
    private List<Validation> validations = new ArrayList<Validation>();

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

    public Order[] getSortOrder() {
        if (sortOrder == null) {
            sortOrder = new Order[] { Order.NONE, Order.ASC, Order.DESC };
        }

        return sortOrder;
    }

    public void setSortOrder(Order... sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
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

    public boolean isGeneratedOnTheFly() {
        return generatedOnTheFly;
    }

    public void setGeneratedOnTheFly(boolean generatedOnTheFly) {
        this.generatedOnTheFly = generatedOnTheFly;
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

    public void addWorksheetValidation(Validation validation) {
        addWorksheetValidation(validation, null, null);
    }

    public void addWorksheetValidation(Validation validation, String value) {
        addWorksheetValidation(validation, value, null);
    }

    public void addWorksheetValidation(Validation validation, String value, String errorMessage) {
        validation.setValue(value);
        validation.setMessage(errorMessage);
        validations.add(validation);
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
        for (Validation validation: validations) {
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

	public String getCustomWorksheetValidation() {
	    StringBuffer customValidations = new StringBuffer();

	    for (Validation validation: validations) {
	        if (validation.equals(Validation.CUSTOM)) {
	            customValidations.append("jQuery.validator.addMethod('" + validation.getValue() + "', " + validation.getValue() + ");\n");
	        }
	    }

	    return customValidations.toString();
	}
}
