package org.jmesaweb.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

/**
 * An interface to bridge Groovy and Java code.
 * 
 * @author Jeff Johnston
 */
public interface HtmlTableTemplate {
    public String build(Collection<Object> items, HttpServletRequest request);
}
