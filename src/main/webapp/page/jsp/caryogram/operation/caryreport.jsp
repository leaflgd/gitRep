<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>核型分析分割</title>
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
   		<c:redirect url="/page/jsp/caryogram/operation/carycount.jsp"/>
   </c:when>
</c:choose>
<!-- 生成报告页面 -->
	<div class="fade in" id="create_report_show">
	<input id="PageContext" type="hidden" style="display: none;" value="${pageContext.request.contextPath}" />
   		<div  class="create_report_img">
		
       	</div>  
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>
  <script type="text/javascript" 

src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" 

src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  <script type="text/javascript" 

src="${pageContext.request.contextPath}/page/js/plug/jquery.contextMenu.js"></script>
  <script type="text/javascript" 

src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryreport.js"></script

>
  <link rel="stylesheet" type="text/css" 

href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  <link rel="stylesheet" type="text/css" 

href="${pageContext.request.contextPath}/page/css/index.css">
  <link href="${pageContext.request.contextPath}/page/css/jquery.contextMenu.css" 

rel="stylesheet" type="text/css" />
</html>