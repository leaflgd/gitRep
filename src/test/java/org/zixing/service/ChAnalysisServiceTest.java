package org.zixing.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class ChAnalysisServiceTest {
	
	@Autowired
	private ChAnalyService chAnsession;
	
	@Test
    public void chAnalysisRead() throws Exception{
		try {
			List<String> map =chAnsession.chKaryQuery("6");
			for (String cl : map) {
				System.out.println(cl);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
    }
}
