<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
   <title>分层聚类结果展示</title>
   
   <link href="/kMeans/css/bootstrap.min.css" rel="stylesheet">
   <script src="/kMeans/js/jquery-1.8.0.min.js"></script>
   <script src="/kMeans/js/bootstrap.min.js"></script>
</head>

<table class="table">
   <caption>数据列表</caption>   
  <%
  	ArrayList<Map<Integer, ArrayList<Integer>>> dataList =(ArrayList<Map<Integer, ArrayList<Integer>>>)request.getSession().getAttribute("iterationArrayList");  
 %>
   <tbody>    
      <c:forEach var="dataArray" items="${sessionScope.iterationArrayList}"  varStatus="p" begin="1" step="1"> 
      					<tr>
      						<td style="width: 15%">
     						<h1>第${p.count }次迭代</h1>
     							</td>
     					</tr>     
     					<tr>
     						<th>类标记</th>
     						<th>类</th>
     					</tr> 			
     					<c:forEach  var="dataMap" items="${dataArray }" >
     					
     			        <tr>			
     			        			<th><font color="red">${dataMap.key }</font></th>
     								<c:forEach var="data" items="${dataMap.value }">
     											<th>
     													${data }
     											</th>
     								</c:forEach>
     			        </tr>
     					</c:forEach>	
     					
	</c:forEach> 
	
   </tbody>
</table> 
</html>
