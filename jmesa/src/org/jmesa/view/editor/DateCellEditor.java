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
package org.jmesa.view.editor;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jmesa.view.ContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class DateCellEditor extends ContextSupport implements CellEditor {
    private String pattern = "MM/dd/yyyy";

    private Logger logger = LoggerFactory.getLogger( DateCellEditor.class );    
    
    public DateCellEditor() {
    }

    public DateCellEditor(String pattern) {
        this.pattern = pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Object getValue(Object item, String property, int rowcount) {
        Object itemValue = null;

        try {
            itemValue = PropertyUtils.getProperty(item, property);
            Locale locale = getWebContext().getLocale();
            if (itemValue != null) {
                itemValue = DateFormatUtils.format((Date) itemValue, pattern, locale);
            }
        } catch (Exception e) {
            logger.warn( "item class " + item.getClass().getName() + " doesn't have property " + property );
        }

        return itemValue;
    }
}
