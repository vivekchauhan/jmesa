<%@ taglib uri="/tld/c" prefix="c" %>
<html>

  <head>
    <title>Basic JMesa Example</title>
  </head>

  <body>

    <p class="content">
      The basic JMesa example coded in Groovy!
    </p>

    <p class="content">
      Other examples:<br/>
      <a href="<c:url value="/limit.run?restore=true"/>">Limit (with AJAX)</a> <br/>
      <a href="<c:url value="/basic.run?restore=true"/>">Basic</a> <br/>
      <a href="<c:url value="/worksheet.run"/>">Worksheet</a><br/>
    </p>

    <form name="presidentsForm" action="<c:url value="/groovy.run"/>">
      <c:out value="${presidents}" escapeXml="false"/>
    </form>

    <p class="content">
      This example source code can be found
      <a href="http://code.google.com/p/jmesa/wiki/FacadeGroovyExample">here</a>.
    </p>

    <script type="text/javascript">
        function onInvokeAction(id) {
            createHiddenInputFieldsForLimitAndSubmit(id);
        }
    </script>

  </body>

</html>
