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

import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.jmesa.util.ItemUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An editor to work with Joda Time dates. Just send in a valid date pattern and the date will be formated.
 *
 * @since 2.4.4
 * @author Jeff Johnston
 */
public class DateTimeCellEditor extends DateCellEditor {
    private final Logger logger = LoggerFactory.getLogger(DateTimeCellEditor.class);

    public DateTimeCellEditor() {
        // default constructor
    }

    /**
     * @param pattern The pattern to use.
     */
    public DateTimeCellEditor(String pattern) {
        setPattern(pattern);
    }

    /**
     * Get the formatted date value based on the pattern set.
     */
    @Override
    public Object getValue(Object item, String property, int rowcount) {
        Object itemValue = null;

        try {
            itemValue = ItemUtils.getItemValue(item, property);

            if (itemValue == null || StringUtils.isBlank(String.valueOf(itemValue))) {
                return null;
            }

            Locale locale = getWebContext().getLocale();
            DateTime dateTime = (DateTime) itemValue;
            itemValue = dateTime.toString(getPattern(), locale);
        } catch (Exception e) {
            logger.warn("Could not process date time editor with property " + property, e);
        }

        return itemValue;
    }
}