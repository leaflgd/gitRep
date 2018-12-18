package org.zixing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zixing.entity.Incident;
import org.zixing.util.ReadTxtFile;

//主动生成模拟Txt文件生成
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class TxtGeneration {

	@Resource
	private IncidentDao inDao;
	
	@Test
	public void txtMain(){   
		List<Incident> list= inDao.inGanDao("");
		for (Incident in : list) {
			System.out.println(in.getIncidentName());
			List<String>list1 = new ArrayList<String>(); 
			list1.add(in.getIncidentName());
			list1.add("354712");
			list1.add("王春秋");
			list1.add("27");
			list1.add("女");
			list1.add("2018-06-22");
			new ReadTxtFile().writeTxtFile(list1,"E:/thisTXT/"+in.getIncidentName()+".txt");
		}
	}

}
