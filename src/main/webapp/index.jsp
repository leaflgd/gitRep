<%@ page language="java" contentType="text/html; charset=Utf-8" %>
<html>
<body>
	<input type="button" class="submit" value="提交"/>
	<input type="button" class="submit1" value="提交1"/>
	<input type="button" class="submit2" value="提交2"/>
	<input type="button" class="submit3" value="提交3"/>
	<input type="button" class="submit4" value="提交4"/>
	<input type="button" class="submit5" value="提交5"/>
	<input type="button" class="submit6" value="提交6"/>
	<input type="button" class="submit7" value="提交7"/>
</body>
<script type="text/javascript" src="page/js/plug/jquery.min.js"></script> 
<script>
$(function(){   
	$(".submit").click(function() {
		$.ajax({
			url:'/chromosome/boot/test',
			type:'post',
			data:JSON.stringify(),
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log("成功");
		 		console.log(js);
		 	}
		});
	});
	$(".submit1").click(function() {
		$.ajax({
			url:'/chromosome/boot/picPathStorage',
			type:'post',
			data:JSON.stringify({'greyId' : '42375',
				'chromid' :['336203'],
				'status' : 1,
				'logName':'liujie',
				'chrom':[
				         {
				        	'picPath' : '699690',
							'adjustPicPath' : '699690',
							'enhancePicPath' : '699690',
							'Arith_Offset' : '120,150',
							'OffsetPicPath' : 'asdasdasd'
				         }
					]
				}), 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log("成功");
		 		console.log(js);
		 	}
		});
	});
	
	$(".submit2").click(function() {
		$.ajax({
			url:'/chromosome/boot/chromHiLiteOutlineQuery?chromid=699690',
			type:'post',
			data:JSON.stringify(), 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log("成功");
		 		console.log(js);
		 	}
		});
	});
	
	$(".submit3").click(function() {
		$.ajax({
			url:'/chromosome/boot/hiLiteOutlineStorage',
			type:'post',
			data:JSON.stringify({'hiLiteOutlin' : 'C335510/2~A/proc/C335510.2~A.6/grypoc/ahigh_C335510.2~A.6_881_25.jpg',
				'chromid' : '699690',
			}), 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log("成功");
		 		console.log(js);
		 	}
		});
	});
	
	$(".submit4").click(function() {
		$.ajax({
			url:'/chromosome/boot/chromConnectQuery',
			type:'post',
			data:JSON.stringify(['330115','330116','330117','330118']), 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log("成功");
		 		console.log(js);
		 	}
		});
	});
	
	$(".submit5").click(function() {
		$.ajax({
			url:'/chromosome/boot/chromGreyStorage',
			type:'post',
			data:JSON.stringify({
				'greyid':'33923',
				'picPath':'20180905195031.png',
				'status':0
			}), 
			//'C327019/B~A/proc/C327019.B~A.8/grypoc/littlebaby.jpg'
			contentType:"application/json;charset=utf-8",
			dataType:"json",
		 	success:function(js){
		 		console.log("成功");
		 		console.log(js);
		 	}
		});
	});
})

</script>
</html>