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
package org.jmesa.model;

import javax.servlet.http.HttpServletRequest;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.LimitActionFactory;

/**
 * @since 3.0
 * @author Jeff Johnston
 */
public class TableModelUtils {
    private TableModelUtils(){}

    public static boolean isExporting(String id, HttpServletRequest request) {
        return getExportType(id, request) != null;
    }

    public static ExportType getExportType(String id, HttpServletRequest request) {
        LimitActionFactory actionFactory = new LimitActionFactory(id, request.getParameterMap());
        return actionFactory.getExportType();
    }
}
