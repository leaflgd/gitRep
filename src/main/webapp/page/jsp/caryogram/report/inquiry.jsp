<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>查询</title>
</head>
<%@include  file="../../plug/head.jsp"%>

<body>
<c:if test="${sessionScope.loginName == null || sessionScope.loginName == ''}">
 <!-- 未登录 -->
 <c:redirect url="/page/jsp/user/login.jsp"/>
</c:if>
<!-- 层级二——查询页面 -->
  <div class="" id="inquiry">
    <ul id="myTab2" class="nav nav-tabs">
        <li class="active">
            <a href="#event_tabulation" data-toggle="tab">事件列表</a>
        </li>
        <li>
            <a href="#statistics" data-toggle="tab">统计</a>
        </li>
     </ul>
<!-- 事件列表 -->
    <div class="tab-pane fade in active" data-toggle="tab" id="event_tabulation">
      
        <label >日期：</label>
        <input name="control_date2" type="text" id="control_date1"  placeholder="所有时间" />
        <label>至</label>
        <input name="control_date2" type="text" id="control_date2"  placeholder="所有时间" />
        <label>筛选条件：</label><select id = "selectid" class="event_tabulation_select"  onchange = "$(this).blur();"><option value = "1">事件名称</option><option value = "2">核型</option><option value = "3">阅片及报告打印人</option><option value = "4">姓名</option><option value = "5">病历号</option></select>
        <label>值：</label><input type="" name="" id = "price"  onKeyUp="">
        <button id="search2">搜索</button>
        <button id="search3">导出Excel</button>
          <div class='tab_head'>
              <div>采集日期</div><div>事件</div><div>病历号</div><div>姓名</div><div>性别</div><div>年龄</div><div>临床诊断</div><div>核型</div><div>阅片及报告打印人</div><div>审核人</div><div>校片人</div><div>报告日期</div>    
   </div> 
      <div class="tabulation_show">
        <table class="event_tabulation_tab">
          <!-- <thead>
            <tr>
              <th>采集日期</th>
              <th>事件</th>
              <th>病历号</th>
              <th>姓名</th>
              <th>性别</th>
              <th>年龄</th>
              <th>临床诊断</th>
              <th>核型</th>
              <th>阅片及报告打印人</th>
              <th>审核人</th>
              <th>校片人</th>
              <th>报告日期</th>
            </tr>
          </thead> -->
          <tbody class= "inenerbox0">
          </tbody>
        </table>
      </div>  
      
    </div>
<!-- 统计 -->
     <div class="tab-pane fade" data-toggle="tab" id="statistics">
         <label >日期:</label>
         <input name="control_date2" type="text" id="control_date3"  readonly="readonly" placeholder="所有时间" />
         <label>至</label>
         <input name="control_date2" type="text" id="control_date4"  readonly="readonly" placeholder="所有时间" />
          <button id="search4">搜索</button>
          <div class="statistics_show">
          <div class="staticon">
            <table class="statistics_tab">
              <thead>
                <tr>
                  <th>姓名</th>
                  <th>数量</th>
                </tr>
              </thead>
              <tbody class= "inenerboxs">
               </tbody>
            </table>
            </div>
          </div>
     </div>
</body>
	

  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/laydate/laydate.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/caryogram/report/inquiry.js"></script>          
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css"> 
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/index.css">
</html>