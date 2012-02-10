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

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Represents the unique property for the row.
 * 
 * @since 2.3
 * @author Jeff Johnston
 */
public class UniqueProperty implements Serializable {
		
    private final String name;
    private final String value;

    public UniqueProperty(String name, String value) {
		
        this.name = name;
        this.value = value;
    }

    public String getName() {
		
        return name;
    }

    public String getValue() {
		
        return value;
    }
    
    /**
     * Equality is based on the property. Or, in other words no two Filter
     * Objects can have the same property.
     */
    @Override
    public boolean equals(Object o) {
		
        if (o == this)
            return true;

        if (!(o instanceof UniqueProperty))
            return false;

        UniqueProperty that = (UniqueProperty) o;

        return that.getValue().equals(this.getValue());
    }

    @Override
    public int hashCode() {
		
        int result = 17;
        int val = this.getValue() == null ? 0 : this.getValue().hashCode();
        result = result * 37 + val;
        return result;
    }

    @Override
    public String toString() {
		
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("name", name);
        builder.append("value", value);
        return builder.toString();
    }
}
