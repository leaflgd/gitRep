package org.zixing.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.zixing.dto.GrayQueryDate;
import org.zixing.dto.InSlideDate;
import org.zixing.dto.SlideQuery;
import org.zixing.entity.Incident;

//事件service层
public interface IncidentService {
	//事件模糊查询
	List<Incident>  inGaninService(String inName);
	//查询事件下的玻片
	Map<String,Object> sQueryService(String inName);
	//查询玻片下的灰底图
	Map<String, Object> grayQeryservice(String slideid);
	//改变灰底图计数分析状态
	int grayStateService(JSONObject sumMap, String logName, boolean status, String inName);
	//更新单个染色体的勾选状态
	boolean grayStatusUpdate(String greyid, boolean flag, int tick);
}
