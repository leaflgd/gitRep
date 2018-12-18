package org.zixing.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.entity.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
						"classpath:spring/spring-service.xml"})
public class UserServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	
	@Test
	public void testUserServiceLog() throws Exception{
		User u = new User("ls123", "ls321");
		boolean bl= userService.userLogService(u);
		logger.info("登录{}",bl);
	}
	
	@Test
	public void testUserServiceReg()throws Exception{
//		User u = new User("李四", "ls123", "123", true);
//		boolean bl= userService.userRegService(u);
//		logger.info("注册{}",bl);
//		System.out.println(bl);
	}
	
	@Test
	public void testLogNameService()throws Exception{
		List<String> bl= userService.logName();
		logger.info("用户{}",bl);
		System.out.println(bl);
	}
	
	
}
