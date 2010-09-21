<!-- Header image and css/js will be included by sitemesh decorator (/WEB-INF/decorators.xml)-->

<%@ page import="
org.jmesa.web.HttpServletRequestWebContext,
org.jmesalive.dao.PresidentDao,
org.jmesalive.domain.President
"%>

<%
   // set the collection in the request for JMesa tag lib
   PresidentDao presidentDao = new PresidentDao(new HttpServletRequestWebContext(request));
   java.util.Collection<President> presidents = presidentDao.getPresidents();
   request.setAttribute("presidents", presidents);
%>

<html>
    
    <head>
        <title>Basic JMesa Example</title>
        <%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
    </head>
    
    <body>
        
        <strong class="large">Basic</strong>
        <h4 class="alt"> The basic JMesa example that uses the tag library. </h4>
        <hr/>
        
        <div class="span-24">
           <div class="span-11 colborder">
               Also using the State feature (by passing parameter "restore=true" in request). You can see that
               everytime you come back to this page the table is sorted, filted, and paged the way
               that you left it.
           </div>

           <div class="span-12 last">
              <strong> Tips: </strong> <br/>
              1. Click search box above 'Career' column to see droplist having unique values.
           </div>
        </div>

        <hr class="space"/>

        <form name="presidentsForm" action="${pageContext.request.contextPath}/jsp/tagBasic.jsp">
            <!-- required to put "active" css class on hyperlink of current example (/decorators/main.jsp) -->
            <input type="hidden" name="type" value="tagBasic"/>
            
            <jmesa:tableModel
               id="tagBasic" 
               items="${presidents}"
               maxRows="12"
               maxRowsIncrements="12,20,36"
               stateAttr="restore"
               var="bean"
               >
               <jmesa:htmlTable 
                   caption="Presidents" 
                   width="700px"
                   >               
                   <jmesa:htmlRow> 
                       <jmesa:htmlColumn property="name.firstName" title="First Name">
                           <a href="http://www.whitehouse.gov/history/presidents/">${bean.name.firstName}</a>
                       </jmesa:htmlColumn>
                       <jmesa:htmlColumn property="name.lastName" title="Last Name"/>
                       <jmesa:htmlColumn property="term"/>
                       <jmesa:htmlColumn property="career"
                           filterEditor="org.jmesa.view.html.editor.DroplistFilterEditor"
                       />
                       <jmesa:htmlColumn property="born"
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
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
        </script>
        
    </body>
    
</html>
