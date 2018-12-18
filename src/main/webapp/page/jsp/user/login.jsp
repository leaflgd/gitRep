<%@ page language="java" contentType="text/html; charset=Utf-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no" />
<title>登录</title>

</head>
<body>
	<p>${msg}</p>	<!-- 登录页面 -->
	<div class="login_logo">
		<img src="../../images/aks.png">
	</div>
	<div class="login_bg">
		<div class="login_box">
			<p class="p1">染色体核型分析系统</p>
			<div class="login_fun">
				<span id="info"></span>
				<form action="/chromosome/user/log" method="post">
					<div class="login1">
						<span class="user_bg"><i></i><p></p></span><input type="text" name="userLogin" value="" placeholder="用户名" class="username" id="username">
					</div>
					<div class="login2">
						<span class="pas_bg"><i></i><p></p></span><input type="password" name="userPassword" value="" placeholder="密码" class="password" id="password">
					</div>
					<div class="remeber">
						<input type="checkbox" name="" value="" checked="checked" id="che1">
						<label for="che1"></label>
						<p>记住密码</p>
						 <input type="checkbox" name="" value=""  id="che2">
						 <label for="che2"></label>
						<p>自动登录</p> 
					 <a href="forget.html">忘记密码</a>
					</div>
					<div class="btn_box">
						<button class="login_btncs" id="login_btn">登录</button>
					</div>
				</form>
				<div class="lot">
					<a href="register.jsp">没有账号？<span>立即注册&gt;</span></a>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<span>
			<img src="../../images/login_logo.png">
		</span>
		<p>Copyright©2018  光绣-自兴智能医疗联合实验室</p>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/login.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/user/login.js"></script>
</html>
