<html>
    
    <head>
        <title>Worksheet JMesa Example</title>
    </head>
    
    <body>
        
        <p class="content">
            The worksheet JMesa example shows what an editable table looks like.
        </p>
        
        <p class="content">
            Other examples:<br/>
            <a href="${pageContext.request.contextPath}/basic.run?restore=true">Basic</a> <br/>
            <a href="${pageContext.request.contextPath}/limit.run?restore=true">Limit (with AJAX)</a> <br/>
            <a href="${pageContext.request.contextPath}/groovy.run?restore=true">Groovy</a><br/>
            <a href="${pageContext.request.contextPath}/tag.run?restore=true">Tag</a><br/>
        </p>
        
        <form id="presidentsForm" action="${pageContext.request.contextPath}/worksheet.run">
            ${presidents}
        </form>
        
        <p class="content">
            This example source code can be found 
            <a href="http://code.google.com/p/jmesa/wiki/WorksheetExample">here</a>.
        </p>
        
        
        <script type="text/javascript">

            $(document).ready(function() {
                $("#selectAllChkBox").click(function() {
                    var isChecked = this.checked;
                    $("#presidentsForm :checkbox:not(#selectAllChkBox)").each(function() {
                        this.click();
                        this.checked = isChecked;
                    });
                });

            });

            function onInvokeAction(id) {
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
        </script>
        
    </body>
    
</html>
