<%@ taglib uri="/tld/jmesa" prefix="jmesa" %>
<%@ taglib uri="/tld/c" prefix="c" %>
<html>

  <head>
    <title>Basic JMesa Example</title>

  </head>

  <body>

    <p class="content">
      The JMesa example that uses the tag library.
    </p>

    <p class="content">
      Other examples:<br/>
      <a href="<c:url value="/limit.run?restore=true"/>">Limit (with AJAX)</a> <br/>
      <a href="<c:url value="/basic.run?restore=true"/>">Basic</a> <br/>
      <a href="<c:url value="/groovy.run?restore=true"/>">Groovy</a><br/>
      <a href="<c:url value="/worksheet.run"/>">Worksheet</a><br/>
    </p>

    <form name="presidentsForm" action="<c:url value="/tag.run"/>">

      <jmesa:tableFacade
        id="tag"
        items="<%request.getAttribute('presidents')%>"
        maxRows="8"
        exportTypes="csv,excel"
        maxRowsIncrements="8,16,24"
        filterMatcherMap="org.jmesaweb.controller.TagFilterMatcherMap"
        stateAttr="restore"
        var="bean"
        >
      <jmesa:htmlTable
        captionKey="presidents.caption"
        width="600px"
        >
        <jmesa:htmlRow>
          <jmesa:htmlColumn property="name.firstName" titleKey="presidents.firstName">
            <a href="http://www.whitehouse.gov/history/presidents/">${bean.name.firstName}</a>
          </jmesa:htmlColumn>
          <jmesa:htmlColumn property="name.lastName" title="Last Name"/>
          <jmesa:htmlColumn property="term"/>
          <jmesa:htmlColumn property="career"/>
          <jmesa:htmlColumn property="born" pattern="MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
        </jmesa:htmlRow>
      </jmesa:htmlTable>
      </jmesa:tableFacade>

    </form>

    <p class="content">
      This example source code can be found
      <a href="http://code.google.com/p/jmesa/wiki/TagsExample">here</a>.
    </p>

    <script type="text/javascript">
        function onInvokeAction(id) {
            setExportToLimit(id, '');
            createHiddenInputFieldsForLimitAndSubmit(id);
        }
        function onInvokeExportAction(id) {
            var parameterString = createParameterStringForLimit(id);
            location.href = '<c:url value="/tag.run?"/>' + parameterString;
        }
    </script>

  </body>

</html>
