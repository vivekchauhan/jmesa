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
package org.jmesa.facade;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.message.Messages;
import org.jmesa.core.preference.Preferences;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;

/**
 * @since 2.1
 * @author Jeff Johnston
 */
public interface TableFacade {
    public void setExportTypes(HttpServletResponse response, String... exportTypes);

    public WebContext getWebContext();

    public void setWebContext(WebContext webContext);

    public Limit getLimit();

    public void setLimit(Limit limit);

    public RowSelect setRowSelect(int maxRows, int totalRows);

    public void setStateAttr(String stateAttr);

    public CoreContext getCoreContext();

    public void setCoreContext(CoreContext coreContext);

    public void performFilterAndSort(boolean performFilterAndSort);

    public void setMessages(Messages messages);

    public void setPreferences(Preferences preferences);

    public void addFilterMatcher(MatcherKey key, FilterMatcher matcher);
    
    public void addFilterMatcherMap(FilterMatcherMap filterMatcherMap);

    public void setItems(Collection<Object> items);

    public Table getTable();

    public void setTable(Table table);

    public Toolbar getToolbar();

    public void setToolbar(Toolbar toolbar);

    public void setMaxRowsIncrements(int... maxRowsIncrements);

    public View getView();

    public void setView(View view);

    public String render();
}
