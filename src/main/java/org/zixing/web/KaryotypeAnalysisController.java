package org.zixing.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zixing.dao.CrayAnalysisDao;
import org.zixing.dao.CrayCountDao;
import org.zixing.dao.DataSiftDao;
import org.zixing.dao.KaryotypeAnalysisDao;
import org.zixing.dto.GreyBaseMap;
import org.zixing.dto.IncidentCheck;
import org.zixing.dto.SlideQuery;
import org.zixing.entity.User;
import org.zixing.service.CrayCountService;
import org.zixing.service.UserService;
import org.zixing.util.GetEventKaryotype;
import org.zixing.util.his.call.BaseInfoEntity;
import org.zixing.util.his.call.WsZxBaseInfo;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xzl
 * @Date: 2018/11/7 17:30
 * @Description:
 */
@Controller
@RequestMapping("KaryotypeAnalysis")
public class KaryotypeAnalysisController {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private KaryotypeAnalysisDao dao;
    @Autowired
    private UserService userService;
    @Autowired
    private CrayAnalysisDao analysisDao;
    @Autowired
    private CrayCountService crayCountService;
    @Autowired
    private DataSiftDao dataSiftDao;
    @Autowired
    private CrayCountDao countDao;


    @RequestMapping("eventList")
    @ResponseBody
    public Map getEventList(boolean isSearch, String endDate, String inName, String date, String check, HttpServletRequest request) {
        String loginName = (String) request.getSession().getAttribute("loginName");
        System.out.println(String.format("--------------inName :%s,date : %s,check: %s,endDate: %s", inName, date, check, endDate));
        int i = 0;
        
        //输入事件号去除空格
        inName = inName.replace(" ", "");
        
        if (inName == null) {
            ++i;
            inName = "";
        }
        if (date == null) {
            ++i;
            date = "";
        }
        if (check == null) {
            ++i;
            check = "";
        }
        if (endDate == null) {
            ++i;
            endDate = "";
        }
        if (i < 4) {
            userService.updateUserThreeInfo(endDate, date, check, inName, loginName);
        }
        Map<String, Object> map = new HashMap<>();

        if (endDate != "") {
            endDate = endDate + " 23:59";
        }
        List<IncidentCheck> eventList = dao.incidentQuery(inName, date, endDate, check);
        List<String> taskList = dataSiftDao.selectTaskByInName();
        for (IncidentCheck e : eventList) {

            if (taskList.contains(e.getInName())) {
                e.setStatus(false);
            } else {
                e.setStatus(true);
            }

        }
        map.put("event", eventList);


        User user = userService.userThreeInfoQuery(loginName);
        List<String> strList = new ArrayList<String>();
        userNotNull(user, strList);

        map.put("user", strList);
        map.put("dsum", isSearch ? null : request.getSession().getAttribute("dsum") == null ?
                request.getSession().getAttribute("inName") :
                request.getSession().getAttribute("dsum"));
        return map;

    }

    //获取用户搜索值
    @RequestMapping("getUserParams")
    @ResponseBody
    public List<String> getUserParams(HttpServletRequest request) {
        String loginName = (String) request.getSession().getAttribute("loginName");
        User user = userService.userThreeInfoQuery(loginName);
        List<String> strList = new ArrayList<String>();
        userNotNull(user, strList);
        return strList;

    }

    @RequestMapping("queryGreyBaseMap")
    @ResponseBody
    public Map queryGreyBaseMap(String inName, String pl, HttpServletRequest request) {
        System.out.println(String.format("inName :%s,pl : %s", inName, pl));

        if (inName == null || inName.equals("")) {
            inName = null;
        } else {
            request.getSession().setAttribute("inName", inName);
            request.getSession().setAttribute("dsum", inName);
        }
        if (pl == null || pl.equals("")) {
            pl = null;
        }

        Map<String, Object> map = new HashMap<>();
        List<GreyBaseMap> greyBaseMapList = dao.queryGreyBaseMapByEventIdOrPlectrumName(inName, pl);
        map.put("GreyBaseMap", greyBaseMapList);
        List<SlideQuery> plectrumList = dao.queryEventPlectrum(inName);

        //更新listSession对象
        request.getSession().setAttribute("chromQueryList", crayCountService.grayQServiceC2(inName));

        List<Integer> taskList = dataSiftDao.selectTaskByDialPiece();
        for (SlideQuery p : plectrumList) {

            if (taskList.contains(p.getSlideId())) {
                p.setStatus(0);
            } else {
                p.setStatus(1);
            }

        }
        map.put("Plectrum", plectrumList);
        IncidentCheck userInfo = dao.queryEventInfo(inName);
        userInfo.setAnalyStatusSum(greyBaseMapList.size());


        try {
            if (userInfo.getCardName() == null || userInfo.getCardName().equals("")) {

                if (inName.indexOf("A") > -1) {
                    userInfo.setCardName(null);
                    userInfo.setCardSex(-1);
                }else
                // 如果是生产环境，调用his获取患者信息
                if (new File("/home/uftp/his_web_service/orm.xml").exists()) {
                    try {
                        String hisResult = new WsZxBaseInfo(inName).getHisResult();
                        BaseInfoEntity e = WsZxBaseInfo.parsEntity(hisResult);
                        userInfo.setCardSex(e.getXb() != null && e.getXb().equals("男") ? 0 : 1);
                        userInfo.setCardName(e.getXm());
                        log.info("核型分析调用his系统获取信息 BaseInfoEntity:" + e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        log.info("核型分析调用his系统获取信息错误：" + ex.getMessage());
                    }
                }

            }

            if (userInfo.getCaryogram() == null || userInfo.getCaryogram() == "")
                throw new NullPointerException("无生产报告，改从匹配规则中查询");
        } catch (Exception e) {
            log.info("queryGreyBaseMap:"+e.getMessage());
            List<String> analysis = analysisDao.queryAllArithKaryotype(inName);
            if (analysis.size() > 0) {
                //设置匹配规则的核型
                userInfo.setCaryogram(GetEventKaryotype.getEventKaryotype(analysis));
            } else {
                log.info("根据Event_id 获取核型为空");
            }
        }
        map.put("UserInfo", userInfo);
        //获取末次生成报告核型
        map.put("reportRecord", countDao.recordFind(inName));
        return map;
    }

    public void userNotNull(User user, List<String> strList) {
        if (user != null) {
            strList.add(user.getInfoOne());
            strList.add(user.getInfoTwo());
            strList.add(user.getInfoThree());
            strList.add(user.getEndDate());
        }
    }

    //跳转到分割
    @RequestMapping("GoToCrayexcision")
    public String goToCrayexcision(HttpServletRequest request) {

        String grayid = request.getParameter("grayid");
        request.getSession().setAttribute("grayid", grayid);
        return "redirect:/page/jsp/caryogram/operation/crayexcision.jsp";

    }

    //跳转到分析
    @RequestMapping("GoToChromanalyse")
    public String goToChromanalyse(HttpServletRequest request) {
        String grayid = request.getParameter("grayid");
        request.getSession().setAttribute("grayid", grayid);
        return "redirect:/page/jsp/caryogram/operation/chromanalyse.jsp";
    }

}
