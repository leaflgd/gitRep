package org.zixing.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.GrayQueryDate;
import org.zixing.dto.InSlideDate;
import org.zixing.dto.SlideQuery;
import org.zixing.entity.Incident;


//事件Dao层
public interface IncidentDao {
	//模糊查询事件
	List<Incident> inGanDao(String inName);
	//模糊查询事件(带提交次数)
	List<Incident> inGanDaoAndSubmitCount(String inName);

	List<GrayQueryDate> inGrayQueryDao(String inName);
	//查询事件下的玻片
	List<SlideQuery> sQueryDao(String inName);
	//查询玻片下的图片
	List<GrayQueryDate> grayQueryDao(@Param("slideid")String slideid);
	//更改灰体图的分析技术状态
	boolean grayStateDao(@Param("classify")String classify,@Param("grayid")List grayid);
	//查询灰底图所属的玻片号和事件编号
	InSlideDate inSlideQuery(@Param("slideId")String slideId);
	//查看状态条数
	List<String> grayStateNum();
	//查看提交状态
	String slideStateQuery(@Param("slideId")String slideid);
	//修改提交状态
	boolean slideStateAlter(@Param("slideId")String slideid);
	//添加提交信息
	boolean submitbAdd(@Param("slideId")String slideid,@Param("logName")String logName);
	//查询事件下玻片号id
	List<String> inSlideNameQuery(String inName);		
	//初始化玻片下的勾选状态
	boolean grayStateDelete(@Param("slideId")String slideid);
	//更新单个染色体的勾选状态
	boolean grayStatusUpdate(@Param("greyid") String greyid, @Param("flag") boolean flag, 
			@Param("tick") int tick);
	//查询灰底图所属玻片的勾选状态
	boolean DialPieceStatusQuery(@Param("greyid") String greyid);
	//锁定状态查询
	List<String> grayLock(@Param("id") String inName, @Param("status") boolean b);
}
