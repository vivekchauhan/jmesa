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
package org.jmesa.limit;

/**
 * <p>
 * The actions that are used to figure how the user interacted with the table.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public enum Action {
    FILTER, SORT, CLEAR, PAGE, MAX_ROWS, EXPORT;

    public String toParam() {
        switch (this) {
        case FILTER:
            return "f_";
        case SORT:
            return "s_";
        case CLEAR:
            return "c_";
        case PAGE:
            return "p_";
        case MAX_ROWS:
            return "mr_";
        case EXPORT:
            return "e_";
        default:
            return "";
        }
    }

    public static Action valueOfParam(String param) {
        for (Action action : Action.values()) {
            if (action.toParam().equals(param)) {
                return action;
            }
        }

        return null;
    }
}
