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
public enum Validation {
    REQUIRED,
    EMAIL,
    URL,
    DATE,
    DATE_ISO,
    NUMBER,
    DIGITS,
    CREDIT_CARD,
    ACCEPT,
    MAX_LENGTH,
    MIN_LENGTH,
    RANGE_LENGTH,
    RANGE,
    MAX_VALUE,
    MIN_VALUE,
    CUSTOM;

    String value = null;
    String errorMessage = null;

    public String getValue() {
    	return value;
    }
    
    public void setValue(String value) {
    	switch (this) {
    	case MAX_LENGTH:
    	case MIN_LENGTH:
    	case RANGE_LENGTH:
    	case RANGE:
    	case MIN_VALUE:
    	case MAX_VALUE:
    	case CUSTOM:
    		this.value = value;
    	}
    }

    public void setMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }
    
    public String getMessage() {
    	String errMsg = this.errorMessage;
        
        if (errMsg == null) {
    		if (this == CUSTOM) {
    		    errMsg = "Custom error!";
            } else {
                return "";
            }
    	}

    	return this.toCode() + ": '" + errMsg + "'";
    }
    
    public String getRule() {
    	if (this == CUSTOM) {
    		return this.value + ": true";
    	}
    	
    	return this.toCode() + ": " + ((this.value == null) ? "true" : this.value);
    }
    
    public String toCode() {
        switch (this) {
        case REQUIRED:
            return "required";
        case EMAIL:
            return "email";
        case URL:
            return "url";
        case DATE:
            return "date";
        case DATE_ISO:
            return "dateISO";
        case DIGITS:
            return "digits";
        case CREDIT_CARD:
            return "creditcard";
        case ACCEPT:
            return "accept";
        case MAX_LENGTH:
            return "maxlength";
        case MIN_LENGTH:
            return "minlength";
        case RANGE_LENGTH:
            return "rangelength";
        case RANGE:
            return "range";
        case MIN_VALUE:
            return "min";
        case MAX_VALUE:
            return "max";
        case CUSTOM:
            return this.getValue();
        default:
            return "";
        }
    }
}
