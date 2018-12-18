if( 'function' === typeof importScripts) {
//	var Module = {
//		TOTAL_MEMORY : 268435456
//	}
   // This loads the wasm generated glue code
	importScripts('opencvJs.js');
	
	let frame_bytes;
   
	addEventListener('message', onMessage);

	function onMessage(e) { 
		switch(e.data.cmd){
			case "smearImage":
				smearImage(e.data.img_data,e.data.attr,e.data.pointList);
				break;
			case "smearLine":
				smearLine(e.data.img_data,e.data.attr,e.data.pointList);
				break;
			case "clipImage":
				clipImage(e.data.img_data,e.data.attr,e.data.pointList);
				break;
			case "fastCrossOrJoin":
				fastCrossOrJoin(e.data.img_data,e.data.attr,e.data.pointList);
				break;
			case "enhanceImage":
				enhanceImage(e.data.imgData,e.data.dstData,e.data.attr);
				break;
			case "fillImage":
				fillImage(e.data.imgData,e.data.dstData,e.data.attr);
				break;
				
		}
	}   
	
	// Overrides for the generated emcc script, module gets redifined later
	Module.onRuntimeInitialized = function() {
		postMessage({'cmd': 'onRuntimeInitilized'});
	}; 
	
	function _freeArray(heapBytes) {
		try{
			Module._free(heapBytes.byteOffset);
		}
		catch(err){
			console.log(err);
		}
	}

	function _arrayToHeap(typedArray) {
		var numBytes = typedArray.length * typedArray.BYTES_PER_ELEMENT;
		var ptr = Module._malloc(numBytes);
		heapBytes = Module.HEAPU8.subarray(ptr, ptr + numBytes);
		heapBytes.set(typedArray);
		return heapBytes;
	}

	function _arrayToHeap1(typedArray) {
		var numBytes = typedArray.length;
		var ptr = Module._malloc(numBytes);
		heapBytes = Module.HEAPU8.subarray(ptr, ptr + numBytes);
		heapBytes.set(typedArray);
		return heapBytes;
	}

	function _createHeap(arrayLen){
		var ptr = Module._malloc(arrayLen);
		Module._memset(ptr,0,arrayLen);
		heapBytes = Module.HEAPU8.subarray(ptr, ptr + arrayLen);
		//heapBytes.set(typedArray);
		return heapBytes;
	}

	function _createHeap16(typedArray, arrayLen){
		var ptr = Module._malloc(arrayLen);
		heapBytes = Module.HEAPU16.subarray(ptr, ptr + arrayLen);
		heapBytes.set(typedArray);
		return heapBytes;
	}

	function _createHeap32New(typedArray, arrayLen){
		var numBytes = arrayLen*4;
		var ptr = Module._malloc(numBytes);
		Module._memset(ptr, 0, numBytes);
		heapBytes = Module.HEAPU8.subarray(ptr, ptr + numBytes);
		//heapBytes.set(typedArray);
		var j = 0;
		for(i=0; i < arrayLen; i++)
		{
			highBytes = Math.floor(typedArray[i]/65536);
			lowBytes = typedArray[i]%65536;
			
			heapBytes[j] = lowBytes%256;
			heapBytes[j+1] = lowBytes/256;
			heapBytes[j+2] = highBytes%256;
			heapBytes[j+3] = highBytes/256;
			j = j + 4;
		}
		return heapBytes;
	}

	function _createHeap16New(typedArray, arrayLen){
		var numBytes = arrayLen*2;
		var ptr = Module._malloc(numBytes);
		Module._memset(ptr, 0, numBytes);
		heapBytes = Module.HEAPU8.subarray(ptr, ptr + numBytes);
		//heapBytes.set(typedArray);
		var j = 0;
		for(i=0; i < arrayLen; i++)
		{
			heapBytes[j] = typedArray[i]%256;
			heapBytes[j+1] = typedArray[i]/256;

			j = j + 2;
		}
		return heapBytes;
	}

	function convertToJS16(typedArray, arrayLen)
	{
		var cvtArray = new Array();
		for(i=0;i<arrayLen*2;i+=2){
			cvtArray.push(typedArray[i+1]*256
						+ typedArray[i]);
		}

		return cvtArray;
	}
	
	function convertToJS32(typedArray, arrayLen)
	{
		var cvtArray = new Array();
		for(i=0;i<arrayLen*4;i+=4){
			cvtArray.push(typedArray[i+3]*65536*256
						+ typedArray[i+2]*65536
						+ typedArray[i+1]*256
						+ typedArray[i]);
		}

		return cvtArray;
	}
	
	function smearImage(img_data,attr,pointList){
		var width = attr.canvasWidth;
		var height = attr.canvasHeight;
		var r = attr.r || 0;
		var type = attr.type || 1;
		var id = attr.id;
		
		var returnObj = draw(img_data, width, height, pointList,type,r);
		var finalTotal = returnObj.length;
		var stepLength = returnObj.stepLength;
		var returnPointList = {};
		
		var returnPointList = {};
		for(var i=0;i<finalTotal; i++){
			returnPointList["x_"+i] = returnObj.x[i];
			returnPointList["y_"+i] = returnObj.y[i];
			returnPointList["length_"+i] = returnObj.x[i].length;
			
//			console.log("ix"+i,JSON.stringify(returnObj.x[i]));
//			console.log("iy"+i,JSON.stringify(returnObj.y[i]));
		}
		
		
//		_freeArray(frame_bytes);
//		_freeArray(listx);
//		_freeArray(listy);
//		_freeArray(returnX);
//		_freeArray(returnY);
//		_freeArray(returnLength);
		
		var returnAttr = {
			type:type,
			r:r,
			id:id,
			length:finalTotal
		}
		
		postMessage({'cmd': 'drawProcessedFrame', 
			'img_data': img_data,
			'returnPointList':returnPointList, 
//			'inputX':xList,
//			'inputY':yList,
			'returnAttr':returnAttr
			});
		

	}
	
	function smearLine(img_data,attr,pointList){
		var width = attr.canvasWidth;
		var height = attr.canvasHeight;
		var r = attr.r || 0;
		var type = attr.type || 0;
		var id = attr.id;
		console.time();
		var returnObj = draw(img_data, width, height, pointList,type,r);
		console.timeEnd();
		var finalTotal = returnObj.length;
		var stepLength = returnObj.stepLength;
		var returnPointList = {};
//		console.log("mytest, finalTotal: " + finalTotal);
//		console.log("mytest, stepLength: " + stepLength);
		
		for(var i=0;i<finalTotal; i++){
			returnPointList["x_"+i] = returnObj.x[i];
			returnPointList["y_"+i] = returnObj.y[i];
			returnPointList["length_"+i] = stepLength[i];
			
//			console.log("ix"+i,JSON.stringify(returnObj.x[i]));
//			console.log("iy"+i,JSON.stringify(returnObj.y[i]));
		}
		var returnAttr = {
			type:type,
			r:r,
			id:id,
			length:finalTotal
		}
		postMessage({'cmd': 'drawProcessedFrame', 
			'img_data': img_data,
			'returnPointList':returnPointList, 
//			'inputX':xList,
//			'inputY':yList,
			'returnAttr':returnAttr
			});
	}
	
	function fastCrossOrJoin(img_data,attr,pointList){
		smearLine(img_data,attr,pointList);
	}
	
	function enhanceImage(oridata,dstData,attr){
		
	    var img = {
	    		width: oridata.width,
	    		height:oridata.height
	    }
	    
	    let colorStepList = attr.colorStepList;
	    let optTypeArray = []; // 类型
	    let kArray = []; // 值
	    let stepLength = colorStepList.length; // 步骤
	    
	    colorStepList.forEach(function(item,index){
	    	optTypeArray.push(item.type);
	    	kArray.push(item.value);
	    });
	    var inputTypeArray = _createHeap32New(optTypeArray, optTypeArray.length);
		var inputKarray = _createHeap32New(kArray, kArray.length);
		


	    
//	    let imgMat = cv.imread(img);

		//begin to handle
	    var frame_bytes = _arrayToHeap(oridata.data);
	    var frame_bytes_dst = _arrayToHeap(dstData.data);

	    Module._enhanceImageBySteps(img.width, 
	    		img.height, 
	                    frame_bytes.byteOffset, 
	                    frame_bytes_dst.byteOffset, 
	                    inputTypeArray.byteOffset,
	                    inputKarray.byteOffset,
	                    stepLength
	                    );
	    dstData.data.set(frame_bytes_dst);
	    postMessage({'cmd': 'drawImage', 
			'dstData': dstData,
			'id': attr.id
			});
	    
	    
	    //dstCxt.putImageData(dstData, 0, 0, 0, 0, img.width, img.height);
	}
	
	function fillImage(oridata,dstData,attr){
		let colorStepList = attr.colorStepList;
		if(!colorStepList){
			return;
		}
		var img = {
	    		width: oridata.width,
	    		height:oridata.height
	    }
	    
		let ctxId = attr.ctxId;
	    let optTypeArray = []; // 类型
	    let kArray = []; // 值
	    let stepLength = colorStepList.length; // 步骤
	    
	    colorStepList.forEach(function(item,index){
	    	optTypeArray.push(item.type);
	    	kArray.push(item.value);
	    });
	    var inputTypeArray = _createHeap32New(optTypeArray, optTypeArray.length);
		var inputKarray = _createHeap32New(kArray, kArray.length);
		


	    
//	    let imgMat = cv.imread(img);

		//begin to handle
	    var frame_bytes = _arrayToHeap(oridata.data);
	    var frame_bytes_dst = _arrayToHeap(dstData.data);

	    Module._enhanceImageBySteps(img.width, 
	    		img.height, 
	                    frame_bytes.byteOffset, 
	                    frame_bytes_dst.byteOffset, 
	                    inputTypeArray.byteOffset,
	                    inputKarray.byteOffset,
	                    stepLength
	                    );
	    dstData.data.set(frame_bytes_dst);
	    postMessage({'cmd': 'drawFillImage', 
			'dstData': dstData,
			'id': attr.id,
			'ctxId': ctxId
			});
		
	}
	
	function clipImage(img_data,attr,pointList){
		var width = attr.canvasWidth;
		var height = attr.canvasHeight;
		var r = attr.r;
		var type = attr.type;
		
		var returnObj = get_images(img_data, width, height, pointList,type,r);
		var length = returnObj.length;
		var returnImageList = [];
		//console.log("mytest, length: " + length);
		
		for(var i=0;i<length; i++){
			returnImageList["image_"+i] = returnObj.imageList[i];
			returnImageList["width_"+i] = returnObj.widthList[i];
			returnImageList["height_"+i] = returnObj.heightList[i];

//			console.log("ix_image"+i,JSON.stringify(returnObj.imageList[i]));
//			console.log("iy_image"+i,JSON.stringify(returnObj.widthList[i]));
//			console.log("iy_image"+i,JSON.stringify(returnObj.heightList[i]));
			
		}
		var returnAttr = {
			type:type,
			r:r,
			length:length
		}
		
		postMessage({'cmd': 'uploadImage', 
			'img_data': img_data,
			'returnImageList':returnImageList,
			'returnAttr':returnAttr
			});
	}
	function draw(img_data, width, height, stepsInfo,type,r){
		var frame_bytes = _arrayToHeap(img_data.data);
		//array for addr
		var maxInputSteps = 35;
		var maxOutputImages = 35;
		var inputXAddrListArray = new Array();
		var inputYAddrListArray = new Array();

		var maxImageRows = 180;
		var maxImageCols = 180;
		var returnObj = {};
		
		var returnXAddrPool = _createHeap(maxImageRows * maxImageCols * maxOutputImages);
		var returnYAddrPool = _createHeap(maxImageRows * maxImageCols * maxOutputImages);

		var returnLengthArray = new Array();
		var returnLength = _createHeap(4);

		var flagsListArray = new Array();
		var inputRadiusListArray = new Array();
		
		var inputLenListArray = new Array();

		for(i=0; i < stepsInfo.length; i++){
			var item = stepsInfo[i];
			var pointListx = item.xList;
			var pointListy = item.yList;
//			console.log("inputX:",JSON.stringify(pointListx));
//			console.log("inputX:",JSON.stringify(pointListy));
//			console.log("type::",item.type);
//			console.log("r::",item.r);
			//console.log(pointListx,pointListy);
			var listx = _createHeap32New(pointListx, pointListx.length);
			var listy = _createHeap32New(pointListy, pointListy.length);
			
			
			inputLenListArray.push(pointListx.length);

			//if more than one, use for
			inputXAddrListArray.push(listx.byteOffset);
			inputYAddrListArray.push(listy.byteOffset);
			flagsListArray.push(item.type);
			inputRadiusListArray.push(item.r);
		}
		var flagsList = _createHeap32New(flagsListArray, maxInputSteps);
		var inputLenList = _createHeap32New(inputLenListArray, maxInputSteps);

		var inputXAddrList = _createHeap32New(inputXAddrListArray, maxInputSteps);
		var inputYAddrList = _createHeap32New(inputYAddrListArray, maxInputSteps);
		var inputRadiusList = _createHeap32New(inputRadiusListArray, maxInputSteps)

		var outputXAddrListArray = new Array();
		var outputYAddrListArray = new Array();

		for (var i = 0; i < maxOutputImages; i++){
			var returnTempX = returnXAddrPool.byteOffset + 
								maxImageRows * maxImageCols * i;
			
			var returnTempY = returnYAddrPool.byteOffset + 
								maxImageRows * maxImageCols * i;					
			outputXAddrListArray.push(returnTempX);
			outputYAddrListArray.push(returnTempY);
		}

		var outputXAddrList = _createHeap32New(outputXAddrListArray, maxOutputImages * 4);
		var outputYAddrList = _createHeap32New(outputYAddrListArray, maxOutputImages * 4);

		var steps = stepsInfo.length;

		stepsOutputLenList = _createHeap(maxOutputImages * 4);

		var result = Module._draw_line(width, height, frame_bytes.byteOffset,
			inputXAddrList.byteOffset,
			inputYAddrList.byteOffset,
			inputLenList.byteOffset,
			inputRadiusList.byteOffset,
			steps,
			outputXAddrList.byteOffset,
			outputYAddrList.byteOffset,
			returnLength.byteOffset,
			flagsList.byteOffset,
			stepsOutputLenList.byteOffset);
		//console.log("stepsOutputLenList: " + stepsOutputLenList);
		//console.log("returnLength: " + returnLength);
		
		var lineCount = returnLength[0];
		//console.log("outputXAddrList:  " + outputXAddrList);
		var returnImagesListX = [];
		var returnImagesListY = [];
		
		stepsOutputLenListArray = convertToJS32(stepsOutputLenList, returnLength[0]);

		for (var i=0; i < maxOutputImages; i++){
			if (stepsOutputLenListArray[i] && stepsOutputLenListArray[i] != 0){
				var ptrx = returnXAddrPool.byteOffset + (maxImageRows * maxImageCols * i);
				var ptry = returnYAddrPool.byteOffset + (maxImageRows * maxImageCols * i);

				var tempListX = Module.HEAPU8.subarray(ptrx, ptrx + stepsOutputLenListArray[i] * 4);
				var tempListY = Module.HEAPU8.subarray(ptry, ptry + stepsOutputLenListArray[i] * 4);;
//				var tempX = convertToJS32(tempListX
//						,stepsOutputLenListArray[i]);
				//console.log("tempX" + tempX.length);
				//console.log("before last: " + tempX[tempX.length-4] + "last:  " + tempX[tempX.length-3]);
				returnImagesListX.push(convertToJS16(tempListX
														,stepsOutputLenListArray[i]));
				returnImagesListY.push(convertToJS16(tempListY
														,stepsOutputLenListArray[i]));
			}
		}
		
		_freeArray(returnXAddrPool);
		_freeArray(returnYAddrPool);
		_freeArray(frame_bytes);
		_freeArray(outputXAddrList);
		_freeArray(outputYAddrList);
		_freeArray(returnLength);
		_freeArray(flagsList);
		_freeArray(inputLenList);
		_freeArray(stepsOutputLenList);

		for(i=0;i<inputXAddrList.length;i+=4)
		{
			var xAddr = inputXAddrList[i + 3] * 65536 * 256
			+ inputXAddrList[i + 2] * 65536
			+ inputXAddrList[i + 1] * 256
			+ inputXAddrList[i];
			var yAddr = inputYAddrList[i + 3] * 65536 * 256 
			+ inputYAddrList[i + 2] * 65536
			+ inputYAddrList[i + 1] * 256
			+ inputYAddrList[i];
			Module._free(xAddr);
			Module._free(yAddr);
		}

		_freeArray(inputXAddrList);
		_freeArray(inputYAddrList);
		
		return {x:returnImagesListX,y:returnImagesListY,length:lineCount,stepLength:stepsOutputLenListArray}
	}
	
	function convert1To4(width, height, matInfo){
		var imageArray = new Array();
		var indexNewImage = 0;
		for(j=0;j<height;j++){
			for(k=0;k<width;k++){
				imageArray[indexNewImage] = matInfo[j*width + k];
				imageArray[indexNewImage + 1] = matInfo[j*width + k];
				imageArray[indexNewImage + 2] = matInfo[j*width + k];
				imageArray[indexNewImage + 3] = 255;
				indexNewImage = indexNewImage + 4;
			}
		}
		
		var imageArrayList = _arrayToHeap1(imageArray);
		return imageArrayList;
	}
	
	function get_images(img_data, width, height, stepsInfo,type,r){
		var frame_bytes = _arrayToHeap(img_data.data);
		//array for addr
		var maxInputSteps = 10;
		var maxOutputImages = 20;
		var inputXAddrListArray = new Array();
		var inputYAddrListArray = new Array();

		var maxImageRows = 800;
		var maxImageCols = 800;

		var returnMat1 = _createHeap(maxImageRows * maxImageCols);
		var returnMat2 = _createHeap(maxImageRows * maxImageCols);
		var returnMat3 = _createHeap(maxImageRows * maxImageCols);
		var returnMat4 = _createHeap(maxImageRows * maxImageCols);
		var returnMat5 = _createHeap(maxImageRows * maxImageCols);
		var returnMat6 = _createHeap(maxImageRows * maxImageCols);

		var returnLengthArray = new Array();
		var returnLength = _createHeap(4);

		var flagsListArray = new Array();

		var inputRadiusListArray = new Array();
		
		var inputLenListArray = new Array();

		for(i=0; i < stepsInfo.length; i++){
			var item = stepsInfo[i];
			var pointListx = item.xList;
			var pointListy = item.yList;

			var listx = _createHeap32New(pointListx, pointListx.length);
			var listy = _createHeap32New(pointListy, pointListy.length);
			inputLenListArray.push(pointListx.length);

			//if more than one, use for
			inputXAddrListArray.push(listx.byteOffset);
			inputYAddrListArray.push(listy.byteOffset);
			flagsListArray.push(0);
			inputRadiusListArray.push(0);
		}
		var flagsList = _createHeap32New(flagsListArray, maxInputSteps);
		var inputXAddrList = _createHeap32New(inputXAddrListArray, maxInputSteps);
		var inputYAddrList = _createHeap32New(inputYAddrListArray, maxInputSteps);
		var inputRadiusList = _createHeap32New(inputRadiusListArray, maxInputSteps);
		var inputLenList = _createHeap32New(inputLenListArray, maxInputSteps);
		
		var outputAddrListArray = new Array();

		outputAddrListArray.push(returnMat1.byteOffset);
		outputAddrListArray.push(returnMat2.byteOffset);
		outputAddrListArray.push(returnMat3.byteOffset);
		outputAddrListArray.push(returnMat4.byteOffset);
		outputAddrListArray.push(returnMat5.byteOffset);
		outputAddrListArray.push(returnMat6.byteOffset);

		var outputAddrList = _createHeap32New(outputAddrListArray, maxOutputImages * 4);

		var steps = stepsInfo.length;

		stepsOutputLenList = _createHeap(maxOutputImages * 4);

		var widthList = _createHeap(maxOutputImages * 4);
		var heightList = _createHeap(maxOutputImages * 4);
		
		var result = Module._get_images(width, height, frame_bytes.byteOffset,
			inputXAddrList.byteOffset,
			inputYAddrList.byteOffset,
			inputLenList.byteOffset,
			inputRadiusList.byteOffset,
			steps,
			outputAddrList.byteOffset,
			returnLength.byteOffset,
			flagsList.byteOffset,
			widthList.byteOffset,
			heightList.byteOffset);

		console.log("returnLength: " + returnLength[0]);
		var lineCount = returnLength[0];
		var widthListArray = convertToJS32(widthList, returnLength[0]);
		var heightListArray = convertToJS32(heightList, returnLength[0]);
		
		var returnImagesList = [];

		if (widthListArray[0] && widthListArray[0] != 0){
			var imageArrayList = convert1To4(widthListArray[0], heightListArray[0], returnMat1);
			returnImagesList.push(imageArrayList);
		}
		if (widthListArray[1] && widthListArray[1] != 0){
			var imageArrayList = convert1To4(widthListArray[1], heightListArray[1], returnMat2);
			returnImagesList.push(imageArrayList);
		}
		if (widthListArray[2] && widthListArray[2] != 0){
			var imageArrayList = convert1To4(widthListArray[2], heightListArray[2], returnMat3);
			returnImagesList.push(imageArrayList);
		}
		if (widthListArray[3] && widthListArray[3] != 0){
			var imageArrayList = convert1To4(widthListArray[3], heightListArray[3], returnMat4);
			returnImagesList.push(imageArrayList);
		}
		if (widthListArray[4] && widthListArray[4] != 0){
			var imageArrayList = convert1To4(widthListArray[4], heightListArray[4], returnMat5);
			returnImagesList.push(imageArrayList);
		}
		if (widthListArray[5] && widthListArray[5] != 0){
			var imageArrayList = convert1To4(widthListArray[5], heightListArray[5], returnMat6);
			returnImagesList.push(imageArrayList);
		}
		
		_freeArray(frame_bytes);
		_freeArray(returnMat1);
		_freeArray(returnMat2);
		_freeArray(returnMat3);
		_freeArray(returnMat4);
		_freeArray(returnMat5);
		_freeArray(returnMat6);

		_freeArray(outputAddrListArray);
		_freeArray(returnLength);
		_freeArray(flagsList);
		_freeArray(widthList);
		_freeArray(heightList);

		for(i=0;i<inputXAddrList.length;i++)
		{
			_freeArray(inputXAddrList[i]);
			_freeArray(inputYAddrList[i]);
		}

		_freeArray(inputXAddrList);
		_freeArray(inputYAddrList);

		return {imageList:returnImagesList,widthList:widthListArray,heightList:heightListArray,length:lineCount};
	}
}





