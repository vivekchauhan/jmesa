package org.jmesa.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.view.html.component.HtmlRow;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public class RowTag extends SimpleTagSupport {
    private boolean highlighter = true;
    private String onclick;
    private String onmouseover;
    private String onmouseout;

    private HtmlRow row;

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

    public HtmlRow getRow() {
        if (row != null) {
            return row;
        }

        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
        this.row = tableTag.getComponentFactory().createRow();
        tableTag.getTable().setRow(row);

        return row;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HtmlRow row = getRow();
        row.setHighlighter(isHighlighter());
        row.setOnclick(getOnclick());
        row.setOnmouseover(getOnmouseover());
        row.setOnmouseout(getOnmouseout());

        JspFragment body = getJspBody();
        if (body == null) {
            return;
        }

        body.invoke(null);
    }
}
