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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Jeff Johnston
 */
public final class Sort {
    private final String alias;
    private final String property;
    private final Order order;

    public Sort() {
        this.alias = null;
        this.property = null;
        this.order = Order.UNORDERED;
    }

    public Sort(String alias, String property, Order order) {
        this.alias = alias;
        this.property = property;
        this.order = order;
    }

    public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }

    public Order getOrder() {
        return order;
    }

    public boolean isSorted() {
        return order != null;
    }
    
    public boolean isAliased() {
        return !alias.equals(property);
    }
    
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("alias", alias);
        builder.append("property", property);
        builder.append("order", order);
        return builder.toString();
    }
}