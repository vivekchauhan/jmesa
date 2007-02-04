<html>

<head>
	<title>JMesa Example</title>
</head>

<body>

	<p class="content">
		JMesa In Action. 
	</p>
	
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/advanced.run">
		${presidents}
	</form>
	
<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/presidents.run?' + parameterString;
}
</script>

</body>

</html>

