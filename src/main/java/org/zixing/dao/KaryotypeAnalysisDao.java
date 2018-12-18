package org.zixing.dao;

import org.apache.ibatis.annotations.Param;
import org.zixing.dto.IncidentCheck;
import org.zixing.dto.SlideQuery;

import java.util.List;

/**
 * @Author: xzl
 * @Date: 2018/11/7 17:27
 * @Description: 核型分析界面接口
 */
public interface KaryotypeAnalysisDao {

    //获取事件名，分析计数总数
    List<IncidentCheck> incidentQuery(@Param("inName")String inName,
                                      @Param("date")String date, 
                                      @Param("endDate") String endDate,
                                      @Param("incheck")String check);

    //获取事件名下拨片名，总数
    List<SlideQuery> queryEventPlectrum(String inName);

    List<org.zixing.dto.GreyBaseMap> queryGreyBaseMapByEventIdOrPlectrumName(@Param("inName")String inName,@Param("plectrumName")String plectrumName);

    IncidentCheck queryEventInfo(String inName);

    int queryGreyBaseMapCountByEventId(String inName);
}
