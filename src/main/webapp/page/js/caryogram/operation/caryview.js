$(function(){
//	var urls="/chromosome/page/images/patientdata";
	
	//var urls="http://192.168.100.156:18005/images/image/patient/";   //156
//	var urls="http://192.168.80.64:18005/images/image/patient/";   //64
	//核型一览返回按钮
	$("#retrun2").click(function(){
      window.history.go(-1);
	});
	$("#model_analyse1").addClass("active");
	$("#li_analyse_show_a").addClass("active");
	window.onload =function(){ 
		$.ajax({
			url:'/chromosome/chAnalysis/caryviewQuery',
			type:'post',
			dataType:"json",
		 	success:function(js){
		 		for ( var j in js) {
		 			var hh=""
		 			hh+="<img src='"+urls+j+"'/>";
		 			$(".analyse_Right1_box").html(hh);
					var data = js[j];
					console.log(data);
//					var six = (Math.ceil(data.length/6))*7;
//					alert(six);
//					console.log(data);
//		 			for (var i = 0; i < data.length; i++) {
					var k;
	 				var index = 0;
	 				var row=0;
	 				for (var h= 0; h < 21; h++) {
	 					k = h%7;
	 					if (k==0) {
	 						row++;
	 						$(".analyse_Left2_tab ").append("<div class='outflex'><div class='innerflex' id='row"+row+"'></div></div>");
	 					} else{
	 						if(data[index]!=undefined){
	 							var element=document.getElementById(""+data[index].greyId);
	 							 if (typeof(element)== "undefined" || element == null){
//	 								 	alert("存在");
	 								 	$("#row"+row).append("<div class='inneritem'><div id='"+data[index].greyId+"' class='ac'><div class='imgcontainer'><img class='checked' id='"+data[index].chromId+"' src='"+urls+data[index].chromUrl+"'></div>" +
		 			    	    				   "<p>"+ data[index].slideName+","+data[index].grayNumber+"</p>"+
		 			    	    			   		"</div></div> ");
	 							    }else{
//	 							    	alert("不存在");
	 							    	$("#"+data[index].greyId).prepend("<div class='imgcontainer'><img id='"+data[index].chromId+"' class='checked' src='"+urls+data[index].chromUrl+"'></div>");
	 							    	h--;
	 							    }
	 							 
	 							$("#"+data[index].chromId).css("transform" ,data[index].chScacss+" "+data[index].chRotcss);
	 						   $("#"+data[index].chromId).css("transform","rotate("+data[index].chCircumX+")");
    	    			   }else{
    	    				   $("#row"+row).append("<div class='inneritem'><div>" +
    	    				   "<p>"+"?"+"</p>"+
    	    			   		"</div></div> ");
    	    			   }
    	    			   index++;
    	    			   
    	    		   }
    	    		   /*if(k==6){
    	    			   $(".analyse_Left2_tab tbody").append("</tr>");
    	    		   }*/
    	    	   }
    	    	  /* if(k!=6){
    	    		   $(".analyse_Left2_tab tbody").append("</tr>");
        		   }*/
	 			}
		 		var imgnum = document.querySelectorAll('.checked').length;
		 		var objnum = document.querySelectorAll('.checked');
		 		console.log(imgnum)
		 		$('.checked').load(function(){
		 			if(!--imgnum){
			 			for( var i=1; i<=objnum.length; i++){
			 				objnum[i-1].parentNode.style.width = objnum[i-1].getBoundingClientRect().width+'px';
			 				$('.checked').eq(i-1).css({'top':$(objnum).eq(i-1).parent().height()/2-objnum[i-1].getBoundingClientRect().height/2})
			 			}
			 		}
		 		})
		 		
		 		
		 	}
		 	
		});
//		setTimeout(tz,500);

	};
	
});
//function tz(){
//	for(var i=0;i<=$('.outflex').children('.innerflex').length;i++){
//		   
//		   console.log($('.outflex').children('.innerflex').eq(i).height())
//		   if($('.outflex').children('.innerflex').eq(i).height()>=270){
//			   $('.outflex').children('.innerflex').eq(i).css({'height':$('.outflex').eq(i).height()})
//		   }else{
//			   $('.outflex').children('.innerflex').eq(i).css({'height':'226px'})
//		   }
//    }
//}