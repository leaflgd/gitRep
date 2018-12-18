package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.entity.User;

public interface UserDao {
	/**
	 * 根据用户登录名与密码来登录
	 * */
	String userLog(User u);
	/**
	 * 根据提供的用户对象来注册用户
	 * **/
	void userReg(User u);
	/**
	 * 注册时通过登录名判断是否有注册了
	 * */
	String userLogin(String str);
	/**
	 * 查询所有用户用户名
	 * */
	List<String> logName();
	
	
	//查询用户表中是否存在阅片及报告打印人相同的用户名
	String userNameQuery(@Param("putAsealOn")String putAsealOn);
			
	//根据阅片及报告打印人更新用户表中用户的工作量
	int updateUserWork(@Param("putAsealOn")String putAsealOn);
	
	List<String> userNamesQuery();
	
	//更新用户列表中查询的条件
	int updateUserThreeInfo(@Param("endDate")String endDate,@Param("date")String date,@Param("eventStatus")String eventStatus,@Param("eventId")String eventId,@Param("loginName")String loginName);
	
	//根据用户名查询列表中的三个查询条件
	User userThreeInfoQuery(@Param("loginName")String loginName);
	
	//注册页面验证用户名是否存在
	String userRegCheck(@Param("username")String username);
	

	//查询操作人员
	String checkName(@Param("name")String name);
	
	//更新此次查询到数据库INF_USER ReserchMem字段
	void updateMem(@Param("loginName")String loginName,@Param("inName")String inName);
	
	//根据用户查询数据精选记忆搜索SearchMem字段
    String getMem(@Param("user")String user);
}
