$(function(){
	//报告跳转
	$('#create_report').click(function(){
		window.location.href='/chromosome/page/jsp/caryogram/operation/caryreport.jsp';
	});
	var storge = window.localStorage;//把百分比的放大存在浏览器缓存里
	//搜索
    $(document).on("click", "#search", function(){
    	$(".file_card").css("display","none");
    	var inc = $(".inName").val();
        var check = $(".incheck").val();
        var contor = $("#control_date2").val();
        var endDate = $("#control_date4").val();

        dele = [];
        delespan = [];
        
        $(".countRight3").html("");
        $(".innerbox7").html("");
        loadEvent({"inName":inc,"date":contor,"check":check,"endDate":endDate});
        setTimeout(function () {
            $(".innerbox7 ul:first span").mousedown();
            
        },600);
    });
    
 //关闭文件卡
    $(".card_close").click(function(){
		$(".file_card").css("display","none")
	});

    
	 //var urls="http://192.168.100.156:18005/images/image/patient/";   //156
//    var urls="http://192.168.80.64:18005/images/image/patient/";    //64
	    //分割图片地址
	    //var greyBaseMapAllChromosome="http://192.168.100.156:8080/images/image/patient/";   //156
//    var greyBaseMapAllChromosome="http://192.168.80.64:8080/images/image/patient/";     //64
	    
	    
	var analy = "";
	var count = "";
	
	$("#index_fenxi1").click(function(){
		window.location.href='/chromosome/page/jsp/caryogram/operation/caryindex_new.jsp';
	});

	$(document).ready(function(){
		$("#model_analyse1").attr("class","active");
		$.post('/chromosome/KaryotypeAnalysis/getUserParams',function(json){
    		var user = json;
            if(user[0] != null || user[0] != ""){
                $("#control_date2").val(user[0]);
            }
            if(user[3] != null || user[3] != ""){
                $("#control_date4").val(user[3]);
            }
            if(user[1] != null || user[1] != ""){
                if(user[1]=="0"){
                    $('.incheck option:eq(0)').attr('selected','selected');
                }else if(user[1]=="1"){
                    $('.incheck option:eq(1)').attr('selected','selected');
                }else if(user[1]=="2"){
                    $('.incheck option:eq(2)').attr('selected','selected');
                }else if(user[1]=="3"){
                	$('.incheck option:eq(3)').attr('selected','selected');
                }else if(user[1]=="4"){
                    $('.incheck option:eq(4)').attr('selected','selected');
                }   
            }
            if(user[2] != null || user[2] != ""){
                $(".inName").val(user[2]);
            }
            
            var inc = $(".inName").val();
            var check = $(".incheck").val();
            var contor = $("#control_date2").val();
            var endDate = $("#control_date4").val();
            console.log("inc ===== "+inc);
            console.log("check ===== "+check);
            console.log("contor ===== "+contor);
            $(".innerbox7").html("");
            loadEvent({"inName":inc,"date":contor,"check":check,"endDate":endDate});
           /* setTimeout(function () {
                $(".innerbox7 ul:first span").mousedown();
            },1000);*/
            /*var inName = '1';
    		var xsum = '';
    		var tiaoshu = '';
    		//loadEvent();
    		$.ajax({
    			
    			url : '/chromosome/chromatid/grayQuerysC',
    			type : 'post',
    			data : JSON.stringify({
    				dsum :inName,
    				xsum :xsum,
    				tiaoshu :tiaoshu
    			}),
    			contentType : "application/json;charset=utf-8",
    			dataType : "json",
    			success : function(js) {
    				console.log('进入');
    				setTimeout(function () {
    					  $("#"+js.Info.evenNumber).children().css("color","#00a0e9");
    				    },600);
    				
    				var list = js["CountQuery"];
    				var Info = js["Info"];
    				var count = js["count"];
    				var count0 = js["count0"];
    				setTimeout(function () {
    					$("#"+Info.evenNumber+" #incident").mousedown();
  				    },600);
    				
    					  		var ht = "";
    							$(list).each(function(index, e) {
    												ht += "<div class='img_date' id="
    														+ e.grayId + ">";
    												if (e.analyCheck || e.countCheck) {
    													ht += "<i><img src = '../../../images/patientdata/ture_03.png' /></i>";
    												}
    												ht += "<img src="
    														+ urls
    														+ e.grayUrl
    														+ " onclick='goToCrayexcision("
    														+ e.grayId + ")'/>\n";
    												
    												
    												if (e.analyCheck || e.countCheck) {
    													ht += "      \t\t<div class='count_img_date bgcolor'>\n"
    														+ "      \t\t\t<span class='imgs imgs_identifier'>"
    														+ e.inName
    														+ "</span>\n"
    														+ "      \t\t<span class='imgs1 imgs_date llll'>"
    														+ e.grayDate
    														+ "</span>\n"
    														+ "      \t\t<span class='imgs2 imgs_id'>"
    														+ e.slideName
    														+ " "
    														+ e.grayNumber
    														+ "</span>\n"
    														+ "      \t\t<span class='imgs3 imgs_number'>"
    														+ e.grayCount
    														+ "</span></div>\n"
    														+ "      \t\t</div>";
    												}else{
    													ht += "      \t\t<div class='count_img_date'>\n"
    														+ "      \t\t\t<span class='imgs imgs_identifier'>"
    														+ e.inName
    														+ "</span>\n"
    														+ "      \t\t<span class='imgs1 imgs_date llll'>"
    														+ e.grayDate
    														+ "</span>\n"
    														+ "      \t\t<span class='imgs2 imgs_id'>"
    														+ e.slideName
    														+ " "
    														+ e.grayNumber
    														+ "</span>\n"
    														+ "      \t\t<span class='imgs3 imgs_number'>"
    														+ e.grayCount
    														+ "</span></div>\n"
    														+ "      \t\t</div>";
    												}
    												
    												

    											});

    							$(".innerbox").append(ht);

    				$("#fileCardId").text(Info.evenNumber);
    				$("#fileCardName").text(Info.name);
    				if(Info.sex==0){
    					$("#fileCardSex").text('男');
    				}else{
    					$("#fileCardSex").text('女');
    				}
    				

    				$("#fileCaryogram").text(Info.caryogram);
            
    				// 没有核对的就按照全部显示
    				if (count0[0].analyCheck == 0) {
    					analy1 = count0[0].analyStatusSum;
    				} else {
    					// 按照核对的显示
    					analy1 = count0[0].analyCheck;
    				}

    				// 没有核对的就按照全部显示
    				if (count0[0].countCheck == 0) {
    					count1 = count0[0].countStatusSum;
    				} else {
    					// 按照核对的显示
    					count1 = count0[0].countCheck;
    				}
    				$("#fileCardAnalyze").text(analy1);
    				$("#fileCardCount").text(count1);
    				
    				拼接已核对/未核对
//    				if(list != ""){
//    					if (list[0].checkStatus=="1")
//    	                {
//    						$(".index_right_span2").attr("src","../../../images/Checked.png")
//    	                }else {
//    	                	$(".index_right_span2").attr("src","../../../images/Unchecked.png")  
//    	                }
//    				}
    				if(js["Status"] == 1){
    					$(".index_right_span2").attr("src","../../../images/Checked.png");
    				}else{
    					$(".index_right_span2").attr("src","../../../images/Unchecked.png");
    				}
    				
    				拼接核型统计数据
    				for ( var a in count) {
    					var sum1 = count[0];
    					var sum2 = count[1];
    					var sum3 = count[2];
    					var sum4 = count[3];
    					var allCheckCount = count[4];
    					var checkCount1 = count[5];
    					var checkCount2 = count[6];
    					var checkCount3 = count[7];
    					var checkCount4 = count[8];
    				}
    				
    				for(var i =0;i < count.length ;i++){
    					
    				}
    				
    				document.getElementById('sum1').innerHTML = sum1;
    				document.getElementById('sum2').innerHTML = sum2;
    				document.getElementById('sum3').innerHTML = sum3;
    				document.getElementById('sum4').innerHTML = sum4;
    				
    				document.getElementById('check_fruit').value = allCheckCount;
    				document.getElementById('fruit1_1').value = checkCount1;
    				document.getElementById('fruit2_2').value = checkCount2;
    				document.getElementById('fruit3_3').value = checkCount3;
    				document.getElementById('fruit4_4').value = checkCount4;
    			}
    		});*/
    	});
		
		
	})
	
	
	
    function loadEvent(param){
		console.log("666");
        $.ajax({
            url:'/chromosome/KaryotypeAnalysis/eventList',
            type:'post',
            data:param,
            dataType:"json",
            success:function(json){
                var ht="";
                var ht1="";
                if(json.event!=''){
                	$(json.event).each(function (index, e) {
                        ht=ht+"<ul class='"+ht1+"' id='"+e.inName+"'><span id='incident' class='";
                        if(e.status){
                            ht=ht+"gray";
                            ht=ht+"'><img id='incident_img' src='../../../images/graymax.png'/>"+e.inName+" ("+e.count+")</span>";
                        }else{
                            ht=ht+"gray1";
                            ht=ht+"'><img id='incident_img' src='../../../images/whitemax.png'/>"+e.inName+" ("+e.count+")</span>";
                        }
                        //ht=ht+"gray1";
                       



                        ht=ht+"</ul>";

                    });
                }else{
                	$("#fileCardId").text('');
					$("#fileCardName").text('');
					$("#fileCardSex").text('');
					$("#fileCaryogram").text('');
					$("#fileCardAnalyze").text('');
					$("#fileCardCount").text('');
	                $(".index_right_span2").attr("src","../../../images/Unchecked.png");
	                
					$("#sum1").text('');
					$("#sum2").text('');
					$("#sum3").text('');
					$("#sum4").text('');
					
					var allCheckCount = '';
					var checkCount1 = '';
					var checkCount2 = '';
					var checkCount3 = '';
					var checkCount4 = '';
					
					document.getElementById('check_fruit').value = allCheckCount;
					document.getElementById('fruit1_1').value = checkCount1;
					document.getElementById('fruit2_2').value = checkCount2;
					document.getElementById('fruit3_3').value = checkCount3;
					document.getElementById('fruit4_4').value = checkCount4;
                }

              //  $(".countRight3").html("");
                $("#index_right_r").html("");
                $(".innerbox7").html("");
                $(".innerbox7").html(ht);
                
                if(json.dsum==null){
                	console.log("dsum==null");
                	$(".innerbox7 ul:first span").mousedown();
                }else{
                	console.log("dsum=="+json.dsum);
                	$("#"+json.dsum+" #incident").mousedown();
                }
          	  
                
          	  
            }
        });
    }
	
	
	
	
	
	
	
	 function jishu(dsum, xsum, tiaoshu) {
			$(".countRight3").children('div').remove();
			$.ajax({
						url : '/chromosome/chromatid/grayQuerysC',
						type : 'post',
						data : JSON.stringify({
							dsum : dsum,
							xsum : xsum,
							tiaoshu : tiaoshu
						}),
						contentType : "application/json;charset=utf-8",
						dataType : "json",
						success : function(js) {
							var list = js["CountQuery"];
							var Info = js["Info"];
							var count = js["count"];
							var count0 = js["count0"];
							var reportRecord = js["reportRecord"];
							/*console.log(list);
							console.log(Info);
							console.log(count);
							console.log(count0);*/
							var ht = "";
							$(list).each(function(index, e) {
								if(e.grayCountRemark != null && e.grayCountRemark != ""){
									e.grayCount = e.grayCount+','+ e.grayCountRemark;
								}
												ht += "<div class='img_date' id="
														+ e.grayId + ">";
												if (e.analyCheck || e.countCheck) {
													ht += "<i><img src = '../../../images/patientdata/ture_03.png' /></i>";
												}
												ht += "<img src="
														+ urls
														+ e.grayUrl
														+ " onclick='goToCrayexcision("
														+ e.grayId + ")'/>\n";
												ht += "      \t\t<div class='count_img_date'>\n"
														+ "      \t\t\t<span class='imgs imgs_identifier'>"
														+ e.inName
														+ "</span>\n"
														+ "      \t\t<span class='imgs1 imgs_date llll'>"
														+ e.grayDate
														+ "</span>\n"
														+ "      \t\t<span class='imgs2 imgs_id'>"
														+ e.slideName
														+ " "
														+ e.grayNumber
														+ "</span>\n"
														+ "      \t\t<span title="+e.grayCount+" class='imgs3 imgs_number'>"
														+ e.grayCount
														+ "</span></div>\n"
														+ "      \t\t</div>";

											});

							$(".countRight3").append("<div class='innerbox'>" + ht + "</div>");
							if(storge.percentage!=undefined){
								if (storge.percentage == 40) {
									$("input[name='radio1'] + label").css({
										"color":"#1188bf",
										"background-color":"#424040"
									});
									$('#rad7 + label').css({
										"color":"#fff",
										"background-color":"#009ae0"
									});
									$(".img_date").css("width", "304px");
									$(".img_date").find("img").css({
										"width" : "304px"
									});
									$(".count_img_date").css("width", "304px");
								}
								if (storge.percentage == 60) {
									$("input[name='radio1'] + label").css({
										"color":"#1188bf",
										"background-color":"#424040"
									});
									$('#rad8 + label').css({
										"color":"#fff",
										"background-color":"#009ae0"
									});
									$(".img_date").css("width", "382px");
									$(".img_date").find("img").css({
										"width" : "382px"
									});
									$(".count_img_date").css("width", "382px");

								}
								
								if (storge.percentage == 80) {
									$("input[name='radio1'] + label").css({
										"color":"#1188bf",
										"background-color":"#424040"
									});
									$('#rad9 + label').css({
										"color":"#fff",
										"background-color":"#009ae0"
									});
									$(".img_date").css("width", "513px");
									$(".img_date").find("img").css({
										"width" : "513px"
									});
									$(".count_img_date").css("width", "513px");
								}}

							$("#fileCardId").text(Info.inName);
							$("#fileCardName").text(Info.cardName);
							if(Info.cardSex==0){
								$("#fileCardSex").text('男');
							}else if(Info.cardSex==1){
								$("#fileCardSex").text('女');
							}else{
								$("#fileCardSex").text('');
							}

							$("#fileCaryogram").text(reportRecord);

							// 没有核对的就按照全部显示
							if (count0[0].analyCheck == 0) {
								analy1 = count0[0].analyStatusSum;
							} else {
								// 按照核对的显示
								analy1 = count0[0].analyCheck;
							}

							// 没有核对的就按照全部显示
							if (count0[0].countCheck == 0) {
								count1 = count0[0].countStatusSum;
							} else {
								// 按照核对的显示
								count1 = count0[0].countCheck;
							}
							$("#fileCardAnalyze").text(analy1);
							$("#fileCardCount").text(count1);
							
							/*拼接已核对/未核对*/
							/*if(list != ""){
								if (list[0].checkStatus=="1")
				                {
				                    $(".index_right_span2").html("已核对");
				                }else {
				                    $(".index_right_span2").html("未核对");
				                }
							}
							*/
							if(js["Status"] == 1){
		    					$(".index_right_span2").attr("src","../../../images/Checked.png");
		    				}else{
		    					$(".index_right_span2").attr("src","../../../images/Unchecked.png");
		    				}
							/*拼接核型统计数据*/
							for ( var a in count) {
								var sum1 = count[0];
								var sum2 = count[1];
								var sum3 = count[2];
								var sum4 = count[3];
								var allCheckCount = count[4];
								var checkCount1 = count[5];
								var checkCount2 = count[6];
								var checkCount3 = count[7];
								var checkCount4 = count[8];
							}
							
							/*for(var i =0;i < count.length ;i++){
								
							}*/
							
							document.getElementById('sum1').innerHTML = sum1;
							document.getElementById('sum2').innerHTML = sum2;
							document.getElementById('sum3').innerHTML = sum3;
							document.getElementById('sum4').innerHTML = sum4;
							
							document.getElementById('check_fruit').value = allCheckCount;
							document.getElementById('fruit1_1').value = checkCount1;
							document.getElementById('fruit2_2').value = checkCount2;
							document.getElementById('fruit3_3').value = checkCount3;
							document.getElementById('fruit4_4').value = checkCount4;
							
						

							if (va == 40) {
								$('#rad7 + label').click();
							}
							if (va == 60) {
								$('#rad8 + label').click();

							}
							if (va == 80) {
								$('#rad9 + label').click();
							}
							
						}
					});
		
		}
	 
	   $("input[name='radio1'] + label").mousedown(function(){
		   $("input[name='radio1'] + label").css({
  			  "color":"#1188bf",
  			  "background-color":"#424040"
  		  })
		   $(this).css({
 			  "color":"#fff",
 			  "background-color":"#00a0e9"
 		  })
	   })
	 
	 
	 //点击跳转计数核对页面
	 $(document).on("click",".img_date",function() {
					var id = $(this).attr("id");
					window.location.href = '/chromosome/chromatid/chromRequest?grayId=c'
							+ id + '';
				});
	 
	 
	  //点击左侧事件列表查询相应事件
	    $(document).on("mousedown", "#incident", function(e) {
	    	dele = [];//清空不同事件号选中的值
	    	$(".gray1>img").attr("src","../../../images/whitemax.png");
			$(this).find("#incident_img").attr("src","../../../images/bluemax.png");
			$(".gray>img").attr("src","../../../images/graymax.png");
	    	$(".file_card").css("display","none");
	        var dt = $(this);
	        var inName = dt.parent().attr('id');
	        var loadGreyBaseMap = true;
	        if($(this).hasClass("gray")){
	        	loadGreyBaseMap=false;
	        }
	        
	        
	        console.log(dt);
	        $("ul span").removeAttr("style");
	        dt.css('color','#00a0e9');
            if($(this).attr("class").indexOf("gray1")!=-1){
	        	
	            $.ajax({
		            url:'/chromosome/KaryotypeAnalysis/queryGreyBaseMap',
		            type:'post',
		            data:{'inName':inName},
		            dataType:"json",
		            success:function(js){

		                $("#index_right_r").html("");

		                        /*var ht="";

		                        var GreyBaseMap = js.GreyBaseMap;

		                        ht = loadGreyBaseMapData(GreyBaseMap);*/
		                        var p="";
		                        $(js.Plectrum).each(function (index,ee) {
		                            $(".innerbox7 ul li").remove();
		                           
		                            p = p+ "<li id='child_node' style='display: list-item;'>"+
		                                "<span id='clide' class='";
		                           if(ee.status==1){
		                                p=p+"gray"
		                            }else{
		                                p=p+"gray1"
		                            }
		                            //p=p+"gray1";
		                            p=p+"' aa='"+ee.slideId+"'><img id='clide_img' src='../../../images/white.png'/>"+ee.slideName+" ("+ee.count+")</span></li>";

		                            $("#"+ee.inName+"").append(p);
		                        });
		                        /*$("#index_right_r").append(ht);*/
		                        $("#"+inName).children().css("color","#00a0e9");
		                        jishu(inName,'','');
		            }
		        });
	        }else if($(this).attr("class").indexOf("gray")!=-1){
	        	$.ajax({
					url : '/chromosome/chromatid/grayQuerysC1',
					type : 'post',
					data : JSON.stringify({
						dsum : inName,
						xsum : '',
						tiaoshu : ''
					}),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(js) {
					       $.ajax({
					            url:'/chromosome/KaryotypeAnalysis/queryGreyBaseMap',
					            type:'post',
					            data:{'inName':inName},
					            dataType:"json",
					            success:function(js){

					                $("#index_right_r").html("");

					                        var ht="";

					                        var GreyBaseMap = js.GreyBaseMap;
					                        console.log($(this).attr("class"));
					                        //如果是灰色就不展示灰底图
					                        if(loadGreyBaseMap){
					                        	ht = loadGreyBaseMapData(GreyBaseMap);
					                            $("#index_right_r").append(ht);
					                        }
					                        //console.log("hasClass:"+$(this).hasClass("gray"));
					                        
					                        var p="";
					                        $(js.Plectrum).each(function (index,ee) {
					                            $(".innerbox7 ul li").remove();
					                            
					                            p = p+ "<li id='child_node' style='display: list-item;'>"+
					                                "<span id='clide' class='";

					                            if(ee.status==1){
					                                p=p+"gray"
					                            }else{
					                                p=p+"gray1"
					                            }
					                            p=p+"' aa='"+ee.slideId+"'><img id='clide_img' src='../../../images/white.png'/>"+ee.slideName+"("+ee.count+")</span></li>";

					                            $("#"+ee.inName+"").append(p);
					                        });

					            }
					        });
						
						
						var Info = js["Info"];
						var count0 = js["count0"];
						/*console.log(list);
						console.log(Info);
						console.log(count);
						console.log(count0);*/					

						$("#fileCardId").text(js["inName"]);					
						
						// 没有核对的就按照全部显示
						if (count0[0].analyCheck == 0) {
							analy1 = count0[0].analyStatusSum;
						} else {
							// 按照核对的显示
							analy1 = count0[0].analyCheck;
						}

						// 没有核对的就按照全部显示
						if (count0[0].countCheck == 0) {
							count1 = count0[0].countStatusSum;
						} else {
							// 按照核对的显示
							count1 = count0[0].countCheck;
						}
						$("#fileCardAnalyze").text(analy1);
						$("#fileCardCount").text(count1);

	    				$(".index_right_span2").attr("src","../../../images/Unchecked.png");
	    						
					}
				});
	   		    $("#fileCardName").text('');
				$("#fileCardSex").text('');
				$("#fileCaryogram").text('');
                $(".index_right_span2").attr("src","../../../images/Unchecked.png");
                
				$("#sum1").text('');
				$("#sum2").text('');
				$("#sum3").text('');
				$("#sum4").text('');
				
				var allCheckCount = '';
				var checkCount1 = '';
				var checkCount2 = '';
				var checkCount3 = '';
				var checkCount4 = '';
				
				document.getElementById('check_fruit').value = allCheckCount;
				document.getElementById('fruit1_1').value = checkCount1;
				document.getElementById('fruit2_2').value = checkCount2;
				document.getElementById('fruit3_3').value = checkCount3;
				document.getElementById('fruit4_4').value = checkCount4; 
				$(".countRight3").html(" ")
	        }else{
	        	$(".countRight3").html(" ")
	        }
	     
	        
	    });
	 

		  // 点击分析返回该事件总览
	$(document).on("mousedown", "#index_jishu", function(e) {
		var inName = $("#fileCardId").text();
		/*jishu(inName, '', '');*/
		$("#"+inName+" #incident").mousedown();
	});
	
	
	 
	    
	    
	    function loadGreyBaseMapData(jsonData) {
	        var ht = "";
	        $(jsonData).each(function (index,e) {
	            ht +="<div>";
	            if(e.analyCheck) {
	                ht += "<i><img src = '../../../images/patientdata/ture_03.png' /></i>";
	            }
	            ht += "<img src="+urls+e.grayUrl+" onclick='goToCrayexcision("+e.grayId+")'/>\n";
	            if(e.greyBaseMapAllChromosome!=null){
	                ht +="<img src="+greyBaseMapAllChromosome+e.greyBaseMapAllChromosome+" " +
	                    "onclick='goToChromanalyse("+e.grayId+")'/>";
	            }
	            ht+=
	                "      \t\t<div class='index_right_r_div'>\n" +
	                "      \t\t\t<span>"+e.inName+"</span>\n" +
	                "      \t\t<span>"+e.grayDate+"</span>\n" +
	                "      \t\t<span>"+e.slideName+" "+e.grayNumber+"</span>\n" +
	                "      \t\t<span>"+e.grayCray+"</span></div>\n" +
	                "      \t\t</div>";
	        });

	        return ht;
	    }
	    
	    
	    
	    
	    
	    //玻片查询
	    $(document).on("mousedown", "#clide.gray1", function(e) {
	        var dt = $(this); 
	        $("#clide.gray1 img").attr("src","../../../images/white.png");
	    	$(this).find("img").attr("src","../../../images/blue.png");
	        var inName = $(this).parent().parent().attr("id");
	        var plectrumName  = dt.text();
	        $("#child_node span").css("color","#ffffff");
	        $(this).css("color","#00a0e9");
            $(".file_card").css("display","none");
            if($(this).hasClass("gray1")){
            	jishu(inName,plectrumName,'');
	        }
	        
	    });

	    //不同条数的玻片查询
	    $(document).on("click", "#bear_fruit", function() {
	    	var dsum = $("#fileCardId").text();
	    	var xsum ="";
	    	var tiaoshu = "45";
	    	$(".bear_div button").css("color","#ffffff");
	    	$(this).css("color","rgb(0, 160, 233)");
	    	/*console.log(dsum+' '+xsum+' '+tiaoshu);*/
	    	
	    	jishu(dsum,xsum,tiaoshu);
 	    });
	    $(document).on("click", "#bear_fruit1", function() {
	    	$(".bear_div button").css("color","#ffffff");
	    	$(this).css("color","rgb(0, 160, 233)");
	    	var dsum = $("#fileCardId").text();
	    	var xsum ="";
	    	var tiaoshu = "46";
	    	/*console.log(dsum+' '+xsum+' '+tiaoshu);*/
	    	jishu(dsum,xsum,tiaoshu);
	    });
	    $(document).on("click", "#bear_fruit2", function() {
	    	$(".bear_div button").css("color","#ffffff");
	    	$(this).css("color","rgb(0, 160, 233)");
	    	var dsum = $("#fileCardId").text();
	    	var xsum ="";
	    	var tiaoshu = "47";
	    	/*console.log(dsum+' '+xsum+' '+tiaoshu);*/
	    	jishu(dsum,xsum,tiaoshu);
	    });
	    $(document).on("click", "#bear_fruit3", function() {
	    	$(".bear_div button").css("color","#ffffff");
	    	$(this).css("color","rgb(0, 160, 233)");
	    	var dsum = $("#fileCardId").text();
	    	var xsum ="";
	    	var tiaoshu = "48";
	    	/*console.log(dsum+' '+xsum+' '+tiaoshu);*/
	    	jishu(dsum,xsum,tiaoshu);
	    });
	    
	    
	    
	    $("#event_date1").mousedown(function(){
	    	$(this).attr("src","../../../images/shijian.png")
	    });
	    
	    $("#event_date1").mouseup(function(){
	    	$(this).attr("src","../../../images/shijian1.png")
	    })
	    
	 // 打开文件卡
		$("#event_date1").click(function() {
			$(".file_card").css("display", "block");
			var date = new Date();
			var seperator1 = "-";
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var strDate = date.getDate();
			var grays = $(".count_img_date").first().children('.imgs_id').text().substring('0','3');
			
			if (month >= 1 && month <= 9) {
				month = "0" + month;
			}
			if (strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = year + seperator1 + month
					+ seperator1 + strDate;
			var inNamesss = $(".number1").text();
			//清空数据
			$("#yourformid").find("input").val("");
			$.ajax({
				url : '/chromosome/chCount/folderInit2',
				type : 'post',
				data : {},
				dataType : "json",
				success : function(js) {
					var caryogram = $("#fileCaryogram").text();
					console.log(js);// begin
					var data = js[1];
					if(js[0]){
						console.log("A事件隐藏");
						//病历号
						$("#medical_record").attr('disabled','disabled')
						$("#medical_record").css({'opacity': '0.7'})
						//姓名
						$("#name").attr('disabled','disabled')
						$("#name").css({'opacity': '0.7'})
						//年龄
						$("#age").attr('disabled','disabled')
						$("#age").css({'opacity': '0.7'})
						//性别
						$("#sex").attr('disabled','disabled')
						$("#sex").css({'opacity': '0.7'})
						//孕龄周
						$("#pregnancy").attr('disabled','disabled')
						$("#pregnancy").css({'opacity': '0.7'})
						//孕龄天
						$("#pregnancys").attr('disabled','disabled')
						$("#pregnancys").css({'opacity': '0.7'})
						//临床诊断
						$("#diagnose").attr('disabled','disabled')
						$("#diagnose1").attr('disabled','disabled')
						$("#diagnose").css({'opacity': '0'})
						$("#diagnose1").css('background','#CDCDCD')
						
					}else{
						//病历号
						$("#medical_record").removeAttr('disabled')
						$("#medical_record").css({'opacity': '1'})
						//姓名
						$("#name").removeAttr('disabled')
						$("#name").css({'opacity': '1'})
						//年龄
						$("#age").removeAttr('disabled')
						$("#age").css({'opacity': '1'})
						//性别
						$("#sex").removeAttr('disabled')
						$("#sex").css({'opacity': '1'})
						//孕龄周
						$("#pregnancy").removeAttr('disabled')
						$("#pregnancy").css({'opacity': '1'})
						//孕龄天
						$("#pregnancys").removeAttr('disabled')
						$("#pregnancys").css({'opacity': '1'})
						//临床诊断
						$("#diagnose").removeAttr('disabled')
						$("#diagnose1").removeAttr('disabled')
						$("#diagnose").css({'opacity': '1'})
						$("#diagnose1").css('background','#fff')
						//病历号
						if(data.medicalRecord!=null) $("#medical_record").val(data.medicalRecord);
						//姓名
						if(data.name!=null) $("#name").val(data.name);
						//年龄
						if(data.age!=null) $("#age").val(data.age);
						//性别
						if(data.sex!=null) $("#sex").val(data.sex);
						//孕龄周
						if(data.pregnancy!=null) $("#pregnancy").val(data.pregnancy);
						//孕龄天
						if(data.pregnancys!=null) $("#pregnancys").val(data.pregnancys);
						//临床诊断
						if(data.diagnose!=null) $("#diagnose").val(data.diagnose);
					}
					//事件卡
					if(data.evenNumber!=null) $("#even_number").val(data.evenNumber);
					//样本来源
					if(data.specimenSource!=null) $("#specimen_source").val(data.specimenSource); 
					//采集日期
					if(data.gatherDate!=null) $("#gather_date").val(data.gatherDate);
					//接种日期
					if(data.vaccinateDate!=null) $("#vaccinate_date").val(data.vaccinateDate);
					//意见
					if(data.suggestion!=null) $("#suggestion").val(data.suggestion);
					//制片人
					if(data.theProducers!=null) $("#the_producers").val(data.theProducers);
					//阅片人及打印人
					if(data.putAsealOn!=null) $("#put_a_seal_on").val(data.putAsealOn);
					//校片人
					if(data.examineAndNucleus!=null) $("#examine_and_nucleus").val(data.examineAndNucleus);
					//审核人
					if(data.examineAndVerify!=null) $("#examine_and_verify").val(data.examineAndVerify);
					//核对
					if(data.check!=null) $("#check").val(data.check);
					//报告日期
					if(data.reportDate!=null) $("#report_date").val(data.reportDate);
					//中期
					if(data.whole!=null) $("#whole").val(data.whole);
					//核型直接带入生成报告末次核型（未生成报告则为空）
					$("#caryogram").val(caryogram);
				}
			});
		});
		
		
		//禁止鼠标右键
		document.oncontextmenu = function(){
	        event.returnValue = false;
	        };
		
//		
//		$(document).on("click","#ascertain",function() {
//					var evenNumber = document.getElementById('even_number').value;
//					var medicalRecord = document
//							.getElementById('medical_record').value;
//					var name = document.getElementById('name').value;
//					var age = document.getElementById('age').value;
//					var sex = document.getElementById('sex').value;
//					var pregnancy = document.getElementById('pregnancy').value;
//					var pregnancys = document.getElementById('pregnancys').value;
//					if(pregnancy!=''){
//						if(pregnancy>=0&&pregnancy<=42){
//							if(pregnancy.split("+").length>1){
//								pregnancy = pregnancy.split("+")[1];
//							}
//						}else{
//							alert('请输入正确的孕龄周');
//							return false;
//						}
//					}
//					if(pregnancys!=''){
//						if(pregnancys>=0&&pregnancys<=7){
//							
//						}else{
//							alert('请输入正确的孕龄天');
//							return false;
//						}
//					}
//					if(pregnancy==''&&pregnancys!=''){
//						pregnancy = 0;
//					}
//					if(pregnancys==''&& pregnancy!=''){
//						pregnancys = 0;
//					}
//					var gatherDate = document.getElementById('gather_date').value;
//					var vaccinateDate = document
//							.getElementById('vaccinate_date').value;
//					var diagnose = document.getElementById('diagnose').value;
//					var suggestion = document.getElementById('suggestion').value;
//					var caryogram = document.getElementById('caryogram').value;
//					var specimenSource = document
//							.getElementById('specimen_source').value;
//					var theProducers = document
//							.getElementById('the_producers').value;
//					var putAsealOn = document
//							.getElementById('put_a_seal_on').value;
//					var examineAndNucleus = document
//							.getElementById('examine_and_nucleus').value;
//					var examineAndVerify = document
//							.getElementById('examine_and_verify').value;
//					var reportDate = document.getElementById('report_date').value;
//					var check = document.getElementById('check').value;
//					var whole = document.getElementById('whole').value;
//
//					$.ajax({
//						url : '/chromosome/chCount/updateFolder',
//						type : 'post',
//						data : {
//							evenNumber : evenNumber,
//							medicalRecord : medicalRecord,
//							name : name,
//							age : age,
//							sex : sex,
//							pregnancy : pregnancy,
//							pregnancys : pregnancys,
//							gatherDate : gatherDate,
//							vaccinateDate : vaccinateDate,
//							diagnose : diagnose,
//							suggestion : suggestion,
//							caryogram : caryogram,
//							specimenSource : specimenSource,
//							theProducers : theProducers,
//							putAsealOn : putAsealOn,
//							examineAndNucleus : examineAndNucleus,
//							examineAndVerify : examineAndVerify,
//							reportDate : reportDate,
//							check : check,
//							whole : whole
//						},
//						dataType : "json",
//						success : function(data) {
//						}
//					});
//					$(".file_card").css("display", "none");
//
//				});
		
		 //计数
        var dele = [];
        var delespan = [];
    	$(document).on("mousedown", ".img_date", function(e){
    		if(e.button==2){
    			 var tatues = true;
    			 for (var int = 0; int < dele.length; int++) {
    				 if($(this).attr("id")==dele[int]){
    					 tatues=false;
    					 dele.splice(int,1);
    					 delespan.splice(int,1);
    					 $(this).find(".shade").remove();
    				 }
    				 
				}
    			if(tatues) {
    				dele.push($(this).attr("id"));
    				delespan.push($(this).find("span:eq(3)").text());
    				$(this).append("<div class='shade'></div>");
    				$(this).find(".shade").css({
    					"width":$(this).children('img').width(),
    					"height":$(this).height(),
    					"opacity":"0.4",
    					"top":0,
    				    "position":"absolute",
    				    "background-color":"#000",
    				    "z-index":"11"
    				})
    			}
    		}
    	});
    	
    	   $(document).keydown(function(e){
		        if(e.keyCode==46){
		        	console.log(dele);
		        	   if(dele!=''){
		        		   if(confirm("确定删除吗？")){
		        			   console.log(delespan);
		        			   for (var int = 0; int < dele.length; int++) {
								   $("#"+dele[int]).remove();  
							    }
		        			  
		        			   for (var int2 = 0; int2 < delespan.length; int2++) {
		        				   console.log("delespan[int2]:"+delespan[int2]);
								if(delespan[int2]==45){
									var sum1 = $("#sum1").text();
									sum1--;
									$("#sum1").text(sum1)
								
								}
								if(delespan[int2]==46){
									var sum2 = $("#sum2").text();
									sum2--;
									$("#sum2").text(sum2)
								
								}
								if(delespan[int2]==47){
									var sum3 = $("#sum3").text();
									sum3--;
									$("#sum3").text(sum3)
									
									}
								if(delespan[int2]<45 || delespan[int2]>47){
									var sum4 = $("#sum4").text();
									sum4--;
									$("#sum4").text(sum4)
							
								}
		
							}
		        			   $.ajax({
							       url : '/chromosome/one_key_delete',
								   type : 'post',
								   data : {'jumpCondition':'CountDelete','ids' :dele,'parameter':"DELETE_CountGreyBaseMap",'count45' :$("#fruit1_1").val(),'count46':$("#fruit2_2").val(),"count47":$("#fruit3_3").val(),"countElse":$("#fruit4_4").val()},
								   traditional: true,
								   success : function(js) {	
									   console.log(js.countElse);
									   $("#fruit1_1").val(js.count45);
									   $("#fruit2_2").val(js.count46);
									   $("#fruit3_3").val(js.count47);
									   $("#fruit4_4").val(js.countElse)
									   $("#check_fruit").val(js.count45+js.count46+js.count47+js.countElse);  
									   
									   
									   
									    var analy;
						                var count;
						                //没有核对的就按照全部显示
						                if(js.userInfo.analyCheck==0){
						                    analy=js.userInfo.analyStatusSum;
						                }else {
						                    //按照核对的显示
						                    analy=js.userInfo.analyCheck;
						                }
						                if(js.userInfo.analyCheck==0){
						                    count=js.userInfo.countStatusSum;
						                }else {
						                    //按照核对的显示
						                    count=js.userInfo.countCheck;
						                }
						                $("#fileCardAnalyze").html(analy);
						                
						                var sum1 = $("#sum1").text();
						                var sum2 = $("#sum2").text();
						                var sum3 = $("#sum3").text();
						                var sum4 = $("#sum4").text();
						                var sum5 = document.getElementById('check_fruit').value;
						                
						                if(sum5==0){
							                $("#fileCardCount").html(parseInt(sum1)+parseInt(sum2)+parseInt(sum3)+parseInt(sum4));
						                }else{
						                    $("#fileCardCount").html(sum5);
						                }
						                
									}
									});
		        			   dele = [];
		        			   delespan = [];
				        	}else{
				        		 for (var int = 0; int < dele.length; int++) {
									   $("#"+dele[int]).find("img").css("border","none");
								    }
				        		dele = [];
				        		delespan = [];
				        	}
			           }

		        }
		    });
    	   
    	   var va = '';
    	// 页面div大小
    	   $(":input[name='radio1']").click(function() {
    			 va = $('input:radio[name="radio1"]:checked').val();

    			storge.setItem("percentage",va);
    			if (va == 40) {
    				$(".img_date").css({"width":"310px",
    					"margin":"2px 2px 2px 2px"});
    				$(".img_date").find("img").css({
    					"width" : "310px"
    				});
    				$(".count_img_date").css("width", "310px");
    				$(".shade").css({
  					"width":$('.img_date').children('img').width(),
  					"height":$('.img_date').height(),
  					"opacity":"0.4",
  					"top":0,
  				    "position":"absolute",
  				    "background-color":"#000",
  				    "z-index":"11"
  				})
    			}
    			if (va == 60) {
    				$(".img_date").css({"width":"387px",
    					"margin":"2px 2px 2px 2px"});
    				$(".img_date").find("img").css({
    					"width" : "387px",
    					
    				});
    				$(".count_img_date").css("width", "387px");
    				$(".shade").css({
  					"width":$('.img_date').children('img').width(),
  					"height":$('.img_date').height(),
  					"opacity":"0.4",
  					"top":0,
  				    "position":"absolute",
  				    "background-color":"#000",
  				    "z-index":"11"
  				})

    			}
    			if (va == 80) {
    				$(".img_date").css({"width":"519px",
    					"margin":"2px 2px 2px 2px"});
    				$(".img_date").find("img").css({
    					"width" : "519px",
    					
    				});
    				$(".count_img_date").css("width", "519px");
    				$(".shade").css({
  					"width":$('.img_date').children('img').width(),
  					"height":$('.img_date').height(),
  					"opacity":"0.4",
  					"top":0,
  				    "position":"absolute",
  				    "background-color":"#000",
  				    "z-index":"11"
  				})
    			}
    			

    		});


        laydate.render({
            elem: '#control_date2'
        });

        laydate.render({
            elem: '#control_date4'
        });
    	   
        
        //搜索快捷键
        $(document).keydown(function(e){
     	   if(e.keyCode==13){
     		   $("#search").click();
     	   }
        })
        
        //悬浮显示
        $(".index_right_span111").hover(function(){
    		$(this).attr("title",$("#fileCaryogram").text());
    	})
		

})