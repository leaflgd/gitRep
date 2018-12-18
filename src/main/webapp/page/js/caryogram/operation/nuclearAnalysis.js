const TypeColorEnm = {
	gamma:2,
	band:1
}
let isShiftDown = false;
var nuclearAnalysisApp = new Vue({
  el: '#crayexcisionId',
  data: {
    imageList:[],
    countSize:0,
    db:'',
    defaultGamma:50,
    defaultBand:1,
    type:2
  },
  methods: {
	  init: function(){
		var _self =  this;
		$.ajax({
			url : nodeUrl + '/chromImage/init',
			type : 'get',
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : {
				greyId:$("#grayId").val()
			},
			success : function(js) {
					$("#pic1").attr("src",urls+js["label"].grayUrl);
					$("#analyse_Left_img_data").text(js["label"].inName+" "+js["label"].slideName+" "+js["label"].grayNumber)
					$(".disinName_span").append("<img id='disinName_spanimg' src='../../../images/wclick.png'>")
				//loader(js["ch"]);
					_self.imageList = js["ch"];
					_self.imageList.forEach(function(item,index){
						item.isShow = true;
						item.gamma = item.gamma || nuclearAnalysisApp.defaultGamma;
						item.band = item.band || nuclearAnalysisApp.defaultBand;
						item.chromEnhance = urls + item.chromEnhance;
						nuclearAnalysisApp.$set(_self.imageList, index, item);
					});
					
					getdb(function(err,db){
						_self.db = db;
						_self.imageList.forEach(function(item,index){
							let url = item.chromEnhance;
							let id = item.chromId;
							let grayId = $("#grayId").val();
							getImageObj(id,grayId,url,db);
						});
						
					});
					
				//$("#check_fruit2").val($(".analyse_tab img").length);
				_self.countSize = _self.getImageSize();
				_self.grayQuerys(js["label"]);
			}
		});
	  },
	  grayQuerys: function(jsLabel){
		  var u2 = jsLabel.inName;
		  var u3 = jsLabel.grayNumber;
		  var u4 = jsLabel.slideName;
		  $.ajax({ 
				url:'/chromosome/chromatid/grayQuerys2', 
		        type:'post',
		        data:JSON.stringify("c"),
		        contentType: "application/json;charset=utf-8",
		        dataType:"json",
		        success:function(js){
					console.log(u2+"-"+u3+"-"+u4)
					//根据拨片号判断是否勾选分析
		        	var check = 0;
		        	var check1 = u4+','+u3;
		        	
		        	for ( var a in js) {
		        		var check2 = js[a].slideName+','+js[a].grayNumber;
		        		if(check1 == check2){
		        			check = 1;
		        		}
		        	}
		        	
		        	console.log("check:"+check);
		        	if(check==1){
		        		console.log("check56466565");
		        		$("#analyse_Left_img_data").attr("name","aa");
		        		$("#analyse_Left_img_data").css({"color":"#fff","cursor":"pointer"});
		        	}else{
		        		$("#analyse_Left_img_data").css("color","#999999");

		        	}

		        }
			});
	  },
	  copyImageList:function(item){
		  var _self =  this;
		  if(item.chromEnhance.indexOf(urls) == -1){
			  item.chromEnhance = urls + item.chromEnhance;
			}
		  _self.imageList.push(item);
		  _self.countSize = _self.getImageSize();
	  },
	  resetImageList:function(imageList){
		  var _self =  this;
		  _self.imageList = [];
		  imageList.forEach(function(item,index){
				item.isShow = true;
				
				if(item.chromEnhance.indexOf(urls) == -1){
					item.chromEnhance = urls + item.chromEnhance;	
				}
				nuclearAnalysisApp.$set(_self.imageList, index, item);
				
			});
		  _self.countSize = _self.getImageSize();
	  },
	  addImageList:function(imageList,currentChromId){
		  var _self =  this;
		  let currentColorStep = [];
		  _self.imageList.forEach(function(item,index){
				if(item.chromId == currentChromId){
					item.isShow = false;
					currentColorStep = item.colorStepList;
				}
			});
			
		  imageList.forEach(function(item,index){
			  item.chromEnhance = urls + item.chromEnhance;
			  if(item.isSmallImage){
				 item.isShow = true;
				 item.gamma = item.gamma || nuclearAnalysisApp.defaultGamma;
				 item.band = item.band || nuclearAnalysisApp.defaultBand;
				 item.colorStepList = currentColorStep;
				 _self.imageList.push(item);
				}else{
					item.isShow = true;
					item.gamma = item.gamma || nuclearAnalysisApp.defaultGamma;
					item.band = item.band || nuclearAnalysisApp.defaultBand;
					item.colorStepList = currentColorStep;
					_self.imageList.splice(index,0,item);
				}
			});
		  _self.countSize = _self.getImageSize();
		  _self.updateImageTodb(imageList);
		  
	  },
	  getImageSize: function(){
		  var count = 0;
		  this.imageList.forEach(function(item,index){
			  if(item.isShow){
				  count++;
			  }
		  });
		  return count;
	  },
	  showEnImage: function(id,newUrl){
		  var _self =  this;
		  _self.imageList.forEach(function(item,index){
				if(item.chromId == id){
					item.chromEnhance = newUrl;
				}
			});
	  },
	  updateImageTodb: function(imageList){
		  var _self =  this;
		  imageList.forEach(function(item,index){
				let url = item.chromEnhance;
				let id = item.chromId;
				let grayId = $("#grayId").val();
				getImageObj(id,grayId,url,_self.db);
			});
	  },
	  enImage: function(id,colorStepList){
		  let p_colorStepList = colorStepList;
		  read(id,this.db,function(err,result){
			  if(!result){
				  return;
			  }
			  var imageData = result.imageData;
			  
				var enhanceCanvas = document.getElementById("enhanceOutput");
				var enhanCxt = enhanceCanvas.getContext("2d");
			    //enhanCxt.clearRect(0,0,result.width,result.height);
			    var dstData = enhanCxt.getImageData(0, 0, result.width, result.height);
			    

			    var imageDataCopy = new ImageData(
						  new Uint8ClampedArray(imageData),
						  result.width,
						  result.height
						)
			    var attr = {
			    	colorStepList: p_colorStepList,
			    	id:result.id
			    }
				
			    //enhanceImage(finalMat,90);
			    workerSmear.postMessage({'cmd': 'enhanceImage',  'imgData': imageDataCopy,'dstData':dstData, 'attr':attr});
		  });
	  },
	  fillImage: function(id,grayId,currentImage,ctxId){
		  let _self = this;
		  read(id,this.db,function(err,result){
			  if(!result){
				  return;
			  }
			  let cImage = currentImage || {};
			  if(!result.fillOriImage){
				  let imageInfo = {
		        	id: result.id,
		        	grayId:grayId,
		        	imageData: result.imageData,
		        	width: result.width,
		        	height: result.height,
		        	fillOriImage: currentImage.imageData
				  }
				  putChromosomeInDb(_self.db,imageInfo);
			  }else{
				  cImage.imageData = result.fillOriImage;
				  cImage.width = result.fillOriImage.width;
				  cImage.height = result.fillOriImage.height;
				  
			  }
			  
			  
			  let findItem = _.find(_self.imageList, function(o) {
					return id == o.chromId; 
				});

				
			    var attr = {
			    	colorStepList: findItem.colorStepList,
			    	id:id,
			    	ctxId:ctxId
			    }
			    
			    var enhanceCanvas = document.getElementById(ctxId);
				var enhanCxt = enhanceCanvas.getContext("2d");
			    var dstData = enhanCxt.getImageData(0, 0, cImage.width, cImage.height);
				
			    //enhanceImage(finalMat,90);
			    workerSmear.postMessage({'cmd': 'fillImage',  'imgData': cImage.imageData, 'dstData':dstData, 'attr':attr});
		  })
	  },
	  changeColor: function(item,gamma,band){
		  if(item.isShow){
				if(nuclearAnalysisApp.type == TypeColorEnm.gamma){
					item.gamma = item.gamma + gamma;
				}else if(nuclearAnalysisApp.type == TypeColorEnm.band){
					item.band = item.band + band;
				}

				if(!item.colorStepList){
					item.colorStepList = [];
				}
				let length = item.colorStepList.length;
				let lastStep = length > 0 ? item.colorStepList[length - 1] : {};
				let isExistFlag = lastStep.type == nuclearAnalysisApp.type;
				let upateStep = {
					id:item.chromId,
					type:nuclearAnalysisApp.type,
					value:nuclearAnalysisApp.type == TypeColorEnm.gamma ? item.gamma : item.band
				}
				if(isExistFlag){
					item.colorStepList[length - 1] = upateStep;
				}else{
					item.colorStepList.push(upateStep);
				}
				
				// 重新按照类型和步骤重新增强或者锐化
				nuclearAnalysisApp.enImage(item.chromId,item.colorStepList);
				
				
			}
	  },
	  changeFillColor: function(item,gamma,band){
		  let _self = this;
		  read(item.chromId,this.db,function(err,result){
			  if(!result){
				  return;
			  }
			  let cImage = result.fillOriImage;
			  if(!item.colorStepList){
					item.colorStepList = [];
				}
			  let length = item.colorStepList.length;
			  let lastStep = length > 0 ? item.colorStepList[length - 1] : {};
			  let isExistFlag = lastStep.type == nuclearAnalysisApp.type;
			  if(nuclearAnalysisApp.type == TypeColorEnm.gamma){
				  item.gamma = item.gamma + gamma;
				}else if(nuclearAnalysisApp.type == TypeColorEnm.band){
					item.band = item.band + band;
				}
			  let upateStep = {
				id:item.chromId,
				type:nuclearAnalysisApp.type,
				value:nuclearAnalysisApp.type == TypeColorEnm.gamma ? item.gamma : item.band
			  }
			  if(isExistFlag){
				item.colorStepList[length - 1] = upateStep;
			  }else{
				item.colorStepList.push(upateStep);
			  }

			 var ctxId = "myCanvas";
			 var attr = {
			    	colorStepList: item.colorStepList,
			    	id:item.chromId,
			    	ctxId:ctxId
			    }
			    
		     var enhanceCanvas = document.getElementById(ctxId);
			 var enhanCxt = enhanceCanvas.getContext("2d");
		     var dstData = enhanCxt.getImageData(0, 0, cImage.width, cImage.height);
			
		     //enhanceImage(finalMat,90);
		     workerSmear.postMessage({'cmd': 'fillImage',  'imgData': cImage.imageData || cImage, 'dstData':dstData, 'attr':attr});
		  })  
	  }
  },
  mounted:function () {
	  this.init();
  }
})

$(document).keydown(function(event){
	if(event.keyCode == 16){
		isShiftDown = true;
	}
	// +
	if((event.keyCode==107 || event.keyCode==109) && isShiftDown){
		nuclearAnalysisApp.type = TypeColorEnm.gamma;
	}else if((event.keyCode==107 || event.keyCode==109) && !isShiftDown){
		nuclearAnalysisApp.type = TypeColorEnm.band;
	}
	
	let isArCanvas = false;
	let isVisible = $("#chuli1").css("visibility");
	if(isVisible == "visible"){
		isArCanvas = true;
	}
	
	if(event.keyCode==107 && !isArCanvas){
		nuclearAnalysisApp.imageList.forEach(function(item,index){
			nuclearAnalysisApp.changeColor(item,2,0.3);
		});
	}
	
	if(event.keyCode==107 && isArCanvas){
		let findItem = _.find(nuclearAnalysisApp.imageList, function(o) {
			return currentImage.chromId == o.chromId; 
		});
		nuclearAnalysisApp.changeFillColor(findItem,2,0.1);
	}
	
	// -
	if(event.keyCode==109 && !isArCanvas){
		nuclearAnalysisApp.imageList.forEach(function(item,index){
			nuclearAnalysisApp.changeColor(item,-2,-0.1);
		});
	}
	
	if(event.keyCode==109 && isArCanvas){
		let findItem = _.find(nuclearAnalysisApp.imageList, function(o) {
			return currentImage.chromId == o.chromId; 
		});
		
		nuclearAnalysisApp.changeFillColor(findItem,-2,-0.1);
	}
})

$(document).keyup(function(event){
	if(event.keyCode == 16){
		isShiftDown = false;
	}
})