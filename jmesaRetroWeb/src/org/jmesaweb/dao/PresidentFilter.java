/*
 * Project: CareerAdmin
 * @(#)$Id: ApplicationFilter.java,v 1.1 2006/09/21 22:05:17 jjohnston Exp $
 * 
 * This Software is confidential and copyrighted.
 * Copyright  2003, 2005 Room & Board, Inc.  All Rights Reserved.
 * 
 * This software is the proprietary information of Room & Board, Inc.  Use 
 * is subject to license terms.
 * 
 * Title to Software and all associated intellectual property rights is retained 
 * by Room & Board, Inc. and/or its licensors.
 * 
 * Except as specifically authorized in any Supplemental License Terms, this 
 * software may not be copied.  Unless enforcement is prohibited by 
 * applicable law, you may not modify, decompile, reverse engineer this 
 * Software without the express written permission of Room & Board, Inc.
 * 
 * No right, title or interest in or to any trademark, service mark, logo or 
 * trade name of Room & Board, Inc. or its licensors is granted under 
 * this Agreement.
 */
package org.jmesaweb.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Creates a command to wrap the Hibernate criteria API to filter.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class PresidentFilter implements CriteriaCommand {
    List filters = new ArrayList();

    public void addFilter(String property, Object value) {
        filters.add(new Filter(property, value));
    }

    public Criteria execute(Criteria criteria) {
        for (Iterator it = filters.iterator(); it.hasNext();) {
            Filter filter = (Filter)it.next();
            buildCriteria(criteria, filter.getProperty(), filter.getValue());
        }

        return criteria;
    }

    private void buildCriteria(Criteria criteria, String property, Object value) {
        if (value != null) {
            criteria.add(Restrictions.like(property, "%" + value + "%").ignoreCase());
        }
    }

    private static class Filter {
        private final String property;
        private final Object value;

        public Filter(String property, Object value) {
            this.property = property;
            this.value = value;
        }

        public String getProperty() {
            return property;
        }

        public Object getValue() {
            return value;
        }
    }
}
