package org.zixing.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.SubmitbQuery;

public interface SubmitbDao {
	
	//查询算法已处理完的提交事件
	List<SubmitbQuery> submitbQuery();
	//状态回滚
	boolean grayStateRoll(@Param("slideId")String slideid);
	//删除么拨片下所有小图
	boolean chromDelete(@Param("slideId")String slideid);
	//提交成功修改时间
	boolean submitbDate(@Param("slideId")String slideid,@Param("dates")Date date);
	//提交信息处理完毕修改状态
//	boolean submitbProcessStatus(@Param("slideId")String slideid);
	//提交信息删除
	boolean submitbDelete(@Param("slideId")String slideid);
	//提交处理状态修改
	boolean submitbStatusUpdate(@Param("slideId")String slideid,@Param("status")int status);
}
