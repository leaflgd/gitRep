package org.zixing.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dao.IncidentDao;
import org.zixing.dao.UserDao;
import org.zixing.dto.GrayQueryDate;
import org.zixing.dto.InSlideDate;
import org.zixing.dto.SlideQuery;
import org.zixing.entity.Incident;
import org.zixing.service.IncidentService;
import org.zixing.util.GetAndPost;
import org.zixing.util.ListTypeCon;

@Controller
//模块
@RequestMapping("/incident")
public class IncidentController {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IncidentService inService;
	
	@Autowired
	private UserDao userDao;
	//事件模糊查询
	@ResponseBody
	@RequestMapping(value="/inGain")
	public List<Incident> incidentGain(@RequestBody String inName,HttpServletRequest req){
		//清除保存的session对象
		req.getSession().removeAttribute("inName");
		req.getSession().removeAttribute("slideid");
		//事件模糊查询
		String in= inName.substring(1, inName.length()-1);
		
		//更新此次查询到数据库INF_USER SerchMem字段
		String loginName = (String) req.getSession().getAttribute("loginName");
		//System.out.println("inName:"+in);
		
		userDao.updateMem(loginName, in);
		if(inName==null) inName = "";
		List<Incident> li=inService.inGaninService(in);
		return li;
	}
	
	//数据精选记忆搜索数据
	@ResponseBody
	@RequestMapping(value="/getMem")
	public String getMem(HttpServletRequest request){
		String user = (String) request.getSession().getAttribute("loginName");
		System.out.println("user:"+user);
		
		String Memory = userDao.getMem(user);
		if(Memory==null||Memory==""){
			Memory = "";
		}
		return Memory;
	}
	//查询麽个事件下的玻片
	@ResponseBody
	@RequestMapping(value="/sQuery")
	public Map<String,Object> slideQuery(@RequestBody String inName,HttpServletRequest req){
		String in= inName.substring(1, inName.length()-1);
		req.getSession().setAttribute("inName", in);
		req.getSession().removeAttribute("slideid");
		Map<String,Object> map=inService.sQueryService(in);
		return map;
	}
	//灰底图查询
	@ResponseBody
	@RequestMapping(value="/grayQuery")
	public Map<String,Object> grayQuery(@RequestBody String slideId,HttpServletRequest req){
//		System.out.println(slideId.substring(1, slideId.length()-1));
		String slideid= slideId.substring(1, slideId.length()-1);
		req.getSession().setAttribute("slideid", slideid);
		Map<String, Object> map=inService.grayQeryservice(slideid);
		return map;
	}
	
	//灰底图勾图状态更新
	@ResponseBody
	@RequestMapping(value="/grayStatusUpdate")
	public boolean grayStatusUpdate(HttpServletRequest req,@RequestBody String str){
		JSONObject json = JSONObject.fromObject(str);
		String greyid = json.getString("greyid");
    	boolean flag = json.getBoolean("flag");
    	int Tick = json.getInt("Tick");
    	//System.out.println("id:"+greyid+",flag:"+flag+",Tick:"+Tick);
    	boolean bl=inService.grayStatusUpdate(greyid,flag,Tick);
		return bl;
	}

	
	//提交
	@ResponseBody
	@RequestMapping(value="/graySubmit")
	public int graySubmit(HttpServletRequest request){
		HttpSession session=  request.getSession();
		String logName = (String) session.getAttribute("loginName");
		String inName = (String)session.getAttribute("inName");

		boolean bl = Boolean.parseBoolean(request.getParameter("status"));
		String sumMapString = request.getParameter("sumMap");
		JSONObject sumMap = JSONObject.fromObject(sumMapString);

		return inService.grayStateService(sumMap,logName,bl,inName);
	}
}
