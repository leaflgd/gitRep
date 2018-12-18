package org.zixing.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.entity.User;
import org.zixing.service.UserService;
import org.zixing.util.StringUtil;
import org.zixing.util.his.call.WsZxBaseInfo;

@Controller
// 模块
@RequestMapping("/user")
// url：/模块/资源/{id}....
public class UserController {
	// 日志文件
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userservice;

	// 登录用户
	@RequestMapping(value = "/log")
	public String login(User u, Model model, HttpServletRequest request) {
//		System.out.println(u);
		boolean bl = userservice.userLogService(u);
		if (bl) {
			request.getSession().setAttribute("loginName", u.getUserLogin());
			return "redirect:/page/jsp/caryogram/date/date.jsp";
		} else {
			model.addAttribute("msg", "用户不存在，登录失败");
			JOptionPane.showMessageDialog(null, "用户名或密码错误，请重新登陆");
			return "redirect:/page/jsp/user/login.jsp";
		}
	}

	// 注册用户
	@RequestMapping(value = "/reg")
	public String regsit(User u, Model model, HttpServletRequest request) {
		System.out.println(u);
		boolean bl = userservice.userRegService(u);
		if (bl) {
			return "redirect:/page/jsp/user/login.jsp";
		}
		model.addAttribute("msg", "注册失败");
		JOptionPane.showMessageDialog(null, "用户名已存在，请重新注册");
		return "redirect:/page/jsp/user/register.jsp";
	}
	
	// 注册用户验证用户名是否存在
			@RequestMapping(value = "/check")
			@ResponseBody
			public String nameCheck(String username) {
				//System.out.println("username---------------"+username);
				if(username==""){
					return("3");
				}
				String back = userservice.userRegCheck1(username);
				//System.out.println("back+++++++++"+back);
				if(back==""||back==null){
					return("1");
				}else{
					return("2");
				}		
			}
			
	// 获取用户名
	@RequestMapping(value = "/logname")
	public String logname(Model model) {
		List<String> li = userservice.logName();
		model.addAttribute("list", li);
		return "user/login";
	}
	
		//获取地址
		@RequestMapping(value = "/ipAddress")
		@ResponseBody
		public String ipAddress() {
			//获取项目IP地址.
			InetAddress address = null;//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			String hostAddress = address.getHostAddress();//192.168.0.121
			return hostAddress;
		}
		
		//
		@RequestMapping(value = "/test")
		@ResponseBody
		public String testHis() {
			//return new WsZxBaseInfo("C146800").getHisResult();
			return System.getProperty("catalina.home");
		}
}
