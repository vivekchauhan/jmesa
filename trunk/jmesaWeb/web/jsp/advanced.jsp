<html>

<head>
	<title>Advanced JMesa Example</title>
</head>

<body>

	<p class="content">
		The advanced JMesa example uses the ComponentFactory to build a table. This is the same as the Limit example, 
		but is not using the database to do the pagination or using AJAX. In the future this will serve to show 
		more of the customizations possible with JMesa. However, one feature you should notice is that each time
		you come back to this table it is displayed just the way that you left it by using the State interface.
	</p>
	
	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/basic.run">Basic</a><br/>
		<a href="${pageContext.request.contextPath}/basicGroovy.run">Basic With Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/tag.run">Tag</a><br/>
		<a href="${pageContext.request.contextPath}/facade.run">Facade</a> <br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/advanced.run">
		${presidents}
	</form>
	
<pre>
HtmlComponentFactory factory = new HtmlComponentFactory(webContext, coreContext);

// create the table
HtmlTable table = factory.createTable();
table.setCaption("Presidents");
table.getTableRenderer().setWidth("650px");

// create the row
HtmlRow row = factory.createRow();
table.setRow(row);

// create the editor
CellEditor editor = factory.createBasicCellEditor();

// create the columns

CellEditor customEditor = new PresidentsLinkEditor(editor);
HtmlColumn firstName = factory.createColumn("name.firstName", customEditor);
firstName.setTitle("First Name");
firstName.getHeaderRenderer().setDefaultSortOrderable(false);
row.addColumn(firstName);

HtmlColumn lastName = factory.createColumn("name.lastName", editor);
lastName.setTitle("Last Name");
row.addColumn(lastName);

HtmlColumn term = factory.createColumn("term", editor);
row.addColumn(term);

HtmlColumn career = factory.createColumn("career", editor);
row.addColumn(career);

CellEditor dateCellEditor = factory.createDateCellEditor();
HtmlColumn bornColumn = factory.createColumn("born", dateCellEditor);
bornColumn.setFilterable(false);
bornColumn.setSortable(false);
row.addColumn(bornColumn);

// create the view
ToolbarFactoryImpl toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, CSV);
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

