package org.zixing.service;

import java.util.List;
import java.util.Map;

import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.FolderDate;
import org.zixing.dto.GrayOper;
import org.zixing.util.UserList;

public interface ChCountService {

	// 染色体标签查询
	List<ChromLabel> chromLabelQuerys(String grayid);

	// 单个染色体查询
	Map<String, Object> ChromQuery(String grayid, List<GrayOper> list);

	// 计数中的页面上下页数据加载
	Map<String, Object> chCountQuery(String greyId, String inName, boolean b);

	
	// 修改染色体的数量及备注
	int chromNumberAndRemakesUpdate(String chromCount, String remakes,
			String grayid);

	// 修改文件卡
	int updateFolder(FolderDate folderDate);

	// 修改单个染色体的数目
	int chromSingleUpdate(String chromId, String chromNum);

	// 修改单个染色体的数目同时修改染色体数量
	int chromNumberUpdate(String chromCount, String grayid);

	// 查询时间编号对应的文件卡
	List<Object> foldeQuery(String path, String inName,String logName,boolean isMake);

	//分析页面查询时间编号对应的文件卡以及生成报告
	FolderDate foldeQuery1(String inName);

	// 根据单染色体id查询对应的轮廓图
	String singleChromToLine(String imgId);

	// 根据截图查询染色体信息
	List<ChromLabel> chromAllPicQuerys(String chAllpic);

	// 生成报告后改变事件的生成报告状态,核型
//	int updateReportstatus(String inName,String caryogram);

	// 统计分析中勾选的
	int anStatusSum(String inName);

	// 统计除了分析中勾选的 计数勾选的
	Integer countStatusSum(String inName);

	// 统计分析中核对的
	int anCheckSum(String inName);

	// 统计计数中核对的
	int countCheckSum(String inName);

	// 更新文件卡中的中期(统计，计数，分析)数据
	int updateWholes(String wholes, String inName);

	// 根据事件编号查询核对状态
	boolean queryCheckStatu(FolderDate FolderDate,String pathFile, String inName);

	// 根据事件编号查询分析中的核型
	List<String> arithKaryotypeQuery(String inName);

	// 如果核对中有相同的核型则更新文件卡中的核型
	int updateCaryogram(String caryogram, String inName);

	// 根据灰底图id查询当前核对状态
	int anStatuThisQuery(int greyId);

	//pdf文件生成返回其pdf的路径
	String pdfGeneration(String inName,int greyId,
			String txtUrl,int report,String imgPath,String greyPath,String path,String pdfpath);
	//存储文件下拉框
	boolean updateFolderList(UserList user);
	
	//意见匹配核型并确定出返回的模版
	int ChMatchOpinion(String inName);

	//看核型和性别是否匹配
	boolean ChMatchKaryotypes(String inName);

}
