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
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * A WorksheetColumn represents the edited HtmlColumn. The originalValue is the value that was
 * originally pulled from the bean (ie, pulled from database). This allows a table to revert back to
 * the original value if needed. The changedValue is the value that was modified by the user.
 * </p>
 *
 * <p>
 * You are also able to register an error with the column. I debated being able to put more than one
 * error, but the GUI would have a hard time dealing with multiple errors anyway so I kept this down
 * to one error.
 * </p>
 *
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetColumn {
    private WorksheetRow row;
    private String property;
    private String error;
    private String originalValue;
    private String changedValue;

    public WorksheetColumn(String property, String originalValue) {
        this.property = property;
        this.originalValue = originalValue;
    }

    public void setRow(WorksheetRow row) {
        this.row = row;
    }

    /**
     * @return The column property.
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return The original column value before editing it.
     */
    public String getOriginalValue() {
        return originalValue;
    }

    /**
     * @return The value of the column after it was edited.
     */
    public String getChangedValue() {
        return changedValue;
    }

    /**
     * Set the changed value for this column.
     *
     * @param changedValue The edited column value.
     */
    public void setChangedValue(String changedValue) {
        this.changedValue = changedValue;
    }

    /**
     * Set the error for this column.
     *
     * @param error The text of what went wrong.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Set the error for this column.
     *
     * @param key The error key to find in the messages.
     */
    public void setErrorKey(String key) {
        setError(row.getMessages().getMessage(key));
    }

    /**
     * @return The text error.
     */
    public String getError() {
        return error;
    }

    /**
     * @return Is true if an error is set.
     */
    public boolean hasError() {
        return StringUtils.isNotBlank(error);
    }

    /**
     * Remove the error that was previously set.
     */
    public void removeError() {
        this.error = null;
    }

    /**
     * Equality is based on the property. In other words no two Column Objects can have the same
     * property.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof WorksheetColumn))
            return false;

        WorksheetColumn that = (WorksheetColumn) o;

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
        builder.append("property", this.getProperty());
        builder.append("originalValue", this.getOriginalValue());
        builder.append("changedValue", this.getChangedValue());
        return builder.toString();
    }
}
