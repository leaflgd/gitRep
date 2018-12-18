package org.zixing.service;

import java.util.List;

import org.zixing.dto.ChQueryDate;

public interface ChQueryService {
	
	//查询所有事件列表
	List<ChQueryDate> chQueryAll(String time1,String time2,String selectType,String price);
	
	//查询所有阅片人的统计
	List<ChQueryDate> chQueryAllCount(String time3,String time4);
}
