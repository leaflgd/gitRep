<%@ page language="java" contentType="text/html; charset=Utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>染色单体分析</title>
</head>
<c:choose>
	<c:when
		test="${sessionScope.loginName == null or sessionScope.loginName == ''}">
		<!-- 未登录 -->
		<c:redirect url="/page/jsp/user/login.jsp" />
	</c:when>
	<c:when
		test="${sessionScope.inName == null or sessionScope.inName == ''}">
		<c:redirect url="/page/jsp/caryogram/operation/caryindex.jsp" />
	</c:when>
	<c:when
		test="${sessionScope.grayid == null or sessionScope.grayid == ''}">
		<c:redirect url="/page/jsp/caryogram/operation/caryanalyse.jsp" />
	</c:when>
</c:choose>
<%@include file="../../plug/head.jsp"%>
<jsp:include page="../../plug/folder.jsp" flush="true" />
<body>
	<!-- 染色单体分析页面 -->
	<%@include file="../../plug/crayhead.jsp"%>
	<div class="fade in" id="analyse">
		<input id="PageContext" type="hidden" style="display: none;"
			value="${pageContext.request.contextPath}" />
		<div class="analyse_Left1">
			<div style="display:none">
				<canvas id="dstcanvasOutput"></canvas>
			</div>
			<div style="display:none">
				<canvas id="enhanceOutput"></canvas>
			</div>
			<div id="model_temp" style="display: none">
				<div class="rx">
					<!-- <div class="rx-l"></div>
      <div class="rx-r"></div> -->
					<div class="rxc-1 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">13</span>
						</div>
					</div>
					<div class="rxc-2 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">12</span>
						</div>
					</div>
					<div class="rxc-3 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">11.2</span>
						</div>
					</div>
					<div class="rxc-4 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">11.1</span>
						</div>
						<img src="../../../images/wn.png">

					</div>
					<div class="rxc-5 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">12</span>
						</div>
					</div>
					<div class="rxc-6 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">21</span>
						</div>
					</div>
					<div class="rxc-7 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">21</span>
						</div>
					</div>
					<div class="rxc-8 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">22</span>
						</div>
					</div>
					<div class="rxc-9 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">23</span>
						</div>
					</div>
					<div class="rxc-10 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">24</span>
						</div>
					</div>
					<div class="rxc-11 pos-r">
						<div class="tip">
							<span class="line"></span> <span class="num">25</span>
						</div>
					</div>

				</div>
			</div>
			<div id="chScreenshot" class="unselect">
				<div class="flex-contentout" >
					<!--9-3修改-->
					<div class="out-flex1 flexbox">
						<div class="flex-item1 item">
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark1">

									
									</div>
								</div>
								<p>1</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark2">

										
									</div>
								</div>
								<p>2</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark3">

										
									</div>
								</div>
								<p>3</p>
							</div>
							<div class='filling1'></div>
							<!-- <div class="flex-item2 item"> -->
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark4">

										
									</div>
								</div>
								<p>4</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark5">

										
									</div>
								</div>
								<p>5</p>
							</div>
							<!-- </div> -->
						</div>
					</div>
					<div class="out-flex2 flexbox">
						<div class="flex-item2 item">
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark6">

										
									</div>
								</div>
								<p>6</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark7">

										
									</div>
								</div>
								<p>7</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark8">

										
									</div>
								</div>
								<p>8</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark9">

										
									</div>
								</div>
								<p>9</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark10">

										
									</div>
								</div>
								<p>10</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark11">

										
									</div>
								</div>
								<p>11</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark12">

										
									</div>
								</div>
								<p>12</p>
							</div>
						</div>
					</div>
					<div class="out-flex3 flexbox">
						<div class="flex-item3 item">
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark13">

										
									</div>
								</div>
								<p>13</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark14">

										
									</div>
								</div>
								<p>14</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark15">

										
									</div>
								</div>
								<p>15</p>
							</div>
							<div class='filling2'></div>
							<!-- <div class="flex-item5 item"> -->
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark16">

										
									</div>
								</div>
								<p>16</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark17">

										
									</div>
								</div>
								<p>17</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark18">

										
									</div>
								</div>
								<p>18</p>
							</div>
							<!-- </div> -->
						</div>
					</div>
					<div class="out-flex4 flexbox">
						<div class="flex-item4 item">
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark19">

										
									</div>
								</div>
								<p>19</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark20">

										
									</div>
								</div>
								<p>20</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark21">

										
									</div>
								</div>
								<p>21</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark22">

										
									</div>
								</div>
								<p>22</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark23">

										
									</div>
								</div>
								<p>X</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="mark24">

										
									</div>
								</div>
								<p>Y</p>
							</div>
							<div class="inflex">
								<div class="ac">
									<div class="wys"></div>
									<div id="markan"></div>
								</div>
								<p>?</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="analyse_Left1_tab_div">
			<div class="dec"></div>
			<div class="analyse_Left1_tab_div_1" id="chromAbnormal"></div>
		</div>
		<div class="analyse_bottom">
			<p class="number1"></p>
			<p class="id1">1-A</p>
			<p class="position1"></p>
			<input type="text" class="order_number1">
			<input type="hidden" id="grayId" value="${sessionScope.grayid}">
			<button id="retrun1">返回</button>
			<button id="ascertain1">确定</button>
			<button id="update">更新核型</button>

		</div>
	</div>

	<div class="analyse_Right1">
		<div class="analyse_Right1_box"></div>
		<div id="annotate_model">
			<p id="model_400">400条带</p>
			<p id="model_550">550条带</p>
			<p id="model_850">850条带</p>
		</div>
		<div id="report_show1">
			<p id="report1">外周血正常异常</p>
			<p id="report2">外周血正常、异常高分辨</p>
			<p id="report3">外周血异常-审核人</p>
			<p id="report4">羊水</p>

		</div>
		<div class="analyse_btn">
			<button id="revolve">旋转180°</button>
			<button id="images">镜像</button>
			<button id="reduce">缩小</button>
			<button id="amplify">放大</button>
			<button id="duplicate">复制</button>
			<button id="adjust">颜色调整</button>
			<div class="rad_2">
				<input id="range" type="range" min="0" max="100" value="50" step="1" onchange="changColor()">
			</div>
			<button id="annotate">核型注解</button>
			<button id="general_survey">核型一览</button>
			<button id="event_date">事件数据</button>
			<button id="report">生成报告</button>
			<button id="cut_apart">分割</button>
		</div>

	</div>
	<input type="hidden" id="contextPath" value=<%= request.getContextPath() %> >
	<div id="chBabyPicSrc"
		style="border: 2px solid; width: 1024px; height: 905px;">
		<img id="chPicSrc" src="" />
	</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/caryogram/operation/chromimage.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/common/opencv.js"></script>
<script type="text/javascript">
	var isOpencvFlag = false;
	function onOpenCvReady() {
		isOpencvFlag = true;
	}
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/lodash.core.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/common/common.js"></script>

<!-- 9-3修改-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/html2canvas.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/jquery.contextMenu.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/roat.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/plug/layer/layer.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/caryogram/operation/chromanalyse.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/page/js/caryogram/operation/karyotypetips.js"></script>


<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/page/css/index.css">
</html>