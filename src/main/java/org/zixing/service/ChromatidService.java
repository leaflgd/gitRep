package org.zixing.service;

import java.util.List;
import org.zixing.dto.IncidentCheck;


public interface ChromatidService {
	//事件查询、
	List<IncidentCheck> inQueryService(String inName,String date,String check);
	//根据染色体id增加染色体条数并返回增加后的条数
//	ChromQuery chromCountNum(int num, String countHandle,int chid);
	
	//是否打印报告核对状态查询
	String inNameChack(String inName);
}
