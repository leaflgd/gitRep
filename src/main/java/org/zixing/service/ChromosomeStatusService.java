package org.zixing.service;

import org.zixing.entity.ChromosomeStatus;

public interface ChromosomeStatusService {
	//删除
    int deleteByPrimaryKey(Integer statusId);
    
    //保存
    int save(ChromosomeStatus record);
    
    ChromosomeStatus queryKey(Integer grayId);
}
