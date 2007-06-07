package org.jmesaweb.controller;

import java.util.Collection;

import org.jmesa.web.WebContext;

/**
 * An interface to bridge Groovy and Java code.
 * 
 * @author Jeff Johnston
 */
public interface HtmlTableTemplate {
    public String build(Collection items, WebContext webContext);
}
