<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>核型分析</title> 
</head>
<%@include  file="../../plug/head.jsp"%>
<%@include  file="../../plug/crayhead.jsp"%>
<body>
<c:choose>
   <c:when test="${sessionScope.loginName == null or sessionScope.loginName == ''}">
  		   <!-- 未登录 -->
 		<c:redirect url="/page/jsp/user/login.jsp"/>
   </c:when>
               
   <c:when test="${sessionScope.inName == null or sessionScope.inName == ''}"> 
   		<c:redirect url="/page/jsp/caryogram/operation/caryindex.jsp"/>
   </c:when>
  </c:choose>
  "${sessionScope.karyotypeOperation}"
<!-- 核型分析页面 -->
	<div class="tab-pane" id="model_analyse">
		<div class="fade active in" id="analyse_show">
		<div id='head'>
		 <span id='head_span'>编号:</span>
		 <span id = "innames"></span>
		 <button id="return2">返回列表</button>
		</div>
		    <div id="innerbox3">
		    	
		   	</div>
		</div>
  	</div>
</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryanalyse.js"></script>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
</html>