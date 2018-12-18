/**
 * 文件卡js
 * 
 */


$(".file_card select").on("change",function(){
	$(this).next().val($(this).val());
});
$('.file_card select option').mouseover(function(){
	$(this).css({"background":"#1e90ff","color":"#ffffff"});
});
$('.file_card select option').mouseleave(function(){
	$(this).css({"background":"#ffffff","color":"#000000"});
});


$('.file_card select').mousedown(function() {
	console.log($(this).find("option").length)
	$(this).css({
		"z-index":"6",
		"padding":"20px 0"
	})
	if($(this).find("option").length<6){
		$(this).attr('size',$(this).find("option").length)
	}
    if($(this).find("option").length>6){
		$(this).attr('size',6)
	}
});
$('.file_card select').change(function(){
	$(this).css({
		"z-index":"4",
		"padding":"10px"
	});
	$(this).attr("size",0);
})
$('.file_card select').blur(function(){
	$(this).css({
		"z-index":"4",
		"padding":"10px"
	});
	$(this).attr("size",0);
})

$('#suggestion1').mousedown(function() {
	$(this).css({
		"z-index":"6",
		"padding":"38px 0"
	})
	if($(this).find("option").length<6){
		$(this).attr('size',$(this).find("option").length)
	}
    if($(this).find("option").length>6){
		$(this).attr('size',6)
	}
});
$('#suggestion1').change(function(){
	$(this).css({
		"z-index":"4",
		"padding":"29px"
	});
	$(this).attr("size",0);
})
$('#suggestion1').blur(function(){
	$(this).css({
		"z-index":"4",
		"padding":"29px"
	});
	$(this).attr("size",0);
})

$("#suggestion").change(function(){
	$("#opinionStatus").val("1");
});
$("#suggestion1").change(function(){
	$("#opinionStatus").val("1");
});
$("#report_date").change(function(){
	$("#reportDate_status").val("1");
});



// 点击确定按钮关闭文件卡
 $("#ascertain").click(function() {

	//校验文件卡数据是否合格
	var bl= Check();
	if(!bl){return;}
	
	var sss = new FormData(document.getElementById("yourformid"));
	console.log(sss);
	$.ajax({
		url :'/chromosome/chCount/updateFolder',
		type:'post',
        data:sss,
		contentType:false,
		processData:false,
		success : function(data){
			if(!data){
				alert("文件卡提交异常");
			}else{
				$(".analyse_btn button").removeClass('btncolor')
				$(".file_card").css("display", "none");
			}
		}
	});	
});

function  Check(){
	//日期正则
	var date = /^((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))$/;
	//姓名正则
	var name=/^[\u4E00-\u9FA5A-Za-z]+$/;
	//年龄正则
	var age =/^((([0-9]|[1-9]\d|1\d{2}|200)(岁|))|(\d+月)|(\d+(日|天)))$/;
	//组合年龄
	var ages =/^(((([0-9]|[1-9]\d|1\d{2}|200)(岁|)\d+月\d+(日|天)))|((\d+月\d+(日|天))))$/
	//孕龄周正则
	var pregnancyWeek=/^([0-9]|[1-4]\d|50)$/; 
	var pregnancyDay=/^[0-7]$/;
	console.log("名字:"+$('#name').val())
	 
	if(!date.test($('#gather_date').val()) && $('#gather_date').val()!=""){
		alert("请确保采集日期中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return false;
	}
	if(!date.test($('#vaccinate_date').val()) && $('#vaccinate_date').val()!=""){
		alert("请确保接种日期中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return false;
	}
	if(!date.test($('#report_date').val()) && $('#report_date').val()!=""){
		alert("请确保报告日期中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return false;
	}
	if(!name.test($('#name').val()) && $('#name').val()!=""){
		alert("请确保姓名由中文和英文字母组成");
		return false;
	}
	//年龄
	if(!age.test($('#age').val()) && !ages.test($('#age').val()) && $('#age').val()!=""){
		alert("年龄格式不对");
		return false;
	}
	//性别
	if($('#sex').val()!='男' && $('#sex').val()!='女'&& $('#sex').val()!=""){
		alert("性别为男或女");
		return false;
	}
	//孕龄
	if($('#pregnancy').val()=="0"&&$('#pregnancys').val()=="0"){
		alert("孕龄不能为0-0");
		return false;
	}else{
		//周
		if(!pregnancyWeek.test($('#pregnancy').val())&&$('#pregnancy').val()!=""){
			alert("孕周为0~50");
			return false;
		}
		//天
		if(!pregnancyDay.test($('#pregnancys').val())&&$('#pregnancys').val()!=""){
			alert("孕天为0~7");
			return false;
		}
	}
	//核型空值判断
	if($('#caryogram').val().replace(/\s+/g,"")==""){
		alert("核型不能为空");
		return false;
	}
	return true;
};



