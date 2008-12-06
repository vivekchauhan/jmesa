

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <script language="javascript" src="${createLinkTo(dir:"plugins/jmesa-1.0/js",file:"jquery-1.2.2.pack.js")}"></script>
        <script language="javascript" src="${createLinkTo(dir:"plugins/jmesa-1.0/js",file:"jquery.bgiframe.pack.js")}"></script>
        <script language="javascript" src="${createLinkTo(dir:"plugins/jmesa-1.0/js",file:"jquery.jmesa.js")}"></script>
        <script language="javascript" src="${createLinkTo(dir:"plugins/jmesa-1.0/js",file:"jmesa.js")}"></script>

        <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:"plugins/jmesa-1.0/css",file:"jmesa.css")}" />
        <script type="text/javascript">
        function onInvokeAction(id) {
            setExportToLimit(id, '');
            createHiddenInputFieldsForLimitAndSubmit(id);
        }
        function onInvokeExportAction(id) {
            var parameterString = createParameterStringForLimit(id);
            location.href = '/pluginSample/book/list2?' + parameterString;
        }
        </script>
        <title>Book List</title>
    </head>
    <body>
            <form name="bookForm" action="/pluginSample/book/list2">
                <jmesa:tableFacade
                    id="tag"
                    items="${bookList}"
                    maxRows="15"
                    exportTypes="csv,excel"
                    stateAttr="restore"
                    var="bean"
                    limit="${limit}"
                    >
                    <jmesa:htmlTable
                        caption="books"
                        width="600px"
                        >
                        <jmesa:htmlRow>
                            <jmesa:htmlColumn property="title" cellEditor="org.jmesa.view.editor.BasicCellEditor"><a href="#">${bean.title}</a></jmesa:htmlColumn>
                            <jmesa:htmlColumn property="author"/>
                        </jmesa:htmlRow>
                    </jmesa:htmlTable>
                </jmesa:tableFacade>

            </form>
    </body>
</html>
