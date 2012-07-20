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
 * Deals with CellEditors when the the table is being used as an editable worksheet. Wraps an
 * existing CellEditor. If the cell was edited then will return the edited value, otherwise return
 * the value of the regular CellEditor.
 * 
 * @since 2.3
 * @author Jeff Johnston
 * @deprecated You should use the InputWorksheetEditor
 */
@Deprecated
public class HtmlWorksheetEditor extends InputWorksheetEditor {
}
