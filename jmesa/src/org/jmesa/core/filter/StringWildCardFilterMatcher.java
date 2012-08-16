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
package org.jmesa.core.filter;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

/**
 * Will do a string match based on wildcards.
 * 
 * @since 2.3.3
 * @author Oscar Perez
 */
public class StringWildCardFilterMatcher implements FilterMatcher {
		
    private static final String ANYCHARREGEXP = ".";
    private static final String ANYSTRINGREGEXP = ".*?";
    private static final String IGNORECASESFLAG = "(?i)";
    protected boolean ignoreCases;

    /**
     * Ignores cases by default
     */
    public StringWildCardFilterMatcher() {
		
        ignoreCases = true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean evaluate(Object itemValue, String filterValue) {
		
        if (ignoreCases) {
            itemValue = StringUtils.lowerCase(String.valueOf(itemValue));
        }

        Pattern filterPattern = createFilterPattern(filterValue);
        if (filterPattern == null) {
            return false;
        }

        return filterPattern.matcher(String.valueOf(itemValue)).matches();
    }

    /**
     * Creates a simple regexp pattern out of filterStr, replacing wildcards ? and * with
     * the matching regular expression components.
     *
     * @param filterStr The string to filter by.
     * @return the pattern to be filtered against
     */
    private Pattern createFilterPattern(String filterStr) {
		
        if (filterStr == null) {
            return null;
        }

        if (ignoreCases) {
            filterStr = filterStr.toLowerCase();
        }
        
//        String regFilter = (filterStr.replace(ANYCHAR, ANYCHARREGEXP)).replace(ANYSTRING, ANYSTRINGREGEXP);
        String regFilter = replaceAsterisk(filterStr);

        //for usability reasons, automatic wildcard to end of the word. The user can just filter
        //by beginning letters of the word without worrying about the wildcard
        regFilter += ANYSTRINGREGEXP;

        if (ignoreCases) {
            regFilter = IGNORECASESFLAG + regFilter;
        }

        Pattern result = null; 
        try {
            result = Pattern.compile(regFilter);
        } catch (PatternSyntaxException pse) {
            // Do nothing. We've encountered some bad string we can't compile.
        }
        
        return result; 
    }
    
    private String replaceAsterisk(String filterString) {
        
        StringBuilder sb = new StringBuilder();
        
        for(String s : filterString.split("\\*")) {
            sb.append(replaceQuestionMark(s));
            sb.append(ANYSTRINGREGEXP);
        }
        
        return sb.toString();
    }

    private String replaceQuestionMark(String filterString) {
        
        StringBuilder sb = new StringBuilder();
        
        for(String s : filterString.split("\\?")) {
            sb.append(Pattern.quote(s));
            sb.append(ANYCHARREGEXP);
        }
        
        return sb.toString();
    }
}
