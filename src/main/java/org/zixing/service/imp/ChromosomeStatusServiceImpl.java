package org.zixing.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zixing.dao.ChromosomeStatusMapper;
import org.zixing.entity.ChromosomeStatus;
import org.zixing.service.ChromosomeStatusService;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class ChromosomeStatusServiceImpl  implements ChromosomeStatusService{
	
	@Resource
	private ChromosomeStatusMapper mapper;

	@Override
	public int deleteByPrimaryKey(Integer statusId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(statusId);
	}
	
	
	@Override
	public int save(ChromosomeStatus record) {
		int result = mapper.updateByPrimaryKeySelective(record);
		return result;
		
	}

	@Override
	public ChromosomeStatus queryKey(Integer grayid) {
		//System.out.println("grayid :"+grayid);
		ChromosomeStatus model = mapper.selectByPrimaryKey(grayid);
		//如果没有就保存一个初始化样式
		if(model == null){
			model = new  ChromosomeStatus(null,grayid,0,0,50,null);
			mapper.insertSelective(model);
		}
		//System.out.println("model :"+model);
		return model;
	}


}
