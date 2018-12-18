package org.zixing.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auto xzl
 * 获取事件中的核型
 * 获取规则,获取最多相同核型，如果其中有(46，xy),输出(46，xy)，如果没有获取最后一条
 */
public class GetEventKaryotype {

    static final String assignKaryotypeXY = "46,XY";
    static final String assignKaryotypeXX = "46,XX";

    public static String getEventKaryotype(List<String> analysis) {
        //如果有已核对的核型
        if (analysis.size() > 0) {
            //获取规则,获取最多相同核型，如果其中有(46，xy),输出(46，xy)，如果没有获取最后一条

            //查看是否有46，XY核型
            if (analysis.contains(assignKaryotypeXY)) return assignKaryotypeXY;
            //查看是否有46，XX核型
            if (analysis.contains(assignKaryotypeXX)) return assignKaryotypeXX;

            Map<String, Integer> screenMap = new HashMap<String, Integer>();
            String maxKey = null;
            Integer maxValue = 0;
            for (String s : analysis) {
                Integer num = screenMap.get(s);
                if (s == null) continue;
                if (num == null) {
                    screenMap.put(s, 1);
                } else {
                    if (maxValue <= num) {
                        maxValue = num;
                        maxKey = s;
                    }
                    screenMap.put(s, ++num);
                }
            }
            //获取相同度最多的核型相同数
            //查看是否有46，XY核型
            if (analysis.contains(assignKaryotypeXY)) {
                //获取46，XY核型的相同数
                Integer assignKaryotypeNum = screenMap.get(assignKaryotypeXY);
                if (assignKaryotypeNum >= (screenMap.get(maxKey) == null ? 0 : screenMap.get(maxKey))) {
                    maxKey = assignKaryotypeXY;
                }
            }

            //查看是否有46，XX核型
            if (analysis.contains(assignKaryotypeXX)) {
                //获取46，XX核型的相同数
                Integer assignKaryotypeNum = screenMap.get(assignKaryotypeXX);
                if (assignKaryotypeNum >= (screenMap.get(maxKey) == null ? 0 : screenMap.get(maxKey))) {
                    maxKey = assignKaryotypeXX;
                }
            }

            return (maxKey == null ? analysis.get(analysis.size() - 1) : maxKey);
        }
        return null;
    }


}
