/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmesa.view.html;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Will build up an Html String piece by piece.
 * 
 * Note: unless otherwise noted if the parameter used for any of the methods is
 * null or empty then that html element will not be appended. The advantage of
 * this is you do not have to do any null or empty string checking to use this
 * class.
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlBuilder {
		
    private StringBuilder builder;

    /**
     * Default constructor using a StringWriter, which is really just a
     * StringBuilder.
     */
    public HtmlBuilder() {
		
        this.builder = new StringBuilder();
    }

    /**
     * Write out the content to the internal writer.
     */
    public HtmlBuilder append(Object text) {
		
        if (text != null) {
            builder.append(text);
        }

        return this;
    }

    /**
     * The length of the internal Writer.
     */
    public int length() {
		
        return builder.toString().length();
    }

    /**
     * <p>
     * Append tabs [\t] and newlines [\n].
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces \t to put in.
     * @param newlines
     *            The number of newlines \n to put in.
     */
    public HtmlBuilder format(int tabs, int newlines) {
		
        tabs(tabs);
        newlines(newlines);

        return this;
    }

    /**
     * <p>
     * Append tabs.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder tabs(int tabs) {
		
        for (int i = 0; i < tabs; i++) {
            tab();
        }

        return this;
    }

    /**
     * <p>
     * Append newlines [\n].
     * </p>
     * 
     * @param newlines
     *            The number of newlines \n to put in.
     */
    public HtmlBuilder newlines(int newlines) {
		
        for (int i = 0; i < newlines; i++) {
            newline();
        }

        return this;
    }

    /**
     * <p>
     * Append tab [\t].
     * </p>
     */
    public HtmlBuilder tab() {
		
        append("\t");

        return this;
    }

    /**
     * <p>
     * Append newline [\n].
     * </p>
     */
    public HtmlBuilder newline() {
		
        append("\n");

        return this;
    }

    /**
     * <p>
     * Close the element [>]
     * </p>
     */
    public HtmlBuilder close() {
		
        append(">");

        return this;
    }

    /**
     * <p>
     * Close the element with a slash to comply with xhtml [/>]
     * </p>
     */
    public HtmlBuilder end() {
		
        append("/>");

        return this;
    }

    /**
     * <p>
     * The start of the table element [<table].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the table.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder table(int tabs) {
		
        newline();
        tabs(tabs);
        append("<table");

        return this;
    }

    /**
     * <p>
     * The close tag of the table element [</table>].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the table ends.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder tableEnd(int tabs) {
		
        newline();
        tabs(tabs);
        append("</table>");

        return this;
    }

    /**
     * <p>
     * The start of the button element [<button].
     * </p>
     */
    public HtmlBuilder button() {
		
        append("<button");
        return this;
    }

    /**
     * <p>
     * The close tag of the button element [</button>].
     * </p>
     */
    public HtmlBuilder buttonEnd() {
		
        append("</button>");

        return this;
    }

    /**
     * <p>
     * The start of the tr element [<tr].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the tr.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder tr(int tabs) {
		
        newline();
        tabs(tabs);
        append("<tr");

        return this;
    }

    /**
     * <p>
     * The close tag of the tr element [</tr>].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the tr ends.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder trEnd(int tabs) {
		
        newline();
        tabs(tabs);
        append("</tr>");

        return this;
    }

    /**
     * <p>
     * The start of the th element [<th].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the th.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder th(int tabs) {
		
        newline();
        tabs(tabs);
        append("<th");

        return this;
    }

    /**
     * <p>
     * The close tag of the th element [</th>].
     * </p>
     */
    public HtmlBuilder thEnd() {
		
        append("</th>");

        return this;
    }

    /**
     * <p>
     * The start of the td element [<td].
     * </p>
     */
    public HtmlBuilder td() {
		
        newline();
        append("<td");

        return this;
    }
    
    /**
     * <p>
     * The start of the td element [<td].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the td.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder td(int tabs) {
        
        newline();
        tabs(tabs);
        append("<td");
        
        return this;
    }

    /**
     * <p>
     * The close tag of the td element [</td>].
     * </p>
     */
    public HtmlBuilder tdEnd() {
		
        append("</td>");

        return this;
    }

    /**
     * <p>
     * The start of the input element [<input].
     * </p>
     */
    public HtmlBuilder input() {
		
        append("<input");

        return this;
    }

    /**
     * <p>
     * The type attribute [type=].
     * </p>
     */
    public HtmlBuilder type(String type) {
		
        if (StringUtils.isNotBlank(type)) {
            append(" type=\"").append(type).append("\" ");
        }

        return this;
    }

    public HtmlBuilder link() {
		
        append("<link");

        return this;
    }

    public HtmlBuilder rel(String rel) {
		
        if (StringUtils.isNotBlank(rel)) {
            append(" rel=\"").append(rel).append("\" ");
        }

        return this;
    }

    public HtmlBuilder media(String media) {
		
        if (StringUtils.isNotBlank(media)) {
            append(" media=\"").append(media).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The name attribute [name=].
     * </p>
     */
    public HtmlBuilder name(String name) {
		
        if (StringUtils.isNotBlank(name)) {
            append(" name=\"").append(name).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The value attribute [value=].
     * </p>
     * 
     * <p>
     * If the value parameter is null or empty then will append a empty value
     * element.
     * </p>
     */
    public HtmlBuilder value(String value) {
		
        if (StringUtils.isNotBlank(value)) {
            append(" value=\"").append(value).append("\" ");
        } else {
            append(" value=\"").append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The start of the select element [<select].
     * </p>
     */
    public HtmlBuilder select() {
		
        append("<select");

        return this;
    }

    /**
     * <p>
     * The close tag of the select element [</select>].
     * </p>
     */
    public HtmlBuilder selectEnd() {
		
        append("</select>");

        return this;
    }

    /**
     * <p>
     * The start of the option element [<option].
     * </p>
     */
    public HtmlBuilder option() {
		
        append("<option");

        return this;
    }

    /**
     * <p>
     * The close tag of the option element [</option>].
     * </p>
     */
    public HtmlBuilder optionEnd() {
		
        append("</option>");

        return this;
    }

    /**
     * <p>
     * The start of the form element [<form].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] before the form.
     * </p>
     */
    public HtmlBuilder form() {
		
        newline();
        append("<form");

        return this;
    }

    /**
     * <p>
     * The close tag of the form element [</form>].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] before the end.
     * </p>
     */
    public HtmlBuilder formEnd() {
		
        newline();
        append("</form>");

        return this;
    }

    /**
     * <p>
     * The title attribute [title=].
     * </p>
     */
    public HtmlBuilder title(String title) {
		
        if (StringUtils.isNotBlank(title)) {
            append(" title=\"").append(title).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The action attribute [action=].
     * </p>
     */
    public HtmlBuilder action(String action) {
		
        append(" action=\"");
        if (StringUtils.isNotBlank(action)) {
            append(action);
        }
        append("\" ");

        return this;
    }

    /**
     * <p>
     * The method attribute [method=].
     * </p>
     */
    public HtmlBuilder method(String method) {
		
        if (StringUtils.isNotBlank(method)) {
            append(" method=\"").append(method).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The enctype attribute [enctype=].
     * </p>
     */
    public HtmlBuilder enctype(String enctype) {
		
        if (StringUtils.isNotBlank(enctype)) {
            append(" enctype=\"").append(enctype).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onchange attribute [onchange=].
     * </p>
     */
    public HtmlBuilder onchange(String onchange) {
		
        if (StringUtils.isNotBlank(onchange)) {
            append(" onchange=\"").append(onchange).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onsubmit attribute [onsubmit=].
     * </p>
     */
    public HtmlBuilder onsubmit(String onsubmit) {
		
        if (StringUtils.isNotBlank(onsubmit)) {
            append(" onsubmit=\"").append(onsubmit).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onclick attribute [onclick=].
     * </p>
     */
    public HtmlBuilder onclick(String onclick) {
		
        if (StringUtils.isNotBlank(onclick)) {
            append(" onclick=\"").append(onclick).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onmouseover attribute [onmouseover=].
     * </p>
     */
    public HtmlBuilder onmouseover(String onmouseover) {
		
        if (StringUtils.isNotBlank(onmouseover)) {
            append(" onmouseover=\"").append(onmouseover).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onmouseout attribute [onmouseout=].
     * </p>
     */
    public HtmlBuilder onmouseout(String onmouseout) {
		
        if (StringUtils.isNotBlank(onmouseout)) {
            append(" onmouseout=\"").append(onmouseout).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onkeypress attribute [onkeypress=].
     * </p>
     */
    public HtmlBuilder onkeypress(String onkeypress) {
		
        if (StringUtils.isNotBlank(onkeypress)) {
            append(" onkeypress=\"").append(onkeypress).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The onkeyup attribute [onkeyup=].
     * </p>
     */
    public HtmlBuilder onkeyup(String onkeyup) {
		
        if (StringUtils.isNotBlank(onkeyup)) {
            append(" onkeyup=\"").append(onkeyup).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The id attribute [id=].
     * </p>
     */
    public HtmlBuilder id(String id) {
		
        if (StringUtils.isNotBlank(id)) {
            append(" id=\"").append(id).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The class attribute [class=].
     * </p>
     */
    public HtmlBuilder styleClass(String styleClass) {
		
        if (StringUtils.isNotBlank(styleClass)) {
            append(" class=\"").append(styleClass).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The style attribute [style=].
     * </p>
     */
    public HtmlBuilder style(String style) {
		
        if (StringUtils.isNotBlank(style)) {
            append(" style=\"").append(style).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The width attribute [width=].
     * </p>
     */
    public HtmlBuilder width(String width) {
		
        if (StringUtils.isNotBlank(width)) {
            append(" width=\"").append(width).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The align attribute [align=].
     * </p>
     */
    public HtmlBuilder align(String align) {
		
        if (StringUtils.isNotBlank(align)) {
            append(" align=\"").append(align).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The valign attribute [valign=].
     * </p>
     */
    public HtmlBuilder valign(String valign) {
		
        if (StringUtils.isNotBlank(valign)) {
            append(" valign=\"").append(valign).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The border attribute [border=].
     * </p>
     */
    public HtmlBuilder border(String border) {
		
        if (StringUtils.isNotBlank(border)) {
            append(" border=\"").append(border).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The cellpadding attribute [cellpadding=].
     * </p>
     */
    public HtmlBuilder cellpadding(String cellPadding) {
		
        if (StringUtils.isNotBlank(cellPadding)) {
            append(" cellpadding=\"").append(cellPadding).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The cellspacing attribute [cellspacing=].
     * </p>
     */
    public HtmlBuilder cellspacing(String cellSpacing) {
		
        if (StringUtils.isNotBlank(cellSpacing)) {
            append(" cellspacing=\"").append(cellSpacing).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The colspan attribute [colspan=].
     * </p>
     */
    public HtmlBuilder colspan(String colspan) {
		
        if (StringUtils.isNotBlank(colspan)) {
            append(" colspan=\"").append(colspan).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The rowspan attribute [rowspan=].
     * </p>
     */
    public HtmlBuilder rowspan(String rowspan) {
		
        if (StringUtils.isNotBlank(rowspan)) {
            append(" rowspan=\"").append(rowspan).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The size attribute [size=].
     * </p>
     */
    public HtmlBuilder size(String size) {
        if (StringUtils.isNotBlank(size)) {
            append(" size=\"").append(size).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The start of the span element [<span].
     * </p>
     */
    public HtmlBuilder span() {
		
        append("<span");

        return this;
    }

    /**
     * <p>
     * The close tag of the span element [</span>].
     * </p>
     */
    public HtmlBuilder spanEnd() {
		
        append("</span>");

        return this;
    }

    /**
     * <p>
     * The start of the div element [<div].
     * </p>
     */
    public HtmlBuilder div() {
		
        append("<div");

        return this;
    }

    /**
     * <p>
     * The start of the div element [<div].
     * </p>
     */
    public HtmlBuilder div(int tabs) {

        newline();
        tabs(tabs);
        append("<div");

        return this;
    }

    /**
     * <p>
     * The close tag of the div element [</div>].
     * </p>
     */
    public HtmlBuilder divEnd() {
		
        append("</div>");

        return this;
    }

    /**
     * <p>
     * The close tag of the div element [</div>].
     * </p>
     */
    public HtmlBuilder divEnd(int tabs) {

        newline();
        tabs(tabs);
        append("</div>");

        return this;
    }

    /**
     * <p>
     * A URL parameter name/value [name=value]
     * </p>
     */
    public HtmlBuilder param(String name, String value) {
		
        append(name);
        equals();
        append(value);

        return this;
    }

    /**
     * <p>
     * The start of the a element attribute [<a].
     * </p>
     */
    public HtmlBuilder a() {
		
        append("<a");

        return this;
    }

    /**
     * <p>
     * The a element attribute with the href attribute [<a href=].
     * </p>
     * 
     * @param url The web url string.
     * @param displayText The text to display for the url.
     */
    public HtmlBuilder ahref(String url, String displayText) {
		
        return ahref(url, displayText, null);
    }

    /**
     * <p>
     * The a element attribute with the href attribute [<a href=].
     * </p>
     * @param url The web url string.
     * @param displayText The text to display for the url.
     * @param params A Map of name/value pair of parameters.
     */
    public HtmlBuilder ahref(String url, String displayText, Map<String, String> params) {
		
        StringBuilder urlBuilder = new StringBuilder(url);
        if (params != null && params.size() > 0) {
            boolean firstRow = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (firstRow) {
                    urlBuilder.append("?");
                } else {
                    urlBuilder.append("&amp;");
                }
                String key = entry.getKey();
                String val = entry.getValue();
                try {
                    key = URLEncoder.encode(key, "UTF-8");
                    val = URLEncoder.encode(val, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                urlBuilder.append(key).append("=").append(val);
                firstRow = false;
            }
        }
        a().href().quote().append(urlBuilder.toString()).quote().close().append(displayText).aEnd();
        return this;
    }

    public HtmlBuilder href(String href) {
		
        if (StringUtils.isNotBlank(href)) {
            append(" href=\"").append(href).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The href attribute [ href= ].
     * </p>
     *  
     */
    public HtmlBuilder href() {
		
        append(" href=");

        return this;
    }
    
    /**
     * <p>
     * The hr attribute [ hr ].
     * </p>
     *  
     */
    public HtmlBuilder hr() {
        
        append("<hr");

        return this;
    }

    /**
     * <p>
     * The end tag of the a element [ </a> ].
     * </p>
     */
    public HtmlBuilder aEnd() {
		
        append("</a>");

        return this;
    }

    /**
     * <p>
     * The bold element [<b>].
     * </p>
     */
    public HtmlBuilder bold() {
		
        append("<b>");

        return this;
    }

    /**
     * <p>
     * The close tag of the bold element [</b>].
     * </p>
     */
    public HtmlBuilder boldEnd() {
		
        append("</b>");

        return this;
    }

    /**
     * <p>
     * A single quote ["].
     * </p>
     */
    public HtmlBuilder quote() {
		
        append("\"");

        return this;
    }

    /**
     * <p>
     * A single question mark [?].
     * </p>
     */
    public HtmlBuilder question() {
		
        append("?");

        return this;
    }

    /**
     * <p>
     * A single equals [=].
     * </p>
     */
    public HtmlBuilder equals() {
		
        append("=");

        return this;
    }

    /**
     * <p>
     * A single ampersand [&].
     * </p>
     */
    public HtmlBuilder ampersand() {
		
        append("&");

        return this;
    }

    /**
     * <p>
     * The start of the img element [<img].
     * </p>
     */
    public HtmlBuilder img() {
		
        append("<img");

        return this;
    }

    /**
     * <p>
     * The src attribute [src=].
     * </p>
     */
    public HtmlBuilder src(String src) {
		
        if (StringUtils.isNotBlank(src)) {
            append(" src=\"").append(src).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The alt attribute [alt=].
     * </p>
     */
    public HtmlBuilder alt(String alt) {
		
        if (StringUtils.isNotBlank(alt)) {
            append(" alt=\"").append(alt).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The start of the textarea element [<textarea].
     * </p>
     */
    public HtmlBuilder textarea() {
		
        append("<textarea");

        return this;
    }

    /**
     * <p>
     * The close tag of the textarea element [</textarea>].
     * </p>
     */
    public HtmlBuilder textareaEnd() {
		
        append("</textarea>");

        return this;
    }

    /**
     * <p>
     * The cols attribute [cols=].
     * </p>
     */
    public HtmlBuilder cols(String cols) {
		
        if (StringUtils.isNotBlank(cols)) {
            append(" cols=\"").append(cols).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The rows attribute [rows=].
     * </p>
     */
    public HtmlBuilder rows(String rows) {
		
        if (StringUtils.isNotBlank(rows)) {
            append(" rows=\"").append(rows).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The checked attribute [checked="checked"].
     * </p>
     */
    public HtmlBuilder checked() {
		
        append(" checked=\"checked\"");

        return this;
    }

    /**
     * <p>
     * The selected attribute [selected="selected"].
     * </p>
     */
    public HtmlBuilder selected() {
		
        append(" selected=\"selected\"");

        return this;
    }

    /**
     * <p>
     * The readonly attribute [readonly="readonly"].
     * </p>
     */
    public HtmlBuilder readonly() {
		
        append(" readonly=\"readonly\"");

        return this;
    }

    /**
     * <p>
     * The non-breaking space [&nbsp;].
     * </p>
     */
    public HtmlBuilder nbsp() {
		
        append("&#160;");

        return this;
    }

    /**
     * <p>
     * The comment [<!-- -->].
     * </p>
     */
    public HtmlBuilder comment(String comment) {
		
        if (StringUtils.isNotBlank(comment)) {
            append(" <!-- ").append(comment).append(" -->");
        }

        return this;
    }

    /**
     * <p>
     * The ul element [
     * <ul>].
     * </p>
     */
    public HtmlBuilder ul() {
		
        append("<ul");

        return this;
    }

    /**
     * <p>
     * The close tag of the ul element [
     * </ul>].
     * </p>
     */
    public HtmlBuilder ulEnd() {
		
        append("</ul>");

        return this;
    }

    /**
     * <p>
     * The li element [
     * <li> ].
     * </p>
     */
    public HtmlBuilder li() {
		
        append("<li");

        return this;
    }

    /**
     * <p>
     * The li element [
     * <li> ].
     * </p>
     */
    public HtmlBuilder liEnd() {
		
        append("</li>");

        return this;
    }

    /**
     * <p>
     * The br element [<br/>].
     * </p>
     */
    public HtmlBuilder br() {
		
        append("<br/>");

        return this;
    }

    /**
     * <p>
     * The disabled attribute [disabled="disabled"].
     * </p>
     */
    public HtmlBuilder disabled() {
		
        append(" disabled=\"disabled\" ");

        return this;
    }

    /**
     * <p>
     * The nowrap attribute [nowrap="nowrap"].
     * </p>
     */
    public HtmlBuilder nowrap() {
		
        append(" nowrap=\"nowrap\" ");

        return this;
    }

    /**
     * <p>
     * The maxlength attribute [maxlength=].
     * </p>
     */
    public HtmlBuilder maxlength(String maxlength) {
		
        if (StringUtils.isNotBlank(maxlength)) {
            append(" maxlength=\"").append(maxlength).append("\" ");
        }

        return this;
    }

    /**
     * <p>
     * The start of the tbody element [<tbody].
     * </p>
     */
    public HtmlBuilder tbody(int tabs) {
		
        newline();
        tabs(tabs);
        append("<tbody");

        return this;
    }

    /**
     * <p>
     * The end tag of the tbody element [</tbody>].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the </tbody>.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder tbodyEnd(int tabs) {
		
        newline();
        tabs(tabs);
        append("</tbody>");

        return this;
    }

    /**
     * <p>
     * The start of the thead element [<thead].
     * </p>
     */
    public HtmlBuilder thead(int tabs) {
		
        newline();
        tabs(tabs);
        append("<thead");

        return this;
    }

    /**
     * <p>
     * The end tag of the thead element [</thead>].
     * </p>
     * 
     * <p>
     * Also appends a newline [\n] and the specified number of tabs [\t] before
     * the </thead>.
     * </p>
     * 
     * @param tabs
     *            The number of tab spaces [\t] to put in.
     */
    public HtmlBuilder theadEnd(int tabs) {
		
        newline();
        tabs(tabs);
        append("</thead>");

        return this;
    }

    /**
     * <p>
     * The start of the p element [<p].
     * </p>
     */
    public HtmlBuilder p() {
		
        append("<p");

        return this;
    }

    /**
     * <p>
     * The close tag of the p element [
     * </p>].
     * </p>
     */
    public HtmlBuilder pEnd() {
		
        append("</p>");

        return this;
    }

    /**
     * <p>
     * The start of the h1 element [<h1].
     * </p>
     */
    public HtmlBuilder h1() {
		
        append("<h1");

        return this;
    }

    /**
     * <p>
     * The close tag of the h1 element [</h1>].
     * </p>
     */
    public HtmlBuilder h1End() {
		
        append("</h1>");

        return this;
    }

    /**
     * <p>
     * The start of the h2 element [<h2].
     * </p>
     */
    public HtmlBuilder h2() {
		
        append("<h2");

        return this;
    }

    /**
     * <p>
     * The close tag of the h2 element [</h2>].
     * </p>
     */
    public HtmlBuilder h2End() {
		
        append("</h2>");

        return this;
    }

    /**
     * <p>
     * The start of the h3 element [<h3].
     * </p>
     */
    public HtmlBuilder h3() {
		
        append("<h3");

        return this;
    }

    /**
     * <p>
     * The close tag of the h3 element [</h3>].
     * </p>
     */
    public HtmlBuilder h3End() {
		
        append("</h3>");

        return this;
    }

    /**
     * <p>
     * The start of the h4 element [<h4].
     * </p>
     */
    public HtmlBuilder h4() {
		
        append("<h4");

        return this;
    }

    /**
     * <p>
     * The close tag of the h4 element [</h4>].
     * </p>
     */
    public HtmlBuilder h4End() {
		
        append("</h4>");

        return this;
    }

    /**
     * <p>
     * The start of the h5 element [<h5].
     * </p>
     */
    public HtmlBuilder h5() {
		
        append("<h5");

        return this;
    }

    /**
     * <p>
     * The close tag of the h5 element [</h5>].
     * </p>
     */
    public HtmlBuilder h5End() {
		
        append("</h5>");

        return this;
    }

    public HtmlBuilder script() {
		
        append("<script");

        return this;
    }

    public HtmlBuilder scriptEnd() {
		
        append("</script>");

        return this;
    }

    public HtmlBuilder semicolon() {
		
        append(";");

        return this;
    }

    public HtmlBuilder caption() {
		
        append("<caption");

        return this;
    }

    public HtmlBuilder captionEnd() {
		
        append("</caption>");

        return this;
    }

    public HtmlBuilder html() {
		
        append("<html");

        return this;
    }

    public HtmlBuilder htmlEnd() {
		
        append("</html>");

        return this;
    }

    public HtmlBuilder body() {
		
        append("<body");

        return this;
    }

    public HtmlBuilder bodyEnd() {
		
        append("</body>");

        return this;
    }

    public HtmlBuilder head() {
		
        append("<head");

        return this;
    }

    public HtmlBuilder headEnd() {
		
        append("</head>");

        return this;
    }

    public HtmlBuilder style() {
		
        append("<style");

        return this;
    }

    public HtmlBuilder styleEnd() {
		
        append("</style>");

        return this;
    }
    
    public HtmlBuilder dl() {
		
        append("<dl");
        
        return this;
    }
    
    public HtmlBuilder dlEnd() {
		
        append("</dl>");
        
        return this;
    }

    public HtmlBuilder dd() {
		
        append("<dd");
        
        return this;
    }
    
    public HtmlBuilder ddEnd() {
		
        append("</dd>");
        
        return this;
    }
    
    public HtmlBuilder dt() {
		
        append("<dt");
        
        return this;
    }
    
    public HtmlBuilder dtEnd() {
		
        append("</dt>");
        
        return this;
    }
    
    public HtmlBuilder label() {
		
        append("<label");
        
        return this;
    }
    
    public HtmlBuilder labelEnd() {
		
        append("</label>");
        
        return this;
    }
    
    public HtmlBuilder forAttr(String forAttr) {
		
        if (StringUtils.isNotBlank(forAttr)) {
            append(" for=\"").append(forAttr).append("\" ");
        }
        
        return this;
    }
    
    @Override
    public String toString() {
		
        return builder.toString();
    }
}
