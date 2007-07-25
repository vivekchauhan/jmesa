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
package org.jmesa.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author jeff jie
 */
public class PropertyTag extends TagSupport {
    private Logger logger = LoggerFactory.getLogger(ColumnTag.class);

    private static final long serialVersionUID = 1L;

    private String name;

    private ColumnTag column;

    @Override
    public int doStartTag() throws JspException {
        column = (ColumnTag) getParent();
        if (null == getName() || !"".equals(getName())) {
            try {
                pageContext.getOut().write("#{" + getName().trim() + "}");
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
            column.addAttribute(getName().trim());
        }
        return EVAL_PAGE;
    }

    public ColumnTag getColumn() {
        return column;
    }

    public void setColumn(ColumnTag column) {
        this.column = column;
    }

    public String getName() {
        return name == null || "".equals(name) ? column.getProperty() : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
