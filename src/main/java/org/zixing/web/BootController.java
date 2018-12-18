package org.zixing.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dao.ChDivisionDao;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.RetrunData;
import org.zixing.entity.Cancel;
import org.zixing.service.ChDivisionService;
import org.zixing.util.ReadTxtFile;

@Controller
//模块
@RequestMapping("/boot")
public class BootController {
	public static int j=0;
	public static int i=0;
	
	private static final Logger log = Logger.getLogger(BootController.class);
	
	
	@Autowired
	private ChDivisionDao  chDDao;
	@Autowired
	private ChDivisionService chDService;
	
	@ResponseBody
	@RequestMapping(value="/test")
	public boolean testss(HttpServletRequest request){
		j+=1;
		System.out.println("提交-----------"+j);

		try {
			new ReadTxtFile().readTxtFTPFile("/home/uftp/apache-tomcat-7.0.88/webapps/TXT/C344133.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//连接算法
		
		return true;
	}
//	int long short double char float boolean byte 
	@ResponseBody
	@RequestMapping(value="/tests")
	public String testsss(@RequestBody String str){
		System.out.println(str);
		if(str!=null){
			return "";
		}
		return "da sha bi";
	}
	
	@ResponseBody
	@RequestMapping(value="/chCoordinateStore")
	public List<Cancel> chCoordinateStore(HttpServletRequest req,@RequestBody String strs){
		String chromid = "716645";
		String grayid = "699690";
		JSONObject json = JSONObject.fromObject(strs);
		String mess =(String)json.get("message");
		int state = (int) json.get("status");
		// System.out.println("储存的id："+chromid+",储存的状态"+status);
		// 编译坐标
		String str = "";
		String[] ss = mess.substring(0, mess.length() - 1).split(",");
		for (String str1 : ss) {
			str += str1 + ",";
		}
		
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		if(str.equals("")){
			return null;
		}
		// 查询并设置步骤总数
		int stepSum = 0;
		try {
			stepSum = chDDao.chProcedureSum(chromid);
		} catch (Exception e) {
			System.out.println("读取步骤总数异常");
		}
		chDDao.chProcedureSumSet(stepSum + 1, chromid);
		// 查询最后的坐标步骤数
		int stepNum = 0;
		try {
			stepNum = chDDao.chProcedureMaximum(chromid);
		} catch (Exception e) {
			System.out.println("读取步骤异常");
		}
		// System.out.println("第"+stepNum+1+"步骤");
		Cancel can = null;
		if (stepNum == 0) {
			can = new Cancel(chromid, str, 1, state);
		} else {
			can = new Cancel(chromid, str, stepNum + 1, state);
		}
		try {
			chDDao.chSplittingStep(can);
		} catch (Exception e) {
			System.out.println("步骤存入数据库出异常：" + e.getMessage());
		}
		
		//读取所有步骤传递到前端
		List<Cancel> list= chDDao.chSplittingQuery(chromid);
		for (Cancel cancel : list) {
			System.out.println(cancel);
		}
		return list;
	}

//	测试
//	----------------------------------------------------------------------
	
	//读取该染色体的画线画圈的坐标集
	@ResponseBody
	@RequestMapping(value="/chCoordinateQuery")
	public RetrunData chCoordinateQuery(HttpServletRequest req){
		int status=0;
		RetrunData rd = new RetrunData();
		try {
			String chromid =req.getParameter("chromid");
			System.out.println("id:"+chromid);
			//读取所有步骤传递到前端
			rd.setData(chDService.chCoordinateQueryService(chromid));
		} catch (Exception e) {
			status=1;
			
		}
		if(status==0){
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		
		return rd;
	}
	
	//存储染色单体图片
	@ResponseBody
	@RequestMapping(value="/picPathStorage")
	public RetrunData picPathStorage(HttpServletRequest req,@RequestBody String str){
		int bl=0;
		RetrunData rd = new RetrunData();
		try {
			//前段传递数据处理
			JSONObject json = JSONObject.fromObject(str);
			String greyid = (String) json.get("greyId");
			int status = (int)json.get("status");
			String logName = (String) json.get("logName");
			JSONArray chromids = json.getJSONArray("chromid");
			List <String> chromid = (List<String>) JSONArray.toCollection(chromids);
			JSONArray web = json.getJSONArray("chrom");
			//对数据操作并返回值
			rd.setData(chDService.picPathStorageService(greyid, status, logName, chromid, web));
		}catch (Exception e) {
			rd.setMsg(e.getMessage());
			rd.setCode(1);
		}
		if(bl==0){
			rd.setMsg("成功");
			rd.setCode(0);
		}
		return rd;
	}
	
	
	//查询高亮图的地址或轮廓坐标
	@ResponseBody
	@RequestMapping(value="/chromHiLiteOutlineQuery")
	public RetrunData chromHiLiteOutlineQuery(HttpServletRequest req){

		String chromid =req.getParameter("chromid");
		
		String bl = chDDao.chromHiLiteOutlineQuery(chromid);
		
		RetrunData rd = new RetrunData();
		if(bl!=null){
			rd.setMsg("成功");
			rd.setCode(0);
			rd.setData(bl);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}
	
	//储存高亮图的地址或轮廓坐标
	@ResponseBody
	@RequestMapping(value="/hiLiteOutlineStorage")
	public RetrunData hiLiteOutlineStorage(HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String chromid = (String) json.get("chromid");
		String hiLiteOutlin =(String)json.get("hiLiteOutlin");
		
		boolean bl = chDDao.hiLiteOutlineStorage(chromid,hiLiteOutlin);
		RetrunData rd = new RetrunData();
		if(bl){
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}
	
	//连接功能查询
	@ResponseBody
	@RequestMapping(value="/chromConnectQuery")
	public RetrunData chromConnectQuery (HttpServletRequest req,@RequestBody String str){
		JSONArray jsonArray = JSONArray.fromObject(str);
		List < String > list = (List<String>) JSONArray.toCollection(jsonArray);
		List<ChromQuery> chroms = chDDao.chromConnectQuery(list);
		RetrunData rd = new RetrunData();
		if(chroms!=null){
			rd.setMsg("成功");
			rd.setCode(0);
			rd.setData(chroms);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}
	
	
	//灰底图存储
	@ResponseBody
	@RequestMapping(value="/chromGreyStorage")
	public RetrunData chromGreyStorage  (HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String greyid = (String) json.get("greyid");
		String picPath = (String) json.get("picPath");
		int status =(int)json.get("status");
		RetrunData rd = new RetrunData();
		boolean bl = chDDao.chromGreyPicStorage(greyid,status,picPath);
		if(bl){
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}
	
	//修改指定染色体的编号
	/**
	 * 传递过来的参数为
	 * [{id:'123','num':'12'},....]
	 * */
	@ResponseBody
	@RequestMapping(value="/chromUpateNum")
	public RetrunData chromUpateNum (HttpServletRequest req,@RequestBody String str){
		RetrunData rd = new RetrunData();
		//接收数据处理
		JSONArray chroms = JSONArray.fromObject(str);
		List<Cancel> list = new ArrayList<Cancel>();
		for(int i = 0; i<chroms.size() ; i++ ){ //但是对象中又嵌套着数组
			Cancel chrom = new Cancel();
			String t = chroms.getString(i); //遍历过程将web数组元素赋给String型变量
			System.out.println("对象数据"+t);
			JSONObject we = JSONObject.fromObject(t); //通过String又得到每个元素的对象
			chrom.setChromId((String)we.get("id"));
			chrom.setNum((Integer)we.get("num"));
			chrom.setUrl((String)we.get("roateFileName"));	
			list.add(chrom);
		};
		long startTime=System.currentTimeMillis();   //获取开始时间
		boolean bl =chDService.chromUpateNum(list); 
		long endTime=System.currentTimeMillis(); //获取结束时间
		log.debug("修改所用时间： "+(endTime-startTime)+"ms");
		if(bl){
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}
	
	/**
	 * 传输的数据类型
	 * {'greyid':'123'}
	 * */
	//一键识别查询
	@ResponseBody
	@RequestMapping(value="/chromDiscernQuery")
	public RetrunData chromDiscernQuery (HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String greyid = (String) json.get("greyid");
		RetrunData rd = new RetrunData();
		List<ChromQuery> bl =chDService.chromDiscernQuery(greyid);
		if(bl.size()>=0){
			rd.setData(bl);
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}
	//灰底图生成查询染色单体
	@ResponseBody
	@RequestMapping(value="/greyCreateQuery")
	public RetrunData greyCreateQuery (HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String greyid = (String) json.get("greyid");
		RetrunData rd = new RetrunData();
		try {
			rd.setData(chDService.greyCreateQuery(greyid));
			rd.setMsg("成功");
			rd.setCode(0);
		} catch (Exception e) {
			rd.setMsg("失败");
			rd.setCode(1);
		}
 		return rd;
	}
	/**
	 * 传输的数据类型
	 * {'greyId':'123',...}
	 * */
	//生成报告所需灰底图写入
	@ResponseBody
	@RequestMapping(value="/reportGreyStore")
	public RetrunData reportGreyStore (HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String greyId = (String) json.get("greyId");
		String babyPic = (String) json.get("babyPic");
		String oriPic = (String) json.get("oriPic");
		RetrunData rd = new RetrunData();
		boolean bl =chDService.reportGreyStore(greyId,babyPic,oriPic);
		if(bl){
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}

	/**
	 * 批量写入摆正图
	 * js数据传输方式
	 * JSON.stringify([{'id':id,'url':path},......]);
	 * */
	@ResponseBody
	@RequestMapping(value="/writAdjustPaths ")
	public RetrunData writAdjustPaths (HttpServletRequest req,@RequestBody String str){
		boolean bl =false;
		RetrunData rd = new RetrunData();
		//获取数据
		JSONArray json = JSONArray.fromObject(str);
		bl = chDService.writAdjustPaths(json);
		if(bl){
			rd.setMsg("成功");
			rd.setCode(0);
		}else{
			rd.setMsg("失败");
			rd.setCode(1);
		}
		return rd;
	}

}
