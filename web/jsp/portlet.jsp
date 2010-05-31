<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="<c:url value="/js/jquery-1.2.2.pack.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.bgiframe.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jmesa.js" />"></script>
<link rel="stylesheet" href="<c:url value="/css/jmesa.css" />" type="text/css">

<form action="${action}" method="post">
    <input type="hidden" name="jmesaId" value="<c:out value='${table.id}' />">
    <div><c:out value="${table.markup}" escapeXml="false" /></div>
</form>

<script type="text/javascript">

    var url = '<c:url value="/portlet.run" />?id=${table.id}&';

<c:if test="${useAjax}">
    function onInvokeAction(id) {
        $.jmesa.setExportToLimit(id, '');
        var parameterString = $.jmesa.createParameterStringForLimit(id);
        $.get(url + parameterString, function(data) {
            $('#${table.id}').parents("div:first").html(data);
        });
    }
</c:if>

<c:if test="${!useAjax}">
    function onInvokeAction(id, action) {
        $.jmesa.setExportToLimit(id, '');
        $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
    }
</c:if>

    function onInvokeExportAction(id) {
        var parameterString = $.jmesa.createParameterStringForLimit(id);
        console.log($.jmesa.getTableFacade(id).limit);
        location.href = url + parameterString;
    }

</script>