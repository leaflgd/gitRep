package org.zixing.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.zixing.dao.UserDao;
import org.zixing.entity.User;
import org.zixing.service.UserService;
// 组件                           service  dao  web、
//@Component @service @Dao @Conroller
@Service
public class UserServiceImp implements UserService{
	//日志文件
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//Dao层
	@Autowired
	private UserDao userdao;
	
	private final String slat="%^@wsdghg*&%25486JKG(*sda,.;a;~!@IOP%^#$^[";
	//登录
	public boolean userLogService(User u) {
		u.setUserPassword(getMD5(u.getUserPassword()));
		if(userdao.userLog(u)==null){
			return false;
		}
		return true;
	}
	//注册
	public boolean userRegService(User u) {
		u.setUserPassword(getMD5(u.getUserPassword()));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		u.setUserDate(sdf.format(date));
		String str = userdao.userLogin(u.getUserLogin());
		if(str==null){
			userdao.userReg(u);
			return true;
		}
		return false;
	}
	//MD5加密
	private String getMD5(String str){
		String base = str+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	//获取用户
	public List<String> logName(){
		List<String>  li= userdao.logName();
		return li;
	}
	
	//查询用户表中是否存在阅片及报告打印人相同的用户名
	@Override
	public String userNameQuery(String putAsealOn) {
		return userdao.userNameQuery(putAsealOn);
	}

	//根据阅片及报告打印人更新用户表中用户的工作量
	@Override
	public int updateUserWork(String putAsealOn) {
		return userdao.updateUserWork(putAsealOn);
	}
	@Override
	public List<String> userNamesQuery() {
		return userdao.userNamesQuery();
	}
	
	//更新用户列表中查询的条件
	@Override
	public int updateUserThreeInfo(String endDate,String date, String eventStatus,
			String eventId, String loginName) {
		return userdao.updateUserThreeInfo(endDate,date,eventStatus,eventId,loginName);
	}
	//根据用户名查询列表中的三个查询条件
	@Override
	public User userThreeInfoQuery(String loginName) {
		return userdao.userThreeInfoQuery(loginName);
	}
	
	//注册页面验证用户名是否存在
	public String userRegCheck1(String username) {
		return userdao.userRegCheck(username);
	}
}
