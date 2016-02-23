
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>聚类分析</title>
<link href="css/style.css" rel='stylesheet' type='text/css' />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ page contentType="text/html; charset=utf-8" %>
<meta name="keywords" content="App Sign in Form,Login Forms,Sign up Forms,Registration Forms,News latter Forms,Elements"/>
<!--  
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
</script>-->
<!--webfonts-->
<link href='http://fonts.useso.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
<!--//webfonts-->
</head>

<body>
	<h1>结果展示</h1>
		<div class="app-cam">
		<!--  <div class="cam"><img src="images/cam.png" class="img-responsive" alt=""   /></div>-->	
			
			  	<!-- <input type="hidden" name="method" value="doPost"/> -->	
											
			</div>
				<%--通过Request对象方法接受参数 --%> 
				<%
					Enumeration e = request.getParameterNames();
					while(e.hasMoreElements()){
						String str = (String)e.nextElement();
						String strRequest = (String)request.getParameter(str);
						out.println(str+":"+strRequest);
					}
				%>
				
			
		</div>
	<!--start-copyright-->
   		<div class="copy-right">
				<p>基于K-means算法的聚类分析：By:孙一铀、孙鹏、李雪、夏敏</a></p>
		</div>
	<!--//end-copyright-->
</body>
</html>