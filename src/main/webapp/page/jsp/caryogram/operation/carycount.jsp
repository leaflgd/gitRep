<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>核型分析</title>
	 
</head>
<%@include  file="../../plug/head.jsp"%>
<c:choose>
   <c:when test="${sessionScope.loginName == null or sessionScope.loginName == ''}">
  		   <!-- 未登录 -->
 		<c:redirect url="/page/jsp/user/login.jsp"/>
   </c:when>
   <%-- <c:when test="${sessionScope.inName == null or sessionScope.inName == ''}"> 
   		<c:redirect url="/page/jsp/caryogram/operation/carycount.jsp"/>
   </c:when> --%>
  </c:choose>
<!-- 文件卡 -->
<jsp:include  page="../../plug/folder.jsp" flush="true"/>
<body>
<!-- 核型分析页面 -->
  <div class="tab-pane" id="model_analyse">
        <div  id="model_analyse_tabulation">
        <span>日期：</span> 
        <input name="control_date2" type="text" id="control_date2" readonly placeholder="所有时间"/>    
        		  <span style="position: relative; right: 40px">至：</span>
		  <input name="control_date4" type="text" id="control_date4" readonly placeholder="所有时间" style="position: relative;right: 35px;"/>
        <span>筛选条件：</span>
        <select class="incheck" onchange = "$(this).blur();">
          <option value="0">事件名称</option>
          <option value="1">未核对</option>
          <option value="2">已核对</option>
          <option value="3">核型</option>
          <option value="4">孕龄</option>
        </select>
        <span>值：</span>
        <input type="test" class="inName"/>
        <input type="button" id="search" value="搜索"/>
       	 <ul class='liebiao' style='display:none'> 
			              <li>事件编号</li>
			              <li>分析</li>
			              <li>计数</li>
			              <li>核型</li>
			              <li>核型状态</li>
			          
		      </ul>       
      		</div>
	<div class="fade active in" id="count">
	  <div class="index_left_fileBox11 fileBox">
      <div class="innerbox7">
      </div>
      </div>
      <div style='position: relative;float:right'>
      <!-- 核型分析页面 -->
		<ul id="myTab3" class="nav nav-tabs">
			 <li>
              <a href="caryindex_new.jsp" data-toggle="tab" id='index_fenxi1'>分析</a>
              </li>
              <li class="active">
              <a href="" data-toggle="tab" id='index_jishu'>计数</a>
              </li>
	    </ul> 
        <div class="countLeft3" >
      		<div class='index_right_5'>
      		<span class='index_right_span111' >核型：<span id='fileCaryogram'></span></span>
      		<span>编号：</span><span id='fileCardId'></span><br>
      		<span>姓名：</span><span id='fileCardName'></span><br>
      		<span>性别：</span><span id='fileCardSex'></span><br>
      		<span>分析：</span><span id='fileCardAnalyze'></span><br>
      		<span>计数：</span><span id='fileCardCount'></span><br>
      		<div class='index_right_div1' ><img class='index_right_span2'></img></div>
      		</div>
      	
        <div style='position: relative;top: 282px;left: 2px' class='bear_div'> 
           <button id="bear_fruit">计数45条(<span id="sum1"></span>)</button>
           <button id="bear_fruit1">计数46条(<span id="sum2"></span>)</button>
           <button id="bear_fruit2">计数47条(<span id="sum3"></span>)</button>
           <button id="bear_fruit3">其他(<span id="sum4"></span>)</button>
           </div>
           <div class="countLeft3_fruit">
             <span style="color: #ffff00">核对结果</span>
             <input type="" name="" id="check_fruit" readonly>
             <p class="fruit1">45</p><input type="" name="" id="fruit1_1" readonly="readonly">
             <p class="fruit2">46</p><input type="" name="" id="fruit2_2"  readonly="readonly">
             <p class="fruit3">47</p><input type="" name="" id="fruit3_3"  readonly="readonly">
             <p class="fruit4">其他</p><input type="" name="" id="fruit4_4"  readonly="readonly">   
           </div>
             <div style='height:85px;border-bottom:1px solid #666666;position: relative;top:310px;width: 125ox;left: 3px'>
             <span class="images_size">图像尺寸:</span>
         <!--     <input type="radio" name="radio1" value="20" class="count_rad rad6" id="rad6">
			 <label for="rad6">20%</label> -->
			 <input type="radio" name="radio1" value="40" class="count_rad rad7" id="rad7">
			 <label for="rad7">40%</label>
			 <input type="radio" name="radio1" value="60" class="count_rad rad8" checked id="rad8">
			 <label for="rad8">60%</label>
			 <input type="radio" name="radio1" value="80" class="count_rad rad9" id="rad9">
			 <label for="rad9">80%</label>
	<!-- 	 <input type="radio" name="radio1" value="100" class="count_rad rad10" id="rad10">
			 <label for="rad10">100%</label> -->
			 </div>
			  <div style='height:125px;position: relative;top: 325px;margin: 0px 20px'>
             <img id="event_date1" src='../../../images/shijian1.png'>
             <img id="create_report" src='../../../images/shencheng.png'>

             </div>
        </div>
        <div class="countRight3">
        	<div class="innerbox">
            	
        	</div>
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
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>	
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/laydate/laydate.js"></script> 
 <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/carycount.js"></script> --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/carycountnew.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
  <link href="${pageContext.request.contextPath}/page/css/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
</html>