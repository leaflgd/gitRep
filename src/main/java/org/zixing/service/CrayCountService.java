package org.zixing.service;

import java.util.List;
import java.util.Map;

import org.zixing.dto.GrayOper;



public interface CrayCountService {
	//分析计数查询
	List<GrayOper> grayQService(String inName,String sum);
	
	//核型分析计数查询
	List<GrayOper> grayQServiceC(String inName,String dialPieceNum,String sum);
	
	List<GrayOper> grayQServiceC2(String inName);

	//核型分析计数页面数据查询
	public Map<String,Object> CrayCountQuery(String inName ,List<GrayOper> list);
	
	public Map<String,Object> CrayCountQuery1(String inName ,List<GrayOper> list);
}
