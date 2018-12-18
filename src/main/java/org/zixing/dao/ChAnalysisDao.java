package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.ChromSwopQuery;
import org.zixing.entity.GrayBaseMapStatus;

public interface ChAnalysisDao {
	//染色单体查询
	ChromLabel chCountLabelQuery(@Param("grayid")String gray);
	List<ChromQuery> chQuery(@Param("id")String id);
	//查询灰底图的核对状态
	boolean chromAncheckQuery(@Param("greyid") String grayid);
	//更改染色体的编号
	boolean chAlternate(@Param("chromId")String chromId,@Param("num")String num);
	//染色体查询
	ChromSwopQuery chromQuerys(@Param("id")String chromId);
	//染色体添加
	boolean chromInsert(@Param("list")List<ChromSwopQuery> list);
	//存图返回id
	int chromInsertQuery(ChromSwopQuery cq);
	//染色体修改
	boolean chromUpdate(@Param("chrom")ChromSwopQuery chrom);
	//染色体删除
	boolean chromDelete(@Param("list")List list);
	//更改染色体状态
	boolean chAbnormal(@Param("chromId")String chromId);
	String KaryotypeQuery(@Param("state")String state,@Param("grayid")String grayid);
	//染色单体复制粘贴页面异步刷新显示
	List<ChromSwopQuery> chCopyQuery(@Param("greyId")String greyId,@Param("mark")String chromNum);
	//核型一栏查询
	List<ChromQuery> caryviewQuery(@Param("chromNum")String chromNum,@Param("inid")String inid);
	//分析页面确定按钮把截图保存到本地并将路径保存到数据库中
	int chromConfirmOneSave(@Param("path")String path,@Param("greyId")int greyId,@Param("status")boolean status);
	//分割页面返回按钮把截图保存到本地并将路径保存到数据库中
	int chromConfirmTwoSave(@Param("path")String path,@Param("gradId")int gradId);
	//查询玻片下所有染色体截图的路径
	List<ChromQuery> chromPicQuery(@Param("inName")String inName);
	//查询单个染色体截图的路径
	ChromQuery chromSinglePicQuery(@Param("greyId")int greyId ,@Param("report")int report);
	//分析点确定更新分析核对的状态
	int chromAncheckupdate(@Param("greyId")int greyId);
	//点确定更新核型
	boolean chromKaryotype(@Param("greyId")int greyId,@Param("kary")String kary,@Param("kary1")int kary1);
	//查询原始灰底图
    String greyPictureQuery(@Param("greyid")int greyid);
    //分析页面中镜像的样式保存
    int chScacssSave(@Param("scacss")String scacss,@Param("id")String id);
    //分析页面中旋转180的样式保存
    int chRotcssSave(@Param("rotcss")String rotcss,@Param("id")String id);
    
    //分析页面中旋转180的样式查询
  	String chRotcssQuery(@Param("chromId")int chromId);
  		
  	//分析页面中镜像的样式查询
  	String chScacssQuery(@Param("chromId")int chromId);
    
    //分析页面中旋转X度的样式保存
    int chCircumXSave(@Param("circumX")String circumX,@Param("chromId")int chromId);
    
    //查询分割页面中灰底图的样式
  	String grayStyleQuery(@Param("chromId")int chromId);
    //更新全局染色体的样式
  	int chromExcisionStatusUpdate(@Param("chromStyle")String chromStyle,@Param("chromId")int chromId);
   //插入全局染色体样式
  	int chromExcisionStatusInset(@Param("chromId")int chromId ,@Param("greyId")int greyId , @Param("chromStyle")String chromStyle);
  	
  	List<GrayBaseMapStatus> grayStatusList(@Param("greyId")int greyId);
  	
  	
  	
  	//更改染色体上下高低，排序编号
  	boolean chAlter(@Param("chromId")String chromId,@Param("topDown")Integer topDown);
  	
  	//更改染色体排序编号
  	int chAlterSortNumber(@Param("list")List<ChromSwopQuery> sortList);
  	
  //查询排序编号
  ChromSwopQuery chAlterSortNumberQuery(@Param("chromId")String chromId);

  //分析页面打印PDF羊水报告带出胎儿图
  	String BabyPicOut(@Param("greyId")String greyId);
  	
  //分析确定同步更新事件核型
  	void confirmUpdate(@Param("caryogram")String caryogram,@Param("event")String event);
  	
  //分析分割页面跳转计数核对页面
  	String analyToCount(@Param("eventId")String eventId,@Param("greyNum")String greyNum,@Param("dialPiece")String dialPiece);

  //根据id查询摆正图
	String chAdjustQuery(@Param("chromId")String chromId);
	
	//存储摆正图
	boolean chAdjustUpdate(@Param("chromId")String chromId,@Param("url") String str);

}
