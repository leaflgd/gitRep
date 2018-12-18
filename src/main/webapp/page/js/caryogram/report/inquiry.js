$(function(){
	$("#inquiry_inquiry").addClass("active");
$(document).click(function(e){ 
        if(!$(e.target).is($('#__calendarPanel')) && !$(e.target).is($(":input[name='control_date2']")) && $(e.target).parent('#__calendarPanel').length === 0){
              $("#__calendarPanel").css("visibility","hidden");  
        }
 }); 
var inName;

window.onload =function(){ 
	$.ajax({
		url:'/chromosome/chQuery/chQueryAll', 
        type:'post',
        data:{},
        dataType:"json",
        success:function(data){
        	var ht="";
        	for ( var a in data) {
        		if(data[a].reportDate == null){
        			data[a].reportDate="";
        		}
        		if(data[a].collectDate == null){
        			data[a].collectDate="";
        		}
        		if(data[a].inName == null){
                    data[a].inName="";
                }
                if(data[a].patientName == null){
                    data[a].patientName="";
                }
                if(data[a].chromCary == null){
                    data[a].chromCary="";
                }
                if(data[a].viewAndPrinter == null){
                    data[a].viewAndPrinter="";
                }
        		inName = data[a].inName;
                if(data[a].patientSex==null){
                    data[a].patientSex="";
                }else if(data[a].patientSex=='1'){
                    data[a].patientSex="女";
				}else {
                    data[a].patientSex="男";

				}

                if(data[a].patientSex == null){
                    data[a].patientSex="";
                }
                if(data[a].patientAge == null){
                    data[a].patientAge="";
                }
                if(data[a].clinicalDiagnosis == null){
                    data[a].clinicalDiagnosis="";
                }
                if(data[a].auditor == null){
                    data[a].auditor="";
                }
                if(data[a].xpr == null){
                    data[a].xpr="";
                }
                if(data[a].caseNumber == null){
                    data[a].caseNumber="";
                }
//                console.log("a:"+data[a].viewAndPrinter);
        		ht = ht +"<tr><td>"+data[a].collectDate+"</td>" +
        		"<td id='"+data[a].inName+"'>"+data[a].inName+"</td>" +
        		"<td>"+data[a].caseNumber+"</td>"+
        		"<td>"+data[a].patientName+"</td>" +
        		"<td>"+data[a].patientSex+"</td>"+ 
        		"<td>"+data[a].patientAge+"</td>"+
        		"<td title="+data[a].clinicalDiagnosis+"><span>"+data[a].clinicalDiagnosis+"<span></td>" +
        		"<td title="+data[a].chromCary+"><span>"+data[a].chromCary+"<span></td>" +
        		"<td title="+data[a].viewAndPrinter+">"+data[a].viewAndPrinter+"</td>" +
        		"<td title="+data[a].auditor+">"+data[a].auditor+"</td>" +
        		"<td title="+data[a].xpr+">"+data[a].xpr+"</td>" +
        		"<td>"+data[a].reportDate+"</td></tr>"
        	}
        	$(".inenerbox0").html(ht);
  
        	$(".inenerbox tr:even").css("background","#7b7b7b");
        }
	});
	
	$.ajax({
		url:'/chromosome/chQuery/chQueryAllCount', 
        type:'post',
        data:{},
        dataType:"json",
        success:function(data){
        	var ht="";
        	for(var i = 0 ; i < data.length ; i++){
        		if(data[i].viewAndPrinter != null && data[i].viewAndPrinter != ""){
	        		ht = ht +"<tr><td>"+data[i].viewAndPrinter+"</td>" +
	        		"<td>"+data[i].viewAndPrinterCount+"</td></tr>"
        		}
        	}
        	$(".inenerboxs").html(ht);
        	$(".statistics_tab tr:even").css("background","#424040");
        }
	});
};

$(document).keydown(function(e){
	if(e.keyCode==13){
		$("#search2").click();
	}
});

$(document).on("click","#search2",function(){
	var time1 = document.getElementById("control_date1").value;
	var time2 = document.getElementById("control_date2").value;
	var mydate = new Date();
		if(time2 !=""){
			if(time1>mydate || time2<time1 ){
				alert("选择的时间段不正确");
			}
		}
		var selectType = document.getElementById("selectid").value;
		var price = document.getElementById("price").value;
		$.ajax({
			url:'/chromosome/chQuery/chQueryAll', 
	        type:'post',
	        data:{time1:time1,time2:time2,selectType:selectType,price:price},
	        dataType:"json",
	        success:function(data){
	        	var ht="";
	        	for ( var a in data) {
	        		if(data[a].reportDate == null){
	        			data[a].reportDate="";
	        		}
	        		if(data[a].collectDate == null){
	        			data[a].collectDate="";
	        		}
	        		if(data[a].inName == null){
	                    data[a].inName="";
	                }
	                if(data[a].patientName == null){
	                    data[a].patientName="";
	                }
	                if(data[a].chromCary == null){
	                    data[a].chromCary="";
	                }
	                if(data[a].viewAndPrinter == null){
	                    data[a].viewAndPrinter="";
	                }
	        		inName = data[a].inName;
	                if(data[a].patientSex==null){
	                    data[a].patientSex="";
	                }else if(data[a].patientSex=='1'){
	                    data[a].patientSex="女";
					}else {
	                    data[a].patientSex="男";

					}

	                if(data[a].patientSex == null){
	                    data[a].patientSex="";
	                }
	                if(data[a].patientAge == null){
	                    data[a].patientAge="";
	                }
	                if(data[a].clinicalDiagnosis == null){
	                    data[a].clinicalDiagnosis="";
	                }
	                if(data[a].auditor == null){
	                    data[a].auditor="";
	                }
	                if(data[a].xpr == null){
	                    data[a].xpr="";
	                }
	                if(data[a].caseNumber == null){
	                    data[a].caseNumber="";
	                }
	        		ht = ht +"<tr><td>"+data[a].collectDate+"</td>" +
	        		"<td id='"+data[a].inName+"'>"+data[a].inName+"</td>" +
	        		"<td>"+data[a].caseNumber+"</td>"+
	        		"<td>"+data[a].patientName+"</td>" +
	        		"<td>"+data[a].patientSex+"</td>" 
	        		+"<td>"+data[a].patientAge+"</td>" 
	        		+"<td><span>"+data[a].clinicalDiagnosis+"<span></td>" +
	        		"<td title="+data[a].chromCary+"><span>"+data[a].chromCary+"</span></td>" +
	        		"<td>"+data[a].viewAndPrinter+"</td>" +
	        		"<td>"+data[a].auditor+"</td>" +
	        		"<td>"+data[a].xpr+"</td>" +
	        		"<td>"+data[a].reportDate+"</td></tr>"
	        	}
	        	$(".inenerbox0").html(ht);
	  
	        	$(".inenerbox tr:even").css("background","#7b7b7b");
	        }
		});
	
});

var strFullPath=window.document.location.href;  
var strPath=window.document.location.pathname;  
var pos=strFullPath.indexOf(strPath);  
var prePath=strFullPath.substring(0,pos);  
var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);  
var basePath = prePath;  
basePath = prePath + postPath; 

$(".event_tabulation_tab").on("click","tr",function(){
	/*var file = $(this).find("td").eq(1).attr("id");
	window.location = basePath+"/pdf/"+file+".pdf";*/
	var file = $(this).find("td").eq(1).attr("id");
	   var curWwwPath=window.document.location.href;
	   //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	   var pathName=window.document.location.pathname;
	   var pos=curWwwPath.indexOf(pathName);
	   //获取主机地址，如： http://localhost:8083
	   var localhostPaht=curWwwPath.substring(0,pos);
	window.location = localhostPaht+"/pdf/items/viewer.html?file="+file+".pdf";
});


$(document).on("click","#search4",function(){
	var time3 = document.getElementById("control_date3").value;
	var time4 = document.getElementById("control_date4").value;
	var mydate = new Date();
	if(time4!=""){
		if(time3>mydate || time4<time3){
			alert("选择的时间段不正确");
		}
	}
			$.ajax({
			url:'/chromosome/chQuery/chQueryAllCount', 
	        type:'post',
	        data:{time3:time3,time4:time4},
	        dataType:"json",
	        success:function(data){
	        	var ht="";
	        	for(var i = 0 ; i < data.length ; i++){
	        		if(data[i].viewAndPrinter != null  && data[i].viewAndPrinter != ""){
		        		ht = ht +"<tr><td>"+data[i].viewAndPrinter+"</td>" +
		        		"<td>"+data[i].viewAndPrinterCount+"</td></tr>"
	        		}
	        	}
	        	$(".inenerboxs").html(ht);
	        	$(".statistics_tab tr:even").css("background","#424040");
	        }
		});
	
});	


$(document).on("click","#search3",function(){
	var time1 = document.getElementById("control_date1").value;
	var time2 = document.getElementById("control_date2").value;
	var selectType = document.getElementById("selectid").value;
	var price = document.getElementById("price").value;
	$.ajax({
		url:'/chromosome/chQuery/chQueryExport', 
        type:'post',
        data:{time1:time1,time2:time2,selectType:selectType,price:price},
        dataType:"json",
        success:function(data){
        	var str;
        	for ( var i in data) {
				str = data[i];
			}
        	window.location = "/chromosome/excel/"+str;
        }
	});
});


laydate.render({
	  elem: '#control_date1'
	});
laydate.render({
	 elem: '#control_date2'
		}); 
laydate.render({
	 elem: '#control_date3'
		}); 
laydate.render({
	 elem: '#control_date4'
		}); 


});




