package org.zixing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.ChNames;
import org.zixing.dto.ChromLabel;
import org.zixing.dto.ChromOffsetQuery;
import org.zixing.dto.ChromQuery;
import org.zixing.dto.ChromSwopQuery;
import org.zixing.dto.InfLog;
import org.zixing.entity.Cancel;

public interface ChDivisionDao {
	// 染色体分割页面增强灰底图
	List<String> DivisionEnhanceGrey(@Param("grayid") String grayid);

	// 染色体分割页面标签初始化查询
	ChromLabel chDivisionLabelQuery(@Param("greyid") String greyid);
	
	// 染色体分割页面原始灰底图
	List<String> DivisionOriginalGrey(@Param("grayid") String grayid);

	// 染色多体查询
	List<ChromQuery> chMultimerInit(@Param("grayid") String grayid);

	// 查询进行操作的染色单体原图
	String chRawQuery(@Param("chromid") String chromid);

	// 查询进行操作的染色单体轮廓图
	String chOperationQuery(@Param("chromid") String chromid);

	// 根据灰底图id查询玻片和灰底图名称
	List<ChNames> chNamesQuery(@Param("chromid") String chromid);

	// 获取轮廓的最大步骤数
	int chProcedureMaximum(@Param("chromid") String chromid);

	// 获取画线涂抹撤销的步骤总数
	int chProcedureSum(@Param("chromid") String chromid);

	// 设置画线涂抹撤销的步骤总数
	boolean chProcedureSumSet(@Param("stepSum") int stepSum,@Param("chromid") String chromid);

	// 设置轮廓的步骤
	boolean chSplittingStep(@Param("cancel") Cancel can);
	
/**
 * 
 * 测试用
 * 
 * */
	//读取轮廓的步骤
	List<Cancel> chSplittingQuery(@Param("chromid") String chromid);
	
	ChromOffsetQuery chOffsetQuery(@Param("chromid") String chromid);
	//存储的原始灰底图和相对坐标
	boolean picPathStorage(@Param("chroms") List<ChromQuery> chrom);
	//查询高亮轮廓的坐标
	String chromHiLiteOutlineQuery(@Param("chromid") String chromid);
	
	//储存高亮轮廓的坐标
	boolean hiLiteOutlineStorage(@Param("chromid") String chromid,@Param("hiLiteOutline") String hiLiteOutline );
	
	//连接功能染色单体查询
	List<ChromQuery> chromConnectQuery(@Param("list") List lists);
	
	//储存报告生成需要的灰底图
	boolean chromGreyPicStorage(@Param("greyid") String greyid,@Param("status") int status,@Param("picPath") String picPath);
	
	//查询需要识别的的子图
	List<ChromQuery> chSonIdQuery(@Param("chFatherId") String chFatherId,@Param("greyid") String greyid);
	//查询生成灰底图的染色体图
	List<ChromQuery> greyCreateQuery(@Param("greyid") String greyid);
	//存储报告所需灰底图
	boolean reportGreyStore(@Param("greyid") String greyId, @Param("babyPic") String babyPic, @Param("oriPic") String oriPic);


/**-----------------------------------------------------* **/
	// 删除最后的步骤
	Integer DivisionStepDelete(@Param("chromid") String chromid);
	
	// 删除所有的步骤
	boolean DivisionStepDeletes(@Param("chromid") String chromid);

	// 批量删除
		boolean chromDelete(@Param("chromid") List chromid,@Param("num") int num);

	// 分割状态更改
	boolean chDivisionStatusAlter(@Param("chromid") String chromid,@Param("chromids") List chromids,
			@Param("status") int status);

	// 处理步骤查询
	List<InfLog> processStepsQuery(@Param("greyid") String greyid);
	
	//查询最后一个处理步骤的id
	String stepsQuery(@Param("greyid") String greyid);
	//根据logid处理步骤查询
	InfLog stepsClickQuery(@Param("logid") String logid);
	// 处理步骤删除
	boolean processStepsDelete(@Param("logid") String logid);

	// 查询删除的父类id
	String chDivisionDeleteIDQuery(@Param("logid") String LogId);

	// 查询连接的子类id
	String chConnectSonIdQuery(@Param("logid") String LogId);

	// 查询连接的父类id
	String chConnectFatherIdQuery(@Param("logid") String LogId);

	// 删除关联的子图
	boolean chDivisionDelete(@Param("chlogid") String chLogId);

	// 染色体复制
	ChromQuery chCopyQuery(@Param("chromid") String chromid);

	// 染色体粘贴
	boolean chCopy(ChromQuery cq);

	// 查询处理步骤记录
	int QueryStepsMark(@Param("grayid") String grayid);

	// 处理步骤记录设置
	boolean SettingStepsMark(@Param("grayid") String grayid,
			@Param("num") int num);

	// 添加步骤
	boolean processAdd(InfLog il);
	
	//添加删除步骤
	boolean processDeleteAdd(InfLog il);

	// 查询单个染色体进行页面显示
	ChromQuery chQuerySingle(@Param("chromid") String chromid);

	// 查询子图id集合
	List<String> subgraphIdQuery(@Param("chromid") String chromid);

	// 查询子图带有的步骤
	List<String> sonStepQuerys(@Param("chromids") List<String> chromid);

	// 模糊查询子图带有的连接步骤
	String sonConnectStepQuery(@Param("chromid") String chromid);

	// 查询需要删除的日志名称
	List<String> chLogName(@Param("chromids") List<String> chroms);

	// 查询复制粘贴撤回的id
	String chLogId(@Param("logid") String logid);

	// 步骤撤销的类型
	int withdrawStepState(@Param("logid") String logid);

	// 轮廓高亮图查询
	String chOutlineQuery(@Param("chromid") String chromid);
	
	// 根据logid查询高亮图
	String chOutlineLogIdQuery(@Param("logid") String logid);

	// 查询父类id
	String chFatherId(@Param("chromid") String chromid);

	// 添加连接步骤并返回自增ID
	int processConnect(InfLog in);

	// 操作一步清除
	boolean DivisionStepAllDelete(@Param("chromid") String chromid);

	// 重写删除指定的步骤（删掉一键交叉，一键粘连步骤）
	boolean DivisionStepDeleteOneKey(@Param("chromid") String chromid,
			@Param("status") int status);

	// 获取最近轮廓的状态（用于查询是否属于一键交叉，分割）
	Object chProcedureStatus(@Param("chromid") String chromid);

	//	修改指定染色体的编号
	boolean chromUpateNum(@Param("chroms") List<Cancel> chroms);

	//批量写入摆正图
	boolean writAdjustPaths(@Param("list") List<ChromSwopQuery> list);
	
	//单张灰底图中染色体最大排序值查询
	int sortMAXNumQuery(@Param("greyid") String greyid);
}
