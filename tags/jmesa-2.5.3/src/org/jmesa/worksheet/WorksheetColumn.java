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
public interface WorksheetColumn extends Serializable {
    /**
     * @return The column property.
     */
    public String getProperty();

    /**
     * @return The original column value before editing it.
     */
    public String getOriginalValue();

    /**
     * @return The value of the column after it was edited.
     */
    public String getChangedValue();

    /**
     * Set the changed value for this column.
     * 
     * @param changedValue The edited column value.
     */
    public void setChangedValue(String changedValue);

    /**
     * Set the error for this column.
     * 
     * @param error The text of what went wrong.
     */
    public void setError(String error);

    /**
     * Set the error for this column.
     * 
     * @param key The error key to find in the messages.
     */
    public void setErrorKey(String key);

    /**
     * @return The text error.
     */
    public String getError();

    /**
     * @return Is true if an error is set.
     */
    public boolean hasError();
    
    /**
     * Remove the error that was previously set.
     */
    public void removeError();
}
