<html>

<head>
	<title>Basic JMesa Example</title>
</head>

<body>

	<p class="content">
		The JMesa example that uses the TableFacade class. 
	</p>

	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/basic.run">Basic</a> <br/>
		<a href="${pageContext.request.contextPath}/basicGroovy.run">Basic With Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/advanced.run?restore=true">Advanced</a><br/>
	</p>

	<form name="presidentsForm" action="${pageContext.request.contextPath}/facade.run">
		${presidents}
	</form>
	
	<p class="content">
		The example code using the new TableFacade class:
	</p>
	
<pre>
</pre>		
	

<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/facade.run?' + parameterString;
}
</script>

</body>

</html>
