<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>核型分析</title>
	 
</head>
<%@include  file="../../plug/head.jsp"%>
<%@include  file="../../plug/crayhead.jsp"%>
<c:choose>
   <c:when test="${sessionScope.loginName == null or sessionScope.loginName == ''}">
  		   <!-- 未登录 -->
 		<c:redirect url="/page/jsp/user/login.jsp"/>
   </c:when>
   <c:when test="${sessionScope.inName == null or sessionScope.inName == ''}"> 
   		<c:redirect url="/page/jsp/caryogram/operation/caryindex.jsp"/>
   </c:when>
  </c:choose>
<!-- 文件卡 -->
<jsp:include  page="../../plug/folder.jsp" flush="true"/>
<body>
<!-- 核型分析页面 -->
  <div class="tab-pane" id="model_analyse">
	<div class="fade active in" id="count">
        <div class="countLeft3" > 
           <button id="bear_fruit">计数结果45条(<span id="sum1"></span>)</button>
           <button id="bear_fruit1">计数结果46条(<span id="sum2"></span>)</button>
           <button id="bear_fruit2">计数结果47条(<span id="sum3"></span>)</button>
           <button id="bear_fruit3">其他(<span id="sum4"></span>)</button>
           <div class="countLeft3_fruit">
             <span style="color: #ffff00">核对计数结果</span>
             <input type="" name="" id="check_fruit" readonly>
             <p class="fruit1">45</p><input type="" name="" id="fruit1_1" readonly="readonly">
             <p class="fruit2">46</p><input type="" name="" id="fruit2_2"  readonly="readonly">
             <p class="fruit3">47</p><input type="" name="" id="fruit3_3"  readonly="readonly">
             <p class="fruit4">其他</p><input type="" name="" id="fruit4_4"  readonly="readonly">   
           </div>
             <span class="images_size">图像尺寸:</span>
             <input type="radio" name="radio1" value="20" class="count_rad rad6" id="rad6">
			 <label for="rad6">20%</label>
			 <input type="radio" name="radio1" value="40" class="count_rad rad7" id="rad7">
			 <label for="rad7">40%</label>
			 <input type="radio" name="radio1" value="60" class="count_rad rad8" checked id="rad8">
			 <label for="rad8">60%</label>
			 <input type="radio" name="radio1" value="80" class="count_rad rad9" id="rad9">
			 <label for="rad9">80%</label>
			 <input type="radio" name="radio1" value="100" class="count_rad rad10" id="rad10">
			 <label for="rad10">100%</label>
             <button id="event_date1">事件数据</button>
             <button id="create_report">生成报告</button>
             <button id="return1">返回列表</button>
        </div>
        <div class="countRight3">
        	<div class="innerbox">
            	
        	</div>
    	</div>
	  </div>
	
	</div>
</body>

<script type="text/javascript">
	
	setTimeout(outTime,300);
	function outTime(){
		var grayid = "<%=session.getAttribute("grayid")%>";
		location.hash="#"+grayid;
	}
</script>
	
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/carycount.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
  <link href="${pageContext.request.contextPath}/page/css/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
</html>