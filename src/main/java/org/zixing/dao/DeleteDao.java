package org.zixing.dao;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.ChromSwopQuery;
import org.zixing.dto.GrayOper;
import org.zixing.entity.GrayBaseMapStatus;

import java.util.List;

/**
 * 一键删除
 */
public interface DeleteDao {

	int oneKeyDelete(String id,String module);
	
	int deleteCountGreyBaseMap(String[] ids);

	List<GrayOper> queryCountCheck(String[] GreyBaseMap_ids);
}
