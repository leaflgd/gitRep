$(function(){
	$("#model_analyse1").addClass("active");
	$("#li_count_a").addClass("active");
//	var urls="/chromosome/page/images/patientdata";
	
	//var urls="http://192.168.100.156:18005/images/image/patient/";    //156
//	var urls="http://192.168.80.64:18005/images/image/patient/";   //64
	
//	var urls="";
	var inName;
	var chromN;
	var sums = 0;
	var grayId;
	var allCheckCount = 0;
	var chPath = "";
	var count2 = 0;
//	页面初始化加载
	$(document).ready(function(){ 
		$.ajax({ 
			url:'/chromosome/chCount/chromLabelQuery', 
	        type:'post',
	        data:JSON.stringify(),
	        contentType: "application/json;charset=utf-8",
	        dataType:"json",
	       success:function(data){
	    	   var list1 = data["label"];
	    	   var list2 = data["sing"];
	    	   var list3 = data["countCheck"];
	    	   var list4 = data["analysAble"];
	    	   
	    	   grayId = data["id"];
	    	   for (var int = 0; int < list3.length; int++) {
	    			if(grayId==list3[int].grayId){
	    				if(list3[int].countCheck==true || list3[int].analyCheck==true){
	    				
	    					$('#countLeft2_hexing').css({"width":"50px","height":"28px","display":"block"})
	    				}else {
	    					if(list4[0].analyStatus == 1 && list4[0].countStatus == 1){								
	    						if(list4[0].checkTag==0){									
	    							$('#countLeft2_hexing').attr("src","../../../images/wu.png");
	    							$('#countLeft2_hexing').attr("class","wu_img");
	    							$('.wu_img').css({"width":"59px","height":"59px","display":"inline-block","position":"absolute","left":"500px"});
	    						}else{								
	    							$('#countLeft2_hexing').attr("src","../../../images/you.png");
	    							$('#countLeft2_hexing').attr("class","you_img1");
	    							$('#countLeft2_hexing').css({"width":"59px","height":"59px","display":"block"})
	    						}
	    					}
	    					
	    				}
	    						
	    									
	    			}

	    		}
   
	    	   for(var q = 0 ; q <list2.length ; q++){
	    		   sums = sums+parseInt(list2[q].chromNum);
	    		   var ht="";
    	    	   var k;
    	    	   var index = 0;
    	    	   for (var h= 0; h < 70; h++) {
    	    		   k = h%7;
    	    		   if (k==0) {
    	    			   ht = ht+"<tr>" 
    	    		   } else{
    	    			   if(list2[index]!=undefined){
    	    				   chPath = list2[index].chPath;
    	    				   ht = ht+ "<td><img  src='"+urls+list2[index].chromUrl+"'>" +
    	    				   "<img style='display:none;' src='"+urls+list2[index].chPath+"'>" +
    	    			   		"<div class='countNum' id='"+list2[index].chromId+"'>" +
    	    			   			"<label>"+list2[index].chromNum+"</label>" +
    	    			   		"</div></td> "
    	    			   }else{
    	    				   ht = ht+ "<td>" +
    	    			   		"<div class='countNum' id=''>" +
    	    			   			"<label></label>" +
    	    			   		"</div></td> "
    	    			   }
    	    			   index++;
    	    		   }
    	    		   $(".analyse_tab1 button").click(function(){
    	    			   if(true){
    	    				   for(var a=0;a<=49;a++){
    	    					   var sum=0;
    	    					   sum+=a;
    	    				   }
    	    				
    	    			   }else{
    	    				   
    	    			   }
    	    		   })
    	    		   if(k==6){
    	    			   ht = ht+"</tr>"
    	    		   }
    	    	   }
    	    	   if(k!=6){
        			   ht = ht+"</tr>"
        		   }
        		   $(".analyse_tab1").html(ht); 
	    	   }
	    	   
	    	   
	    	   var count7 = 0;
				$(".countNum label").each(function(){
					if(parseInt($(this).text())>0){
						count7 += parseInt($(this).text());
					}
				});
				console.log("实际条数:"+count7);
		    	   
		    	   
		    	   grayId = data["id"];
		    	   for(var l = 0 ; l<list1.length;l++){
		    		   if(list1[l].chromRemake==null){
		    			   list1[l].chromRemake=='';
		    		   }
		    		  // console.log('list1[l].chromRemake:'+list1[l].chromRemake);
		    		   $('#number').attr(list1[l].inName);
		    		   document.getElementById('number').innerHTML=list1[l].inName;
		    		   inName = list1[l].inName;
	    			   document.getElementById('id').innerHTML=list1[l].slideName;
	   	    		  $("#id").append("<img id='id_img' src='../../../images/wclick.png' style='position:relative;left:17px;bottom:1px'>");
					 
	    			   document.getElementById('position').value=list1[l].grayNumber;
	    			   /*document.getElementById('text').value = list1[l].chromCount+' '+list1[l].chromRemake;*/
	    			   if(list1[l].chromRemake!=null){
	    				   if(list1[l].chromRemake.length>0){
	        				   document.getElementById('text').value = list1[l].chromCount+','+list1[l].chromRemake;
	    				   }else{
	    					   document.getElementById('text').value = list1[l].chromCount;
	    				   }
	    			   }else{
	    				   document.getElementById('text').value = list1[l].chromCount;
	    			   }
	    			   $("#pic2").attr("src",""+urls+list1[l].grayUrl);
	    			   $("#pic1").attr("src",""+urls+list1[l].arithOffset);
	    			   console.log("带入条数:"+list1[l].chromCount);
		    	   }
   
	    	   //加载核对的数值
	    	   var counts =data["count"];
	    	   $("#in45").text(counts[0]);
	    	   $("#in46").text(counts[1]);
	    	   $("#in47").text(counts[2]);
	    	   $("#in48").text(counts[3]);
	    	   $("#check_fruit1").text(counts[4]);
	    	   
	    		if(list4[0].analyStatus == 1){
					$("#number").css({
						"cursor":"pointer"
					});	
				}else{
					$("#number").attr("name","no");
				}
	    	   

	    	   
	       }
		});
		//预加载
		$.get("/chromosome/chCount/CountQuery",{status:"down"},function(data){
			console.log(data)
			var imglength1 = data.sing
			var urlsImages = [];
			$(data.label).each(function(index,e){
				 var ss = urls+e.arithOffset;  //156 
//				var ss = "http://192.168.80.64:18005/images/image/patient/"+e.arithOffset;     //64
				 urlsImages.push(ss)
			});
			for(var i = 1;i<=imglength1.length;i++){
				var ll =urls+imglength1[i-1].chromUrl    //156
//				var ll ="http://192.168.80.64:18005/images/image/patient/"+imglength1[i-1].chromUrl     //64
				urlsImages.push(ll)
			}
		    limitLoad(urlsImages, loadImg, 4).then(function() {
			      console.log('全部图片加载完毕！');
			});
			
		});
		//并发处理三个
	    function limitLoad(urls, handler, limit) {
	      var sequence = [].concat(urls);
	      var count = 0;
	      var promises = [];
	      var load = function() {
	        if (sequence.length <= 0 || count > limit)
	          return;
	        count += 1;
	        return handler(sequence.shift()).catch(function(err) {
	          console.error(err);
	        }).then(function() {
	          count -= 1;
	          console.log(("当前并发数：" + count));
	        }).then(function() {
	          load();
	        });
	      };
	      for (var i = 0; i < limit && i < sequence.length; i++) {
	        promises.push(load());
	      }
	      return Promise.all(promises);
	    }
	    function loadImg(url) {
	      return new Promise(function(resolve, reject) {
	        var img = new Image();
	        img.onload = function() {
	          resolve(img);
	        };
	        img.onerror = reject;
	        img.src = url;
	      });
	    }
		
		
	});
	
	
	

	/*分析核对点击可分析*/
	$(".countLeft1").on("click",".wu_img",function(){
		 $(this).removeAttr("class")
		var u2 = $("#number").text();
		var u3 = $("#position").val();
		var u4 = $("#id").text();
			$.ajax({
				url:'/chromosome/chCount/update2',
				type:'post',
				data:{chromId:u2,greyNum:u3,greyNum1:u4},
		        success:function(data){
		        	$('#countLeft2_hexing').attr("src","../../../images/you.png");
		        	$('#countLeft2_hexing').attr("class","you_img1");
		    		$('#countLeft2_hexing').css({"width":"59px","height":"59px","display":"block"});
		    		
		        }
			});	
	});
	
	
	/*分析核对点击无分析*/
	$(".countLeft1").on("click",".you_img1",function(){
        $(this).removeAttr("class")
		var u2 = $("#number").text();
		var u3 = $("#position").val();
		var u4 = $("#id").text();
		console.log('111');
			$.ajax({
				url:'/chromosome/chCount/update3',
				type:'post',
				data:{chromId:u2,greyNum:u3,greyNum1:u4},
		        success:function(data){
		        	$('#countLeft2_hexing').attr("src","../../../images/wu.png");
		        	$('#countLeft2_hexing').attr("class","wu_img");
		    		$('#countLeft2_hexing').css({"width":"59px","height":"59px","display":"block"});
		        }
			});	
	});
	
	

	
	
	
	//加减在页面上的条数
	function inquery(str){
		$.ajax({ 
			url:'/chromosome/chromatid/chromCountNum', 
	        type:'post',
	        data:JSON.stringify(str),
	        contentType: "application/json;charset=utf-8",
	        dataType:"json",
	       success:function(js){
	    	   $("#count"+js.chromId+" label").text(js.chromNum);
	       }
		});
	}

	
	$(document).on("mousedown","#confirm5",function(){
	     $(this).attr("src","../../../images/tiaoguo1.png");
		});
		$(document).on("mouseup","#confirm5",function(){
			$(this).attr("src","../../../images/tiaoguo.png");
		});
	
/*	//确定按钮
	$(document).on("click","#confirm4",function(){
		var u1 = $("#order_number").val();
		var u2 = $("#number").text();
		var u3 = $("#position").text();
		alert('u1:'+u1+' '+'u2:'+u2+' '+'u3:'+u3);
		$.ajax({
			url:'/chromosome/chCount/update1',
			type:'post',
			data:{caryogram:u1,chromId:u2,greyNum:u3},
	        dataType:"json",
	        success:function(data){
	        }
		});
		
		var chromCount = document.getElementById("order_number").value;
		var remakes = document.getElementById("text").value;
		$.ajax({
			url:'/chromosome/chCount/chromUpdate',
			type:'post',
			data:{chromcount:chromCount,remake:remakes},
	        dataType:"json",
	        success:function(data){
	        	if(data == 1){
	        		window.location.href='/chromosome/chromatid/grayRequest?inName=c'+inName;
	        	}
	        }
		});
		
	});*/
	
	
		//禁止右键默认事件
		document.oncontextmenu = function(){
	        event.returnValue = false;
	    };
	    var imgthis;
	    var order_number,hexing,order_number1;
		$(".analyse_tab1").on("mousedown","tbody tr td",function(e){
			 e.preventDefault();
			 $("input").blur();
			 imgthis = $(this).find('label');
			 if(imgthis.text()!=''){
				    //右键减
					if(e.button==2){
						count2 = 0;
						var chromId = $(this).find("div").attr("id");
						gaoliang(chromId);
						$(".countNum label").each(function(){
	    					if(parseInt($(this).text())>0){
	    						count2 += parseInt($(this).text());
	    					}
	    				});
					
					     var a= $(this).find("label").html();
					      if (a>0) {
					    	/*	if($("#text").val().length>2){
									order_number1 = count2;
									hexing = $("#text").val().substring($("#text").val().indexOf(',')+1,$("#text").val().length);
									console.log("order_number:"+order_number1);
									console.log("hexing:"+hexing);
								}else{
									order_number1 = count2;
								}*/
					    	  if($("#text").val().indexOf(',')!=-1){
					    		    order_number1 = count2;
									hexing = $("#text").val().substring($("#text").val().indexOf(',')+1,$("#text").val().length);
									console.log("order_number:"+order_number1);
									console.log("hexing:"+hexing);
					    	  }else{
					    		    order_number1 = count2;
					    	  }
					          a--;
					          order_number1--;
					          $(this).find("label").html(a);
					          
					          if($("#text").val().indexOf(',')==-1){
					        	  $("#text").val(order_number1); 
					          }else{
					        	  $("#text").val(order_number1+","+hexing);
					          }
					      }   
					      $.ajax({
								url:'/chromosome/chCount/chromSingleUpdate',
								type:'post',
								data:{chromId:chromId,a:a,sums:count2},
						        dataType:"json",
						        success:function(data){
						        }
							});
					}
					//左键加
					if(e.button==0){
						count2 = 0;
						var chromId = $(this).find("div").attr("id");
						gaoliang(chromId);
						var a= $(this).find("label").html();
						$(".countNum label").each(function(){
	    					if(parseInt($(this).text())>0){
	    						count2 += parseInt($(this).text());
	    					}
	    				});
						
						if($("#text").val().indexOf(',')!=-1){
							order_number1 = count2;
							hexing = $("#text").val().substring($("#text").val().indexOf(',')+1,$("#text").val().length);
							console.log("order_number:"+order_number1);
							console.log("hexing:"+hexing);
						}else{
							order_number1 = count2;
						}
					    a++;
					    order_number1++;
					    $(this).find("label").html(a);

					     if($("#text").val().indexOf(',')==-1){
									        	  $("#text").val(order_number1); 
									          }else{
									        	  $("#text").val(order_number1+","+hexing);
									          }
					    $.ajax({
							url:'/chromosome/chCount/chromSingleUpdate',
							type:'post',
							data:{chromId:chromId,a:a,sums:order_number1},
					        dataType:"json",
					        success:function(data){
					        }
						});
					};
					//中间键
					if(e.button == 1){
						count2 = 0;
						var chromId = $(this).find("div").attr("id");
						gaoliang(chromId);
						$(".countNum label").each(function(){
	    					if(parseInt($(this).text())>0){
	    						count2 += parseInt($(this).text());
	    					}
	    				});

						if($("#text").val().indexOf(',')!=-1){
												order_number = count2;
												hexing = $("#text").val().substring($("#text").val().indexOf(',')+1,$("#text").val().length);
												console.log("order_number:"+order_number);
												console.log("hexing:"+hexing);
											}else{
												order_number = count2;
												console.log("order_number:"+order_number);
											}
						$('.imgbox').css('display','block')
						$('.mengban').css('display','block')
						var td_src = $(this).children('img').next().attr("src");
						$('.imgbox').append('<img>')
						$('.imgbox img').attr('src',td_src)
						$('.imgbox img').css({
							'position':'absolute','transform':'translateX(-50%) translateY(-50%) scale(1)','left':'50%','top':'50%',
						});
						$('.imgbox input').val($(this).children('.countNum').children('label').html());
						$(".imgbox input").select();
					}
			 }
		});


    
	var zhege;
	var ctx;
	var imgObj ;
	var cvs;
	var sn=0;
	
	$(".countLeft2").on("click",function(){
		
		if($("#countLeft2_img").length==0){
			$(".mengban").css({'display':'block'})
			$('.countBtn').css({'z-index':'102'})
			var imgurl = $(this).find('img').attr("src");
			$(".countLeft2_k").remove();
			$(this).after("<div class='countLeft2_k'><div class='countLeft2_box'><img src="+imgurl+"></div><span class='close'></span></div>")
			
			
			
			
		}
	});
	/*滚轮放大缩小*/
	 var w,h,l,t;
	 $(document).on("mousewheel",".countLeft2_box",function(ev){
	      var oImg = this;
	      console.log(oImg);
	      var ev = event || window.event;//返回WheelEvent
	      ev.preventDefault();
	      var delta = ev.detail ? ev.detail > 0 : ev.wheelDelta < 0;
	      var ratioL = (ev.clientX - oImg.offsetLeft) / oImg.offsetWidth,
	          ratioT = (ev.clientY - oImg.offsetTop) / oImg.offsetHeight,
	          ratioDelta = !delta ? 1 + 0.1 : 1 - 0.1,
	         w = parseInt(oImg.offsetWidth * ratioDelta),
	         h = parseInt(oImg.offsetHeight * ratioDelta),
	         l = Math.round(ev.clientX - (w * ratioL)),
	         t = Math.round(ev.clientY - (h * ratioT));
	         with(oImg.style) {
	             width = w +'px';
	             height = h +'px';
	             left = l +'px';
	             top = t +'px';
	         }
	         $(oImg).children(':not(".close")').css({
	             'width':w,
	             'height':h,
	             'left':l,
	             'top':t,
	         })
	         $(oImg).parent().css({
	        	 'width':w,
	             'height':h,
	             'left':l,
	             'top':t,
	         })
	         
	         
	         
	 });
	 /*关闭*/
	 $(document).on("click",".close",function(){
		    code13 = 0;
		 	$(".mengban").css({'display':'none'});
		 	$('.countBtn').css({'z-index':'10'});
			$("#countLeft2_img").remove();
			$(".countLeft2_box").remove();
			$('.countLeft2_k').remove();
			sn=0;
		})
	
		
		/*使offsetParent父级指向body*/
		var actualLeft;
		var actualTop;
		var current;

		function elementParent(ele) {
			actualLeft = ele.offsetLeft;
			actualTop = ele.offsetTop;
			current = ele.offsetParent;
			while (current !== null) {
				actualTop += current.offsetTop;
				actualLeft += current.offsetLeft;
				current = current.offsetParent;
			}

		}
		/*标记*/
		/*点击添加一层canvas同时出现标记*/
		var adc = 0;
	    var zi =0;
	    var sn=0;
	   
		$(document).on("mousedown",".countLeft2_box",function(e) {
			if(e.button == 0){
				
			
				lock = true;
				elementParent(this);
				adc++;
		        zi++;
		        sn++;
		        var clickw = $('.countLeft2_box').width()
		        var clickh = $('.countLeft2_box').height()
		        var clickl = $('.countLeft2_box').position().left
		        var clickt = $('.countLeft2_box').position().top
		        $('.countLeft2_box').append("<canvas id=can"+adc+" style=z-index:"+zi+" class='can'></canvas>");
		        $('.can').css({
		            'width':clickw,
		            'height':clickh,
		            'left':clickl,
		            'top':clickt,
		        });
		        var clix = (e.clientX - actualLeft)*870/clickw-5;
		        var cliy = (e.clientY - actualTop)*870/clickh-1.5;
	
		        var cvs = document.getElementById("can"+adc);
		        cvs.width = 870;
		        cvs.height = 870;
		        var ctx = cvs.getContext('2d');
		        ctx.fillStyle="#FF0000";
		        ctx.fillRect(clix,cliy,10,3);
		        ctx.fillRect(clix+3.5,cliy-3.5,3,10);
		        var aa = $("#text").val();
		        
		        
		        if(aa.indexOf(",")!=-1){
		        	$('#text').val(sn + aa.substring(aa.indexOf(","),aa.length))
		        }else{
		        	$('#text').val(sn)
		        }

			}
			if(e.button == 2){
				code13 = 0;
				$(".mengban").css({'display':'none'});
			 	$('.countBtn').css({'z-index':'10'});
				$("#countLeft2_img").remove();
				$(".countLeft2_box").remove();
				$('.countLeft2_k').remove();
				sn=0;
			}
	        
		});
		/*backspace*/
		var lock = true;
		$(document).keydown(function (e) {
			if($('#text').is(':focus')){
				lock = false
			}
			
			if(lock == true && $(".countLeft2_box").length>0){
				
					if(e.keyCode==8){
						zi--;
						sn--;
						if(sn<=0){
			            	sn=0;
			            };
			            if(zi<=0){
			                zi=0;
			            };
						var aa = $("#text").val();
						
			            if(aa.length>2){
				        	$('#text').val( sn +',' +aa.substring(aa.indexOf(",")+1,aa.length));
				        }else{
				        	$('#text').val(sn)
				        };
			            
			            $('.can').last().remove();
					}
				
			}
	        
	    });
		
    /*$(document).on("click","#countLeft2_img img",function(e){
    	e.stopPropagation();
    	$(this).parent().remove();
    	
    });  */
    
    $(document).on("mousedown",".imgbox input",function(e){
    	e.stopPropagation();
    });
	

    
    //图片中间的计数
	var  count = 0;
	$(document).on("mousedown",".imgbox",function(e){
		if(e.button == 1){
			count = parseInt($('.imgbox input').val());
			if(isNaN(count)){
				count = 0;
			} 
			if(count<0){
				alert("请输入正常的核型条数数量");
			}else{
				imgthis.html(count);
				var count3 = 0;
				$(".countNum label").each(function(){
					if(parseInt($(this).text())>0){
						count3 += parseInt($(this).text());
					}
				});

				console.log("count3:"+count3);
				if($("#text").val().length>2){
					$("#text").val(count3+","+hexing);
				}else{
					$("#text").val(count3);
				}
				$('.imgbox').css('display','none')
				$('.mengban').css('display','none')
				$('.imgbox img').remove()
				$('.imgbox input').val('');
			}
			
			
			
		
			

		}
		if(e.button == 0){
			var chromId = $(this).find("div").attr("id");
			count = parseInt($('.imgbox input').val());

			
			if(isNaN(count)){
				count = 0;
				count++;
			}else if(count<0){
				alert("请输入正常的核型条数数量");
			}else{
				count++;order_number++;
			}
			
			
			
			$('.imgbox input').val(count)
			imgthis.html(count)
			if($("#text").val().length>2){
				$("#text").val(order_number+","+hexing);
			}else{
				$("#text").val(order_number);
			}
			
			
			
		    $.ajax({
				url:'/chromosome/chCount/chromSingleUpdate',
				type:'post',
				data:{chromId:chromId,a:count,sums:order_number},
		        dataType:"json",
		        success:function(data){
		        }
			});
		}
		if(e.button == 2){
			var chromId = $(this).find("div").attr("id");
			count = parseInt($('.imgbox input').val());
			if(count>0){
				count--;
				order_number--;	
			}else if(isNaN(count)){
				count = 0;
			}else if(count<0){
				alert("请输入正常的核型条数数量");
			}
			
			
		
			
			
			$('.imgbox input').val(count)
			imgthis.html(count)
			if($("#text").val().length>2){
				$("#text").val(order_number+","+hexing);
			}else{
				$("#text").val(order_number);
			}
			 $.ajax({
					url:'/chromosome/chCount/chromSingleUpdate',
					type:'post',
					data:{chromId:chromId,a:count,sums:order_number},
			        dataType:"json",
			        success:function(data){
			        }
				});
		}
	})
    
    
    

	
	//图片变亮
	function gaoliang(imgid){
		  $.ajax({
			  url:'/chromosome/chCount/singleChromToLine',
				type:'post',
				data:{imgId:imgid},
		        dataType:"text",
		        success:function(data){
		        	$("#pic1").attr("src",urls+data);
		        }
		  });
	}
	
	//zy用来判断文本框是否有焦点 用于左右键切换判断
	var zy = 0;
	
	$("input").blur(function(){
		zy = 0;
	});

    $("input").focus(function(){
		zy = 1;
	});
	
	
	//左右快捷键
	  $(document).keyup(function(event){
		  if(zy==0){
			  if(event.keyCode == 37){

						$.get('/chromosome/chromatid/grayFoWordQuerys',{'v':'up'},function(data){
							if(data=='success'){
								location.reload();
							}else{
								alert("已是第一页或最后一页");
								$('.mengban').css('display','none');
								$('.countLeft2_k').remove();
							}
						});				    
				  }else if(event.keyCode == 39){

						$.get('/chromosome/chromatid/grayFoWordQuerys',{'v':'down'},function(data){
							if(data=='success'){
								location.reload();
							}else{
								alert("已是第一页或最后一页");
								$('.mengban').css('display','none');
								$('.countLeft2_k').remove();
							}
						});
				  }
		  }

	  });
	
	  $("#fortysix").click(function(){
		  code13 = 0;
		  if($("#text").val().indexOf(",")>-1){ 
			  $("#text").val("46,"+$("#text").val().substring($("#text").val().indexOf(",")+1,$("#text").val().length));	
		  }else{
			  $("#text").val("46");
		  }
		  


		  
		 //$("#text").val("46"); 
		 
		 /*var u1 = $("#order_number").val();
			var u2 = $("#number").text();
			var u3 = $("#position").text();
			alert('u1:'+u1+' '+'u2:'+u2+' '+'u3:'+u3);
			$.ajax({
				url:'/chromosome/chCount/update1',
				type:'post',
				data:{caryogram:u1,chromId:u2,greyNum:u3},
		        dataType:"json",
		        success:function(data){
		        }
			});*/
	  });
	  
	  $("#fortysix").mousedown(function(){
		  $(this).attr("src","../../../images/461.png")
	  });
      $("#fortysix").mouseup(function(){
    	  $(this).attr("src","../../../images/46.png")
	  });
      
      /*确定按钮*/
      $("#confirm5").click(function(){
    	  ascertain();
      });
      var code13 = 0;
      $(document).keydown(function (e){
    	  if(code13!=1){
    		  if(e.keyCode == 13){
        		  ascertain();
        	  }  
    	  }
    	
      })
      
      function ascertain(){
    	  var u1 = $("#text").val();
			var u2 = $("#number").text();
			var u3 = $("#position").val();
			var u4 = $("#id").text();
		
			if(u1==null||u1==''){
				alert("计数结果不能为空");
			}else{
				$.ajax({
					url:'/chromosome/chCount/update1',
					type:'post',
					data:{caryogram:u1,chromId:u2,greyNum:u3,greyNum1:u4},
			        success:function(data){
			        	$.get('/chromosome/chromatid/grayFoWordQuerys',{'v':'down'},function(data){
							if(data=='success'){
								location.reload();
							}else{
								alert("已是第一页或最后一页");
								$('.mengban').css('display','none');
								$('.countLeft2_k').remove();
							}
						});
			        }
				});	
			}  
      }
      
      
      //计数页面的文本框跳转
      $("#position").focus(function(){
    	  code13  = 1;
    	  document.onkeydown = function(ew){
    		  var e = ew || windown.ew || argyments.caller.arguments[0];
    		  if(e.keyCode == 13){
    			//根据事件号、00X查找玻片号
    			  var u2 = $("#number").text();
					var u3 = $("#position").val();
					var u4 = $("#id").text();
	        		 $.ajax({
	 					url:'/chromosome/chCount/findGreyId1',
	 					type:'post',
	 					data:{eventId:u2,greyNum:u3,dialPiece:u4},
	 			        dataType:"json",
	 			        success:function(data){
	 			        	/*alert(data);*/
	 			        	if(data=="1"){
	 			        		alert("此编号玻片未勾选可计数");
	 			        	}else{
	 			        		var id = data;
								window.location.href = '/chromosome/chromatid/chromRequest?grayId=c'
										+ id + '';
	 			        	}	 			   	
	 			        }
	 				});
    			 
    		  }
    	  }
      });
      
      
      $("#number").hover(function(){
    	  if($(this).attr("name")!="no"){
    		  $(this).css("color","#00a0e9");
        	  $("#id").css("color","#00a0e9");
        	  $("#id_img").attr("src","../../../images/yclick.png");
    	  }
    	 
      },function(){
    	  $(this).css("color","#fff"); 
    	  $("#id").css("color","#fff");
    	  $("#id_img").attr("src","../../../images/wclick.png");
      })
      
    //计数页面点击编号的跳转
      $("#number").click(function(){
    	  if($(this).attr("name")!="no"){
    		    $.ajax({ 
				url:'/chromosome/chromatid/grayQuerys1', 
		        type:'post',
		        data:JSON.stringify("a"),
		        contentType: "application/json;charset=utf-8",
		        dataType:"json",
		        success:function(js){
		        	console.log(js);
		        	var u1 = $("#order_number").val();
					var u2 = $("#number").text();
					var u3 = $("#position").val();
					var u4 = $("#id").text();
					//根据拨片号判断是否勾选分析
		        	var check = 0;
		        	var check1 = u4+','+u3;
		        	console.log('check1:'+check1);
		        	for ( var a in js) {
		        		var check2 = js[a].slideName+','+js[a].grayNumber;
		        		if(check1 == check2){
		        			check = 1;
		        		}
		        	}
		        	if(check == 1){
				//根据事件号、00X查找玻片号
		        		 $.ajax({
		 					url:'/chromosome/chCount/setAnalyGreyId',
		 					type:'post',
		 					data:{eventId:u2,greyNum:u3,dialPiece:u4},
		 			        dataType:"json",
		 			        success:function(data){
		 			        	
		 			        	var id=  data ;
	    		        		window.location.href='/chromosome/chromatid/chromRequest?grayId=a'+id;
		 			        }
		 				});   		        		 		      
		        	}else{
		        		alert("未勾选可分析,不可跳转!");
		        	}

		        }
			});
    	  }
           });
      
    //计数核对页面点击计数返回
      $("#li_count_a").click(function(){
    	  var inName = $("#number").text();
    	  
    	  $.ajax({
  			url : '/chromosome/chromatid/paramSet',
  			type : 'post',
  			data : JSON.stringify({
  				"dsum" :inName,
  				"xsum" :'',
  				"tiaoshu" :''
  			}),
  			contentType : "application/json;charset=utf-8",
  			
  			success : function(js) {
  			
  				window.location.href='/chromosome/page/jsp/caryogram/operation/carycount.jsp';
  				
  			}
  		});	
      });
});


