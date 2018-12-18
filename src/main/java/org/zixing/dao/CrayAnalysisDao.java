package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.GrayOper;

public interface CrayAnalysisDao {
	// 查询
	List<GrayOper> grayQuerys(@Param("inName") String inName,
			@Param("sum") String sum);

	// 查询灰底图核对状态
	String grayCheckState(@Param("grayNum") String num);

	// 根据Event_id 获取核型重复值最多的核型
	String querysMaximumArithKaryotype(String EventId);
	
	// 根据Event_id 获取核型
		List<String> queryAllArithKaryotype(String eventId);

	// 根据Event_id 获取核型分析计数总数
	Integer querysAnalyzeCount(String EventId);
	
	// 根据Event_id 获取核型分析计数总数
	Integer querysAnalyzeCount1(String EventId);
}
