package org.jmesa.facade.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.util.ItemUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.component.HtmlColumnsGenerator;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to generate columns on the fly for the tag library.
 * 
 * @since 2.2.1
 * @author Jeff Johnston
 */
public class HtmlColumnsTag extends SimpleTagSupport {

    private Logger logger = LoggerFactory.getLogger(HtmlColumnTag.class);

    private String htmlColumnsGenerator;

    private List<HtmlColumn> columns;

    public String getHtmlColumnsGenerator() {
        return htmlColumnsGenerator;
    }

    public void setHtmlColumnsGenerator(String htmlColumnsGenerator) {
        this.htmlColumnsGenerator = htmlColumnsGenerator;
    }

    @SuppressWarnings("unchecked")
    public Object getValue(String property) {
        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        String var = facadeTag.getVar();
        Object item = getJspContext().getAttribute(var);

        if (item == null) {
            return null;
        }

        return ItemUtils.getItemValue(item, property);
    }

    public void addColumns() {
        if (columns != null && columns.size() > 0) {
            return;
        }

        try {
            TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);

            HtmlColumnsGenerator htmlColumnsGenerator = (HtmlColumnsGenerator) Class.forName(getHtmlColumnsGenerator()).newInstance();
            SupportUtils.setCoreContext(htmlColumnsGenerator, facadeTag.getCoreContext());
            SupportUtils.setWebContext(htmlColumnsGenerator, facadeTag.getWebContext());
            this.columns = htmlColumnsGenerator.getColumns(facadeTag.getComponentFactory());

            HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
            HtmlRow row = rowTag.getRow();
            for (HtmlColumn column : columns) {
                row.addColumn(column);
            }
        } catch (Exception e) {
            logger.error("Could not create the autoGenerateColumns [" + getHtmlColumnsGenerator() + "]", e);
        }
    }

    @Override
    public void doTag() throws JspException, IOException {
        addColumns();

        HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();
        for (Column column : columns) {
            String property = ((HtmlColumn) column).getProperty();
            pageItem.put(property, getValue(property));
        }
    }
}