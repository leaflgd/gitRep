package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.FolderDate;
import org.zixing.dto.StatusQuery;
import org.zixing.util.UserList;


public interface ChCountDao {
	
	//染色体标签查询
	List<ChromLabel> chromLabelQuerys(@Param("grayid")String grayid);
	
	//单个染色体查询
	List<ChromQuery> singleChromQuery(@Param("grayid")String grayid);
	
	//计数页面上下页的greyid查询
	String CountGreyIdQuery(@Param("greyid")String greyId, @Param("inName")String inName, @Param("status")boolean b);
	
	//修改染色体的数量
	int chromNumberAndRemakesUpdate(@Param("chromCount")String chromCount , @Param("remakes")String remakes, @Param("grayid")String grayid);
	
	//修改文件卡
	int updateFolder(@Param("folder")FolderDate folder,@Param("inName") String inName);
	
	//修改单个染色体的数目
	int chromSingleUpdate(@Param("chromId")String chromId, @Param("chromNum")String chromNum);
	
	//修改单个染色体的数目同时修改染色体数量
	int chromNumberUpdate(@Param("chromCount")String chromCount , @Param("grayid")String grayid);
	
	//查询时间编号对应的文件卡
	FolderDate foldeQuery(@Param("inName")String inName);
	
	//根据单染色体id查询对应的轮廓图
	String singleChromToLine(@Param("imgId")String imgId);
	
	//根据截图查询染色体信息
	List<ChromLabel> chromAllPicQuerys(@Param("chAllpic")String chAllpic);
	
	//生成报告后改变事件的生成报告状态,核型
	int updateReportstatus(@Param("inName")String inName,@Param("caryogram")String caryogram,@Param("date")String date);
	
	//统计分析中勾选的
	int anStatusSum(@Param("inName")String inName);
	//统计除了分析中勾选的  计数勾选的
	Integer countStatusSum(@Param("inName")String inName);
	//统计分析中核对的
	int anCheckSum(@Param("inName")String inName);
	//统计计数中核对的
	int countCheckSum(@Param("inName")String inName);
	
	int countCheckSum1(@Param("inName")String inName);
	
	//更新文件卡中的中期(统计，计数，分析)数据
	int updateWholes(@Param("wholes")String wholes,@Param("inName")String inName);
	
	//根据事件编号查询核对状态
	String queryCheckStatu(@Param("inName")String inName);
	
	//根据事件编号查询分析中的核型
	List<String> arithKaryotypeQuery(@Param("inName")String inName);
	
	//如果核对中有相同的核型则更新文件卡中的核型
	int updateCaryogram(@Param("caryogram")String caryogram,@Param("inName")String inName);
	
	//根据灰底图id查询当前核对状态
	int anStatuThisQuery(@Param("greyId")int greyId);
	
	//根据事件号查询到玻片号
	String dialPieceNumFind(@Param("greyId")String greyId,@Param("greyNum1")String greyNum1);
		
	//将可分析状态更新
	void updateStatus1(@Param("dialPieceNum")String dialPieceNum,@Param("greyNum")String greyNum);
	
	//将可分析状态取消
	void updateStatus2(@Param("dialPieceNum")String dialPieceNum,@Param("greyNum")String greyNum);
	
	//根据玻片号，灰底图编号查询到核型
	String caryogramFind(@Param("DialPieceid")String DialPieceid,@Param("greyNum")String greyNum);
	    
	//计数页面更新的核型更新到GreyBaseMap表中
	void updateCaryogram1(@Param("caryogram")String caryogram,@Param("dialPieceNum")String dialPieceNum,@Param("greyNum")String greyNum);
	
	//计数页面更新的核型更新到GreyBaseMap表中
	void updateCaryogram2(@Param("caryogram1")String caryogram1,@Param("dialPieceNum")String dialPieceNum,@Param("greyNum")String greyNum,@Param("caryogram")String caryogram);
		
	//计数核对的数量更新到分析中
	void updateCaryogram3(@Param("analyCaryogram")String analyCaryogram,@Param("dialPieceNum")String dialPieceNum,@Param("greyNum")String greyNum);
		
	//计数右键自动生成报告拿到玻片号slideName
	List<String> findSlideName(@Param("inName")String inName);
	
	//计数核对寻找核型
	String  findCaryogram(@Param("dialPieceNum")String dialPieceNum,@Param("greyNum")String greyNum);
	
	//分析页面判断是否可进行分析
	List<StatusQuery>  StatusQueryFind(@Param("grayid")String grayid);
	
	//分析核对页面点击事件号查询玻片号
	String dialPieceNumFind1(@Param("eventId")String eventId,@Param("greyNum")String greyNum,@Param("dialPiece")String dialPiece);
	
	//分析核对页面点击00X查询玻片号
	String dialPieceNumFind2(@Param("eventId")String eventId,@Param("greyNum")String greyNum,@Param("dialPiece")String dialPiece);
	//文件卡下拉框
	boolean updateFolderList(@Param("user")UserList user);
	
	//查询核型和意见
	FolderDate chOpinionQuery(@Param("inName") String inName);

	//查询性别和核型
	FolderDate chGenderQuery(@Param("inName") String inName);
	
	//存储指定事件的匹配意见
	boolean chOpinionStorage(@Param("inName") String inName,@Param("opin") String opin);

	//修改文件卡锁定
	boolean CardOpinionStatusUpdate(@Param("inName") String inName, @Param("folder")FolderDate data);

}
