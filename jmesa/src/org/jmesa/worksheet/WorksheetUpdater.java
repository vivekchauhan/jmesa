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

import org.jmesa.web.WebContext;

/**
 * Will store the Worksheet object in the users session by the table id. However, once the servlet
 * is set up the developer will not ever have to deal with the fact that the Worksheet object is in
 * session. The ajax calls will abstract that out from the html table side. Then the TableFacade
 * will abstract out the retrieve of the Worksheet in the controller.
 *
 * @since 2.3
 * @author Jeff Johnston
 */
public interface WorksheetUpdater {
    void update(WebContext webContext);
}
