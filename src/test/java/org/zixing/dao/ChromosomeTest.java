package org.zixing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.GrayOper;
import org.zixing.dto.GrayQueryDate;
import org.zixing.dto.IncidentCheck;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class ChromosomeTest {
	@Resource
	private ChromatidDao chDao;
	@Test
	public void testChQuery(){
		List<IncidentCheck> list =chDao.incidentQuery("","","0");

		for (IncidentCheck in : list) {
			System.out.println(in.toString());
		}
		
		
	}
	@Test
	public void testGrayQuery(){
		List<GrayOper> lis = new ArrayList<GrayOper>();
		try {
//			 lis=chDao.grayQuerys("C3954", "count", null);
//			for (GrayOper gq : lis) {
//				System.out.println(gq.toString());
//			}
		} catch (NullPointerException e) {
			System.out.println("该查询结果为空");
		}
		
	}
	//染色单体
	@Test
	public void testChromQuery(){   
		List<ChromQuery> lis = new ArrayList<ChromQuery>();
		try {
//			 lis=chDao.chromQuery("4");
//			for (ChromQuery cl : lis) {
//				System.out.println(cl.toString());
//			}
		} catch (NullPointerException e) {
			System.out.println("该查询结果为空");
		}
	}

}
