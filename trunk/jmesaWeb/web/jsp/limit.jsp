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
		<a href="${pageContext.request.contextPath}/basic.run">Basic</a> <br/>
		<a href="${pageContext.request.contextPath}/groovy.run">Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/tag.run">Tag</a><br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/presidents.run">
		<div id="presidents">
			${presidents}
		</div>
	</form>
	
	<p class="content">
		This example source code can be found 
		<a href="http://code.google.com/p/jmesa/wiki/Example">here</a>.
	</p>

	<p class="content" style="font-style: italic;">
		Note: This is what the AJAX call looks like using the JQuery library.
	</p>
	
<pre>
function onInvokeAction(id) {
    setExportToLimit(id, '');

    var parameterString = createParameterStringForLimit(id);
    $.get('${pageContext.request.contextPath}/ajax.run?' + parameterString, function(data) {
        $("#presidents").html(data)
    });
}

function onInvokeExportAction(id) {
    var parameterString = createParameterStringForLimit(id);
    location.href = '${pageContext.request.contextPath}/presidents.run?' + parameterString;
}
</pre>		

<script type="text/javascript">
function onInvokeAction(id) {
    setExportToLimit(id, '');

    var parameterString = createParameterStringForLimit(id);
    $.get('${pageContext.request.contextPath}/limit.run?ajax=true&' + parameterString, function(data) {
        $("#presidents").html(data)
    });
}

function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/presidents.run?ajax=true&' + parameterString;
}
</script>

</body>

</html>

