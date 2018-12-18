package org.zixing.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixing.dao.CrayAnalysisDao;
import org.zixing.dto.GrayOper;
import org.zixing.service.CrayAnalysisService;


@Service
public class CrayAnalysisServiceImp implements CrayAnalysisService{
	
	@Autowired
	private CrayAnalysisDao crayAnaly;
	
	public List<GrayOper> grayQService(String inName ,String sum) {
		List<GrayOper> list = crayAnaly.grayQuerys(inName,sum);
		return list;
	}

	@Override
	public boolean grayCheckState(String num) {
		String str =  crayAnaly.grayCheckState(num);
		if(str.equals("0")) return true;
		
		return false;
	}

}
