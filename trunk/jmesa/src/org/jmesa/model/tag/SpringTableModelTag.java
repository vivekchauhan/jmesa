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
package org.jmesa.model.tag;

import javax.servlet.jsp.PageContext;
import org.jmesa.facade.TableFacade;
import static org.jmesa.facade.TableFacadeFactory.createSpringTableFacade;
import org.jmesa.web.JspPageSpringWebContext;
import org.jmesa.web.SpringWebContext;

/**
 * A way to get the Spring specific functionality.
 *
 * @since 2.3.3
 * @author Jeff Johnston
 */
public class SpringTableModelTag extends TableModelTag {

    @Override
    protected SpringWebContext getWebContext() {
		
        return new JspPageSpringWebContext((PageContext) getJspContext());
    }

    @Override
    protected TableFacade createTableFacade() {
		
        return createSpringTableFacade(getId(), getWebContext());
    }
}
