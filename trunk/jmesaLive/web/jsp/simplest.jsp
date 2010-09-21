<!-- Header image and css/js will be included by sitemesh decorator (/WEB-INF/decorators.xml)-->

<html>
    
    <head>
        <title>Basic JMesa Example</title>
    </head>
    
    <body>
        
        <strong class="large">Simplest</strong>
        <h4 class="alt"> The Simplest JMesa example. </h4>
        <hr/>
        
        <form name="presidentsForm" action="${pageContext.request.contextPath}/PresidentController">
            <!-- required in PresidentController and to put "active" css class on hyperlink of current example (/decorators/main.jsp) -->
            <input type="hidden" name="type" value="simplest"/>

            ${presidents}
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
