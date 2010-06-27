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
package org.jmesa.worksheet;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.AbstractContextSupport;

/**
 * @since 2.4.7
 * @author Siddhant Agrawal
 */
public class WorksheetValidation extends AbstractContextSupport {

    public static final String TRUE = "true";

    private final String validationType;
    private final String value;
    private String errorMessage;
    private String errorMessageKey;

    private boolean custom;

    /**
     * WorksheetValidationType.REQUIRED, WorksheetValidation.TRUE
     * WorksheetValidationType.EMAIL, WorksheetValidation.TRUE
     * WorksheetValidationType.ACCEPT, "'jpg|png'"
     * WorksheetValidationType.MAX_LENGTH, "10"
     * WorksheetValidationType.MIN_LENGTH, "5"
     * WorksheetValidationType.RANGE_LENGTH, "[5, 10]"
     * WorksheetValidationType.RANGE, "[15, 20]"
     * WorksheetValidationType.MAX_VALUE, "20"
     * WorksheetValidationType.MIN_VALUE, "15"
     *
     * @since 2.4.7
     */
    public WorksheetValidation(String validationType, String value) {

        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Value is required for worksheet validation: " + validationType);
        }

        this.validationType = validationType;
        this.value = value;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public String getValidationType() {
        return validationType;
    }

    public WorksheetValidation setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public WorksheetValidation setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
        return this;
    }
    
    public String getRule() {
        return this.validationType + ": " + this.value;
    }
    
    public String getMessage() {

        String msg = errorMessage;
        if (errorMessageKey != null) {
            msg = getCoreContext().getMessage(errorMessageKey);
        }

        if (msg == null) {
            return "";
        }

        return this.validationType + ": '" + msg + "'";
    }
    
    public String getCustomWorksheetValidation() {
    	if (!custom) {
    		return "";
    	}
    	
    	return "jQuery.validator.addMethod('" + validationType + "', " + value + ");\n";
    }
}
