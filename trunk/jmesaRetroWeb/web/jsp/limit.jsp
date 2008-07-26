<%@ taglib uri="/tld/jmesa" prefix="jmesa" %>
<%@ taglib uri="/tld/c" prefix="c" %>
<html>

  <head>
    <title>JMesa Example</title>
  </head>

  <body>

    <p class="content">
      JMesa using the Limit to only retrieve the current rows needed. Also using AJAX to avoid a full page refresh.
    </p>

    <p class="content">
      Other examples:<br/>
      <a href="<c:url value="/basic.run?restore=true"/>">Basic</a> <br/>
      <a href="<c:url value="/groovy.run?restore=true"/>">Groovy</a><br/>
      <a href="<c:url value="/worksheet.run"/>">Worksheet</a><br/>

    </p>

    <form name="presidentsForm">
      <div id="presidents">
        <c:out value="${presidents}" escapeXml="false"/>
      </div>
    </form>

    <p class="content">
      This example source code can be found
      <a href="http://code.google.com/p/jmesa/wiki/FacadeLimitExample">here</a>.
    </p>

    <script type="text/javascript">
        function onInvokeAction(id) {
            setExportToLimit(id, '');

            var parameterString = createParameterStringForLimit(id);
            $.get('<c:url value="/limit.run?ajax=true&"/>' + parameterString, function(data) {
            $("#presidents").html(data)
        });
    }

    function onInvokeExportAction(id) {
        var parameterString = createParameterStringForLimit(id);
        location.href = '<c:url value="/limit.run?ajax=false&"/>' + parameterString;
    }
    </script>

  </body>

</html>
