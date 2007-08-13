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
 * An immutable class that is used to sort the rows that are returned for a
 * table. The property is the Bean (Or Map) attribute that will used to sort the
 * results based on the order. Or, in other words, it is simply the column that
 * the user is trying to sort in the order specified.
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
public final class Sort implements Serializable, Comparable<Sort> {
    private final int position;
    private final String property;
    private final Order order;

    public Sort(int position, String property, Order order) {
        this.position = position;
        this.property = property;
        this.order = order;
    }

    /**
     * @return The Bean (Or Map) attribute used to reduce the results.
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return Will be used to sort the results.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @return The placement of the Sort within the SortSet.
     */
    public int getPosition() {
        return position;
    }

    /**
     * A Sort is compared by its position. This follows the natural ordering
     * because the assumption is that each Sort has a unique property with a
     * unique order. Or, in other words, if two Sort objects have the same
     * property then they have to have the same position. And in the same manner
     * if two Sort objects have the same position they better have the same
     * property.
     */
    public int compareTo(Sort sort) {
        if (this.getPosition() < sort.getPosition()) {
            return -1;
        }

        if (this.getPosition() == sort.getPosition()) {
            return 0;
        }

        return 1;
    }

    /**
     * Equality is based on the property. Or, in other words no two Sort Objects
     * can have the same property.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Sort))
            return false;

        Sort that = (Sort) o;

        return that.getProperty().equals(this.getProperty());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int property = this.getProperty() == null ? 0 : this.getProperty().hashCode();
        result = result * 37 + property;
        return result;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("position", position);
        builder.append("property", property);
        builder.append("order", order);
        return builder.toString();
    }
}