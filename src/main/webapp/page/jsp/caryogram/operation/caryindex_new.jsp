<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>事件核对状态</title>
</head>
<%@include  file="../../plug/head.jsp"%>
<body>
<c:if test="${sessionScope.loginName == null || sessionScope.loginName == ''}">
 <!-- 未登录 -->
 <c:redirect url="/page/jsp/user/login.jsp"/>
</c:if>
<jsp:include  page="../../plug/folder.jsp" flush="true"/>
<!-- 事件核对状态页面 -->
<div class="tab-pane" id="model_analyse">
<!-- 列表 -->
    <div class="fade in active" id="tabulation">
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
      		<div >
      <div id='index_left'>
      <!--一：文件夹区块  -->
      <div class="index_left_fileBox fileBox">
      <div class="innerbox7">
      </div>
      </div>
      		</div>
      		<div id='index_right'>
      		<div class='index_right_1'>
      		 <ul id="index_right_myTab2" class="nav nav-tabs" >
              <li class="active">
              <a href="" data-toggle="tab" id='index_fenxi'>分析</a>
              </li>
              <li class='active1'>
              <a href="" data-toggle="tab">计数</a>
              </li>
             </ul>
      		</div>
      		<div class='index_right_2'>
      		<span class='index_right_span' >核型：<span id="fileCardGrayCray"></span></span>
      		<div class='index_right_3'>
      		<div class='index_right_4'>
      		<span>编号：</span><span id="fileCardId"></span><br>
      		<span>姓名：</span><span id="fileCardName"></span><br>
      		<span>性别：</span><span id="fileCardSex"></span><br>
      		<span>分析：</span><span id="fileCardAnalyze"></span><br>
      		<span>计数：</span><span id="fileCardCount"></span><br>
      		<div class='index_right_div'><img class='index_right_span1' id="fileCardIsCheck"></img></div>
      		
      		
      		<img id='index_right_date' src='../../../images/shijian1.png'>
      		</div>
      		</div>
      		<div id='index_right_r'>

      		</div>
      		</div>
      		</div>
      		
      		</div>
      		
      		
      		

      		
      		
      		
      		
      		
      		<div class="innerbox8" >
      			<div class="tblcon">
				<table class="tabulation_tab">
		         	<tbody class="IncidentQuery">
		      		</tbody>	
		    	</table>
		    	</div>
		    	</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/common/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/laydate/laydate.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryindex.js"></script>--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryindexnew.js"></script>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
	<script>
        function  goToCrayexcision(id) {
        	window.location.href='/chromosome/chromatid/chromRequest?grayId=a'+id;
        }

        function  goToChromanalyse(id) {
            window.location.href="/chromosome/KaryotypeAnalysis/GoToChromanalyse?grayid="+id;
        }

        function NoImage(imgObject){   imgObject.style.display="none";  }
	</script>
</html>
