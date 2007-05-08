<html>

<head>
	<title>Basic JMesa Example</title>
</head>

<body>

	<p class="content">
		The basic JMesa example uses the TableFactory to build a table and coded in Groovy!
	</p>
	
	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/advanced.run?restore=true">Advanced</a><br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/basicGroovy.run">
		${presidents}
	</form>
	
	<p class="content">
		The example code using the TableFactory and coded in Groovy. See the wiki for the complete 
		<a href="http://code.google.com/p/jmesa/wiki/BasicGroovy">source code</a>.
	</p>
	
<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
</script>

</body>

</html>

