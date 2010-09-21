<!-- Header image and css/js will be included by sitemesh decorator (/WEB-INF/decorators.xml)-->

<html>
    
    <head>
        <title>Basic JMesa Example</title>
    </head>
    
    <body>

        <strong class="large">Basic</strong>
        <h4 class="alt"> The basic JMesa example to build a table. </h4>
        <hr/>
        
        <div class="span-24">
           <div class="span-11 colborder">
               Also using the State feature (by passing parameter "restore=true" in request). You can see that
               everytime you come back to this page the table is sorted, filted, and paged the way
               that you left it.
           </div>

           <div class="span-12 last">
              <strong> Tips: </strong> <br/>
              1. Click search box above 'Career' column to see droplist having unique values.
           </div>
        </div>

        <hr class="space"/>

        <form name="presidentsForm" action="${pageContext.request.contextPath}/PresidentController">
            <!-- required in PresidentController and to put "active" css class on hyperlink of current example (/decorators/main.jsp) -->
            <input type="hidden" name="type" value="basic"/>

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
