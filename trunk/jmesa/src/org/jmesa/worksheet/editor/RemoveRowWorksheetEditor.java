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
package org.jmesa.worksheet.editor;

import org.jmesa.limit.Limit;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.HtmlConstants;
import org.jmesa.view.html.HtmlUtils;
import static org.jmesa.worksheet.WorksheetUtils.isRowRemoved;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class RemoveRowWorksheetEditor extends AbstractWorksheetEditor {

    private final static String REMOVE_WORKSHEET_ROW_ITEM = "remove_worksheet_row";
    
    public Object getValue(Object item, String property, int rowcount) {

        HtmlBuilder html = new HtmlBuilder();

        html.div().close();

        html.img();
        String imagesPath = HtmlUtils.imagesPath(getWebContext(), getCoreContext());
        String imageSrc = null;
        String imageTitle = null;
        
        if (isRowRemoved(getCoreContext().getWorksheet(), getColumn().getRow(), item)) {
            imageSrc = getCoreContext().getPreference(HtmlConstants.IMAGE_UNDO_REMOVE_WORKSHEET_ROW);
            imageTitle = getCoreContext().getMessage(HtmlConstants.TEXT_UNDO_REMOVE_WORKSHEET_ROW);
        } else {
            imageSrc = getCoreContext().getPreference(HtmlConstants.IMAGE_REMOVE_WORKSHEET_ROW);
            imageTitle = getCoreContext().getMessage(HtmlConstants.TEXT_REMOVE_WORKSHEET_ROW);
        }
        
        Limit limit = getCoreContext().getLimit();

    	html.src(imagesPath + imageSrc);
        html.onclick(getUniquePropertyJavaScript(item) + "jQuery.jmesa.setRemoveRowToWorksheet('" + limit.getId() 
                + "'," + UNIQUE_PROPERTY + ");" + getOnInvokeActionJavaScript(limit));
        html.style("border:0;cursor:pointer");
        html.alt(imageTitle);
        html.title(imageTitle);
        
        html.end();

        html.divEnd();

        return html.toString();
	}

    protected String getOnInvokeActionJavaScript(Limit limit) {
        String onInvokeAction = getCoreContext().getPreference(HtmlConstants.ON_INVOKE_ACTION);
        return onInvokeAction + "('" + limit.getId() + "', '" + REMOVE_WORKSHEET_ROW_ITEM + "')";
    }
}
