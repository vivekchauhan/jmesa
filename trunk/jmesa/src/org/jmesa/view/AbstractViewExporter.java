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
package org.jmesa.view;

import java.nio.charset.Charset;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextSupport;
import org.jmesa.util.ExportUtils;
import static org.jmesa.view.ExportConstants.ENCODING;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public abstract class AbstractViewExporter implements ViewExporter, CoreContextSupport {
		
    private View view;
    private CoreContext coreContext;
    private HttpServletResponse response;
    private String fileName;

    public AbstractViewExporter(View view, CoreContext coreContext, HttpServletResponse response) {
		
        this(view, coreContext, response, null);
    }

    public AbstractViewExporter(View view, CoreContext coreContext, HttpServletResponse response, String fileName) {
		
        this.view = view;
        this.coreContext = coreContext;
        this.response = response;
        this.fileName = fileName;
        if (fileName == null) {
            this.fileName = ExportUtils.exportFileName(view, getExtensionName());
        }
    }

    public void responseHeaders(HttpServletResponse response)
            throws Exception {
        response.setContentType(getContextType());
        String encoding = coreContext.getPreference(ENCODING);
        if (encoding == null) {
            encoding = Charset.defaultCharset().name();
        }
        String fn = new String(fileName.getBytes(encoding), encoding);
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
    }

    public View getView() {
		
        return view;
    }

    public void setView(View view) {
		
        this.view = view;
    }

    public CoreContext getCoreContext() {
		
        return coreContext;
    }

    public void setCoreContext(CoreContext coreContext) {
		
        this.coreContext = coreContext;
    }

    protected HttpServletResponse getResponse() {
		
        return response;
    }

    public abstract String getContextType();

    public abstract String getExtensionName();
}
