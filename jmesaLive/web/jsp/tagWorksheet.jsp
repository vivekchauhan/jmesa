<!-- Header image and css/js will be included by sitemesh decorator (/WEB-INF/decorators.xml)-->

<%@ page import="
org.jmesa.model.TableModelUtils,
org.jmesa.web.HttpServletRequestWebContext,
org.jmesalive.controller.WorksheetSaverImpl,
org.jmesalive.dao.PresidentDao
"%>

<%
   // id, which is provided in jmesa:TableModel tag
   String tableId = "tagWorksheet";
   PresidentDao presidentDao = new PresidentDao(new HttpServletRequestWebContext(request));

   WorksheetSaverImpl worksheetSaver = new WorksheetSaverImpl(presidentDao);
   TableModelUtils.saveWorksheet(tableId, request, worksheetSaver);

   // set the success / error messages after saving the worksheet
   request.setAttribute("saveResults", worksheetSaver.getSaveResults());

   // set the collection in the request for JMesa tag lib
   request.setAttribute("presidents", presidentDao.getPresidents());

   // set the object for added row
   request.setAttribute("newRecord", PresidentDao.getNewPresident());
%>


<html>
    <head>
        <title>JMesa-Taglib::Worksheet Example</title>
        <%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
    </head>
    
    <body>
        
        <strong class="large">Worksheet</strong>
        <h4 class="alt"> The worksheet JMesa example shows what an editable table looks like. (Uses the tag library)</h4>
        <hr/>

        <div class="span-24">
           <div class="span-11 colborder">
               <strong> Validations: </strong> <br/>
               1. Enter 'foo' in First Name or leave blank (Javascript validation). <br/>
               2. Enter invalid email in Email (Javascript validation). <br/>
               3. Leave Last Name blank (Javascript validation). <br/>
               4. Enter 'foo' in Career (API validation. Error will be shown after saving). <br/>
               Hover the mouse over the (red) cell to see the error message. <br/>
               Click 'Save Worksheet' to save and see list of erroneous rows!
           </div>

           <div class="span-12 last">
               <strong> Tips: </strong> <br/>
               1. Click any cell and press 'Tab / Shift+Tab' to navigate to next / previous cell. <br/>
               2. Hover a modified cell while pressing 'Ctrl' to display the original value. <br/>
               3. Click a modified cell and press 'Ctrl+Shift+z' to get the original value back. <br/>
               4. Click 'Clear Changes' to remove all the changes of worksheet. <br/>
           </div>
        </div>
        
        <hr class="space"/>

        <p>
            ${saveResults}
        </p>

        <!-- "remove" is a dummy column for the purpose of row deletion -->

        <form name="presidentsForm" id="presidentsForm" action="${pageContext.request.contextPath}/jsp/tagWorksheet.jsp">
            <!-- required to put "active" css class on hyperlink of current example (/decorators/main.jsp) -->
            <input type="hidden" name="type" value="<%=tableId%>"/>

            <jmesa:tableModel
               id="<%=tableId%>"
               items="${presidents}"
               maxRows="12"
               maxRowsIncrements="12,20,36"
               stateAttr="restore"
               var="bean"
               editable="true"
               addedRowObject="${newRecord}"
               >
               <jmesa:htmlTable 
                   caption="Presidents"
                   width="700px"
                   >
                   <jmesa:htmlRow uniqueProperty="id">     
                      <jmesa:htmlColumn property="remove" title="&nbsp;"
                         sortable="false"
                         filterable="false"
                         worksheetEditor="org.jmesa.worksheet.editor.RemoveRowWorksheetEditor"
                      />
                      <jmesa:htmlColumn property="selected" title="&nbsp;"
                         sortable="false"
                         filterable="false"
                         worksheetEditor="org.jmesa.worksheet.editor.CheckboxWorksheetEditor"
                      />
                      <jmesa:htmlColumn property="name.firstName" title="First Name"
                         worksheetValidation="required:true"
                         customWorksheetValidation="customFirstNameValidation:validateFirstName"
                         errorMessage="customFirstNameValidation:You cannot use foo as a value"
                      />
                      <jmesa:htmlColumn property="name.lastName" title="Last Name"
                         worksheetValidation="required:true"
                         errorMessage="required:Last name is required."
                      />
                      <jmesa:htmlColumn property="email"
                         worksheetValidation="email:true"
                      />
                      <jmesa:htmlColumn property="term"/>
                      <jmesa:htmlColumn property="career"/>
                      <jmesa:htmlColumn property="born"
                         editable="false"
                         pattern="MM/yyyy"
                         cellEditor="org.jmesa.view.editor.DateCellEditor"
                      />
                   </jmesa:htmlRow>
               </jmesa:htmlTable> 
            </jmesa:tableModel>
        </form>
        
        <hr class="space"/>
        
        <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }

            // custom validation
            function validateFirstName(val) {
               if (val == 'foo') {
                  return false;
               }

               return true;
            }
        </script>
    </body>
</html>
