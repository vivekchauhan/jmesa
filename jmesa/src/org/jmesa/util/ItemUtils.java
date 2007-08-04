package org.jmesa.util;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General utilities to process the Collecton of Beans or the Collection of
 * Maps. Most methods wrap or add value to the commons Beanutils.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class ItemUtils {
    private static Logger logger = LoggerFactory.getLogger(ItemUtils.class);

    private ItemUtils() {
    }

    /**
     * Get the value from the Bean or Map by property.
     * 
     * @param item The Bean or Map.
     * @param property The Bean attribute or Map key.
     * @return The value from the Bean or Map.
     */
    @SuppressWarnings("unchecked")
    public static Object getItemValue(Object item, String property) {
        Object itemValue = null;

        try {
            if (item instanceof Map) {
                itemValue = ((Map) item).get(property);
            } else {
                itemValue = PropertyUtils.getProperty(item, property);
            }
        } catch (Exception e) {
            logger.warn("item class " + item.getClass().getName() + " doesn't have property " + property);
        }

        return itemValue;
    }
}
