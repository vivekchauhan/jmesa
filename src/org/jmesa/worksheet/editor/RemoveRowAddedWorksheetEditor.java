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
package org.jmesa.worksheet.editor;

/**
 * @since 2.4.7
 * @author Siddhant Agrawal
 */
public class RemoveRowAddedWorksheetEditor extends RemoveRowWorksheetEditor {
    /**
     * Display the icon as the column will be present only in added rows.
     */
    @Override
    public Object getValue(Object item, String property, int rowcount) {
        if (getWorksheetColumn(item, property) == null) {
            return "";
        }

        return super.getValue(item, property, rowcount);
    }
}
