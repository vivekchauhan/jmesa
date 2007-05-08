<html>

<head>
	<title>Basic JMesa Example</title>
</head>

<body>

	<p class="content">
		The basic JMesa example uses the TableFactory to build a table. 
	</p>
	
	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/basicGroovy.run">Basic With Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/advanced.run?restore=true">Advanced</a><br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/basic.run">
		${presidents}
	</form>
	
	<p class="content">
		The example code using the TableFactory. See the wiki for the complete 
		<a href="http://code.google.com/p/jmesa/wiki/Example">source code</a>.
	</p>
	
<pre>
HtmlTableFactory tableFactory = new HtmlTableFactory(webContext, coreContext);
HtmlTable table = tableFactory.createTable("name.firstName", "name.lastName", "term", "career");
table.setCaption("Presidents");
table.getTableRenderer().setWidth("600px;");

CellEditor editor = new PresidentsLinkEditor(new BasicCellEditor());
Column firstName = table.getRow().getColumn("name.firstName");
firstName.setTitle("First Name");
firstName.getCellRenderer().setCellEditor(editor);

Column lastName = table.getRow().getColumn("name.lastName");
lastName.setTitle("Last Name");

ToolbarFactoryImpl toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, CSV);
toolbarFactory.enableSeparators(false);
Toolbar toolbar = toolbarFactory.createToolbar();
View view = new HtmlView(table, toolbar, coreContext);
view.render();
</pre>
	
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

