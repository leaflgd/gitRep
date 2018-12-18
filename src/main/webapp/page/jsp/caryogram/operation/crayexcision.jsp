<%@ page language="java" contentType="text/html; charset=Utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>核型分析分割</title>
<style>
	.arCanas, .drCanas{
      position: absolute;
      top: 40px;
      left: 0;
      display: none;
   }
   .drCanas.b{
   	display: block;
   }
   .arCanas.open{
   	  display: block;
   	  z-index: 1;
   }
   
   #chuli1 .circle {
     width: 48px;
     height: 48px;
     background-color: rgba(102,102,102,0.05);
     border-radius: 50%;
     display:none;
     position: absolute;
     left:0;
     border: 1px solid rgba(255,0,0,1);
 }
 
 #chuli1 .circle.open{
 	 display:block;
 }
</style>
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
</c:choose>
<%@include file="../../plug/head.jsp"%>
<%@include  file="../../plug/crayhead1.jsp"%>
<body>
<div id="crayexcisionId">
<div style="display:none">
	<canvas id="enhanceOutput"></canvas>
</div>
<div style="display:none">
	<canvas id="enhanceIn"></canvas>
</div>
<div style="position: absolute;z-index: 1001;width: 150px;left: 1750px;height: 26px;top: 1039px;/* z-index: 10001; */">
<span style="color:#ffff00;">分割区域数量</span>
<input type="" name="" id="check_fruit2" readonly :value="countSize">
</div>
<div class="zhezhao"></div>
	<!-- 分割 -->
	<div class="fade in unselect" id="analyse1">
		<!-- 左边 -->
		<div class="analyse_Left">
			<!-- 左边第一个div -->
			<div class="analyse_Left_img" id = "analyse_Left_img">
				<img id="pic1">
			</div>
			<div>
				<input style="display:none" type="text" id="disinName" value="<%=session.getAttribute("inName")%>" />
				<span class="disinName_span">
				<span id='analyse_Left_img_data' style="line-height: 65px;color:#ffffff"></span>
				</span>
				<button id="yuanshi"><i id="yuanshia"></i>原始图像</button>
				<button id="chuli"><i id="chulia"></i>处理后图像</button>
				<button id="copy"><i id="copya"></i>连接</button> <!--新加-->
				<button id="procedure"><i id="procedurea"></i>处理步骤</button>
				<button id="lianjie"><i id="lianjiea"></i>复制</button> 
				<button id="delete"><i id="deletea"></i>批量删除</button>
				<button id="yes_delete" disabled="disabled"><i id="yes_deletea"></i>确定删除</button>
				<button id="retrun"><i id="retruna"></i>生成核型</button>	
				<button id='show_count'></button>
				</div>
		</div>

		
		<div id="chuli1">
		<div class="circle"></div>
		<span class="del"><img src="../../../images/timg.png"></span>
			<button id="settingOut"><i id="settingOuta"></i>画线</button>
			<button id="smear"><i id="smeara"></i>涂抹</button>
			<button id="save"><i id="savea"></i>保存</button>
			<button id="clear1"><i id="clear1a"></i>撤销</button>
			<canvas id="myCanvas" ></canvas>
			<canvas id="bmCanvas" class="arCanas open"></canvas>
			<canvas id="arCanvas" class="arCanas open"></canvas>
			<input type="hidden" id="grayId" value="${sessionScope.grayid}">
			<input type="hidden" id="loginName" value="${sessionScope.loginName}">
		</div>
		<!-- 右边 -->
		<div class="analyse_Right">
			<div class="innerbox">
				<div class="analyse_tab sc">							
					<div v-for="(item,index) in imageList" class="analyse_tab--item" v-show="item.isShow" :class="{'selected': item.isSelected,'normal':!item.isSelected}">
						<img :id="item.chromId" :src="item.chromEnhance">
					</div>
				</div>
			</div>
		</div>

		<!-- 处理步骤 -->
		<div class="c" style="display:none">
		<table class="step" style="display:none">
			
		</table>
		</div>
		<input type="hidden" id="contextPath" value=<%= request.getContextPath() %> > 
	</div>

</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/common/common.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/page/js/caryogram/operation/color_cycle_worker.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/page/js/caryogram/operation/color_cycle.js'></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/common/vue.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/common/db.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/caryogram/operation/nuclearAnalysis.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/page/js/caryogram/operation/smear.js'></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/html2canvas.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/lodash.core.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/jquery.contextMenu.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryexcision.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/drag.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/css/nuclearAnalysis.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/css/index.css">
<link
	href="${pageContext.request.contextPath}/page/css/jquery.contextMenu.css"
	rel="stylesheet" type="text/css" />
</html>