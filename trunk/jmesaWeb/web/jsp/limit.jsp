<html>

<head>
	<title>JMesa Example</title>
</head>

<body>

	<p class="content">
		JMesa In Action using Limit and AJAX. 
	</p>
	
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/presidents.run">
		<div id="presidents">
			${presidents}
		</div>
	</form>

<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	
	var parameterString = createParameterStringForLimit(id);
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open('GET', '${pageContext.request.contextPath}/ajax.run?' + parameterString, true);
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			var presidents = document.getElementById('presidents');
			presidents.innerHTML = xmlhttp.responseText;
		}
	}
	
	xmlhttp.send(null);
}

function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/presidents.run?' + parameterString;
}
</script>

</body>

</html>

