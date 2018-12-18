package org.zixing.service;

import java.util.List;

import org.zixing.dto.GrayOper;



public interface CrayAnalysisService {
	//分析计数查询
	List<GrayOper> grayQService(String inName,String sum);
	//查询灰底图核对状态
	boolean grayCheckState(String num);
}
