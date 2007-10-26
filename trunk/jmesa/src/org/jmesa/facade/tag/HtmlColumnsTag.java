package org.jmesa.facade.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.util.ItemUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlColumnsGenerator;
import org.jmesa.view.html.component.HtmlRow;

/**
 * Used to generate columns on the fly for the tag library.
 * 
 * @since 2.2.1
 * @author Jeff Johnston
 */
public class HtmlColumnsTag extends SimpleTagSupport {
    private String htmlColumnsGenerator;

    public String getHtmlColumnsGenerator() {
        return htmlColumnsGenerator;
    }

    public void setHtmlColumnsGenerator(String htmlColumnsGenerator) {
        this.htmlColumnsGenerator = htmlColumnsGenerator;
    }

    @SuppressWarnings("unchecked")
    private Object getValue(String property, TableFacadeTag facadeTag) {
        String var = facadeTag.getVar();
        Object item = getJspContext().getAttribute(var);

        if (item == null) {
            return null;
        }

        return ItemUtils.getItemValue(item, property);
    }

    /**
     * @return The list of columns generated on the fly.
     */
    private List<HtmlColumn> getColumns(TableFacadeTag facadeTag) {
        HtmlColumnsGenerator htmlColumnsGenerator = (HtmlColumnsGenerator) ClassUtils.createInstance(getHtmlColumnsGenerator());
        SupportUtils.setCoreContext(htmlColumnsGenerator, facadeTag.getCoreContext());
        SupportUtils.setWebContext(htmlColumnsGenerator, facadeTag.getWebContext());

        return htmlColumnsGenerator.getColumns(facadeTag.getComponentFactory());
    }

    /**
     * Process the list of columns that are generated on the fly.
     */
    @Override
    public void doTag() throws JspException, IOException {
        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        Collection<Object> pageItems = facadeTag.getPageItems();
        if (pageItems.size() == 1) {
            HtmlRow row = facadeTag.getTable().getRow();
            List<HtmlColumn> columns = getColumns(facadeTag);
            for (HtmlColumn column : columns) {
                column.setGeneratedOnTheFly(true);
                TagUtils.validateColumn(this, column.getProperty());
                row.addColumn(column);
            }
        }

        HtmlRowTag rowTag = (HtmlRowTag) findAncestorWithClass(this, HtmlRowTag.class);
        Map<String, Object> pageItem = rowTag.getPageItem();

        HtmlRow row = facadeTag.getTable().getRow();
        List<Column> columns = row.getColumns();
        for (Column column : columns) {
            HtmlColumn htmlColumn = (HtmlColumn) column;
            if (htmlColumn.isGeneratedOnTheFly()) {
                String property = htmlColumn.getProperty();
                if (property != null) {
                    pageItem.put(property, getValue(property, facadeTag));
                }
            }
        }
    }
}