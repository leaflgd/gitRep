package org.zixing.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.GrayQueryDate;
import org.zixing.dto.SlideQuery;
import org.zixing.entity.Incident;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
						"classpath:spring/spring-service.xml"})
public class IncidentServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IncidentService inService;
	
	@Test
	public void testInService()throws Exception{
		List<Incident> bl= inService.inGaninService("56");
		logger.info("事件{}",bl);
		System.out.println(bl);
	}
	
	@Test
	public void testSlide()throws Exception{
//		Map<String, List<SlideQuery>> bl= inService.sQueryService("C4954");
//		for (String str : bl.keySet()) {
//			System.out.println("KEY:"+str+",VALUE"+bl.get(str));
//		}
//		System.out.println(bl);
	}
	
	@Test
	public void testGary()throws Exception{
//		Map<String, List<GrayQueryDate>> bl= inService.grayQeryservice("1-A");
//		for (String str : bl.keySet()) {
//			System.out.println("KEY:"+str+",VALUE"+bl.get(str));
//		}
	}
}
