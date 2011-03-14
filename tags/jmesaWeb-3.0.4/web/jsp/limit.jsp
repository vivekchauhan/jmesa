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
            <a href="${pageContext.request.contextPath}/basic.run?restore=true">Basic</a> <br/>
            <a href="${pageContext.request.contextPath}/groovy.run?restore=true">Groovy</a><br/>
            <a href="${pageContext.request.contextPath}/tag.run?restore=true">Tag</a><br/>
            <a href="${pageContext.request.contextPath}/worksheet.run">Worksheet</a><br/>
        </p>
        
        <form name="presidentsForm">
            <div id="presidents">
                ${presidents}
            </div>
        </form>
        
        <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');

                var parameterString = $.jmesa.createParameterStringForLimit(id);
                $.get('${pageContext.request.contextPath}/limit.run?ajax=true&' + parameterString, function(data) {
                $("#presidents").html(data)
            });
        }

        function onInvokeExportAction(id) {
            var parameterString = $.jmesa.createParameterStringForLimit(id);
            location.href = '${pageContext.request.contextPath}/limit.run?ajax=false&' + parameterString;
        }
        </script>
        
    </body>
    
</html>
