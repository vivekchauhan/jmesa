<html>

<head>
	<title>Basic JMesa Example</title>
	<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
</head>

<body>

	<p class="content">
		The JMesa example that uses the tag library. 
	</p>

	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/basic.run?restore=true">Basic</a> <br/>
		<a href="${pageContext.request.contextPath}/groovy.run">Groovy</a><br/>
	</p>

	<form name="presidentsForm" action="${pageContext.request.contextPath}/tag.run">
		
		<jmesa:tableFacade
			id="pres" 
			items="${presidents}"
			maxRows="8"
			exportTypes="csv,excel"
			maxRowsIncrements="8,16,24"
			stateAttr="restore"
			var="pres"
			>
			<jmesa:table 
				captionKey="presidents.caption" 
				width="600px"
				>		
				<jmesa:row>	
					<jmesa:column property="name.firstName" titleKey="presidents.firstName">
						<a href="http://www.whitehouse.gov/history/presidents/">${pres.name.firstName}</a>
					</jmesa:column>
					<jmesa:column property="name.lastName" title="Last Name"/>
					<jmesa:column property="term"/>
					<jmesa:column property="career"/>
					<jmesa:column property="born" filterable="false" pattern="MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
				</jmesa:row>
			</jmesa:table> 
		</jmesa:tableFacade>
	
	</form>
	
	<p class="content">
		This example source code can be found 
		<a href="http://code.google.com/p/jmesa/wiki/TagsExample">here</a>.
	</p>

<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/tag.run?' + parameterString;
}
</script>

</body>

</html>
