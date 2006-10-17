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
package org.jmesa.limit.state;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.limit.Limit;

/**
 * <p>
 * Works like the current implementation in that you pass it a parameter 
 * telling it to keep the persisted state.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class NotifyToPersistState implements State {
	private final HttpServletRequest request;
	private final String id;
	private final String stateAttr;

	public NotifyToPersistState(HttpServletRequest request, String id, String stateAttr) {
		this.request = request;
		this.id = id;
		this.stateAttr = stateAttr;
	}

	public Limit getLimit() {
		String stateAttrValue = request.getParameter(stateAttr);
		if ("true".equalsIgnoreCase(stateAttrValue)) {
			return (Limit) request.getSession().getAttribute(id);
		}

		return null;
	}

	public void setLimit(Limit limit) {
		request.getSession().setAttribute(id, limit);
	}
}
