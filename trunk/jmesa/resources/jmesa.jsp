<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

	<head>
		<link rel="stylesheet" type="text/css" href="jmesa.css"></link>
		<script type="text/javascript" src="jmesa.js"></script>
		<title>JMesa</title>
	</head>

<body>

	<form name="presidentsForm">
	
	${presidents}

	</form>

<script>
var limit = new Limit('pres');
LimitManager.addLimit(limit);
	
function onInvokeAction() {
	LimitManager.getLimit('pres').createHiddenInputFields(document.presidentsForm);
	document.presidentsForm.submit();
}
</script>

</body>

</html>
