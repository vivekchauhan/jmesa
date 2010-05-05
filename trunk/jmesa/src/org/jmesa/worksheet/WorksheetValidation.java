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

    public static final String TRUE = "true";
    
    private String validationType;
    private String value;
    private String errorMessage;
    private boolean isCustomValidation = false;

    public void setCustomValidation(boolean isCustomValidation) {
        this.isCustomValidation = isCustomValidation;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getRule() {
        return this.validationType + ": " + this.value;
    }
    
    public String getMessage() {
        if (errorMessage == null) {
            return "";
        }

        return this.validationType + ": '" + errorMessage + "'";
    }

    public String attachHandlerScript() {
        if (isCustomValidation) {
            return "jQuery.validator.addMethod('" + validationType + "', " + validationType + ");\n";
        }
        
        return "";
    }
}
