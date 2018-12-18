//var fp;
//var worker;
//function newWork(){
//	var path = $("#contextPath").val();
//	worker = new Worker(path+'/page/js/caryogram/operation/color_cycle_worker.js');
//	worker.postMessage({'cmd': 'start'}); // Start the worker.
//
//	worker.addEventListener('message', function(e) {
//	  switch(e.data.cmd) {
//		  case 'onRuntimeInitilized':
//				onRuntimeInitialized();
//				break;
//	    case 'drawProcessedFrame':
//	      console.log(e);
//				drawProcessedFrame(e.data.img_data,e.data.x,e.data.y,e.data.length);
//				break;
//	  }
//	}, false);
//}
//
//
//function onRuntimeInitialized() {
//  console.log("初始化成功");
//}
//
//function drawProcessedFrame(img_data,returnX,returnY,length){
//  console.log("drawProcessedFrame")
//  if(fp != null){
//    // Render to viewport
//    fp.viewport.putImageData(img_data, 0, 0);
//  }
//}
//
//
//function makeFrameProcessor(img_date, width, height, pointListx, pointListy) {
//
//  fp.viewport = fp.canvas.getContext("2d");
//  console.log("makeFrameProcessor: " + pointList);
//  worker.postMessage({'cmd': 'processFrame',  'img_data': img_date, img_width: width, img_height: height, pointListx: pointListx, pointListy: pointListy});
//
//   return 0;
//};
//
//// 记录坐标点
//var message = new Array();
//// 画布参数
//var oGetMousePos;
//var oContextRc;
//$(function(){
//	oGetMousePos = document.getElementById("myCanvas");
//	keyUp();
//	mouseup();
//	mouseDown();
//});
//
//function keyUp(){
//	$(document).keyup(function(event){
//
//		if(event.keyCode==27){
//			$.ajax({
//				url : '/chromosome/chDivision/chCoordinateRepeal',
//				type : 'post',
//				data : JSON.stringify(),
//				contentType : "application/json;charset=utf-8",
//				dataType : "json",
//				success : function(js) {
////					alert("撤销完成");
//					if(js[0]!=null){
//						if(oContextRc != null){
//							oContextRc.clearRect(0, 0, 800, 800);
//							oContextRc = null;
//						}
//						oContextRc = oGetMousePos.getContext("2d");
//						var imgObj = new Image();
//						var srcs =urls + js[0]+"?temp=" + Math.random();
////						alert(srcs);
//						imgObj.src =srcs;
//						if(imgObj.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数  
//							oContextRc.drawImage(imgObj, 0, 0);
//			            } else{
//							imgObj.onload = function() {
//								oContextRc.drawImage(this, 0, 0);// this即是imgObj,保持图片的原始大小：470*480
//							}
//			            }
//						clickStatus = false;
//					}else{
//						//关闭遮罩
//						$(".zhezhao").css("display","none");
//						$("#chuli1").css("display", "none");
//						clickStatus = false;
//						if(oContextRc != null){
//							oContextRc.clearRect(0, 0, 800, 800);
//							oContextRc = null;
//						}
//						message = new Array();
//					}
//				}
//			})
//		}
//	});
//}
//
//function mouseup(){
//	// 离开事件
//	$("#myCanvas").mouseup(function() {
////		if (message.length != 0) {
////			console.log(message + "");
////		}
//		var messages = {
//			'message' : message,
//			'status' : status
//		};
//		$.ajax({
//			url : '/chromosome/chDivision/chCoordinateStore',
//			type : 'post',
//			data : JSON.stringify(messages),
//			contentType : "application/json;charset=utf-8",
//			dataType : "json",
//			success : function(js) {
//				//清空图片
//				if(js[0]!=null){
//					if(oContextRc != null){
//						oContextRc.clearRect(0, 0, 800, 800);
//						oContextRc = null;
//					}
//					
//					oContextRc = oGetMousePos.getContext("2d");
//					var imgObj = new Image();
//					var srcs =urls + js[0]+"?temp=" + Math.random();
//					imgObj.src = srcs;
//					if(imgObj.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数  
//						oContextRc.drawImage(imgObj, 0, 0);
//		            } else{
//						imgObj.onload = function() {
//							oContextRc.drawImage(this, 0, 0);// this即是imgObj,保持图片的原始大小：470*480
//						}
//		            }
//				}
////				alert("123123");
//			}
//		});
//		clickStatus = false;
//		message = new Array();
//		
//	});
//}
//
//function mouseDown(){
//	// 鼠标点击
//	$("#myCanvas").mousedown(function(e) {
//		
//		if(e.button==2){
//		 
//			$(".zhezhao").css("display","none");
//			// 保存canvas
//			$.ajax({
//				url : '/chromosome/chDivision/chDivisionDecide',
//				type : 'post',
//				data : JSON.stringify(),
//				contentType : "application/json;charset=utf-8",
//				dataType : "json",
//				success : function(js) {
//					for ( var key in js) {
//						if(key=='0'){
////							alert("分割成功");
//						}else if(key=='1'){
//							alert("分割失败");
//						}else{
//							alert("分割异常");
//						}
//						loader(js[key]);
//					}
//				}
//			});
//			
//			$("#chuli1").css("display", "none");
//			clickStatus = false;
//			if(oContextRc != null){
//				oContextRc.clearRect(0, 0, 800, 800);
//				oContextRc = null;
//			}
//			message = new Array();
//		}
//		
//		clickStatus=true;
//		oGetMousePos.addEventListener("mousemove", function(evt) {
//			var mousePos = getMousePos(oGetMousePos, evt);
//			if (clickStatus) {
//				oContextRc.fillStyle = "red";
//				oContextRc.fillRect(mousePos.x, mousePos.y, 2, 2);
//				a = mousePos.x + "," + mousePos.y;
//				message.push(a + ":");
//			}
//		}, false);
//	});
//}
//
//// 打印鼠标指针坐标
//function writeMessage(oGetMousePos, message) {
//	if (clickStatus) {
//		oContextRc.clearRect(0, 0, oGetMousePos.width, oGetMousePos.height);
//		oContextRc.font = "20pt Microsoft JhengHei";
//		oContextRc.fillStyle = "tomato";
//		oContextRc.fillText(message, 10, 25);
//	}
//}
//// 获取鼠标指针坐标
//function getMousePos(oContextRc, evt) {
//	if (clickStatus) {
//		var rect = oGetMousePos.getBoundingClientRect();
//		return {
//			x : evt.clientX - rect.left,
//			y : evt.clientY - rect.top
//		};
//	}
//}
//
//
