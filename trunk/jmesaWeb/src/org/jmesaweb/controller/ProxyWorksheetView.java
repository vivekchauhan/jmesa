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
package org.jmesaweb.controller;

import java.util.List;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.worksheet.editor.WorksheetEditor;

/**
 * @author Jeff Johnston
 */
public class ProxyWorksheetView extends HtmlView {

    public Object render() {

        setWorksheeCellEditorProxy();

        return super.render();
    }

    private void setWorksheeCellEditorProxy() {
        List<Column> columns = getTable().getRow().getColumns();
        for (Column column : columns) {
            HtmlColumn htmlColumn = (HtmlColumn) column;
            if (htmlColumn.getProperty().equals("career")) {
                htmlColumn.getCellEditor(); // creates and wraps worksheet editor
                                                              // this should also be done when requesting worksheet editor
                                                              // and is a workaround for this example to work right now
                
                // get the worksheet editor
                WorksheetEditor worksheetEditor = htmlColumn.getWorksheetEditor();
                
                // decorate the worksheet editor
                ProxyWorksheetEditor proxyWorksheetEditor = new ProxyWorksheetEditor(worksheetEditor);
                htmlColumn.setWorksheetEditor(proxyWorksheetEditor);
            }
        }
    }
}
