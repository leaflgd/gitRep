package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.FolderDate;
import org.zixing.dto.GrayOper;
import org.zixing.dto.IncidentCheck;



public interface CrayCountDao {
	//查询
	List<GrayOper> grayQuerys(@Param("inName")String inName,@Param("sum")String sum);   
	
	//计数已勾选查询
	List<GrayOper> grayQuerys1(@Param("inName")String inName);
	
	//核型分析查询计数
	List<GrayOper> grayQuerysC(@Param("inName")String inName,@Param("dialPieceNum")String dialPieceNum,@Param("sum")String sum);
	
	List<GrayOper> grayQuerysC2(@Param("inName")String inName);
	
	//核型分析查询计数
	FolderDate infoQuery(@Param("inName")String inName);

	//查询分析、计数已勾选
	List<IncidentCheck> incidentQuery(@Param("inName")String inName);
	
	List<IncidentCheck> incidentQuery1(@Param("inName")String inName);
	
	//查询核对状态是否生成报告
	String statusFind(@Param("inName")String inName);
	
	//查询末次生成报告的核型
	String recordFind(@Param("inName")String inName);
}
