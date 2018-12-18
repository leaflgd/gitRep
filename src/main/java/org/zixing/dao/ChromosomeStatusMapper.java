package org.zixing.dao;

import org.zixing.entity.ChromosomeStatus;

public interface ChromosomeStatusMapper {
	//删除
    int deleteByPrimaryKey(Integer statusId);
    //添加
    int insertSelective(ChromosomeStatus record);
    //根据ID查询
    ChromosomeStatus selectByPrimaryKey(Integer statusId);
    //修改
    int updateByPrimaryKeySelective(ChromosomeStatus record);

}