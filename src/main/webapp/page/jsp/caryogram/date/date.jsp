<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>数据精选</title>
 	<%@include  file="../../plug/head.jsp"%>
 	<style type="text/css">
 		.gray{color:#8e8e8e !important}
 	</style>

</head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/pageControl.js"></script> 
<body>
<c:if test="${sessionScope.loginName == null || sessionScope.loginName == ''}">
 <!-- 未登录 -->
 <c:redirect url="/page/jsp/user/login.jsp"/>
</c:if>
<!-- 数据精选页面 -->
<div class="tab-pane fade in active" id="date">    
    <div class="date1">
		<!-- <span>筛选条件：</span> <input type="text" value="事件名称" readonly="true"> -->
		<span>事件编号：</span> <input placeholder='事件编号' type="text" class="inName">
		<input type="button" id="search1" value="搜索">
	</div>

    <!--刷选条件页面 -->
    <div class="sxBox" >
      <!--一：文件夹区块  -->
      <div class="fileBox">
      <div class="innerbox7">
      </div>
      </div>

      <!--二：图像库区块  -->
      <div class="tuxiangBox" >
    <!--     <span>图像库</span> -->
        
        <span class="imgs_size">图像尺寸：</span>
        <div class="rad_1">
        <input type="radio" name="radio" value="20" class="rad rad1" id="rad1">
        <label for="rad1">20%</label>
        <input type="radio" name="radio" value="40" class="rad rad2" id="rad2">
        <label for="rad2">40%</label>
        <input type="radio" name="radio" value="50" class="rad rad3" checked id="rad3">
        <label for="rad3">60%</label>
        <input type="radio" name="radio" value="80" class="rad rad4" id="rad4">
        <label for="rad4">80%</label>
        <input type="radio" name="radio" value="100" class="rad rad5" id="rad5">
        <label for="rad5">100%</label>
        </div>
        <div class="count">  
        </div>
        <span class="count1">分析</span>
        <input type="checkbox" class="qxBtn"  id="qxBtn" value="计数全选">
        <label for="qxBtn" class="q"></label><span class="qx">计数全选</span>
        <div class="date_analyse"></div>
        <span class="date_analyse1">计数</span>  
        <div class="count2">已选分析</div>
        <div class="z1">张</div>
        <input class="count3" type="text" name="" value="0" readonly="true">        
        <div class="date_analyse2"> 
        	已选计数
        </div>
        <div class="z2">张</div>
        <input class="date_analyse3" type="text" name="" value="0" readonly="true">

        <!-- 提交 -->
         <button class="submitBtn">提交</button>
       </div>

      <!-- 三：图片显示块 -->
      <div class="position_img fade in">
        <div class="innerbox1">
     	</div>
     </div>
    </div>
</div>

</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.i18n.properties.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/Calendar.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.lazyload.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/date/date.js"></script>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/layer/layer.js"></script>

<script type="text/javascript">

</script>
</html>