<%@ taglib uri="sitemesh-decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jmesa.css"></link>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.js"></script>
		<title>JMesa</title>
		<decorator:head/>
	</head>

	<body>
		
		<div style="height:75px;background-repeat:repeat-x;background-image: url('${pageContext.request.contextPath}/images/header.png')">
			<a href="${pageContext.request.contextPath}/index.jsp">
				<img id="header" src="${pageContext.request.contextPath}/images/logo.png" alt="logo" />
			</a>
		</div>
		
		<div id="content">
			<decorator:body/>			
		</div>

	</body>

</html>
