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

/**
 * @since 2.4.7
 * @author Siddhant Agrawal
 */
public class WorksheetValidation {

    private WorksheetValidationType validationType;
    private String validationName;
    private String value;
    private String errorMessage;

    public void setValidationType(WorksheetValidationType validationType) {
        this.validationType = validationType;
    }

    public void setValidationName(String validationName) {
        this.validationName = validationName;
    }

    public void setValue(String value) {
        if (value == null) {
            this.value = "true";
        } else {
            this.value = value;
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getRule() {
        return this.validationName + ": " + this.value;
    }
    
    public String getMessage() {
        if (errorMessage == null) {
            return "";
        }

        return this.validationName + ": '" + errorMessage + "'";
    }

    public String attachHandlerScript() {
        if (validationType == WorksheetValidationType.CUSTOM) {
            return "jQuery.validator.addMethod('" + validationName + "', " + validationName + ");\n";
        }
        
        return "";
    }
}
