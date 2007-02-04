<html>

<head>
	<title>JMesa Example</title>
</head>

<body>

	<p class="content">
		JMesa In Action. 
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

HtmlTable table = tableFactory.createTable("firstName", "lastName", "term", "career");
table.setCaption("Presidents");
table.getTableRenderer().setWidth("600px;");

CellEditor editor = new PresidentsLinkEditor(new BasicCellEditor());
table.getRow().getColumn("firstName").getCellRenderer().setCellEditor(editor);

ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, "csv");
Toolbar toolbar = toolbarFactory.createToolbar();
View view = new HtmlView(table, toolbar, coreContext);
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

