$(function() {
	// var urls="/chromosome/page/images/patientdata";
	// update by tangtao 2018/11/16 look at common.js
//	var urls = "http://192.168.100.160:18005/images/image/patient/";
//	var nodeUrl = "http://192.168.100.160:18015"; 
	
//	var urls="http://192.168.80.64:18005/images/image/patient/";
//	var nodeUrl = "http://192.168.80.64:18015";
	
	var a6 = "";// 一般用于旋转镜像
	var deg = 0;// 旋转180度
	var b = 0;
	var c = "c2";// 颜色调整
	var num1 = 0;// 交换用
	// var a9 = null;
	var a8 = null;// 一般用于移动 复制 交换
	var image = null;// 替换用于保存src路径
	var a10 = null;// 核型一览用到 保存id
	// var a11 = 0;//保存id
	var grayNumber;
	var slideName;
	var inName;
	var chromtoColorOpera;
	var caryograms = "";
	var imgobj;
	var obj;
	var imgobjwidth = 0;
	var imgobjheight = 0;
	$("#model_analyse1").addClass("active");
	$("#li_analyse_show_a").addClass("active");

	// 缩小标识
	var x = 0;
	// 放大标识
	var d = 0;
	// 样式状态ID
	var statusId;
	// 是否点击确定过
	var isAscertain1 = false;
	// 初始化页面
	$(document).ready(function() {
		init();
	});
	function init() {
		$.ajax({
					url : '/chromosome/chAnalysis/chLabelQuerys',
					type : 'post',
					data : JSON.stringify(),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(js) {
						for ( var key in js) {
							if (key == "label") {
								for ( var i in js[key]) {
									inName = js[key][i].inName;
									$(".number1").text(js[key][i].inName);
									$(".id1").text(js[key][i].slideName);
									slideName = js[key][i].slideName;
									$(".position1").text(js[key][i].grayNumber);
									grayNumber = js[key][i].grayNumber;
									$(".order_number1").val(
											js[key][i].chromCary);
									caryograms = js[key][i].chromCary;
									$(".analyse_Right1_box").html(
											"<img src='" + urls
													+ js[key][i].grayUrl
													+ "' />");
								}
							} else if (key == "chrom") {
								for ( var j in js[key]) {
									var mark = js[key][j].chromMark;
									var judeg = js[key][j].chromState
									if (judeg) {
										$("#chromAbnormal")
												.prepend(
														"<div class='imgContainer'><img crossorigin='' class='checked' style=' ' id='"
																+ js[key][j].chromId
																+ "' src='"
																+ urls
																+ js[key][j].chromUrl
																+ "'/></div>");
									} else if (mark == 25) {
										$("#markan")
												.prepend(
														"<div class='imgContainer'><img crossorigin='' class='checked' style='' id='"
																+ js[key][j].chromId
																+ "' src='"
																+ urls
																+ js[key][j].chromUrl
																+ "' /></div>");
									} else {
										$("#mark" + mark)
												.prepend(
														"<div class='imgContainer'><img crossorigin=''  class='checked' style='' id='"
																+ js[key][j].chromId
																+ "' src='"
																+ urls
																+ js[key][j].chromUrl
																+ "' /></div>");
									}
									if (js[key][j].chScacss == null) {
										js[key][j].chScacss = "";
									}
									if (js[key][j].chRotcss == null) {
										js[key][j].chRotcss = "";
									}

									$("#" + js[key][j].chromId).css(
											"transform",
											js[key][j].chScacss + " "
													+ js[key][j].chRotcss);
									$("#" + js[key][j].chromId).css(
											"transform",
											"rotate(" + js[key][j].chCircumX
													+ ")");
									$("#" + js[key][j].chromId).css("top",
											js[key][j].topDown + "px");
									
								}
								/*
								 * obj =
								 * document.querySelectorAll("img[src*='.bmp']")
								 * console.log(obj) for(var i = 0;i<=obj.length;i++){
								 *  }
								 */

							}else if (key == "status"){
								console.log("状态"+js["status"]);
								if(js["status"]==true){
									$("#report").attr("disabled",false);
									$("#report").css({'color':'#fff'});
								}
							}
						}

						var imgNum = document.querySelectorAll('.checked').length;
						var objimg = document.querySelectorAll('.checked')
						$('.checked').load(
										function() {
											if (!--imgNum) {
												for (var i = 1; i <= objimg.length; i++) {
													objimg[i - 1].parentNode.style.width = objimg[i - 1]
															.getBoundingClientRect().width
															+ 'px'
													// objimg[i-1].style.top =
													// objimg[i-1].parentNode.style.height/2-objimg[i-1].getBoundingClientRect().height/2+'px';
													// $('.checked').eq(i-1).css({'top':$(objimg).eq(i-1).parent().height()/2-objimg[i-1].getBoundingClientRect().height/2})

												}

											}
										});
						setTimeout(wys, 1000);

						containHeight();

						// setTimeout(felxH,500);

						// inquery();
						/*
						 * $(imgobj).parent().width(imgobj.getBoundingClientRect().width);
						 * $(imgobj).parent().height(imgobj.getBoundingClientRect().height)
						 */
						// console.log(imgobj)
						// 加载样式
						$.get('/chromosome/chAnalysis/queryStatus', function(
								result) {
							statusId = result['statusId'];
							// alert("statusId:"+statusId);
							// 获取放大标记
							var magnifyTag = result['magnifyTag'];
							// 获取缩小标记
							var shrinkTag = result['shrinkTag'];
							d = magnifyTag;
							x = shrinkTag;

							// 修改颜色调整
							//$("#range").attr("value", result['colorTag']);
							// 一秒后设置
							setTimeout(colorTag, 300);
							setTimeout(wys, 1000);

							containHeight();

						});
						
						$.ajax({ 
							url:'/chromosome/chromatid/grayQuerys2', 
					        type:'post',
					        data:JSON.stringify("c"),
					        contentType: "application/json;charset=utf-8",
					        dataType:"json",
					        success:function(js){
								var u2 = $(".number1").text();
								var u3 = $(".position1").text();
								var u4 = $(".id1").text();
								
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
					        	

					        	if(check==1){
					        		console.log("check56466565");
					        		$(".number1").css({"cursor":"pointer"});
					        		$(".number1").attr("name","aa")
					        	}else{
					        		$(".number1").css("color","#ffffff");
					        	}

					        }
						});
					}

				});

	};
	
	$(".number1").hover(function(){
		if($(this).attr("name")=="aa"){
			$(this).find("img").attr("src","../../../images/yclick.png");
			$(this).css("color","#00a0e9");
		}
	},function(){
		if($(this).attr("name")=="aa"){
			$(this).find("img").attr("src","../../../images/wclick.png");
			$(this).css("color","#fff");
		}
	});
	
	//zy用来判断文本框是否有焦点 用于左右键切换判断
	var zy = 0;
	
	$("input").blur(function(){
		zy = 0;
	});

    $("input").focus(function(){
		zy = 1;
	});

	
	$(".order_number1").mouseover(function(){
		$(this).attr("title",$(this).val());
	});

	// 空格旋转180
	// 空格旋转180
	var xc = 0;
	$(document).keydown(function(e) {
						if (e.keyCode == 32) {
							clearAll();
							if (xc == 0) {
								if (a6.attr("id") != '') {
									var chromId = a6.attr("id");
									$.ajax({
										url : '/chromosome/chAnalysis/chRotcssQuery',
										type : 'post',
										data : {
											chromId : chromId
										},
										dataType : "text",
										cache : false,
										async : false,
										success : function(js) {
											rotcss = js;
										}
									});
									$.ajax({
										url : '/chromosome/chAnalysis/chScacssQuery',
										type : 'post',
										data : {
											chromId : chromId
										},
										dataType : "text",
										cache : false,
										async : false,
										success : function(js) {
											scacss = js;
										}
									});

									//var imgsrc = a6.attr("src");
									if (a6 !== "") {
										if (a6.attr("style").indexOf("scaleX(-1)") == -1) {
											if (a6.attr("style").indexOf("rotate(180deg)") == -1) {
												rotcss = "rotate(180deg)";
												a6.css({
													"transform" : rotcss
												})
											} else {
												rotcss = "rotate(0deg)";
												a6.css({
													"transform" : rotcss
												})
											}
										} else {
											if (a6.attr("style").indexOf("rotate(180deg)") == -1) {
												rotcss = "rotate(180deg)";
												a6.css({
													"transform" : rotcss + " " + scacss
												})
											} else {
												rotcss = "rotate(0deg)";
												a6.css({
													"transform" : rotcss + " " + scacss
												})
												// var img = data();
											}
										}
									}
									$.ajax({
										url : '/chromosome/chAnalysis/chRotcss',
										type : 'post',
										data : {
											rotcss : rotcss,
											id : chromId
										},
										dataType : "json",
										success : function(js) {
										}
									});
	 
								}
								xc = 1;
							}
						}
						$(this).keyup(function() {
							if (xc == 1) {
								a6.css("border", "none");
								num1 = 0;
								image = null;
								a8 = null;
								chromid = null;
								chromnum = null;
								a6 = "";
							}
							xc = 0;
						})
					});


	// 修改放大缩小颜色调整样式
	function colorTag() {
		//$('#range').trigger("change");
		if (d > x) {
			// 放大
			var i = d - x;
			d = i;
			x = 0;
			for (var j = 0; j < i; j++) {
				$(".ac div:nth-child(2) img").each(
						function(index, item) {
							var scalImage = scalAddPx(item.width, item.height,0.95);
							var height = scalImage.height;
							var width = scalImage.width;

//							$(this).css("width", width);
//							$(this).css("height", height);
							setImageAttr(width,height,this);
							// $(this).parent().css('width':)

							$(this).parent().width(
									this.getBoundingClientRect().width)
							// var scalImage =
							// scalAddPx(img.width,img.height,0.1,"+");
							// var height = scalImage.height;
							// var width = scalImage.width;

							setTimeout(wys, 1000);
						});
			}
		} else if (d < x) {
			// 缩小
			var i = x - d;
			d = 0;
			x = i;

			for (var j = 0; j < i; j++) {
				$('.ac div:nth-child(2) img').each(function(index, item) {
					var scalImage = scalScPx(item.width, item.height, 0.95);
					var height = scalImage.height;
					var width = scalImage.width;
//					$(this).css("width", width);
//					$(this).css("height", height);
					setImageAttr(width,height,this);
					$(this).parent().width(this.getBoundingClientRect().width)
					/*
					 * var height = $(this).height()-5; var width =
					 * $(this).width()-1.5; $(this).css("width", width);
					 * $(this).css("height", height);
					 */
					setTimeout(wys, 1000);
				});

			}

		}
	}

	// 移动放大缩小颜色调整样式
	function colorTags(id) {

		//$('#range').trigger("change");
		if (d > x) {
			// 放大
			var i = d - x;
			d = i;
			x = 0;
			for (var j = 0; j < i; j++) {

				var scalImage = scalAddPx($("#" + id).width(), $("#" + id).height(), 0.95);
				var height = scalImage.height;
				var width = scalImage.width;

				$("#" + id).css("width", width);
				$("#" + id).css("height", height);

				$("#" + id).parent().width(scalImage.width);

				setTimeout(wys, 1000);

			}
		} else if (d < x) {
			// 缩小
			var i = x - d;
			d = 0;
			x = i;
			for (var j = 0; j < i; j++) {

				var scalImage = scalScPx($("#" + id).width(), $("#" + id).height(), 0.95);
				$("#" + id).css("width", scalImage.width);
				$("#" + id).css("height", scalImage.height);

				// $("#"+id).parent().width($("#"+id).parent().getBoundingClientRect().width)
				$("#" + id).parent().width(scalImage.width);

				setTimeout(wys, 1000);
			}
		}
	}

	// 核型更新
	$(document).on("mousedown", "#update", function() {
		$("#update").css({
			"background" : "linear-gradient(#2ec0fb, #0e9aee,#0091ea)"
		});
	})
	$(document).on("mouseup", "#update", function() {
		$("#update").css({
			"background" : "linear-gradient(#59bbe9, #2ea5df,#0791d7)"
		});
	})
	$("#update").click(function() {
		console.log("触发更新核型");
		$.ajax({
			url : '/chromosome/chAnalysis/chKaryotyp',
			type : 'post',
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(js) {
				console.log("返回值"+js);
				$(".order_number1").val(js[0]);
				inquery();
			}
		});
	});
	// 判断页面的46个染色体的缺失
	function inquery() {
		var cary = "";
		for (var i = 1; i <= 24; i++) {
			var mark = $("#chScreenshot").find("#mark" + i).find(".checked").length
			var num = i;
			if (i < 23) {
				if (mark > 2) {
					for (var j = 0; j < mark - 2; j++) {
						cary = cary + ",+" + num;
					}
				} else if (mark < 2) {
					for (var j = 0; j < 2 - mark; j++) {
						cary = cary + ",-" + num;
					}
				}
			}
		}
		var carys = $(".order_number1").val();
		$(".order_number1").val(carys + cary);
	}

	
	// 选中的Mark

	// 调整移动复制的可触发作用域
	var markys = '';
	function wys() {
		markys = $('.ac');
		for (var i = 1; i <= markys.length; i++) {
			var w = markys.eq(i - 1).width();
			$('.wys').eq(i - 1).css('width', w);
		}
		newqiao = $('.inflex');
		for (var j = 0; j <= newqiao.length; j++) {
			var h = $('.item').eq(j).height();
			$('.flexbox').eq(j).find('.ac').children('.wys').css({
				'height' : h
			})

		}
	}

	// 调整高度
	/*
	 * function felxH(){ for(var i=0;i<=$('.item').length;i++){
	 * if($('.item').eq(i).height()>=180){ $('.item').eq(i).css({'height':''})
	 * }else{ $('.item').eq(i).css({'height':'180px'}) } } }
	 */
	// 旋转180
	var rotcss = "";// 角度
	var scacss = "";// 镜像
	$("#revolve").click(function() {
		var chromId = a6.attr("id");
		$.ajax({
			url : '/chromosome/chAnalysis/chRotcssQuery',
			type : 'post',
			data : {
				chromId : chromId
			},
			dataType : "text",
			cache : false,
			async : false,
			success : function(js) {
				rotcss = js;
			}
		});
		$.ajax({
			url : '/chromosome/chAnalysis/chScacssQuery',
			type : 'post',
			data : {
				chromId : chromId
			},
			dataType : "text",
			cache : false,
			async : false,
			success : function(js) {
				scacss = js;
			}
		});

		//var imgsrc = a6.attr("src");
		if (a6 !== "") {
			if (a6.attr("style").indexOf("scaleX(-1)") == -1) {
				if (a6.attr("style").indexOf("rotate(180deg)") == -1) {
					rotcss = "rotate(180deg)";
					a6.css({
						"transform" : rotcss
					})
				} else {
					rotcss = "rotate(0deg)";
					a6.css({
						"transform" : rotcss
					})
				}
			} else {
				if (a6.attr("style").indexOf("rotate(180deg)") == -1) {
					rotcss = "rotate(180deg)";
					a6.css({
						"transform" : rotcss + " " + scacss
					})
				} else {
					rotcss = "rotate(0deg)";
					a6.css({
						"transform" : rotcss + " " + scacss
					})
					// var img = data();
				}
			}
		}
		$.ajax({
			url : '/chromosome/chAnalysis/chRotcss',
			type : 'post',
			data : {
				rotcss : rotcss,
				id : chromId
			},
			dataType : "json",
			success : function(js) {
			}
		});
		num1 = 0;
		a6 = "";
		$("img").removeClass("a");
		$('img').css({
			'border' : 'none'
		})
		a8 = null;
		chromid = null;
		chromnum = null;
		image = null;
		clearAll();
	});

	// 镜像
	$("#images").click(function() {
		var chromId = a6.attr("id");
		$.ajax({
			url : '/chromosome/chAnalysis/chRotcssQuery',
			type : 'post',
			data : {
				chromId : chromId
			},
			dataType : "text",
			cache : false,
			async : false,
			success : function(js) {
				rotcss = js;
			}
		});
		$.ajax({
			url : '/chromosome/chAnalysis/chScacssQuery',
			type : 'post',
			data : {
				chromId : chromId
			},
			dataType : "text",
			cache : false,
			async : false,
			success : function(js) {
				scacss = js;
			}
		});

		//
		//var imgsrc = a6.attr("src");
		if (a6 !== "") {
			if (a6.attr("style").indexOf("rotate(180deg)") == -1) {
				if (a6.attr("style").indexOf("scaleX(-1)") == -1) {
					scacss = "scaleX(-1)";
					a6.css({
						"transform" : scacss
					});
				} else {
					scacss = "scaleX(1)";
					a6.css({
						"transform" : scacss
					});
				}
			} else {
				if (a6.attr("style").indexOf("scaleX(-1)") == -1) {
					scacss = "scaleX(-1)";
					a6.css({
						"transform" : rotcss + "" + scacss
					});
				} else {
					scacss = "scaleX(1)";
					a6.css({
						"transform" : rotcss + "" + scacss
					});
				}
			}
			$.ajax({
				url : '/chromosome/chAnalysis/chScacss',
				type : 'post',
				data : {
					scacss : scacss,
					id : chromId
				},
				dataType : "json",
				success : function(js) {
				}
			});
			$("img").css('border', 'none')
			$("img").removeClass("a");
			a8 = null;
			a6 = "";
			num1 = 0;
			chromid = null;
			chromnum = null;
			image = null;
			clearAll();
		}
	});

	// 点击颜色调整按钮
	var q1 = 0;
	$("#adjust").click(function() {

		if (q1 == 0) {
			q1++;
			$(".analyse_btn div").css("display", "block");
		} else {
			$(".analyse_btn div").css("display", "none");
			q1 = 0;
		}
	});

	// 禁止鼠标右键
	document.oncontextmenu = function() {
		event.returnValue = false;
	};

	var imgW = 0;
	var allW = 0;
	// 判断染色体条数上限
	/*
	 * $(".analyse_Left1_tab tr td
	 * div:first-child").on("click","img",function(){ imgW = 0; imgW =
	 * $(this).width();
	 * 
	 * }); $(".analyse_Left1_tab tr td").click(function(){ allW =0; var markL =
	 * $(this).find("div:first").children("img")
	 * 
	 * for(var i = 1; i <= markL.length; i++){ var markW =
	 * markL.eq(i-1).width(); allW += markW; }
	 *  })
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 选中图片
	var divobj1 = [];
	var choseimg = '';
	$(".wys").next().on("mousedown", ".imgContainer img", function(event) {
		if(event.button == 0){
			a10 = $(this).attr("id");
			a6 = $(this);
			divobj1.push($(this).parent().parent().attr('id'));
			
			choseimg =$(this);//移动方法选取得图片
				
			console.log("选取图片")
			if (a6 != '') {
				$(".wys").css({
					'z-index' : '5'
				});
				
					$(this).css('border','1px solid #00a0e9');
				

			} else {
				$(".wys").css({
					'z-index' : ''
				})
			}
		}
		
	});
	
	
	
	//调用移动方法
	var divobj2 = '';
	var postion = "";
	var moveStatus = false;// 移动状态
	var elewidth = 0;
	var evexy = 0;
	$(".ac div:nth-child(1)").click(function(e) {
		
		var scend = $(this).next();
		
		evexy = e.offsetX;
		elewidth = $(e.currentTarget).next().width();
		
		// 左右移动图片
		var zuobiao1 = $(this).width() + $(this).offset().left;
		var zuobiao = $(this).offset().left + zuobiao1
		var chang = parseInt(zuobiao / 2);
		if (chang < parseInt(e.clientX)) {
			postion = "right";
		} else {
			postion = "left";
		}
		$("img").css("border", "none");
		aq = 0;
		z = $(this).next();
		a10 = $(this).next().attr("id");
		divobj1.push($(this).next().attr('id'));
		// 状态为正常区移动
		moveStatus = true;
		if (a8 != null) {
			move(a10, postion);
			
			movemethod(choseimg,scend);
			choseimg = ''
			clearAll();
			setTimeout(wys, 800);
			setTimeout(function() {
				containHeight()
			}, 300)
		}
		
		$(".wys").css({
			'z-index' : ''
		})
		
	});
	
	
	//调用replace方法
	var divobj3 = [];
	var timer = null;
	var dele = '';
	var isImglock = 0;
	var isImg1='';
	var isImg2='';

	$("div[id^='mark']").on("click", "img", function(e) {
		$("button").blur();
		clearTimeout(timer);
		dele = $(this).attr("id");
		console.log($(this).attr("id"));
		a10 = $(this).parent().parent().attr("id");
		divobj3.push($(this).parent().parent().attr('id'));
		// 状态2为正常区交换
		moveStatus = false;
		isImglock++;
		
		replace({
			'th' : $(this),
			'e' : e
		});
		a6 = $(this);
		if(isImglock%2==0){
			isImg2 = $(this);
			replacemethod(isImg1,isImg2);
			choseimg = '';
			clearAll()
		}else{
			isImg1 = $(this)
		}
		//if (aq == 0) {
		//} else {
			//aq = 0;
			//$("img").css("border", "none");
		//}
		setTimeout(wys, 1000);
		console.log(choseimg)
		
	});
	
	
	//点击标签移动
	$(".inflex p").click(function(e) {
		evexy = e.offsetX;
		elewidth = $(this).width();
		console.log(elewidth+"---"+evexy);
		//p标签
		var pscend = $(this);
		movemethod1(choseimg,pscend);
		choseimg = '';
		clearAll();
	});
	
	
	
	// 复制按钮
	var jsid = '';
	$("#duplicate").click(function() {
		if (a8 != null) {
			$.contextMenu({
				selector : '.wys',
				callback : function() {
					if (a8 == null || a8.attr("src") == null) {
						alert("未复制");
					} else {
						var isoff = $(this).next();
						
						$("img").css("border", "none");
						var chnum1 = $(this);
						$.ajax({
							url : '/chromosome/chAnalysis/chCopy',
							type : 'post',
							data : JSON.stringify({
								id : chromid,
								num : chnum1.next().attr("id")
							}),
							contentType : "application/json;charset=utf-8",
							dataType : "json",
							success : function(js) {
								console.log(js)
								//mapHandling(js);
								jsid = js;
								copyImg(a8,isoff,jsid);
								$("img").removeClass("a");
								$("img").css('border', 'none');
								num1 = 0;
								image = null;
								a8 = null;
								chromid = null;
								chromnum = null;
							}
						});
						a6 = "";
					}
				},
				items : {
					"copy" : {
						name : "粘贴",
						icon : "粘贴"
					}
				}
			});
			clearAll();
		}
	});
	
	
	//清空移动或交换的方法
	function clearAll(){
		isImglock = 0;
		isImg1='';
		isImg2='';
		
	}
	
	
	
	//标签移动方法
	function movemethod1(cfirst,cscend){
		var cfirst = cfirst;
		var cscend = cscend;
		//移动
		if(cfirst!=''){
			if(cfirst.attr('src')!=undefined && cscend.attr('src')==undefined){
				console.log("执行移动1")
				if(evexy>elewidth/2){
					var cfirst1 = cfirst.parent().clone()
					cscend.parent().children().children("div:nth-child(2)").append(cfirst1);
					cfirst.parent(".imgContainer").remove();
					return;
				}else{
					var cfirst1 = cfirst.parent().clone()
					cscend.parent().children().children("div:nth-child(2)").prepend(cfirst1);
					cfirst.parent(".imgContainer").remove();
					return;
				}
			}
		}
	}
	
	
	
	//移动方法
	function movemethod(chosefirst,chosescend){
		var chosefirst = chosefirst;
		var chosescend = chosescend;
		
		//移动
		if(chosefirst!=''){
			if(chosefirst.attr('src')!=undefined && chosescend.attr('src')==undefined){
				console.log("执行移动")
				
				if(evexy>elewidth/2){
					var chosefirstclone1 = chosefirst.parent().clone()
					chosescend.append(chosefirstclone1)
					chosefirst.parent(".imgContainer").remove();
					
					return;
				}else if (evexy<elewidth/2){
					var chosefirstclone2 = chosefirst.parent().clone()
					chosescend.prepend(chosefirstclone2)
					chosefirst.parent(".imgContainer").remove();
					
					return;
				}
			}
		  
		
		}
	}
	//交换方法
	function replacemethod(imgfirst,imgscend){
		var imgfirst = imgfirst;
		var imgscend = imgscend;
		if(imgfirst != ''){
			if(imgfirst.attr('src')!=undefined && imgscend.attr('src')!=undefined){
				if(imgfirst.attr('src')!=imgscend.attr('src')){
					 console.log("执行交换")
					 console.log(imgfirst)
					 console.log(imgscend)
					 var replacechose1 = imgfirst.parent().get(0).outerHTML;
					 var replacechose2 = imgscend.parent().get(0).outerHTML;
					 imgfirst.parent().get(0).outerHTML=replacechose2;
					 imgscend.parent().get(0).outerHTML=replacechose1;
					 console.log("交换成功")
					 
					 return;
				} 
				//自己与自己交换
				if(imgfirst.attr('src')==imgscend.attr('src')){
					 /*var replacechose1 = chosefirst.parent().get(0).outerHTML;
					 var replacechose2 = chosescend.parent().get(0).outerHTML;
					 chosefirst.parent().get(0).outerHTML=replacechose2;
					 chosescend.parent().get(0).outerHTML=replacechose1;*/
					 
					 return;
				}
			}
		}
		
	}
	
	
	//复制
	function copyImg(isImg,isDiv,jsid){
		var isImg = isImg;
		var isDiv = isDiv;
		var jsid = jsid;
		
		if(isImg!=''){
			if(isImg.attr('src')!=undefined && isDiv.attr('src')==undefined){
				var cloneisImg = isImg.parent().clone();
				console.log(cloneisImg)
				isDiv.append(cloneisImg)
				cloneisImg.children("img").attr('id',jsid)
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$(".flexbox p").click(function(e) {
				var zuobiao1 = $(this).width() + $(this).offset().left;
				var zuobiao = $(this).offset().left + zuobiao1
				var chang = parseInt(zuobiao / 2);
				if (chang < parseInt(e.clientX)) {
					postion = "right";
				} else {
					postion = "left";
				}
				$("img").css("border", "none");
				aq = 0;
				z = $(this).parent().children('div').find('div[id*="mark"]');
				a10 = $(this).parent().children('div').find('div[id*="mark"]')
						.attr("id");
				divobj1.push(a10);

				// 状态为正常区移动
				moveStatus = true;
				if (a8 != null) {
					move(a10, postion);
					setTimeout(wys, 800);
					setTimeout(function() {
						containHeight()
					}, 300)
				}

				$(".wys").css({
					'z-index' : ''
				})
			});

	// 异常区移动
	// var exceptions1 = [];
	// 异常区交换
	// var exceptions2 = [];
	var exceptionStatus = false;// 异常区状态
	$(".analyse_Left1_tab_div").on("click", "img", function(e) {
		$(this).css("border", "1px solid #00a0e9");
		// 状态3为异常区交换
		// exceptions2.push($(this).next().attr('id'));
		exceptionStatus = true;
		replace({
			'th' : $(this),
			'e' : e
		});
	});

	$(".dec").click(function() {
		if (a8 != null) {
			// console.log("异常区移动");
			$("img").css("border", "none");
			multimer = true;
			// 状态4为异常区移动
			// exceptions1.push($(this).next().attr('id'));

			// console.log($(this).next())
			exceptionStatus = true;

			move($(this));

		}
		setTimeout(wys, 1000);
		// setTimeout(felxH,500);
	});
	function containHeight() {
		if ($('.analyse_Left1_tab_div_1').children().children('img').length <= 0) {
			$('.analyse_Left1_tab_div').css({
				'display' : 'none'
			})
			$('#chScreenshot').addClass('h')
			$('.flex-contentout').addClass('h100')
		}
	}
	function sumOutFlexHeight() {
		var outFlex4 = document.getElementsByClassName("out-flex4 flexbox")[0];
		var bottom = document.getElementsByClassName("analyse_bottom")[0];
		var oHeight = $("#chScreenshot .flex-item4").outerHeight(true) + 10;
		if (!outFlex4 || !bottom) {
			return;
		}

		var lastOutTop = outFlex4.getBoundingClientRect().top
				+ document.body.scrollTop + oHeight;

		var bottomHeight = bottom.getBoundingClientRect().top
				+ document.body.scrollTop;

		return lastOutTop + 5 > bottomHeight;
	}
	function getScaleHeight() {
		var sumHeight = 0;
		for (var i = 1; i <= 4; i++) {
			sumHeight = sumHeight
					+ $("#chScreenshot_copy .flex-item" + i).outerHeight(true)
		}

		return sumHeight;
	}
	
	
	// 报告放大
	function reportImage(width, height, sc) {
		if (width <= 0 || height <= 0) {
			return 0;
		}
		return {
			width : width * sc,
			height : height * sc
		}
	}
	
	// 按照固定像素缩放图片
	function scalAddPx(width, height, sc) {
		if (width <= 0 || height <= 0) {
			return 0;
		}
		return {
			width : Math.round(width * (1 / sc)),
			height : Math.round(height * (1 / sc))
		}
	}
	
	// 按照固定像素缩放图片
	function scalScPx(width, height, sc) {
		if (width <= 0 || height <= 0) {
			return 0;
		}
		return {
			width : Math.round(width * sc),
			height : Math.round(height * sc)
		}
	}


	function autoResizeImage(maxWidth, maxHeight, width, height) {
		var hRatio;
		var wRatio;
		var ratio = 1;
		var w = width;
		var h = height;
		wRatio = maxWidth / w;
		hRatio = maxHeight / h;
		if (maxWidth == 0 && maxHeight == 0) {
			ratio = 1;
		} else if (maxWidth == 0) { //
			if (hRatio < 1) {
				ratio = hRatio
			}
			;
		} else if (maxHeight == 0) {
			if (wRatio < 1) {
				Ratio = wRatio
			}
			;
		} else if (wRatio < 1 || hRatio < 1) {
			ratio = (wRatio <= hRatio ? wRatio : hRatio);
		}
		if (ratio < 1) {
			w = w * ratio;
			h = h * ratio;
		}
		return {
			width : w,
			height : h
		}
	}

	// 处理Map集合
	function mapHandling(map) {
		var tags = null;
		for ( var key in map) {
			if (key == "chromAbnormal") {
				tags = $("#chromAbnormal");
				// console.log("交换****"+tags.get(0).getAttribute('id'))
			} else {
				tags = $("#mark" + key);
				// console.log("移动****"+tags.get(0).getAttribute('id'))

			}
			tags.children(".imgContainer").remove();
			for ( var j in map[key]) {
				tags.prepend("<div class='imgContainer'><img crossorigin='' class='checked' style=' ' id='"
						+ map[key][j].chromId + "' src='" + urls
						+ map[key][j].adjustUrl
						+ "' /></div>");
				if (map[key][j].circumgyrate == null) {
					map[key][j].circumgyrate = "";
				}
				if (map[key][j].mirror == null) {
					map[key][j].mirror = "";
				}
				$("#" + map[key][j].chromId).css("transform",
						map[key][j].circumgyrate + " " + map[key][j].mirror);
				$("#" + map[key][j].chromId).css("transform",
						"rotate(" + map[key][j].circumXdegree + ")");
				$("#" + map[key][j].chromId).css("top", map[key][j].topDown);

				colorTags(map[key][j].chromId);
			}
			/*
			 * moved_before(); moved_after();
			 */

		}
		if (exceptionStatus == true && moveStatus == true) {
			exceptions_move();
		} else if (exceptionStatus == true && moveStatus == false) {
			exceptions_replace();
		} else if (exceptionStatus == false && moveStatus == true) {
			moved_after();
		} else if (exceptionStatus == false && moveStatus == false) {
			replace_after();
		}
		// console.log(exceptionStatus == false && moveStatus == true)

	}

	// 移动复制交换遍历目标
	// 移动
	function moved_after() {

		var imgnum1 = $('#' + divobj1[0]).find("img");
		var objimg1 = document.querySelector('#' + divobj1[0])
				.querySelectorAll("img");
		var imglength1 = $('#' + divobj1[0]).find("img").length;

		imgnum1.load(function() {
			if (!--imglength1) {

				for (var i = 1; i <= imgnum1.length; i++) {
					objimg1[i - 1].parentNode.style.width = objimg1[i - 1]
							.getBoundingClientRect().width
							+ 'px';
					// objimg1[i-1].style.top =
					// $(objimg1).eq(i-1).parent().height()/2-objimg1[i-1].getBoundingClientRect().height/2
					// +'px'
				}
			}
		});
		var imgnum2 = $('#' + divobj1[1]).find("img");
		var objimg2 = document.querySelector('#' + divobj1[1])
				.querySelectorAll("img");
		var imglength2 = $('#' + divobj1[1]).find("img").length;

		imgnum2.load(function() {
			if (!--imglength2) {

				for (var i = 1; i <= imgnum2.length; i++) {
					objimg2[i - 1].parentNode.style.width = objimg2[i - 1]
							.getBoundingClientRect().width
							+ 'px';
					// objimg2[i-1].style.top =
					// $(objimg2).eq(i-1).parent().height()/2-objimg2[i-1].getBoundingClientRect().height/2
					// +'px'
				}
			}
		});

		divobj1 = [];
		divobj3 = [];
		moveStatus = false;
		exceptionStatus = false;
	}
	// 替换
	function replace_after() {

		var imgnum3 = $('#' + divobj3[0]).find("img");
		var objimg3 = document.querySelector('#' + divobj3[0])
				.querySelectorAll("img");
		var imglength3 = $('#' + divobj3[0]).find("img").length;
		
		imgnum3.load(function() {
			if (!--imglength3) {
				console.log("替换---选中图片加载完成")
				for (var i = 1; i <= imgnum3.length; i++) {
					objimg3[i - 1].parentNode.style.width = objimg3[i - 1]
							.getBoundingClientRect().width
							+ 'px';
					/*objimg3[i - 1].style.top = $(objimg3).eq(i - 1).parent()
							.height()
							/ 2
							- objimg3[i - 1].getBoundingClientRect().height
							/ 2 + 'px'*/
				}
			}
		});
		var imgnum4 = $('#' + divobj3[1]).find("img");
		var objimg4 = document.querySelector('#' + divobj3[1]).querySelectorAll("img");
		var imglength4 = $('#' + divobj3[1]).find("img").length;
		
		imgnum4.load(function() {
			if (!--imglength4) {
				console.log("替换---目标图片加载完成")
				for (var i = 1; i <= imgnum4.length; i++) {
					objimg4[i - 1].parentNode.style.width = objimg4[i - 1]
							.getBoundingClientRect().width
							+ 'px';
					/*objimg4[i - 1].style.top = $(objimg4).eq(i - 1).parent()
							.height()
							/ 2
							- objimg4[i - 1].getBoundingClientRect().height
							/ 2 + 'px'*/
				}
			}
		});
		divobj1 = [];
		divobj3 = [];
		moveStatus = false;
		exceptionStatus = false;
	}
	// 异常区交换
	function exceptions_replace() {
		console.log("异常区交换")

		moveStatus = false;
		exceptionStatus = false;
	}
	// 异常区移动
	function exceptions_move() {
		console.log("异常区移动")
		moveStatus = false;
		exceptionStatus = false;
	}

	// 多体判断
	var multimer = false;
	// 移动
	function move(chroms) {
		styles = a8.attr("style");
		var chrom1 = {
			'id' : chromid,
			'num' : chromnum,
			'num1' : chroms,
			'move' : postion
		}

		$.ajax({
			url : '/chromosome/chAnalysis/chAlternate',
			type : 'post',
			data : JSON.stringify(chrom1),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(jsonStr) {
				//mapHandling(jsonStr);
				multimer = false;
				chromid = null;
				chromnum = null;
				image = null;
				num1 = 0;
				a8 = null;
				$("img").removeClass("a");
				z = undefined;

				moveStatus = false;
			}
		});

	}
	;
	var chromid = null;
	var chromnum = null;

	// 替换
	function replace(e) {
		e.e.stopPropagation();
		a8 = e.th;
		if (num1 == 0) {
			num1++;
			image = a8.attr("src");
			a8.addClass("a");
			chromid = a8.attr("id");
			chromnum = a8.parent().parent().attr('id');
			styles = a8.attr("style");
		} else {
			if (chromid != a8.attr("id")) {
				var chrom1 = {
					'id' : chromid,
					'num' : chromnum,
					'id1' : a8.attr("id"),
					'num1' : a8.parent().parent().attr('id')
				}
				console.log(chrom1);
				$.ajax({
					url : '/chromosome/chAnalysis/chAlternate',
					type : 'post',
					data : JSON.stringify(chrom1),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(jsonStr) {
						//mapHandling(jsonStr);
					}
				});
			}
			num1 = 0;
			image = null;
			a8 = null;
			chromid = null;
			chromnum = null;

			// divobj3=[];
			a6 = "";
			$("img").css("border", "none");
		}
	}
	;
	var markW2 = 0;
	// 判断染色体条数上限
	$(".analyse_Left1_tab tr td").on("contextmenu", function() {
		markW2 = 0;
		var allimgL2 = $(this).find("div:first img");
		for (var i = 1; i <= allimgL2.length; i++) {
			allimgW2 = allimgL2.eq(i - 1).width();
			markW2 += allimgW2;
			// console.log(markW2)
		}
	})



	// 缩小
	$("#reduce").click(function() {
		if (x <= (5 + d)) {
			x++;
			$('.ac div:nth-child(2) img').each(function() {
				var height = $(this).height();
				var width = $(this).width();
				var borderWidth = parseInt($(this).css('border-width'));
				if(borderWidth!=0){
					height = $(this).height()+2;
					width = $(this).width()+2;
				}
				var scalImage = scalScPx(width, height, 0.95);
//				$(this).css("width", scalImage.width);
//				$(this).css("height", scalImage.height);
				setImageAttr( scalImage.width,scalImage.height,this);

				$(this).parent().width(this.getBoundingClientRect().width)
				setTimeout(wys, 1000);
				// setTimeout(felxH,500);
			});
		}

		// delete by tangtao 2018/11/23
		// 保存样式
		var color = document.getElementById('range').value;
		$.post('/chromosome/chAnalysis/saveStatus', {
			'statusId' : statusId,
			'magnifyTag' : d,
			'shrinkTag' : x,
			'colorTag' : color
		}, function(result) {
			if (result > 0) {
				// alert("保存样式成功");
			} else {
				alert("修改样式失败");
			}
		});
		clearAll();
		
		
	});
	// 放大
	$("#amplify").click(function() {
		var sumOutHeight = sumOutFlexHeight();
		if (sumOutHeight) {
			return;
		}

		if (d <= (5 + x)) {
			d++;
			$('.ac div:nth-child(2) img').each(function() {
				var height = $(this).height();
				var width = $(this).width();
				var borderWidth = parseInt($(this).css('border-width'));
				if(borderWidth!=0){
					height = $(this).height()+2;
					width = $(this).width()+2;
				}
				var scalImage = scalAddPx(width, height, 0.95);
//				$(this).css("width", scalImage.width);
//				$(this).css("height", scalImage.height);
				setImageAttr( scalImage.width,scalImage.height,this);
				$(this).parent().width(this.getBoundingClientRect().width)
				setTimeout(wys, 1000);
			});
		}
		
		// 保存样式
		var color = document.getElementById('range').value;
		$.post('/chromosome/chAnalysis/saveStatus', {
			'statusId' : statusId,
			'magnifyTag' : d,
			'shrinkTag' : x,
			'colorTag' : color
		}, function(result) {
			if (result > 0) {
				// alert("保存样式成功");
			} else {
				alert("修改样式失败");
			}
		});
		clearAll();
		
	});

	// 返回
	$("#retrun1").click(function() {
		if(isWaitBack){
			layer.msg('正在保存', {
				   icon: 16
				  ,shade: 0.01
				  ,time:500
				}, function(){
					window.location.href = "/chromosome/page/jsp/caryogram/operation/caryindex_new.jsp"
				});
		}else{
			window.location.href = "/chromosome/page/jsp/caryogram/operation/caryindex_new.jsp"
		}
	});

	// 分割
	$("#cut_apart")
			.click(
					function() {
						window.location.href = "/chromosome/page/jsp/caryogram/operation/crayexcision.jsp";
					});

	// 核型一览
	$("#general_survey").click(function() {
						//console.log(a10)
						window.location.href = "/chromosome/chAnalysis/skipcaryview?a10="
								+ a10;
					});
	
	//点击确定按钮之前生成报告按钮不能点击
	$("#report").attr("disabled",true)
	$("#report").css({'color':'#666'})

	// 确定按钮
	
	$("#ascertain1").mousedown(function(){
			$(this).css({'background':'#59ceff','outline':'none','color':'#fff'})
	});
	$("#ascertain1").mouseup(function(){
		$(this).css({'background':'#424041','outline':'none','color':'#fff'})
	});
	
	/* 是否点击过确定按钮	xzl	*/
	var isMake = false;
	/*是否生成报告的状态*/
	var isWaitBack = false;
	$("#ascertain1").click(function() {
		var caryogramStatus = $(".order_number1").val().replace(/\s+/g,"")
		if(caryogramStatus !="" && caryogramStatus!=null){
			//$("#chScreenshot_copy").remove();
			// 保存样式
			var color = document.getElementById('range').value;
			isWaitBack = true;
			$.post('/chromosome/chAnalysis/saveStatus', {
				'statusId' : statusId,
				'magnifyTag' : d,
				'shrinkTag' : x,
				'colorTag' : color
			}, function(result) {
				if (result > 0) {
					// alert("保存样式成功");
				} else {
					alert("修改样式失败");
				}
			});
	
			// console.log(karyotype);
			//console.log("2");
			//调用截图函数
			var dataURL= chromAll("chScreenshot_copy",false);
			var dataURLs= chromAll("chScreenshot_copys",true);
			//console.log(dataURL+"\n ************************"+dataURLs);
			/*确定同步更新核型*/
			var ascertain1Caryogram = $(".order_number1").val();
			var event = $(".number1").text();
			var karyotype = $(".order_number1").val();
			$.ajax({
				url : '/chromosome/chAnalysis/ascertain1Update',
				type : 'post',
				data : {'ascertain1Caryogram' : ascertain1Caryogram,
				        'event' : event,
				        'karyotype' :karyotype
				},
				success : function(data) {
					console.log('更新事件核型成功');
				}
			});
			
			var grayId=$("#grayId").val();
			$.ajax({
				url : nodeUrl + '/generateReport',
				type : 'post',
				data : {
					grayId:grayId
				},
				success : function(data) {
					console.log('生成胎儿报告成功');
					isWaitBack = false;
			}})
			console.log("3");
			//更新核型
			$("#report").attr("disabled",false)
			$("#report").css({'color':'#fff'});
		}else{
			alert("核型不能为空");
		}
        isMake = true;
	});
	
	/**
	 * 染色图截图函数
	 * copyName 截图的id名称
	 * status 截图隐藏判断
	 * */
	function chromAll(copyName,status){
		console.log("图片截图函数触发");
		$(".analyse_Left1").append($("#chScreenshot").clone().addClass("chScreenshot_copy").attr("id",copyName));
		var scalPrint = getScaleHeight();
		var scalPx = 935 / scalPrint;
		$("#"+copyName+" .ac div[id^='mark'] img").each(function(index, item) {
			var id = item.id;
			var width = item.width == 0? $("#"+ id).attr("c_width") : item.width;
			var height = item.height == 0? $("#"+ id).attr("c_height") : item.height;
			if(!$("#"+ id).attr("c_width")){
				$("#"+ id).attr("c_width",width)
			}
			if(!$("#"+ id).attr("c_height")){
				$("#"+ id).attr("c_height",height)
			}
			var scalImage = reportImage(parseInt(width), parseInt(height),scalPx);
			if (scalImage) {
				$(this).css("width", scalImage.width);
				$(this).css("height", scalImage.height);
				$(this).parent().width(
						this.getBoundingClientRect().width)
			}
		});
		if(status){
			console.log("性染色体隐藏");
			$("#"+copyName+" #mark23").css("display", "none");
			$("#"+copyName+" #mark24").css("display", "none");
		}
		console.log("图片截图函数copy完毕");
		html2canvas($('#'+ copyName +" :last-child"), {
			//allowTaint : true, // 允许污染
			taintTest : true, // 在渲染前测试图片(没整明白有啥用)
			useCORS : true, // 使用跨域(当allowTaint为true时这段代码没什么用,下面解释)
			background : "#fff",
			onrendered : function(canvas) {
				//console.log("1");
				canvas.id = "mycanvas";
				// document.body.appendChild(canvas);
				// 生成base64图片数据
				var dataURL = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
//				console.log('截图的数据'+dataURL);
				$.ajax({
					url : '/chromosome/chAnalysis/chromConfirmOne',
					type : 'post',
					data : JSON.stringify({
						'url' : dataURL,
						'status' : status
					}),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						return true;
					}
				});
				//更新核型
				isAscertain1 = true;
				
				if(isAscertain1 == true){
					$("#report").attr("disabled",false);
					$("#report").css({'color':'#fff'});
				}
			}
		});
	}
	function cutDiv2() {
		var divContent = document.getElementById("mark1").innerHTML;
		var data = "data:image/svg+xml,"
				+ "<svg xmlns='http://www.w3.org/2000/svg' width='200' height='200'>"
				+ "<foreignObject width='100%' height='100%'>"
				+ "<div xmlns='http://www.w3.org/1999/xhtml' style='font-size:16px;font-family:Helvetica'>"
				+ divContent + "</div>" + "</foreignObject>" + "</svg>";
		var img = new Image();
		img.src = data;
		var canvas = document.createElement("canvas");
		var ctx = canvas.getContext("2d");
		img.crossOrigin = "";
		img.src = "data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg'></svg>";
		ctx.drawImage(img, 0, 0);
		canvasbase = canvas.toDataURL();
		alert(canvasbase);
	}

	// 点击按钮数据弹出文件卡
	$("#event_date").click(function() {
		$(".file_card").css("display", "block");
		//清空数据
		$("#yourformid").find("input").val("");
		$.ajax({
			url : '/chromosome/chCount/folderInit2',
			type : 'post',
			data : {'isMake':isMake},
			dataType : "json",
			success : function(js) {
				console.log(js);// begin
				var data = js[1];
				if(js[0]){
					console.log("A事件隐藏");
					//病历号
					$("#medical_record").attr('disabled','disabled')
					$("#medical_record").css({'opacity': '0.7'})
					//姓名
					$("#name").attr('disabled','disabled')
					$("#name").css({'opacity': '0.7'})
					//年龄
					$("#age").attr('disabled','disabled')
					$("#age").css({'opacity': '0.7'})
					//性别
					$("#sex").attr('disabled','disabled')
					$("#sex").css({'opacity': '0.7'})
					//孕龄周
					$("#pregnancy").attr('disabled','disabled');
					$("#pregnancy").css({'opacity': '0.7'});
					//孕龄天
					$("#pregnancys").attr('disabled','disabled');
					$("#pregnancys").css({'opacity': '0.7'});
					//临床诊断
					$("#diagnose").attr('disabled','disabled');
					$("#diagnose1").attr('disabled','disabled');
					$("#diagnose").css({'opacity': '0'});
					$("#diagnose1").css('background','#CDCDCD');
					
				}else{
					//病历号
					$("#medical_record").removeAttr('disabled')
					$("#medical_record").css({'opacity': '1'})
					//姓名
					$("#name").removeAttr('disabled')
					$("#name").css({'opacity': '1'})
					//年龄
					$("#age").removeAttr('disabled')
					$("#age").css({'opacity': '1'})
					//性别
					$("#sex").removeAttr('disabled')
					$("#sex").css({'opacity': '1'})
					//孕龄周
					$("#pregnancy").removeAttr('disabled')
					$("#pregnancy").css({'opacity': '1'}) 
					//孕龄天
					$("#pregnancys").removeAttr('disabled')
					$("#pregnancys").css({'opacity': '1'})
					//临床诊断
					$("#diagnose").removeAttr('disabled')
					$("#diagnose1").removeAttr('disabled')
					$("#diagnose").css({'opacity': '1'})
					$("#diagnose1").css('background','#fff')
					//病历号
					if(data.medicalRecord!=null) $("#medical_record").val(data.medicalRecord);
					//姓名
					if(data.name!=null) $("#name").val(data.name);
					//年龄
					if(data.age!=null) $("#age").val(data.age);
					//性别
					if(data.sex!=null) $("#sex").val(data.sex);
					//孕龄周
					if(data.pregnancy!=null) $("#pregnancy").val(data.pregnancy);
					//孕龄天
					if(data.pregnancys!=null) $("#pregnancys").val(data.pregnancys);
					//临床诊断
					if(data.diagnose!=null) $("#diagnose").val(data.diagnose);
				}
				//事件卡
				if(data.evenNumber!=null) $("#even_number").val(data.evenNumber);
				//样本来源
				if(data.specimenSource!=null) $("#specimen_source").val(data.specimenSource); 
				//采集日期
				if(data.gatherDate!=null) $("#gather_date").val(data.gatherDate);
				//接种日期
				if(data.vaccinateDate!=null) $("#vaccinate_date").val(data.vaccinateDate);
				//意见
				if(data.suggestion!=null) $("#suggestion").val(data.suggestion);
				//制片人
				if(data.theProducers!=null) $("#the_producers").val(data.theProducers);
				//阅片人及打印人
				if(data.putAsealOn!=null) $("#put_a_seal_on").val(data.putAsealOn);
				//校片人
				if(data.examineAndNucleus!=null) $("#examine_and_nucleus").val(data.examineAndNucleus);
				//审核人
				if(data.examineAndVerify!=null) $("#examine_and_verify").val(data.examineAndVerify);
				//核对
				if(data.check!=null) $("#check").val(data.check);
				//报告日期
				if(data.reportDate!=null) $("#report_date").val(data.reportDate);
				//中期
				if(data.whole!=null) $("#whole").val(data.whole);
				//核型
				if(data.caryogram!=null) {
                    $("#caryogram").val(data.caryogram);
				}else {
					var v = $(".order_number1").val();
					console.log(".order_number1 3v:"+v);
                    $("#caryogram").val(v);
				}
			}
		});
						
//										var list1 = data[0];
//										var list2 = data[1];
//										//最新的核型放置事件卡数据中
//                                       	if(isAscertain1==true) {
//                                            list2[0].caryogram = $(".order_number1").val();
//                                        }
//                                       	
//										
//										document.getElementById('even_number').value = list2[0].evenNumber;
//										
//										document.getElementById('medical_record').value = list2[0].medicalRecord;
//										document.getElementById('name').value = list2[0].name;
//										document.getElementById('age').value = list2[0].age;
//										if(list2[0].sex==0){
//											document.getElementById('sex').value = "男";
//										}else if(list2[0].sex==1){
//											document.getElementById('sex').value = "女";
//										}
//										document.getElementById('gather_date').value = list2[0].gatherDate;
//										
//										
//										if (inNamesss.split("P").length - 1 > 0) {
//											document
//													.getElementById('specimen_source').value = "羊水";
//										} else {
//											document
//													.getElementById('specimen_source').value = "外周血";
//										}
//
//										document
//												.getElementById('vaccinate_date').value = list2[0].vaccinateDate;
//										document.getElementById('diagnose').value = list2[0].diagnose;
//										
						/*if (list2[0].suggestion == null
								|| list2[0].suggestion == "") {											
							if (list2[0].caryogram == "46,XX"
									|| list2[0].caryogram == "46,XY") {
								if (grays.length > 3) {
									document
											.getElementById('suggestion').value = "高分辨G带未见异常";
								}else{
									document
									.getElementById('suggestion').value = "普通G带未见异常";
								}
							}
							if (list2[0].caryogram
									.search("der") != -1 ||list2[0].caryogram
									.search("rob") != -1) {
								document
										.getElementById('suggestion').value = "为罗氏易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram == "47,XXY") {
								document
										.getElementById('suggestion').value = "为克氏征 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram.split("+21").length - 1 > 0) {
								document
										.getElementById('suggestion').value = "为21三体综合征 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram
									.split("t()").length > 1) {
								document
										.getElementById('suggestion').value = "为平衡易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门";
							}
							if (list2[0].caryogram
									.search("mos") != -1) {
								document
										.getElementById('suggestion').value = "为嵌合体 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram
									.search("46,XX")
									|| list2[0].caryogram
											.search("46,XY")) {
								if (list2[0].caryogram
										.split("inv(9)").length - 1 > 0) {
									document
											.getElementById('suggestion').value = "为9号染色体次缢痕区臂间倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》";
								}
							}
							if (list2[0].caryogram
									.search("46,XX")
									|| list2[0].caryogram
											.search("46,XY")) {
								if (list2[0].caryogram
										.split("inv(Y)").length - 1 > 0) {
									document
											.getElementById('suggestion').value = "为Y染色体倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》";
								}
							}
							if (list2[0].caryogram
									.search("47,XY") != -1
									&& list2[0].caryogram
											.search("mar") != -1) {
								document
										.getElementById('suggestion').value = "为47，XY，+mar 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram
									.search("47,XX") != -1
									&& list2[0].caryogram
											.search("mar") != -1) {
								document
										.getElementById('suggestion').value = "为47,XX,+mar 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram
									.search("15p+") != -1) {
								document
										.getElementById('suggestion').value = "为15p+ 建议行FISH做进一步检测";
							}
							if (list2[0].caryogram
									.search("mat") != -1) {
								document
										.getElementById('suggestion').value = "为母源性平衡易位 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram
									.search("47,XYY") != -1) {
								document
										.getElementById('suggestion').value = "为47，XYY 建议来我院看遗传咨询专家门诊";
							}
							if (list2[0].caryogram
									.search("46,XX") != -1
									|| list2[0].caryogram
											.search("46,XY") != -1) {
								if (list2[0].caryogram
										.search("DSD") != -1) {
									document
											.getElementById('suggestion').value = "为DSD（性发育异常） 建议来我院看遗传咨询专家门诊";
								}
							}
							if (list2[0].caryogram
									.search("46,XX") != -1
									|| list2[0].caryogram
											.search("46,XY") != -1) {
								if (list2[0].caryogram
										.split("t(").length >1) {
									if (list2[0].caryogram
											.split(";").length >1) {
										document
												.getElementById('suggestion').value = "为复杂易位携带者 建议行PGD-CCS 建议来我院看遗传咨询专家门诊";
									}
								}else{
									document
									.getElementById('suggestion').value = "高分辨G带未见异常";
								}
							}
							if (list2[0].caryogram == "46,XX"
								|| list2[0].caryogram == "46,XY") {
							if (grays.search("h") != -1||grays.search("H") != -1) {
								document
										.getElementById('suggestion').value = "高分辨G带未见异常";
							}else{
								document
								.getElementById('suggestion').value = "普通G带未见异常";
							}
						}
						}*/ 
						/*if (list2[0].suggestion == null
								|| list2[0].suggestion == "") {*/											
//											if (list2[0].caryogram == "46,XX"|| list2[0].caryogram == "46,XY") {
//											if (grays.search("h") != -1||grays.search("H") != -1) {
//												document.getElementById('suggestion').value = "高分辨G带未见异常";
//											}else{
//												document.getElementById('suggestion').value = "普通G带未见异常";
//											}
//										}
//											if (list2[0].caryogram.search("der") != -1 ||list2[0].caryogram.search("rob") != -1) {
//												document.getElementById('suggestion').value = "为罗氏易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门诊";
//											}
//											if (list2[0].caryogram == "47,XXY") {
//												document.getElementById('suggestion').value = "为克氏征 建议来我院看遗传咨询专家门诊";
//											}
//											if (list2[0].caryogram.split("+21").length - 1 > 0) {
//												document.getElementById('suggestion').value = "为21三体综合征 建议来我院看遗传咨询专家门诊";
//											}
//											
//											if (list2[0].caryogram.search("mos") != -1) {
//												document.getElementById('suggestion').value = "为嵌合体 建议来我院看遗传咨询专家门诊";
//											}
//												if (list2[0].caryogram.split("inv(9)").length - 1 > 0) {
//													document.getElementById('suggestion').value = "为9号染色体次缢痕区臂间倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》";
//												}
//												if (list2[0].caryogram.split("inv(Y)").length - 1 > 0) {
//													document.getElementById('suggestion').value = "为Y染色体倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》";
//												}
//											if (list2[0].caryogram.search("47,XY") != -1&& list2[0].caryogram.search("mar") != -1) {
//												document.getElementById('suggestion').value = "为47，XY，+mar 建议来我院看遗传咨询专家门诊";
//											}
//											if (list2[0].caryogram.search("47,XX") != -1
//													&& list2[0].caryogram.search("mar") != -1) {
//												document.getElementById('suggestion').value = "为47,XX,+mar 建议来我院看遗传咨询专家门诊";
//											}
//											if (list2[0].caryogram.search("15p+") != -1) {
//												document.getElementById('suggestion').value = "为15p+ 建议行FISH做进一步检测";
//											}
//											if (list2[0].caryogram.search("mat") != -1) {
//												document.getElementById('suggestion').value = "为母源性平衡易位 建议来我院看遗传咨询专家门诊";
//											}
//											if (list2[0].caryogram.search("47,XYY") != -1) {
//												document.getElementById('suggestion').value = "为47，XYY 建议来我院看遗传咨询专家门诊";
//											}
////											if (list2[0].caryogram.search("46,XX") != -1|| list2[0].caryogram.search("46,XY") != -1) {
////												if (list2[0].caryogram.split("t(").length >1) {
////													if (list2[0].caryogram.split(";").length >2) {
////														document.getElementById('suggestion').value = "为复杂易位携带者 建议行PGD-CCS 建议来我院看遗传咨询专家门诊";//
////													}
////													
////												}
////												if (list2[0].caryogram.search("DSD") != -1) {
////													document.getElementById('suggestion').value = "为DSD（性发育异常） 建议来我院看遗传咨询专家门诊";
////												}
////											}//保留1
////											
////											if (list2[0].caryogram.split("t(").length > 1 && list2[0].caryogram.split(";").length == 2) {
////												document.getElementById('suggestion').value = "为平衡易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门";//平衡易位
////											}//保留2
//											
//											if (list2[0].caryogram.search("DSD") != -1) {
//												document.getElementById('suggestion').value = "为DSD（性发育异常） 建议来我院看遗传咨询专家门诊";
//											}
//											
//											if((list2[0].caryogram.split("t(")).length-1==1){
//												document.getElementById('suggestion').value = "为平衡易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门";
//										    }    // 1
//											
//											if(list2[0].caryogram.split("t(",2).length > 1 && list2[0].caryogram.split(";").length > 1){
//												document.getElementById('suggestion').value = "为复杂易位携带者 建议行PGD-CCS 建议来我院看遗传咨询专家门诊";
//										    }    // 2
//											
//										    if (list2[0].caryogram.split("t(").length >1) {
//													document.getElementById('suggestion').value = "为复杂易位携带者 建议行PGD-CCS 建议来我院看遗传咨询专家门诊";
//											}    // 3
							
							
							
						
							
						/*}else {
							document
									.getElementById('suggestion').value = list2[0].suggestion;
						}*/
//										document.getElementById('pregnancy').value = list2[0].pregnancy;
//										var str1 = list2[0].pregnancys;
//										if(str1.indexOf("+")==-1){
//											document.getElementById('pregnancys').value = str1;
//										}else{
//											var str = str1.split("+");
//											document.getElementById('pregnancys').value = str[1];
//										}
//										document.getElementById('the_producers').value = list2[0].theProducers;
//										document.getElementById('put_a_seal_on').value = list2[0].putAsealOn;
//										document.getElementById('examine_and_nucleus').value = list2[0].examineAndNucleus;
//										document.getElementById('examine_and_verify').value = list2[0].examineAndVerify;
//										if (list2[0].reportDate == null|| list2[0].reportDate == "") {
//											document.getElementById('report_date').value = currentdate;
//										} else {
//											document.getElementById('report_date').value = list2[0].reportDate;
//										}
//										document.getElementById('check').value = list2[0].check;
//										document.getElementById('whole').value = list2[0].whole;
//										document.getElementById('caryogram').value = list2[0].caryogram;// stop
	});


	// 鼠标右键上下移动图片
	var mouseDownY, mouseMoveY, flag, y, top = false;
	var count = 0;
	$(".ac div:nth-child(2)")
			.on(
					"mousedown",
					"img",
					function(e) {
						mouseDownY = e.clientY;
						flag = true;
						var imghei = $(this).height();
						var hei = $(this).parent().parent().parent().parent().parent().height()-19;
						var imgobj = this;
						imgobj.isBase64 = false;
						var ids = $(this).attr("id");
						e.preventDefault();
						if (e.button == 2) {
							top = $(this).css("top");
						/*	if ($(this).attr("style").indexOf("transform") == -1) {*/
								$(this).on("mousemove",function(ev) {
													count = 1;
													if (flag) {
														mouseMoveY = ev.clientY;
														var rota = parseInt(parseFloat(top)
																+ mouseMoveY
																- mouseDownY);
														var rota1 = "-"
																+ (hei - imghei);
														if (rota < rota1) {
															rota = imghei - hei;
														}
														if (rota > ($(imgobj)
																.parent()
																.height() / 2 - parseInt(imgobj
																.getBoundingClientRect().height) / 2)) {
															rota = ($(imgobj)
																	.parent()
																	.height() / 2 - parseInt(imgobj
																	.getBoundingClientRect().height) / 2);
														}
														$(this).css({
															top : rota
														});
													}
												});
				/*			} else {
								$(this)
										.on(
												"mousemove",
												function(ev) {
													count = 1;
													if (flag) {
														mouseMoveY = ev.clientY;
														var rota = parseInt(parseFloat(top)
																+ mouseMoveY
																- mouseDownY);
														var rota1 = "-"
																+ (hei - imghei);
														if (rota < rota1) {
															rota = imghei - hei;
														}
														if (rota > 50 + ($(
																imgobj)
																.parent()
																.height() / 2 - parseInt(imgobj
																.getBoundingClientRect().height) / 2)) {
															rota = 50 + ($(
																	imgobj)
																	.parent()
																	.height() / 2 - parseInt(imgobj
																	.getBoundingClientRect().height) / 2);
														}
														$(this).css({
															top : rota
														});
													}
												});
							}*/

						}
						$(".ac div:nth-child(2)")
								.on(
										"mouseout",
										"img",
										function() {

											if (count == 1) {
												flag = false;
												var top1 = $(this).css("top");
												$
														.post(
																"/chromosome/chAnalysis/chAlterTopDown",
																{
																	"chromId" : ids,
																	"topDown" : parseInt(top1)
																}, function(
																		data) {
																});

											}
											count = 0;
											$(this).unbind("mousemove");

										});
						// 鼠標鬆開
						$(this).on("mouseup", function() {
							var top1 = $(this).css("top");
							$.post("/chromosome/chAnalysis/chAlterTopDown", {
								"chromId" : ids,
								"topDown" : parseInt(top1)
							}, function(data) {
							});
							y = mouseMoveY;
							$(this).unbind("mousemove");
							flag = false;
							count = 0;
						});
					});

	// 找到最大的父级
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
	// keydown事件
	var speed = 1;
	$(document).keydown(function(event) {
		if (event.keyCode == 107) {
			speed = speed + 0.1;
			console.log(speed)
			if (speed > 2.5) {
				speed = 2.5
			}
		}
		if (event.keyCode == 109) {
			speed = speed - 0.1;
			console.log(speed)
			if (speed < 0.2) {
				speed = 0.2
			}
		}
	})
	
	
	
	// 鼠标长按图片旋转
	var preX, preY;// 上一次鼠标点的坐标
	var curX, curY;// 本次鼠标点的坐标
	var preAngle;// 上一次鼠标点与圆心(150,150)的X轴形成的角度(弧度单位)
	var transferAngle;// 当前鼠标点与上一次preAngle之间变化的角度
	var aq = 0;// aq是用来保存旋转的角度
	var aq1 = 0;// aq1是用来保存上一次aq的旋转角度
	var curAngle;
	var cont = -1;
	var rotateCount = 0;
	var cdeg =0;//
	 var count6 = 0;
	// 点击事件
	$(".ac div:nth-child(2)").on("mousedown","img",function(event) {
						if (event.button == 0) {
							event.preventDefault();
							clearTimeout(timer);
							var ids = $(this).attr("id");
							var imgsrc = $(this);
							imgobj = this;
							var top1 = 0;
							
							// var imgss= document.querySelector("#"+ids);
							elementParent(this);
							preX = event.clientX;
							preY = event.clientY;
							//获得之前旋转的度数.
							var imgobjid = $(imgobj).attr('id')
							var afterdeg = 0;
							console.log($('#'+imgobjid))
								var isStyle = $('#'+imgobjid).attr('style');
								if(isStyle && isStyle.indexOf("rotate(180deg)") == -1){
									afterdeg = 0;
								}else{
									afterdeg = 180;
								}

                            
							// 延迟函数
							timer = setTimeout(function() {
								        console.log("123")
										clearAll();
								        count6 = 1;
										z = "";
										preAngle = Math.atan2(preY, preX);
										choseimg = '';
										scend = '';
										$("html").mouseup(function() {
											console.log(count6);
											if(count6==1){
												console.log('没有移动就触发')
												$("html").unbind("mousemove");
												$("img").css("border","none");
												clearTimeout(timer);
												$("img").removeClass("a");
												a6 = "";
												num1 = 0;
												a8 = null;
												chromid = null;
												chromnum = null;
												image = null
												choseimg = '';
												count6 = 0;
											}
										
											
										});
										// 鼠标移动事件
										$("html").mousemove(function(event) {
															console.log('移动就触发')
															clearAll();
															choseimg = ''
															var h = $(imgobj).parent().height();
															// 移动时外切div变化
															top1 = parseInt($(imgobj).parent().height()/ 2- parseInt(imgobj.getBoundingClientRect().height / 2))
															$(imgobj).parent().width(parseInt(imgobj.getBoundingClientRect().width));
															$(imgobj).css({
																'top' : $(imgobj).parent().height()/ 2- parseInt(imgobj.getBoundingClientRect().height / 2)
																			})
															cont = 5;
															// 鼠标移动是的坐标
															curX = event.clientX;
															curY = event.clientY;
															// 鼠标移动的距离
															var distance = Math.sqrt((curY - preY)* (curY - preY)+ (curX - preX)* (curX - preX));
															// 正为顺时针，负为逆时针
															var flag = 1;
															if (curX > preX) {
																flag = -1;
															} else {
																flag = 1;
															}
															aq = (flag)* ((distance / (2 * h)) * 360 / Math.PI)* speed;
															
															cdeg += aq;
															if(!cdeg){
																console.log("解除")
																$("html").unbind("mousemove");$("html").unbind("mouseup");
																$("img").css('border','none')
																cdeg=0;
																return;
															}
															console.log(parseFloat(cdeg + afterdeg));
															rotateCount = parseFloat(cdeg + afterdeg).toFixed(2);
															console.log(rotateCount)
															
															if (rotateCount >= 361 || rotateCount <= -361) {
																rotateCount = 0;
															}
															aq1 = rotateCount;
													
														
															imgsrc.css({"transform" : "rotate("+ rotateCount +"deg)"});
															preX = curX;
															preY = curY;
															preAngle = curAngle;
															$("img").removeClass("a");
															a6 = "";
															num1 = 0;
															a8 = null;
															chromid = null;
															chromnum = null;
															image = null
															
															// 这个html的鼠标松开事件（是用来每次鼠标按下时
															// 鼠标松开就清空边框）
															$("html").mouseup(function() {
																/*$("img").css("border","none");
																$("html").unbind("mousemove");
																clearTimeout(timer);
																$(imgobj).parent().width(imgobj.getBoundingClientRect().width);
																// $(imgobj).parent().height(imgobj.getBoundingClientRect().height)*/
																if(imgobj.isBase64){
																	return;
																}
																$("img").css("border","none");
																$("html").unbind("mousemove");
																clearTimeout(timer);
																//$(imgobj).parent().width(imgobj.getBoundingClientRect().width);
																// update by tangtao 2018/11/12
																let imagId = $(imgobj).attr("id");
																let currentImage = $("#"+imagId);
																let roateImageWidth = imgobj.getBoundingClientRect().width;
																let roateImageHeight = imgobj.getBoundingClientRect().height;
																choseimg = ''
																if(!rotateCount){
																	imgobj.isBase64 = true;
																	return ;
																}
																roateImage(currentImage[0],roateImageWidth,roateImageHeight,rotateCount);
																cdeg = 0;
//																console.log(rotateCount);
																var type = "image/bmp"
														        var data = document.getElementById("dstcanvasOutput").toDataURL(type);
														        //data = data.replace('data:' + type + ';base64,', ''); //split off junk at the beginning
														        
														        var isBaseImage = '<img class="checked" src="'+data+'" crossorigin="" id="'+imagId+'">';
														        $(imgobj).parent().append(isBaseImage);
														        $(imgobj).remove();
														        //$("#"+imagId).attr("src",data);
														        var baseid = $(isBaseImage).attr('id');
														        $('#'+baseid).parent().css('width','');

														        $.ajax({
																	url : '/chromosome/chAnalysis/chCircumX',
																	type : 'post',
																	data : JSON.stringify({
																		'id' : ids,
																		'data' : data
																	}),
																	dataType : "json",
																	contentType : "application/json;charset=utf-8",
																	success : function(
																			data) {
																	}
																});
														        
														        $("html").unbind("mouseup");
														        imgobj.isBase64 = true;
																// $(imgobj).parent().height(imgobj.getBoundingClientRect().height)

																	
															});
														});
									}, 200);

							// html的鼠标松开事件 ajax发送保存的数据到数据库 且清空变量
							$("html").mouseup(function() {

												if (cont > 0) {
												/*	$
															.ajax({
																url : '/chromosome/chAnalysis/chCircumX',
																type : 'post',
																data : {
																	aq1 : aq1,
																	ids : ids,
																	"top" : top1
																},
																dataType : "json",
																success : function(
																		data) {
																}
															});*/
													aq = 0;
													cont = -1;
												}

												preX = undefined;
												preY = undefined;
												preAngle = undefined;
												transferAngle = undefined;
												curAngle = undefined;
												$("html").unbind("mouseup");
											});

							// 鼠标松开后发送保存的旋转x度角度后的数据到数据库保存
							imgsrc.mouseup(function() {

										if (cont > 0) {
										/*	$
													.ajax({
														url : '/chromosome/chAnalysis/chCircumX',
														type : 'post',
														data : {
															aq1 : aq1,
															ids : ids,
															"top" : top1
														},
														dataType : "json",
														success : function(data) {
														}
													});*/
											cont = -1;
										}

										preX = undefined;
										preY = undefined;
										preAngle = undefined;
										transferAngle = undefined;
										curAngle = undefined;
									});
						}
					});

	
	
	
	
/*	// 鼠标长按图片旋转
	var preX, preY;// 上一次鼠标点的坐标
	var curX, curY;// 本次鼠标点的坐标
	var preAngle;// 上一次鼠标点与圆心(150,150)的X轴形成的角度(弧度单位)
	var transferAngle;// 当前鼠标点与上一次preAngle之间变化的角度
	var aq = 0;// aq是用来保存旋转的角度
	var aq1 = 0;// aq1是用来保存上一次aq的旋转角度
	var curAngle;
	var cont = -1;
	var count = 0;
	// 点击事件
	$(".ac div:nth-child(2)").on("mousedown","img",
					function(event) {
						if (event.button == 0) {
							event.preventDefault();
							clearTimeout(timer);
							var ids = $(this).attr("id");
							var imgsrc = $(this);
							imgobj = this;
							imgobj.isBase64 = false;
							var top1 = 0;
							preX = event.clientX;
							preY = event.clientY;
							// var imgss= document.querySelector("#"+ids);
							elementParent(this);
							// 延迟函数
							timer = setTimeout(function() {
								
								   console.log("kai shi xuan zhuan")
										z = "";
										preAngle = Math.atan2(preY, preX);
										$("html").mouseup(function() {
											$("img").css("border","none");
											$("html").unbind("mousemove");
											clearTimeout(timer);
											$(imgobj).parent().width(imgobj.getBoundingClientRect().width);
										});
//										var cont = 0;
										// 鼠标移动事件
										$("html").mousemove(function(event) {
															var h = $(imgobj).parent().height();
															// 移动时外切div变化
															top1 = parseInt($(imgobj).parent().height()/ 2- parseInt(imgobj.getBoundingClientRect().height / 2))
															$(imgobj).parent().width(parseInt(imgobj.getBoundingClientRect().width));
															$(imgobj).css({'top' : $(imgobj).parent().height()/ 2- parseInt(imgobj.getBoundingClientRect().height / 2)
																			})
															cont = 5;
															// 鼠标移动是的坐标
															curX = event.clientX;
															curY = event.clientY;
															// 鼠标移动的距离
															var distance = Math.sqrt((curY - preY)* (curY - preY)+ (curX - preX)* (curX - preX));
															// 正为顺时针，负为逆时针
															var flag = 1;
															if (curX > preX) {
																flag = -1;
															} else {
																flag = 1;
															}
															aq = (flag)* ((distance / (2 * h)) * 360 / Math.PI)* speed;
															
                                                            console.log("aq:"+aq);
															count += aq;
														
															if (count >= 361|| count <= -361) {
																count = 0;
															}
															aq1 = count;
															
															
															imgsrc.css({"transform" : "rotate("+ count+ "deg)"});
															preX = curX;
															preY = curY;
															preAngle = curAngle;
															$("img").removeClass("a");
															a6 = "";
															num1 = 0;
															a8 = null;
															chromid = null;
															chromnum = null;
															image = null;
															var ccc = 0;
															// 这个html的鼠标松开事件（是用来每次鼠标按下时
															// 鼠标松开就清空边框）
															$("html").mouseup(function() {
																if(imgobj.isBase64){
																	return;
																}
																$("img").css("border","none");
																$("html").unbind("mousemove");
																clearTimeout(timer);
																//$(imgobj).parent().width(imgobj.getBoundingClientRect().width);
																// update by tangtao 2018/11/12
																let imagId = $(imgobj).attr("id");
																let currentImage = $("#"+imagId);
																let roateImageWidth = imgobj.getBoundingClientRect().width;
																let roateImageHeight = imgobj.getBoundingClientRect().height;
																roateImage(currentImage[0],roateImageWidth,roateImageHeight,count);
																 var type = "image/bmp"
														        var data = document.getElementById("dstcanvasOutput").toDataURL(type);
														        //data = data.replace('data:' + type + ';base64,', ''); //split off junk at the beginning
														        
														        var isBaseImage = '<img src="'+data+'" crossorigin="" id="'+imagId+'">';
														        $(imgobj).parent().append(isBaseImage);
														        $(imgobj).remove();
														        //$("#"+imagId).attr("src",data);
																 imgobj.isBase64 = true;
																// $(imgobj).parent().height(imgobj.getBoundingClientRect().height)

															});
														});
									}, 200);

							// html的鼠标松开事件 ajax发送保存的数据到数据库 且清空变量
							$("html").mouseup(function() {

												if (cont > 0) {
													$.ajax({
																url : '/chromosome/chAnalysis/chCircumX',
																type : 'post',
																data : {
																	aq1 : aq1,
																	ids : ids,
																	"top" : top1
																},
																dataType : "json",
																success : function(
																		data) {
																}
															});
													aq = 0;
													cont = -1;
												}

												preX = undefined;
												preY = undefined;
												preAngle = undefined;
												transferAngle = undefined;
												curAngle = undefined;
												$("html").unbind("mouseup");
											});

							// 鼠标松开后发送保存的旋转x度角度后的数据到数据库保存
							imgsrc.mouseup(function() {
								$("html").unbind("mousemove");
										if (cont > 0) {
											$.ajax({
														url : '/chromosome/chAnalysis/chCircumX',
														type : 'post',
														data : {
															aq1 : aq1,
															ids : ids,
															"top" : top1
														},
														dataType : "json",
														success : function(data) {
														}
													});
											cont = -1;
										}

										preX = undefined;
										preY = undefined;
										preAngle = undefined;
										transferAngle = undefined;
										curAngle = undefined;
									});
						}
					});*/

	// 左键生成报告
	$("#report").click(function(e) {
		var inName = $(".number1").text();
		var report = 0;
		reportProduce(report);
//		var grays = $(".id1").text();
//		$.ajax({
//			url : 'romosomeCount/folderInit1',
//			type : 'post',
//			data : {
//				'report' : 0
//			},
//			dataType : "json",
//			success : function(data) {
//				var list1 = data[0];
//				var list2 = data[1];
//				var sexFinal = list1[4];
//				var caryogramFinal = list2[0].caryogram;
//				if (list2[0].suggestion == null || list2[0].suggestion == "") {
//					alert("意见为空，请先选择!");
//				} else {
//					if(list2[0].pregnancy!=""||inName.split("P").length - 1 > 0){
//						report = 4;
//					}else if(grays.split("h").length - 1 > 0
//							|| grays.split("H").length - 1 > 0
//							|| list2[0].suggestion == "高分辨G带未见异常") {
//							report = 2;													
//					} else if (list2[0].suggestion == "为平衡易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门"
//							|| list2[0].suggestion == "为嵌合体 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为47，XY，+mar 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为47,XX,+mar 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为母源性平衡易位 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为DSD（性发育异常） 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为复杂易位携带者 建议行PGD-CCS 建议来我院看遗传咨询专家门诊") {
//						report = 3;
//					}else if(list2[0].suggestion == "普通G带未见异常"
//							|| list2[0].suggestion == "为罗氏易位携带者 可考虑行FISH-PGD或PGD-CCS 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为克氏征 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为21三体综合征 建议来我院看遗传咨询专家门诊"
//							|| list2[0].suggestion == "为9号染色体次缢痕区臂间倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》"
//							|| list2[0].suggestion == "为Y染色体倒位，在临床上有争议，按《人类染色体命名 的国际体制（ISCN2013版）》"
//							|| list2[0].suggestion == "为15p+ 建议行FISH做进一步检测"
//							|| list2[0].suggestion == "为47，XYY 建议来我院看遗传咨询专家门诊"
//							) {
//						report = 1;
//					}
//					reportProduce(inName,report)
//				}
//			}
//		});
	});


	// 右键
	var qww = 0;
	$("#report").mousedown(function(e) {
		if (e.button == 2) {
			if (qww == 0) {
				qww++;
				$("#report_show1").css("display", "block");
				$(document).click(function(){
					$("#report_show1").css("display", "none");
				})
			} else {
				$("#report_show1").css("display", "none");
				qww = 0;
			}
		}
	});
	
	

//	var pageContext = document.getElementById('PageContext').value + "/pdf/";
//	var strFullPath = window.document.location.href;
//	var strPath = window.document.location.pathname;
//	var pos = strFullPath.indexOf(strPath);
//	var prePath = strFullPath.substring(0, pos);
//	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
//	var basePath = prePath;
//	basePath = prePath + postPath;

	$("#report1").click(function() {
		var report = 1;
		reportProduce(report)
	});
	$("#report2").click(function() {
		var report = 2;
		reportProduce(report)
	});
	$("#report3").click(function() {
		var report = 3;
		reportProduce(report)
	});
	$("#report4").click(function() {
		var report = 4;
		reportProduce(report);
	});
	//生成报告函数
	function reportProduce(report){
		$.ajax({
			url : '/chromosome/chCount/ChMatchKaryotypes',
			type : 'post',
			data : JSON.stringify({
				'report' : report
			}),
			dataType : "json",
			contentType : "application/json;charset=utf-8",
			success : function(data) {
				console.log(data);
				if(data[0]==5){
					$("#report_show1").css('display','none');
					alert("该核型匹配意见失败，请手动选择意见再选择模版生成报告");
					
					return false;
				}
				else if(data[0]==0){
					$("#report_show1").css('display','none');
					alert("该意见无法自动匹配报告，请选择模版生成报告 ");
					return false;
				}else{
					if (data[1]) {
						if (!confirm("该事件中的社会性别和染色体核型不一致，是否继续生成报告?")) {
							console.log(111)
							$("#report_show1").css('display','none');
							return false;
						}
					}
				}
				window.location.href="/chromosome/chCount/ChfolderTolocality?report="+data[0];
			}
		});
	}


	$(".analyse_btn").find("button").mousedown(function() {
		$(this).addClass("btncolor1")
	})
	$(".analyse_btn").find("button").mouseup(function() {
		$(this).removeClass("btncolor1")
	});
	
	
	// 左右快捷键
	$(document).keyup(function(event) {
		if (event.keyCode == 37) {
			if(zy==0){
			$.get('/chromosome/chromatid/grayFoWordQuerys1',{'v' : 'up'},
							function(data) {
								console.log(data)
								if (data == 'success1') {
									location.reload();
								} else if (data == 'success') {
									window.location.href = "/chromosome/page/jsp/caryogram/operation/crayexcision.jsp"
								} else {
									alert("已是第一页或最后一页");
								}
							});
			}
		} else if (event.keyCode == 39) {
			if(zy==0){
			$.get('/chromosome/chromatid/grayFoWordQuerys1',
							{
								'v' : 'down'
							},
							function(data) {
								if (data == 'success1') {
									location.reload();
								} else if (data == 'success') {
									window.location.href = "/chromosome/page/jsp/caryogram/operation/crayexcision.jsp"
								} else {
									alert("已是第一页或最后一页");
								}
							});
			}
		}
		
		
		
	});
	
	 //关闭文件卡
    $(".card_close").click(function(){
		$(".file_card").css("display","none")
	});

	// 上下快捷键跳转已核对
	$(document).keyup(function(event) {
						if (event.keyCode == 38) {
							if(zy==0){
							$.get('/chromosome/chromatid/grayFoWordQuerys2',
											{
												'v' : 'up'
											},
											function(data) {
												console.log(data)
												if (data == 'success') {
													location.reload();
												}else {
													alert("已没有可查看的灰底图了！");
												}
											});
							}
						} else if (event.keyCode == 40) {
							if(zy==0){
							$.get('/chromosome/chromatid/grayFoWordQuerys2',
											{
												'v' : 'down'
											},
											function(data) {
												if (data == 'success') {
													location.reload();
												}else {
													alert("已没有可查看的灰底图了！");
												}
											});
							}
						}
						
						
						
					});


	//delete删除
    $(document).keydown(function(e){
        if(e.keyCode==46){
        	console.log("a10:"+dele);
        	if(dele!=''){
              	if(confirm("确定删除吗？")){
      	           
  	        	   $("#"+dele).parent().remove();
  	        	   $("#"+dele).remove();
  	        	   $.ajax({
  			           url : '/chromosome/one_key_delete',
  				   type : 'post',
  				   data : {'id' : parseInt(dele),'parameter':"DELETE_Chrom"},
  				   success : function(js) {			
  					}
  					});
  	           
  	      	num1 = 0;
 		    image = null;
 			a8 = null;
 			chromid = null;
 			chromnum = null;
 			a6="";
 			dele = '';
 			clearAll();
         	}else{
         		 $("#"+dele).css("border","none");
              	num1 = 0;
 			    image = null;
 				a8 = null;
 				chromid = null;
 				chromnum = null;
 				a6="";
 				dele = '';
 				clearAll();
         	}
        	}

        }
    })
	    
	    function setImageAttr(width,height,item){
	    	$(item).css("width", width);
			$(item).css("height", height);
			$(item).attr("c_width",width);
			$(item).attr("c_height",height);
	    }
	    
	    function toPdfPage(inName){
	    	/*window.location = basePath
			+ "/pdf/viewer.html?file="
			+ inName
			+ ".pdf";*/

	    	window.location = "/pdf/items/viewer.html?file=" + inName+ ".pdf";
	    	$("#chScreenshot_copy").remove();
	    }
	    
	  //分析核对页面点击编号的跳转
	      $(".number1").click(function(){  
	    	  if($(this).attr("name")=='aa'){
	    		    $.ajax({ 
	    				url:'/chromosome/chromatid/grayQuerys2', 
	    		        type:'post',
	    		        data:JSON.stringify("c"),
	    		        contentType: "application/json;charset=utf-8",
	    		        dataType:"json",
	    		        success:function(js){
	    		        	console.log(js);
	    					var u2 = $(".number1").text();
	    					var u3 = $(".position1").text();
	    					var u4 = $(".id1").text();
	    					
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
	    		        	if(check == 1){
	    				//根据事件号、00X查找玻片号
	    		        		 $.ajax({
	    		 					url:'/chromosome/chCount/setCountGreyId',
	    		 					type:'post',
	    		 					data:{eventId:u2,greyNum:u3,dialPiece:u4},
	    		 			        dataType:"json",
	    		 			        success:function(data){
	    		 			        	
	    		 			        	var id = data;
	    								window.location.href = '/chromosome/chromatid/chromRequest?grayId=c'
	    										+ id + '';
	    		 			        }
	    		 				});   		        		 		      
	    		        	}else{
	    		        		alert("未勾选可分析,不可跳转!");
	    		        	}

	    		        }
	    			}); 
	    	  }
	  
	    	 
	           });
});

