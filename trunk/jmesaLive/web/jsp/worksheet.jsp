<!-- Header image and css/js will be included by sitemesh decorator (/WEB-INF/decorators.xml)-->

<html>
    <head>
        <title>Worksheet JMesa Example</title>
    </head>
    
    <body>

        <strong class="large">Worksheet</strong>
        <h4 class="alt"> The worksheet JMesa example shows what an editable table looks like. </h4>
        <hr/>

        <div class="span-24">
           <div class="span-11 colborder">
               <strong> Validations: </strong> <br/>
               1. Enter 'foo' in First Name or leave blank (Javascript validation). <br/>
               2. Enter invalid email in Email (Javascript validation). <br/>
               3. Leave Last Name blank (Javascript validation). <br/>
               4. Enter 'foo' in Career (API validation. Error will be shown after saving). <br/>
               Hover the mouse over the (red) cell to see the error message. <br/>
               Click 'Save Worksheet' to save and see list of erroneous rows!
           </div>

           <div class="span-12 last">
               <strong> Tips: </strong> <br/>
               1. Click any cell and press 'Tab / Shift+Tab' to navigate to next / previous cell. <br/>
               2. Hover a modified cell while pressing 'Ctrl' to display the original value. <br/>
               3. Click a modified cell and press 'Ctrl+Shift+z' to get the original value back. <br/>
               4. Click 'Clear Changes' to remove all the changes of worksheet. <br/>
           </div>
        </div>
        
        <hr class="space"/>

        <p>
            ${saveResults}
        </p>

        <form name="presidentsForm" id="presidentsForm" action="${pageContext.request.contextPath}/PresidentController">
            <!-- required in PresidentController and to put "active" css class on hyperlink of current example (/decorators/main.jsp) -->
            <input type="hidden" name="type" value="worksheet"/>

            ${presidents}
        </form>

        <hr class="space"/>
        
        <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }

            // custom validation
            function validateFirstName(val) {
               if (val == 'foo') {
                  return false;
               }

               return true;
            }
        </script>
    </body>
</html>
