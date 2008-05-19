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

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCacheLoader {

    private Logger logger = LoggerFactory.getLogger(ProductCacheLoader.class);
    
    private ProductCacheLoaderDao productCacheLoaderDao;
    private Statistics statistics = new Statistics();
    private String webSiteProductControllerUrl;
    private int threads = 20;

    public ProductCacheLoader(ProductCacheLoaderDao productCacheLoaderDao, String webSiteProductControllerUrl) {

        this.productCacheLoaderDao = productCacheLoaderDao;
        this.webSiteProductControllerUrl = webSiteProductControllerUrl;
    }

    public void loadCache() {

        List<String> articleNumbers = productCacheLoaderDao.queryAllArticleNumbers();
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(articleNumbers.size());

        // load up the queue with all the article numbers
        for (Iterator<String> iter = articleNumbers.iterator(); iter.hasNext();) {
            queue.add(iter.next());
        }

        statistics.stopwatch.start();

        // start up the threads
        for (int i = 0; i < threads; i++) {
            new ProductInitializerThread(queue, "ProductPrefetch - " + i).start();
        }

        listenForJobsToFinish(queue);
    }

    /**
     * Listen and wait for the queue to empty out. When the queue is empty then
     * notify someone that it is done.
     */
    private void listenForJobsToFinish(final BlockingQueue<String> queue) {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                if (queue.isEmpty()) {
                    statistics.stopwatch.stop();
                    logger.info("Finished loading " + statistics.getProductCount() + " products. " + statistics.stopwatch.shortSummary());
                    scheduler.shutdownNow();
                }
            }
        }, 10, 10, SECONDS); // check every 10 seconds for the queue to empty

    }

    /**
     * Will get fed a queue of products that need to be processed.
     */
    private class ProductInitializerThread extends Thread {

        private BlockingQueue<String> queue;
        private HttpClient client = new HttpClient();

        public ProductInitializerThread(BlockingQueue<String> queue, String name) {

            this.queue = queue;
            this.setName(name);
            this.setPriority(Thread.MIN_PRIORITY);
        }

        @Override
        public void run() {

            while (!queue.isEmpty()) {
                String articleNumber = null;
                try {
                    articleNumber = queue.take();

                    String[] urls = webSiteProductControllerUrl.split(",");
                    for (String url : urls) {
                        GetMethod method = new GetMethod(url + articleNumber);
                        int status = client.executeMethod(method);
                        if (status != HttpStatus.SC_OK) {
                            logger.error("Error loading up product for article number " + articleNumber);
                        }
                        method.releaseConnection();
                    }
                    statistics.addProductCount();
                } catch (Exception e) {
                    logger.error("Error loading product for article number " + articleNumber, e);
                }
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    logger.error("Problems putting thread to sleep.");
                }
            }
        }
    }
}
