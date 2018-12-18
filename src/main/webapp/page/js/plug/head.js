$(function(){
	//查询-----------------------------------------------------------------
	$("#inquiry_inquiry").click(function(){ 
		window.location.href="/chromosome/page/jsp/caryogram/report/inquiry.jsp";
	});
	
	//核型分析--------------------------------------------------------------
	$("#model_analyse1").click(function(){ 
		window.location.href="/chromosome/page/jsp/caryogram/operation/caryindex_new.jsp";
	});
	
	$("#date_date").click(function(){
		window.location.href="/chromosome/page/jsp/caryogram/date/date.jsp";
	});
});