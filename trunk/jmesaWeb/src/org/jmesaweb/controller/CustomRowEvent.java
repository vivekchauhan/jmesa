package org.jmesaweb.controller;

import org.jmesa.view.html.event.MouseRowEvent;

public class CustomRowEvent extends MouseRowEvent {
    @Override
    public String execute(Object item, int rowcount) {
        String result = super.execute(item, rowcount);
        
        result += ";$(this).attr('title', 'more infor')";
        
        return result;
    }
}
