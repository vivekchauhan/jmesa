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
            <a href="${pageContext.request.contextPath}/limit.run?restore=true">Limit (with AJAX)</a> <br/>
            <a href="${pageContext.request.contextPath}/basic.run?restore=true">Basic</a> <br/>
            <a href="${pageContext.request.contextPath}/tag.run?restore=true">Tag</a><br/>
            <a href="${pageContext.request.contextPath}/worksheet.run">Worksheet</a><br/>
        </p>
        
        <form name="presidentsForm" action="${pageContext.request.contextPath}/groovy.run">
            ${presidents}
        </form>
        
        <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
        </script>
        
    </body>
    
</html>
