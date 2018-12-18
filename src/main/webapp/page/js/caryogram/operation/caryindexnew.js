$(function () {

	reload();
	$("#model_analyse1").addClass("active") ;
    

    //var urls="http://192.168.100.156:18005/images/image/patient/";   //156
//	var urls="http://192.168.80.64:18005/images/image/patient/";   //64
    //分割图片地址
    //var greyBaseMapAllChromosome="http://192.168.100.156:8080/images/image/patient/";   //156
//	 var greyBaseMapAllChromosome="http://192.168.80.64:8080/images/image/patient/";   //64

    function loadEvent(param){
        $.ajax({
            url:'/chromosome/KaryotypeAnalysis/eventList',
            type:'post',
            data:param,
            dataType:"json",
            success:function(json){
                var ht="";
                var i = 0;
                var oneLine = false;
                $(json.event).each(function (index, e) {
					i++;
                    ht=ht+"<ul  id='"+e.inName+"'><span id='incident' class='";
                    if(e.status){
                        ht=ht+"gray";
                        ht=ht+"'><img id='incident_img' src='../../../images/graymax.png'/>"+e.inName+" ("+e.count+")</span>";
						if(i==1){
                            oneLine = true;
						}
                    }else{
                        ht=ht+"gray1";
                        ht=ht+"'><img id='incident_img' src='../../../images/whitemax.png'/>"+e.inName+" ("+e.count+")</span>";
                    }
                    //ht=ht+"gray1";
                    
                    ht=ht+"</ul>";
                });

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
               	//如果搜索不存在的
                if(i==0 || oneLine){
                	$("#fileCardId").html("");
					$("#fileCardName").html("");
					$("#fileCardSex").html("");
					$("#fileCardGrayCray").html("");
					$("#fileCardAnalyze").html("");
					$("#fileCardCount").html("");
                    $("#fileCardIsCheck").attr("src","../../../images/Unchecked.png");
                }

            }
        });
    }

 // 禁止鼠标右键
	document.oncontextmenu = function() {
		event.returnValue = false;
	};

	 //关闭文件卡
    $(".card_close").click(function(){
		$(".file_card").css("display","none")
	});
    
    function loadGreyBaseMapData(jsonData) {
        var ht = "";
        console.log(jsonData);
        $(jsonData).each(function (index,e) {

            ht +="<div class='index_right_r_img' id="+e.grayId+">";
            if(e.analyCheck) {
                ht += "<i class='analy_check'><img src = '../../../images/patientdata/ture_03.png' class='i_img'/></i>";
            }else{
            	if(e.greyBaseMapTag==1){
            	
            		ht += "<i><img src = '../../../images/you.png' class='you_img'/></i>";
            	}
            }

            			if(e.printReport){
                        	ht += "<i><img src = '../../../images/print_report.png' style='left: 0px;top: 335px'/></i>";
                        }


			   if(e.analyCheck&&e.greyBaseMapAllChromosome!=null){
			                ht += "<img style='width: 387px;height: 383px;float: left;' src="+urls+e.grayUrl+" onclick='goToCrayexcision("+e.grayId+")'/>\n";
			                ht +="<div  align='center' style='width: 393px;background-color: #ffffff;height: 383px;float: left;'><img onerror='javascript:NoImage(this)' style='margin-top: 5%;width: 100%;height: 90%;' src="+greyBaseMapAllChromosome+e.greyBaseMapAllChromosome+" " +
			                    "onclick='goToChromanalyse("+e.grayId+")'/></div>";
			            }else {
			                ht += "<img style='width: 387px;height: 383px;float: left;' src="+urls+e.grayUrl+" onclick='goToCrayexcision("+e.grayId+")'/>\n";
						}

            
            if(e.analyCheck || e.greyBaseMapTag==1){
            	 ht+=
                     "      \t\t<div class='index_right_r_div bgcolor'>\n" +
                     "      \t\t\t<span>"+e.inName+"</span>\n" +
                     "      \t\t<span>"+e.grayDate+"</span>\n" +
                     "      \t\t<span>"+e.slideName+" "+e.grayNumber+"</span>\n" +
                     "      \t\t<span title="+e.grayCray+">"+e.grayCray+"</span></div>\n" +
                     "      \t\t</div>";
            }else{
            	 ht+=
                     "      \t\t<div class='index_right_r_div'>\n" +
                     "      \t\t\t<span>"+e.inName+"</span>\n" +
                     "      \t\t<span>"+e.grayDate+"</span>\n" +
                     "      \t\t<span>"+e.slideName+" "+e.grayNumber+"</span>\n" +
                     "      \t\t<span title="+e.grayCray+">"+e.grayCray+"</span></div>\n" +
                     "      \t\t</div>";
            }
            
           


            
            
        });

        return ht;
    }





    //事件下玻片的查询
    $(document).on("mousedown", "#incident", function(e) {
    	dele = [];//清空不同事件号选中的值
    	$(".gray1>img").attr("src","../../../images/whitemax.png");
		$(this).find("#incident_img").attr("src","../../../images/bluemax.png");
		$(".gray>img").attr("src","../../../images/graymax.png");
    	$(".file_card").css("display","none");
        var dt = this;
        var inName = dt.parentNode.id;
        var loadGreyBaseMap = true;
        if($(this).hasClass("gray")){
        	loadGreyBaseMap=false;
        }
        
        $("ul span").removeAttr("style");
        dt.style.color='#00a0e9';
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
                            p=p+"' aa='"+ee.slideId+"'><img id='clide_img' src='../../../images/white.png'/>"+ee.slideName+" ("+ee.count+")</span></li>";

                            $("#"+ee.inName+"").append(p);
                        });

                        

                $("#fileCardId").html(js.UserInfo.inName);
                $("#fileCardName").html(js.UserInfo.cardName);

                if(js.UserInfo.cardSex==0){
                    $("#fileCardSex").html("男");
                }else if(js.UserInfo.cardSex==1){
                    $("#fileCardSex").html("女");
                }

                $("#fileCardGrayCray").html(js.reportRecord);
                if (js.UserInfo.inCheck)
                {
                	$("#fileCardIsCheck").attr("src","../../../images/Checked.png")
                }else {
                	$("#fileCardIsCheck").attr("src","../../../images/Unchecked.png")
               
 }

                var analy;
                var count;
                //没有核对的就按照全部显示
                if(js.UserInfo.analyCheck==0){
                    analy=js.UserInfo.analyStatusSum;
                }else {
                    analy=js.UserInfo.analyCheck;
                }
                
                if(js.UserInfo.countCheck==0){
                    count=js.UserInfo.countStatusSum;
                }else { 
                    count=js.UserInfo.countCheck;
                }
                $("#fileCardAnalyze").html(analy);
                $("#fileCardCount").html(count);

            }
        });
    });


    //玻片查询图片
    $(document).on("mousedown", "#clide.gray1", function(e) {
    	$("#clide.gray1 img").attr("src","../../../images/white.png");
    	$(this).find("img").attr("src","../../../images/blue.png");
        console.log("玻片查询图片");
        $('#qxBtn').attr("checked",false);
        $(".date_analyse3").val(0);
        $(".count3").val(0);
        $(".rad_1").find("input").eq(2).prop("checked",true);
        $(".rad_1 input[type='radio']").attr("disabled",false);
        $(".qxBtn").removeAttr("disabled");
        var dt = this;
        var plectrumName= dt.getAttribute("aa");
        console.log("param:"+JSON.stringify(plectrumName));
        $("#child_node span").css("color","#ffffff");
        dt.style.color='#00a0e9';
        $.ajax({
            url:'/chromosome/KaryotypeAnalysis/queryGreyBaseMap',
            type:'post',
            data:{'inName': $(this).parent().parent().attr('id'),'pl':plectrumName},
            dataType:"json",
            success:function(json){

                $("#index_right_r").html("");

                var ht="";
                var GreyBaseMap = json.GreyBaseMap;

                ht = loadGreyBaseMapData(GreyBaseMap);

                $("#index_right_r").append(ht);

            }
        });

    });



    function reload(){
    	
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
            
            if(user[3] != null || user[3] != ""){
                $("#control_date4").val(user[3]);
            }
            
            var inc = $(".inName").val();
            var check = $(".incheck").val();
            var contor = $("#control_date2").val();
            var endDate = $("#control_date4").val();
            
            loadEvent({"inName":inc,"date":contor,"check":check,"endDate":endDate});
           
    	});
    	
    	
    }

    //搜索快捷键
    $(document).keydown(function(e){
 	   if(e.keyCode==13){
 		   $("#search").click();
 	   }
    })
    
    
    var dele = []; 
	$("#index_right_r").on("mousedown",".index_right_r_img",function(e){
		if(e.button==2){
			
				 var tatues = true;
    			 for (var int = 0; int < dele.length; int++) {
    				 if($(this).attr("id")==dele[int]){
    					 tatues=false;
    					 $(this).find(".shade").remove();
    					 dele.splice(int,1);
    				 } 
    				 
				}
    			if(tatues) {
    				dele.push($(this).attr("id"));
    				$(this).append("<div class='shade'></div>");

    				$(this).find(".shade").css({
    		    					"width":$(this).width(),
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
	
//delete键删除
	   $(document).keydown(function(e){
	        if(e.keyCode==46){
	        	console.log(dele);
	        	   if(dele!=''){
	        		   if(confirm("确定删除吗？")){
                           
	        			   for (var int = 0; int < dele.length; int++) {
							   $("#"+dele[int]).remove();

						    }

	        			   $.ajax({
						       url : '/chromosome/one_key_delete',
							   type : 'post',
							   data : {'jumpCondition':'AnalyDelete','ids' :dele,'parameter':"DELETE_CountGreyBaseMap"},
							   traditional: true,
							   success : function(js) {	
								   
								    var analy;
					                var count;
					                //没有核对的就按照全部显示
					                if(js.userInfo.analyCheck==0){
					                    analy=js.userInfo.analyStatusSum;
					                }else {
					                    analy=js.userInfo.analyCheck;
					                }
					                
					                if(js.userInfo.countCheck==0){
					                    count=js.userInfo.countStatusSum;
					                }else { 
					                    count=js.userInfo.countCheck;
					                }
					                $("#fileCardAnalyze").html(analy);
					                $("#fileCardCount").html(count);
					                
								}
								});
	        			   dele = [];
			        	}else{
			        		 for (var int = 0; int < dele.length; int++) {
								   $("#"+dele[int]).find("img").css("border","none");
							    }
			        		dele = [];
			        	}
		           }

	        }
	    });

    
    
    
    //搜索
    $(document).on("click", "#search", function(){
    	dele = [];
    	$(".file_card").css("display","none");
    	var inc = $(".inName").val();
        var check = $(".incheck").val();
        var contor = $("#control_date2").val();
        var endDate = $("#control_date4").val();

        
        loadEvent({"inName":inc,"date":contor,"check":check,"endDate":endDate,"isSearch":true});
    });


    laydate.render({
        elem: '#control_date2'
    });

    laydate.render({
        elem: '#control_date4'
    });


    $("#index_right_date").mousedown(function(){
    	$(this).attr("src","../../../images/shijian.png");
    	$(this).mouseup(function(){
    		$(this).attr("src","../../../images/shijian1.png");
    	})
    });
    
    
  
    
  
    
    
    
    /* ====================================================*/
    
	   var ss = "";
	   var aa = "";
	   aa =  $(".innerbox7 ul:first span").parent().text();
	   $(document).on("mousedown",".innerbox7 ul",function(){
		   aa = $(this).children('span').text()+',';
		   
	   })
	   $(document).on("mousedown","#clide",function(){
		   $(".file_card").css("display","none");
		   ss = $(this).text()+',';   
		   //$(this).parent().siblings().text()+','+
	   });
	   
	   $("#jishu").click(function(e){
	    	jishu(aa);
	   });
	    /*function jishu(dsum,xsum,tiaoshu){
	    	var sum = 'C333787,3~A,46';
	    	$("#index_right_r").children('div').remove()
	    	$.ajax({
				url : '/chromosome/chromatid/paramSet',
				type : 'post',
				data : JSON.stringify({
					dsum :dsum,
					xsum :xsum,
					tiaoshu :tiaoshu
				}),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				success : function(js) {
					console.log(js);
					var htm ='';
					$(js.CountQuery).each(function (index, e) {
						htm += "<div><img src="+urls+e.grayUrl+"></div>"
						
					})
					
					$("#index_right_r").html(htm)
					
					
				}
			});
	    }*/
	   
	   
	   
		 // 打开文件卡
		$("#index_right_date").click(function() {
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
					var caryogram = $("#fileCardGrayCray").text();
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
	   
	   
	   
//		$(document).on("click","#ascertain",function() {
//					var evenNumber = document.getElementById('even_number').value;
//					var medicalRecord = document.getElementById('medical_record').value;
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
//                $(".file_card").css("display","none")
//				});   
	
	$(".active1").click(function(){
 
	   	$.ajax({
			url : '/chromosome/chromatid/paramSet',
			type : 'post',
			data : JSON.stringify({
				"dsum" :aa,
				"xsum" :ss,
				"tiaoshu" :''
			}),
			contentType : "application/json;charset=utf-8",
			
			success : function(js) {
			
				window.location.href='/chromosome/page/jsp/caryogram/operation/carycount.jsp';
				
			}
		});	
		
	});
    
	
	//悬浮是显示title
	$(".titlespan").hover(function(){
		$(this).attr("title",$(this).text());
	});
	
	$(".index_right_span").hover(function(){
		$(this).attr("title",$("#fileCardGrayCray").text());
	});
    
})