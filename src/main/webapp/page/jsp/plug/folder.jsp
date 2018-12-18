<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>文件卡</title>
</head>
<body>
	<div class="file_card" >
		<form id="yourformid" onsubmit="return false">
		<img alt="" class='card_close' src="../../../images/close.png">
       	<p>文件卡</p>
       	<!-- 隐藏值 -->
       	<input name="cardOpinionStatus" id="opinionStatus" style="display:none">
       	<input name="reportDateStatus" id="reportDate_status" style="display:none">
           <span class="even_number1">事件编号:</span><br>
           <input name="evenNumber" id="even_number" readonly="readonly">
           <span class="specimen_source">样本来源:</span>
           
   		   <select  id="specimen_source1" onchange="this.parentNode.nextSibling.value=this.value">
       		   <option value=" "> </option>
       		   <option value="外周血">外周血</option>
       		    <option value="羊水">羊水</option>
       		   <option value="脐血">脐血</option>
   		   </select>
   		   <input id = "specimen_source" class="yb"  name="specimenSource" ><br>
	     <!--   <input name="box" id = "specimen_source"  style="" value="外周血"><br> -->
	       <!-- <input type="text" name="" id="specimen_source" value = "外周血"> -->
           <span class="case_num">病历号:</span><br>
           <input name="medicalRecord" id="medical_record" maxlength="10"><br>
           <span class="name">姓名:</span>
           <span class="age">年龄:</span>
           <span class="sex">社会性别:</span>
           <span class="pregnancy">孕龄:</span>
           <input type="text" id = "pregnancys" name="pregnancys" class="pregnancy_1"><br>
           <span class="wk"></span>
           <input type="" name="name" id="name" maxlength="20">
           <input type="" name="age" id="age" maxlength="11">
           <input type="text" name="sex" id="sex">
           <span class="gather_date">采集日期:</span>
           <input type="" name="pregnancy" id="pregnancy"><span class="week">W</span><br>
           

           
           <span class="vaccinate_date">接种日期:</span><br>
           <input id="gather_date" name="gatherDate"></input>
           <!--<select id="vaccinate_date1" style="" onchange="this.parentNode.nextSibling.value=this.value">
              <!--  <option value="添加">添加</option>
		      <option value="删除">删除</option>
		   </select>-->
		   <input id="vaccinate_date" name="vaccinateDate"><br>


           <span class="clinical_diagnosis">临床诊断:</span><br>
           <select  id="diagnose1" >
           		<option value=" "> </option>
               <option>继发不孕</option>
               <option>原发不孕</option>
               <option>染色体复查</option>
               <option>IVF-ET术前</option>
               <option>ICSI术前</option>
               <option>自然流产查因</option>
               <option>CCS-PGD术后</option>
               <option>FISH-PGD术后</option>
               <option>ICSI术后</option>
               <option>PGS术后</option>
               <option>复发性流产</option>
               <option>习惯性流产</option>
               <option>不良孕史</option>
               <option>不良妊娠史、生育史</option>
               <option>生化妊娠史</option>
               <option>胚胎停育查因</option>
               <option>原发闭经</option>
               <option>继发闭经</option>
               <option>小睾查因</option>
               <option>极度少弱精症查因</option>
               <option>弱畸精子症</option>
               <option>阻塞性无精</option>
               <option>智低查因</option>
               <option>第二性征发育欠佳查因</option>
               <option>平衡易位携带者</option>
               <option>排除染色体异常</option>
               <option>卵巢早衰</option>
               <option>宫内妊娠</option>
               <option>阴茎短小，藏匿阴茎</option>
               <option>原因不明不孕</option>
               <option>不孕症</option>
               <option>原发不孕、智力低下</option>
               <option>先天性白内障</option>
          <!--      <option value="添加">添加</option>
    		   <option value="删除">删除</option> -->
           </select>
           <input id="diagnose" name="diagnose" ><br>


           <span class="opinion">意见:</span><br>
           <select  id="suggestion1" >
           	   <option value=" "> </option>
               <option>普通G显带未见异常</option>
               <option>高分辨率G显带未见异常</option>
               <option>为罗氏易位携带者,可考虑行FISH-PGD或PGD-CCS,建议来我院看遗传咨询专家门诊</option>
               <option>为克氏征,建议来我院看遗传咨询专家门诊</option>
               <option>为21三体综合征,建议来我院看遗传咨询专家门诊</option>
               <option>为平衡易位携带者,可考虑行FISH-PGD或PGD-CCS,建议来我院看遗传咨询专家</option>
               <option>为嵌合体,建议来我院看遗传咨询专家门诊</option>
               <option>为9号染色体次缢痕区臂间倒位。在临床上有争议,按《人类染色体命名 的国际体制（ISCN2013版）》</option>
               <option>为Y染色体倒位,在临床上有争议,按《人类染色体命名 的国际体制（ISCN2013版）》</option>
               <option>为47,XY,+mar,建议来我院看遗传咨询专家门诊</option>
               <option>为47,XX,+mar,建议来我院看遗传咨询专家门诊</option>
               <option>为15p+,建议行FISH做进一步检测</option>
               <option>为母源性平衡易位,建议来我院看遗传咨询专家门诊</option>
               <option>为47,XYY,建议来我院看遗传咨询专家门诊</option>
               <option>为DSD（性发育异常）,建议来我院看遗传咨询专家门诊</option>
               <option>为复杂易位携带者,建议行PGD-CCS,建议来我院看遗传咨询专家门诊</option>
               <!--  <option value="添加">添加</option>
    		   <option value="删除">删除</option>-->
           </select>
           <input id="suggestion" name="suggestion" maxlength="200"><br>


           <span class="the_producers">制片人:</span>
           <span class="put_a_seal_on">阅片人及打印人:</span>
           <span class="nucleus">校片人:</span>
           <span class="examine_and_verify">审核人:</span><br>
           <select id="the_producers1">
           		<option value=" "> </option>
				<option value="刘鑫、郭永腾、宋璇">刘鑫、郭永腾、宋璇</option>
				<option value="郑清、张晴、胡晓宇">郑清、张晴、胡晓宇</option>
				<option value="王琪、熊巧媛、聂双双">王琪、熊巧媛、聂双双</option>
				<option value="张桦、王银辉、张丹君">张桦、王银辉、张丹君</option>
				<option value="莫兰香、谢春波、徐芳">莫兰香、谢春波、徐芳</option>
				<option value="杨强、肖敦、佘雪妮">杨强、肖敦、佘雪妮</option>
				<option value="张俊">张俊</option>
				<option value="石正英">石正英</option>
            	<!--  <option value="添加">添加</option>
		        <option value="删除">删除</option>-->
		   </select>
		   <input id="the_producers" name="theProducers" maxlength="20">

           <select  id="put_a_seal_on1" style="position: absolute;">
           				<option value=" "> </option>
						<option value="王银辉">王银辉</option>
						<option value="张俊">张俊</option>
						<option value="王琪">王琪</option>
						<option value="郑清">郑清</option>
						<option value="张桦">张桦</option>
						<option value="刘鑫">刘鑫</option>
						<option value="莫兰香">莫兰香</option>
						<option value="杨强">杨强</option>
						<option value="佘雪妮">佘雪妮</option>
						<option value="肖敦">肖敦</option>
						<option value="郭永腾">郭永腾</option>
						<option value="谢春波">谢春波</option>
						<option value="张晴">张晴</option>
						<option value="徐芳">徐芳</option>
						<option value="谭沁">谭沁</option>
						<option value="聂双双">聂双双</option>
						<option value="万振兴">万振兴</option>
						<option value="戴婧">戴婧</option>
						<option value="张丹君">张丹君</option>
						<option value="易朵">易朵</option>
						<option value="熊巧媛">熊巧媛</option>
						<option value="张毅">张毅</option>
						<option value="胡晓宇">胡晓宇</option>
						<option value="宋璇">宋璇</option>
						<option value="罗爱祥">罗爱祥</option>
						<option value="陈茂盛">陈茂盛</option>
						<option value="李海玉">李海玉</option>
						<option value="王涵铎">王涵铎</option>
						<option value="涂超峰">涂超峰</option>
						<option value="石正英 王琪">石正英 王琪</option>
		            	<!--  <option value="添加">添加</option>
				        <option value="删除">删除</option>-->
		   </select>
		   <input id="put_a_seal_on" name="putAsealOn" maxlength="20">


           <select id="examine_and_nucleus1">
           	
           	
           		<option value=" "> </option>
           		<option value="狄玉芬">狄玉芬</option>
           		<option value="杨强">杨强</option>
           		<option value="谭跃球 研究员">谭跃球 研究员</option>
				
				
            	<!--  <option value="添加">添加</option>
		        <option value="删除">删除</option>-->
		   </select>
		   <input id="examine_and_nucleus"  name="examineAndNucleus" maxlength="20">

           
           <select id="examine_and_verify1">
           
           		<option value=" "> </option>
           		<option value="谭跃球 研究员">谭跃球 研究员</option>
           		<option value="钟昌高 副主任医师">钟昌高 副主任医师</option>
				
            	<!-- <option value="添加">添加</option>
		        <option value="删除">删除</option> -->
		   </select>
		   <input id="examine_and_verify" name="examineAndVerify" maxlength="20"><br>


           <span class="report_date">报告日期:</span>
           <span class="check">核对:</span><br>
        
           <!--  <select class="dt" id="report_date1" style="" onchange="this.parentNode.nextSibling.value=this.value">
            	
		   </select>-->
		   
		   
		   <input id="report_date" name="reportDate">
         
           <!--<select id="check1" style="" onchange="this.parentNode.nextSibling.value=this.value">
            	
		   </select>-->
		   <input id="check" name="check" maxlength="64"><br>



           <span class="whole">中期(总数,计数的,分析的):</span>
           <span class="caryogram">核型:</span><br>
           <input name="whole" id="whole" readonly="readonly">
           <input name="caryogram" id="caryogram" maxlength="200"><br>
           
	       <button id="ascertain">确定</button>
        	</form>
        </div> 
</body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/folder.js"></script> 
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/folder.css">
</html>