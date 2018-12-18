package org.zixing.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.RetrunData;
import org.zixing.service.ChAnalyService;
import org.zixing.service.ChDivisionService;
import org.zixing.util.ConfigLoad;
import org.zixing.util.ListTypeCon;

@Controller
//模块
@RequestMapping("/chDivision")
public class ChDivisionController {
	
	@Autowired
	private ChDivisionService chDivService;
	
	// 染色体分割初始化
	@ResponseBody
	@RequestMapping(value = "/init")
	public RetrunData init(HttpServletRequest request) {
		RetrunData rd = new RetrunData();
		try {
			Map<String, Object> map = chDivService.chDivisionInit(request.getParameter("greyid"));
			rd.setData(map);
			rd.setCode(0);
			rd.setMsg("OK");
		} catch (Exception e) {
			rd.setCode(1);
			rd.setMsg(e.getMessage());
		}
		return rd;
	}

	// 查询需要分割的染色单体对象
	@ResponseBody
	@RequestMapping(value = "/chQuery")
	public List<String> chQuery(HttpServletRequest req, @RequestBody String str) {
		// 获取所需要分割的id
		JSONObject json = JSONObject.fromObject(str);
		String id = (String) json.get("id");
		HttpSession session = req.getSession();
		session.removeAttribute("chromid");
		session.setAttribute("chromid", id);
		List<String> list = new ArrayList<String>();
		list.add(chDivService.chQuery(id));
		return list;
	}

	// 对于染色单体撤销步骤坐标的储存
	@ResponseBody
	@RequestMapping(value = "/chCoordinateStore")
	public List<String> chCoordinateStore(HttpServletRequest req,
			@RequestBody String str) {
		HttpSession session = req.getSession();
		String chromid = (String) session.getAttribute("chromid");
		String grayid = (String) session.getAttribute("grayid");
		JSONObject json = JSONObject.fromObject(str);
		JSONArray mes = (JSONArray) json.get("message");
		int status = (int) json.get("status");
		// System.out.println("储存的id："+chromid+",储存的状态"+status);
		List<String> list = new ArrayList<String>();
		list.add(chDivService.chCoordinateStore(mes, status, chromid,grayid));
		return list;
	}

	// 对于染色单体撤销步骤坐标的撤销
	@ResponseBody
	@RequestMapping(value = "/chCoordinateRepeal")
	public RetrunData chCoordinateRepeal(HttpServletRequest req) {
		RetrunData rd = new  RetrunData();
		System.out.println("撤销操作");
		HttpSession session = req.getSession();
		String chromid = (String) session.getAttribute("chromid");
		int count= chDivService.chCoordinateRepeal(chromid);
		if(count<2){
			rd.setMsg("成功");
			rd.setCode(0);
			rd.setData(count);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
			rd.setData(count);
		}
		
		return rd;
	}

	// 进行分割操作
	@ResponseBody
	@RequestMapping(value = "/chDivisionDecide")
	public Map<String, List> chDivisionDecide(HttpServletRequest req) {
		System.out.println("分割操作");
		HttpSession session = req.getSession();
		String chromid = (String) session.getAttribute("chromid");
		String grayid = (String) session.getAttribute("grayid");
		String logName = (String) session.getAttribute("loginName");
//		System.out.println("需要分割的id:"+chromid);
		return chDivService.chDivisionDecide(chromid, grayid,logName);
	}

	// 原始或增强灰底图按钮查询
	 @ResponseBody
	 @RequestMapping(value="/greyImageConversion") 
	 public List<String> greyImageConversion(HttpServletRequest req,@RequestBody String str){ 
		 HttpSession session =req.getSession(); JSONObject json = JSONObject.fromObject(str);
		 String status = json.getString("grayStatus");
		 String greyId = (String) session.getAttribute("grayid");
		 List<String> list = chDivService.greyImageConversion(greyId, status);
		 if(list!=null){
			 return list;
		 }
		 return null;
	 }

	// 批量删除
		// 提交
		@ResponseBody
		@RequestMapping(value = "/chromDelete")
		public Map<String,List> graySubmit(@RequestBody String str, HttpServletRequest request) {
			HttpSession session = request.getSession();
			String greyid = (String)session.getAttribute("grayid");
			String userName = (String) session.getAttribute("loginName");
			JSONObject json = JSONObject.fromObject(str);
			List<Integer> chromid = new ListTypeCon().listConInt((List) json.get("chromid"));
			return chDivService.chromDeleteService(chromid,greyid,userName);
		}
	
	//处理步骤查询
	@ResponseBody
	@RequestMapping(value = "/processStepsQuery")
	public List processStepsQuery(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String greyid = (String) session.getAttribute("grayid");
		return chDivService.ProcessStepsQuery(greyid);
	}
	
	//处理步骤点击事件
	@ResponseBody
	@RequestMapping(value = "/processStepsClickQuery")
	public Map<String, Object> processStepsClickQuery(HttpServletRequest request,@RequestBody String str) {
		JSONObject json = JSONObject.fromObject(str);
		String logid = json.getString("logid");
		return chDivService.processStepsClickQuery(logid);
	}
	
	//处理步骤删除
	@ResponseBody
	@RequestMapping(value = "/processStepsDelete")
	public RetrunData processStepsDelete(@RequestBody String str, HttpServletRequest request) {
		RetrunData rd = new RetrunData();
		try {
		JSONObject json = JSONObject.fromObject(str);
		String logid =json.getString("logid");
		String grayid =json.getString("grayid");
		Map<String, List> resultData =  chDivService.ProcessStepsDelete(grayid, logid);
		rd.setData(resultData);
		rd.setCode(0);
		rd.setMsg("OK");
		}catch(Exception e){
			rd.setCode(1);
			rd.setMsg(e.getMessage());
		}
		return rd;
	}
	
	//处理步骤一键删除
	@ResponseBody
	@RequestMapping(value = "/chromStepsRollback")
	public Map<String,List> chromStepsRollback(HttpServletRequest request) {
		HttpSession session =request.getSession();
		String str = (String) session.getAttribute("grayid");
		return chDivService.chromStepsRollback(str);
	}
	
	//复制粘贴
	@ResponseBody
	@RequestMapping(value = "/chromCopy")
	public ChromQuery chromCopy(@RequestBody String str, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginName = (String) session.getAttribute("loginName");
		String greyId = (String) session.getAttribute("grayid");
		JSONObject json = JSONObject.fromObject(str);
		String chromid =json.getString("chromid");
		return chDivService.chDivisionCopy(greyId,chromid, loginName);
	}
	
	// 对于染色一步撤销步骤坐标的撤销
	@ResponseBody
	@RequestMapping(value = "/chCoordinateRepealAll")
	public boolean chCoordinateRepealAll(HttpServletRequest req) {
		System.out.println("撤销操作");
		HttpSession session = req.getSession();
		String chromid = req.getParameter("imga");
		boolean back=chDivService.chCoordinateRepealAll(chromid);
		return back;
	}
	

	//染色体对应的高亮图显示
	@ResponseBody
	@RequestMapping(value = "/chromOutlineShow")
	public List<String> chromOutlineShow(@RequestBody String str) {
		JSONObject json = JSONObject.fromObject(str);
		String chromid =json.getString("chromId");
//		System.out.println("id*******"+chromid);
		return chDivService.chOutlineQuery(chromid);
	}
	
	//染色体连接
	@ResponseBody
	@RequestMapping(value = "/chromConnect")
	public Map<String,Object> chromConnect(@RequestBody String str,HttpServletRequest req){
		HttpSession session = req.getSession();
		String grayid = (String) session.getAttribute("grayid");
		String logName = (String) session.getAttribute("loginName");
		List<Integer> list = new ArrayList<Integer>();
		String[] strs = str.substring(1,str.length()-1).split(",");
		String test=new ConfigLoad().configload(req, "/java.properties","CDCProOne"); //properties
		for (String str1 : strs) {
//			System.out.println(str1.substring(1,str1.length()-1));
			list.add(Integer.parseInt(str1.substring(1,str1.length()-1)));
		}
		return chDivService.chromConnect(list,grayid,logName,test);
	}
	
	// 一键交叉
		@ResponseBody
		@RequestMapping(value = "/oneKeyCross")
		public String oneKeyCross(HttpServletRequest request) {
			HttpSession session = request.getSession();
			String chromid = (String) session.getAttribute("chromid");
			// 交互算法
			JSONObject json = new JSONObject();
			json.put("Chromosome_id", Integer.parseInt(chromid));
			json.put("GreyBaseMap_id", Integer.parseInt((String) session.getAttribute("grayid")));
			/*json.accumulate("Logs_Steps", "segment" + 1);
			json.accumulate("Login_Name", session.getAttribute("loginName"));*/
			json.put("Logs_Steps", "nulls");
			json.put("Login_Name", "nulls");
			json.put("Status", Integer.parseInt(request.getParameter("Status")));
			String hah=new ConfigLoad().configload(request, "/java.properties","CDCProTwo");
			
			json.put("canvas",request.getParameter("canvasX")+","+request.getParameter("canvasY"));
			return chDivService.oneKeyCross(json,hah);
		}
		//分割传图
		@ResponseBody
		@RequestMapping(value = "/divisionImgStore")
		public boolean divisionImgStore(HttpServletRequest request ,@RequestBody String str) {
			//json对象
			JSONObject json = JSONObject.fromObject(str);
			JSONArray base =  json.getJSONArray("base");
			String chromid =(String) json.get("chromid");
			List<String> list = new ArrayList<String>();
			System.out.println("json数组处理");
			for (Object o : base) {
				list.add((String) o);
			}
			return chDivService.divisionImgStore(list, chromid);
		}
}
