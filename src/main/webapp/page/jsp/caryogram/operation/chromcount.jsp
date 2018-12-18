<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>染色单体计数</title>
	 
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
   		<c:redirect url="/page/jsp/caryogram/operation/carycount.jsp"/>
   </c:when>
</c:choose>
<%@include  file="../../plug/head.jsp"%>
<%@include file="../../plug/crayhead1.jsp"%>
		
	
<body>
<div class="tab-pane" id="model_analyse">
  <div class="fade in" id="count_1">
        <div class="countLeft"> 
          <!-- 计数左边div1 -->
        <div class="countLeft1">
         <img id='countLeft2_hexing' src="../../../images/patientdata/ture_03.png">
       	 <img id="pic1" src=""/>
        </div>
          <!-- 计数左边div2 -->
        <div class="countLeft2">
        	<img id="pic2" src=""/>
        </div>
        <!-- 计数下面的bnt -->
        <div class="countBtn">
        <p class="number" id="number">${lable.inName}</p>
        <p class="id" id="id">${lable.slideName}</p>
        <input class="position" id="position"/>${lable.grayNumber}
        <input class="order_number" id="order_number">${lable.chromCary}
        <input type="" name="" class="text" id="text">
        <img src='../../../images/46.png' id='fortysix'/>
        <img id="confirm5" src="../../../images/tiaoguo.png">
        <div id="countBtn_shuliang">
			<span style="color:#ffff00;position: relative;top: 20px;font-size:18px;">核对计数结果</span>
			<span type="" name="" id="check_fruit1"></span>
			<span class='in45'>45</span><span id='in45'></span>
			<span class='in46'>46</span><span id='in46'></span>
			<span class='in47'>47</span><span id='in47'></span>
			<span class='in48'>其他</span><span id='in48'></span>
		</div>
        </div> 
        </div>
<!-- 计数右边div -->
        <div class="countRight">
        <div class="innerbox">
         	
        <table class="analyse_tab1">

        </table>
         	
        </div> 
        </div> 
        </div>
        </div>
       	<!-- 蒙版 -->
       	<div class="mengban">
       		
       	</div>
       	<div class="imgbox">
       		<input/>
       	</div>
</body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>	
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/chromcount.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
  <link href="${pageContext.request.contextPath}/page/css/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
</html>