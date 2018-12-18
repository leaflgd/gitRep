package org.zixing.dao;

import org.zixing.entity.Task;
import org.zixing.entity.TaskGrey;

import java.util.List;

/**
 * @Author: xzl
 * @Date: 2018/11/23 14:36
 * @Description:
 */

public interface DataSiftDao {

    int insertTaskGrey(List<TaskGrey> record);

    int insertTask(Task record);

    //提交处理修改灰底图是计数还是分析
    int updateGreyBaseMapCountCheck(List<Integer> greyBaseMapId);

    //提交处理修改灰底图是计数还是分析
    int updateGreyBaseMapAnalyCheck(List<Integer> greyBaseMapId);

    int findSubmitCountByInName(String inName);

    int updateGreyBaseMapCheck(List<TaskGrey> taskGreys);

    List<String> selectTaskByInName();

    List<Integer> selectTaskByDialPiece();
}
