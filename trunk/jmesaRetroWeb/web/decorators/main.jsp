<%@ taglib uri="/tld/sitemesh-decorator" prefix="decorator" %>
<%@ taglib uri="/tld/c" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

	<head>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/web.css"/>"></link>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/jmesa.css"/>"></link>
		<script type="text/javascript" src="<c:url value="/js/jquery-1.2.2.pack.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jquery.bgiframe.pack.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/jmesa.js"/>"></script>
		<title>JMesa</title>
		<decorator:head/>
	</head>

	<body>
		
		<div style="height:75px;background-repeat:repeat-x;background-image: url('<c:url value="/images/header.png'"/>)">
			<a href="<c:url value="/index.jsp"/>">
				<img id="header" src="<c:url value="/images/logo.png"/>" alt="logo" />
			</a>
		</div>
		
		<div id="content">
			<decorator:body/>			
		</div>

	</body>

</html>
