package org.zixing.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.zixing.dto.ChromQuery;
import org.zixing.dto.InfLog;
import org.zixing.entity.Cancel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ChDivisionService {
	// 染色体分割初始化
	Map<String, Object> chDivisionInit(String grayid);

	// 需分割的染色单体查询
	String chQuery(String id);

	// 坐标储存
	String chCoordinateStore(JSONArray json, int state, String chromid,
			String grayid);

	// 坐标撤回
	int chCoordinateRepeal(String chromid);

	// 进行分割
	Map<String, List> chDivisionDecide(String chromid, String grayid,
			String LogName);

	// 批量删除
	Map<String, List> chromDeleteService(List chromid, String grayid,String userName);

	// 图像转换
	List<String> greyImageConversion(String greyid, String status);

	// 处理步骤日志查询
	List<InfLog> ProcessStepsQuery(String greyid);

	//处理步骤点击事件
	Map<String, Object> processStepsClickQuery(String logid);
	
	// 处理步骤删除
	Map<String, List> ProcessStepsDelete(String greyid, String logid);

	// 复制粘贴
	ChromQuery chDivisionCopy(String greyid, String chromid,
			String userName);

	// 坐标一键撤回
	boolean chCoordinateRepealAll(String chromid);

	// 轮廓高亮图查询
	List<String> chOutlineQuery(String chromid);

	// 连接功能
	Map<String, Object> chromConnect(List<Integer> list, String grayid,
			String logName,String test);
	
	//一键交叉
	String oneKeyCross(JSONObject json,String testTwo);
	
	//分割存图
	boolean divisionImgStore(List<String> base,String chromid);
	
	//快捷删除处理步骤
	Map<String,List> chromStepsRollback(String str);
	
	/**
	 * 迭代内容
	 * **/
	//读取该染色体的画线画圈的坐标集
	Map<String, Object> chCoordinateQueryService(String chromid);
	//存储染色单体图片
	Map<String,Object> picPathStorageService(String greyid ,int status,String logName,List <String> chromid,JSONArray web);

	boolean chromUpateNum(List<Cancel> list);

	List<ChromQuery> chromDiscernQuery(String greyid);
	//灰底图生成查询染色单体
	List<ChromQuery> greyCreateQuery(String greyid);
	//存储报告所需的灰底图
	boolean reportGreyStore(String greyId, String babyPic, String oriPic);
	//批量写入摆正图
	boolean writAdjustPaths(JSONArray json);

}
