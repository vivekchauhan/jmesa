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
 * <p>
 * The Export represents the export type that the user invoked.
 * </p> 
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class Export {
    private final String type;

    public Export(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Export))
            return false;

        Export that = (Export) o;

        return that.getType().equals(this.getType());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int property = this.getType() == null ? 0 : this.getType().hashCode();
        result = result * 37 + property;
        return result;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("type", getType());
        return builder.toString();
    }
}
