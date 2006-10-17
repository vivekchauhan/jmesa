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

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Filter))
            return false;

        Filter that = (Filter) o;

        return that.getProperty().equals(this.getProperty());
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        int alias = this.getProperty() == null ? 0 : this.getProperty().hashCode();
        result = result * 37 + alias;
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
