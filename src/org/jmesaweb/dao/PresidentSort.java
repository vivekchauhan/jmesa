/*
 * Project: CareerAdmin
 * @(#)$Id: ApplicationSort.java,v 1.1 2006/09/21 22:05:17 jjohnston Exp $
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
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * Creates a command to wrap the Hibernate criteria API to sort.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class PresidentSort implements CriteriaCommand {
    List<Sort> sorts = new ArrayList<Sort>();

    public void addSort(String property, String order) {
        sorts.add(new Sort(property, order));
    }

    public Criteria execute(Criteria criteria) {
        for (Sort sort : sorts) {
            buildCriteria(criteria, sort.getProperty(), sort.getOrder());
        }

        return criteria;
    }

    private void buildCriteria(Criteria criteria, String property, String order) {
        if (order.equals(Sort.ASC)) {
            criteria.addOrder(Order.asc(property));
        } else if (order.equals(Sort.DESC)) {
            criteria.addOrder(Order.desc(property));
        }
    }
    
    private static class Sort {
        public final static String ASC = "asc";
        public final static String DESC = "desc";

        private final String property;
        private final String order;

        public Sort(String property, String order) {
            this.property = property;
            this.order = order;
        }

        public String getProperty() {
            return property;
        }

        public String getOrder() {
            return order;
        }
    }
}
