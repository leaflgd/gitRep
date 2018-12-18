$(function(){
	
	

	
	
//	var urls=loadProperties('url'); //156
//	function loadProperties(key){
//	    jQuery.i18n.properties({// 加载properties文件
//	    name:'JavaScriptConfig', // properties文件名称
//	    path:'/chromosome/', // properties文件路径
//	    mode:'map', // 用 Map 的方式使用资源文件中的值
//	    callback: function() {// 加载成功后设置显示内容
//	       console.log("22222222222"+$.i18n.prop(key));//其中isp_index为properties文件中需要查找到的数据的key值
//	       return $.i18n.prop(key);
//	    }
//	    });
//	}
//	console.log("111:"+urls);
    
	
//	var urls="/chromosome/page/images/patientdata";
	$("#date_date").addClass("active") ;
	$(".tableBox tbody tr:even").css("background","#7b7b7b"); 
	$(".tabulation_tab tbody tr:even").css("background","#7b7b7b");
	$(".qxBtn").attr("disabled", true);
	//页面加载触发事件
	$(document).ready(function(){
		//数据精选记忆搜索显示
		$.ajax({ 
			url:'/chromosome/incident/getMem', 
	        type:'post',
	        success:function(data){
	        	/*alert(data);*/
	        	$(".inName").val(data);
	        	search1();
	        }
		});
		
		//禁止右键默认事件
		document.oncontextmenu = function(){
	        event.returnValue = false;
	    };

	    
	    
	    
	    
	    $("div").on("mousedown","img:nth-child(2)",function(event){
	    	console.clear();
	   
	    	//灰底图左右键点击事件
	    	var chromid;
	    	var flag;
	    	var Tick;
	    	
	    	event.stopPropagation();
	    	//鼠标右键点击事件
	    	/*if(){
	    		
	    	}*/
	    	
           var flag = true;
	    	$(this).parent().find('input').each(function(){
	    		if($(this).attr("class")=='fenxi66'){
	    			flag = false;
	    		}
	    	})
	    	
	    	console.log(flag);
	    	
	    	
	    	if(flag){
		    	if(event.button==2){
		    		
		    		Tick=0;
		    		chromid=$(this).next().find(".position_img_count input").val();
					flag =$(this).next().find(".position_img_count input").prop("checked");
					var count1 = $(".position_img_analyse").find("input:checked");
					var count = $(".position_img_analyse").find("input:checked");
					var count = $(".position_img_count").find("input:checked");
				    if(flag==false){
				    	if($(this).next().find(".position_img_analyse input").prop("checked")==false){
				    		$(".date_analyse3").val(count1.length+1);
				    	}
		    	    	$(this).next().find(".position_img_analyse input").prop("checked",true);
		    	    	$(this).parent().find("img[name='fenxi2']").css("display","block");
		    	    	$(this).next().find(".position_img_count input").prop("checked",true);
				    	$(".count3").val(count.length+1);	
				    }else{
				    	$(this).next().find(".position_img_count input").prop("checked",false);
				    	$(".count3").val(count.length-1);
				    }
				    //鼠标左键点击事件
		    	}else if(event.button==0){
		    	
		    		chromid=$(this).next().find(".position_img_count input").val();
		    		Tick=1;
		    		flag =$(this).next().find(".position_img_analyse input").prop("checked");
		    		var count = $(".position_img_analyse").find("input:checked");
		    		if(flag==false){
		    			$(this).parent().find("img[name='fenxi2']").css("display","block");
		    			$(this).next().find(".position_img_analyse input").prop("checked",true);
		    			$(".date_analyse3").val(count.length+1);
		    		}else{
		    			$(this).next().find(".position_img_analyse input").prop("checked",false);
		    			$(".date_analyse3").val(count.length-1);
		    			$(this).parent().find("img[name='fenxi2']").css("display","none");
		    		}
		    	}
		   
		    	$.ajax({ 
			        url:'/chromosome/incident/grayStatusUpdate', 
			        type:'post',
			        data:JSON.stringify({"greyid":chromid,"flag":!flag,"Tick":Tick}),
			        contentType: "application/json;charset=utf-8",
			        dataType:"json",
			        success:function(data){
			        	if(!data){
			        		alert("错误");
			        	}
			        }
		    	});
		    	
				//选中计数或分析时底部色块为深灰色
				 if($(this).next().find(".position_img_count input").prop("checked")==true||$(this).next().find(".position_img_analyse input").prop("checked")==true){
					 $(this).next().css("background-color","#424040");
				 }else{
					 $(this).next().css("background-color","#8e8e8e");
				 }
				 if($(this).next().find(".position_img_count input").prop("checked")==true){
	    	    		$(this).parent().children("img:first-child").attr("src","../../../images/you.png");
				 }else{
					 $(this).parent().children("img:first-child").attr("src","../../../images/wu.png")
				 }
	    	}
	    	

			 
		 });
	});
	$(document).on("mousedown","#search1",function(){
		$("#search1").css({"background":"linear-gradient(#2ec0fb, #0e9aee,#0091ea)"});
	})
	$(document).on("mouseup","#search1",function(){
		$("#search1").css({"background":"linear-gradient(#59bbe9, #2ea5df,#0791d7)"});
	})
	$(document).on("click", "#search1", function(){
		search1();
	});
	

	   $(document).keydown(function(e){
	    	if(e.keyCode==13){
	    		$("#search1").click();
	    	}
	    });

	//var greyClickStatus=false;
	//AJAX时间编号搜索
	function search1(){
		var dt =$(".inName").val();
		$.ajax({ 
	        url:'/chromosome/incident/inGain', 
	        type:'post',
	        data:JSON.stringify(dt),
	        contentType: "application/json;charset=utf-8",
	        dataType:"json",
	        success:function(json){
	    	   var ht="";
	    	   for ( var a in json) {
	    		  ht=ht+"<ul  id='"+json[a].incidentName+"'><span id='incident'"
	    		  /*if(json[a].status==1){
	    			  ht=ht+"gray"
	    		  }*/

	    		  ht=ht+"><img id='incident_img' src='../../../images/whitemax.png'/>"+json[a].incidentName+" ("+json[a].count+")</span>";
	    		  if(json[a].submitCount>0){
                      ht=ht+"<img style='margin-left:7px;margin-bottom:3px;margin-right:3px' src='../../../images/up.png'><span id='countbtn'>"+json[a].submitCount+"</span>";
				  }
	    		  ht=ht+"</ul>";

	    	   }
	    	   $(".innerbox1").html("");
	    	   $(".innerbox7").html(ht);   
	       }
		});
	};
	//事件下玻片的查询
    var incident_id = null;
	$(document).on("mousedown", "#incident", function(e) {
        incident_id =null;
         incident_id = $(this);

		if(e.button==2){
				 key($(this).parent().attr("id"),"DELETE_Event",'','','',1);
			}
		count = 0;
		$("ul>span img").attr("src","../../../images/whitemax.png");
		$(this).find("#incident_img").attr("src","../../../images/bluemax.png");
		$('#qxBtn').attr("checked",false);
		$(".count3").val(0);
		$(".date_analyse3").val(0);
		$(".rad_1 input[type='radio']").attr("disabled",false);
		$(".qxBtn").removeAttr("disabled");
		var dt = this;
		var dd = dt.parentNode.id;
		$("ul span").removeAttr("style");
		dt.style.color='#00a0e9';
		submitStatus=true;
		$.ajax({ 
	        url:'/chromosome/incident/sQuery', 
	        type:'post',
	        data:JSON.stringify(dd),
	        contentType: "application/json;charset=utf-8",
	        dataType:"json",
	        success:function(js){
	        	$(".innerbox1").html("");
	        	//greyClickStatus=false;
	        	for ( var i in js) {
	        		/*if(!js["status"]){
	        			greyClickStatus=true;
	        		}*/
	        		var ht="";
	        		if(i=='grey'){
		        		var js1 = js[i]
		        		for ( var j in js1) {

                            var checkCount =false;
                            var checkAnalyse = false;
                            var analyStatus = js1[j].analyStatus;
                            var countStatus = js1[j].countStatus;

                            for(var checkType=0;checkType<js1[j].checkTypeList.length;checkType++){
                                if(js1[j].checkTypeList[checkType]=="1"){
                                    checkAnalyse = true;
                                }else if(js1[j].checkTypeList[checkType]=="2"){
                                    checkCount = true;
                                }
                            }

		        			ht=ht+"<div class='position_img_date'><img class='fenxi1' src='../../../images/wu.png' style='position:absolute;right:0px;width:55px' />" +
		    	        		"<img class='lazy' data-original='"+urls+js1[j].grayUrl+"' src=''>"+
		    	        		"<div class='img_date_show'>" +
			        			"<div class='position_img_count'>" +
			        				"<input type='checkbox'  name='"+ js1[j].dialPieceId +"' value='"+js1[j].grayId+"'"
			        	if(checkAnalyse || analyStatus){

			        			ht=ht+"checked='checked'";
                            	if(checkAnalyse){
                                    ht=ht+"  class='fenxi66'";
								}
			        			var values =parseInt($(".count3").val())+1;
			        			console.log("-------"+values);
			        			$(".count3").val(values);
			        	}					
		        		ht=ht+"><span>"+"分析"+"</span>"+
			        			"</div>" +
			        			"<div class='position_img_analyse'><img class='fenxi2' name='fenxi2' src='../../../images/gou.png' style='position:absolute;left:0;width:20px;height:19'/>" +
			        				"<input type='checkbox' name='"+ js1[j].dialPieceId +"' value='"+js1[j].grayId+"'"
			        	if(checkCount || countStatus){

					        ht=ht+"checked='checked'";
                            if(checkCount){
                                ht=ht+"  class='fenxi66'";
                            }
					        var values =parseInt($(".date_analyse3").val())+1;
		        			console.log(values);
		        			$(".date_analyse3").val(values);
					        
					    }	
		        		ht=ht+"><span>"+"计数"+"</span>"+
			        			"</div>" +
			        			"<span class='imgs2 imgs_id'>"+js1[j].grayNumber+"</span>" +
			        			"<span class='imgs3 imgs_number'>"+js1[j].grayDate+"</span>" +
		        		"</div></div>"
		        		}
		        		$(".innerbox1").html(ht);
		        		ht="";
	        		}else if(i!='status'){
	        			$(".innerbox7 ul li").remove(); 
			        	for ( var j in js[i]) {
			        		ht=ht+"<li id='child_node' style='display: list-item;'>"+
			        		"<span id='clide'" 
							/*if(js[i][j].status==1){
				    			  ht=ht+" gray"
				    		  }*/
			        		ht=ht+" aa='"+js[i][j].slideId+"'><img id='clide_img' src='../../../images/white.png'/>"+js[i][j].slideName+" ("+js[i][j].count+")</span></li>" 
			        	}
			        	$("#"+i+"").append(ht); 		
	        		}
	        	}
	        	/*懒加载*/
	        	$("img.lazy").lazyload({
                    container: $(".innerbox1"),
                    effect: "fadeIn",
                    threshold:200,
	        	});
	    		if (vau==20) {
	    			$(".position_img_date img:nth-child(2)").css("width", "255px");
	    			$(".position_img_date").css("width", "255px");
	    			$(".img_date_show").css("width", "255px");
	    			pid = "position_img_date20"
	    	    }
	    	    if (vau==40) {
	    	    	$(".position_img_date img:nth-child(2)").css("width", "307.5px");
	    			$(".position_img_date").css("width", "307.5px");
	    			$(".img_date_show").css("width", "307.5px");
	    	    	pid = "position_img_date40"
	    	    }
	    	    if (vau==60) {
	    	    	$(".position_img_date img:nth-child(2)").css({"width": "385.5px"});
	    			$(".position_img_date").css("width", "385.5px");
	    			$(".img_date_show").css("width", "385.5px");
	    	    	pid = "position_img_date60" 
	    	   
	    	    }
	    	    if (vau==80) {
	    	    	$(".position_img_date img:nth-child(2)").css("width", "516px");
	    			$(".position_img_date").css("width", "516px");
	    			$(".img_date_show").css("width", "516px");	
	    	    	pid = "position_img_date80"  
	    	    }
	    	    if (vau==100) {
	    	    	$(".position_img_date img:nth-child(2)").css("width", "778px");
	    			$(".position_img_date").css("width", "778px");
	    			$(".img_date_show").css("width", "778px");
	    	    	pid = "position_img_date100" 
	    	    }
	    	    $(".img_date_show").each(function(){
                    $(".fenxi66").parent().parent().parent().children("img").css("opacity","0.7");
	    	    	if($(this).find(".position_img_count input").prop("checked")==true||$(this).find(".position_img_analyse input").prop("checked")==true){
	    	    		$(this).css("background-color","#424040");
	    	    	}
	    	    	if($(this).find(".position_img_count input").prop("checked")==true){
	    	    		$(this).parent().children("img:first-child").attr("src","../../../images/you.png")
	    	    	}
	    	    	 if($(this).find(".position_img_analyse input").prop("checked")==true){
						 $(this).parent().find("img[name='fenxi2']").css("display","block");
					 }else{
						 $(this).parent().find("img[name='fenxi2']").css("display","none");
					 }
		        });
	        }
		});
	});
	


	
	$(".rad_1 input[type='radio']").attr("disabled",true);
	//玻片查询图片
	var pid = ""
	$(document).on("mousedown", "#clide", function(e) { 
		if(e.button==2){
			key($(this).attr("aa"),"DELETE_DialPiece",$(this),$(this).parent().parent().children(":first").text(),$(this).parent().parent().attr("id"));
		}
		submitStatus=false;
		$("ul li span img").attr("src","../../../images/white.png");
		$(this).find("#clide_img").attr("src","../../../images/blue.png");
		$('#qxBtn').attr("checked",false);
		count = 0;
		$(".date_analyse3").val(0);
		$(".count3").val(0);
		$(".rad_1").find("input").eq(2).prop("checked",true);
		$(".rad_1 input[type='radio']").attr("disabled",false);
		$(".qxBtn").removeAttr("disabled");
		var dt = this;
		var a= dt.getAttribute("aa");
		$("#child_node span").css("color","#ffffff");
		dt.style.color='#00a0e9';
		$.ajax({ 
	        url:'/chromosome/incident/grayQuery', 
	        type:'post',
	        data:JSON.stringify(a),
	        contentType: "application/json;charset=utf-8",
	        dataType:"json",
	        success:function(js){
	        	submitStatus=false;
	        	//greyClickStatus=false;
	        	var ht="";
	        	/*if(!js["status"]){
        			greyClickStatus=true;
        		}*/
	        	var js1 = js["gray"]
	        	for ( var i in js1) {
	        		var checkCount =false;
	        		var checkAnalyse = false;
                    var analyStatus = js1[i].analyStatus;
                    var countStatus = js1[i].countStatus;
                    for(var checkType=0;checkType<js1[i].checkTypeList.length;checkType++){
                        if(js1[i].checkTypeList[checkType]=="1"){
                            checkAnalyse = true;
                        }else if(js1[i].checkTypeList[checkType]=="2"){
                            checkCount = true;
						}
                    }

                    //alert(js1[i].grayDate+"--"+js1[i].grayUrl+"--"+js1[i].grayNumber+"--"+js1[i].slide.slidenName);
	        		ht=ht+"<div class='position_img_date'><img class='fenxi1' src='../../../images/wu.png' style='position:absolute;right:0px;width:55px'/>" +
	        			"<img class='lazy' data-original='"+urls+js1[i].grayUrl+"' src=''>"+
	        			"<div class='img_date_show'>" +
		        			"<div class='position_img_count'>" +
		        				"<input type='checkbox' name='"+ js1[i].dialPieceId +"' value='"+js1[i].grayId+"'" ;

                    if(checkAnalyse || analyStatus){

                        ht=ht+"checked='checked'";
                        if(checkAnalyse){
                            ht=ht+"  class='fenxi66'";
                        }
                        var values =parseInt($(".count3").val())+1;
                        console.log("-------"+values);
                        $(".count3").val(values);
                    }

                    ht=ht+"><span>"+"分析"+"</span>"+
		        			"</div>" +
		        			"<div class='position_img_analyse'><img class='fenxi2' name='fenxi2' src='../../../images/gou.png' style='position:absolute;left:0;width:20px;height:19'/>" +
		        				"<input type='checkbox' name='"+ js1[i].dialPieceId +"' value='"+js1[i].grayId+"'";

						if(checkCount || countStatus){

							ht=ht+"checked='checked'";
							if(checkCount){
								ht=ht+"  class='fenxi66'";
							}
							var values =parseInt($(".date_analyse3").val())+1;
							console.log(values);
							$(".date_analyse3").val(values);

						}

                    ht=ht+"><span>"+"计数"+"</span>"+
		        			"</div>" +
		        			"<span class='imgs2 imgs_id'>"+js1[i].grayNumber+"</span>" +
		        			"<span class='imgs3 imgs_number'>"+js1[i].grayDate+"</span>" +
	        		"</div></div>";
	        		
	        		
	        	}
	        	
	        	$(".innerbox1").html(ht);
	        	
	        	$("img.lazy").lazyload({
                    container: $(".innerbox1"),
                    effect: "fadeIn",
                    threshold:200,
	        	})
	    		if (vau==20) {
	    			$(".position_img_date img:nth-child(2)").css("width", "229px");
	    			$(".position_img_date").css("width", "231px");
	    			$(".img_date_show").css("width", "229px");
	    			pid = "position_img_date20"
	    	    }
	    	    if (vau==40) {
	    	    	$(".position_img_date img:nth-child(2)").css("width", "277.5px");
	    			$(".position_img_date").css("width", "279px");
	    			$(".img_date_show").css("width", "277.5px");
	    	    	pid = "position_img_date40"
	    	    }
	    	    if (vau==60) {
	    	    	$(".position_img_date img:nth-child(2)").css({"width": "349.5px"});
	    			$(".position_img_date").css("width", "351.5px");
	    			$(".img_date_show").css("width", "349.5px");
	    	    	pid = "position_img_date60" 
	    	   
	    	    }
	    	    if (vau==80) {
	    	    	$(".position_img_date img:nth-child(2)").css("width", "470.5px");
	    			$(".position_img_date").css("width", "472px");
	    			$(".img_date_show").css("width", "470.5px");	
	    	    	pid = "position_img_date80"  
	    	    }
	    	    if (vau==100) {
	    	    	$(".position_img_date img:nth-child(2)").css("width", "711px");
	    			$(".position_img_date").css("width", "713px");
	    			$(".img_date_show").css("width", "711px");
	    	    	pid = "position_img_date100" 
	    	    }
	     	    $(".img_date_show").each(function(){
	     	    	$(".fenxi66").parent().parent().parent().children("img").css("opacity","0.7");
	    	    	if($(this).find(".position_img_count input").prop("checked")==true||$(this).find(".position_img_analyse input").prop("checked")==true){
	    	    		$(this).css("background-color","#424040");
	    	    	}
	    	    	if($(this).find(".position_img_count input").prop("checked")==true){
	    	    		$(this).parent().children("img:first-child").attr("src","../../../images/you.png")
	    	    	}
	    	    	 if($(this).find(".position_img_analyse input").prop("checked")==true){
						 $(this).parent().find("img[name='fenxi2']").css("display","block");
					 }else{
						 $(this).parent().find("img[name='fenxi2']").css("display","none");
					 }
		        });
	     	    
	     	  
	        }
		});
		
	});
	/*点击拨片*/
	var vau = 0;
	$("#rad3").next().css({"background":"#00a0e9","color":"#ffffff"})
	$('input:radio[name="radio"]').click(function(){ 
		vau = $('input:radio[name="radio"]:checked').val();
		if($('input:radio[name="radio"]:checked')){
			$(this).siblings("label").css({"background":"#424040","color":"#1188bf"})
			$(this).next().css({"background":"#00a0e9","color":"#ffffff"})
		}
	});
	
	
	// 全选按钮并且计算分析和计数有多少被选中的个数
	var count = 0;
	$(document).on("click", ".qxBtn", function() {
		if (count == 0) {
			$(".position_img_analyse input").prop("checked", true);
			var chk = $(".position_img_analyse input:checked");
			var len = chk.length;
			$(".fenxi2").css("display","block");
			$(".date_analyse3").val(len);
			$(".img_date_show").css("background-color","#424040");
			count = 1;
		} else {
			
			$(".position_img_analyse input").prop("checked", false);
			$(".date_analyse3").val(0);
			$(".fenxi2").css("display","none");
			count = 0;
		
		}	
	});
	
	 function key(a10,YM,code,codeparent,codeparentcodeparent,da){
		 document.onkeydown = function(ew){
			  var e = ew || windown.ew || argyments.caller.arguments[0];
			  if(e && e.keyCode == 46){
				console.log("a10:"+a10);
			  if(confirm("是否删除？")){
				if(a10!=''){
					$(".innerbox1").html("");
	        	   $("#"+a10).css("display","none");
	        	   $("span[aa="+a10+"]").remove();
	        	   if(code!=''){
	        		   if(code.text()!=undefined){
		        		   var count = codeparent.substring(codeparent.indexOf("(")+1,codeparent.indexOf(")")) - code.text().substring(code.text().indexOf("(")+1,code.text().indexOf(")"))
		        		   $("#"+codeparentcodeparent).children(":first").text(codeparentcodeparent+"("+count+")")
		        	   }
	        	   }
	        	   $.ajax({
			        url : '/chromosome/one_key_delete',
				   type : 'post',
				   data : {'id' : a10,'parameter':YM},
				   success : function(js) {			
					}
					});
	        		a10 = '';
	           }
			  }else{
				    a10 = '';
			  }	  
			  }
			        }
		}

	
	//计算分析被选的个数
	$(document).on("click", ".position_img_analyse input", function(){
	       var chk=$(".position_img_analyse input:checked");
	          var len =chk.length;
	           $(".date_analyse3").val(len);
	 });
	//计算计数被选的个数
	$(document).on("click", ".position_img_count input",function(){
	       var chk=$(".position_img_count input:checked");
	          var len =chk.length;
	           $(".count3").val(len);
	 });
	
	// 对于拨片的操作
	$(":input[name='radio']").click(function() {
		var va = $('input:radio[name="radio"]:checked').val();
		if (va == 20) {
			$(".position_img_date img:nth-child(2)").css("width", "255px");
			$(".position_img_date").css("width", "255px");
			$(".img_date_show").css("width", "255px");
		}
		if (va == 40) {
			$(".position_img_date img:nth-child(2)").css("width", "307.5px");
			$(".position_img_date").css("width", "307.5px");
			$(".img_date_show").css("width", "307.5px");

		}
		if (va == 50) {
			$(".position_img_date img:nth-child(2)").css({"width": "385.5px"});
			$(".position_img_date").css("width", "385.5px");
			$(".img_date_show").css("width", "385.5px");
		}
		if (va == 80) {
			$(".position_img_date img:nth-child(2)").css("width", "516px");
			$(".position_img_date").css("width", "516px");
			$(".img_date_show").css("width", "516px");	
		}
		if (va == 100) {
			$(".position_img_date img:nth-child(2)").css("width", "778px");
			$(".position_img_date").css("width", "778px");
			$(".img_date_show").css("width", "778px");
		}
	});


	//提交
	var submitStatus=true;
	$(document).on("mousedown",".submitBtn",function(){

		$(".submitBtn").css({"background":"linear-gradient(#2ec0fb, #0e9aee,#0091ea)"});
	})
	$(document).on("mouseup",".submitBtn",function(){
		$(".submitBtn").css({"background":"linear-gradient(#59bbe9, #2ea5df,#0791d7)"});
	});
//防抖变量
	var timer= undefined;
	console.log("2");
	$(document).on("click", ".submitBtn",function(){
		
		  
 	    
		if(!confirm("是否确定提交结果？")){
			return false;
		}
		if(timer){
	        clearTimeout(timer)
	    }
		
	    timer = setTimeout(function () {

			var sumMap = new Map();
            var sumArray = {};
			$('.position_img_analyse input:checked').each(function(index,e){
				if($(this).attr("class")!="fenxi66"){
                    var pid = $(this).attr("name");
                    if(sumMap.get(pid)==undefined){
                    	//第一次
                        var array = new Array();
                        array[0] = $(this).val() + "," + 2;
						sumMap.set(pid,array);
					}else {
                        var array = sumMap.get(pid);
                        var length = array.length;
                        array[length] = $(this).val() + "," + 2;
                        sumMap.set(pid,array);
                    }
				}
			}); 
			
			$('.position_img_count input:checked').each(function(index,e){
				if($(this).attr("class")!="fenxi66"){
                    var pid = $(this).attr("name");
                    if(sumMap.get(pid)==undefined){
                        //第一次
                        var array = new Array();
                        array[0] = $(this).val() + "," + 1;
                        sumMap.set(pid,array);
                    }else {
                        var array = sumMap.get(pid);
                        var length = array.length;
                        array[length] = $(this).val() + "," + 1;
                        sumMap.set(pid,array);
                    }
				}	
			});

            sumMap.forEach(function (value, key, map) {
                console.log("属性：" + key + ",值："+ value);
                sumArray[key] = value;
            });


			if(sumMap.size==0){
				alert("请勾选玻片或灰底图再提交");
				return false;
			}

			$.ajax({ 
		        url:'/chromosome/incident/graySubmit',
		        type:'post',
		        data:{'status':submitStatus,'sumMap':JSON.stringify(sumArray)},
		        success:function(js){
		        	var state = false;
		        	switch(js)
		        	{
		        	case 0:
			        	  //alert("提交成功");
                          layer.msg('提交成功！', {icon: 1,offset: 't'});
			        	  var count = incident_id.parent().find('#countbtn').text();
			    		  if(count==''){
			    			  count++;
			    			  incident_id.after("<img style='margin-left:7px;margin-bottom:3px;margin-right:3px' src='../../../images/up.png'><span id='countbtn'></span>")
			    	          incident_id.parent().find('#countbtn').text(count);
			    		  }else{
			    			  count++;
			    	          incident_id.parent().find('#countbtn').text(count);
			    		  }
			        	  //search1();
			        	  break;
		        	case 1:
		        		alert("提交失败已经被提交过");
		        	  break;
		        	case 2:
		        		alert("提交失败数据写入失败");
		        	  break;
		        	case 3:
		        		alert("请勾选灰底图再提交");
			        	  break;
		        	case 4:
			        	  alert("请选中玻片再提交");
			        	  break;
		        	default:
		        		alert("其他异常");
		        	}
		        	
		        	if(state){
		        		window.location.href='romosome/page/jsp/caryogram/operation/caryindex.jsp'
		        	}
		        	else {
			        	$(".innerbox1").html("");
					}
		        },error:function (msg) {
					alert("提交失败数据写入失败");
					console.log("提交失败数据写入失败:msg="+msg.status+"--"+msg.statusText);
                }
		    });
	        timer = undefined;
	    },400);
	});

})