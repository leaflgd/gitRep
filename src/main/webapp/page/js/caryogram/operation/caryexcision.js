//20180702
// 分别画线与涂抹
var status = 0;
var currentImage = {};
//点击ctrl时 鼠标左键判断的值
var ac = 0;
var oContext;
var drawSmearContext;
var canvasOffscreen;
var canvasX,canvasY;


$(function() {
	var op = {
		line: true,
		arc: false,
		ctrl: false
	};
	var currentOp = {
		line: false,
		arc: false,
		ctrl: false		
	}
	// 图片地址
//	var urls = "/chromosome/page/images/patientdata";

	// 页面按钮效果
	$("#model_analyse1").addClass("active");
	$("#li_analyse_show_a").addClass("active");

	// 分割页面初始化
	$(document).ready(function() {
		//init();
		//initNode();
		drawSmear();
		$("#chuli").addClass("btncss");
		$("#chuli").find('i').css({"color":"009ae0","background":"url('../../../../page/images/patientdata/blue/chuli.png')"});
	});
	function saveCurretnOp(line, arc, ctrl){
		currentOp.line = line;
		currentOp.arc = arc;
		currentOp.ctrl = ctrl;
	}
	// 轮廓
	function drawSmear(){
		if(drawSmearContext){
			return;
		}
		var myCanvas = $("#myCanvas");
		canvasOffscreen = document.createElement('canvas');
		canvasOffscreen.id = "drawSmear";
		$(canvasOffscreen).attr("width",myCanvas.attr("width"));
		$(canvasOffscreen).attr("height",myCanvas.attr("height"));
		drawSmearContext = canvasOffscreen.getContext('2d');
		drawSmearContext.translate(0.5, 0.5)
		$(canvasOffscreen).addClass("drCanas b");
		$("#chuli1").append(canvasOffscreen);
		return drawSmearContext;
	}
	function backStepOp(status){
		for(var i = drawOpList.length - 1; i>=0; i--){
			if(status == 0){
				drawOpList.splice(i,1);
				break;
			}else if(status == 1){
				drawOpList.splice(i,1);
				break;
			}
		}
		drawSmearContext.clearRect(0, 0, arContext.canvas.width,arContext.canvas.height);
		var lastPoint = _.last(drawOpList);
		if(!lastPoint){
			return;
		}
		var returnPointList = lastPoint.returnPointList;
		drawLineForReturn(drawSmearContext,returnPointList,lastPoint.length);
	}
	

	function init() {
		$.ajax({
			url : nodeUrl + '/chromImage/init',
			type : 'get',
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : {
				greyId:$("#grayId").val()
			},
			success : function(js) {
				console.log(js["label"]);	
					$("#pic1").attr("src",urls+js["label"].grayUrl);
					$("#analyse_Left_img_data").text(js["label"].inName+" "+js["label"].slideName+" "+js["label"].grayNumber)
/*				loader(js["ch"]);
				$("#check_fruit2").val($(".analyse_tab img").length);*/
				
				var u2 = js["label"].inName;
				var u3 = js["label"].grayNumber;
				var u4 = js["label"].slideName;
				
				$.ajax({ 
					url:'/chromosome/chromatid/grayQuerys2', 
			        type:'post',
			        data:JSON.stringify("c"),
			        contentType: "application/json;charset=utf-8",
			        dataType:"json",
			        success:function(js){
			        	console.log(js);
						
						console.log(u2+"-"+u3+"-"+u4)
						//根据拨片号判断是否勾选分析
			        	var check = 0;
			        	var check1 = u4+','+u3;
			        	
			        	for ( var a in js) {
			        		var check2 = js[a].slideName+','+js[a].grayNumber;
			        		if(check1 == check2){
			        			check = 1;
			        		}
			        	}
			        	
			        	console.log("check:"+check);
			        	if(check==1){
			        		console.log("check56466565");
			        		$("#analyse_Left_img_data").css({"color":"rgb(0, 160, 233)","cursor":"pointer"});
			        		$("#analyse_Left_img_data").attr("name","aa")
			        	}else{
			        		$("#analyse_Left_img_data").css("color","#ffffff");

			        	}

			        }
				});
			}
		});
	}
	//小图页面加载器
	function loader(js) {
		var ht = "";
		var k;
		var index = 0;
		//获取图片的个数
		for ( var i in js) {
			index++;
		}
		var j=0;
		if(index>40){
			j=(index/5)*6;
			index=0;
		}else{
			j=48;
			index=0;
		}
		for (var h = 0; h < j; h++) {
			k = h % 6;
			if (k == 0) {
			/*	ht += "<div class='analyse_tab--item normal'>"*/
			} else {
				if (js[index] != undefined) {
					ht += "<div class='analyse_tab--item normal'><img id='"
							+ js[index].chromId
							+ "' src='" + urls
							+ js[index].chromEnhance
							+ "'></div>";
					index++;
				} else {
					ht += "<div></div>";
				}
			}
			if (k == 5) {
				ht = ht + "</div>"
			}
		}
		if (k != 5) {
			ht = ht + "</div>"
		}
		$(".analyse_tab").html("");
		$(".analyse_tab").html(ht);
	
	}

	//原始图像按键
	$("#yuanshi").click(function() {
		$(".step").css("display", "none");
		ca = 0;
		$.ajax({
			url : '/chromosome/chDivision/greyImageConversion',
			type : 'post',
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify({'grayStatus':"yuanshi"}),
			dataType : "json",
			success : function(js) {
				for ( var i in js) {
					
					$("#pic1").attr("src",urls + js[i]);
					$("#pic1").css("width","900px");
				}
				$("#analyse_Left_img").css("border","none");
			}
		});
	});
	//增强图像按键
	$("#chuli").click(function() {
		$(".step").css("display","none");
		clickStatuss=false;
    	connect = 0;	
    	$(".analyse_tab--item.normal").removeAttr('id');
	   index = 0;
		$(".analyse_Left button").not("#chuli").removeClass('btncss');
		$("#yuanshi").find('i').css({"background":"url('../../../../page/images/patientdata/white/yuanshi.png')"});
		$("#copy").find('i').css({"background":"url('../../../../page/images/patientdata/white/copy.png')"});
		$("#procedure").find('i').css({"background":"url('../../../../page/images/patientdata/white/procedure.png')"});
		$("#delete").find('i').css({"background":"url('../../../../page/images/patientdata/white/delete.png')"});
		
		if($(this).attr("class")=='btncss'){
			
			$(this).removeClass('btncss');
			$(this).css("color","#fff");
			$(this).find('i').css({"background":"url('../../../../page/images/patientdata/white/chuli.png')"})
		}else{
			
			$(this).addClass('btncss');
			$(this).css("color","#009ae0");
			$(this).find('i').css({"background":"url('../../../../page/images/patientdata/blue/chuli.png')"})
		}
		$(".step").css("display", "none");
		ca = 0;
		$.ajax({
			url : '/chromosome/chDivision/greyImageConversion',
			type : 'post',
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify({'grayStatus':"chunli"}),
			dataType : "json",
			success : function(js) {
				for ( var i in js) {
					$("#pic1").attr("src",urls + js[i]);
//					$(".analyse_Left_img").attr("style","background:url('" + 
//								urls + js[i]+ "') no-repeat");
				}
				$("#analyse_Left_img").css("border","2px solid #000000");
				$("#pic1").attr("style","");
			}
		});
	});
	
	// 返回按钮
//	$("#retrun").click(function() {
//		window.history.go(-1);
//	});

	// 分割的处理步骤
	var ca = 0;
	//step用于保存删除处理步骤的id
	var step = '';
	$("#procedure").click(function() {
		if(ca==0){
			$(".c").css("display", "block");
			$(".step").css("display", "block");
			ca=1;
			$.ajax({
				url : '/chromosome/chDivision/processStepsQuery',
				type : 'post',
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(),
				dataType : "json",
				success : function(js) {
					var ht="";
					for ( var i in js) {
						ht+="<tr id='" +
						js[i].logId+"'><td" +
								">"+js[i].logName
						+"</td></tr>";
					}
					$(".step").html(ht);
					//$("#check_fruit2").val($(".analyse_tab img").length);
					step = $(".step tr").parent().children().last().attr("id");
				}
			});
			
			
		}else{
			$(".step").css("display", "none");
			ca=0;
			$(".step").html("");
			$('.analyse_tab .analyse_tab--item').attr("class","analyse_tab--item normal");
			$(".zhezhao").css("display","none");
			
		}
	});

	//关闭canvas框
$(".del img").click(function(){
	$.ajax({
		url:'/chromosome/chDivision/chCoordinateRepealAll',
		type:'post',
		data:{imga:imga},
        dataType:"json",
        success:function(data){
         /*   var c = document.getElementById("myCanvas");
            var ct = c.getContext('2d');
            ct.height = ct.height;*/
            /*var c = document.getElementById("myCanvas").getContext('2d');
            c.clearRect(0,0,c.widht,c.height);
            var img = new Image();
            img.src = "";
            c.drawImage(imgObj, 0, 0);*/
        	$("#chuli1").css("visibility","hidden");
        	$(".zhezhao").css("display","none");
        },
	});
	
});
	//处理步骤左键点击查询
	$(".step").on("click","tr",function(){
		$('.analyse_tab .analyse_tab--item').attr("class","analyse_tab--item normal");
		$(".step tr").css("color","#fff");
		$(this).css("color","009ae0")
		console.log("处理步骤id"+$(this).attr("id"));
		$.ajax({
			url : '/chromosome/chDivision/processStepsClickQuery',
			type : 'post',
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify({'logid':$(this).attr("id")}),
			dataType : "json",
			success : function(js) {
//				console.log(js['url']);
//				console.log(js['ids']);
				for ( var key in js) {
					if(key=='url') $("#pic1").attr("src",urls+js[key]);
					if(key=='ids'){
						for ( var i in js[key]) {
//							console.log(js['ids'][i]);
							//$("#"+js['ids'][i]).parent().css("border","2px solid red")
							$("#"+js['ids'][i]).parent().attr("class","analyse_tab--item red")
						}
					}
				}
//				$("#pic1").attr("src","http://192.168.80.64:8080/images/image/patient/"+js['url']);
			}
		});
	})
	//处理步骤右键删除
	$.contextMenu({
		selector : '.step tr',
		callback : function() {
			$.ajax({
				url : nodeUrl + '/chromImage/processStepsDelete',
				type : 'post',
				data:{
					 logId:$(this).attr("id"),
					 greyId:$("#grayId").val(),
				},
				success : function(js) {
					if(js["no"]==null){
						shanchu(js)
					}
					else{
						var sss="请删除";
						for ( var i in js["no"]) {
							sss+=js["no"][i]+","
						}
						alert(sss);
					}
					$('.analyse_tab .analyse_tab--item').css("border","1px solid #000000")
					//$("#check_fruit2").val($(".analyse_tab img").length);
				}
			});
		},
		items : {
			"copy" : {
				name : "删除",
				icon : "删除"
			}
		}
	});
	
	//鼠标移上去触发染色体移动
	$('.analyse_Left_img').on("mouseover",'img:not(":first")',function(){
		$(this).myDrag({
			randomPosition : false,
			direction : 'all',
			handler : false
		});
	});
	
    $(document).on("click",".check",function(){
  	 
		if($(this).prop("checked")==true){
			$(this).prop("checked",false);
		}else{
			
			$(this).prop("checked",true);
		}
       });
		$('.analyse_tab').on("mousedown",".analyse_tab--item",function(){
			if($(this).find(".check").prop("checked")==true){
				$(this).find(".check").prop("checked",false);
			}else{
				$(this).find(".check").prop("checked",true);
			}
		});
	
	

	// 批量删除
	var del = 0;
	$("#delete").click(function() {
		
		$("button").attr("disabled", true);
		$("#delete").attr("disabled", false);
		del++;
		  if($(this).attr("class")=='btncss'){
          	qwer = 1;
          }else{
          	qwer = 0;
          }
		if (del%2!=0) {
			qwer = 1;
			$("#yes_delete").attr("disabled",false);
			var appbox = "";
			appbox += '<div class="caryogram_img_analyse">';
			appbox += '<input class="check" type="checkbox" name="img">';
			appbox += '</div>'
			$(".analyse_tab .analyse_tab--item img").before(appbox);
			
			
		} else {
			$(".caryogram_img_analyse").remove();
			$("button").removeAttr("disabled");
			$("#yes_delete").attr("disabled", true);
			qwer = 0;
			
		}
       $(".check").prop("checked",false);
		
	});
	// 确定删除
	$("#yes_delete").click(function() {
		$(".analyse_tab .analyse_tab--item").removeAttr("id");
		$(".analyse_tab .analyse_tab--item").removeClass("red");
		if(del!=0){
			console.log("666666");
			var chk_chrom = [];
			$("input[name='img']").each(function() {
				if ($(this).prop("checked")==true) {
				chk_chrom.push($(this).parent().siblings('img').attr("id"));
				}
			});
			$.ajax({
				url : '/chromosome/chDivision/chromDelete',
				type : 'post',
				data : JSON.stringify({
						'chromid' : chk_chrom
					}),contentType : "application/json;charset=utf-8",
						dataType : "json",
						success : function(js) {
							for ( var key in js) {
								if (key == '0') {
//													location.reload();
									$("#yes_delete").removeClass('btncss');
									$("#yes_delete").css('color','#999999');
									$("#yes_delete i").css({'background' : 'url("../../../../page/images/patientdata/gary/yes_delete.png")'})
								} else if (key == '1') {
									$("#yes_delete").removeClass('btncss')
									$("#yes_delete").css('color','#999999')
									$("#yes_delete i").css({
														'background' : 'url("../../../../page/images/patientdata/gary/yes_delete.png")'
									})
									alert("删除失败");
								} else {
									$("#yes_delete").removeClass('btncss')
									$("#yes_delete").css('color','#999999')
									$("#yes_delete i").css({
										'background' : 'url("../../../../page/images/patientdata/gary/yes_delete.png")'
									})
									alert("删除异常");
								}
								//loader(js[key]);
								nuclearAnalysisApp.resetImageList(js[key]);
								//$("#check_fruit2").val($(".analyse_tab img").length);
							}
						}
					});
			qwer = 0;
			$("button").removeAttr("disabled");
			$("#yes_delete").attr("disabled",
					true);
			$(".caryogram_img_analyse").css(
					"display", "none");
		}
		del = 0;
	});
	// 撤销按钮
	$("#clear1").click(function() {
		// 撤销canvas
		$.ajax({
			url : '/chromosome/chDivision/chCoordinateRepeal',
			type : 'post',
			data : JSON.stringify(),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(res) {
				if(!res || res.code != 0){
					alert("撤销失败。");
					return;
				}
				if(status == 1 || status == 0){
					if(_.isEmpty(drawOpList)){
						//关闭遮罩
						$(".zhezhao").css("display","none");
						$("#chuli1").css("visibility","hidden");
						click= false;
					}else{
						backStepOp(status);
					}
				}else{
					//关闭遮罩
					$(".zhezhao").css("display","none");
					$("#chuli1").css("visibility","hidden");
					clickStatus = false;
					//message = new Array();
				}
//				alert("撤销完成");
//				if(js[0]!=null){
//					if(status == 1 || status == 0){
//						if(_.isEmpty(drawOpList)){
//							//关闭遮罩
//							$(".zhezhao").css("display","none");
//							$("#chuli1").css("visibility","hidden");
//							clickStatus = false;
//						}else{
//							backStepOp(status);
//						}
//						
//						return;
//					}
//					clickStatus = false;
//				}else{
//					//关闭遮罩
//					$(".zhezhao").css("display","none");
//					$("#chuli1").css("visibility","hidden");
//					clickStatus = false;
//					if(oContext != null){
//						oContext.clearRect(0, 0, 800, 800);
//						oContext = null;
//					}
//					message = new Array();
//				}
			}
		});
	});
	


	// 分割保存提交
	$("#save").click(function() {
//		alert("保存操作");
		//关闭遮罩
		
		$(".zhezhao").css("display","none");
		// 保存canvas
		$.ajax({
			url : nodeUrl + '/chromImage/generateCutImage',
			type : 'get',
			data : {
				chromId:currentImage.chromId,
				greyId:$("#grayId").val(),
				loginName:$("#loginName").val()
			},
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(res) {
				if(res.code!='0'){
//					alert("分割成功");
					alert("分割异常");
				}
				
				nuclearAnalysisApp.addImageList(res.data,currentImage.chromId);
				//$("#check_fruit2").val($(".analyse_tab img").length);
			}
		});
		
		$("#chuli1").css("visibility","hidden");
		clickStatus = false;
		if(oContext != null){
			oContext.clearRect(0, 0, 800, 800);
			oContext = null;
		}
		message = new Array();
	});
	
	// 点击td弹出canvas手动分割框
	$("#settingOut").attr("zt","1");
	$("#smear").attr("zt","0")
    function outlineShow(th){
		//th.parent().css({'border-top': '1px solid blue','border-right': '1px solid blue','border-bottom': '1px solid blue','border-left': '1px solid blue'})
		th.attr("id")
		$.ajax({
			url:'/chromosome/chDivision/chromOutlineShow',
			type:'post',
			data:JSON.stringify({'chromId':th.attr("id")}), 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		for ( var i in js) {
					$("#pic1").attr("src",urls + js[i]);
				}
		 		
		 	}
		});
		
//		console.log("激活"+th.attr("id"));
	}
	
	
	//连接按钮
	$("#copy").click(function(){
		analyse_Left_button1 = '';
		analyse_Left_button2 = '';
		index = 0;
		 
		 ca = 0;
		$(".step").css("display","none");
		$(".analyse_Left button").removeClass('btncss');
		$("#yuanshi").find('i').css({"background":"url('../../../../page/images/patientdata/white/yuanshi.png')"});
		$("#chuli").find('i').css({"background":"url('../../../../page/images/patientdata/white/chuli.png')"});
		$("#procedure").find('i').css({"background":"url('../../../../page/images/patientdata/white/procedure.png')"});
		$("#delete").find('i').css({"background":"url('../../../../page/images/patientdata/white/delete.png')"});
		    if(connect==0){
		    	$(this).addClass("btncss");
		    	clickStatuss=true;
		    	connect = 1;
		    	console.log('1');
		    	
		    }else{
		    	$(this).removeClass("btncss");
		    	$(this).css("color","#fff")
		    	$(this).find('i').css({"background":"url('../../../../page/images/patientdata/white/copy.png')"});
		    	clickStatuss=false;
		    	connect = 0;
		       $(".analyse_tab .analyse_tab--item").removeAttr("id");
		    }
	});


	
//	键盘控制控制键
	$(document).keydown(function(event){
		if(clickStatuss) return;
		 if (event.keyCode == 17) { 
//			 console.log("启动");
			 clickStatuss=true;
		 }
		 
		 
		 if (event.ctrlKey) {
				ac = 1;
				op = isStartArc(status,ac);
				$(this).keyup(function(){
					ac = 0;
					op = isStartArc(status,ac,currentOp);
				});
		}
	
		 if(event.keyCode == 8){
			 stepsRollback()
		 }
		 
	});
	
	//步骤快捷回滚
	function  stepsRollback(){
		console.log("触发步骤");
		$.ajax({
			url:'/chromosome/chDivision/chromStepsRollback',
			type:'post',
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log(js);
	 			console.log("完毕");
	 			if(ca==1){
	 				console.log("点开");
	 				shanchu(js)
	 			}else{
	 				console.log("没有点开");
	 				//loader(js["chrom"]);
	 				nuclearAnalysisApp.resetImageList(js["chrom"]);
	 				$("#pic1").attr("src",urls + js["label"]);
	 			}
 				//$("#check_fruit2").val($(".analyse_tab img").length);
		 	}
		});
	} 
	
	
	
	//处理步骤删除数据处理2
	function shanchu(js){
		for ( var key in js) {
			if(key=="chrom" || key=="id"||key=="label"){
				switch(key)
				{
				//染色单体
				case "chrom":
					if(js[key]!=null){
						//loader(js["chrom"]);
						nuclearAnalysisApp.resetImageList(js["chrom"]);
					}
				  break;
				case "id":
//				  if(js[key]!=null){
//					  for ( var j in js[key]) { 
//						$("#"+js["id"][j]).remove();
//					}
//				  }
				    break;
				//标签
				case "label":
					if(js[key]!=null){
						 $("#pic1").attr("src",urls + js[key]);
					 }
					 break;
			    default:
				break;
				}
					
			}else{
				var ht="";
				for ( var i in js[key]) {
						ht+="<tr id='" +
						js[key][i].logId+"'><td>"+js[key][i].logName
						+"</td></tr>";
				}
				$(".step").html(ht);
			}
		}
	}

	
	


	$(document).keyup(function(event){
		if (event.keyCode == 17) { 
//			console.log("释放");
			clickStatuss=false;
		 }
		if (event.keyCode == 13) { 
//			console.log("触发************");
			var arry =  []; 
			$("div#border_zt.analyse_tab--item").each(function(){
//				console.log("值+++++++++++++++++=="+$(this).children("img").attr("id"));
				arry.push($(this).children("img").attr("id"));
			});
			$.ajax({
				url:nodeUrl + '/chromImage/connect',
				type:'post',
				data:{
					"chromIdArray":arry,
					"greyId":$("#grayId").val(),
					"loginName":$("#loginName").val()
				}, 
				//contentType:"application/json;charset=utf-8",
				//dataType:"json",
				
			 	success:function(res){
			 		for ( var key in res.data) {
						if (key == "label") {
							$("#pic1").attr("src",urls + res.data[key].grayUrl);
						} else if (key = "ch") {
							//loader(res.data[key]);
							nuclearAnalysisApp.resetImageList(res.data[key]);
							$("div#border_zt.analyse_tab--item").each(function(){
								$(this).removeAttr("id");
							});
						}
					}
			 		//$("#check_fruit2").val($(".analyse_tab img").length);
			 		console.log("完毕");
			 	}
			});
//			console.log("值************"+JSON.stringify(arry));
			connect = 0;
			clickStatuss=false;
			$("#copy").css({"background-color":"#424041","color":"#fff"});
			$("#copy").find('i').css("background","url(../../../images/patientdata/white/copy.png)")
			$("#copy").removeClass('btncss');
		 }
	});
	
	
	

	var clickStatuss=false;
	$(".analyse_tab").on("click", ".analyse_tab--item", function() {
		if(clickStatuss){
			outlineShow($(this).children("img"));
			if($(this).attr('id')=='border_zt'){
				$(this).removeAttr("id");
			}else{
				$(this).attr('id','border_zt');
			}
		} 
		
		
	});
	
	var imga = "";
	var connect = 0;//点击连接按钮这个用来阻止弹出canvas框来进行连接操作
	$(".analyse_tab").on("click", ".analyse_tab--item img", function(e) {
		
			e.stopPropagation();
			imga = $(this).attr("id");
			if(clickStatuss){
				outlineShow($(this));
				if($(this).parent().attr('id')=='border_zt'){
					$(this).parent().removeAttr("id");
				}else{
					$(this).parent().attr('id','border_zt');
				}
				
				return;
			}
			$(".step").css("display", "none");
			ca = 0;
				$("#chuli1 button").find('i').css({'background':''})
				$("#chuli1 button").siblings().removeClass('btncss');
				if($("#settingOut").attr("zt")!=0){
					$("#settingOut").addClass("btncss");
					$("#settingOut").find('i').css({"background":"url(../../../../page/images/patientdata/blue/settingOut.png)"})
				}
				if($("#smear").attr("zt")!=0){
					$("#smear").addClass("btncss");
					$("#smear").find('i').css({"background":"url(../../../../page/images/patientdata/blue/smear.png)"})
				}

			if(qwer==0){//判断是否点击了复制按钮 0 没点击复制 1点击了很复制功能
				$('.analyse_tab--item.normal').removeAttr('id');//点击了分割弹框清空未连接完的状态样式
				$(".zhezhao").css("display","block");
				drawOpList = [];
				// 初始化画线操作
				//selectedLine();
				defaultOp(status);
			var id = $(this).attr("id");
			$.ajax({
				url : '/chromosome/chDivision/chQuery',
				type : 'post',
				data : JSON.stringify({
					'id' : id
				}),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				success : function(js) {
			
					// 获取并设置画布
					//清空图片
//					drawSmearContext.clearRect(0, 0, 800, 800);
					drawSmearContext.clearRect(0, 0, arContext.canvas.width,arContext.canvas.height);
					arcXPath = [];
					arcYPath = [];
					oContext = oGetMousePos.getContext("2d");
					var imgObj = new Image();
					var srcs =urls + js[0]+"?temp=" + Math.random();
//					alert(srcs);
					imgObj.src =srcs;
					imgObj.crossOrigin = '';
					oGetMousePos.width=500;
					oGetMousePos.height=400;
					oGetMousePos1.width=500;
					oGetMousePos1.height=400;
					canvasOffscreen.width=500;
					canvasOffscreen.height=400;
					$("#chuli1").css("height",400);
					$("#chuli1").css("width",500);
					canvasBm.width=500;
					canvasBm.height=400;
					console.log(imgObj.src);
					if(imgObj.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数  
						if(400-40<=imgObj.height){
							$("#chuli1").css("height",(imgObj.height+40));
							oGetMousePos.height=imgObj.height;
							oGetMousePos1.height=imgObj.height;
							canvasOffscreen.height=imgObj.height;
							canvasBm.height=imgObj.height;
						}
						if($("#chuli1").width()<=imgObj.width){
							$("#chuli1").css("width",imgObj.width);
							oGetMousePos.width=imgObj.width;
							oGetMousePos1.width=imgObj.width;
							canvasOffscreen.width=imgObj.width;
							canvasBm.height=imgObj.height;
						}
						oContext.drawImage(imgObj, 0, 0,imgObj.width,imgObj.height);
					     var w = imgObj.width;
							var h = imgObj.height+40;
							var w1 = w + e.clientX;
							var h1 = h + e.clientY;
							var w2 = (w1 - e.clientX) / 2;
							var h2 = (h1 - e.clientY) / 2;
							$("#chuli1").offset({
								top : e.clientY - h2
							});
							var h3 = $("#chuli1").offset().top;
							var w5 = w + e.clientX;
							if (w5 > 2055) {
								console.log("超过最右面");
								$("#chuli1").offset({
									left : (1910 - w),
									top : e.clientY - h2
								});
							} else {
								console.log("跟随鼠标移动");
								$("#chuli1").offset({
									left : e.clientX - w2,
									top : e.clientY - h2
								});
							}
							if (h3 < 0) {
								console.log("超过最上面");
								$("#chuli1").offset({
									top : 0
								});
							}
							if ((h3 + h) > 1080) {
								console.log("超过最下面");
								$("#chuli1").offset({
									top : (1070 - h)
								});
							}
							$("#chuli1").css("visibility","visible")
		            } else{
						imgObj.onload = function() {
							if(400-40<=imgObj.height){
								$("#chuli1").css("height",(imgObj.height+40));
								oGetMousePos.height=imgObj.height;
								oGetMousePos1.height=imgObj.height;
								canvasOffscreen.height=imgObj.height;
								canvasBm.height=imgObj.height;
							}
							if($("#chuli1").width()<=imgObj.width){
								$("#chuli1").css("width",imgObj.width);
								oGetMousePos.width=imgObj.width;
								oGetMousePos1.width=imgObj.width;
								canvasOffscreen.width=imgObj.width;
								canvasBm.width=imgObj.width;
							}
							oContext.drawImage(imgObj, 0, 0,imgObj.width,imgObj.height);//this即是imgObj,保持图片的原始大小：470*480
					    	var imageData = oContext.getImageData(0,0, imgObj.width,imgObj.height)
							var imageDataCopy = new ImageData(
							  new Uint8ClampedArray(imageData.data),
							  imageData.width,
							  imageData.height
							)
					    	currentImage.imageData = imageDataCopy;
					    	currentImage.width = imgObj.width;
							currentImage.height = imgObj.height;
							currentImage.chromId = imga;
							let grayId = $("#grayId").val();;
							nuclearAnalysisApp.fillImage(id,grayId,currentImage,"myCanvas");
						     var w = imgObj.width;
								var h = imgObj.height+40;
								var w1 = w + e.clientX;
								var h1 = h + e.clientY;
								var w2 = (w1 - e.clientX) / 2;
								var h2 = (h1 - e.clientY) / 2;
								$("#chuli1").offset({
									top : e.clientY - h2
								});
								var h3 = $("#chuli1").offset().top;
								var w5 = w + e.clientX;
								if (w5 > 2055) {
									console.log("超过最右面");
									$("#chuli1").offset({
										left : (1910 - w),
										top : e.clientY - h2
									});
								} else {
									console.log("跟随鼠标移动");
									$("#chuli1").offset({
										left : e.clientX - w2,
										top : e.clientY - h2
									});
								}
								if (h3 < 0) {
									console.log("超过最上面");
									$("#chuli1").offset({
										top : 0
									});
								}
								if ((h3 + h) > 1080) {
									console.log("超过最下面");
									$("#chuli1").offset({
										top : (1070 - h)
									});
								}
								$("#chuli1").css("visibility","visible");
						}
		            }
					clickStatus = false;
				}
			});}
		

	});


	// 画线按钮
	$("#settingOut").click(function() {
		$(this).attr("zt","1");
		$("#smear").attr("zt","0")

		status = 0;
		op = isStartArc(status,ac);
	});
	
	$(document).keyup(function(event){
		if(event.keyCode==16){
			var ztFlag = $("#smear").attr("zt");
			if(ztFlag == 1){
				selectedLine();
			}else{
				selectedArc();
			}
//			if(count1%2!==0){
//				selectedArc();
//			}else{
//				selectedLine();
//			}
		}})
	
	function selectedArc(){
		//画线跳涂抹
		$("#smear").attr("zt","1");
		$("#settingOut").attr("zt","0");
		$("#smear").addClass("btncss");
		$("#settingOut").removeClass("btncss");
		$("#smeara").css("background-image","url(../../../images/patientdata/blue/smear.png)");
		$("#settingOuta").css("background-image","url(../../../images/patientdata/white/settingOut.png)");
		status = 1;
		isDrawing = false;
		isDisplayArc = false;
		op = isStartArc(status,ac);
	}
	
	function selectedLine(){
		//涂抹跳画线
		$("#settingOut").attr("zt","1");
		$("#smear").attr("zt","0");
		$("#settingOut").addClass("btncss");
		$("#smear").removeClass("btncss");
		$("#smeara").css("background-image","url(../../../images/patientdata/white/smear.png)");
		
		$("#settingOuta").css("background-image","url(../../../images/patientdata/blue/settingOut.png");
		status = 0;
		op = isStartArc(status,ac);
		
	}
	
	function defaultOp(status){
		if(status == 0){
			selectedLine();
		}else if(status == 1){
			selectedArc();
		}
	}

	// 涂抹按钮
	$("#smear").click(function() {
		$(this).attr("zt","1");
		$("#settingOut").attr("zt","0")

		status = 1;
		op = isStartArc(status,ac);
	});

	// 记录坐标点
	var message = new Array();

	// 画布参数
	var oGetMousePos
	
	var oGetMousePos1
	// 画布功能判断
	var clickStatus
	// 画布功能初始化
	myCanvas();

	document.oncontextmenu = function() {
		event.returnValue = false;
	};


    
    //获得坐标
	var canvas = document.getElementById("myCanvas");
	canvas.onmousemove = function (e) {
		var bbox = canvas.getBoundingClientRect();
		var x = e.clientX - bbox.left * (canvas.width / bbox.width);
		var y = e.clientY - bbox.top * (canvas.height / bbox.height);
		 canvasX = e.clientX - bbox.left;
		 canvasY = e.clientY - bbox.top;
	};
	function myCanvas() {	
	$(document).keyup(function(event){
	if(event.keyCode==27){
		$.ajax({
			url : '/chromosome/chDivision/chCoordinateRepeal',
			type : 'post',
			data : JSON.stringify(),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(res) {
//				alert("撤销完成");
				if(!res || res.code != 0){
					alert("撤销失败。");
					return;
				}
				if(status == 1 || status == 0){
					if(_.isEmpty(drawOpList)){
						//关闭遮罩
						$(".zhezhao").css("display","none");
						$("#chuli1").css("visibility","hidden");
						clickStatus = false;
					}else{
						backStepOp(status);
					}
				}else{
					//关闭遮罩
					$(".zhezhao").css("display","none");
					$("#chuli1").css("visibility","hidden");
					clickStatus = false;
					//message = new Array();
				}
//				if(js[0]!=null){
//					if(status == 1 || status == 0){
//						if(_.isEmpty(drawOpList)){
//							//关闭遮罩
//							$(".zhezhao").css("display","none");
//							$("#chuli1").css("visibility","hidden");
//							clickStatus = false;
//						}else{
//							backStepOp(status);
//						}
//						
//						return;
//					}
//					clickStatus = false;
//				}else{
//					//关闭遮罩
//					$(".zhezhao").css("display","none");
//					$("#chuli1").css("visibility","hidden");
//					clickStatus = false;
//					if(oContext != null){
//						oContext.clearRect(0, 0, 800, 800);
//						oContext = null;
//					}
//					message = new Array();
//				}
			}
		})
	}
			});
	
		
		
		
		
	// 打印鼠标指针坐标
		oGetMousePos = document.getElementById("myCanvas");
		oGetMousePos1 = document.getElementById("arCanvas");
		
		function writeMessage(oGetMousePos, message) {
			if (clickStatus) {
				oContext.clearRect(0, 0, oGetMousePos.width, oGetMousePos.height);
				oContext.font = "20pt Microsoft JhengHei";
				oContext.fillStyle = "tomato";
				oContext.fillText(message, 10, 25);
			}
		}
		// 获取鼠标指针坐标
		function getMousePos(oContext, evt) {
			if (clickStatus) {
				var rect = oGetMousePos.getBoundingClientRect();
				return {
					x : evt.clientX - rect.left,
					y : evt.clientY - rect.top
				};
			}
		}
		// 离开事件
		$("#myCanvas").mouseup(function() {
			if(!op.line){
			  return;
			}
			if(status == 0){
				var messages = {
						'message' : message,
						'status' : parseInt(status)
					};
					$.ajax({
						url : '/chromosome/chDivision/chCoordinateStore',
						type : 'post',
						data : JSON.stringify(messages),
						contentType : "application/json;charset=utf-8",
						dataType : "json",
						success : function(js) {
							//清空图片
							if(js[0]!=null){
								if(oContext != null){
									oContext.clearRect(0, 0, 800, 800);
									oContext = null;
								}
								
								oContext = oGetMousePos.getContext("2d");
								var imgObj = new Image();
								var srcs =urls + js[0]+"?temp=" + Math.random();
								imgObj.src = srcs;
								if(imgObj.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数  
									oContext.drawImage(imgObj, 0, 0);
					            } else{
									imgObj.onload = function() {
										oContext.drawImage(this, 0, 0);// this即是imgObj,保持图片的原始大小：470*480
									}
					            }
							}
//							alert("123123");
						}
					});
					clickStatus = false;
					message = new Array();
			}
			
		});

	}

	// // 复制
	// var a7 = null;
	// $(".analyse_tab").on("mousedown","td",function(e){
	// if(qwer==0){
	// $.contextMenu({
	// selector : '.analyse_tab tbody tr td img',
	// callback : function() {
	// a7 = $(this);
	// },
	// items : {
	// "copy" : {
	// name : "复制",
	// icon : "复制"
	// }
	// }
	// });
	// 复制
	var a7 = null;
	var copyStatues = false;
	$(".analyse_tab").on("mousedown","div",function(e) {
						if (qwer == 1) {
							if (copyStatues) {
								$.contextMenu({
									selector : '.analyse_tab .analyse_tab--item img',
									callback : function() {
										a7 = $(this);
									},
									items : {
										"copy" : {
											name : "复制",
											icon : "复制"
										}
									}
								});
							}

							// 粘贴
							$('.analyse_Left_img').mousedown(function(e) {
												if (a7 != null|| a7.attr("src") != undefined) {
													$.contextMenu({
																selector : '.analyse_Left_img',
																callback : function() {
																	$.ajax({
																				url : '/chromosome/chDivision/chromCopy',
																				type : 'post',
																				contentType : "application/json;charset=utf-8",
																				data : JSON.stringify({
																							'chromid' : a7.attr("id")
																						}),
																				dataType : "json",
																				success : function(
																						js) {
																					if (js != null) {
																						// 给灰底图添加图片
																						$(".analyse_Left_img").append(
																										"<img id='"+ a7.attr("id")
																												+ "' src='"
																												+ a7.attr("src")
																												+ "'>");
																						// 重新刷新染色单体
																						//loader(js);
																						nuclearAnalysisApp.copyImageList({
																							"chromEnhance":a7.attr("src"),
																							"chromId" :js.chromId,
																							isShow:true
																						});
																						a7 = null;
																					} else {
																						alert("复制粘贴失败");
																					}
																				}
																			});
																
																},
																items : {
																	"copy" : {
																		name : "粘贴",
																		icon : "粘贴"
																	}
																}
															});
													
												}
												qwer = 0;
											});
    
						}

					});

	// // 连接按钮
	// var qwer = 0;
	// $("#lianjie").click(function() {
	// $(".step").css("display", "none");
	// ca = 0;
	// qwer = 1;
	// $(".analyse_Left button").attr("disabled", true);
	// $.contextMenu({
	// selector : '.analyse_tab tbody tr td',
	// callback : function(items) {
	// if (items = "lianjie") {
	// $("#yes_delete").attr("disabled", true);
	// }
	// if (items = "quxiao") {
	// $("button").removeAttr("disabled");
	// $("#yes_delete").attr("disabled", true);
	// qwer = 0;
	// }
	// },
	// items : {
	// "lianjie" : {
	// name : "连接",
	// icon : "连接",
	// },
	// "quxiao" : {
	// name : "取消",
	// icon : "取消"
	// }
	// }
	// });
	// });
	//
	// });
	// 复制按钮
	var qwer = 0;//点击复制后 qwer=1 用来判断点击复制后不弹分割框
	$("#lianjie").click(function() {
		$(".step").css("display","none");
		if (copyStatues) {
			console.log("复制关闭");
			$("#lianjie").removeClass("btncss");
			$("#lianjiea").removeAttr("background");
			qwer = 0;
			copyStatues = false;
		} else {
			console.log("复制开启");
			qwer = 1;
			copyStatues = true;

		}
		
		console.log(qwer + "***" + copyStatues);
	});


$("#retrun").click(function(){
	var grayNumber =$("#grayId").val();
	/* $('.analyse_Left_img img').each(function() {
		  if($(this).attr("id")!="pic1"){
			  var chromId = $(this).attr("id");
			  var chromSrc = $(this).attr("src");
			  var chromStyle = $(this).attr("style");
	
		  }
	}); */
	let imageList = [];
	nuclearAnalysisApp.imageList.forEach(function(item,index){
		if(item.isShow){
			let tempItem = {
				id: item.chromId,
				colorStepList:item.colorStepList || []
			}
			imageList.push(tempItem);
		}
	});
	
	   $.ajax({
		  url:nodeUrl + '/chAnalysis/chromExcisionStatus',
		  type:'post',
		  data:{
			  grayId:grayNumber,
			  imageList:imageList
			  },
	      success:function(data){
	    	  //window.location.href = "/chromosome/page/jsp/caryogram/operation/chromanalyse.jsp"
	    	  if(data.code ==  0){
      			//window.location.href="../../../../chAnalysis/chDiscern";
      			window.location.href = "/chromosome/page/jsp/caryogram/operation/chromanalyse.jsp"
      		}
	      }
		  
	  }); 
});
var analyse_Left_button1 = '';
var analyse_Left_button2 = '';
var index = 0;
window.onload = function() {
	

	
	$('.analyse_Left button').not("#show_count").not("#copy").not("#chuli").click(
			function() {
				clickStatuss=false;
		    	connect = 0;
                $(".analyse_tab--item.normal").removeAttr('id')
				$(this).addClass('btncss');
				$(this).siblings().removeClass('btncss');
				var thimgid1 = $(this).attr('id');
				var slimg1 = $(this).siblings('button');
				for (var i = 1; i <= slimg1.length; i++) {
					var slimgid1 = slimg1.eq(i - 1).attr('id');
					imgurl1 = ""
					$("#" + slimgid1 + "a").css({
						'background' : imgurl1
					})
				}
				imgurl2 = "url(../../../../page/images/patientdata/blue/"
						+ thimgid1 + ".png)"
				$("#" + thimgid1 + "a").css({
					'background' : imgurl2
				});
				
				
				index++;
				if(index==1){
					analyse_Left_button1 = $(this).attr('id');
				}else{
					analyse_Left_button2 = $(this).attr('id');
					index = 0;
				}
				console.log('analyse_Left_button1'+analyse_Left_button1)
				console.log('analyse_Left_button2'+analyse_Left_button2)
				if(analyse_Left_button1 == analyse_Left_button2){
					$("#"+analyse_Left_button1).removeClass('btncss');
					analyse_Left_button1 = '';
					analyse_Left_button2 = '';
					index = 0;
				
				};
			});
	$('.analyse_Left button')
			.not('#yes_delete')
			.click(
					function() {
						$('#yes_deletea')
								.css(
										{
											'background' : 'url(../../../../page/images/patientdata/gary/yes_delete.png)'
										});
						$('#yes_delete').css({
							'color' : '#999999'
						});
					})

	$('#chuli1 button').not("#clear1").click(
			function() {
				connect=0;//连接按钮用
				$(this).addClass('btncss');
				$(this).siblings().removeClass('btncss');
				var thimgid2 = $(this).attr('id');
				console.log(thimgid2)
				var slimg2 = $(this).siblings('button');
				for (var i = 1; i <= slimg2.length; i++) {
					var slimgid2 = slimg2.eq(i - 1).attr('id');
					imgurl4 = ""
					$("#" + slimgid2 + "a").css({
						'background' : imgurl4
					})
				}
				imgurl3 = "url(../../../../page/images/patientdata/blue/"
						+ thimgid2 + ".png)"
				$("#" + thimgid2 + "a").css({
					'background' : imgurl3
				})
			});
	var count = 0;
	$('#delete').click(function() {
						count++;
                      
						$(".step").css("display", "none");
						ca = 0;
						if(count%2==0 ){
							
							console.log("666");
							$('#yes_deletea').css({
								'background' : 'url(../../../../page/images/patientdata/gary/yes_delete.png)'
							})
							$('#yes_delete').css({
								'color' : '#999'
							})
						}else{
							console.log("999");
							
							$('#yes_deletea').css({
								'background' : 'url(../../../../page/images/patientdata/white/yes_delete.png)'
							})
							$('#yes_delete').css({
								'color' : '#ffffff'
							})
						}
					});

	/** 一键粘连，交叉 * */
	/*
	 * var canvas = document.getElementById("myCanvas"); var canvasX,canvasY;
	 * 
	 * canvas.onmousemove = function (e) { var bbox =
	 * canvas.getBoundingClientRect(); var x = e.clientX - bbox.left *
	 * (canvas.width / bbox.width); var y = e.clientY - bbox.top *
	 * (canvas.height / bbox.height); canvasX = e.clientX - bbox.left; canvasY =
	 * e.clientY - bbox.top; };
	 */

	/*
	 * $("html").keydown(function(e){
	 * 
	 * var keyCode = e.keyCode || e.which || e.charCode; var ctrlKey = e.ctrlKey ||
	 * e.metaKey;
	 * 
	 * if(ctrlKey && keyCode == 49 || keyCode == 50) {
	 * 
	 * if(canvasX==undefined||canvasY==undefined){ alert("超出有效范围！"); return
	 * false; }
	 * 
	 * var type = 2;
	 * 
	 * if(keyCode == 49){ //
交叉 ctrl+1 }else{ //一键粘连 ctrl+2 type = 3; }
	 * 
	 * $.post("/chromosome/chDivision/oneKeyCross",{"Status":type,"canvasX":canvasX,"canvasY":canvasY},function(data){
	 * alert("结果："+data); }); }
	 * 
	 * 
	 * e.preventDefault();
	 *  })
	 */

	/*
	 * document.onkeydown = function(e) {
	 *  }
	 */
	/** 一键粘连，交叉 END * */
}

// 左右快捷键
$(document)
		.keyup(
				function(event) {
					if (event.keyCode == 37) {
						$
								.get(
										'/chromosome/chromatid/grayFoWordQuerys1',
										{
											'v' : 'up'
										},
										function(data) {
											console.log(data)
											if (data == 'success') {
												location.reload();
											} else if (data == 'success1') {
												window.location.href = "/chromosome/page/jsp/caryogram/operation/chromanalyse.jsp"
											} else {
												alert("已是第一页或最后一页");
											}
										});
					} else if (event.keyCode == 39) {
						$
								.get(
										'/chromosome/chromatid/grayFoWordQuerys1',
										{
											'v' : 'down'
										},
										function(data) {
											if (data == 'success') {
												location.reload();
											} else if (data == 'success1') {
												window.location.href = "/chromosome/page/jsp/caryogram/operation/chromanalyse.jsp"
											} else {
												alert("已是第一页或最后一页");
												}
										});
					}
				});

//分割页面点击编号的跳转
$(".disinName_span").click(function(){  
	  if($(this).find('#analyse_Left_img_data').attr("name")=='aa'){
						$.ajax({
							url : nodeUrl + '/chromImage/init',
							type : 'get',
							contentType : "application/json;charset=utf-8",
							dataType : "json",
							data : {
								greyId:$("#grayId").val()
							},
							success : function(js) {
								var u2 = js["label"].inName;
								var u3 = js["label"].grayNumber;
								var u4 = js["label"].slideName;
								
								 $.ajax({
		    		 					url:'/chromosome/chCount/setCountGreyId',
		    		 					type:'post',
		    		 					data:{eventId:u2,greyNum:u3,dialPiece:u4},
		    		 			        dataType:"json",
		    		 			        success:function(data){
		    		 			        	
		    		 			        	var id = data;
		    								window.location.href = '/chromosome/chromatid/chromRequest?grayId=c'
		    										+ id + '';
		    		 			        }
		    		 				}); 
							}
						}); 		 		        		 		      
	  }
     });

//点击计数跳转计数页面
$("#li_count_a").click(function() {
  	  $.ajax({
			url : '/chromosome/chromatid/paramSet1',
			type : 'post',
			success : function(js) {
				window.location.href='/chromosome/page/jsp/caryogram/operation/carycount.jsp';				
			}
		});	
});


$("#yes_delete").hover(function(){},function(){$(this).css("color","#fff");});

$(".analyse_Left button").hover(function(){
	if($(this).attr("class")!="btncss"){
		$(".analyse_Left button").not($("#yes_delete")).css("color","#fff");
		$(this).css("color","#00a0e9");
		if($(this).find("i").attr("id")=="yuanshia"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/yuanshi.png')")
		}else if($(this).find("i").attr("id")=="yes_deletea"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/yes_delete.png')")
		}else if($(this).find("i").attr("id")=="deletea"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/delete.png')")
		}else if($(this).find("i").attr("id")=="chulia"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/chuli.png')")
		}else if($(this).find("i").attr("id")=="procedurea"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/procedure.png')")
		}else if($(this).find("i").attr("id")=="lianjiea"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/lianjie.png')")
		}else if($(this).find("i").attr("id")=="retruna"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/retrun.png')")
		}else if($(this).find("i").attr("id")=="copya"){
			$(this).find("i").css("background","url('../../../../page/images/patientdata/blue/copy.png')")
		}		
	}},function(){
		if($(this).attr("class")!="btncss"){
			$(".analyse_Left button").not($("#yes_delete")).css("color","#fff");
			if($(this).find("i").attr("id")=="yuanshia"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/yuanshi.png')")
			}else if($(this).find("i").attr("id")=="yes_deletea"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/yes_delete.png')")
			}else if($(this).find("i").attr("id")=="deletea"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/delete.png')")
			}else if($(this).find("i").attr("id")=="chulia"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/chuli.png')")
			}else if($(this).find("i").attr("id")=="procedurea"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/procedure.png')")
			}else if($(this).find("i").attr("id")=="lianjiea"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/lianjie.png')")
			}else if($(this).find("i").attr("id")=="retruna"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/retrun.png')")
			}else if($(this).find("i").attr("id")=="copya"){
				$(this).find("i").css("background","url('../../../../page/images/patientdata/white/copy.png')")
			}		
		}
	}
	);


$(".disinName_span").hover(function(){
	if($(this).find("span").attr("name")=="aa"){
		$(this).find("span").css("color","#00a0e9");
		$(this).find("img").attr("src","../../../images/yclick.png");
	}
	
},function(){
	   if($(this).find("span").attr("name")=="aa"){
		$(this).find("img").attr("src","../../../images/wclick.png");
		$(this).find("span").css("color","#fff");
	}
	
});




});
