package org.jmesa.facade.tag;

import static org.jmesa.facade.tag.TagUtils.createInstance;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.core.CoreContext;
import org.jmesa.util.ItemUtils;
import org.jmesa.util.SupportUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlColumnsGenerator;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.web.WebContext;

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

    private Object getValue(String property, String var) {
        Object item = getJspContext().getAttribute(var);

        if (item == null) {
            return null;
        }

        return ItemUtils.getItemValue(item, property);
    }

    /**
     * @return The list of columns generated on the fly.
     */
    private List<HtmlColumn> getColumns(HtmlComponentFactory factory, WebContext webContext, CoreContext coreContext) {
        HtmlColumnsGenerator htmlColumnsGenerator = (HtmlColumnsGenerator) createInstance(getHtmlColumnsGenerator());
        SupportUtils.setWebContext(htmlColumnsGenerator, webContext);
        SupportUtils.setCoreContext(htmlColumnsGenerator, coreContext);

        return htmlColumnsGenerator.getColumns(factory);
    }

    /**
     * Process the list of columns that are generated on the fly.
     */
    @Override
    public void doTag() {
        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        Collection<Map<String, Object>> pageItems = facadeTag.getPageItems();
        if (pageItems.size() == 1) {
            HtmlRow row = facadeTag.getTable().getRow();
            HtmlComponentFactory factory = facadeTag.getComponentFactory();
            WebContext webContext = facadeTag.getTableFacade().getWebContext();
            CoreContext coreContext = facadeTag.getTableFacade().getCoreContext();
            List<HtmlColumn> columns = getColumns(factory, webContext, coreContext);
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
                    String var = facadeTag.getVar();
                    pageItem.put(property, getValue(property, var));
                }
            }
        }
    }
}
