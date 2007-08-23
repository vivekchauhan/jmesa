package org.jmesa.facade.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an HtmlRow.
 * 
 * @since 2.1
 * @author jeff jie
 */
public class HtmlRowTag extends SimpleTagSupport {
    private Logger logger = LoggerFactory.getLogger(HtmlRowTag.class);

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
     * Get the row Onclick RowEvent object.
     */
    protected RowEvent getRowOnclick() {
        if (StringUtils.isBlank(getOnclick())) {
            return null;
        }

        try {
            Object obj = Class.forName(getOnclick()).newInstance();
            if (obj instanceof RowEvent) {
                return (RowEvent) obj;
            }
        } catch (Exception e) {
            logger.error("Could not create the onclick RowEvent [" + getOnclick() + "]", e);
        }

        return null;
    }

    /**
     * Get the row Onmouseover RowEvent object.
     */
    protected RowEvent getRowOnmouseover() {
        if (StringUtils.isBlank(getOnmouseover())) {
            return null;
        }

        try {
            Object obj = Class.forName(getOnmouseover()).newInstance();
            if (obj instanceof RowEvent) {
                return (RowEvent) obj;
            }
        } catch (Exception e) {
            logger.error("Could not create the onmouseover RowEvent [" + getOnmouseover() + "]", e);
        }

        return null;
    }

    /**
     * Get the row Onmouseout RowEvent object.
     */
    protected RowEvent getRowOnmouseout() {
        if (StringUtils.isBlank(getOnmouseout())) {
            return null;
        }

        try {
            Object obj = Class.forName(getOnmouseout()).newInstance();
            if (obj instanceof RowEvent) {
                return (RowEvent) obj;
            }
        } catch (Exception e) {
            logger.error("Could not create the onmouseout RowEvent [" + getOnmouseout() + "]", e);
        }

        return null;
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
        row.setOnclick(getRowOnclick());
        row.setOnmouseover(getRowOnmouseover());
        row.setOnmouseout(getRowOnmouseout());

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

        String var = facadeTag.getVar();
        WebContext webContext = facadeTag.getWebContext();
        Object bean = webContext.getPageAttribute(var);
        pageItem.put(var, bean);

        getRow();

        body.invoke(null);
    }
}
