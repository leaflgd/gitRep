package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.ChQueryDate;

public interface ChQueryDao {
	
	//查询所有事件列表
	List<ChQueryDate> chQueryAll(@Param("time1")String time1,@Param("time2")String time2,@Param("selectType")String selectType,@Param("price")String price);
	
	//查询所有阅片人的统计
	List<ChQueryDate> chQueryAllCount(@Param("time3")String time3,@Param("time4")String time4);

}
