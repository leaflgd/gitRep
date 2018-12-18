package org.zixing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.dto.ChromLabel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class ChromCountTest {
		@Resource
		private ChCountDao chDao;
		//测试
		@Test
		public void testChromLabelQuery(){   
			List<ChromLabel>  list=chDao.chromLabelQuerys("4");
			for (ChromLabel chrom: list) {
				System.out.println(chrom.toString());
			}
		}
}
