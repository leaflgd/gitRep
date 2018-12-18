package org.zixing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.SubmitbQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class ChromAnalysisTest {
	
	@Resource
	private ChAnalysisDao chDao;
	@Resource
	private SubmitbDao subDao;
	
	//测试
	@Test
	public void testChromLabelQuery(){   
		System.out.println(chDao.chAlternate("1", "1"));
	}
	
	//
	@Test
	public void testChromNum(){   
		List<SubmitbQuery> list=subDao.submitbQuery();
		for (SubmitbQuery sub : list) {
			System.out.println(sub);
		}
	}
}
