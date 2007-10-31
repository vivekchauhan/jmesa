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
package org.jmesa.facade.tag;

import java.util.Map;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common utilities for the tag classes.
 * 
 * @since 2.2.1
 * @author Jeff Johnston
 */
class TagUtils {
    private static Logger logger = LoggerFactory.getLogger(TagUtils.class);

    /**
     * @return Is true is the validation passes
     */
    static boolean validateColumn(SimpleTagSupport simpleTagSupport, String property) {
        if (property == null) {
            return true; // no coflicts
        }

        HtmlRowTag rowTag = (HtmlRowTag) SimpleTagSupport.findAncestorWithClass(simpleTagSupport, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();
        if (pageItem.get(property) != null) {
            String msg = "The column property [" + property + "] is not unique. One column value will overwrite another.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        TableFacadeTag facadeTag = (TableFacadeTag) SimpleTagSupport.findAncestorWithClass(simpleTagSupport, TableFacadeTag.class);
        String var = facadeTag.getVar();
        if (var.equals(property)) {
            String msg = "The column property [" + property + "] is the same as the TableFacadeTag var attribute [" + var + "].";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        return true;
    }
}
