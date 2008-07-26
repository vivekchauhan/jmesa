<html>

  <head>
    <title>Basic JMesa Example</title>
  </head>

  <body>

    <p class="content">
      Multiple JMesa tables on one page.
    </p>

    <form>
      <div id="table1">
        ${table1}
      </div>
    </form>

    <br/>
    <br/>

    <form>
      <div id="table2">
        ${table2}
      </div>
    </form>

    <script type="text/javascript">
        function onInvokeAction(id) {
            var parameterString = createParameterStringForLimit(id);

            if (id == 'table1') {
                $.get('${pageContext.request.contextPath}/multi.run?method=table1&' + parameterString, function(data) {
                $("#table1").html(data)
            });
        } else if (id == 'table2') {
            $.get('${pageContext.request.contextPath}/multi.run?method=table2&' + parameterString, function(data) {
            $("#table2").html(data)
        });
    }
}
    </script>

  </body>

</html>
