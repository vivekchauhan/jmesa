<%@ taglib uri="/tld/jmesa" prefix="jmesa" %>
<%@ taglib uri="/tld/c" prefix="c" %>
<html>

  <head>
    <title>Basic JMesa Example</title>
  </head>

  <body>

    <p class="content">
      The basic JMesa example to build a table. Also using the State feature. You can see that
      everytime you come back to this page the table is sorted, filted, and paged the way
      that you left it.
    </p>

    <p class="content">
      Other examples:<br/>
      <a href="<c:url value="/limit.run?restore=true"/>">Limit (with AJAX)</a> <br/>
      <a href="<c:url value="/groovy.run?restore=true"/>">Groovy</a><br/>
      <a href="<c:url value="/tag.run?restore=true"/>">Tag</a><br/>
      <a href="<c:url value="/worksheet.run"/>">Worksheet</a><br/>
    </p>

    <form name="presidentsForm" action="<c:url value="/basic.run"/>">
      <c:out value="${presidents}" escapeXml="false"/>
    </form>

    <p class="content">
      This example source code can be found
      <a href="http://code.google.com/p/jmesa/wiki/FacadeExample">here</a>.
    </p>


    <script type="text/javascript">
        function onInvokeAction(id) {
            setExportToLimit(id, '');
            createHiddenInputFieldsForLimitAndSubmit(id);
        }
        function onInvokeExportAction(id) {
            var parameterString = createParameterStringForLimit(id);
            location.href = '<c:url value="/presidents.run"/>' + '?' + parameterString;
        }
    </script>

  </body>

</html>
