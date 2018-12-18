var workerEnhance;
function initWork(){
	var path = $("#contextPath").val();
	workerEnhance = new Worker(path+'/page/js/caryogram/operation/enhance_worker.js');
	workerEnhance.postMessage({'cmd': 'start'}); // Start the worker.

	workerEnhance.addEventListener('message', function(e) {
	  switch(e.data.cmd) {
		  case 'onRuntimeInitilized':
			onRuntimeInitialized();
			break;
		  case 'drawImage':
			  drawImage(e.data.dstData,e.data.id);
			break;
			
	  }
	}, false);
}
//openDatabase();
function onRuntimeInitialized() {
	console.log("核型页面opencv初始化成功");
}

//initWork();

function roateImage(src2,roateImageWidth,roateImageHeight,angle){
		let dst = new cv.Mat();
		let src = cv.imread(src2);
        //define the max zone first
        var maxSize;

        if (roateImageHeight > roateImageWidth) {
            maxSize = roateImageHeight;
        }
        else {
            maxSize = roateImageWidth;
        }
        
        let heightNew = maxSize;
        let widthNew = maxSize;

        cv.cvtColor(src, src, cv.COLOR_RGBA2GRAY);
        //define the max zone first

        //do shift
        let dst_shift = new cv.Mat();
        let dsize_shift = new cv.Size(widthNew, heightNew);
        let M_shift = cv.matFromArray(2, 3, cv.CV_64FC1, [1, 0, (widthNew - src.cols) / 2, 0, 1, (heightNew - src.rows) / 2]);

        cv.warpAffine(src, dst_shift, M_shift, dsize_shift, cv.INTER_LINEAR, cv.BORDER_CONSTANT, new cv.Scalar(255));
        //do shift

        //do rotate
        let dsize = new cv.Size(widthNew, heightNew);
        let center = new cv.Point(widthNew / 2, heightNew / 2);
        let M = cv.getRotationMatrix2D(center, -angle, 1);

        cv.warpAffine(dst_shift, dst, M, dsize, cv.INTER_LINEAR, cv.BORDER_CONSTANT, new cv.Scalar(255));
        

        //find rect
        //console.log(dst.channels());
        //cv.imshow('dstcanvasOutput', dst);
        let bMat = new cv.Mat();
        cv.threshold(dst, bMat, 254, 255, cv.THRESH_BINARY_INV);
        let contours = new cv.MatVector();
        let hierarchy = new cv.Mat();
        cv.findContours(bMat, contours, hierarchy, cv.RETR_EXTERNAL, 
            cv.CHAIN_APPROX_NONE);
        let rect = cv.boundingRect(contours.get(0));
        
//        console.log(rect.x, rect.y, rect.height, rect.width);
//        console.log(dst.rows, dst.cols);

        let ROI = dst.roi(rect);
        let finalMat = ROI.clone();
		// let rectangleColor = new cv.Scalar(255, 0, 0);
		// let point1 = new cv.Point(10, 10);
		// let point2 = new cv.Point(finalMat.cols-10, finalMat.rows-10);
		// cv.rectangle(finalMat, point1, point2, rectangleColor);
        //console.log(finalMat.rows, finalMat.cols);
        //cv.imshow('bcanvasOutput', bMat);
        cv.imshow('dstcanvasOutput', finalMat);
        //find rect
        src.delete();
        dst.delete();
        M.delete();
        M_shift.delete();
        bMat.delete();
        finalMat.delete();
        ROI.delete();
}

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
	  var newImg = document.createElement('img'),
	      url = URL.createObjectURL(blob);
	  newImg.id = id;;
	  newImg.onload = function() {
	    // no longer need to read the blob so it's revoked
	    URL.revokeObjectURL(url);
	  };

	  newImg.src = url;
	  
	  $("#"+id).parent().append(newImg);
	  $("#"+id).remove();
	  //document.body.appendChild(newImg);
  });
}

function enhImage(value){
	console.time("enhImage");
	$('.ac div:nth-child(2) img').each(function(index,item) {
		let roateImageWidth = item.getBoundingClientRect().width;
		let roateImageHeight = item.getBoundingClientRect().height;
		var enhanceCanvas = document.getElementById("enhanceOutput");
		var enhanCxt = enhanceCanvas.getContext("2d");
	    enhanCxt.clearRect(0,0,roateImageWidth,roateImageHeight);
	    var enhanData = enhanCxt.getImageData(0, 0, roateImageWidth, roateImageHeight);
	    
	    let inputMap = cv.imread(item);

	    var imageDataCopy = new ImageData(
				  new Uint8ClampedArray(inputMap.data),
				  item.width,
				  item.height
				)
	    var attr = {
	    	value: value,
	    	id:$(item).attr("id")
	    }
		
	    //enhanceImage(finalMat,90);
	    workerEnhance.postMessage({'cmd': 'enhanceImage',  'imgData': imageDataCopy,'dstData':enhanData, 'attr':attr});
	    inputMap.delete();
		
	})
	
	 console.timeEnd("enhImage");
}

function changColor(){
	
	var x = document.getElementById('range').value;
	console.log("x",x);
	enhImage(x);
}