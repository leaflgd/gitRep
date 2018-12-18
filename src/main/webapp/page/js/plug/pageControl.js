$(function(){
	function pageControl(state){
		if(state>0){
			var logname="<%=session.getAttribute('loginName')%>"; 
			alert(logname);
			if(logname==null||logname==""){
				alert("未登录");
			}else{
				alert("已登录");
			}
		}
	}
});