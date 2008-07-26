package org.jmesaweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlColumnsGenerator;

public class TagHtmlColumnsGenerator extends AbstractContextSupport implements HtmlColumnsGenerator {
    public List getColumns(HtmlComponentFactory componentFactory) {
        List columns = new ArrayList();

        HtmlColumn firstName = componentFactory.createColumn("name.firstName");
        firstName.setTitle("First Name");
        columns.add(firstName);

        HtmlColumn lastName = componentFactory.createColumn("name.lastName");
        lastName.setTitle("Last Name");
        columns.add(lastName);

        HtmlColumn born = componentFactory.createColumn("born");
        columns.add(born);

        return columns;
    }
}