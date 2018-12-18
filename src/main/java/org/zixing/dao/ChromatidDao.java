package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.GrayOper;
import org.zixing.dto.IncidentCheck;
import org.zixing.dto.SubmitbQuery;


public interface ChromatidDao {
	//查询事件
	List<IncidentCheck> incidentQuery(@Param("inName")String inName,
			@Param("date")String date,@Param("incheck")String check);
	
	//染色单体的条数加减
//	int chromAlter(@Param("chid")int chid,@Param("num")int num);
	//查询染色体条数
//	ChromQuery chromCountQuery(@Param("chid")int chid);
	String inNameChack(@Param("inName")String inName);
	
	String queryCardCaryogram(@Param("inName")String inName);
	
	//修改灰底图已打印报告
	int updatePrintReort(int id);
}
