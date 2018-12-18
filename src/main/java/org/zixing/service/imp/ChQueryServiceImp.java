package org.zixing.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixing.dao.ChQueryDao;
import org.zixing.dto.ChQueryDate;
import org.zixing.service.ChQueryService;

@Service
public class ChQueryServiceImp implements ChQueryService {

	@Autowired
	private ChQueryDao chQueryDao;
	
	//查询所有事件列表
	public List<ChQueryDate> chQueryAll(String time1, String time2,
			String selectType, String price) {
		return chQueryDao.chQueryAll(time1,time2,selectType,price);
	}

	//查询所有阅片人的统计
	public List<ChQueryDate> chQueryAllCount(String time3, String time4 ) {
		return chQueryDao.chQueryAllCount(time3,time4);
	}
	
	
}
