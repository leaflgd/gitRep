$(function(){
    $("#model_analyse1").addClass("active");
    $("#listload").addClass("active");
    $(".tableBox tbody tr:even").css("background","#7b7b7b");
    $(".tabulation_tab tbody tr:even").css("background","#7b7b7b");
    //分析页面-------------------
    //事件查询
//	$(document).ready(function(){ inquery();});
    $(document).on("mousedown","#search",function(){
        $("#search").css({"background":"linear-gradient(#2ec0fb, #0e9aee,#0091ea)"});
    })
    $(document).on("mouseup","#search",function(){
        $("#search").css({"background":"linear-gradient(#59bbe9, #2ea5df,#0791d7)"});
    })

    $(document).ready(function(){
        inquery("");
        $(".inName").focus();
    });

    $(document).on("click", "#search", function(){
        var dt ="";
        var inc = $(".inName").val();
        var check = $(".incheck").val();
        var contor = $("#control_date2").val();
        console.log("inc ===== "+inc);
        console.log("check ===== "+check);
        console.log("contor ===== "+contor);
        dt=dt+"c"+inc+",";
        dt=dt+"c"+contor+",";
        dt=dt+"c"+check+",";
        inquery(dt);
    });
    
    $(document).keydown(function(e){
    	if(e.keyCode==13){
    		$("#search").click();
    	}
    });
    
    function inquery(dt){
        $.ajax({
            url:'/chromosome/chromatid/inQuery',
            type:'post',
            data:JSON.stringify(dt),
            contentType: "application/json;charset=utf-8",
            dataType:"json",
            success:function(js){

                var ht="";
                for ( var key in js) {
                    if(key=="val"){
                        for ( var a in js[key]) {

                            if(js[key][a].maximumArithKaryotype==null){
                                if(js[key][a].caryogram == null){
                                    js[key][a].caryogram = "";
                                }
                                js[key][a].maximumArithKaryotype=js[key][a].caryogram;
                            }

                            var analy;
                            var count;
                            //没有核对的就按照全部显示
                            if(js[key][a].analyCheck==0){
                                analy=js[key][a].analyStatusSum;
                                count=js[key][a].countStatusSum;
                            }else {
                                //按照核对的显示
                                analy=js[key][a].analyCheck;
                                count=js[key][a].countCheck;
                            }


                            if(js[key][a].status){

                                ht+="<tr ><td><span class='ffffff'>"+js[key][a].inName+"</span></td>"

                                if (js[key][a].inCheck)
                                {

                                    ht+="<td><span class='ffffff'>"+analy+"</span></td>" +
                                        "<td><span class='ffffff'>"+count+"</span></td>" +
                                        "<td><span class='ffffff'>"+js[key][a].maximumArithKaryotype+"</span></td>" +
                                        "<td><span class='ffffff'>已核对</span><i><img src='../../../images/patientdata/gou.png'></i></td></tr>"
                                }
                                else{ht+="<td><span class='ffffff'>"+analy+"</span></td>" +
                                    "<td><span class='ffffff'>"+count+"</span></td>" +
                                    "<td><span class='ffffff'>"+js[key][a].maximumArithKaryotype+"</span></td>" +
                                    "<td><span class='ffffff'>未核对</span></td></tr>"
                                }
                            }else{

                                ht+="<tr><td>"+js[key][a].inName+"</td>"

                                if (js[key][a].inCheck)
                                {


                                    ht+= "<td class='analyse_show' id = '"+js[key][a].inName+"'><span data-toggle='tab' >"+analy+"</span></td>" +
                                        "<td class='count_show' id = '"+js[key][a].inName+"'><span data-toggle='tab' >"+count+"</span></td>" +
                                        "<td><span data-toggle='tab'>"+js[key][a].maximumArithKaryotype+"</span></td>"+
                                        "<td>已核对<i><img src='../../../images/patientdata/gou.png'></i></td></tr>"
                                }
                                else{ht+= "<td class='analyse_show'  id = '"+js[key][a].inName+"'><span data-toggle='tab' >"+analy+"</span></td>" +
                                    "<td class='count_show' id = '"+js[key][a].inName+"'><span data-toggle='tab' >"+count+"</span></td>" +
                                    "<td><span data-toggle='tab'>"+js[key][a].maximumArithKaryotype+"</span></td>"+
                                    "<td>未核对</td></tr>"
                                }
                            }
                        }
                        $(".IncidentQuery").html(ht);
                    }else if(key=="lable"){
                        if(js[key][0]=="a"){
                            var th=$(".analyse_show[id="+js[key][1]+"]");
                            var th1=$(".analyse_show[id="+js[key][1]+"]").parent();
                            th1.css({'background-color':'#00a0e9'});
                            th.css({'color':'#ffff00'});
                        }
                        if(js[key][0]=="c"){
                            var th=$(".count_show[id="+js[key][1]+"]");
                            var th1=$(".count_show[id="+js[key][1]+"]").parent();
                            th1.css({'background-color':'#00a0e9'});
                            th.css({'color':'#ffff00'});
                        }
                    }else if(key == "user"){
                        if(js[key][0] != null || js[key][0] != ""){
                            $("#control_date2").val(js[key][0]);
                        }
                        if(js[key][1] != null || js[key][1] != ""){
                            if(js[key][1]==""){
                                $('select option:eq(0)').attr('selected','selected');
                            }else if(js[key][1]=="1"){
                                $('select option:eq(1)').attr('selected','selected');
                            }else if(js[key][1]=="0"){
                                $('select option:eq(2)').attr('selected','selected');
                            }
                        }
                        if(js[key][2] != null || js[key][2] != ""){
                            $(".inName").val(js[key][2]);
                        }

                    }

                }

            }
        });
    }

    $(document).on("click", ".analyse_show", function(){

        var dt = "a"+$(this).attr("id");
        window.location.href='/chromosome/chromatid/grayRequest?inName='+dt;
    });
    $(document).on("click", ".count_show" ,function(){
        var dt = "c"+$(this).attr("id");
        window.location.href='/chromosome/chromatid/grayRequest?inName='+dt;
    });
    
    laydate.render({
   	 elem: '#control_date2'
   		});
    
    
});
