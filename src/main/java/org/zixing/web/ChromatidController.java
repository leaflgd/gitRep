package org.zixing.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




import net.sf.json.JSONObject;

import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dao.CrayCountDao;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.GrayOper;
import org.zixing.dto.IncidentCheck;
import org.zixing.entity.Incident;
import org.zixing.entity.User;
import org.zixing.service.ChCountService;
import org.zixing.service.ChromatidService;
import org.zixing.service.CrayAnalysisService;
import org.zixing.service.CrayCountService;
import org.zixing.service.UserService;
import org.zixing.util.StringUtil;

@Controller
// 模块
@RequestMapping("/chromatid")
// 核型分析
public class ChromatidController {
	//
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChromatidService chService;
	@Autowired
	private CrayAnalysisService crayAnaly;
	@Autowired
	private CrayCountService crayCount;
	@Autowired
	private UserService userService;
	@Autowired
	private ChCountService chCountService;
	@Autowired 
	private CrayCountDao crayCountDao;

	// 核对事件点击查询并初始化
	@ResponseBody
	@RequestMapping(value = "/inQuery")
	public Map<String, List> incidentQuery(HttpServletRequest request,@RequestBody String str) {
		String inName = (String) request.getSession().getAttribute("inName");
		String loginName = (String) request.getSession().getAttribute("loginName");
		request.getSession().removeAttribute("inName");
		String str2 = str.substring(1, str.length() - 1);
		System.out.println("参数:"+str2);
		
		if(!"".equals(str2)){
			String[] str1 = new StringUtil().StringPart(str2);     //
			String  stringList = str1[0].toUpperCase();
			if(stringList.indexOf("，")>=0){
				stringList = stringList.replace('，', ',');
			}
			userService.updateUserThreeInfo("endDate",str1[1],str1[2],stringList,loginName);
		}		
		User user = userService.userThreeInfoQuery(loginName);
		List<String> strList = new ArrayList<String>();
		if(user!=null){
			strList.add(user.getInfoOne());
			strList.add(user.getInfoTwo());
			strList.add(user.getInfoThree());
			strList.add(user.getEndDate());
		}
		List<IncidentCheck> list = chService.inQueryService(user.getInfoThree(),user.getInfoOne(),user.getInfoTwo());
		
		List<String> list2 = new ArrayList<String>();
		if(inName!=null){
			list2.add(inName.charAt(0)+"");
			list2.add(inName.substring(1));
		}
		
		List<String> strlist = chCountService.arithKaryotypeQuery(inName);
		String temp = "";
		if(strlist!=null){
			 for (int i = 0; i < strlist.size() - 1; i++) {
		    	temp = strlist.get(i);
		    	for (int j = i + 1; j < strlist.size(); j++) {
		    		if(strlist.get(j)!=null){
		    			if (temp.equals(strlist.get(j))) {
			              	chCountService.updateCaryogram(temp,inName.substring(1, inName.length()));
			          	}
		    		}
		        	
		      	}
		 	}
		}
		
		Map<String, List> map = new HashMap<String, List>();
		map.put("val", list);
		System.out.println("list -----:"+list);
		map.put("lable", list2);
		map.put("user", strList);
		return map;
	}

	// 页面跳转
	@RequestMapping(value = "/grayRequest")
	public String grayRequest(HttpServletRequest request, Model model) {
		String inName = request.getParameter("inName");
		HttpSession session = request.getSession();
		if (inName.charAt(0) == 'a') {
			session.setAttribute("inName", inName.substring(1));
			return "redirect:/page/jsp/caryogram/operation/caryanalyse.jsp";
		} else if (inName.charAt(0) == 'c') {
			session.setAttribute("inName", inName.substring(1));
			return "redirect:/page/jsp/caryogram/operation/carycount.jsp";
		}
		return "redirect:/page/jsp/caryogram/operation/caryindex.jsp";
	}

	// 事件查询
	@ResponseBody
	@RequestMapping(value = "/grayQuerys")
	public List<GrayOper> grayQuery(HttpServletRequest request,@RequestBody String str1) {
		String str = str1.substring(1, str1.length() - 1);
		HttpSession session = request.getSession();
		String inName = (String) session.getAttribute("inName");
		System.out.println("事件名称"+inName);
//		session.removeAttribute("grayid");
		List<GrayOper> list = new ArrayList<GrayOper>();
		if(inName!=null){
			if ("a".equals(str)) {
				list = crayAnaly.grayQService(inName,str);
				
			} else if(str!=null){
				list = crayCount.grayQService(inName,str);
			}
		}
		//System.out.println("保存当前事件列表8-10 :"+list);
		//保存当前事件列表8-10
		session.setAttribute("chromQueryList", list);
		session.setAttribute("greyCounts", crayCount.grayQService(inName,""));
		return list;
	}
	
	// 计数页面点击跳转判断
		@ResponseBody
		@RequestMapping(value = "/grayQuerys1")
		public List<GrayOper> grayQuery1(HttpServletRequest request,@RequestBody String str1) {
			String str = str1.substring(1, str1.length() - 1);
			HttpSession session = request.getSession();
			String inName = (String) session.getAttribute("inName");
			System.out.println("事件名称"+inName);

			List<GrayOper> list = new ArrayList<GrayOper>();
			if(inName!=null){
				if ("a".equals(str)) {
					list = crayAnaly.grayQService(inName,str);
					
				} else if(str!=null){
					list = crayCount.grayQService(inName,str);
				}
			}
			
			return list;
		}

		// 分析页面点击跳转判断
			@ResponseBody
			@RequestMapping(value = "/grayQuerys2")
			public List<GrayOper> grayQuery2(HttpServletRequest request) {
				
				HttpSession session = request.getSession();
				String inName = (String) session.getAttribute("inName");
				System.out.println("事件名称"+inName);

				List<GrayOper> list = new ArrayList<GrayOper>();
				if(inName!=null){					
						list = crayCountDao.grayQuerys1(inName);
				}
				
				return list;
			}
			
			// 计数跳转存储参数到session
			@ResponseBody
			@RequestMapping(value = "/paramSet")
			public void paramSet(HttpServletRequest request,@RequestBody String str) {
				
				HttpSession session = request.getSession();
				
				//获取前端传来的三个参数
				JSONObject json=JSONObject.fromObject(str);
				String dsum = json.getString("dsum");
				String xsum = json.getString("xsum");
				String tiaoshu = json.getString("tiaoshu");
				
				System.out.println("--"+dsum+"-"+xsum+"-"+tiaoshu);
				
				session.setAttribute("dsum", dsum);
				if(dsum.indexOf('(')>1){
					int position = dsum.indexOf('(');
					session.setAttribute("dsum", dsum.substring(0, position));
				}
				
				
				session.setAttribute("xsum", xsum);
				if(xsum.length()>2){
					int position1 = xsum.indexOf('(');
					session.setAttribute("xsum", xsum.substring(0, position1));
				}
				session.setAttribute("tiaoshu", tiaoshu);			
			}
			
			// 分割页面跳转计数储存
			@ResponseBody
			@RequestMapping(value = "/paramSet1")
			public void paramSet1(HttpServletRequest request) {
				
				HttpSession session = request.getSession();
							
				//获取前端传来的三个参数
				
				String dsum = (String) session.getAttribute("inName");
				String xsum = "";
				String tiaoshu = "";
				
				System.out.println("--"+dsum+"-"+xsum+"-"+tiaoshu);
				
				session.setAttribute("dsum", dsum);
				session.setAttribute("xsum", xsum);
				session.setAttribute("tiaoshu", tiaoshu);			
			}

			
			// 核型分析计数部分初始化、查询接口
						@ResponseBody
						@RequestMapping(value = "/grayQuerysC")
						public Map<String,Object> grayQueryC(HttpServletRequest request,@RequestBody String str) {
							
							HttpSession session = request.getSession();
							System.out.println("str:"+str);
							//获取前端传来的三个参数
							JSONObject json=JSONObject.fromObject(str);
							String dsum = json.getString("dsum");
							
							String xsum = json.getString("xsum");
							System.out.println("dsum:"+dsum+"-"+"xsum:"+xsum);
							String tiaoshu;
							if(dsum.equals("1")){
								dsum = (String) session.getAttribute("dsum");
								xsum = (String) session.getAttribute("xsum");
								tiaoshu = (String) session.getAttribute("tiaoshu");
							}else{
								tiaoshu = json.getString("tiaoshu");
							}
							
							if(xsum.indexOf('(')>0){
								int position1 = xsum.indexOf('(');
								xsum = xsum.substring(0, position1);
							}
							
							
							System.out.println("dsum:"+dsum+"-"+"xsum:"+xsum+"-"+"tiaoshu:"+tiaoshu);
							
							//获取前端传来的三个参数 事件名、玻片类型、条数		
							
							String inName = dsum;
							
							String dialPieceNum = xsum;
							
							String sum = tiaoshu;
							
							//返回数据Map集合		
							List<GrayOper> list = new ArrayList<GrayOper>();
							if(inName!=null){
								list = crayCount.grayQServiceC(inName,dialPieceNum,sum);
							}
							
							System.out.println(list);

							//存list到session:上下页跳转
							session.setAttribute("chromQueryList", list);
							
							return  crayCount.CrayCountQuery(inName,list);
							
						}
						
						
						@ResponseBody
						@RequestMapping(value = "/grayQuerysC1")
						public Map<String,Object> grayQueryC1(HttpServletRequest request,@RequestBody String str) {
							
							HttpSession session = request.getSession();
							System.out.println("str:"+str);
							//获取前端传来的三个参数
							JSONObject json=JSONObject.fromObject(str);
							String dsum = json.getString("dsum");
							
							String xsum = json.getString("xsum");
							System.out.println("dsum:"+dsum+"-"+"xsum:"+xsum);
							String tiaoshu;
							if(dsum.equals("1")){
								dsum = (String) session.getAttribute("dsum");
								xsum = (String) session.getAttribute("xsum");
								tiaoshu = (String) session.getAttribute("tiaoshu");
							}else{
								tiaoshu = json.getString("tiaoshu");
							}
							
							if(xsum.indexOf('(')>0){
								int position1 = xsum.indexOf('(');
								xsum = xsum.substring(0, position1);
							}
							
							
							System.out.println("dsum:"+dsum+"-"+"xsum:"+xsum+"-"+"tiaoshu:"+tiaoshu);
							
							//获取前端传来的三个参数 事件名、玻片类型、条数		
							
							String inName = dsum;
							
							String dialPieceNum = xsum;
							
							String sum = tiaoshu;
							
							//返回数据Map集合		
							List<GrayOper> list = new ArrayList<GrayOper>();
							if(inName!=null){
								list = crayCount.grayQServiceC(inName,dialPieceNum,sum);
							}
							
							System.out.println(list);

							//存list到session:上下页跳转
							session.setAttribute("chromQueryList", list);
							
							return  crayCount.CrayCountQuery1(inName,list);
							
						}
	//计数核对页面上下页跳转
	/**
	 * 上一页下一页greayId偏移
	 * @param request
	 * @return 是否偏移成功
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/grayFoWordQuerys")
	public String chromLabelFoWordQuery(HttpServletRequest request) {
		String v = request.getParameter("v");
		HttpSession session = request.getSession();
		
		Integer greayId = Integer.parseInt((String) session.getAttribute("grayid"));
		
		List<GrayOper> grayOpers = (List<GrayOper>) session.getAttribute("chromQueryList");
		System.out.println(greayId+"================="+grayOpers);
		System.out.println("vvvvvvvvvvvvv"+v);
		for(int i =grayOpers.size()-1;i>0;i--){
    		if(greayId==grayOpers.get(i).getGrayId()){
    			try {
    				if(v.equals("up")){ 					
                					session.setAttribute("grayid", String.valueOf(grayOpers.get(i-1).getGrayId()));
            	    				return "success";
        						}	
				} catch (IndexOutOfBoundsException e) {
					//当i为第一条，不能上一页，当i为最后一条，不能下一页
					System.out.println("当i为第一条，不能上一页，当i为最后一条，不能下一页");
					break;
				}
    			
    		}
    	}
		for(int i =0;i<grayOpers.size();i++){
    		if(greayId==grayOpers.get(i).getGrayId()){
    			try {
    				if(v.equals("down")){  					
                					session.setAttribute("grayid", String.valueOf(grayOpers.get(i+1).getGrayId()));
            	    				return "success";
        						}  					
				} catch (IndexOutOfBoundsException e) {
					//当i为第一条，不能上一页，当i为最后一条，不能下一页
					System.out.println("当i为第一条，不能上一页，当i为最后一条，不能下一页");
					break;
				}
    			
    		}
    	}
		return "error";
	}
	
	/**
	 * 分析功能分割页面快捷选择跳转功能
	 * 上一页下一页greayId偏移
	 * @param request
	 * @return 是否偏移成功
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/grayFoWordQuerys1")
	public String chromLabelFoWordQuery1(HttpServletRequest request) {
		String v = request.getParameter("v");
		HttpSession session = request.getSession();
		
		//获取单张染色体图片ID
		Integer greayId = Integer.parseInt((String) session.getAttribute("grayid"));

		List<GrayOper> grayOpers = (List<GrayOper>) session.getAttribute("chromQueryList");

			for(int i=0;i<grayOpers.size();i++){
	    		if(greayId==grayOpers.get(i).getGrayId()){
	    			try {
	    				if(v.equals("up")){
	    					System.out.println("0-------");
	    					i--;
	    					session.setAttribute("grayid", String.valueOf(grayOpers.get(i).getGrayId()));
	    					if(grayOpers.get(i).getAnalyCheck()){   				
	        						return "success1";
	    					}else{
	    	    				return "success";
	    					}
	    					
	    				}else if(v.equals("down")){
	    					System.out.println("0+++++++");
	    					i++;
	    					session.setAttribute("grayid", String.valueOf(grayOpers.get(i).getGrayId()));
	    					if(grayOpers.get(i).getAnalyCheck()){    						
	        						return "success1";
	    					}else{
	    	    				return "success";
	    					}
	    				}
					} catch (IndexOutOfBoundsException e) {
						//当i为第一条，不能上一页，当i为最后一条，不能下一页
						System.out.println("当i为第一条，不能上一页，当i为最后一条，不能下一页");
						break;
					}
	    			
	    		}
	    	}
			
		return "error";
	}
	
	
	//分析页面上下键跳转已核对
			@SuppressWarnings("unchecked")
			@ResponseBody
			@RequestMapping(value = "/grayFoWordQuerys2")
			public String chromLabelFoWordQuery2(HttpServletRequest request) {
				String v = request.getParameter("v");
				HttpSession session = request.getSession();
				
				Integer greayId = Integer.parseInt((String) session.getAttribute("grayid"));
				
				List<GrayOper> grayOpers = (List<GrayOper>) session.getAttribute("chromQueryList");
				System.out.println(greayId+"================="+grayOpers);
				System.out.println("vvvvvvvvvvvvv"+v);
				for(int i =grayOpers.size()-1;i>0;i--){
		    		if(greayId==grayOpers.get(i).getGrayId()){
		    			try {
		    				if(v.equals("up") && grayOpers.get(i-1).getAnalyCheck()){ 					
		                					session.setAttribute("grayid", String.valueOf(grayOpers.get(i-1).getGrayId()));
		            	    				return "success";
		        						}	
						} catch (IndexOutOfBoundsException e) {
							//当i为第一条，不能上一页，当i为最后一条，不能下一页
							break;
						}
		    			
		    		}
		    	}
				for(int i =0;i<grayOpers.size();i++){
		    		if(greayId==grayOpers.get(i).getGrayId()){
		    			try {
		    				if(v.equals("down") && grayOpers.get(i+1).getAnalyCheck()){  					
		                					session.setAttribute("grayid", String.valueOf(grayOpers.get(i+1).getGrayId()));
		            	    				return "success";
		        						}  					
						} catch (IndexOutOfBoundsException e) {
							break;
						}
		    			
		    		}
		    	}
				return "error";
			}
		
	// 染色单体页面跳转设置会话灰底图标题
	@RequestMapping(value = "/chromRequest")
	public String chromRequest(HttpServletRequest request, Model model) {
		String str = null;
		HttpSession session = request.getSession();
		String grayid = request.getParameter("grayId");
		String state = request.getParameter("state");
		if(state!=null){
			if(state.equals("1")){
				session.setAttribute("karyotypeOperation", "1");
			}
		}
		String num = grayid.substring(1, grayid.length());
		
		session.setAttribute("grayid", num);
		if (grayid.charAt(0) == 'a') {
			boolean bl= crayAnaly.grayCheckState(num);
			if(bl){
				return "redirect:/page/jsp/caryogram/operation/crayexcision.jsp";
			}else{
				return "redirect:/page/jsp/caryogram/operation/chromanalyse.jsp";
			}
		} else if (grayid.charAt(0) == 'c') {
			return "redirect:/page/jsp/caryogram/operation/chromcount.jsp";
		}
		// ChromLabel cl= chService.chromLabelQuery(num);
		return "redirect:/page/jsp/caryogram/operation/caryanalyse.jsp";
	}

	@ResponseBody
	@RequestMapping(value = "/caryexcision")
	public String caryexcision(HttpServletRequest request) {
		
		String mess = request.getParameter("mess");
		System.out.println(mess);
		return mess;
	}

}
