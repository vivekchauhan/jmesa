/*
 * This Software is confidential and copyrighted.
 * Copyright  2003, 2008 Room & Board, Inc.  All Rights Reserved.
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
package com.roomandboard.loadtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Startup {

    private static Logger logger = LoggerFactory.getLogger(Startup.class);

    public static void main(String[] args) {

        BeanFactory factory = new ClassPathXmlApplicationContext(new String[]{"com/roomandboard/loadtest/applicationContext.xml"});
        logger.info("Starting the load test processing program...");
        ProductCacheLoader productCacheLoader = (ProductCacheLoader) factory.getBean("productCacheLoader");
        productCacheLoader.loadCache();
    }
}
