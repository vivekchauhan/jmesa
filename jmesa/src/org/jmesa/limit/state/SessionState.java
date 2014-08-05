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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Pass the stateAttr to find the Limit with the persisted state. The allows a
 * user to display a table in the way they left it.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class SessionState extends AbstractState {
		
    private Logger logger = LoggerFactory.getLogger(SessionState.class);
    
    @Override
    public Limit retrieveLimit() {
		
        String stateAttrValue = getWebContext().getParameter(getStateAttr());
        if ("true".equalsIgnoreCase(stateAttrValue)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The Limit is being retrieved from the users session.");
            }
            return (Limit) getWebContext().getSessionAttribute(getId());
        }
        
        stateAttrValue = (String)getWebContext().getRequestAttribute(getStateAttr());
        if ("true".equalsIgnoreCase(stateAttrValue)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The Limit is being retrieved from the users session.");
            }
            return (Limit) getWebContext().getSessionAttribute(getId());
        }

        return null;
    }

    @Override
    public void persistLimit(Limit limit) {
		
        if (logger.isDebugEnabled()) {
            logger.debug("The Limit is being persisted on the users session.");
        }
        getWebContext().setSessionAttribute(getId(), limit);
    }

    @Override
    public void setId(String id) {
		
        super.setId(id + "_LIMIT");
    }
}
