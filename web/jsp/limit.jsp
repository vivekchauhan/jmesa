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
		<a href="${pageContext.request.contextPath}/basicGroovy.run">Basic With Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/advanced.run?restore=true">Advanced</a><br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/presidents.run">
		<div id="presidents">
			${presidents}
		</div>
	</form>
	
	<p class="content">
		See the wiki for the complete 
		<a href="http://code.google.com/p/jmesa/wiki/LimitExample">source code</a>. 
	</p>

	<p class="content" style="font-style: italic;">
		Note: This is what the AJAX call looks like. Also, for the (above) example I am using the
		ComponentFactory, but could have easily used the TableFactory.
	</p>
	
<pre>
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
</pre>		

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

