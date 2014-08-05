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
package org.jmesa.facade.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * A filter to put the request and response on the ThreadLocal to pass to the
 * TableFacade implementation. Very useful for controllers in frameworks that
 * try to keep the request and response inaccessable.
 * </p>
 * 
 * <p>
 *  <filter>
 *     <filter-name>TableFacadeFilter</filter-name>
 *     <filter-class>org.jmesa.facade.filter.TableFacadeFilter</filter-class>
 *  </filter>
 * 
 *  <filter-mapping>
 *     <filter-name>TableFacadeFilter</filter-name>
 *     <url-pattern>/*</url-pattern>
 * </filter-mapping>
 * <p>
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
public class TableFacadeFilter implements Filter {
		
    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        FilterThreadLocal.set((HttpServletRequest) request, (HttpServletResponse) response);

        // run the other filters
        chain.doFilter(request, response);

        FilterThreadLocal.set(null, null);
    }

    @Override
    public void destroy() {}

    public static class FilterThreadLocal {

        private static ThreadLocal<FilterValue> tLocal = new ThreadLocal<FilterValue>();

        static void set(HttpServletRequest request, HttpServletResponse response) {
		
            if (request == null && response == null) {
                tLocal.set(null);
                return;
            }

            tLocal.set(new FilterValue(request, response));
        }

        public static HttpServletRequest getHttpServletRequest() {
		
            FilterValue filterValue = (FilterValue) tLocal.get();
            return filterValue.getRequest();
        }
        
        public static HttpServletResponse getHttpServletResponse() {
		
            FilterValue filterValue = (FilterValue) tLocal.get();
            return filterValue.getResponse();
        }

        private static class FilterValue {
		
            private final HttpServletRequest request;
            private final HttpServletResponse response;

            public FilterValue(HttpServletRequest request, HttpServletResponse response) {
		
                this.request = request;
                this.response = response;
            }

            public HttpServletRequest getRequest() {
		
                return request;
            }

            public HttpServletResponse getResponse() {
		
                return response;
            }
        }
    }
}
