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

import org.jmesa.limit.Limit;
import org.jmesa.web.Context;

/**
 * <p>
 * Will always persist the state of the table without having to pass any 
 * additional parameters around.
 * </p> 
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class PersistState implements State {
	private final Context context;
	private final String id;

	public PersistState(Context context, String id) {
		this.context = context;
		this.id = id;
	}

	public Limit retrieveLimit() {
		return (Limit) context.getSessionAttribute(id);
	}

	public void persistLimit(Limit limit) {
		context.setSessionAttribute(id, limit);
	}
}
