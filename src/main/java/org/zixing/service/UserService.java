package org.zixing.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.entity.User;

public interface UserService {
	//用户登录
	boolean userLogService(User u);
	//用户注册
	boolean userRegService(User u);
	//获取用户信息
	List<String> logName();
	
	//查询用户表中是否存在阅片及报告打印人相同的用户名
	String userNameQuery(String putAsealOn);
		
	//根据阅片及报告打印人更新用户表中用户的工作量
	int updateUserWork(String putAsealOn);
	
	List<String> userNamesQuery();
	
	//更新用户列表中查询的条件
	int updateUserThreeInfo(String endDate,String date,String eventStatus,String eventId,String loginName);
	
	//根据用户名查询列表中的三个查询条件
	User userThreeInfoQuery(String loginName);
	
	//注册页面验证用户名是否存在
	String userRegCheck1(String username);
}
