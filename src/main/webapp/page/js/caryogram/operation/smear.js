var workerSmear;
var arContext;
var arBmContext;
var drawOpList = [];
var arcXPath = [];
var arcYPath = [];
var rDefault = 24;
var canvasBm;
var isDisplayArc = false;
var isDrawing = false;
// 操作类型
var typeMap = {
	'line': 0,
	'smear':1,
	'cross':2,
	'join':3
}

// 操作状态
var statusMap = {
	'init':0,
	'draw':1
}
function initWork(){
	var path = $("#contextPath").val();
	workerSmear = new Worker(path+'/page/js/caryogram/operation/color_cycle_worker.js');
	workerSmear.postMessage({'cmd': 'start'}); // Start the worker.

	workerSmear.addEventListener('message', function(e) {
	  switch(e.data.cmd) {
		  case 'onRuntimeInitilized':
			onRuntimeInitialized();
			break;
	    case 'drawProcessedFrame':
			drawProcessedFrame(
					e.data.img_data,
					e.data.returnPointList,
//					e.data.inputX,
//					e.data.inputY,
					e.data.returnAttr);
			break;
	    case 'uploadImage':
	    	uploadImage(
	    			e.data.img_data,
	    			e.data.returnImageList,
	    			e.data.returnAttr);
			break;
	    case 'drawImage':
			  drawImage(e.data.dstData,e.data.id);
			break;
	    case 'drawFillImage':
	    	drawFillImage(e.data.dstData,e.data.id,e.data.ctxId);
			break;
			
				
	  }
	}, false);
}
//openDatabase();
function onRuntimeInitialized() {
	console.log("初始化成功");
}
var dbName = 'wasm';
var dbVersion = 1;
var storeName = 'wasm-cache';
function openDatabase() {
    return new Promise((resolve, reject) => {
      var request = indexedDB.open(dbName, dbVersion);
      request.onerror = reject.bind(null, 'Error opening wasm cache database');
      request.onsuccess = () => { resolve(request.result) };
      request.onupgradeneeded = event => {
        var db = request.result;
        if (db.objectStoreNames.contains(storeName)) {
            console.log(`Clearing out version ${event.oldVersion} wasm cache`);
            db.deleteObjectStore(storeName);
        }
        console.log(`Creating version ${event.newVersion} wasm cache`);
        db.createObjectStore(storeName)
      };
    });
  }

function drawProcessedFrame(img_data,returnPointList,returnAttr){
	drawSmearContext.clearRect(0, 0, arContext.canvas.width,arContext.canvas.height);
	var type = returnAttr.type;
	if(!_.isEmpty(returnPointList)){
		var r = returnAttr.r;
		var length = returnAttr.length;
		var id = returnAttr.id;
		// 保存撤销步骤
	    setOpStep(id,type,returnPointList,[],[],r,statusMap.draw,length);
	}
	if(_.isEmpty(drawOpList)){
		return;
	}
//	var message = [];
//	_.forEach(arcXPath,function(item,index){
//		message.push(arcXPath[index]+","+arcYPath[index]+",");
//	});
	drawLineForReturn(drawSmearContext,returnPointList,returnAttr.length);
//	saveLineTodb(message,type,r,function(error,response){
//		console.log("算法插入记录");
//	})
    clear();
}


function _arrayToHeap(typedArray) {
	var numBytes = typedArray.length * typedArray.BYTES_PER_ELEMENT;
	var ptr = Module._malloc(numBytes);
	heapBytes = Module.HEAPU8.subarray(ptr, ptr + numBytes);
	heapBytes.set(typedArray);
	return heapBytes;
}
function uploadImage(img_data, returnImageList, returnAttr) {
    console.log("uploadImage")
    console.log(img_data, returnImageList, returnAttr);
    var base64List = [];
    for (var i = 0; i < returnAttr.length; i++) {
        var image = returnImageList["image_" + i];
        var width = returnImageList["width_" + i];
        var height = returnImageList["height_" + i];
        var canvasOffscreen = drawImageBase64(i,width,height);
        var otx = canvasOffscreen.getContext("2d");
        var imageData = otx.getImageData(0, 0, width, height);
        imageData.data.set(image);
        otx.putImageData(imageData, 0, 0);
        var dataURL = canvasOffscreen.toDataURL();
        if(dataURL){
        	console.log(dataURL);
        	$("#drawImageBase64_"+ i).remove();
        	base64List.push(dataURL);
        }
    }
    
    uploadImageToDb(currentImage.chromId,base64List);
    
    function drawImageBase64(id,width,height){
		var canvasOffscreen = document.createElement('canvas');
		canvasOffscreen.id = "drawImageBase64_"+id;
		$(canvasOffscreen).attr("width",width);
		$(canvasOffscreen).attr("height",height);
		return canvasOffscreen;
    }

    function imageToBlob(src, cb) {
        imageToCanvas(src, function (canvas) {
            cb(dataURLToBlob(canvasToDataURL(canvas)));
        });
    }

    function canvasToDataURL(canvas, format, quality) {
        return canvas.toDataURL(format || 'image/jpeg', quality || 1.0);
    }

    function dataURLToBlob(dataurl) {
        var arr = dataurl.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        var bstr = atob(arr[1]);
        var n = bstr.length;
        var u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {
            type: mime
        });
    }

    function imageToCanvas(src, cb) {
        var canvas = document.createElement('CANVAS');
        var ctx = canvas.getContext('2d');
        var img = new Image();
        img.src = src;
        img.onload = function () {
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0);
            cb(canvas);
        };
    }

    function blobToImage(blob, cb) {
        fileOrBlobToDataURL(blob, function (dataurl) {
            var img = new Image();
            img.src = dataurl;
            cb(img);
        });
    }


    function fileOrBlobToDataURL(obj, cb) {
        var a = new FileReader();
        a.readAsDataURL(obj);
        a.onload = function (e) {
            cb(e.target.result);
        };
    }


}

function uploadImageToDb(chromId,base64List){
	if(!chromId || _.isEmpty(base64List)){
		return;
	}
	$.ajax({
		url:'/chromosome/chDivision/divisionImgStore',
		type:'post',
		data:JSON.stringify({'base':base64List,'chromid':chromId}), 
		contentType:"application/json;charset=utf-8",
		dataType:"json",
	 	success:function(js){
	 		if(js) console.log("成功");
	 	}
	});
}


function smearImage(id,img_date,width, height, r,pointListx, pointListy) {
	var attr = {
		id:id,
		type: 1,
		canvasWidth: width,
		canvasHeight: height,
		r:r// 半径
	}
	setOpStep(id,typeMap.smear,[],pointListx,pointListy,r,statusMap.init);
	var pointList = [];
	_.each(drawOpList,function(item){
		pointList.push({
			xList:item.inputX,
			yList: item.inputY,
			r:item.r,
			type:item.type
		});
	});
	
	workerSmear.postMessage({'cmd': 'smearImage',  'img_data': img_date, 'attr':attr, 'pointList': pointList});
	return 0;
};

function smearLine(id,img_date,width, height, r,pointListx, pointListy){
	var attr = {
		id:id,
		canvasWidth: width,
		canvasHeight: height,
		r:r// 半径
	}
	setOpStep(id,typeMap.line,[],pointListx,pointListy,r,statusMap.init);
	var pointList = [];
	_.each(drawOpList,function(item){
		pointList.push({
			xList:item.inputX,
			yList: item.inputY,
			r: item.r,
			type: item.type
		});
	});
	workerSmear.postMessage({'cmd': 'smearLine',  'img_data': img_date, 'attr':attr, 'pointList': pointList});
	
	return 0;
}

function fastCrossOrJoin(img_date,width, height,type,x,y){
	var idName = (type == 2 ? 'cross_' : 'join_');
	var id = idName + guid();
	var r = 0;
	var attr = {
		id:id,
		type:type,
		canvasWidth: width,
		canvasHeight: height
	}
	setOpStep(id,type,[],[x],[y],r,statusMap.init);
	var pointList = [];
	_.each(drawOpList,function(item){
		pointList.push({
			xList:item.inputX,
			yList: item.inputY,
			r: item.r,
			type: item.type
		});
	});
	workerSmear.postMessage({'cmd': 'fastCrossOrJoin',  'img_data': img_date, 'attr':attr, 'pointList': pointList});
	
	return 0;
}


function saveLineTodb(message,status,r,callback){
	var messages = {
			'message' : message,
			'status' : parseInt(status),
			'r':r
		};
		$.ajax({
			url : '/chromosome/chDivision/chCoordinateStore',
			type : 'post',
			data : JSON.stringify(messages),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(reponse) {
				callback(null,reponse);
			}
		});
}

function clipImage(img_date,width, height, r){
	var attr = {
		canvasWidth: width,
		canvasHeight: height,
		r:r// 半径
	}
		
	var pointList = [];
	_.each(drawOpList,function(item){
		pointList.push({
			xList:item.inputX,
			yList: item.inputY,
			r: item.r,
			type: item.type
		});
	});
	workerSmear.postMessage({'cmd': 'clipImage',  'img_data': img_date, 'attr':attr, 'pointList': pointList});
	return 0;
}
initWork();

function drawImage(enhanData,id){
//	console.log("enhanData",enhanData);
//	console.log("id",id);
	var enhanceCanvas = document.getElementById("enhanceOutput");
	enhanceCanvas.width = enhanData.width;
	enhanceCanvas.height = enhanData.height;
    var enhanCxt = enhanceCanvas.getContext("2d");
    enhanCxt.clearRect(0,0,enhanData.width,enhanData.height);
    enhanCxt.putImageData(enhanData, 0, 0, 0, 0, enhanData.width, enhanData.height);
    
    enhanceCanvas.toBlob(function(blob) {
	  //var newImg = document.createElement('img'),
	      var url = URL.createObjectURL(blob);
//	  newImg.id = id;;
//	  newImg.onload = function() {
//	    // no longer need to read the blob so it's revoked
//	    URL.revokeObjectURL(url);
//	  };
//
//	  newImg.src = url;
	  nuclearAnalysisApp.showEnImage(id,url);
//	  $("#"+id).parent().append(newImg);
//	  $("#"+id).remove();
	  //document.body.appendChild(newImg);
  });
}

function drawFillImage(enhanData,id,ctxId){
	var enhanceCanvas = document.getElementById(ctxId);
	enhanceCanvas.width = enhanData.width;
	enhanceCanvas.height = enhanData.height;
    var enhanCxt = enhanceCanvas.getContext("2d");
    enhanCxt.clearRect(0,0,enhanData.width,enhanData.height);
    enhanCxt.putImageData(enhanData, 0, 0, 0, 0, enhanData.width, enhanData.height);
    
}

function isStartArc(status,ac,currentOp){
	if(currentOp){
		if(currentOp.line){
			status = 0;
		}else if(currentOp.arc){
			status = 1;
		}else if(currentOp.ctrl){
			ac = 1;
		}
	}
	var op = {
		line: false,
		arc: false,
		ctrl: false
	}
	if(ac == 1){
		// 取消涂抹功能
//		$("#arCanvas").removeClass("open");
//		$("#drawSmear").removeClass("b");
		circleNone("none");
		// 取消画线
		op.ctrl = true;
		op.line = false;
		op.arc = false;
		return op;
	}
	else if(status == 1){
		$("#arCanvas").addClass("open");
		$("#drawSmear").addClass("b");
		//circleNone("block");
		op.ctrl = false;
		op.line = false;
		op.arc = true;
		return op;
	}else if(status == 0){
		$("#drawSmear").addClass("b");
		$("#arCanvas").addClass("open");
		circleNone("none");
		op.line = true;
		op.ctrl = false;
		op.arc = false;
		return op;
	}
}

$(function() {
	// 红色的
	var canvas = getArc('arCanvas');
    arContext = canvas.getContext('2d');
    arContext.translate(0.5, 0.5)
    
    // 灰色的
    canvasBm = getArc('bmCanvas');
    arBmContext = canvasBm.getContext('2d');
    arBmContext.translate(0.5, 0.5);
	$("#arCanvas").mousedown(startDrawing);
	$("#arCanvas").mousemove(draw);
	$("#arCanvas").mouseup(stopDrawing);
    
	var startMouse = {};
    function startDrawing(event) {
    	var acrOp = isStartArc(status,ac);
    	circleNone("none");
    	// 交叉 或者  粘连
    	if(acrOp.ctrl && (event.button==0 || event.button==2)){
    		var type = event.button==0 ? typeMap.cross : typeMap.join;
    		var mouse = getMousePos(event,true);
    		var r = 0;
    		var message = [];
    		message.push(mouse.x + "," + mouse.y + ",");
    		message.push(r + ",");
    		saveLineTodb(message,type,r,function(error,response){
        		fastCrossOrJoin(currentImage.imageData,currentImage.width, currentImage.height,type,mouse.x,mouse.y);
    		})
    	}
    	else if(status == 0 && event.button==0){
    		isDrawing = true;
    		drawLine(event);
    	}else if (status == 0 && event.button==2){
    		frImage(event);
    	}
    	else if(status == 1){
        	if(ac==1 || (acrOp.arc && event.button==2)){
        		frImage(event);
        		return;
        	}
        	// 记录鼠标位置
        	var mouse = getMousePos(event);
            startMouse = mouse;
            // 显示涂抹圆圈
            isDrawing = !isDrawing;
            var cssName = isDrawing ? "block" : "none" ;
        	circleNone(cssName);
        	// 第二次涂抹显示轮廓
        	isDisplayArc = !isDisplayArc;
            if(!isDisplayArc){
            	console.log("startDrawing......................");
            	stopArcDrawing();
            }
            
    	}
    }
    function draw(event) {
    	if (!isDrawing) {
    		return;
        }
		var acrOp = isStartArc(status,ac);

    	if(acrOp.line){
    		var mouse = getMousePos(event);
    		arContext.lineWidth = 1;
    		//arContext.moveTo((mouse.x + 0.5), (mouse.y + 0.5));
    		arContext.lineTo(trans5(mouse.x), trans5(mouse.y));
    		arContext.strokeStyle = 'red';
            arContext.stroke();
            //arContext.closePath();
            arcXPath.push(mouse.x);
            arcYPath.push(mouse.y);
    	}else if(acrOp.arc){
    		var mouse = getMousePos(event);
    		circleNone("none");
    		
    		// 红圈
            arContext.clearRect(0, 0, canvas.width,canvas.height);
            arContext.save();
            arContext.beginPath();
            arContext.strokeStyle = "rgba(255,0,0,1)";
            arContext.arc(trans5(mouse.x), trans5(mouse.y), rDefault, 0, 2 * Math.PI, false);
            arContext.lineWidth = 1;
            arContext.stroke();
            arContext.closePath();
            arContext.restore();
            
            // 灰圈
            arBmContext.save();
            arBmContext.beginPath();
            arBmContext.arc(trans5(mouse.x), trans5(mouse.y), rDefault, 0, 2 * Math.PI,false);
            arBmContext.lineWidth = 1;
            arBmContext.fillStyle = 'rgba(105,105,105,0.05)';
            arBmContext.fill();
            arBmContext.closePath();
            arBmContext.restore();
            
            // 修改透明值
            var iamgeData = arBmContext.getImageData(trans5(mouse.x)-rDefault,trans5(mouse.y)-rDefault, rDefault*2,rDefault*2);
            
            var dataByte = iamgeData.data;
            for(var i=3; i< dataByte.length; i=i+4){
            	if(dataByte[i] != 0){
            		dataByte[i] = 80;
            	}
            }
            
            arBmContext.putImageData(iamgeData,trans5(mouse.x)-rDefault,trans5(mouse.y)-rDefault);
            
            // 存储鼠标坐标
            arcXPath.push(mouse.x);
            arcYPath.push(mouse.y);
    	}
    }
    function stopDrawing(event) {
    	var acrOp = isStartArc(status,ac);
    	if(acrOp.arc){
    		return;
    	}
    	isDrawing = false;
    	if(_.isEmpty(arcXPath) || _.isEmpty(arcYPath)){
    		return;
    	}
    	if(event.button==2){
    		return;
    	}
    	
    	saveAndDrawCanvas();
    }
    
    function stopArcDrawing() {
    	isDrawing = false;
    	isDisplayArc = false;
    	if(_.isEmpty(arcXPath) || _.isEmpty(arcYPath)){
    		return;
    	}
    	if(event.button==2){
    		return;
    	}
    	saveAndDrawCanvas();
    }
    
    function saveAndDrawCanvas(){
    	// 判断输入的点是否90%范围内相同
    	var message = [];
    	var r = status == 1 ? rDefault : 0;
     	_.forEach(arcXPath,function(item,index){
    		message.push(arcXPath[index]+","+arcYPath[index]+",");
    	});
     	if(!_.isEmpty(message)){
     		message.push(r+",");
     	}
     	//console.log("message............................",JSON.stringify(message));
    	saveLineTodb(message,status,r,function(error,response){
        	if(status == 1){
        		var id = "arc" + guid();
        		smearImage(id,currentImage.imageData,currentImage.width, currentImage.height, r,arcXPath, arcYPath);
        	}else if(status == 0){
        		var id = "line" + guid();
        		smearLine(id,currentImage.imageData,currentImage.width, currentImage.height, r,arcXPath, arcYPath);
        	}
        	//clear();
        	//circleNone("none");
    	})
    }
    
    function mouseListen(){
//    	$("#arCanvas").on("mouseleave",function(event){
//    		stopDrawing(event);
//    		//console.log("mouseleave......................");
//    		stopArcDrawing();
//    		circleNone("none");
//    	});
    	
    	$("#arCanvas").on("mousemove",function(event){
    		var acrOp = isStartArc(status,ac);
    		if(!acrOp.arc){
    			return;
    		}
    		var mouse = getMousePos(event,true);
    		$("#chuli1 .circle").css({
				  "top":40 + mouse.y - rDefault,
				  "left":mouse.x - rDefault
				  })
    	})
    	
//    	$("#arCanvas").on("mouseover",function(event){
//    		var acrOp = isStartArc(status,ac);
//    		if(!acrOp.arc){
//    			return;
//    		}
//    		//circleNone("block");
//    	})
    	
    	$("#arCanvas").on("mouseout",function(event){
    		circleNone("none");
    	})
    	
    	$('#arCanvas').mousewheel(function(event, delta) {
    		// !mouseDistancePos(startMouse,mouse,5)
    		var mouse = getMousePos(event,true);
    		if(isDrawing && mouseDistancePos(startMouse,mouse,5)){
    		 return;
    		}
            var dir = delta > 0 ? 'Up' : 'Down';
     
            if (dir == 'Up' && rDefault < 40) {
            	rDefault = rDefault + 2;
            	
            } else if(rDefault > 24){
            	rDefault = rDefault - 2;
            }
            var mouse = getMousePos(event,true);
            $("#chuli1 .circle").css({
				  "top":40 + mouse.y - rDefault,
				  "left":mouse.x - rDefault,
				  "width": rDefault * 2,
				  "height": rDefault * 2
				  })
            return false;

        });
    }
    
    mouseListen();
	function getArc(name){
        var arc = document.getElementById(name);
        return arc;
    }
	function drawLine(event){
//		arContext.strokeStyle="red";
//		arContext.lineWidth=1.5;
        //var mouse = getMousePos(event);
        arContext.beginPath();
	}
	function drawArc(){
		arContext.globalCompositeOperation = "xor";
		var mouse = getMousePos(event);
        // 红圈
		arContext.save();
		arContext.beginPath();
		arContext.strokeStyle = "rgba(255,0,0,1)";
        arContext.arc(trans5(mouse.x), trans5(mouse.y), rDefault, 0, 2 * Math.PI,false);
        arContext.lineWidth = 1;
        arContext.closePath();
        arContext.stroke();
        
        // 灰圈
        arBmContext.save();
        arBmContext.beginPath();
        arBmContext.fillStyle = 'rgba(105,105,105,0.05)';
        arBmContext.arc(trans5(mouse.x), trans5(mouse.y), rDefault, 0, 2 * Math.PI,false);
        arBmContext.lineWidth = 1;
        arBmContext.fill();
        arBmContext.closePath();
        arBmContext.restore();
	}
	function getMousePos(evt,isFastOp) {
        if (isDrawing || isFastOp) {
            var rect = canvas.getBoundingClientRect();
            return {
                x: (evt.clientX - rect.left),
                y: (evt.clientY - rect.top)
            };
        }
    }
	
	// 鼠标移动的距离
	function mouseDistancePos(startPos,endPos,num){
		if(!startPos || !endPos){
			return;
		}
		
		if((endPos.x - startPos.x) < num && (endPos.y - startPos.y) < num){
			return false;
		}
		return true;
	}
	
	function frImage(e){
		if(e.button==2){
			$(".zhezhao").css("display","none");
			$.ajax({
				url : nodeUrl + '/chromImage/generateCutImage',
				type : 'get',
				data : {
					chromId:currentImage.chromId,
					greyId:$("#grayId").val(),
					loginName:$("#loginName").val()
				},
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				success : function(res) {
					if(res.code!='0'){
//						alert("分割成功");
						alert("分割异常");
					}
//					for ( var key in res.data) {
//						
//						loader(res.data);
//					}
					nuclearAnalysisApp.addImageList(res.data,currentImage.chromId);
					
					//$("#check_fruit2").val($(".analyse_tab img").length);
				}
			});
			
			$("#chuli1").css("visibility","hidden");
			clickStatus = false;
			if(drawSmearContext != null){
				drawSmearContext.clearRect(0, 0, 800, 800);
				//drawSmearContext = null;
			}
//			message = new Array();
			//console.log("开始分割");
			//clipImage(currentImage.imageData,canvas.width, canvas.height,0);
		}
	}
	
	//小图页面加载器
	function loader(js) {
		var ht = "";
		var k;
		var index = 0;
		//获取图片的个数
		for ( var i in js) {
			index++;
		}
		var j=0;
		if(index>40){
			j=(index/5)*6;
			index=0;
		}else{
			j=48;
			index=0;
		}
		for (var h = 0; h < j; h++) {
			k = h % 6;
			if (k == 0) {
				ht += "<tr>"
			} else {
				if (js[index] != undefined) {
					ht += "<td>" + "<img id='"
							+ js[index].chromId
							+ "' src='" + urls
							+ js[index].chromEnhance
							+ "'>" + "</td>";
					index++;
				} else {
					ht += "<td></td>";
				}
			}
			if (k == 5) {
				ht = ht + "</tr>"
			}
		}
		if (k != 5) {
			ht = ht + "</tr>"
		}
		$(".analyse_tab").html("");
		$(".analyse_tab").html(ht);
	
	}
	
})

function clear(){
	arContext.clearRect(0, 0, arContext.canvas.width,arContext.canvas.height);
	arBmContext.clearRect(0, 0, arContext.canvas.width,arContext.canvas.height);
	arcXPath = [];
	arcYPath = [];
}

function setOpStep(id,type,returnPointList,inputX,inputY,r,status,length){

	var stepItem = _.find(drawOpList, function(o) {
		return o.id == id; 
	  }
	);
	if(stepItem){
		stepItem.status = status;
		stepItem.returnPointList = returnPointList;
		stepItem.length = length;
	}else{
		var step = new Object({
			id:id,
			type: type,
			returnPointList:returnPointList,
			inputX:inputX,
			inputY:inputY,
			r:r,
			status:status// 状态：0开始状态，1:操作成功
		})
		
		drawOpList.push(step);
	}
	
	
}
function drawLineForReturn(drawSmearContext,returnPointList,length){
	drawSmearContext.lineWidth = 1;
	var xxList = [];
	var yyList = [];
    for(var i=0;i<length;i++){
    	var pLength = returnPointList["length_"+i];
    	var xp_x = returnPointList["x_"+i];
		var xp_y = returnPointList["y_"+i];
//			console.log("xp_x",JSON.stringify(xp_x));
//			console.log("xp_y",JSON.stringify(xp_y));
		drawSmearContext.beginPath();
		drawSmearContext.save();
		drawSmearContext.strokeStyle = getRandomColor(i);
		drawSmearContext.moveTo(trans5(xp_x[0]), trans5(xp_y[0]));
    	for(var j=0; j< pLength; j++){
        	drawSmearContext.lineTo(trans5(xp_x[j+1]), trans5(xp_y[j+1]))
        	xxList.push(trans5(xp_x[j+1]));
        	yyList.push(trans5(xp_y[j+1]));
    	}
    	//console.log("xp_x",JSON.stringify(xxList));
		//console.log("xp_y",JSON.stringify(yyList));
    	drawSmearContext.closePath();
        drawSmearContext.stroke();
    	drawSmearContext.restore();
    	
    }
}

function circleNone(cssValue){
	var isDelFlag = cssValue == 'none' ? true : false;
	if(isDelFlag){
		$("#chuli1 .circle").removeClass("open");
	}else{
		$("#chuli1 .circle").addClass("open");
	}
}

function S4() {
	return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
	return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}

var getRandomColor = function(index){
	var colorList = ['rgba(255,20,147,1)',
	                 'rgba(0,0,255,1)',
	                 'rgba(0,139,139,1)',
	                 'rgba(0,128,0,1)',
	                 'rgba(255,69,0,1)',
	                 'rgba(30,144,255,1)',
	                 'rgba(138,43,226,1)',
	                 'rgba(165,42,42,1)'];
	if(colorList[index]){
		return colorList[index]
	}else{
		return randomHexColor();
	}
}

function randomHexColor() { //随机生成十六进制颜色
	return '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).substr(-6);
}

function trans5(v){
	return v + 0.5;
}

