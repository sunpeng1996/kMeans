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
	<h1>聚类分析</h1>
		<div class="app-cam">
		<!--  <div class="cam"><img src="images/cam.png" class="img-responsive" alt=""   /></div>-->	
			<form action="<c:url value='/ExecuteServlet?method=doPost'/>"  method="post" enctype="multipart/form-data">
			  	<!-- <input type="hidden" name="method" value="doPost"/> -->	
			<div>
				<div>请选择数据之间的距离概念</div>
				<input type="radio" name="way" value="class">分层聚类
				<input type="radio" name="way" value="point">K-Means
			</div>
			<div>
					<font style="text-align: center;">
					<strong>请上传您需要的文件</strong>
					</font>						
							<input type="file" class="file" value="filename"  name="file1">					
			</div> 
				<div class="clear"></div>
				<div class="buttons">
					<ul>
						<div>
							<font style="text-align: center;">
								<strong>请选择点与点距离方式</strong>
							</font>
						<div>
						<select name="pointWay"><option value="EUCLIDEAN">欧氏距离
						<option value="BLOCK">绝对距离
						<!-- <option value="CHEBYCHEV">Chebychev距离 -->
						<option value="COSIN">夹角余弦
						<option value="CORRELATION">相似度量</select>
						<!-- <li><a href="#" class="hvr-sweep-to-right">Sign in with Facebook</a></li>
						<li><a href="#" class="hvr-sweep-to-left">Sign in with Twitter</a></li> -->
						<div class="clear"></div>
					</ul>
					<br/>
					<ul>
						<div>
							<font style="text-align: center;">
								<strong>请选择类与类距离方式</strong>
							</font>
						<div>
						<select name="classWay">
							<!-- <option value="MIN">最短距离法 -->
							<option value="MAX">最长距离法
							<option value="AVR">类平均法
							<!-- <option value="CORE">重心法 -->
						</select>						
						</div>			
					</ul>
					<div>
					您想分成几类呢？
					<input type="text" name="classnum"  >
					</div>
				</div>
				
				<!--  <div class="new">
					<p><a href="#">Forgot Password ?</a></p>
					<p class="sign">New here ?<a href="#"> Sign Up</a></p>
					<div class="clear"></div>
				</div>-->
			<!--  	<div class="submit"><input type="submit" onclick="submit()" value="提交" ></div>-->
				<input  type="submit" value="提交" >
			</form>
		</div>
	<!--start-copyright-->
   		<div class="copy-right">
				<p>基于K-means算法的聚类分析：By:孙一铀、孙鹏、李雪、夏敏</a></p>
		</div>
	<!--//end-copyright-->
</body>
</html>