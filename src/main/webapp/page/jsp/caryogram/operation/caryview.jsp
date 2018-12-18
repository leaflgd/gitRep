<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>核型分析分割</title>
</head>
<c:choose>
   <c:when test="${sessionScope.loginName == null or sessionScope.loginName == ''}">
  		   <!-- 未登录 -->
 		<c:redirect url="/page/jsp/user/login.jsp"/>
   </c:when>
   <c:when test="${sessionScope.inName == null or sessionScope.inName == ''}"> 
   		<c:redirect url="/page/jsp/caryogram/operation/caryindex.jsp"/>
   </c:when>
   <c:when test="${sessionScope.grayid == null or sessionScope.grayid == ''}">
   		<c:redirect url="/page/jsp/caryogram/operation/caryanalyse.jsp"/>
   </c:when>
   <c:when test="${sessionScope.mark == 'null' or sessionScope.mark == '' or sessionScope.mark == null}">
   		<c:redirect url="/page/jsp/caryogram/operation/chromanalyse.jsp"/>
   </c:when>
</c:choose>

<%@include  file="../../plug/head.jsp"%>
<%@include  file="../../plug/crayhead.jsp"%>
<body>
<!-- 核型一览 -->
 <div class="fade in" id="analyse2">
        <div class="analyse_Left2"> 
         <div class="analyse_Left2_tab" >
         	
         </div>
        </div>
        
        <div class="analyse_Right2">
          <div class="analyse_Right1_box">
          	
          </div>
          <div class="analyse_btn2">
            <button id="revolve">旋转180°</button>
            <button id="images">镜像</button>
            <button id="reduce">缩小</button>
            <button id="amplify">放大</button>
            <button id="duplicate">复制</button>
            <button id="adjust">颜色调整</button>
            <button id="annotate">核型注解</button>
            <button id="retrun2">返回</button>
            <button id="event_date">事件数据</button>
            <button id="report">生成报告</button>
            <button id="cut_apart">分割</button>
          </div>
        </div>
    </div>
    </body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryview.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
</html>