<!-- Header image and css/js will be included by sitemesh decorator (/WEB-INF/decorators.xml)-->

<%@ page import="
org.jmesa.web.HttpServletRequestWebContext,
org.jmesalive.dao.PresidentDao,
org.jmesalive.domain.President
"%>

<%
   // set the collection in the request for JMesa tag lib
   PresidentDao presidentDao = new PresidentDao(new HttpServletRequestWebContext(request));
   request.setAttribute("presidents", presidentDao.getPresidents());
%>

<html>
    
    <head>
        <title>Basic JMesa Example</title>
        <%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
    </head>
    
    <body>
        
        <strong class="large">Simplest</strong>
        <h4 class="alt"> The Simplest JMesa example. </h4>
        <hr/>
        
        <form name="presidentsForm" action="${pageContext.request.contextPath}/jsp/tagSimplest.jsp">
            <!-- required to put "active" css class on hyperlink of current example (/decorators/main.jsp) -->
            <input type="hidden" name="type" value="tagSimplest"/>
            
            <jmesa:tableModel
               id="tagSimplest" 
               items="${presidents}"
               var="bean"
               >
               <jmesa:htmlTable>               
                   <jmesa:htmlRow> 
                       <jmesa:htmlColumn property="name.firstName" title="First Name"/>
                       <jmesa:htmlColumn property="name.lastName" title="Last Name"/>
                       <jmesa:htmlColumn property="term"/>
                       <jmesa:htmlColumn property="career"/>
                       <jmesa:htmlColumn property="born"/>
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
