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
package org.jmesa.view;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.worksheet.Worksheet;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class ViewUtils {
		
    private ViewUtils() {}

    /**
     * Convert camelCase text to a readable word. example: camelCaseToWord --> Camel Case To Word
     */
    public static String camelCaseToWord(String camelCaseText) {
		
        if (StringUtils.isEmpty(camelCaseText)) {
            return camelCaseText;
        }

        if (camelCaseText.equals(camelCaseText.toUpperCase())) {
            return camelCaseText;
        }

        char[] ch = camelCaseText.toCharArray();
        String first = "" + ch[0];
        String build = first.toUpperCase();

        for (int i = 1; i < ch.length; i++) {
            String test = "" + ch[i];

            if (test.equals(test.toUpperCase())) {
                build += " ";
            }

            build += test;
        }

        return build;
    }

    /**
     * Find out if the column is sitting on an even row.
     */
    public static boolean isRowEven(int rowcount) {
		
        if (rowcount != 0 && (rowcount % 2) == 0) {
            return true;
        }

        return false;
    }

    /**
     * Find out if the column is sitting on an odd row.
     */
    public static boolean isRowOdd(int rowcount) {
		
        if (rowcount != 0 && (rowcount % 2) == 0) {
            return false;
        }

        return true;
    }

    /**
     * @return Is true if any columns are filterable.
     */
    public static boolean isFilterable(List<HtmlColumn> columns) {
		
        for (HtmlColumn column : columns) {
            if (column.isFilterable()) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * @param exportTypes The array of export types to check.
     * @return Is true if there is an export to do.
     */
    public static boolean isExportable(String... exportTypes) {
		
        return  exportTypes != null && exportTypes.length > 0;
    }
    
    /**
     * @return Is true if there is a worksheet.
     */
    public static boolean isEditable(Worksheet worksheet) {
		
        if (worksheet != null) {
            return true;
        }

        return false;
    }    
}
