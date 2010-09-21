<%@ taglib uri="sitemesh-decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

	<head>
      <!-- jmesa css -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jmesa.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/example.css"></link>

      <!-- blueprint framework -->
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bp/custom_screen.css" type="text/css" media="screen, projection">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bp/print.css" type="text/css" media="print">
      <!--[if lt IE 8]><link rel="stylesheet" href="${pageContext.request.contextPath}/css/bp/ie.css" type="text/css" media="screen, projection"><![endif]-->
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bp/fancy-type/screen.css" type="text/css" media="screen, projection">
      
      <!-- js files -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
		<title>JMesa</title>
		<decorator:head/>
	</head>

	<body>
      <div class="container">

      <script type="text/javascript">
         <!-- highlight active example based on the parameter passed from request -->
         $(document).ready(function(){
            $("#btn-" + "<%=request.getParameter("type")%>").removeClass("normal");
            $("#btn-" + "<%=request.getParameter("type")%>").addClass("active");
         });
      </script>

      <div class="span-24 example">[
        <a href="${pageContext.request.contextPath}/index.jsp">Web Application Index</a> |
        <a href="http://code.google.com/p/jmesa/">JMesa Website</a>
      ]</div> 

      <div class="span-24 example">
         <span class="span-3"> <strong> API EXAMPLES: </strong> </span>
         <a class="span-2 normal" id="btn-simplest" href="${pageContext.request.contextPath}/PresidentController?type=simplest">Simplest</a>
         <a class="span-2 normal" id="btn-basic" href="${pageContext.request.contextPath}/PresidentController?type=basic&restore=true">Basic</a>
         <a class="span-4 normal" id="btn-basicExport" href="${pageContext.request.contextPath}/PresidentController?type=basicExport&restore=true">Basic with Export</a>
         <a class="span-4 normal" id="btn-worksheet" href="${pageContext.request.contextPath}/PresidentController?type=worksheet&restore=true">Worksheet</a>
      </div>
      <div class="span-24 example">
         <span class="span-3"> <strong> TAG EXAMPLES: </strong> </span>
         <a class="span-2 normal" id="btn-tagSimplest" href="${pageContext.request.contextPath}/jsp/tagSimplest.jsp?type=tagSimplest">Simplest</a>
         <a class="span-2 normal" id="btn-tagBasic" href="${pageContext.request.contextPath}/jsp/tagBasic.jsp?type=tagBasic&restore=true">Basic</a>
         <a class="span-4 prepend-4 normal" id="btn-tagWorksheet" href="${pageContext.request.contextPath}/jsp/tagWorksheet.jsp?type=tagWorksheet&restore=true">Worksheet</a>
      </div>

      <decorator:body/>			

      </div>
	</body>

</html>
