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

/**
 * Will do a string match based on wildcards.
 * 
 * @since 2.3.3
 * @author Oscar Perez
 */
public class StringWildCardFilterMatcher implements FilterMatcher {
		
    private static final String ASTERISK = "*";
    private static final String QUESTION_MARK = "?";
    private static final String ANYCHARREGEXP = ".";
    private static final String ANYSTRINGREGEXP = ".*?";
    protected boolean ignoreCases;

    /**
     * Ignores cases by default
     */
    public StringWildCardFilterMatcher() {
		
        ignoreCases = true;
    }

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {
        
        if (filterValue == null) {
            return false;
        }
		
        if (ignoreCases) {
            filterValue = filterValue.toLowerCase();
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
		
        String regFilter = replaceAsterisk(filterStr);

        //for usability reasons, automatic wildcard to end of the word. The user can just filter
        //by beginning letters of the word without worrying about the wildcard
        regFilter += ANYSTRINGREGEXP;

        Pattern result = null; 
        try {
            result = ignoreCases ? Pattern.compile(regFilter, Pattern.CASE_INSENSITIVE) : Pattern.compile(regFilter);
        } catch (PatternSyntaxException pse) {
            // Do nothing. We've encountered some bad string we can't compile.
        }
        
        return result; 
    }
    
    /**
     * Replace "*" with the regex equivalent.
     * 
     * @param filterString
     * @return
     */
    private String replaceAsterisk(String filterString) {
        
        StringBuilder sb = new StringBuilder();

        if (filterString.contains(ASTERISK)) {
            int i = 0; 
            int index = filterString.indexOf(ASTERISK, i);
            String subStr;
            while (index > -1) {
                subStr = filterString.substring(i, index);
                
                subStr = replaceQuestionMark(subStr);
                sb.append(subStr);
                sb.append(ANYSTRINGREGEXP);
                
                i = index+1;
                index = filterString.indexOf(ASTERISK, i); 
            } 
            if (i < filterString.length()) {
                subStr = filterString.substring(i);
                subStr = replaceQuestionMark(subStr);
                sb.append(subStr);
                sb.append(ANYSTRINGREGEXP);
            }
        } else {
            sb.append(replaceQuestionMark(filterString));
        }
        
        return sb.toString();
    }

    /**
     * Replace "?" with the regex equivalent.
     * 
     * @param filterString
     * @return
     */
    private String replaceQuestionMark(String filterString) {
        
        StringBuilder sb = new StringBuilder();

        if (filterString.contains(QUESTION_MARK)) {
            int i = 0; 
            int index = filterString.indexOf(QUESTION_MARK, i);
            String subStr;
            while (index > -1) {
                index = filterString.indexOf(QUESTION_MARK, i);
                subStr = filterString.substring(i, index);
                sb.append(Pattern.quote(subStr));
                sb.append(ANYCHARREGEXP);
                i = index+1;
                index = filterString.indexOf(QUESTION_MARK, i);
            }
            if (i < filterString.length()) {
                subStr = filterString.substring(i);
                sb.append(Pattern.quote(subStr));
            }
        } else {
            sb.append(Pattern.quote(filterString));
        }
        
        return sb.toString();
    }
}
