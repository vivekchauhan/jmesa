<html>

<head>
	<title>Basic JMesa Example</title>
</head>

<body>

	<p class="content">
		The basic JMesa example coded in Groovy!
	</p>
	
	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/basic.run">Basic</a> <br/>
		<a href="${pageContext.request.contextPath}/tag.run">Tag</a><br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/groovy.run">
		${presidents}
	</form>
	
	<p class="content">
		This example source code can be found 
		<a href="http://code.google.com/p/jmesa/wiki/Example">here</a>.
	</p>
	
<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
</script>

</body>

</html>

