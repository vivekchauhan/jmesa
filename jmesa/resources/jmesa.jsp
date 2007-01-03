<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

	<head>
		<link rel="stylesheet" type="text/css" href="jmesa.css"></link>
		<script type="text/javascript" src="jmesa.js"></script>
		<title>JMesa</title>
	</head>

<body>

	<form name="presidentsForm" action="${pageContext.request.contextPath}/presidents.run">
	
	${presidents}

	</form>

<script type="text/javascript">
function onInvokeAction(id) {
	createHiddenInputFieldsForLimitAndSubmit(id);
}
function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/presidents.run?' + parameterString;
}
</script>

</body>

</html>
