$(function(){
	var pageContext = greyBaseMapAllChromosome;   //156
//	var pageContext = "http://192.168.80.64:18005/images/image/patient/";   //64
	// 禁止鼠标右键
	document.oncontextmenu = function() {
		event.returnValue = false;
	};
	var inName;
	$(document).ready(function(){ grayquery();});
	function grayquery(){
		$("#li_analyse_show_a").removeClass("active");
		$.ajax({ 
				url:'/chromosome/chCount/creapReportInit', 
		        type:'post',
		        data:JSON.stringify(""),
		        contentType: "application/json;charset=utf-8",
		        dataType:"json",
		        success:function(js){
		        	console.log(js);
			        var ht1 = "";
				    for(var a in js){
				        	if(js[a].inName == null){
				    			js[a].inName = "";
				    		}
				    		if(js[a].greyId == null){
				    			js[a].greyId = "";
				    		}
				    		if(js[a].toSystemDate == null){
				    			js[a].toSystemDate = "";
				    		}
				    		if(js[a].grayNumber == null){
				    			js[a].grayNumber = "";
				    		}
				    		if(js[a].chCount == null){
				    			js[a].chCount = "";
				    		}
				    		if(js[a].chromRemake == null){
				    			js[a].chromRemake = "";
				    		}
				    		inName = js[a].inName;
					        ht1=ht1+"<div  class='create_report_img'>"+
					        "<img id = 'pic1' src = '"+pageContext+js[a].chromAllpic+"'>"+
					        "<div>"+
					        "<span class='imgs imgs_identifier'>"+js[a].inName+"</span>"+
					        "<input class='imgs imgs_grad' style='display: none;' value = '"+js[a].greyId+"'>"+
					        "<span class='imgs1 imgs_date'>"+js[a].toSystemDate+"</span>"+
					        "<span class='imgs2 imgs_id'>"+js[a].grayNumber+"</span>"+
					        "<span class='imgs3 imgs_number' title="+js[a].chromRemake+">"+js[a].chromRemake+"</span>"+
					        "</div>"+
					        "</div>"
			        }
			        $("#create_report_show").html(ht1);
			        
			        var imgsL = $(".imgs3")
		        	for(var i=1;i<=imgsL.length;i++){
		        		console.log($(".imgs3").eq(i-1))
		        		console.log($(".imgs3").eq(i-1).width())
		        		if($(".imgs3").eq(i-1).width()>300){
		        			$(".imgs3").eq(i-1).css({"width":"300px","overflow": "hidden","text-overflow": "ellipsis","white-space": "nowrap" });
		        			/*$(".imgs3").eq(i-1).mouseover(function(){
		        				$(this).css({"width":"500px","height":"20px","z-index":"100","position":"absolute","left":"300","top":"30px","background":"#8e8e8e"})
		        			})
		        			$(".imgs3").eq(i-1).mouseout(function(){
		        				$(this).css({"width":"305px","z-index":"0"})
		        			})*/
		        		}
		        		
		        	}

		        }
		});
	}
	
	$("#model_analyse1").addClass("active");
	$("#li_analyse_show_a").addClass("active");
	$("#li_count_a").addClass("active");
	//生成报告页面图片右键
	  /* $.contextMenu({
		  
	        selector: '.create_report_img', 
	        callback: function(items) {
//	        	var pageContext = document.getElementById('PageContext').value+"/pdf/";
	        	var grayNumber = $(this).find("div").find("span").eq(2).text();
	        	var greyId = $(this).find("div").find("input").val();
	        	
	        	var strFullPath=window.document.location.href;  
		        var strPath=window.document.location.pathname;  
		        var pos=strFullPath.indexOf(strPath);  
		        var prePath=strFullPath.substring(0,pos);  
		        var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);  
		        var basePath = prePath;  
		        basePath = prePath + postPath; 
	        	
	            $.ajax({
	            	url:'/chromosome/chCount/creapReportfor', 
			        type:'post',
			        data:{items:items,grayNumber:grayNumber,greyId:greyId},
			        dataType:"json",
			        success:function(js){
			        	window.location = basePath+"/pdf/"+inName+".pdf";
			        },
			        error: function(){
			        	window.location = basePath+"/pdf/"+inName+".pdf";
	    			}
	            });
	        },
	        items: {
	        	"1": {name: "外周血正常异常", icon: "外周血正常异常"},
	        	"2": {name: "外周血正常、异常高分辨", icon: "外周血正常、异常高分辨"},
	        	"3": {name: "外周血异常-审核人", icon: "外周血异常-审核人"},
	            "4": {name: "羊水", icon: "羊水"},
	        }
	    });
	   
	   $(document).on("click",".create_report_img",function(){
		   var id = $(this).children("div").find("input").val();
		   window.location.href='/chromosome/chromatid/chromRequest?grayId=a'+id+'&state=1';
	   });
	   */
	//生成报告页面图片右键
	$(document).on("mousedown",".create_report_img",function(ev){
		if(ev.button==2){
			var grayid = $(this).find(".imgs_grad").val();
			$.ajax({
				url : '/chromosome/chCount/ChMatchKaryotypes',
				type : 'post',
				data : JSON.stringify({
					'report' : 0
				}),
				dataType : "json",
				contentType : "application/json;charset=utf-8",
				success : function(data) {
					console.log(data);
					if(data[0]==5){
						alert("该核型匹配意见失败，请手动选择意见再选择模版生成报告");
						return false;
					}
					else if(data[0]==0){
						alert("该意见无法自动匹配报告，请选择模版生成报告 ");
						return false;
					}else{
						if (data[1]) {
							if (!confirm("该事件中的社会性别和染色体核型不一致，是否继续生成报告?")) {
								return false;
							}
						}
					}
					window.location.href="/chromosome/chCount/ChfolderTolocality?report="+data[0]+"&greyid="+ grayid;
				}
			});
		}
//		   if(ev.button==2){
//			   var inName = $(this).find('span').eq(0).text();
//			   var grayid = $(this).find('.imgs_grad').val();
//				var report = 0;
//				$.ajax({
//					type : 'post',
//					data : { 'inName' : inName },
//					url : '/chromosome/chCount/findSlideName',
//					success : function(data) {
//						slideName = data;
//						grays = slideName;
//						$.ajax({
//							url : '/chromosome/chCount/folderInit',
//							type : 'post',
//							data : {
//								inName : inName
//							},
//							dataType : "json",
//							success : function(data) {
//								var list1 = data[0];
//								var list2 = data[1];
//								var sexFinal = list1[4];
//								var caryogramFinal = list2[0].caryogram;
//								if (list2[0].suggestion == null
//										|| list2[0].suggestion == "") {
//									alert("意见为空，请先选择!");
//								} else {									
//									if(list2[0].pregnancy!=""||inName.split("P").length - 1 > 0){
//										report = 4;
//									}else if(grays.split("h").length - 1 > 0
//											|| grays.split("H").length - 1 > 0
//											|| list2[0].suggestion == "高分辨G带未见异常") {
//											report = 2;													
//									} else if (list2[0].suggestion == "为平衡易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门"
//											|| list2[0].suggestion == "为嵌合体 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为47，XY，+mar 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为47,XX,+mar 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为母源性平衡易位 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为DSD（性发育异常） 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为复杂易位携带者 建议行PGD-CCS 建议来我院看遗传咨询专家门诊") {
//										report = 3;
//									}else if(list2[0].suggestion == "普通G带未见异常"
//											|| list2[0].suggestion == "为罗氏易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为克氏征 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为21三体综合征 建议来我院看遗传咨询专家门诊"
//											|| list2[0].suggestion == "为9号染色体次缢痕区臂间倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》"
//											|| list2[0].suggestion == "为Y染色体倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》"
//											|| list2[0].suggestion == "为15p+ 建议行FISH做进一步检测"
//											|| list2[0].suggestion == "为47，XYY 建议来我院看遗传咨询专家门诊"
//											) {
//										report = 1;
//									}
//									/*alert('report:'+report+' '+'inName:'+inName+' '+'grayNumber:'+grayNumber+' '+'slideName:'+slideName);*/
//	
//									if ((sexFinal == "男"&& caryogramFinal.split("XX").length - 1 > 0)||(sexFinal == "女"&& caryogramFinal.split("XY").length - 1 > 0)) {
//										if (!confirm("该事件中的社会性别和染色体核型不一致，是否继续生成报告?")) {
//											return false;
//										}
//									}
//									window.location.href="/chromosome/chCount/ChfolderTolocality?report="+report+"&greyid="+grayid;
//								}
//							}
//						});
//					},
//				});
//		   }
		   if(ev.button==0){
			   var id = $(this).children("div").find("input").val();
			   window.location.href='/chromosome/chromatid/chromRequest?grayId=a'+id+'&state=1';
		   }
	   });
});