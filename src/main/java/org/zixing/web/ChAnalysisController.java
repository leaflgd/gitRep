package org.zixing.web;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.GrayOper;
import org.zixing.entity.ChromosomeStatus;
import org.zixing.service.ChAnalyService;
import org.zixing.service.ChCountService;
import org.zixing.service.ChromatidService;
import org.zixing.service.ChromosomeStatusService;
import org.zixing.service.CrayCountService;
import org.zixing.util.Base64Toimage;
import org.zixing.util.ConfigLoad;
import org.zixing.util.NginxFileUploading;
import org.zixing.util.StringUtil;

@Controller
//模块
@RequestMapping("/chAnalysis")
public class ChAnalysisController {
	
	@Autowired
	private ChAnalyService chAnService;
	
	@Autowired
	private ChromosomeStatusService statusService;
	
	@Autowired
	private ChCountService chCountService;
	
	@Autowired
	private CrayCountService crayCountService;
	//染色单体页面初始化
	@ResponseBody
	@RequestMapping(value="/chLabelQuerys")
	public Map<String, Object> chLabelQuery(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("mark");
		String greyId = (String) session.getAttribute("grayid");
//		System.out.println(greyId);  
		Map<String, Object> map = chAnService.chromLabelQuery(greyId);
//		for (String cl : map.keySet()) {
//			System.out.println(cl);
//			for (Object cq : map.get(cl)) {
//				System.out.println(cq);
//			}
//		}
		return map;
	}
	//染色单体移动,交替
	@ResponseBody
	@RequestMapping(value="/chAlternate")
	public Map<String,List> chAlternate(@RequestBody String str,HttpServletRequest request){
			JSONObject json = JSONObject.fromObject(str);
			String id = ((String) json.get("id"));
			String num =((String) json.get("num"));
			String id1 =((String) json.get("id1"));
			String num1 =((String) json.get("num1"));

//			System.out.println(id+","+num+","+id1+","+num1);
			String[] strs={id,num,id1,num1};
			String greyId = (String) request.getSession().getAttribute("grayid");     
			//移动后排序
			System.out.println("move --------:"+json.get("move"));
			if(id1 == null && json.get("move")!=null){
				
				return chAnService.chromMove(strs, greyId,json.get("move").toString());
			}
			return chAnService.chromMove(strs,greyId);
	}
	
	//染色体核型更新
	@ResponseBody
	@RequestMapping(value="/chKaryotyp")
	public  List<String> chKaryotyp(HttpServletRequest req){
		HttpSession session = req.getSession();
		String str1= (String) session.getAttribute("grayid");
		return chAnService.chKaryQuery(str1);
	}
	//染色体复制粘贴
	@ResponseBody
	@RequestMapping(value="/chCopy")
	public  String chCopy(HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String id = ((String) json.get("id"));
		String num =((String) json.get("num"));
//		System.out.println(id+",,,,"+num.substring(4));
//		System.out.println("z复制粘贴");
		return chAnService.chromCopy(id, num.substring(4));
	}
	
	//核型一栏页面跳转
	@RequestMapping(value="/skipcaryview")
	public String caryviewQuery(HttpServletRequest request,Model model){
		String a10 = request.getParameter("a10");
		HttpSession session = request.getSession();
		session.setAttribute("mark", a10);
		return "redirect:/page/jsp/caryogram/operation/caryview.jsp";
	}
	//核型一览初始化
	@ResponseBody
	@RequestMapping(value="/caryviewQuery")
	public  Map<String,List> caryviewQuery(HttpServletRequest request){
		HttpSession session= request.getSession();
		String num=(String) session.getAttribute("mark");
		String inid = (String) session.getAttribute("inName");
		String greyid = (String) session.getAttribute("grayid");
		if(num!=null){
			Map<String, List> map = chAnService.caryviewQuery(num,inid,Integer.parseInt(greyid));
			return map;
		}
		return null;
	}
	
	//分析页面确定同步更新核型
	@ResponseBody
	@RequestMapping(value = "/ascertain1Update")
	public void ascertain1Caryogram(HttpServletRequest request,String ascertain1Caryogram,String event,String karyotype) {
		HttpSession session= request.getSession();
		String inid = (String) session.getAttribute("inName");
		String greyid = (String) session.getAttribute("grayid");
		System.out.println(ascertain1Caryogram+","+event+","+karyotype);
		chAnService.confirmUpdate(ascertain1Caryogram, event,karyotype,Integer.parseInt(greyid), inid);
		
		//更新判断上下页跳转的Session对象
        List<GrayOper> list = new ArrayList<GrayOper>();
        list = crayCountService.grayQServiceC2(inid);
        request.getSession().setAttribute("chromQueryList", list);
		
	}
	
	//分析页面确定按钮把截图保存到本地并将路径保存到数据库中
	@ResponseBody
	@RequestMapping(value="/chromConfirmOne")
	public boolean  chromConfirmOne(HttpServletRequest request,@RequestBody String str){
		
		HttpSession session = request.getSession();
		String greyId = (String) session.getAttribute("grayid");
		try {
			//将时间拼接在文件名上即可
			JSONObject json = JSONObject.fromObject(str);
			String base64 = json.getString("url");
			boolean status = json.getBoolean("status");
			String inName = (String) session.getAttribute("inName");
			
			String testDev = new ConfigLoad().configload(request,"/java.properties", "testDev"); //properties
			chAnService.chromConfirmOneSave(testDev,inName,Integer.parseInt(greyId), base64,status);   //properties
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  true;
	}	
	//分割页面返回按钮调用算法生成灰底图
	@ResponseBody
	@RequestMapping(value="/chromConfirmTwo")
	public boolean  chromConfirmTwo(HttpServletRequest request,@RequestBody String str){
		HttpSession session = request.getSession();
		String greyId = (String) session.getAttribute("grayid");
		return  chAnService.chromConfirmTwoSave(greyId);
	}
	
		
		
		/**状态**/
		
		//保存状态
		@ResponseBody
		@RequestMapping(value = "/saveStatus")
		public int saveStatus(ChromosomeStatus status) {
			//System.out.println("saveStatus:"+status);
			return statusService.save(status);
		}
		
		
		//查询状态
		@ResponseBody
		@RequestMapping(value = "/queryStatus")
		public ChromosomeStatus queryStatus(HttpSession session) {
			
			try {
				Integer greyId = Integer.parseInt(session.getAttribute("grayid").toString());
				return statusService.queryKey(greyId);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
			
		}
		
		//分析页面中旋转180的样式保存
		@ResponseBody
		@RequestMapping(value = "/chRotcss")
		public void chRotcssSave(HttpServletRequest request) {
			String rotcss = request.getParameter("rotcss");
			String id = request.getParameter("id");
			chAnService.chRotcssSave(rotcss,id);
			
		}
		
		//分析页面中镜像的样式保存
		@ResponseBody
		@RequestMapping(value = "/chScacss")
		public void chScacssSave(HttpServletRequest request) {
			String scacss = request.getParameter("scacss");
			String id = request.getParameter("id");
			chAnService.chScacssSave(scacss,id);
			
		}
		
		//分析页面中旋转180的样式查询
		@ResponseBody
		@RequestMapping(value = "/chRotcssQuery")
		public String chRotcssQuery(HttpServletRequest request) {
			String chromId = request.getParameter("chromId");
			String rotcss =  chAnService.chRotcssQuery(Integer.parseInt(chromId));
			return rotcss;
		}
		
		//分析页面中镜像的样式查询
		@ResponseBody
		@RequestMapping(value = "/chScacssQuery")
		public String chScacssQuery(HttpServletRequest request) {
			String chromId = request.getParameter("chromId");
			String scass = chAnService.chScacssQuery(Integer.parseInt(chromId));
			return scass;
		}
		
		//分析页面中旋转X度的样式保存
		@ResponseBody
		@RequestMapping(value = "/chCircumX")
		public boolean chCircumXSave(HttpServletRequest request,@RequestBody String str) {
			JSONObject json = JSONObject.fromObject(str);
			String chromId = json.getString("id");
			String data = json.getString("data");
			System.out.println("bate64"+data);

			String path=new ConfigLoad().configload(request,"/java.properties", "testThree");
			return chAnService.chCircumXSave(chromId,data,path);
		}

		
		//分割页面样式保存
		@ResponseBody
		@RequestMapping(value = "/chromExcisionStatus")
		public void chromExcisionStatus(HttpServletRequest request) {
			String chromId = request.getParameter("chromId");
			String chromStyle = request.getParameter("chromStyle");
			String grayid = request.getParameter("grayNumber");
			String style =  chAnService.grayStyleQuery(Integer.parseInt(chromId));
			if(style!=null){
				chAnService.chromExcisionStatusUpdate(chromStyle,Integer.parseInt(chromId));
			}else{
				chAnService.chromExcisionStatusInset(Integer.parseInt(chromId),Integer.parseInt(grayid),chromStyle);
			}
			
		}
		
		//染色单体分割完成后一键识别
		@RequestMapping(value="/chDiscern")
		public String chDiscern(HttpServletRequest request){
			System.out.println("一件识别触发");
			HttpSession session = request.getSession();
			String greyId = (String) session.getAttribute("grayid");
			String path = new ConfigLoad().configload(request, "/java.properties","CAProOne");//properties
			boolean bl = chAnService.chDiscern(greyId,path);
			if(bl){
				return "redirect:/page/jsp/caryogram/operation/chromanalyse.jsp";
			}else{
				return "redirect:/page/jsp/caryogram/operation/crayexcision.jsp";
			}
			
		}
		
		//胎儿隐藏
		@ResponseBody
		@RequestMapping(value="/chFetalHidden")
		public boolean chFetalHidden(HttpServletRequest request){
//			HttpSession session=request.getSession();
//			String greyId=(String) session.getAttribute("grayid");
//			return chAnService.chFetalHidden(greyId);
			return true;
		}

		//染色体保存上下移动
		@ResponseBody
		@RequestMapping(value="/chAlterTopDown")
		public boolean chAlterTopDown(HttpServletRequest request){
			String chromId = request.getParameter("chromId");
			String topDown = request.getParameter("topDown");
			return chAnService.chAlterTopDown(chromId, topDown==null?0:Integer.parseInt(topDown));
		}
		
		// 分析分割页面跳转计数核对页面
		@ResponseBody
		@RequestMapping(value = "/analyToCount")
		public String analyToCount(String eventId, String greyNum,String dialPiece) {

//		return chAnService.analyToCount(eventId, greyNum ,dialPiece);
			return null;
		}
}
