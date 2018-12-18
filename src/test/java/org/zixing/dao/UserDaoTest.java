package org.zixing.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class UserDaoTest {
	@Resource
	private  UserDao userDao;
	@Test
	public void testUserLog() throws Exception{
		User u=new User("zs123", "zs321");
		String s= userDao.userLog(u);
		System.out.println(s);
	}
	@Test
	public void testUserReg(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String da =sdf.format(date);
		try {
			User u = new User("张三", "zs123", "123",da);
			userDao.userReg(u);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void testUserLogin()throws Exception{
		String u = userDao.userLogin("ls123");
		System.out.println(u.toString());
	}
	@Test
	public void testLogName() throws Exception{
		List<String> li = userDao.logName();
		System.out.println("logName:"+li);
	}

}
