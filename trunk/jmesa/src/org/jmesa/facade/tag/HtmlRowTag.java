package org.jmesa.facade.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.view.html.component.HtmlRow;

/**
 * Represents an HtmlRow.
 * 
 * @since 2.1
 * @author Jeff Johnston
 */
public class HtmlRowTag extends SimpleTagSupport {
    private boolean highlighter = true;
    private String onclick;
    private String onmouseover;
    private String onmouseout;

    private HtmlRow row;
    private Map<String, Object> pageItem;

    public boolean isHighlighter() {
        return highlighter;
    }

    public void setHighlighter(boolean highlighter) {
        this.highlighter = highlighter;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * The row to use. If the row does not exist then one will be created.
     */
    public HtmlRow getRow() {
        if (row != null) {
            return row;
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        this.row = facadeTag.getComponentFactory().createRow();
        row.setHighlighter(isHighlighter());
        row.setOnclick(getOnclick());
        row.setOnmouseover(getOnmouseover());
        row.setOnmouseout(getOnmouseout());

        HtmlTableTag tableTag = (HtmlTableTag) findAncestorWithClass(this, HtmlTableTag.class);
        tableTag.getTable().setRow(row);

        return row;
    }

    /**
     * @return The current page item.
     */
    public Map<String, Object> getPageItem() {
        return pageItem;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
            throw new IllegalStateException("You need to wrap the columns in the row tag.");
        }

        TableFacadeTag facadeTag = (TableFacadeTag) findAncestorWithClass(this, TableFacadeTag.class);
        Collection<Object> pageItems = facadeTag.getPageItems();
        this.pageItem = new HashMap<String, Object>();
        pageItems.add(pageItem);
        
        getRow();

        body.invoke(null);
    }
}
