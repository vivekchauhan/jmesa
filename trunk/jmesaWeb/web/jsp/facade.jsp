<html>

<head>
	<title>Basic JMesa Example</title>
</head>

<body>

	<p class="content">
		The JMesa example that uses the TableFacade class. 
	</p>

	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/basic.run">Basic</a> <br/>
		<a href="${pageContext.request.contextPath}/basicGroovy.run">Basic With Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/advanced.run?restore=true">Advanced</a><br/>
		<a href="${pageContext.request.contextPath}/tag.run">Tag</a><br/>
	</p>

	<form name="presidentsForm" action="${pageContext.request.contextPath}/facade.run">
		${presidents}
	</form>
	
	<p class="content">
		The example code using the new TableFacade class:
	</p>
	
<pre>
Collection<Object> items = presidentService.getPresidents();

TableFacade facade = new TableFacadeImpl(id, request, maxRows, items, 
                                         "name.firstName", "name.lastName", "term", "career");
facade.setExportTypes(response, CSV, EXCEL);

Table table = facade.getTable();
table.setCaption("Presidents");

Column firstName = table.getRow().getColumn("name.firstName");
firstName.setTitle("First Name");

Column lastName = table.getRow().getColumn("name.lastName");
lastName.setTitle("Last Name");

Limit limit = facade.getLimit();
if (limit.isExportable()) {
   facade.render();
   return null;
} else {
   HtmlTable htmlTable = (HtmlTable) table;
   htmlTable.getTableRenderer().setWidth("600px");

   firstName.getCellRenderer().setCellEditor(new CellEditor() {
   public Object getValue(Object item, String property, int rowcount) {
          Object value = new BasicCellEditor().getValue(item, property, rowcount);
          HtmlBuilder html = new HtmlBuilder();
          html.a().href().quote().append("http://www.whitehouse.gov/history/presidents/").quote().close();
          html.append(value);
          html.aEnd();
          return html.toString();
       }
   });

   String html = facade.render();
   mv.addObject("presidents", html);
}
</pre>		
	

<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/facade.run?' + parameterString;
}
</script>

</body>

</html>
