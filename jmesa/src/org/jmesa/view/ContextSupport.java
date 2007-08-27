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

import org.jmesa.core.CoreContextSupport;
import org.jmesa.web.WebContextSupport;

/**
 * <p>
 * Support to handle both the CoreContext and WebContext.
 * </p>
 * 
 * <p>
 * Note: in version 2.2 this was changed to an interface so that classes would
 * not have to extend this class to get the feature. This interface is used
 * throughout the library to detect whether or not the webContext and
 * coreContext should be set on the implementing class automatically.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public interface ContextSupport extends WebContextSupport, CoreContextSupport {
    // There are no methods specific to just ContextSupport.
}
