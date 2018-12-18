package org.zixing.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.GrayQueryDate;

import com.mysql.fabric.xmlrpc.base.Array;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class IncidentDaoTest {
	@Resource
	private IncidentDao inDao;
	@Test
	public void testChQuery() throws Exception{
//		List<GrayQueryDate> list= inDao.grayQueryDao("1-A");
//		for (GrayQueryDate gray : list) {
//			System.out.println(gray);
//		}
	}
	@Test
	public void testGray(){
		try {
			List i= new ArrayList();
			i.add(1);
			i.add(2);
//			int[] i ={1,2,3};
			System.out.println(i);
			System.out.println(inDao.grayStateDao("count",i));
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
}
