package org.zixing.service;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.IncidentCheck;
import org.zixing.service.imp.SubmitbServiceImp;
import org.zixing.util.SpringContextHolder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class ChServiceTest {
	@Autowired
	private ChromatidService chservice;
	@Test
	public void testChQuery() throws Exception{
		List<IncidentCheck> list =chservice.inQueryService("","","");
		for (IncidentCheck in : list) {
			System.out.println(in.toString());
		}
	}
	@Test
	public void testChQuerys() throws Exception{
		SubmitbServiceImp ssi =SpringContextHolder.getBean(SubmitbServiceImp.class);
		ssi.SubmitbDispose();
	}
}
