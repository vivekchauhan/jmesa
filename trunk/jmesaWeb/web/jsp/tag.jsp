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
		<a href="${pageContext.request.contextPath}/basic.run">Basic</a> <br/>
		<a href="${pageContext.request.contextPath}/basicGroovy.run">Basic With Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/advanced.run?restore=true">Advanced</a><br/>
		<a href="${pageContext.request.contextPath}/facade.run">Facade</a> <br/>
	</p>

	<form name="presidentsForm" action="${pageContext.request.contextPath}/tag.run">
		
		<jmesa:table 
			id="pres" 
			items="${presidents}"
			var="pres"
			maxRows="8"
			maxRowsIncrements="8,16,24"
			stateAttr="restore"
			captionKey="presidents.caption" 
			exportTypes="csv,excel"
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
	
	</form>
	
	<p class="content">
		The example code using the new tag library:
	</p>
	
<pre>
&lt;jmesa:table 
     id="pres" 
     items="presidents" 
     maxRows="8"
     maxRowsIncrements="8,16,24"
     stateAttr="restore"
     caption="Presidents" 
     exportTypes="csv,excel"
     width="600px"
     >
     &lt;jmesa:row>		
         &lt;jmesa:column property="name.firstName" title="First Name">
             &lt;a href="http://www.whitehouse.gov/history/presidents/">{pres.name.firstName}&lt;/a>
         &lt;/jmesa:column>
         &lt;jmesa:column property="name.lastName" title="Last Name"/>
         &lt;jmesa:column property="term" title="Term"/>
         &lt;jmesa:column property="career" title="Career"/>
     &lt;/jmesa:row>		
&lt;/jmesa:table> 
</pre>		
	

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
