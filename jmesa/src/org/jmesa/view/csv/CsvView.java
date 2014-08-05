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
package org.jmesa.view.csv;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jmesa.view.AbstractExportView;
import org.jmesa.view.component.Column;
import org.jmesa.view.renderer.CellRenderer;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class CsvView extends AbstractExportView {
		
    private String delimiter;

    public CsvView() {}

    public CsvView(String delimiter) {
		
        this.delimiter = delimiter;
    }

    protected String getDelimiter() {
		
        return delimiter;
    }

    @Override
    public Object render() {
		
        StringBuilder results = new StringBuilder();

        List<Column> columns = getTable().getRow().getColumns();
        
        Iterator<Column> headerIterator = columns.iterator();
        while (headerIterator.hasNext()) {
            Column column = headerIterator.next();
            String title = column.getTitle();
            results.append("\"").append(escapeValue(title)).append("\"");
            
            if (headerIterator.hasNext()) {
                results.append(getDelimiter());                
            }
        }
        results.append("\r\n");
        
        int rowcount = 0;
        for (Object item : getCoreContext().getPageItems()) {
            rowcount++;

            Iterator<Column> bodyIterator = columns.iterator();
            while (bodyIterator.hasNext()) {
                Column column = bodyIterator.next();
                CellRenderer cellRenderer = column.getCellRenderer();
                Object value = cellRenderer.render(item, rowcount);
                results.append("\"").append(escapeValue(value)).append("\"");
                
                if (bodyIterator.hasNext()) {
                    results.append(getDelimiter());                    
                }
            }
            results.append("\r\n");
        }

        return results.toString();
    }
    
    String escapeValue(Object value) {
        
        if (value == null) {
            return "";
        }
        
        String stringval = String.valueOf(value);
        return stringval.replaceAll("\"", "\"\"");
    }
}
