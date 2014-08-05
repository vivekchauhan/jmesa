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
package org.jmesa.limit;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * An immutable class that is used to reduce the rows that are returned for a
 * table. The property is the Bean (Or Map) attribute that will used to reduce
 * the results based on the value. Or, in other words, it is simply the column
 * that the user is trying to filter and the value that they entered.
 * </p>
 * 
 * <p>
 * The property can use dot (.) notation to access nested classes. For example
 * if you have an object called President that is composed with another object
 * called Name then your property would be name.firstName
 * 
 * <pre>
 * public class President {
 *     private Name name;
 * 
 *     public Name getName() {
 *         return name;
 *     }
 * }
 * 
 * public class Name {
 *     private String firstName;
 * 
 *     public String getFirstName() {
 *         return firstName;
 *     }
 * }
 * </pre>
 * 
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public final class Filter implements Serializable {
		
    private final String property;
    private final String value;

    public Filter(String property, String value) {
		
        this.property = property;
        this.value = value;
    }

    /**
     * @return The Bean (Or Map) attribute used to reduce the results.
     */
    public String getProperty() {
		
        return property;
    }

    /**
     * @return Will be used to reduce the results.
     */
    public String getValue() {
		
        return value;
    }

    /**
     * Equality is based on the property. Or, in other words no two Filter
     * Objects can have the same property.
     */
    @Override
    public boolean equals(Object o) {
		
        if (o == this) {
            return true;
        }

        if (!(o instanceof Filter)) {
            return false;
        }

        Filter that = (Filter) o;

        return that.getProperty().equals(this.getProperty());
    }

    @Override
    public int hashCode() {
		
        int result = 17;
        int prop = this.getProperty() == null ? 0 : this.getProperty().hashCode();
        result = result * 37 + prop;
        return result;
    }

    @Override
    public String toString() {
		
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("property", property);
        builder.append("value", value);
        return builder.toString();
    }
}
