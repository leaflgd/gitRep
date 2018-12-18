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
<!-- 事件核对状态页面 -->
<div class="tab-pane" id="model_analyse">
<!-- 列表 -->
    <div class="fade in active" id="tabulation">
      <div  id="model_analyse_tabulation">
        <span>日期：</span> 
        <input name="control_date2" type="text" id="control_date2" readonly placeholder="所有时间"/>    
        <span>筛选条件：</span>
        <select class="incheck" onchange = "$(this).blur();">
          <option value="">事件名称</option>
          <option value="0">未核对</option>
          <option value="1">已核对</option>
          <option value="2">核型</option>
        </select>
        <span>值：</span>
        <input type="test" class="inName"/>
        <input type="button" id="search" value="搜索"/>
       	 <ul class='liebiao'> 
			              <li>事件编号</li>
			              <li>分析</li>
			              <li>计数</li>
			              <li>核型</li>
			              <li>核型状态</li>
			          
		      </ul>       
      		</div>
      		<div class="innerbox8">
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/laydate/laydate.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/operation/caryindex.js"></script>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
</html>
