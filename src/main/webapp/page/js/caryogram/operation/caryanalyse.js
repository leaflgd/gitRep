$(function(){
//	var urls="/chromosome/page/images/patientdata";
	

	
//	var urls="";
	$("#model_analyse1").addClass("active");
	$("#li_analyse_show_a").addClass("active");
	$(document).on("click","#listload",function(){
		window.location.href='/chromosome/page/jsp/caryogram/operation/caryindex.jsp';
	});
	$(document).ready(function(){ grayquery();});
	function grayquery(){
		$.ajax({ 
			url:'/chromosome/chromatid/grayQuerys', 
	        type:'post',
	        data:JSON.stringify("a"),
	        contentType: "application/json;charset=utf-8",
	        dataType:"json",
	        success:function(js){
	        	var ht="";
	        	for ( var a in js) {
	        		ht=ht+"<div class='grayanalyse' id = '"+js[a].grayId+"'>" +
	        		"<img src='"+urls+js[a].grayUrl+"'>" +
	        		"<i id='"+js[a].grayId+"b'><img src = '../../../images/patientdata/ture_03.png'>"+"</i>"+
	        		"<div class='img_date1'>" +
	        		"<span class='imgs4 imgs_identifier'>"+js[a].inName+"</span>" +
	        		"<span class='imgs5 imgs_date'>"+js[a].grayDate+"</span>" +
	        		"<span class='imgs6 imgs_id'>"+js[a].slideName+","+js[a].grayNumber+"</span>" +
	        		"<span class='imgs7 imgs_number'>"+js[a].grayCray+"</span>" +
	        		"</div></div>"
	        		$("#innames").text(js[a].inName);
	        	}
	        	
	        	$("#innerbox3").html(ht);   
	        	
	        	for(var b in js){
	        		/*if(js[b].analyCheck){
	        			console.log($("#"+js[b].grayId+"a"));
		        		$("#"+js[b].grayId+"b").css("display","block");
	        		}*/
	        		if(js[b].analyCheck){
	        			console.log($("#"+js[b].grayId+"a"));
		        		$("#"+js[b].grayId+"b").css("display","block");
	        		}else{
	        			if(js[b].analyCheck == false && js[b].checkTag == 1){
	        				console.log("123");
	        				$("#"+js[b].grayId+"b").find("img").attr("src","../../../images/you.png");
	        				$("#"+js[b].grayId+"b").css({
	        					"display":"block","position":"absolute","right":"-9px"
	        				})
	        			}
	        		}
	        	}
	        	
	        	var imgsL = $(".imgs7")
	        	for(var i=1;i<=imgsL.length;i++){
//	        		console.log($(".imgs7").eq(i-1))
//	        		console.log($(".imgs7").eq(i-1).width())
	        		if($(".imgs7").eq(i-1).width()>300){
	        			$(".imgs7").eq(i-1).css({"width":"302px","overflow":"hidden"});
	        			/*$(".imgs7").eq(i-1).mouseover(function(){
	        				$(this).css({"width":"500px","z-index":"100"})
	        			})
	        			$(".imgs7").eq(i-1).mouseout(function(){
	        				$(this).css({"width":"305px","z-index":"0"})
	        			})*/
	        		}
	        		
	        	}

	        }
		});
	}
	$(document).on("click", ".grayanalyse", function(){
		var id=  $(this).attr("id");
		window.location.href='/chromosome/chromatid/chromRequest?grayId=a'+id;
	});
	
	$("#return2").click(function(){
		window.location.href='/chromosome/page/jsp/caryogram/operation/caryindex.jsp'
	});
	
	//禁止鼠标右键
	document.oncontextmenu = function(){
        event.returnValue = false;
        };

	var dele = [];
    //分析页面
	$(document).on("mousedown", ".grayanalyse", function(e){
		if(e.button==2){
			$(this).css("border","2px solid #00a0e9");
			 var tatues = true;
			for (var int = 0; int < dele.length; int++) {
				 if($(this).attr("id")==dele[int]){
					 $("#"+dele[int]).css("border","none")
					 dele.splice(int,1);
					 tatues = false;
				 }
			}
			
			if(tatues){
				 dele.push($(this).attr("id"));
			}
			
			
	        
		}
	});










		    $(document).keydown(function(e){
		        if(e.keyCode==46){
		        	console.log(dele);
		           if(dele!=''){
		        	   for (var int = 0; int < dele.length; int++) {
						$("#"+dele[int]).remove();
						
					}
		        	   $.ajax({
				       url : '/chromosome/one_key_delete',
					   type : 'post',
					   data : {'ids' : dele,'parameter':"DELETE_CountGreyBaseMap"},
					   traditional: true,
					   success : function(js) {			
						}
						});
		           }
		           
		           $(this).keyup(function(){
					dele = [];
		           })
		        }
		    })

	
//	$(document).on("click", ".grayanalyse", function(){
//		var id=  $(this).attr("id");
//		console.log(id);
//		$.ajax({ 
//			url:'/chromosome/chromatid/chromRequest', 
//	        type:'post',
//	        data:{grayId:"a"+id},
//	        dataType:"text",
//	        success:function(js){
//	        	window.location.href = '/chromosome/page/jsp/'+js+".jsp";
//	        }
//		});
//	});
});
