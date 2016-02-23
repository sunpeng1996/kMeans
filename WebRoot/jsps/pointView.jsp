<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
   <title>点之间结果展示</title>
   <link href="/kMeans/css/bootstrap.min.css" rel="stylesheet" >
   <script src="/kMeans/js/jquery-1.8.0.min.js"></script>
   <script src="/kMeans/js/bootstrap.min.js"></script>
   
</head>
<body>
<%
	ArrayList<String> headList =(ArrayList)request.getSession().getAttribute("headlist");
	request.getSession().setAttribute("headList", headList);
	
%>
<%-- <table class="table">
   <caption >初始质心</caption>   
   <thead>
      <tr>      
    	  <th>类标号</th>
     				   <c:forEach var ="hname" items="${sessionScope.headList}" >        
        	<th>${hname}</th>
         </c:forEach>
      </tr>
   </thead>
   
  <%
   				//ArrayList<DataObject>   dvstart =  (ArrayList)request.getSession().getAttribute("dvstart");   		
   				ArrayList<Double> do1 = (ArrayList)request.getSession().getAttribute("dvstart");
 %>
   <tbody>
   
   		<c:forEach begin="1" end="${sessionScope.num1 }" step="1">
		      <tr>
				     	<c:forEach var="do1" items="${sessionScope.dvstart }">    				      			
					      		 <c:forEach var="data" items="do1" >					      						
					      						<td>${do1 }</td>
					      		</c:forEach>	 					      						 
						 </c:forEach>
						 <c:forEach var="do1" items="${sessionScope.dvstart }">    
						 		<td>${do1 }</td>
						 </c:forEach>
		   	 </tr>
      </c:forEach>
    
     <c:forEach var="do1" items="${sessionScope.dvstart }" varStatus="p" begin="0" end="${sessionScope.num1 }" step="1">  
     			
			<tr>
				     	
						 <td>
						 	   ${p.count }
						 </td>
						 <c:forEach var="data" items="${do1 }">    
						 		<td>${data }</td>
						 </c:forEach>
		   	 </tr>
</c:forEach>
   </tbody>
</table> --%>

<table class="table">
   <caption>结束位置</caption>
   <thead>
      <tr>      
    	  <th>类标号</th>
     				   <c:forEach var ="hname" items="${sessionScope.headList}" >        
        	<th>${hname}</th>
         </c:forEach>
      </tr>
   </thead>
   
  <%
   				ArrayList<Double> do2 = (ArrayList)request.getSession().getAttribute("dvend");
 %>
   <tbody>    
     <c:forEach var="do2" items="${sessionScope.dvstart }" varStatus="p" begin="0" end="${sessionScope.num1 }" step="1">  
     			
			<tr>
						 <td>
						 	   ${p.count }
						 </td>
						 <c:forEach var="data" items="${do2 }">    
						 		<td>${data }</td>
						 </c:forEach>
		   	 </tr>
</c:forEach>
   </tbody>
</table>


<table class="table">
   <caption>分类数量</caption>
   <thead>
      <tr>      
    	  <th>类标号</th>
     		<th>类数量</th>
      </tr>
   </thead>
   
  <%
   				int[] classnum =  (int [])request.getSession().getAttribute("classnum");
 %>
   <tbody>    
     <c:forEach var="num" items="${sessionScope.classnum }" varStatus="p" begin="0" end="${sessionScope.num1 }" step="1">  
     			
			<tr>
						 <td>
						 	   ${p.count }
						 </td>
						 <td>
						 		${num}
						 </td>
		   	 </tr>
</c:forEach>
   </tbody>
</table>

</body>
</html>