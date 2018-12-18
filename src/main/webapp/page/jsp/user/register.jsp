<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>

</head>
<body>
	<p>${msg}</p>
	<div class="login_logo">
		<img src="../../images/aks.png">
	</div>
	<div class="register_bg">
		<div class="register_box">
			<p class="p1">染色体核型分析系统</p>
			<div class="register_fun">
				<span id="info"></span>
				<form action="/chromosome/user/reg" method="post" id="formid">
					
					<div class="register1">
						<span class="user_bg"><i></i>
						<p></p></span><input type="text" name="userLogin" value=""
							placeholder="请输入用户名" class="username" id="username" onblur="checkName()"><span name="unameSpan" id="unameSpan"></span>
					</div>
					<div class="register0">
						<span class="truename_bg"><i></i><p></p></span>
						<input type="text" name="userName" id="userName" class="truename" id="userName" placeholder="请输入真实姓名">
					</div>
					<div class="register2">
						<span class="pas_bg"><i></i>
						<p></p></span><input type="password" name="userPassword" value=""
							placeholder="请输入6-16密码" class="password" id="password">
					</div>
					<div class="register3">
						<span class="sure_bg"><i></i>
						<p></p></span>
						
						<input type="password" name="pas" value=""placeholder="请再次确认密码" class="password2" id="password2">
					</div>
					<div class="btn_box">
						<input type="button" class="register_btn" id="register_btn" value="立即注册" onclick="checkUser();">
					</div>
					</form>
				<div class="lot">
					<a href="login.jsp">已有账号？<span>立即登录&gt;</span></a>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/user/register.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/css/index.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/css/register.css">
</html>
