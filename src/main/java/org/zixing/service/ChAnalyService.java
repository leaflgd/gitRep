package org.zixing.service;

import java.util.List;
import java.util.Map;

import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;

public interface ChAnalyService{
	//染色单体分析技术页面标签查询
	Map<String, Object> chromLabelQuery(String grayid);
	//染色单体移动
	Map<String,List> chromMove(String[] strs,String greyid);
	//染色单体移动,带左右指标排序
	Map<String,List> chromMove(String[] strs,String greyid,String isSort);
	//染色体核型更新
	List<String> chKaryQuery(String str);
	//染色体复制粘贴
	String chromCopy(String chromId,String mark);
	//核型一览
	Map<String, List> caryviewQuery(String chromNum,String inid,int greyid);
	//分析页面确定按钮把截图保存到本地并将路径保存到数据库中
	boolean chromConfirmOneSave(String path,String inName,int greyId,String base64,boolean status) ;
	
	//分割页面返回按钮把截图保存到本地并将路径保存到数据库中
	boolean chromConfirmTwoSave(String greyId);
	
	//查询玻片下所有染色体截图的路径
	List<ChromQuery> chromPicQuery(String inName);
	
	//分析点确定更新分析核对的状态
	int chromAncheckupdate(int greyId,String karyotype,int karyotype1);
	//分析页面中旋转180的样式保存
	int chRotcssSave(String rotcss,String imgsrc);
	//分析页面中镜像的样式保存
	int chScacssSave(String scacss,String imgsrc);
	
	//分析页面中旋转180的样式查询
	String chRotcssQuery(int chromId);
		
	//分析页面中旋转180的样式查询
	String chScacssQuery(int chromId);
	
	//分析页面中旋转X度的样式保存
	boolean chCircumXSave(String chromId, String data,String path);
	
	//查询分割页面中灰底图的样式
	String grayStyleQuery(int chromId);
	
	int chromExcisionStatusUpdate(String chromStyle,int chromId);
	
	int chromExcisionStatusInset(int chromId , int greyId , String chromStyle);
	
	//染色单体分割完成后一键识别
	boolean chDiscern(String greyid,String path);
	
	//胎儿隐藏
	boolean chFetalHidden(String greyId);
	
	//染色体保存上下移动
	boolean chAlterTopDown(String chromId,Integer topDown);
	
	//分析确定同步更新事件核型
	void confirmUpdate(String aryogram,String event,String karyotype,int greyid,String inName);
	
}
